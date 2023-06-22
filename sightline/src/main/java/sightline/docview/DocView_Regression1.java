package sightline.docview;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Callable;

import org.mockito.internal.configuration.injection.filter.NameBasedCandidateFilter;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.MiniDocListPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Regression1 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	AssignmentsPage agnmt;
	DocViewRedactions redact;
	SecurityGroupsPage security;
	String roll;

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id : 52167 - Verify the tool tip displayed on mouse over of 'Code
	 * same as last' when document having rectangle redaction in context of
	 * assignment. Description : To Verify the tool tip displayed 'Code same as
	 * last' when document having rectangle redaction in context of assignment
	 */
	@Test(description = "RPMXCON-52167", alwaysRun = true, groups = { "regression" })
	public void verifyToolTipDisplayedWhenDocumentHavingRedaction() throws Exception {
		baseClass = new BaseClass(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-52167");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify the tool tip displayed on mouse over of 'Code same as last' when document having rectangle redaction in context of assignment ####");

		docView = new DocViewPage(driver);
		agnmt = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
//		baseClass.selectproject(Input.additionalDataProject);

		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Bulk assign with new assignment");
		sessionSearch.bulkAssignWithNewAssignment();

		baseClass.stepInfo("Create assignment by bulk assign operationfrom Session search");
		agnmt.createAssignmentByBulkAssignOperation(assignmentName, Input.codeFormName);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Edit assignment by assignment name");
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);

		baseClass.stepInfo("Enabling redactions toogle in assignment page");
		agnmt.enableToogleToEnableRedactions(true);

		baseClass.stepInfo("Select assignment to view in Doc view");

		agnmt.selectAssignmentToViewinDocview(assignmentName);

		String docId = docView.getDocumentWithoutRedaction();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId);

		baseClass.stepInfo("Click on reduction button ");
		baseClass.waitTime(5);
		docViewMetaDataPage.clickOnRedactAndRectangle();

		baseClass.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(5, 55, Input.defaultRedactionTag);

		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil");
		docView.verifyCodeSameAsLastDocMsgIsDisplayed(Input.codeSameAsLastMsg);

		baseClass.stepInfo(
				"Verify Code same as last doc message is displayed by mouse over on code last white pencil on child window");
		docView.verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(Input.codeSameAsLastMsg);
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id : 52166 - Verify that tool tip displayed on mouse over of 'Code
	 * same as last' when document having all page redaction Description : To Verify
	 * the tool tip displayed 'Code same as last' when document having rectangle
	 * redaction.
	 */
	@Test(description = "RPMXCON-52166", alwaysRun = true, groups = { "regression" })
	public void verifyToolTipMessageDisplayedWhenDocHavingRedaction() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52166");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify the tool tip displayed on mouse over of 'Code same as last' when document having rectangle redaction by Doc view basic session search ####");

		docView = new DocViewPage(driver);
		agnmt = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		baseClass.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15, Input.defaultRedactionTag);

		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil");
		docView.verifyCodeSameAsLastDocMsgIsDisplayed(Input.codeSameAsLastMsg);

		baseClass.stepInfo(
				"Verify Code same as last doc message is displayed by mouse over on code last white pencil on child window");
		docView.verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(Input.codeSameAsLastMsg);
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id : 52164 - Verify that tool tip displayed on mouse over of 'Code
	 * same as last' when document having all page redaction Description : To Verify
	 * the tool tip displayed 'Code same as last' when document having this page
	 * redaction.
	 */
	@Test(description = "RPMXCON-52164", alwaysRun = true, groups = { "regression" })
	public void verifyToolTipMsgDisplayedWhenDocHavingThisPageRedaction() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52166");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify the tool tip displayed on mouse over of 'Code same as last' when document having this redaction by Doc view basic session search ####");

		docView = new DocViewPage(driver);
		agnmt = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		redact = new DocViewRedactions(driver);

		baseClass.stepInfo("Set this page reduct in doc");
		redact.performThisPageRedaction(Input.defaultRedactionTag);

		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil");
		docView.verifyCodeSameAsLastDocMsgIsDisplayed(Input.codeSameAsLastMsg);

		baseClass.stepInfo(
				"Verify Code same as last doc message is displayed by mouse over on code last white pencil on child window");
		docView.verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(Input.codeSameAsLastMsg);
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id : 52163 - Verify that tool tip displayed on mouse over of 'Code
	 * same as last' when document having all page redaction Description : To Verify
	 * the tool tip displayed 'Code same as last' when document having multi page
	 * redaction.
	 */
	@Test(description = "RPMXCON-52163", alwaysRun = true, groups = { "regression" })
	public void verifyToolTipMsgDisplayedWhenDocHavingMultiRedaction() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52163");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify the tool tip displayed on mouse over of 'Code same as last' when document having multiple page redaction by Doc view basic session search ####");

		docView = new DocViewPage(driver);
		agnmt = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		redact = new DocViewRedactions(driver);

		baseClass.stepInfo("Set multi page reduct in doc");
		redact.performAllPagesMultiPageRedaction(Input.defaultRedactionTag);

		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil");
		docView.verifyCodeSameAsLastDocMsgIsDisplayed(Input.codeSameAsLastMsg);

		baseClass.stepInfo(
				"Verify Code same as last doc message is displayed by mouse over on code last white pencil on child window");
		docView.verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(Input.codeSameAsLastMsg);
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id : 52151 - Verify that when applied redaction tag is unassigned
	 * then blank row should be displayed in edit redaction pop up on doc view
	 * Description : To Verify that blank row should be displayed on the drop down
	 * when redaction tag is unassigned from security group.
	 */
	@Test(description = "RPMXCON-52151", alwaysRun = true, groups = { "regression" })
	public void verifyBlankRowDisplayedAppliedRedactionIsUnTaggedFromSecurityGroup() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52151 DocView Sprint 03");
		utility = new Utility(driver);
		String redactnam = "ATest" + Utility.dynamicNameAppender();
		final String redactname = redactnam;
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that when applied redaction tag is unassigned then blank row should be displayed in edit redaction pop up on doc view ####");

		docView = new DocViewPage(driver);
		RedactionPage redactTag = new RedactionPage(driver);

		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password, Input.additionalDataProject);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.stepInfo("Add redaction tag");
		redactTag.AddRedaction(redactname, "RMU");
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		baseClass.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15, redactname);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.additionalDataProject);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		security = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Navigate To Security Gropus Page URL");
		security.navigateToSecurityGropusPageURL();

		baseClass.stepInfo("Click on Security group");
		security.selectSecurityGroupAndClickOnProjectFldLink(Input.securityGroup);

		baseClass.stepInfo("Un tag redaction from security group");
		driver.waitForPageToBeReady();
		security.unTagFromRedatctionTags(redactname);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with RMU");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password, Input.additionalDataProject);

		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		redact = new DocViewRedactions(driver);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Unredact on performed redaction");
		redact.clickOnLastPerformedRedactionOnCurrentDoc();

		baseClass.stepInfo("Verify selected option from untag select dropdown is blank");
		redact.verifySelectedOptionFromUnRedactionDropdownIsBlank();
		loginPage.logout();

	}

	/**
	 * Author : Gopinath date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-52152 :: Verify that when adding redaction in audio document blank
	 * row should not be displayed. Description : Verify blank row should not
	 * displayed while redaction.
	 */

	@Test(description = "RPMXCON-52152", groups = { "regression" })
	public void verifyBlankRowNotDisplayedFromDefaultRedactionDropdown() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52152");
		baseClass.stepInfo(
				"#### Verify that when adding redaction in audio document blank row should not be displayed. ####");

		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Audio search for audio document in advanced search");
		sessionsearch.audioSearch("Morning", Input.language);

		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionsearch.ViewInDocView();

		baseClass.stepInfo("Click on add redaction for audio document");
		docViewRedact.clickOnAddRedactionForAudioDocument();

		baseClass.stepInfo("Verify redaction dropdown for audio document is not blank");
		docViewRedact.verifyWeatherAudioRedactionDropdownIsBlank(false);
		loginPage.logout();
	}

	/**
	 * Author : Gopinath date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-52153 :: Verify that when applied redaction tag is unassigned then
	 * blank row should be displayed in edit redaction pop up on audio doc view.
	 * Description : Verify blank row should displayed while selected redaction tag
	 * uassigned from security groups.
	 */

	@Test(description = "RPMXCON-52153", groups = { "regression" })
	public void verifyBlankRowDisplayedWhenUnAssginedRedactionTag() throws Exception {
		baseClass = new BaseClass(driver);
		utility = new Utility(driver);

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		String redactnam = "ATest" + utility.dynamicNameAppender();
		final String redactname = redactnam;
		baseClass.stepInfo("Test case Id: RPMXCON-52153- Sprint 3-DocView");
		baseClass.stepInfo(
				"#### Verify that when applied redaction tag is unassigned then blank row should be displayed in edit redaction pop up on audio doc view. ####");
		RedactionPage redactTag = new RedactionPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate to redactions page");
		redactTag.navigateToRedactionsPageURL();

		baseClass.stepInfo("Add redaction tag");
		redactTag.AddRedaction(redactname, "RMU");

		SessionSearch sessionsearch = new SessionSearch(driver);

		baseClass.stepInfo("Audio search for audio document in advanced search");
		sessionsearch.audioSearch("Morning", Input.language);

		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionsearch.ViewInDocView();

		baseClass.stepInfo("Delete all applied redactions");
		docViewRedact.deleteAllAppliedRedactions();

		baseClass.stepInfo("Click on add redaction for audio document");
		docViewRedact.clickOnAddRedactionForAudioDocument();

		baseClass.stepInfo("Add audio redaction");
		docViewRedact.addAudioRedaction(Input.startTime, Input.endTime, redactname);

		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);

		security = new SecurityGroupsPage(driver);

		baseClass.stepInfo("navigate to security groups page");
		security.navigateToSecurityGropusPageURL();

		baseClass.stepInfo("Click on Security group");
		security.selectSecurityGroupAndClickOnProjectFldLink(Input.securityGroup);

		baseClass.stepInfo("Un tag redaction from security group");
		security.unTagFromRedatctionTags(redactname);

		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with RMU");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		sessionsearch = new SessionSearch(driver);

		baseClass.stepInfo("Audio search for audio document in advanced search");
		sessionsearch.audioSearch("Morning", Input.language);

		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionsearch.ViewInDocView();

		baseClass.stepInfo("Edit applied redaction");
		docViewRedact.editAppliedRedaction();

		baseClass.stepInfo("Verify redaction dropdown for audio document is blank");
		docViewRedact.verifyWeatherAudioRedactionDropdownIsBlank(true);
		loginPage.logout();
	}

	/**
	 * Author : Krishna Created date: NA Modified date: NA Modified by:Krishna
	 * TestCase id : 52169 & 52168 - Verify the tool tip displayed on mouse over of
	 * 'Code same as last' when document having page Redaction Description : Page
	 * Range Redaction in context of assignment
	 */
	@Test(description = "RPMXCON-52169,RPMXCON-52168", alwaysRun = true, groups = { "regression" })
	public void verifyToolTip() throws Exception {
		baseClass = new BaseClass(driver);
		String assignmentName = Input.randomText + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-52169");
		baseClass.stepInfo("Test case Id: RPMXCON-52168");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify the tool tip displayed on mouse over of 'Code same as last' when document having rectangle redaction in context of assignment ####");
		docView = new DocViewPage(driver);
		agnmt = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.selectproject(Input.additionalDataProject);

		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		// searching for docs and bulk assigning
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Bulk assign with new assignment");
		sessionSearch.bulkAssignWithNewAssignment();
		baseClass.stepInfo("Create assignment by bulk assign operationfrom Session search");
		agnmt.createAssignmentByBulkAssignOperation(assignmentName, Input.codeFormName);
		// selecting assignment to view in DocView
		baseClass.stepInfo("Edit assignment by assignment name");
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.stepInfo("Enabling redactions toogle in assignment page");
		agnmt.enableToogleToEnableRedactions(true);
		baseClass.stepInfo("Select assignment to view in Doc view");
		agnmt.selectAssignmentToViewinDocview(assignmentName, Input.additionalDataProject);
		// Clicking redaction Icon and selecting page range
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingRedactionIcon();
		Actions actions = new Actions(driver.getWebDriver());
		actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
		actions.click().build().perform();
		docViewRedact.enteringPagesInMultipageTextBox(Input.pageRange);
		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil");
		docView.verifyCodeSameAsLastDocMsgIsDisplayed(Input.codeSameAsLastMsg);
		baseClass.stepInfo(
				"Verify Code same as last doc message is displayed by mouse over on code last white pencil on child window");
		docView.verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(Input.codeSameAsLastMsg);
		// Clicking redaction Icon and selecting this page redaction
		actions.moveToElement(docViewRedact.thisPageRedaction().getWebElement());
		actions.click().build().perform();
		baseClass.waitTime(5);
		baseClass.hitEnterKey(2);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil");
		docView.verifyCodeSameAsLastDocMsgIsDisplayed(Input.codeSameAsLastMsg);
		baseClass.stepInfo(
				"Verify Code same as last doc message is displayed by mouse over on code last white pencil on child window");
		docView.verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(Input.codeSameAsLastMsg);
		loginPage.logout();
	}

	/**
	 * Author : Krishna Created date: NA Modified date: NA Modified by:Krishna
	 * TestCase id : 52165 - Verify the tool tip displayed on mouse over of 'Code
	 * same as last' when document having page Redaction Description : In context of
	 * SG for page range Redaction
	 */
	@Test(description = "RPMXCON-52165", alwaysRun = true, groups = { "regression" })
	public void verifyToolTipForSG() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52165");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify the tool tip displayed on mouse over of 'Code same as last' when document having rectangle redaction in context of assignment ####");
		docView = new DocViewPage(driver);
		agnmt = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.selectproject(Input.additionalDataProject);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingRedactionIcon();
		Actions actions = new Actions(driver.getWebDriver());
		actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
		actions.click().build().perform();
		docViewRedact.enteringPagesInMultipageTextBox(Input.pageRange);
		baseClass.stepInfo("Verify Code same as last doc message is displayed by mouse over on code last white pencil");
		docView.verifyCodeSameAsLastDocMsgIsDisplayed(Input.codeSameAsLastMsg);
		baseClass.stepInfo(
				"Verify Code same as last doc message is displayed by mouse over on code last white pencil on child window");
		docView.verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(Input.codeSameAsLastMsg);
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: NA Testcase
	 * Testcase id : 51619 - Verify that the undocked windows should be docked to
	 * parent window when the user refreshes the DocView page. Description : Verify
	 * that the undocked windows should be docked to parent window when the user
	 * refreshes the DocView page.
	 * 
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-51619", groups = { "regression" })
	public void verifyAssignSingleDocumentOnDocList() throws InterruptedException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51619 DocView/MetaData Sprint 06");

		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that the undocked windows should be docked to parent window when the user refreshes the DocView page. ####");
		manageAssignment = new ManageAssignment(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate to assignments page");
		manageAssignment.navigateToAssignmentsPageURL();

		int row = manageAssignment.getDocViewRowNum();

		baseClass.stepInfo("Selecting assignment from assignment group table");
		manageAssignment.selectRow(row);

		baseClass.stepInfo("Navigate to doc view page from assignments page");
		manageAssignment.navigateToDocView(row);

		baseClass.stepInfo("Handling new windows in docview page");
		docViewMetaDataPage.verifyingTheNewWindowsInDocView();
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA ////@Testcase_Id : RPMXCON-51903
	 * @Description : Verify that after impersonation icon should be displayed on
	 *              doc view header for expand/collapse
	 */
	@Test(description = "RPMXCON-51903", groups = { "regression" })
	public void verifyingReviewManagerChangesInDocView() throws Exception {
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("Test case Id: RPMXCON-51903 -Production Sprint 07");
		baseClass.stepInfo(
				"#### Verify that after impersonation icon should be displayed on doc view header for expand/collapse. ####");
		baseClass.stepInfo("Impersnated from RMU to Reviewer");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.impersonateRMUtoReviewer();

		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Navigate to session search");
//		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		sessionSearch.navigateToSessionSearchPageURL();

		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("navigating to docview page");
		sessionSearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Refreshing the page");
		driver.Navigate().refresh();

		baseClass.stepInfo("impersnated from reviewer to RMU");
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA. ////@Testcase ID :
	 *         RPMXCON-52206 : Verify that Producing a TIFF with reviewer remarks
	 *         working properly and not eliminate any text characters in the
	 *         produced document.
	 * @Description : Verify that Producing a TIFF with reviewer remarks working
	 *              properly and not eliminate any text characters in the produced
	 *              document.
	 */
	@Test(description = "RPMXCON-52206", groups = { "regression" })
	public void verifyProducingTIFFWithReviewerRemarksWorkingProperly() throws Exception {
		baseClass = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-52206 Production-sprint:07");
		baseClass.stepInfo(
				"####  Verify that Producing a TIFF with reviewer remarks working properly and not eliminate any text characters in the produced document. ####");

		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();
//
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		baseClass.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for the created folder and check the pure hit count
		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Add Docs Met Criteria To Action Board");
		search.addDocsMetCriteriaToActionBoard();

		// Add Remark
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Navigate To Doc View Page URL");
		docView.navigateToDocViewPageURL();

		String docId = docView.getDocumentWithoutRedaction();

		baseClass.stepInfo("Add Remark To Non Audio Document");

		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId);
		baseClass.stepInfo("Add Remark To Non Audio Document");
		docView.addRemarkToNonAudioDocument(1, 20, remark);

		baseClass.stepInfo("Verify Remark Is Added");
		docView.verifyRemarkIsAdded(remark);

		search = new SessionSearch(driver);

		search.bulkFolderExisting(foldername);

		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);

		baseClass.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		baseClass.stepInfo("Navigate To Production Page");
		page.navigateToProductionPage();

		baseClass.stepInfo("Add new production");
		page.addANewProduction(productionname);

		baseClass.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		baseClass.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		baseClass.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction(tagname);

		baseClass.stepInfo("Filling text section");
		page.fillingTextSection();

		baseClass.stepInfo("Navigate to next section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		baseClass.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		baseClass.stepInfo("Filling Generate Page And Regenerating Again");
		page.clickOnGenerateButtonAndVerifyPreGenChecksStatus();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. ////@Testcase ID :
	 *         RPMXCON-52205 :Verify that Producing a PDF with reviewer remarks
	 *         working properly and not eliminate any text characters in the
	 *         produced document.
	 * @Description : Verify that Producing a PDF with reviewer remarks working
	 *              properly and not eliminate any text characters in the produced
	 *              document.
	 */
	@Test(description = "RPMXCON-52205", groups = { "regression" })
	public void verifyProducingPDFWithReviewerRemarksWorkingProperly() throws Exception {
		baseClass = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-52205 Production-sprint:07");
		baseClass.stepInfo(
				"#### Verify that Producing a PDF with reviewer remarks working properly and not eliminate any text characters in the produced document. ####");

		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		baseClass.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		baseClass.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for the created folder and check the pure hit count
		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Add Docs Met Criteria To Action Board");
		search.addDocsMetCriteriaToActionBoard();

		// Add Remark
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Navigate To Doc View Page URL");
		docView.navigateToDocViewPageURL();

		String docId = docView.getDocumentWithoutRedaction();
		System.out.println(docId);

		baseClass.stepInfo("Add Remark To Non Audio Document");

		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId);
		baseClass.stepInfo("Add Remark To Non Audio Document");
		docView.addRemarkToNonAudioDocument(5, 55, remark);

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify Remark Is Added");
		docView.verifyRemarkIsAdded(remark);

		search = new SessionSearch(driver);

		search.bulkFolderExisting(foldername);

		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);

		baseClass.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		baseClass.stepInfo("Navigate To Production Page");
		page.navigateToProductionPage();

		baseClass.stepInfo("Add new production");
		page.addANewProduction(productionname);

		baseClass.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		baseClass.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		baseClass.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingPDFSectionwithBurnRedaction(tagname);

		baseClass.stepInfo("Filling text section");
		page.fillingTextSection();

		baseClass.stepInfo("Navigate to next section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		baseClass.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		baseClass.stepInfo("Filling Generate Page And Regenerating Again");
		page.clickOnGenerateButtonAndVerifyPreGenChecksStatus();
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @throws InterruptedException ////@Testcase_Id : RPMXCON-51855 : Verify that
	 *                              persistent hits panel should not retain
	 *                              previously viewed hits for the document on
	 *                              completing the document.
	 * @description : Verify that persistent hits panel should not retain previously
	 *              viewed hits for the document on completing the document after
	 *              applying the coding stamp from coding form child window.
	 */

	@Test(description = "RPMXCON-51855", groups = { "regression" })
	public void verifyPersistentHitsPanelDisplayedNavigatedToNextDoc() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-51855 Production-sprint:07");
		baseClass.stepInfo(
				"#### Verify that persistent hits panel should not retain previously viewed hits for the document on completing the document after applying the coding stamp from coding form child window. ####");
		String assignmentName = Input.randomText + Utility.dynamicNameAppender();

		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Create Assignment");
		assgnPage.createAssignment(assignmentName, Input.codeFormName);

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Bulk Assign Existing");
		search.bulkAssignExisting(assignmentName);

		baseClass.stepInfo("Created a assignment " + assignmentName);

		baseClass.selectproject();

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Edit Assignment Using Pagination Concept");
		assgnPage.editAssignmentUsingPaginationConcept(assignmentName);

		baseClass.stepInfo("Add Reviewer And Distribute Docs");
		assgnPage.addReviewerAndDistributeDocs();

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Select assignment to view in doc view");
		assgnPage.selectAssignmentToViewinDocview(assignmentName);

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Click on persistant hit eye icon.");
		docView.clickOnPersistantHitEyeIcon();

		baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
		docView.verifyPersistentHitsPanelDisplayed();

		baseClass.stepInfo("Editing Coding Form And Entering To Next Document");
		driver.waitForPageToBeReady();
		docView.editingCodingFormAndEnteringToNextDocument(Input.randomText);

		baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
		docView.verifyPersistentHitsPanelDisplayed();

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assgnPage.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @throws InterruptedException ////@Testcase_Id : RPMXCON-51761 : Verify that
	 *                              toggle to not to view the 0 hit terms does not
	 *                              show when there are no terms at all in the
	 *                              panel.
	 * @description : Verify that toggle to not to view the 0 hit terms does not
	 *              show when there are no terms at all in the panel (when there are
	 *              no search terms or keywords that apply to this document).
	 */

	@Test(description = "RPMXCON-51855", groups = { "regression" })
	public void verify0HitTermsToogleNotDisplayedToAdvancedWorkProduct() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-51761 Production-sprint:07");
		baseClass.stepInfo(
				"#### Verify that toggle to not to view the 0 hit terms does not show when there are no terms at all in the panel (when there are no search terms or keywords that apply to this document). ####");
		String folderName = Input.randomText + Utility.dynamicNameAppender();
		String assignmentName = Input.randomText + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate To Tags And Folder Page");
		tagPage.navigateToTagsAndFolderPage();

		baseClass.stepInfo("Create Folder");
		tagPage.CreateFolder(folderName, Input.securityGroup);

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Bulk Folder Existing");
		search.bulkFolderExisting(folderName);

		baseClass.stepInfo("Log out");
		loginPage.logout();

		baseClass.stepInfo("Login To SightLine");
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		baseClass.stepInfo("Advance Search By Folder");
		search.advanceSearchByFolder(folderName);

		baseClass.stepInfo("Add Docs Met Criteria To Action Board");
		search.ViewInDocViews();

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Click On Persistant Hit Eye Icon");
		docView.clickOnPersistantHitEyeIcon();

		baseClass.stepInfo("Persistent Hits Toogle Not Displayed");
		docView.verifyPersistentHitsToogleNotDisplayed();

		baseClass.stepInfo("Log out");
		loginPage.logout();

		baseClass.stepInfo("Login To SightLine");
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		AssignmentsPage assgnPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Create Assignment");
		assgnPage.createAssignment(assignmentName, Input.codeFormName);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Bulk Assign Existing");
		search.bulkAssignExisting(assignmentName);

		baseClass.stepInfo("Created a assignment " + assignmentName);

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Select assignment to view in doc view");
		assgnPage.selectAssignmentToViewinDocview(assignmentName);

		baseClass.stepInfo("Navigate To Doc View Page URL");
		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Click on persistant hit eye icon.");
		docView.clickOnPersistantHitEyeIcon();

		baseClass.stepInfo("Persistent Hits Toogle Not Displayed");
		docView.verifyPersistentHitsToogleNotDisplayed();

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assgnPage.deleteAssignment(assignmentName);
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @throws InterruptedException ////@Testcase_Id : RPMXCON-51760 : Verify when
	 *                              navigated to doc view with two different ad-hoc
	 *                              results with term/phrase are highlighted which
	 *                              are exists in the document.
	 * @description : Verify when navigated to doc view with two different ad-hoc
	 *              results with term/phrase are highlighted which are exists in the
	 *              document.
	 */

	@Test(description = "RPMXCON-51760", groups = { "regression" })
	public void verifyTwoDifferentAdhocResultsWithTermAndPhrase() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-51760 Production-sprint:07");
		baseClass.stepInfo(
				"#### Verify when navigated to doc view with two different ad-hoc results with term/phrase are highlighted which are exists in the document ####");
		SessionSearch search = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Add Docs Met Criteria To Action Board");
		search.ViewInDocViews();

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Get Persistent Hit");
		docView.getPersistentHit(Input.searchString1);

		baseClass.stepInfo("Log out");
		loginPage.logout();

		baseClass.stepInfo("Login To SightLine");
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearch("\"" + Input.thankyouText + "\"");

		baseClass.stepInfo("Add Docs Met Criteria To Action Board");
		search.ViewInDocViews();

		baseClass.stepInfo("Get Persistent Hit");
		docView.getPersistentHit(Input.thankyouText);

		baseClass.stepInfo("Log out");
		loginPage.logout();

		baseClass.stepInfo("Login To SightLine");
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearchForTwoItems("\"" + Input.thankyouText + "\"", Input.searchString1);

		baseClass.stepInfo("Add Docs Met Criteria To Action Board");
		search.ViewInDocViews();

		baseClass.stepInfo("Get Persistent Hit");
		docView.getPersistentHit(Input.searchString1);
		docView.getPersistentHit(Input.thankyouText);
		loginPage.logout();

	}

	/**
	 * @author Gopinath
	 * @throws InterruptedException ////@Testcase_Id : RPMXCON-52200 : Verify that
	 *                              Producing a TIFF with Block Redaction burning
	 *                              properly and not eliminate any characters in the
	 *                              produced document..
	 * @description : Verify that Producing a TIFF with Block Redaction burning
	 *              properly and not eliminate any characters in the produced
	 *              document.
	 */

	@Test(description = "RPMXCON-52200", groups = { "regression" })
	public void verifyThatProducingTiffWithBlockRedaction() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-52200 Production-sprint:07");
		baseClass.stepInfo(
				"#### Verify that Producing a TIFF with Block Redaction  burning properly and not eliminate any characters in the produced document. ####");
		String redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate To Redactions Page URL");
		redactionpage.navigateToRedactionsPageURL();

		baseClass.stepInfo("Manage Redaction Tags Page");
		redactionpage.manageRedactionTagsPage(redactiontag1);

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Add Docs Met Criteria To Action Board");
		search.addDocsMetCriteriaToActionBoard();

		docViewMetaDataPage = new DocViewMetaDataPage(driver);

		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		baseClass.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15, redactiontag1);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		baseClass.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Bulk Folder Existing");
		search.bulkFolderExisting(foldername);

		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);

		baseClass.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		baseClass.stepInfo("Navigate To Production Page");
		page.navigateToProductionPage();

		baseClass.stepInfo("Add new production");
		page.addANewProduction(productionname);

		baseClass.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		baseClass.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		baseClass.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction(redactiontag1);

		baseClass.stepInfo("Filling text section");
		page.fillingTextSection();

		baseClass.stepInfo("Navigate to next section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		baseClass.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		baseClass.stepInfo("Filling Generate Page And Regenerating Again");
		page.clickOnGenerateButtonAndVerifyPreGenChecksStatus();
		loginPage.logout();

	}

	/**
	 * @author Gopinath
	 * @throws InterruptedException ////@Testcase_Id : RPMXCON-52199 :Verify that
	 *                              Producing a TIFF with Page Redaction burning
	 *                              properly and not eliminate any characters in the
	 *                              produced document.
	 * @description : Verify that Producing a TIFF with Page Redaction burning
	 *              properly and not eliminate any characters in the produced
	 *              document.
	 */

	@Test(description = "RPMXCON-52199", groups = { "regression" })
	public void verifyThatProducingTiffWithPageRedaction() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-52199 Production-sprint:07");
		baseClass.stepInfo(
				"#### Verify that Producing a TIFF with Page Redaction  burning properly and not eliminate any characters in the produced document. ####");
		String redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate To Redactions Page URL");
		redactionpage.navigateToRedactionsPageURL();

		baseClass.stepInfo("Manage Redaction Tags Page");
		redactionpage.manageRedactionTagsPage(redactiontag1);

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Add Docs Met Criteria To Action Board");
		search.addDocsMetCriteriaToActionBoard();

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);

		baseClass.stepInfo("Clicking Redaction Icon");
		docViewRedact.clickingRedactionIcon();

		baseClass.stepInfo("Perform This Page Redaction");
		docViewRedact.performThisPageRedaction(redactiontag1);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		baseClass.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Bulk Folder Existing");
		search.bulkFolderExisting(foldername);

		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);

		baseClass.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		baseClass.stepInfo("Navigate To Production Page");
		page.navigateToProductionPage();

		baseClass.stepInfo("Add new production");
		page.addANewProduction(productionname);

		baseClass.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		baseClass.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		baseClass.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction(redactiontag1);

		baseClass.stepInfo("Filling text section");
		page.fillingTextSection();

		baseClass.stepInfo("Navigate to next section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		baseClass.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		baseClass.stepInfo("Filling Generate Page And Regenerating Again");
		page.clickOnGenerateButtonAndVerifyPreGenChecksStatus();
		loginPage.logout();

	}

	/**
	 * Author : Brundha Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52196 -Verify that Producing a PDF with Page Redaction burning properly
	 * and not eliminate any characters in the produced document. Description
	 * :Verify that Producing a PDF with Page Redaction burning properly and not
	 * eliminate any characters in the produced document.
	 */
	@Test(description = "RPMXCON-52196", alwaysRun = true, groups = { "regression" })
	public void verifyTheRedactionOfTagInProduction() {
		try {
			baseClass = new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-52196 Production-sprint:07");

			String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
			String prefixID = "A_" + Utility.dynamicNameAppender();
			String suffixID = "_P" + Utility.dynamicNameAppender();
			String foldername = "FolderProd" + Utility.dynamicNameAppender();
			baseClass.stepInfo(
					"#### Verify that Producing a PDF with Page Redaction burning properly and not eliminate any characters in the produced document. ####");

			RedactionPage redactionpage = new RedactionPage(driver);
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			UtilityLog.info("Logged in as User: " + Input.rmu1userName);
			Reporter.log("Logged in as User: " + Input.rmu1password);
			redactionpage.navigateToRedactionsPageURL();
			redactionpage.manageRedactionTagsPage(Redactiontag1);

			SessionSearch sessionSearch = new SessionSearch(driver);
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.ViewInDocView();
			baseClass.waitTime(4);

			DocViewPage doc = new DocViewPage(driver);
			doc.nonAudioPageRedaction(Redactiontag1);
			String productionname;
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

			SessionSearch sessionSearch1 = new SessionSearch(driver);
			sessionSearch1 = new SessionSearch(driver);
			sessionSearch1.bulkFolderExisting(foldername);

			// create production with DAT,Native,PDF& ingested Text
			ProductionPage page = new ProductionPage(driver);
			String beginningBates = page.getRandomNumber(2);
			productionname = "p" + Utility.dynamicNameAppender();
			page.selectingDefaultSecurityGroup();
			page.addANewProduction(productionname);
			page.fillingDATSection();
			page.fillingNativeSection();
			page.fillingPDFSection();
			page.disablePrivDocAndEnableBurnRedaction();

			page.burnRedactionWithTag(Redactiontag1);
			page.fillingTextSection();
			page.navigateToNextSection();
			page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
			page.navigateToNextSection();
			page.fillingDocumentSelectionPage(foldername);
			page.navigateToNextSection();
			page.fillingPrivGuardPage();
			page.fillingProductionLocationPage(productionname);
			page.navigateToNextSection();
			page.fillingSummaryAndPreview();

			// Wait until Generate button enables
			baseClass.waitForElement(page.getbtnProductionGenerate());
			page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		} catch (InterruptedException e) {
			baseClass
					.stepInfo("Exception occured while handling  Producing a PDF with Page Redaction" + e.getMessage());
			e.printStackTrace();
		}
		loginPage.logout();

	}

	/**
	 * Author : Brundha Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52197 -Verify that Producing a PDF with Block Redaction burning properly
	 * and not eliminate any characters in the produced document. Description
	 * :Verify that Producing a PDF with Block Redaction burning properly and not
	 * eliminate any characters in the produced document.
	 * 
	 */
	@Test(description = "RPMXCON-52197", alwaysRun = true, groups = { "regression" })
	public void verifyThRectangleeRedactionOfTagInProduction() {
		try {
			baseClass = new BaseClass(driver);
			baseClass.stepInfo("RPMXCON-52197 Production-sprint:07");

			String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
			String prefixID = "A_" + Utility.dynamicNameAppender();
			String suffixID = "_P" + Utility.dynamicNameAppender();
			baseClass.stepInfo(
					"#### Verify that Producing a PDF with Block Redaction burning properly and not eliminate any characters in the produced document. ####");
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			UtilityLog.info("Logged in as User: " + Input.rmu1userName);
			Reporter.log("Logged in as User: " + Input.rmu1password);

			RedactionPage redactionpage = new RedactionPage(driver);
			redactionpage.navigateToRedactionsPageURL();
			redactionpage.manageRedactionTagsPage(Redactiontag1);

			SessionSearch sessionSearch = new SessionSearch(driver);
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.ViewInDocView();

			docViewMetaDataPage = new DocViewMetaDataPage(driver);

			baseClass.stepInfo("Click on reduction button ");
			baseClass.waitTime(5);
			docViewMetaDataPage.clickOnRedactAndRectangle();

			driver.waitForPageToBeReady();
			docViewMetaDataPage.redactbyrectangle(10, 15, Redactiontag1);
			String foldername = "FolderProd" + Utility.dynamicNameAppender();
			String productionname = "p" + Utility.dynamicNameAppender();;
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
			SessionSearch sessionSearch1 = new SessionSearch(driver);
			sessionSearch1 = new SessionSearch(driver);
			sessionSearch1.bulkFolderExisting(foldername);

			// create production with DAT,Native,PDF& ingested Text
			ProductionPage page = new ProductionPage(driver);
			String beginningBates = page.getRandomNumber(2);
			page.selectingDefaultSecurityGroup();
			page.addANewProduction(productionname);
			page.fillingDATSection();
			page.fillingNativeSection();
			page.fillingPDFSection();
			page.disablePrivDocAndEnableBurnRedaction();

			page.burnRedactionWithTag(Redactiontag1);
			page.fillingTextSection();
			page.navigateToNextSection();
			page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
			page.navigateToNextSection();
			page.fillingDocumentSelectionPage(foldername);
			page.navigateToNextSection();
			page.fillingPrivGuardPage();
			page.fillingProductionLocationPage(productionname);
			page.navigateToNextSection();
			page.fillingSummaryAndPreview();

			// Wait until Generate button enables
			baseClass.waitForElement(page.getbtnProductionGenerate());
			page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		} catch (Exception e) {
			baseClass.stepInfo("Exception occured while Producing a PDF with Block Redaction burning" + e.getMessage());
			e.printStackTrace();
		}
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @throws Exception //@Testcase_Id : RPMXCON-52195 :Verify that Producing a PDF
	 *                   with text redaction burning properly and not eliminate any
	 *                   text characters in the produced document..
	 * @description : Verify that Producing a PDF with text redaction burning
	 *              properly and not eliminate any text characters in the produced
	 *              document.
	 */

	@Test(description = "RPMXCON-52195", groups = { "regression" })
	public void verifyThatProducingPDFWithTextRedaction() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-52195 Production-sprint:07");
		baseClass.stepInfo(
				"#### Verify that Producing a PDF with text redaction burning properly and not eliminate any text characters in the produced document. ####");
		String redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate To Redactions Page URL");
		redactionpage.navigateToRedactionsPageURL();

		baseClass.stepInfo("Manage Redaction Tags Page");
		redactionpage.manageRedactionTagsPage(redactiontag1);

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Add Docs Met Criteria To Action Board");
		search.addDocsMetCriteriaToActionBoard();

		redact = new DocViewRedactions(driver);

		baseClass.stepInfo("Redact Text Using Off set");
		redact.redactTextUsingOffset();

		baseClass.stepInfo("Selecting Redaction Tag");
		redact.selectingRedactionTag2(redactiontag1);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		baseClass.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Bulk Folder Existing");
		search.bulkFolderExisting(foldername);

		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);

		baseClass.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		baseClass.stepInfo("Navigate To Production Page");
		page.navigateToProductionPage();

		baseClass.stepInfo("Add new production");
		page.addANewProduction(productionname);

		baseClass.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		baseClass.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		baseClass.stepInfo("Filling PDF Section with Burn Redaction");
		page.fillingPDFSectionwithBurnRedaction(redactiontag1);

		baseClass.stepInfo("Filling text section");
		page.fillingTextSection();

		baseClass.stepInfo("Navigate to next section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		baseClass.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		baseClass.stepInfo("Filling Generate Page And Regenerating Again");
		page.clickOnGenerateButtonAndVerifyPreGenChecksStatus();
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @throws Exception //@Testcase_Id : RPMXCON-52198 : Verify that Producing a
	 *                   TIFF with text redaction burning properly and not eliminate
	 *                   any text characters in the produced document.
	 * @description : Verify that Producing a TIFF with text redaction burning
	 *              properly and not eliminate any text characters in the produced
	 *              document.
	 */

	@Test(description = "RPMXCON-52198", groups = { "regression" })
	public void verifyThatProducingTiffWithTextRedaction() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-52198 Production-sprint:07");
		baseClass.stepInfo(
				"#### Verify that Producing a TIFF with text redaction burning properly and not eliminate any text characters in the produced document. ####");
		String redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate To Redactions Page URL");
		redactionpage.navigateToRedactionsPageURL();

		baseClass.stepInfo("Manage Redaction Tags Page");
		redactionpage.manageRedactionTagsPage(redactiontag1);

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Add Docs Met Criteria To Action Board");
		search.addDocsMetCriteriaToActionBoard();

		redact = new DocViewRedactions(driver);

		baseClass.stepInfo("Redact Text Using Off set");
		redact.redactTextUsingOffset();

		baseClass.stepInfo("Selecting Redaction Tag");
		redact.selectingRedactionTag2(redactiontag1);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		baseClass.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Bulk Folder Existing");
		search.bulkFolderExisting(foldername);

		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);

		baseClass.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		baseClass.stepInfo("Navigate To Production Page");
		page.navigateToProductionPage();

		baseClass.stepInfo("Add new production");
		page.addANewProduction(productionname);

		baseClass.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		baseClass.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		baseClass.stepInfo("Filling PDF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction(redactiontag1);

		baseClass.stepInfo("Filling text section");
		page.fillingTextSection();

		baseClass.stepInfo("Navigate to next section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		baseClass.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		baseClass.stepInfo("Filling Generate Page And Regenerating Again");
		page.clickOnGenerateButtonAndVerifyPreGenChecksStatus();
		loginPage.logout();
	}

	/**
	 * @author Gopinath //@Testcase_Id : RPMXCON-51542 : Verify that same user with
	 *         two different tabs in the same browser, and confirm that able to
	 *         delete reviewer remark to the same records successfully
	 * @description : Verify that same user with two different tabs in the same
	 *              browser, and confirm that able to delete reviewer remark to the
	 *              same records successfully.
	 */

	@Test(description = "RPMXCON-51542", groups = { "regression" })
	public void verifyReamrkByDifferentTabsOnSameBrowser() throws Exception {
		baseClass = new BaseClass(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo("RPMXCON-51542 Production-sprint:07");
		baseClass.stepInfo(
				"#### Verify that same user with two different tabs in the same browser, and confirm that able to delete reviewer remark to the same records successfully. ####");
		String remark = Input.randomText + Utility.dynamicNameAppender();
		SessionSearch search = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Add Docs Met Criteria To Action Board");
//		search.addDocsMetCriteriaToActionBoard();
		search.ViewInDocView();

		String docId = docView.getDocumentWithoutRedaction();
		System.out.println(docId);

		docViewMetaDataPage = new DocViewMetaDataPage(driver);

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Navigate To Doc View Page URL");
		docView.navigateToDocViewPageURL();

		baseClass.waitTime(5);
		baseClass.stepInfo("Add Remark To Non Audio Document");
//		DocViewRedactions docViewred = new DocViewRedactions(driver);
		docView.selectDocInMiniDocList(docId);
		docView.addRemarkToNonAudioDocument(1, 10, remark);

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);

		baseClass.stepInfo("Clicking Redaction Icon");
		docViewRedact.clickingRedactionIcon();

		driver.waitForPageToBeReady();

		baseClass.stepInfo("perform This Page Redaction");
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);

		docView.selectDocInMiniDocList(docId);
		baseClass.stepInfo("Open Duplicate Tab Of Already Opened Tab");
		docViewMetaDataPage.openDuplicateTabOfAlreadyOpenedTab();

		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);

		baseClass.stepInfo("Switch To child Window");
		String parentWindow = reusableDocView.switchTochildWindow();

		baseClass.stepInfo("Add Remark To Non Audio Document");
		docView.selectDocInMiniDocList(docId);
		docView.addRemarkToNonAudioDocument(2, 10, remark);

		baseClass.stepInfo("Switch to parent window from child window");
//		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		docView.childWindowToParentWindowSwitching(parentWindow);

		baseClass.stepInfo("Click On Remark Button");
		docViewMetaDataPage.clickOnRemarkButton();

		baseClass.stepInfo("Verify Another user applied message displayed by adding remark in duplicate window");
		docViewMetaDataPage.verifyAnotherUserAppliedRemarkMsgDisplayed(Input.anotherRemarkMessage);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Verify Remark Is Added");
		docView.selectDocInMiniDocList(docId);
		docView.verifyRemarkIsAdded(remark);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Add Remark To Non Audio Document");
		baseClass.waitTime(4);
		docView.selectDocInMiniDocList(docId);
		docView.addRemarkToNonAudioDocument(5, 55, remark);

		baseClass.stepInfo("Open Duplicate Tab Of Already Opened Tab");
		docViewMetaDataPage.openDuplicateTabOfAlreadyOpenedTab();

		baseClass.stepInfo("Switch To child Window");
		parentWindow = reusableDocView.switchTochildWindow();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete remark");
		docView.selectDocInMiniDocList(docId);
		docView.deleteReamark(remark);

		baseClass.stepInfo("Switch to parent window from child window");
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);

		baseClass.stepInfo("Verify Another user applied message displayed by adding remark in duplicate window");
		docViewMetaDataPage.verifyAnotherUserAppliedRemarkMsgDisplayed(Input.anotherRemarkMessage);
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id : 51446 - To verify documents should be Folder when document
	 * outside of the reviewers batch is viewed from analytics panel. Description :
	 * To verify documents should be Folder when document outside of the reviewers
	 * batch is viewed from analytics panel.
	 */
	@Test(description = "RPMXCON-51446", alwaysRun = true, groups = { "regression" })
	public void verifyDocumentsShouldFolderbyFamilyMembersTab() throws Exception {
		baseClass = new BaseClass(driver);
		String assignmentName = Input.randomText + Utility.dynamicNameAppender();
		String folderName = Input.randomText + Utility.dynamicNameAppender();
		String docId = "ID00001130";
		baseClass.stepInfo("Test case Id: RPMXCON-51446");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### To verify documents should be Folder when document outside of the reviewers batch is viewed from analytics panel. ####");

		docView = new DocViewPage(driver);
		agnmt = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

//		Added on
		baseClass.selectproject(Input.additionalDataProject);

		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(docId);

		baseClass.stepInfo("Bulk assign with new assignment");
		sessionSearch.bulkAssignWithNewAssignment();

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Create assignment by bulk assign operationfrom Session search");
		agnmt.createAssignmentByBulkAssignOperation(assignmentName, Input.codeFormName);

		baseClass.stepInfo("Select assignment to view in Doc view");
		agnmt.selectAssignmentToViewinDocview(assignmentName, Input.additionalDataProject);

		baseClass.stepInfo("Select Doc From Family Members And Create Folder");
		docViewMetaDataPage.selectDocFromFamilyMembersAndCreateFolder(docId, folderName);
		loginPage.logout();

	}

	/**
	 * Author : Brundha Created date: NA Modified date: NA Modified by:NA TestCase
	 * id :RPMXCON- 51931 Description :Verify that when user is viewing a document
	 * in DocView, the entry for the same document in mini-DocList child window must
	 * always present fully in the visible area of the mini-DocList (to the user)
	 */
	@Test(description = "RPMXCON-51931", alwaysRun = true, groups = { "regression" })
	public void verifyTheDocIdWithMiniDoclistDocId() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-51931 docview-sprint:07");
		baseClass.stepInfo(
				"#### Verify that when user is viewing a document in DocView, the entry for the same document in mini-DocList child window  ####");
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("navigating to docview page");
		sessionSearch.ViewInDocView();

		DocViewPage doc = new DocViewPage(driver);

		baseClass.stepInfo("verifying the Document selected on minidoclist in main window");
		doc.selectMiniDocListDocAndComparingWithParentWindow();
		loginPage.logout();
	}

	/**
	 * Author : Brundha Created date: NA Modified date: NA Modified by:NA TestCase
	 * id :RPMXCON- 51932 Description: Verify that when performing doc-to-doc
	 * navigation the entry for the same document in mini-DocList child window must
	 * always present fully in the visible area
	 */
	@Test(description = "RPMXCON-51932", alwaysRun = true, groups = { "regression" })
	public void afterNavigationMiniDocListDocumentDefaultDisplay() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-51932 docview-sprint:07");
		baseClass.stepInfo(
				"#### Verify that when performing doc-to-doc navigation the entry for the same document in mini-DocList child window  ####");

		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("navigating to docview page");
		sessionSearch.ViewInDocView();
		DocViewPage doc = new DocViewPage(driver);

		baseClass.stepInfo("Verification of navigated DocID with parent window DocID");
		doc.switchFromChildWindowToParentWindow();
		loginPage.logout();
	}

	/**
	 * Author : Brundha Created date: NA Modified date: NA Modified by:NA TestCase
	 * id :RPMXCON- 51308 Description:Verify persistent Hit panel of DocView should
	 * present only content terms, not operators when navigating from basic search
	 * 
	 */
	@Test(description = "RPMXCON-51308", alwaysRun = true, groups = { "regression" })
	public void verifyPersistentHitPanelOfDocView() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-51308 docview-sprint:07");
		baseClass.stepInfo(
				"#### Verify persistent Hit panel of DocView should present only content terms, not operators when navigating from basic search  ####");

		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		DocViewPage doc = new DocViewPage(driver);

		baseClass.stepInfo("verify persistent Hit panel of docview contains basic search string");
		doc.getPersistentHit(Input.testData1);
		doc.SearchStringVerification();
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id :RPMXCON- 51304 : Verify persistent Hit panel of DocView should present
	 * only content terms, not metadata terms when navigating from basic search.
	 * Description: Verify persistent Hit panel of DocView should present only
	 * content terms, not metadata terms when navigating from basic search.
	 * 
	 */
	@Test(description = "RPMXCON-51304", alwaysRun = true, groups = { "regression" })
	public void verifyPersistentHitPanelOfDocViewByMetaData() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-51304 docview-sprint:08");
		baseClass.stepInfo(
				"####Verify persistent Hit panel of DocView should present only content terms, not metadata terms when navigating from basic search ####");

		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicMetaDataSearch(Input.metaDataName, null, Input.metaDataCN, null);
		sessionSearch.addDocsMetCriteriaToActionBoard();

		DocViewPage doc = new DocViewPage(driver);

		baseClass.stepInfo("verify persistent Hit panel of docview contains basic search string");
		doc.verifyPersistentPanelNotContainsTerm(Input.metaDataCN);
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id :RPMXCON- 51307 : Verify persistent Hit panel of DocView should present
	 * only content terms, not Comment/Remark when navigating from basic search.
	 * Description: Verify persistent Hit panel of DocView should present only
	 * content terms, not Comment/Remark when navigating from basic search.
	 * 
	 */
	@Test(description = "RPMXCON-51307", alwaysRun = true, groups = { "regression" })
	public void verifyPersistentHitPanelOfDocViewByRemark() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewPage docView = new DocViewPage(driver);
		baseClass.stepInfo("RPMXCON-51307 docview-sprint:08");
		baseClass.stepInfo(
				"#### Verify persistent Hit panel of DocView should present only content terms, not Comment/Remark when navigating from basic search ####");
		String remark = Input.randomText + Utility.dynamicNameAppender();
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();
		String docId = docView.getDocumentWithoutRedaction();

		baseClass.stepInfo("Add Remark To Non Audio Document");
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId);
		baseClass.stepInfo("Add Remark To Non Audio Docment");
		docView.addRemarkToNonAudioDocument(1, 20, remark);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocViewWithoutPureHit();

		docView.selectDocInMiniDocList(docId);
		baseClass.stepInfo("verify persistent Hit panel of docview contains remark");
		docView.verifyPersistentPanelNotContainsTerm(remark);

		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id :RPMXCON- 51906 : Verify that when user in on Images tab and navigates to
	 * next document then should be on Images tab for next document. Description:
	 * Verify that when user in on Images tab and navigates to next document then
	 * should be on Images tab for next document.
	 * 
	 */
	@Test(description = "RPMXCON-51906", alwaysRun = true, groups = { "regression" })
	public void verifyImageTabEnabledWhenNavigatesToNextDocument() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-51906 docview-sprint:08");
		baseClass.stepInfo(
				"#### Verify that when user in on Images tab and navigates to next document then should be on Images tab for next document ####");
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		DocViewPage docView = new DocViewPage(driver);

		baseClass.stepInfo("Navigate To Doc View Page URL");
		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Click On Image Tab");
		docView.clickOnImageTab();

		baseClass.stepInfo("Verify Image Tab Is Enabled");
		docView.verifyImageTabIsEnabled();

		baseClass.stepInfo("Verify user navigated to next document.");
		docView.verifyUserNavigatedToNextDocument();

		baseClass.stepInfo("Verify Image Tab Is Enabled");
		docView.verifyImageTabIsEnabled();
		loginPage.logout();
	}

	/**
	 * @Author : Gopinath
	 * @testcase_id :RPMXCON-51930 : Verify that when completing the documents on
	 *              applying the stamp the entry for the navigated document in
	 *              mini-DocList.
	 * @Description : Verify that when completing the documents on applying the
	 *              stamp the entry for the navigated document in mini-DocList.
	 */

	@Test(description = "RPMXCON-51930", enabled = true, groups = { "regression" })
	public void verifyingCodingStampPostFixColourChildWindowPopUp() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51930");
		String AssignStamp = Input.randomText + Utility.dynamicNameAppender();
		String comment = Input.randomText + Utility.dynamicNameAppender();
		String fieldText = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo(
				"#### Verify that when completing the documents on applying the stamp the entry for the navigated document in mini-DocList. ####");
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		// searching document for assignmnet creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);

		docView = new DocViewPage(driver);

		docView.stampCompleteNavigateNextDocumumentVerification(comment, fieldText, Input.stampSelection);
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCase id : 51907 - Verify that when user in on Images tab
	 *         and folder few documents then on loading of document should be on
	 *         Images tab of document Description : Verify that when user in on
	 *         Images tab and folder few documents then on loading of document
	 *         should be on Images tab of document
	 */
	@Test(description = "RPMXCON-51907", enabled = true, groups = { "regression" })
	public void verifyUserOnImagesTabAfterCreatingFolder() throws InterruptedException {
		String folderName = Input.randomText + Utility.dynamicNameAppender();
		int RowNumber = 1;
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51907");
		baseClass.stepInfo(
				"#### Verify that when user in on Images tab and folder few documents then on loading of document should be on Images tab of document ####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic Basic content search");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Navigate to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("Document selected");
		docView.selectRowFromMiniDocList(RowNumber);

		baseClass.stepInfo("Verify image tab is enabled if folder is added.");
		docView.verifyImageEnabledAfterCreatedFolder(folderName, RowNumber);
		loginPage.logout();
	}

	/**
	 * @Author : Gopinath
	 * @Testcase_Id : RPMXCON-51935 : Verify that when completing the documents on
	 *              applying the stamp the entry for the navigated document in
	 *              mini-DocList child window.
	 * @Description : Verify that when completing the documents on applying the
	 *              stamp the entry for the navigated document in mini-DocList child
	 *              window .
	 */

	@Test(description = "RPMXCON-51935", enabled = true, groups = { "regression" })
	public void verifyCursorNavigatedProperlyBySavedCodingStampApplied() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51935 Sprint 09");
		baseClass.stepInfo(
				"#### Verify that when completing the documents on applying the stamp the entry for the navigated document in mini-DocList child window ####");
		String AssignStamp = Input.randomText + Utility.dynamicNameAppender();
		String assgnColour = Input.randomText + Utility.dynamicNameAppender();
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		MiniDocListPage miniDoc = new MiniDocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		// searching document for assignmnet creation

		baseClass.stepInfo("Basic Content Search");

//		Added
		sessionSearch.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Bulk Assign");
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Assignment Creation");
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);

		baseClass.stepInfo("Toggle Coding Stamp Enabled");
		assignmentPage.toggleCodingStampEnabled();

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Selecting the assignment");
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Navigate To Doc View Page URL");
		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Coding Stamp For Saved Document");
		driver.waitForPageToBeReady();
		docView.codingStampForSavedDocument(assgnColour, Input.stampSelection);

		baseClass.stepInfo("Verify cursor navigated to child window clicking on saved stamp.");
		miniDoc.verifyCursorNavigatedToChildWindowClickingOnSavedStamp(assgnColour, Input.stampSelection);
		loginPage.logout();

	}

	/**
	 * @Author : Gopinath
	 * @Testcase_Id : RPMXCON-51928 : erify that when completing the documents the
	 *              entry for the navigated document in mini-DocList.
	 * @Description : Verify that when completing the documents the entry for the
	 *              navigated document in mini-DocList.
	 */

	@Test(description = "RPMXCON-51928", enabled = true, groups = { "regression" })
	public void verifyCursorNavigatedProperlyByComplete() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51928 Sprint 09");
		baseClass.stepInfo(
				"#### erify that when completing the documents the entry for the navigated document in mini-DocList ####");
		String AssignStamp = Input.randomText + Utility.dynamicNameAppender();
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		// searching document for assignmnet creation

		baseClass.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Bulk Assign");
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Assignment Creation");
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);

		baseClass.stepInfo("Toggle Coding Stamp Enabled");
		assignmentPage.toggleCodingStampEnabled();

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Selecting the assignment");
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Navigate To Doc View Page URL");
		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Complete coding form and verify cursor navigated to next document.");
		docView.completeCodingFormAndVerifyCursorNavigateToNextDoc();
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCase ID : 50912 - Verify when user navigates to the
	 *         document from Text tab Description :To verify when user navigates to
	 *         the document from Text tab
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-50912", enabled = true, groups = { "regression" })
	public void verifyUserNavigateToDocumentFromTextTab() throws InterruptedException {

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50912 spint-09");
		baseClass.stepInfo("#### verify when user navigates to the document from Text tab ####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic Basic content search completed");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Navigated to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("Verifying document displayed in text view.");
		docView.verifyDocumentDisplayedInTextView();

		baseClass.stepInfo("Verifying document loaded in default view.");
		docView.verifyDocumentLoadedInDefaultView();
		loginPage.logout();

	}

	/**
	 * @author Gopinath TestCase Id: 51933 Verify that when completing the documents
	 *         the entry for the navigated document in mini-DocList child window.
	 *         Description: Verify that when completing the documents the entry for
	 *         the navigated document in mini-DocList child window.
	 */
	@Test(description = "RPMXCON-51933", enabled = true, groups = { "regression" })
	public void verifyMiniDocNavigateTonExtDocAfterDOcComplete() {
		baseClass.stepInfo("Test case Id: RPMXCON-51933 Sprint-09");
		String AssignStamp = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo(
				"#### Verify that when completing the documents the entry for the navigated document in mini-DocList child window ####");
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		// searching document for assignmnet creation

		baseClass.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Bulk Assign");
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Assignment Creation");
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Select Assignment By Reviewer");
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);

		docView = new DocViewPage(driver);

		baseClass.stepInfo(
				"Verify weather mini docList Document is navigate to next doc in mini doc list child window after doc complete.");
		docView.verifyNavegatingofDocInMiniDocLIstAfterComplete();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		loginPage.logout();
	}

	/**
	 * Author : Brundha Created date: NA Modified date: NA Modified by:NA TestCase
	 * id :RPMXCON- 51433 Description: Verify that if the document native is being
	 * presented, the "N" icon with the accompanying mouse over tool tip must appear
	 * 
	 */
	@Test(description = "RPMXCON-51433", alwaysRun = true, groups = { "regression" })
	public void verifyNIconAndToolTipInDocView() throws Exception {

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-51433 docview-sprint:9");
		baseClass.stepInfo(
				"#### Verify that if the document native is being presented, the 'N' icon with the accompanying mouse over tool tip must appear ####");

		String DocId = "STC4_00000995";
		String ExpectedText = "Native file variant of the document being displayed";
		baseClass.stepInfo("Basic meta data search");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicSearchWithMetaDataQueryUsingSourceDOCID(DocId);
		// sessionSearch.basicContentSearch(DocId);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		DocViewPage docView = new DocViewPage(driver);
		// docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Verify Native Document Tooltip");
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		docView.verifyingToolTipPopupMessage(DocId, ExpectedText);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicSearchWithMetaDataQueryUsingSourceDOCID(DocId);
		// sessionSearch.basicContentSearch(DocId);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		docView = new DocViewPage(driver);
		// docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Verify Native Document Tooltip");
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		docView.verifyingToolTipPopupMessage(DocId, ExpectedText);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicSearchWithMetaDataQueryUsingSourceDOCID(DocId);
		// sessionSearch.basicContentSearch(DocId);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Verify Native Document Tooltip");
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		docView.verifyingToolTipPopupMessage(DocId, ExpectedText);
		loginPage.logout();

	}
//	37

	/**
	 * Author : Brundha Created date: NA Modified date: NA Modified by:NA TestCase
	 * id :RPMXCON- 51434 Description: Verify that if the document PDF is being
	 * presented, the "P" icon with the accompanying mouse over tool tip must appear
	 * 
	 */
	@Test(description = "RPMXCON-51434", alwaysRun = true, groups = { "regression" })

	public void verifyPIconAndToolTipInDocView() throws Exception {

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-51434 docview-sprint:9");
		baseClass.stepInfo(
				"#### Verify that if the document PDF is being presented, the 'P' icon with the accompanying mouse over tool tip must appear ####");
		SessionSearch sessionSearch = new SessionSearch(driver);

		String ExpectedText = "PDF file variant of the document being displayed";
		String DocId = "STC4_00000964";
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicSearchWithMetaDataQueryUsingSourceDOCID(DocId);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		DocViewPage docView = new DocViewPage(driver);

		// docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Verify PDF Document Tooltip");
		docView.verifyingToolTipPopupMessage(DocId, ExpectedText);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicSearchWithMetaDataQueryUsingSourceDOCID(DocId);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		docView = new DocViewPage(driver);
		// docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Verify PDF Document Tooltip");
		docView.verifyingToolTipPopupMessage(DocId, ExpectedText);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicSearchWithMetaDataQueryUsingSourceDOCID(DocId);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		docView = new DocViewPage(driver);
		// docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Verify PDF Document Tooltip");
		docView.verifyingToolTipPopupMessage(DocId, ExpectedText);
		loginPage.logout();

	}

	/**
	 * @Author : Gopinath
	 * @Testcase_Id : RPMXCON-51850 : Verify that persistent hits panel should not
	 *              retain previously viewed hits for the document on completing the
	 *              document same as last.
	 * @Description : Verify that persistent hits panel should not retain previously
	 *              viewed hits for the document on completing the document same as
	 *              last.
	 */

	@Test(description = "RPMXCON-51850", enabled = true, groups = { "regression" })
	public void verifyPersistentPanelNotRetainPreviouslyViewedHitsByComplete() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51850 Sprint 09");
		baseClass.stepInfo(
				"#### Verify that persistent hits panel should not retain previously viewed hits for the document on completing the document same as last ####");
		String AssignStamp = Input.randomText + Utility.dynamicNameAppender();
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		// searching document for assignmnet creation

		baseClass.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Bulk Assign");
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Assignment Creation");
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);

		baseClass.stepInfo("Toggle Coding Stamp Enabled");
		assignmentPage.toggleCodingStampEnabled();

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Selecting the assignment");
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Navigate To Doc View Page URL");
		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Click on persistant hit eye icon.");
		docView.clickOnPersistantHitEyeIcon();

		baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
		docView.verifyPersistentHitsPanelDisplayed();

		baseClass.stepInfo("Complete coding form and verify cursor navigated to next document.");
		docView.completeCodingFormAndVerifyCursorNavigateToNextDoc();

		baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
		docView.verifyPersistentHitsPanelDisplayed();
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id : 51728 - Verify that persistent hits should be highligted when
	 * documents are assigned to existing assignment from Basic Search. Description
	 * : Verify that persistent hits should be highligted when documents are
	 * assigned to existing assignment from Basic Search.
	 */
	@Test(description = "RPMXCON-51728", alwaysRun = true, groups = { "regression" })
	public void verifyPersistentHitsDisplayedByContextOfAssignment() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51728- DocView Sprint 09 ");
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		String assignStamp = Input.randomText + Utility.dynamicNameAppender();
		baseClass.stepInfo(
				"#### Verify that persistent hits should be highligted when documents are assigned to existing assignment from Basic Search ####");

		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		manageAssignment = new ManageAssignment(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Create Assignment");
		assgnPage.createAssignment(assignStamp, Input.codeFormName);

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Selected Doclist");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Select The Document in Doclistpage");
		docPage.documentSelection(3);

		baseClass.stepInfo("Bulk Assign the selected Document");
		docPage.bulkAssignWithPersistantHit(assignStamp);

		baseClass.stepInfo("Created a assignment " + assignStamp);

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Edit Assignment");
		assgnPage.editAssignment(assignStamp);

		baseClass.stepInfo("Toggle Coding Stamp Enabled");
		assgnPage.toggleCodingStampEnabled();

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assgnPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Selecting the assignment");
		assgnPage.SelectAssignmentByReviewer(assignStamp);

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Click on persistant hit eye icon.");
		docView.clickOnPersistantHitEyeIcon();

		baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
		docView.verifyPersistentHitsPanelDisplayed();

		baseClass.stepInfo("Verifying persistent Hits Displayed.");
		docView.verifyPersistentHitsDisplayed(Input.testData1);
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @throws InterruptedException
	 * @TestCase Id: 51926 Verify that when user is viewing a document in DocView,
	 *           the entry for the same document in mini-DocList.
	 * @Description: To Verify that when user is viewing a document in DocView, the
	 *               entry for the same document in mini-DocList.
	 */
	@Test(description = "RPMXCON-51926", alwaysRun = true, groups = { "regression" })
	public void verifyDocFullyInVisibleAreaOfMiniDocList() throws InterruptedException {
		int rowNumber = 2;
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51926");
		baseClass.stepInfo(
				"#### Verify that when user is viewing a document in DocView, the entry for the same document in mini-DocList must always present fully in the visible area of the mini-DocList (to the user) ####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic Basic content search");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Navigate to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("Select Document From Mini Doc LIst");
		docView.selectDocumentFromMiniDocList(rowNumber);
		loginPage.logout();
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * @Testcase_id : 51727 - Verify that persistent hits should be highligted when
	 *              documents are assigned to new assignment from Saved Search.
	 * @Description : Verify that persistent hits should be highligted when
	 *              documents are assigned to new assignment from Saved Search.
	 */
	@Test(description = "RPMXCON-51727", groups = { "regression" })
	public void verifyPersistantHitByReviewerByCreatingNewAssignment() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51727 spint 09");

		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		String searchname1 = Input.randomText + Utility.dynamicNameAppender();
		final String assignStamp = Input.randomText + Utility.dynamicNameAppender();
		final String searchname = searchname1;

		baseClass.stepInfo(
				"####  Verify that persistent hits should be highligted when documents are assigned to new assignment from Saved Search. ####");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.randomText);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		SavedSearch savedSearch = new SavedSearch(driver);

		baseClass.stepInfo("Open Doc list from Saved search page");
		savedSearch.savedSearchToDocList(searchname);

		DocListPage docList = new DocListPage(driver);

		baseClass.stepInfo("Selecting all documents in current page");
		docList.selectAllDocumentsInCurrentPageOnly();

		baseClass.stepInfo("Assignment Creation");
		assgnPage.assignmentCreationWithPersistantHitDocList(assignStamp, Input.codingFormName);

		baseClass.stepInfo("Toggle Coding Stamp Enabled");
		assgnPage.toggleCodingStampEnabled();

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assgnPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Selecting the assignment");
		assgnPage.SelectAssignmentByReviewer(assignStamp);

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Click on persistant hit eye icon.");
		docView.clickOnPersistantHitEyeIcon();

		baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
		docView.verifyPersistentHitsPanelDisplayed();

		baseClass.stepInfo("Verifying persistent Hits Displayed.");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		docView.persistenHitWithSearchString(Input.randomText);
		loginPage.logout();
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * @Testcase_id : 51726 - Verify that persistent hits should be highligted when
	 *              documents are assigned to new assignment from Advanced Search.
	 * @Description : Verify that persistent hits should be highligted when
	 *              documents are assigned to new assignment from Advanced Search
	 */
	@Test(description = "RPMXCON-51726", groups = { "regression" })
	public void verifyPersistantHitPanelByReviewerByAdvanceSearch() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51726 spint 09");

		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		final String assignStamp = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo(
				"####  Verify that persistent hits should be highligted when documents are assigned to new assignment from Advanced Search ####");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.stepInfo("Advance metadata Search");
		search.metaDataSearchInAdvancedSearch(Input.metaDataName, Input.metaDataCN);

		baseClass.stepInfo("Selected Doclist");
		search.ViewInDocList();

		DocListPage docList = new DocListPage(driver);

		baseClass.stepInfo("Selecting all documents in current page");
		docList.selectAllDocumentsInCurrentPageOnly();

		baseClass.stepInfo("Assignment Creation");
		assgnPage.assignmentCreationWithPersistantHitDocList(assignStamp, Input.codingFormName);

		baseClass.stepInfo("Toggle Coding Stamp Enabled");
		assgnPage.toggleCodingStampEnabled();

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assgnPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Selecting the assignment");
		assgnPage.SelectAssignmentByReviewer(assignStamp);

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Click on persistant hit eye icon.");
		docView.clickOnPersistantHitEyeIcon();

		baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
		docView.verifyPersistentHitsPanelDisplayed();
		loginPage.logout();
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * @Testcase_id : 51725 - Verify that persistent hits should be highligted when
	 *              documents are assigned to new assignment from Basic Search.
	 * @Description : Verify that persistent hits should be highligted when
	 *              documents are assigned to new assignment from Basic Search.
	 */
	@Test(description = "RPMXCON-51725", groups = { "regression" })
	public void verifyPersistantHitPanelByReviewerByBasicSearch() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51725 spint 09");

		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		final String assignStamp = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo(
				"#### Verify that persistent hits should be highligted when documents are assigned to new assignment from Basic Search. ####");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.randomText);

		baseClass.stepInfo("Selected Doclist");
		search.ViewInDocList();

		DocListPage docList = new DocListPage(driver);

		baseClass.stepInfo("Selecting all documents in current page");
		docList.selectAllDocumentsInCurrentPageOnly();

		baseClass.stepInfo("Assignment Creation");
		assgnPage.assignmentCreationWithPersistantHitDocList(assignStamp, Input.codingFormName);

		baseClass.stepInfo("Toggle Coding Stamp Enabled");
		assgnPage.toggleCodingStampEnabled();

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assgnPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Selecting the assignment");
		assgnPage.SelectAssignmentByReviewer(assignStamp);

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Click on persistant hit eye icon.");
		docView.clickOnPersistantHitEyeIcon();

		baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
		docView.verifyPersistentHitsPanelDisplayed();

		baseClass.stepInfo("Verifying persistent Hits Displayed.");
		docView.clickOnPersistantHitEyeIcon();
		docView.persistenHitWithSearchString(Input.randomText);
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCase Id: 51757 Verify that all relevant hits should be
	 *         displayed on persistent hits panel when navigated to doc view.
	 *         Description:To Verify that all relevant hits should be displayed on
	 *         persistent hits panel when navigated to doc view.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51757", groups = { "regression" })
	public void verifySearchTermDisplayOnPersistentHitPanal() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51757");
		String keywordname = Input.randomText + Utility.dynamicNameAppender();
		String keyword = Input.randomText + Utility.dynamicNameAppender();
		baseClass.stepInfo(
				"#### Verify that all relevant hits should be displayed on persistent hits panel when navigated to doc view with ad hoc search and keywords highlighting ####");

		KeywordPage keywordPage = new KeywordPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Add keyword");
		keywordPage.AddKeyword(keywordname, keyword);

		baseClass.stepInfo("Get All Keywords in keywords lsit table");
		List<String> keywords = keywordPage.getAllKeywords();

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		baseClass.stepInfo("Basic Basic content search");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Navigate to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(Input.searchString1);

		baseClass.stepInfo("Verify Persistant Hits With Doc View");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.verifyPersistantHitsWithDocView(keywords);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keyword);
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @TestCase Id : 51730 Verify that persistent hits should be highligted when
	 *           documents are assigned to existing assignment from Saved Search >
	 *           Doc List
	 * @Description : To Verify that persistent hits should be highligted when
	 *              documents are assigned to existing assignment from Saved Search
	 *              > Doc List
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51730", groups = { "regression" })
	public void verifyPersistentHItAfterAssignedToExistingAssignment() throws InterruptedException {
		String BasicSearchName = Input.randomText + Utility.dynamicNameAppender();
		String AssignName = Input.randomText + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51926");
		baseClass.stepInfo(
				"#### Verify that when user is viewing a document in DocView, the entry for the same document in mini-DocList must always present fully in the visible area of the mini-DocList (to the user) ####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		SavedSearch savesearch = new SavedSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		DocListPage doclistpage = new DocListPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		assignmentPage.createAssignment(AssignName, Input.codingFormName);
		baseClass.stepInfo("New Assignment created");

		session.basicContentSearch(Input.searchString2);
		baseClass.stepInfo("Basic Basic content search completed");

		session.saveSearch(BasicSearchName);
		baseClass.stepInfo("Basic search saved succssfully");

		savesearch.savedSearchToDocList(BasicSearchName);
		baseClass.stepInfo("Navigated to DocList Page");

		doclistpage.DoclisttobulkAssign(Input.randomText, "10");
		baseClass.stepInfo("All Doc list selected and navigated bulk assign");

		assignmentPage.assignDocstoExisting(AssignName);
		baseClass.stepInfo("Docs are assign to existing assignment");

		assignmentPage.editAssignment(AssignName);
		baseClass.stepInfo("Assignment selected and navigated to edit assignment page");

		assignmentPage.assignmentDistributingToReviewer();
		baseClass.stepInfo("Reviews added and distributed to Reviewer");

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		driver.waitForPageToBeReady();
		assignmentPage.SelectAssignmentByReviewer(AssignName);

		docView.persistenHitWithSearchString(Input.searchString2);

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * @Testcase_id : 51117 -Verify user can download the redacted document when
	 *              'Allow reviewers to print docs to PDF' is on at an assigment
	 *              level.
	 * @Description : Verify user can download the redacted document when 'Allow
	 *              reviewers to print docs to PDF' is on at an assigment level.
	 */
	@Test(description = "RPMXCON-51117", groups = { "regression" })
	public void verifyDownloadDocumentByAllowingToPrintDocumentsAsPDF() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51117 spint 09");
		ManageAssignment mngAssign = new ManageAssignment(driver);
		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		DocViewMetaDataPage metaData = new DocViewMetaDataPage(driver);
		SessionSearch search = new SessionSearch(driver);
		final String assignStamp = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo(
				"#### Verify user can download the redacted document when 'Allow reviewers to print docs to PDF' is on at an assigment level. ####");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Bulk Assign");
		search.bulkAssign();

		baseClass.stepInfo("Assignment Creation");
		assgnPage.assignmentCreation(assignStamp, Input.codingFormName);

		baseClass.stepInfo("Toggle Coding Stamp Enabled");
		assgnPage.toggleCodingStampEnabled();

		baseClass.stepInfo("Disable print button toogle");
		mngAssign.disablePrintButton(false);

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assgnPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Selecting the assignment");
		assgnPage.SelectAssignmentByReviewer(assignStamp);

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Click on persistant hit eye icon.");
		metaData.verifyDocumenetPrintOnDocView();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login As Review Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assgnPage.deleteAssignment(assignStamp);
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id : 51116 - Verify user can not download the native files when
	 * 'Allow reviewers to download natives' is off at an assigment level.
	 * Description : Verify user can not download the native files when 'Allow
	 * reviewers to download natives' is off at an assigment level
	 */
	@Test(description = "RPMXCON-51116", alwaysRun = true, groups = { "regression" })
	public void verifyNativeDownloadButtonDiabledWhenToogleOFF() throws Exception {
		baseClass = new BaseClass(driver);
		String assignmentName = Input.randomText + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-51116 sprint 09");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify user can not download the native files when 'Allow reviewers to download natives' is off at an assigment level ####");

		ManageAssignment mngAssign = new ManageAssignment(driver);
		docView = new DocViewPage(driver);
		agnmt = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Bulk Assign");
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Assignment Creation");
		agnmt.assignmentCreation(assignmentName, Input.codingFormName);

		baseClass.stepInfo("Toggle Coding Stamp Enabled");
		agnmt.toggleCodingStampEnabled();

		baseClass.stepInfo("Disable native download toogle");
		driver.waitForPageToBeReady();
		mngAssign.disableNativeDownloadButton(true);

		baseClass.stepInfo("Select assignment to view in Doc view");
		agnmt.selectAssignmentToViewinDocview(assignmentName);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify download button is not displayed.");
		docViewMetaDataPage.verifyingDownloadButtonIsNotDisplayed();

		baseClass.stepInfo("Navigate To Assignments Page");
		agnmt.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();

	}

	/**
	 * @author Gopinath
	 * @TestCase Id:51309 Verify persistent Hit panel of DocView should present only
	 *           content terms, not operators when navigating from advance search
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51309", alwaysRun = true, groups = { "regression" })
	public void verifysearchTermsDisplayOnPHPanelWithoutOperator() throws InterruptedException {
		String operator = "OR";
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51309");
		baseClass.stepInfo(
				"#### Verify persistent Hit panel of DocView should present only content terms, not operators when navigating from advance searchVerify persistent Hit panel of DocView should present only content terms, not operators when navigating from advance search ####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo(" Advanced search with operator");
		session.advancedContentSearchWithOperator(Input.searchString1, operator, Input.testData1);

		baseClass.stepInfo("Navigating to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("verifying search terms displayed on persistaent hit panel without operator or not");
		docView.persistenHitWithSearchStringWithOutOperator(Input.searchString1, Input.testData1, operator);
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCase Id:51120 Verify user can see the tiff images when
	 *         'Allow Production / Images view'. Description :To Verify user can see
	 *         the tiff images when 'Allow Production / Images View' is on at an
	 *         assigment level
	 */
	@Test(description = "RPMXCON-51120", alwaysRun = true, groups = { "regression" })
	public void verifyImageTabIsDisplayedAOnDocViewPanel() {

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51120");
		String AssignStamp = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo(
				"#### Verify user can see the tiff images when 'Allow Production / Images View' is on at an assigment level####");

		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("searching document for assignmnet creation");
		sessionSearch.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("performing bulkAssign");
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Creating assignment");
		assignmentPage.createAssignmentWithNativeDownload(AssignStamp, Input.codingFormName);

		baseClass.stepInfo("Select document to view in doc view");
		assignmentPage.selectAssignmentToViewinDocview(AssignStamp);

		baseClass.stepInfo("Verify image tab in doc view.");
		docView.verifyImageTabIsNotDisplayed();
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCase Id:51091-Verify on selection of the version Project
	 *         Admin/RMU/Reviewer will see the translated document. Description :
	 *         Verify on selection of the version Project Admin/RMU/Reviewer will
	 *         see the translated document.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51091", alwaysRun = true, groups = { "regression" })
	public void verifyTranslationTabOnDocView() throws InterruptedException {

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51091 of Sprint 09");

		baseClass.stepInfo(
				"#### Verify on selection of the version Project Admin/RMU/Reviewer will see the translated document ####");

		DocViewPage docView = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("searching document for assignmnet creation");
		sessionSearch.basicContentSearch(Input.translationDocId);

		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Click On Translation Tab");
		docView.clickOnTranslationTab();

		baseClass.stepInfo("Click On Translation From Translations Dropdown");
		docView.clickOnTranslationFromTranslationsDropdown();
		loginPage.logout();
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * @Testcase_id : 51033 -Verify user can see the keywords highlighted in doc
	 *              view based on the assigned keyword group and color to the
	 *              assignment.
	 * @Description : Verify user can see the keywords highlighted in doc view based
	 *              on the assigned keyword group and color to the assignment.
	 */
	@Test(description = "RPMXCON-51033", groups = { "regression" })
	public void verifyKeywordHighlightedOnDocview() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51033 spint 09");
		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		final String assignStamp = Input.randomText + Utility.dynamicNameAppender();
		String keywordname = Input.randomText + Utility.dynamicNameAppender();
		String keyword = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo(
				"#### Verify user can see the keywords highlighted in doc view based on the assigned keyword group and color to the assignment ####");

		KeywordPage keywordPage = new KeywordPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Add keyword");
		keywordPage.AddKeyword(keywordname, keyword + "," + Input.randomText);

		baseClass.stepInfo("Get All Keywords in keywords list table");
		keywordPage.getAllKeywords();

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Bulk Assign");
		search.bulkAssign();

		baseClass.stepInfo("Assignment Creation");
		assgnPage.assignmentCreation(assignStamp, Input.codingFormName);

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assgnPage.addReviewerAndDistributeDocs();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Selecting the assignment");
		assgnPage.SelectAssignmentByReviewer(assignStamp);

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(keyword);

		baseClass.stepInfo("Verify keyword highlighted on doc view.");
		docView.verifyHighlightedKeywordInDocView();

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

//		Added
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assgnPage.deleteAssgnmntUsingPagination(assignStamp);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

//		Added
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keyword);
		loginPage.logout();

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * @Testcase_id : 51023 - To verify that remarks can add/edit/delete if document
	 *              is marked as Completed.
	 * @Description : To verify that remarks can add/edit/delete if document is
	 *              marked as Completed.
	 */
	@Test(description = "RPMXCON-51023", groups = { "regression" })
	public void verifyRemarkOperationsOfCompletedDocumentOnDocview() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51023 spint 09");
		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		 DocViewRedactions docRedact = new DocViewRedactions(driver);
		SessionSearch search = new SessionSearch(driver);
		final String assignStamp = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo("#### To verify that remarks can add/edit/delete if document is marked as Completed. ####");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Bulk Assign");
		search.bulkAssign();

		baseClass.stepInfo("Assignment Creation");
		assgnPage.assignmentCreation(assignStamp, Input.codingFormName);

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assgnPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Selecting the assignment");
		assgnPage.SelectAssignmentByReviewer(assignStamp);

		docView = new DocViewPage(driver);
		docView.getDocViewDocsRedactIcon();
		String docId = docView.getDocumentWithoutRedaction();
		System.out.println(docId);

		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId);

		baseClass.stepInfo("Complete non audio document");
		docView.completeDocument(Input.randomText);

		baseClass.stepInfo("Adding remark to document");
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		docView.getDocViewDocsRedactIcon();
		baseClass.stepInfo("Add Remark To Non Audio Document");
		docView.addRemarkToNonAudioDocument(1, 22, remark);
		baseClass.waitTime(5);
		String docId1 = docRedact.activeDocId().getText();
		System.out.println(docId1);

		baseClass.stepInfo("Verify Remark Is Added");
		docView.getDocViewDocsRedactIcon();
		docView.verifyRemarkIsAdded(remark);
		
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Delete remark");
		docView.getDocView_DocId(docId1).waitAndClick(10);
		driver.waitForPageToBeReady();
		docView.deleteReamark(remark);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assgnPage.deleteAssignment(assignStamp);
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id : 51022 - To verify that if Reviewer Remark is off at Assignment
	 * level then it should not displayed if user naivgates from Assignment-Doc
	 * View.. Description : To verify that if Reviewer Remark is off at Assignment
	 * level then it should not displayed if user naivgates from Assignment-Doc
	 * View.
	 */
	@Test(description = "RPMXCON-51022", alwaysRun = true, groups = { "regression" })
	public void verifyReviewerRemarkOffAtAssignmentLevel() throws Exception {
		baseClass = new BaseClass(driver);
		String assignmentName = Input.randomText + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-51022 sprint 09");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### To verify that if Reviewer Remark is off at Assignment level then it should not displayed if user naivgates from Assignment-Doc View. ####");

		ManageAssignment mngAssign = new ManageAssignment(driver);
		docView = new DocViewPage(driver);
		agnmt = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchText);

		baseClass.stepInfo("Bulk Assign");
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Assignment Creation");
		agnmt.assignmentCreation(assignmentName, Input.codingFormName);

		baseClass.stepInfo("Toggle Coding Stamp Enabled");
		agnmt.toggleCodingStampEnabled();

		baseClass.stepInfo("Disable native download toogle");
		mngAssign.disableReviewerRemarks(true);

		baseClass.stepInfo("Select assignment to view in Doc view");
		agnmt.selectAssignmentToViewinDocview(assignmentName);

		baseClass.stepInfo("Verify download button is not displayed.");
		docViewMetaDataPage.verifyingRemarkButtonIsNotDisplayed();

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchText);

		baseClass.stepInfo("View in Doc view");
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Verify Remark Button Is Displayed");
		docViewMetaDataPage.verifyingRemarkButtonIsDisplayed();

		baseClass.stepInfo("Navigate To Assignments Page");
		agnmt.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");

//		 Modified
		driver.waitForPageToBeReady();
//		agnmt.deleteAssignment(assignmentName);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();

	}

	/**
	 * @author Gopinath TestCase Id:51119:Verify user can not see the tiff images
	 *         when 'Allow Production / Images View' is off at an assigment level.
	 *         Description :Verify user can not see the tiff images when 'Allow
	 *         Production / Images View' is off at an assigment level
	 */
	@Test(description = "RPMXCON-51119", alwaysRun = true, groups = { "regression" })
	public void verifyImageTabIsDisplayedAOnDocViewPanelByDisableImagesToggle() {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51119");
		String AssignStamp = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo(
				"#### Verify user can not see the tiff images when 'Allow Production / Images View' is off at an assigment level####");

		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("searching document for assignmnet creation");
		sessionSearch.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("performing bulkAssign");
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Creating assignment by Disable production/images toggle ");
		assignmentPage.createAssignmentWithImagesViewDisabled(AssignStamp, Input.codingFormName);

		baseClass.stepInfo("Select document to view in doc view");
		assignmentPage.selectAssignmentToViewinDocview(AssignStamp);
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCase Id:51118 Verify user can download the native files
	 *         when 'Allow reviewers to download natives' is on at an assigment
	 *         level Description: To Verify user can download the native files when
	 *         'Allow reviewers to download natives' is on at an assigment level
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51120", alwaysRun = true, groups = { "regression" })
	public void verifyUserDownloadNativefiles() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51120");
		String AssignName = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo(
				"####Verify user can download the native files when 'Allow reviewers to download natives' is on at an assigment level####");

		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docview = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("searching document for assignmnet creation");
		sessionSearch.basicContentSearch(Input.testTenthDocId);

		baseClass.stepInfo("performing bulkAssign");
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Creating assignment");
		assignmentPage.createAssignmentWithNativeDownload(AssignName, Input.codingFormName);

		baseClass.stepInfo("Select document to view in doc view");
		assignmentPage.selectAssignmentToViewinDocview(AssignName);

		baseClass.stepInfo("Select Doc And Download All Formats");
		docview.selectDocAndDownloadAllFormats(Input.testTenthDocId);
		loginPage.logout();
	}

	/**
	 * Author : Brundha Created date: NA Modified date: NA Modified by:NA TestCase
	 * id :RPMXCON- 51435 Description: Verify that if the document TIFF is being
	 * presented, the "T" icon with the accompanying mouse over tool tip must appear
	 * 
	 */
	@Test(description = "RPMXCON-51435", alwaysRun = true, groups = { "regression" })
	public void verifyTIconAndToolTipInDocView() throws Exception {

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-51435 docview-sprint:9");
		baseClass.stepInfo(
				"#### Verify that if the document TIFF is being presented, the 'T' icon with the accompanying mouse over tool tip must appear ####");

		String ExpectedText = "TIFF file variant of the document being displayed";
		String DocId = "D21006P";

		baseClass.stepInfo("Basic meta data search");
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		sessionSearch.basicContentSearch(DocId);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		DocViewPage docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Verify Native Document Tooltip");
		docView.verifyingToolTipPopupMessage(DocId, ExpectedText);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(DocId);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Verify Native Document Tooltip");
		docView.verifyingToolTipPopupMessage(DocId, ExpectedText);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(DocId);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Verify Native Document Tooltip");
		docView.verifyingToolTipPopupMessage(DocId, ExpectedText);
		loginPage.logout();
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * @Testcase_id : 51020 - To verify that Remark can be update and deleted by
	 *              other reviewers in same security group.
	 * @Description : To verify that Remark can be update and deleted by other
	 *              reviewers in same security group.
	 */
	@Test(description = "RPMXCON-51020", groups = { "regression" })
	public void verifyRemarkOperationsOfDocumentOnDocview() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51020 spint 09");
		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);

		final String assignStamp = Input.randomText + Utility.dynamicNameAppender();
		String editRemark = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo(
				"#### To verify that Remark can be update and deleted by other reviewers in same security group. ####");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Bulk Assign");
		search.bulkAssign();

		baseClass.stepInfo("Assignment Creation");
		assgnPage.assignmentCreation(assignStamp, Input.codingFormName);

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assgnPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Selecting the assignment");
		assgnPage.SelectAssignmentByReviewer(assignStamp);
		docView = new DocViewPage(driver);
		String docId = docView.getDocumentWithoutRedaction();

		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId);
		baseClass.stepInfo("Add Remark To Non Audio Document");
		docView.addRemarkToNonAudioDocument(1, 20, remark);

		baseClass.stepInfo("Verify Remark Is Added");
		docView.verifyRemarkIsAdded(remark);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

//		Modified
		baseClass.stepInfo("Edit Remark");
		docView.selectDocInMiniDocList(docId);
		docView.editRemarkForNonAudioDoc(remark, editRemark);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Delete remark");
		docView.selectDocInMiniDocList(docId);
		docView.deleteReamark(remark);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assgnPage.deleteAssgnmntUsingPagination(assignStamp);
		loginPage.logout();

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * @Testcase_id : 51017 - To verify that user can add remarks upto 1000
	 *              characters..
	 * @Description : To verify that user can add remarks upto 1000 characters.
	 */
	@Test(description = "RPMXCON-51017", groups = { "regression" })
	public void verifyRemarkOfDocumentOnDocview1000Characters() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51017 spint 09");
		final String assignStamp = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo("#### To verify that user can add remarks upto 1000 characters. ####");

		agnmt = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Bulk assign with new assignment");
		sessionSearch.bulkAssignWithNewAssignment();

		baseClass.stepInfo("Create assignment by bulk assign operationfrom Session search");
		agnmt.createAssignmentByBulkAssignOperation(assignStamp, Input.codeFormName);

		baseClass.stepInfo("Select assignment to view in Doc view");
		agnmt.selectAssignmentToViewinDocview(assignStamp);

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Add Remark To Non Audio Document by 1000 characters");
		docView.addRemarkToNonAudioDocument1000Characters(5, 55);
		loginPage.logout();

	}

	/**
	 * @author Gopinath TestCase Id:50906 verify user can select same document and
	 *         view the rules on work product. Description: To verify user can
	 *         select same document and view the rules on work product.
	 * @throws InterruptedException
	 * 
	 */
	@Test(description = "RPMXCON-50906", groups = { "regression" })
	public void verifyFamilyDocCodingFormAfterperformCodeSameAs() throws InterruptedException {
		int rowno = 1;
		String DOcId = "ID00000151";
		String InputText = "Perform code sameAs";
		baseClass.stepInfo("Test case Id: RPMXCON-50906");
		String AssignStamp = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo("####To verify user can select same document and view the rules on work product.####");
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.selectproject(Input.additionalDataProject);
		// searching document for assignmnet creation
		baseClass.stepInfo("searching document for assignmnet creation");
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch(Input.docId, DOcId);

		baseClass.stepInfo("performing bulkAssign");
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Create assignment With allow user to save with out complete option");
		assignmentPage.createAssignmentWithAllowUserToSave(AssignStamp, Input.codingFormName);

		baseClass.stepInfo("editiing assignment");
		assignmentPage.editAssignmentUsingPaginationConcept(AssignStamp);

		baseClass.stepInfo("Reviewers added and distributed to Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout RMU '" + Input.rmu1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Assignment selecting for view in dovcview");
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);

		baseClass.stepInfo("Performing Code sameAs for family members document");
		docView.performFamilyMemeberDocCodeSameAs(rowno, DOcId, InputText);
		loginPage.logout();
	}

	/**
	 * @Author : Gopinath
	 * @Testcase_Id : RPMXCON-51929 : Verify that when completing the documents same
	 *              as last the entry for the navigated document in mini-DocList
	 *              must always present fully.
	 * @Description : Verify that when completing the documents same as last the
	 *              entry for the navigated document in mini-DocList must always
	 *              present fully.
	 */

	@Test(description = "RPMXCON-51929", enabled = true, groups = { "regression" })
	public void verifyNavigatingDocumenInMiniDocListtByCompleting() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51929 Sprint 10");
		baseClass.stepInfo(
				"#### Verify that when completing the documents same as last the entry for the navigated document in mini-DocList must always present fully ####");
		String AssignStamp = Input.randomText + Utility.dynamicNameAppender();
		String assgnColour = Input.randomText + Utility.dynamicNameAppender();
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		// searching document for assignmnet creation
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Bulk Assign");
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Assignment Creation");
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);

		baseClass.stepInfo("Toggle Coding Stamp Enabled");
		assignmentPage.toggleCodingStampEnabled();

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Selecting the assignment");
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Navigate To Doc View Page URL");
		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Coding Stamp For Saved Document");
		docView.codingStampForSavedDocument(assgnColour, Input.stampSelection);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Navigate To Assignments Page");
		assignmentPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assignmentPage.deleteAssignment(AssignStamp);
		loginPage.logout();

	}

	/**
	 * @author Gopinath TestCase Id:51898 Verify when user clicks the icon to
	 *         expand/collapse Description : To Verify when user clicks the icon to
	 *         expand/collapse
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51898", enabled = true, groups = { "regression" })
	public void verifyDocListHeader() throws InterruptedException {

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51898 Sprint 10");
		baseClass.stepInfo("#### Verify when user clicks the icon to expand/collapse ####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		DocListPage docList = new DocListPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		session.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Basic content search completed");

		baseClass.stepInfo("View serached dos in DocList");
		session.ViewInDocList();

		baseClass.stepInfo("Verify doclist headers expand collapse.");
		docList.verifyDocListHeadersExpandCollapse();
		loginPage.logout();

	}

	/**
	 * @author Gopinath TestCase Id :51896 Verify that footer should be removed from
	 *         doc view and version is show on the left column/header Description :
	 *         TO Verify that footer should be removed from doc view and version is
	 *         show on the left column/header
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51896", enabled = true, groups = { "regression" })
	public void verifyDOcViewPageVersionFooterRemved() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51896 sprint 10");
		baseClass.stepInfo(
				"#### Verify that footer should be removed from doc view and version is show on the left column/header ####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic  content search ");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("View serached dos in DOcview");
		session.ViewInDocView();

		baseClass.stepInfo("Verify Page Version Displayed");
		docView.verifyPageVersionDisplayed();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 51864-Verify that DocView should render the doc when the file
	 * name has / or \ anywhere in the file name. Description : Verify that DocView
	 * should render the doc when the file name has / or \ anywhere in the file name
	 */
	@Test(description = "RPMXCON-51864", enabled = true, groups = { "regression" })
	public void verifyNavigatedToDocViewBySpecailCharInFileName() {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51864- DocExplorer Sprint 10");
		baseClass.stepInfo(
				"#### Verify that DocView should render the doc when the file name has / or \\ anywhere in the file name ####");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate To Doc Explorer Page");
		docExplorer.navigateToDocExplorerPage();

		baseClass.stepInfo("Enter file name in file name filter.");
		docExplorer.enterFileNameInFileNameFilter("/");

		baseClass.stepInfo("Selecting the document in docExplorer page");
		docExplorer.selectDocument(3);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("View document in doc view on doc explorer");
		docExplorer.docExpViewInDocView();

		baseClass.stepInfo("Verify naviagted to doc view");
		docView.verifyDocViewPageNaviagted();
		loginPage.logout();

	}

	/**
	 * @author Gopinath TestCase Id :51860 When a user tries to navigate to DocView
	 *         with some documents, the first document must present completely in
	 *         mini doc list, default view. Description : When a user tries to
	 *         navigate to DocView with some documents, the first document must
	 *         present completely in mini doc list, default view.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51860", enabled = true, groups = { "regression" })
	public void verifyFirstDocumentFullyVisibleByDefault() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51860 sprint 10");
		baseClass.stepInfo(
				"#### When a user tries to navigate to DocView with some documents, the first document must present completely in mini doc list, default view ####");

		DocListPage docList = new DocListPage(driver);
		docView = new DocViewPage(driver);

		SessionSearch session = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic  content search ");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("View serached dos in DOcview");
		session.ViewInDocView();

		baseClass.stepInfo("Verify first document of mini doc list is fully visible on doc view.");
		String firstDocument = docView.verifyFirstDocumentofMiniDocListIsFullyVisible();

		baseClass.stepInfo("navigate to session search page");
		session.navigateToSessionSearchPageURL();

		baseClass.stepInfo("View in doc list");
		session.ViewInDocList();

		docList.selectAllDocumentsInCurrentPageOnly();

		baseClass.stepInfo("Doc List To Doc View");
		docList.docListToDocView();

		baseClass.stepInfo(
				"Verify first document of mini doc list is fully visible on doc view by navigating from doc list.");
		docView.verifyFirstDocumentofMiniDocListIsFullyVisibleFromDocList(firstDocument);
		loginPage.logout();

	}

	/**
	 * @author Gopinath TestCase Id :51759 Verify when ad hoc search is performed
	 *         with search term and phrase with AND/OR operator then hits panel
	 *         should show both the terms. Description : Verify when ad hoc search
	 *         is performed with search term and phrase with AND/OR operator then
	 *         hits panel should show both the terms.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51759", enabled = true, groups = { "regression" })
	public void verifyPersistantHitPanelByTermAndPhraseUsingAndOr() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51759 sprint 10");
		baseClass.stepInfo(
				"#### Verify when ad hoc search is performed with search term and phrase with AND/OR operator then hits panel should show both the terms ####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic  content search by using operator");
		session.basicContentSearchUsingOperator(Input.searchString1, "OR", "\"" + Input.thankyouText + "\"");

		baseClass.stepInfo("View serached dos in DOcview");
		session.ViewInDocView();

		baseClass.stepInfo("Click on persistant hit eye icon.");
		docView.clickOnPersistantHitEyeIcon();

		baseClass.stepInfo("Verify Persistent Hits Panel Displayed");
		docView.verifyPersistentHitsPanelDisplayed();

		baseClass.stepInfo("Click on persistant hit eye icon.");
		docView.clickOnPersistantHitEyeIcon();

		baseClass.stepInfo("Get Persistent Hit");
		docView.getPersistentHit(Input.searchString1);

		baseClass.stepInfo("Click on persistant hit eye icon.");
		docView.clickOnPersistantHitEyeIcon();

		baseClass.stepInfo("Get Persistent Hit");
		docView.getPersistentHit(Input.thankyouText);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Basic  content search by using operator");
		session.basicContentSearchUsingOperator(Input.searchString1, "AND", "\"" + Input.thankyouText + "\"");

		baseClass.stepInfo("View serached dos in DOcview");
		session.ViewInDocView();

		baseClass.stepInfo("Get Persistent Hit");
		docView.getPersistentHit(Input.searchString1);

		baseClass.stepInfo("Click on persistant hit eye icon.");
		docView.clickOnPersistantHitEyeIcon();

		baseClass.stepInfo("Get Persistent Hit");
		docView.getPersistentHit(Input.thankyouText);
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCase Id :51639 Needs to verify after saving remark
	 *         create text high light with out saving. Description : Needs to verify
	 *         after saving remark create text high light with out saving.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51639", enabled = true, groups = { "regression" })
	public void verifyTextHighLightByWithoutSavingRemark() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51639 sprint 10");
		baseClass.stepInfo("#### Needs to verify after saving remark create text high light with out saving ####");
		final String remark = Input.randomText + Utility.dynamicNameAppender();
		final String remark2 = Input.randomText + Utility.dynamicNameAppender();
		docView = new DocViewPage(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		SessionSearch session = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic  content search ");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("View serached dos in DOcview");
		session.ViewInDocView();

		baseClass.stepInfo("Perform Remark with save operation");
		docViewMetaDataPage.performRemarkWithSaveOperation(10, 15, remark);

		baseClass.stepInfo("Verify Remark Is Added");
		docView.verifyRemarkIsAdded(remark);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Perform Remark without save operation");
		docViewMetaDataPage.performRemarkWithoutSaveOperation(10, 15, remark2);

		baseClass.stepInfo("Click on remark button");
		docView.getNonAudioRemarkBtn().Click();

		baseClass.stepInfo("Delete Remark Is Added");
		docView.deleteReamark(remark);

		baseClass.stepInfo("Verify Remark Is Not Added");
		docView.verifyRemarkIsNotAdded(remark2);
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id : 51620 - Verify that the undocked windows should be docked to
	 * parent window when the user refreshes the DocView page on selecting code
	 * same. Description : Verify that the undocked windows should be docked to
	 * parent window when the user refreshes the DocView page on selecting code
	 * same.
	 */
	@Test(description = "RPMXCON-51620", alwaysRun = true, groups = { "regression" })
	public void verifyUndockedToDockedByRefreshPageOnCodeSameAs() throws Exception {
		baseClass = new BaseClass(driver);
		String assignmentName = Input.randomText + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-51620 sprint-10");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that the undocked windows should be docked to parent window when the user refreshes the DocView page on selecting code same ####");
		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);
		docView = new DocViewPage(driver);
		agnmt = new AssignmentsPage(driver);

		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Create Assignment");
		assgnPage.createAssignment(assignmentName, Input.codeFormName);

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(Input.searchText);

		baseClass.stepInfo("Bulk Assign Existing");
		search.bulkAssignExisting(assignmentName);

		baseClass.stepInfo("Created a assignment " + assignmentName);

		baseClass.stepInfo("Navigate To Assignments Page");
		assgnPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Select assignment to view in Doc view");
		agnmt.selectAssignmentToViewinDocview(assignmentName);

		baseClass.stepInfo("Popout Mini Doc List Coding form Analtical MetaData Panels");
		docView.popOutMiniDocListCodingformAnalticalMetaDataPanels();

		baseClass.stepInfo("Switch To child Window");
		String parentWindow = reusableDocView.switchTochildWindow();

		baseClass.stepInfo("Select Docs From Mini Doc List And Code SameAs");
		docView.selectDocsFromMiniDocListAndCodeSameAs();

		baseClass.stepInfo("Child Window To Parent Window Switching");
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);

		baseClass.stepInfo("Click On Save Widget Button");
		docView.clickOnSaveWidgetButton();

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Accept Browser Alert");
		docView.acceptBrowserAlert(false);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Verify All Panels In DocView Are Docked");
		docView.verifyAllPanelsInDocViewAreDocked();

		baseClass.stepInfo("Navigate To Assignments Page");
		agnmt.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		agnmt.deleteAssignment(assignmentName);
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @TestCase Id : 51554 Verify Persistent Keyword Groups as well as
	 *           Assignment-based Persisted Search Hits
	 * @Description : Verify Persistent Keyword Groups as well as Assignment-based
	 *              Persisted Search Hits
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51554", alwaysRun = true, groups = { "regression" })
	public void verifyAssignmentsBasedPersistantHitsByKeywords() throws InterruptedException {

		String AssignName = Input.randomText + Utility.dynamicNameAppender();
		String keywordname = Input.randomText + Utility.dynamicNameAppender();
		String keyword = Input.randomText + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51554 sprint 10");
		baseClass.stepInfo(
				"####Verify Persistent Keyword Groups as well as Assignment-based Persisted Search Hits ####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		AssignmentsPage assignmentPage = new AssignmentsPage(driver);

		KeywordPage keywordPage = new KeywordPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Add keyword");
		keywordPage.AddKeyword(keywordname, keyword);

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.randomText);

		baseClass.stepInfo("Create bulk assign with new assignment with persistant hit.");
		session.bulkAssignWithNewAssignmentWithPersistantHit(AssignName, Input.codingFormName);

		baseClass.stepInfo("Edit assignment");
		assignmentPage.editAssignment(AssignName);

		baseClass.stepInfo("Verify added keyword is checked.");
		assignmentPage.verifyAddedKeywordsChecked();

		baseClass.stepInfo("Reviews adding and distributing to Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(AssignName);

		baseClass.stepInfo("Verify persistant hit for searched string");
		docView.persistenHitWithSearchString(Input.randomText);

		driver.Navigate().refresh();

		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(keyword);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Navigate To Assignments Page");
		assignmentPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assignmentPage.deleteAssgnmntUsingPagination(AssignName);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keyword);
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCased Id:51875 Verify that Action > Folder works fine
	 *         when all records in the reviewers batch are in an Uncompleted state,
	 *         and the user selects only some/select records Description : To Verify
	 *         that Action > Folder works fine when all records in the reviewers
	 *         batch are in an Uncompleted state, and the user selects only
	 *         some/select records
	 */
	@Test(description = "RPMXCON-51875", alwaysRun = true, groups = { "regression" })
	public void verifyMiniDocSaveConfig() {
		baseClass.stepInfo("Test case Id: RPMXCON-51875");
		String AssignStamp = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo(
				"####Verify that Action > Folder works fine when all records in the reviewers batch are in an Uncompleted state, and the user selects only some/select records ####");
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		// searching document for assignmnet creation
		baseClass.stepInfo("bascic contant search");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("performing bulk assign");
		sessionSearch.bulkAssign();

		baseClass.stepInfo("creating assignment");
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);

		baseClass.stepInfo("edit assignment and distributed to reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout RMU '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("select the assignment and view in docview");
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);

		docView.saveConfigFromChildWindow();
		baseClass.stepInfo("saved the minidoc list config from child window");
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCase Id:51873 Verify that Action > Code Same As works
	 *         fine when all records in the reviewer's batch are in an Uncompleted
	 *         state, and the user selects only some/select records Description:To
	 *         Verify that Action > Code Same As works fine when all records in the
	 *         reviewer's batch are in an Uncompleted state, and the user selects
	 *         only some/select records
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51873", alwaysRun = true, groups = { "regression" })
	public void verifyCodeSameAsMiniDocListAndChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51873");
		int noOfRows = 3;
		String inputText = "Vefifying code sameAs";
		String AssignStamp = Input.randomText + Utility.dynamicNameAppender();
		baseClass.stepInfo(
				"#### Verify that Action > Code Same As works fine when all records in the reviewer's batch are in an Uncompleted state, and the user selects only some/select records####");
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		// searching document for assignmnet creation
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.stepInfo("bascic contant search");

		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("performing bulk assign");
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Create assignment WIth allow user to save with out complete option");
		assignmentPage.createAssignmentWithAllowUserToSave(AssignStamp, Input.codingFormName);

		baseClass.stepInfo("editiing assignment");
		assignmentPage.editAssignment(AssignStamp);

		baseClass.stepInfo("Reviewers added and distributed to Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout RUM '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("select the assignment and view in docview");
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);

		baseClass.stepInfo("performing Code sameas for min doc list documents");
		docView.perfomMiniDocListCodeSameAs(inputText, noOfRows);

		baseClass.stepInfo("save the configuration");
		docView.saveConfigFromChildWindow();

		baseClass.stepInfo("performing code sameAs for min doc list  in child window");
		docView.performCodeSameAsMiniDocChildWindow();
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCase Id :51483 Verify that only text highlighter should
	 *         not delete selected to add remarks. Description : Verify that only
	 *         text highlighter should not delete selected to add remarks
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51483", enabled = true, groups = { "regression" })
	public void verifyAlreadyRemarkedTextHighlighted() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51483 sprint 10");
		baseClass.stepInfo("#### Verify that only text highlighter should not delete selected to add remarks ####");
		final String remark = Input.randomText + Utility.dynamicNameAppender();
		docView = new DocViewPage(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		SessionSearch session = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic  content search ");
		session.basicContentSearch(Input.testData1);

		baseClass.stepInfo("View serached dos in DOcview");
		session.ViewInDocView();

		baseClass.stepInfo("Perform Remark with save operation");
		docViewMetaDataPage.performRemarkWithSaveOperation(5, 55, remark);

		baseClass.stepInfo("Verify Remark Is Added");
		docView.verifyRemarkIsAdded(remark);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo(
				"Clicking on text highlighted and verify remark is not added that not editable and clickable in doc view panel.");
		docView.verifyAlreadyRemarkedTextHighlightedNotRemarkAgain();
		loginPage.logout();

	}

	/**
	 * @author Gopinath TestCase Id :51459 Verify that document should be loaded in
	 *         DocView in 3 to 4 seconds. Description : Verify that document should
	 *         be loaded in DocView in 3 to 4 seconds.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51459", enabled = true, groups = { "regression" })
	public void verifyDocumentLoadedinDocViewIn3to4Seconds() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51459 sprint 10");
		baseClass.stepInfo("#### Verify that document should be loaded in DocView in 3 to 4 seconds. ####");
		docView = new DocViewPage(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		SessionSearch session = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic  content search ");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("View serached dos in Docview");
		session.ViewInDocView();

		baseClass.stepInfo("Verify document in docview loaded in 4 sec");
		docView.verifyDocumentLoadedWithIn4Seconds();
		loginPage.logout();

	}

	/**
	 * @author Gopinath TestCase Id:51103 Verify user should able to download the
	 *         associated files by selecting the option from the drop down selection
	 *         Txt, Native from default view Description :To Verify user should able
	 *         to download the associated files by selecting the option from the
	 *         drop down selection Txt, Native from default view
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51103", enabled = true, groups = { "regression" })
	public void verifydownloadDoc() throws InterruptedException {
		String docId = Input.testTenthDocId;
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51103");
		baseClass.stepInfo(
				"#### Verify user should able to download the associated files by selecting the option from the drop down selection Txt, Native from default view####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic  content search ");
		session.basicContentSearch(docId);

		baseClass.stepInfo("View serached dos in DOcview");
		session.ViewInDocView();

		baseClass.stepInfo("Select Doc And Download All Formats");
		docView.selectDocAndDownloadAllFormats(docId);
		loginPage.logout();

	}

	/**
	 * @author Gopinath TestCase Id:51084 Verify the document number from document
	 *         navigation on navigating to documents Description:To Verify the
	 *         document number from document navigation on navigating to documents
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51084", enabled = true, groups = { "regression" })
	public void verifyDocNavigationafterClicOnNextAndPrevButton() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51084");
		baseClass.stepInfo("#### Verify the document number from document navigation on navigating to documents####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic  content search ");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("View serached dos in DOcview");
		session.ViewInDocView();

		baseClass.stepInfo("click on next navigation button and verifying navigation");
		docView.performNextNavigation(2);

		baseClass.stepInfo("click on previous navigation button and verifying navigation");
		docView.performPrevNavigation(2);
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCase Id :51440 Verify that icon to indicate N/P/T/X
	 *         should not be clickable from doc view panel. Description : Verify
	 *         that icon to indicate N/P/T/X should not be clickable from doc view
	 *         panel.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51440", enabled = true, groups = { "regression" })
	public void verifyiconIndicateAndItsNotClickable() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docView = new DocViewPage(driver);

		String nativeToolTip = "Native file variant of the document being displayed";
		String textToolTip = "Text file variant of the document being displayed";
		String tiffToolTip = "TIFF file variant of the document being displayed";
		String pdfToolTip = "PDF file variant of the document being displayed";
		baseClass.stepInfo("Test case Id: RPMXCON-51440 sprint 10");
		baseClass
				.stepInfo("#### Verify that icon to indicate N/P/T/X should not be clickable from doc view panel ####");
		docView = new DocViewPage(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		SessionSearch session = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		DataSets dataSets = new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithNameInDocView(Input.pdfDataSet);
		String tiffDocId = docView.getRequiredDocs("tiff");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic  content search ");
		session.navigateToSessionSearchPageURL();
		session.metaDataSearchInBasicSearch(Input.docId, tiffDocId);
		session.addPureHit();
		session.addNewSearch();
		session.multipleBasicContentSearch(Input.searchString2);
		session.addPureHit();

		baseClass.stepInfo("View serached dos in Docview");
		session.addDocsMetCriteriaToActionBoard();

		String nativeDocId = docView.getRequiredDocs("native");
		String textDocId = docView.getRequiredDocs("text");
		String pdfDocId = docView.getRequiredDocs("pdf");

		MiniDocListPage miniDoc = new MiniDocListPage(driver);
		miniDoc.removingAllExistingFieldsAndAddingNewField(Input.docId);

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify native file document default tab contains tool tip");
		docView.verifyingToolTipPopupMessage(nativeDocId, nativeToolTip);

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify text file document default tab contains tool tip");
		docView.verifyingToolTipPopupMessage(textDocId, textToolTip);

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify Tiff file document default tab contains tool tip");
		docView.verifyingToolTipPopupMessage(tiffDocId, tiffToolTip);

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify PDF file document default tab contains tool tip");
		docView.verifyingToolTipPopupMessage(pdfDocId, pdfToolTip);

		baseClass.stepInfo("Navigate to session search page");
		session.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Navigate to doc view page");
		docView.navigateToDocViewPageURL();

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify native document icon on default tab is not clickable");
		docView.verifyDocumentIconIsNotClickable(nativeDocId);

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify text document icon on default tab is not clickable");
		docView.verifyDocumentIconIsNotClickable(textDocId);

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify tiff document icon on default tab is not clickable");
		docView.verifyDocumentIconIsNotClickable(tiffDocId);

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify pdf document icon on default tab is not clickable");
		docView.verifyDocumentIconIsNotClickable(pdfDocId);

		miniDoc.removingFieldsAndDragnDropDefault();
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @throws InterruptedException
	 * @TestCased Id:51439 Verify that N/P/T/X should not be displayed when default
	 *            view tab is off at an assignment level in context of an
	 *            assignment.
	 * @Description : Verify that N/P/T/X should not be displayed when default view
	 *              tab is off at an assignment level in context of an assignment
	 */
	@Test(description = "RPMXCON-51439", alwaysRun = true, groups = { "regression" })
	public void verifyNPTXIconsOnDefaultTabByOpertionsOnAssignment() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51439 Sprint 10");
		String AssignStamp = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo(
				"#### Verify that N/P/T/X should not be displayed when default view tab is off at an assignment level in context of an assignment. ####");
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		ManageAssignment mngAssign = new ManageAssignment(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		DataSets dataSets = new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithNameInDocView(Input.pdfDataSet);
		String tiffDocId = docView.getRequiredDocs("tiff");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		// searching document for assignmnet creation
		baseClass.stepInfo("basic contant search");

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch(Input.docId, tiffDocId);
		sessionSearch.addPureHit();
		sessionSearch.addNewSearch();
		sessionSearch.multipleBasicContentSearch(Input.searchString2);
		sessionSearch.addPureHit();

		baseClass.stepInfo("performing bulk assign");
		sessionSearch.bulkAssign();

		baseClass.stepInfo("creating assignment");
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);

		baseClass.stepInfo("Disable show default view tab toogle");
		mngAssign.disableDefaultTabToogle(false);

		baseClass.stepInfo("edit assignment and distributed to reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		baseClass.stepInfo("Select assignment to view in Doc view");
		assignmentPage.selectAssignmentToViewinDocview(AssignStamp);

		String nativeDocId = docView.getRequiredDocs("Native");
		String textDocId = docView.getRequiredDocs("Text");
		String pdfDocId = docView.getRequiredDocs("pdf");

		MiniDocListPage miniDoc = new MiniDocListPage(driver);
		miniDoc.removingAllExistingFieldsAndAddingNewField(Input.docId);

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify native document icon on default tab is displayed");
		docView.verifyDocumentIconIsNotClickable(nativeDocId);

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify text document icon on default tab is displayed");
		docView.verifyDocumentIconIsNotClickable(textDocId);

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify tiff document icon on default tab is displayed");
		docView.verifyDocumentIconIsNotClickable(tiffDocId);

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify pdf document icon on default tab is displayed");
		docView.verifyDocumentIconIsNotClickable(pdfDocId);

		baseClass.stepInfo("Navigate to assignment page");
		assignmentPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Edit assignment by assignment name");
		assignmentPage.editAssignmentUsingPaginationConcept(AssignStamp);

		baseClass.stepInfo("Disable show default view tab toogle");
		mngAssign.disableDefaultTabToogle(true);

		baseClass.stepInfo("Select assignment to view in Doc view");
		assignmentPage.selectAssignmentToViewinDocview(AssignStamp);

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify Native Document Icon Is Not Displayed On Default Tab");
		docView.verifyDocumentIconIsNotDisplayedOnDefaultTab(nativeDocId, "N");

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify Text Document Icon Is Not Displayed On Default Tab");
		docView.verifyDocumentIconIsNotDisplayedOnDefaultTab(textDocId, "X");

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify Tiff Document Icon Is Not Displayed On Default Tab");
		docView.verifyDocumentIconIsNotDisplayedOnDefaultTab(tiffDocId, "T");

		driver.Navigate().refresh();
		baseClass.stepInfo("Verify Pdf Document Icon Is Not Displayed On Default Tab");
		docView.verifyDocumentIconIsNotDisplayedOnDefaultTab(pdfDocId, "P");

		miniDoc.removingFieldsAndDragnDropDefault();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout RMU '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("select the assignment and view in docview");
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);

		miniDoc.removingAllExistingFieldsAndAddingNewField(Input.docId);

		baseClass.stepInfo("Verify Native Document Icon Is Not Displayed On Default Tab");
		docView.verifyDocumentIconIsNotDisplayedOnDefaultTab(nativeDocId, "N");

		baseClass.stepInfo("Verify Text Document Icon Is Not Displayed On Default Tab");
		docView.verifyDocumentIconIsNotDisplayedOnDefaultTab(textDocId, "X");

		baseClass.stepInfo("Verify Tiff Document Icon Is Not Displayed On Default Tab");
		docView.verifyDocumentIconIsNotDisplayedOnDefaultTab(tiffDocId, "T");

		baseClass.stepInfo("Verify Pdf Document Icon Is Not Displayed On Default Tab");
		docView.verifyDocumentIconIsNotDisplayedOnDefaultTab(pdfDocId, "P");

		miniDoc.removingFieldsAndDragnDropDefault();
		loginPage.logout();

	}

	/**
	 * @author Gopinath TestCase Id:51101 Verify user should able to download the
	 *         associated files by selecting the option from the drop down selection
	 *         Txt, Native Description:To Verify user should able to download the
	 *         associated files by selecting the option from the drop down selection
	 *         Txt, Native
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51101", alwaysRun = true, groups = { "regression" })
	public void verifyAssociatedFileNativeDownloadOption() throws InterruptedException {
		String docId1 = "ID00001246";
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51101");
		baseClass.stepInfo(
				"#### Verify user should able to download the associated files by selecting the option from the drop down selection Txt, Native####");

		docView = new DocViewPage(driver);
		MiniDocListPage mini = new MiniDocListPage(driver);
		SessionSearch session = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic  content search for 1st document ");
		session.basicContentSearch(docId1);

		baseClass.stepInfo("View serached dos in Docview");
		session.ViewInDocView();

		mini.removingAllExistingFieldsAndAddingNewField(Input.docId);
		baseClass.stepInfo("select document to download Native");
		docView.selectDocInMiniDocList(docId1);

		baseClass.stepInfo("Verify download selection displayed.");
		docView.verifyDownloadSelectionDisplayed();

		baseClass.stepInfo("Downloading native 1 form selected document");
		docView.downloadSelectedFormaats(Input.fileDownloadLocation, "native", null, null, null);
		mini.removingFieldsAndDragnDropDefault();
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCase I d:51100 Verify user should be able to click the
	 *         download icon when associated files are ingested Description:To
	 *         Verify user should be able to click the download icon when associated
	 *         files are ingested
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51100", alwaysRun = true, groups = { "regression" })
	public void verifyAssociatedFileDownloadOptions() throws InterruptedException {
		String docId1 = "ID00000237";
		String docId2 = "ID00000266";// id to download translation 1
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51100");
		baseClass.stepInfo(
				"#### Verify user should able to download the associated files by selecting the option from the drop down selection Txt, Native from default view####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic  content search for 1st document ");
		session.basicContentSearch(docId1);

		baseClass.stepInfo("Basic  content search for 2st document ");
		session.multipleBasicContentSearch(docId2);

		baseClass.stepInfo("View serached dos in DOcview");
		session.ViewInDocView();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("select document to download Native,tiff,txt");
		docView.getDocView_DocId(docId1).waitAndClick(10);

		driver.waitForPageToBeReady();
		docView.downloadSelectedFormaats(Input.fileDownloadLocation, "txt", "native", null, null);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("select document to download translation 1 ");
		docView.getDocView_DocId(docId2).waitAndClick(10);

		driver.waitForPageToBeReady();
		docView.downloadSelectedFormaats(Input.fileDownloadLocation, "tiff", "txt",null,null);
		loginPage.logout();

	}

	/**
	 * @author Gopinath TestCase Id:51437 Verify on navigating to other Text, Images
	 *         or Translations tab and returning to Default tab should show the "N",
	 *         "P", "T", "X" as per the document Description : To Verify on
	 *         navigating to other Text, Images or Translations tab and returning to
	 *         Default tab should show the "N", "P", "T", "X" as per the document
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51437", alwaysRun = true, groups = { "regression" })
	public void verifyDocIconFromDocViewPanal() throws InterruptedException {
		baseClass.stepInfo("Test case id : RPMXCON-51437");
		baseClass.stepInfo(
				"###Verify on navigating to other Text, Images or Translations tab and returning to Default tab should show the N, P, T, X as per the document###");

		String N_DocToolTipMessage = "Native file variant of the document being displayed";
		String X_DocToolTipMessage = "Text file variant of the document being displayed";
		String T_DocToolTipMessage = "TIFF file variant of the document being displayed";
		String P_DocToolTipMessage = "PDF file variant of the document being displayed";
		SessionSearch session = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		DataSets dataSets = new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithNameInDocView(Input.pdfDataSet);
		String T_DocID = docView.getRequiredDocs("tiff");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.stepInfo("Step 1: Search for the docs ");

		session.navigateToSessionSearchPageURL();
		session.metaDataSearchInBasicSearch(Input.docId, T_DocID);
		session.addPureHit();
		session.addNewSearch();
		session.multipleBasicContentSearch(Input.searchString2);
		session.addPureHit();

		baseClass.stepInfo("Step 2:view docS in DocView");
		session.addDocsMetCriteriaToActionBoard();

		String N_DocID = docView.getRequiredDocs("native");
		String X_DocID = docView.getRequiredDocs("Text");
		String P_DocId = docView.getRequiredDocs("pdf");

		MiniDocListPage miniDoc = new MiniDocListPage(driver);
		miniDoc.removingAllExistingFieldsAndAddingNewField(Input.docId);

		baseClass.stepInfo("Verify T icon and tolltip message for selected document");
		docView.verifydocIdIconAfterClickOnallTabsOndocviewPanal(T_DocID, T_DocToolTipMessage);

		baseClass.stepInfo("Verify X icon and tolltip message for selected document");
		docView.verifydocIdIconAfterClickOnallTabsOndocviewPanal(X_DocID, X_DocToolTipMessage);

		baseClass.stepInfo("Verifying P icon and tolltip message for selected document");
		docView.verifydocIdIconAfterClickOnallTabsOndocviewPanal(P_DocId, P_DocToolTipMessage);

		baseClass.stepInfo("Verifying N icon and tolltip message for selected document");
		docView.verifydocIdIconAfterClickOnallTabsOndocviewPanal(N_DocID, N_DocToolTipMessage);

		miniDoc.removingFieldsAndDragnDropDefault();
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCase Id:51046 Verify user can not see the keywords
	 *         highlighted outside of an assignment when keyword groups assigned to
	 *         the assignment only Description : Verify user can not see the
	 *         keywords highlighted outside of an assignment when assigned to the
	 *         assignment only keyword groups
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-51046", alwaysRun = true, groups = { "regression" })
	public void verifyKeyWordHighlightOnDocView() throws InterruptedException, AWTException {
		String AssignName = Input.randomText + Utility.dynamicNameAppender();
		String keywordname = "t";
		String colour = "Gold";
		String rgbCode = "rgb(255, 215, 0)";
		String HaxCode = "#ffd700";
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51046 sprint 10");
		baseClass.stepInfo(
				"####Verify user can not see the keywords highlighted outside of an assignment when keyword groups assigned to the assignment only####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		AssignmentsPage assignmentPage = new AssignmentsPage(driver);

		KeywordPage keywordPage = new KeywordPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Add keyword");
		keywordPage.addKeyword(keywordname, colour);

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.selectsecuritygroup(Input.securityGroup);
		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Create bulk assign with new assignment with persistant hit.");
		session.bulkAssignWithNewAssignmentWithPersistantHit(AssignName, Input.codingFormName);

		baseClass.stepInfo("Edit assignment");
		assignmentPage.editAssignment(AssignName);

		baseClass.stepInfo("Verify added keyword is checked.");
		assignmentPage.verifyAddedKeywordsChecked();

		baseClass.stepInfo("Reviews adding and distributing to Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(AssignName);

		baseClass.stepInfo("Verify persistant hit for keyword");
		docView.persistenHitWithSearchString(keywordname);

		baseClass.stepInfo("verify highlight keyword in document");
		docView.verifyKeywordHighlightedOnDocViewwithKeywordColour(rgbCode, HaxCode);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Navigate To Assignments Page");
		assignmentPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assignmentPage.deleteAssignment(AssignName);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keywordname);
		loginPage.logout();

	}

	/**
	 * @author Gopinath TestCase Id 51040 Verify keyword highlighting from doc view
	 *         when same keyword groups are mapped to assignment and security group
	 * @description:To Verify keyword highlighting from doc view when same keyword
	 *                 groups are mapped to assignment and security group
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-51040", alwaysRun = true, groups = { "regression" })
	public void verifyKeyWordHighlightofbasicSearchAndAssignmentOnDocView() throws InterruptedException, AWTException {
		String AssignName = Input.randomText + Utility.dynamicNameAppender();
		String keywordname = "t";
		String colour = "Gold";
		String rgbCode = "rgb(255, 215, 0)";
		String HaxCode = "#ffd700";
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51040 sprint 10");
		baseClass.stepInfo(
				"####Verify keyword highlighting from doc view when same keyword groups are mapped to assignment and security group####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		AssignmentsPage assignmentPage = new AssignmentsPage(driver);

		KeywordPage keywordPage = new KeywordPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Add keyword");
		keywordPage.addKeyword(keywordname, colour);

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		// docViewRedact.selectsecuritygroup(Input.securityGroup);
		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Create bulk assign with new assignment with persistant hit.");
		session.bulkAssignWithNewAssignmentWithPersistantHit(AssignName, Input.codingFormName);

		baseClass.stepInfo("Edit assignment");
		assignmentPage.editAssignment(AssignName);

		baseClass.stepInfo("Verify added keyword is checked.");
		assignmentPage.verifyAddedKeywordsChecked();

		baseClass.stepInfo("Reviews adding and distributing to Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();

		baseClass.stepInfo("Verify persistant hit for keyword");
		docView.persistenHitWithSearchString(keywordname);

		baseClass.stepInfo("verify highlight keyword in document as keyword mapped to security group");
		docView.verifyKeywordHighlightedOnDocViewwithKeywordColour(rgbCode, HaxCode);

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(AssignName);

		baseClass.stepInfo("Verify persistant hit for keyword as keywords are mapped to assignment");
		docView.persistenHitWithSearchString(keywordname);

		baseClass.stepInfo("verify highlight keyword in document");
		docView.verifyKeywordHighlightedOnDocViewwithKeywordColour(rgbCode, HaxCode);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Navigate To Assignments Page");
		assignmentPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assignmentPage.deleteAssignment(AssignName);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keywordname);
		loginPage.logout();

	}

	/**
	 * @author Gopinath TestCase Id:51038 Verify keyword highlighting when different
	 *         keyword groups having same keywords, different color mapped to
	 *         different assignments having same documents Description:To Verify
	 *         keyword highlighting when different keyword groups having same
	 *         keywords, different color mapped to different assignments having same
	 *         documents
	 * @throws AWTException,InterruptedException
	 */
	@Test(description = "RPMXCON-51038", alwaysRun = true, groups = { "regression" })
	public void verifyKeywordHighlightSameKeywordWithDifferentColor() throws AWTException, InterruptedException {
		String AssignName1 = Input.randomText + Utility.dynamicNameAppender();
		String AssignName2 = Input.randomText + Utility.dynamicNameAppender();
		String keywordName1 = "key" + Utility.dynamicNameAppender();
		String keywordName2 = "key" + Utility.dynamicNameAppender();
		String keyword = "es";
		String colour1 = "Gold";
		String rgbCode1 = "rgb(255, 215, 0)";
		String HaxCode1 = "#ffd700";
		String colour2 = Input.KeyWordColour;// blue
		String rgbCode2 = "rgb(0, 0, 255)";
		String HaxCode2 = "#0000ff";
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51038 sprint 10");
		baseClass.stepInfo(
				"####Verify keyword highlighting when different keyword groups having same keywords, different color mapped to different assignments having same documents####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		AssignmentsPage assignmentPage = new AssignmentsPage(driver);

		KeywordPage keywordPage = new KeywordPage(driver);

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		// docViewRedact.selectsecuritygroup(Input.securityGroup);
		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Add keyword one");
		keywordPage.addKeyword(keywordName1, keyword, colour1);
		driver.Navigate().refresh();
		baseClass.stepInfo("Add keyword two");
		keywordPage.addKeyword(keywordName2, keyword, colour2);

		baseClass.stepInfo(" Basic content search for");
		session.basicContentSearch(Input.testTenthDocId);

		baseClass.stepInfo("Create bulk assign with new assignment one with persistant hit.");
		session.bulkAssignWithNewAssignmentWithPersistantHit(AssignName1, Input.codingFormName);
		driver.getWebDriver().get(Input.url + "Search/Searches");

		baseClass.stepInfo("Create bulk assign with new assignment two with persistant hit.");
		session.bulkAssignWithNewAssignmentWithPersistantHit(AssignName2, Input.codingFormName);

		baseClass.stepInfo("unmapping all keywords from first assignment");
		assignmentPage.unmappingKeywordsFromAssignment(AssignName1);

		baseClass.stepInfo("aading keyword to first assignment");
		assignmentPage.addKeywordToAssignment(keywordName1);

		baseClass.stepInfo("Reviews adding and distributing to Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(AssignName1);

		baseClass.stepInfo("Verify persistant hit for keyword of first assignment");
		docView.persistenHitWithSearchString(keyword);

		baseClass.stepInfo("verify highlight keyword in document for first assignment");
		docView.verifyKeywordHighlightedOnDocViewwithKeywordColour(rgbCode1, HaxCode1);

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("unmapping all keywords from second assignment");
		assignmentPage.unmappingKeywordsFromAssignment(AssignName2);

		baseClass.stepInfo("aading keyword to second assignment");
		assignmentPage.addKeywordToAssignment(keywordName2);

		baseClass.stepInfo("Reviews adding and distributing to Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		assignmentPage.SelectAssignmentByReviewer(AssignName2);

		baseClass.stepInfo("Verify persistant hit for keyword of second assignment");
		docView.persistenHitWithSearchString(keyword);

		baseClass.stepInfo("verify highlight keyword in document for second assignment");
		docView.verifyKeywordHighlightedOnDocViewwithKeywordColour(rgbCode2, HaxCode2);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Navigate To Assignments Page");
		assignmentPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assignmentPage.deleteAssignment(AssignName1);
		assignmentPage.deleteAssignment(AssignName2);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keywordName1);
		keywordPage.deleteKeywordByName(keywordName2);
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCase Id:51063 Verify keyword highlighting from doc view
	 *         in context of assignments having same assigned documents and keyword
	 *         group. Description: Verify keyword highlighting from doc view in
	 *         context of assignments having same assigned documents and keyword
	 *         group.
	 * @throws AWTException,InterruptedException
	 */
	@Test(description = "RPMXCON-51063", alwaysRun = true, groups = { "regression" })
	public void verifyKeywordHighlightByDifferentAssignments() throws AWTException, InterruptedException {
		String AssignName1 = Input.randomText + Utility.dynamicNameAppender();
		String AssignName2 = Input.randomText + Utility.dynamicNameAppender();
		String keywordName1 = "key" + Utility.dynamicNameAppender();
		String keyword = "es";
		String colour2 = Input.KeyWordColour;// blue
		String rgbCode2 = "rgb(0, 0, 255)";
		String HaxCode2 = "#0000ff";
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51063 sprint 11");
		baseClass.stepInfo(
				"#### Verify keyword highlighting from doc view in context of assignments having same assigned documents and keyword group. ####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		AssignmentsPage assignmentPage = new AssignmentsPage(driver);

		KeywordPage keywordPage = new KeywordPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.selectproject();
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Add keyword one");
		keywordPage.addKeyword(keywordName1, keyword, colour2);

		baseClass.stepInfo(" Basic content search for");
		session.basicContentSearch(Input.testTenthDocId);

		baseClass.stepInfo("Create bulk assign with new assignment one with persistant hit.");
		session.bulkAssignWithNewAssignmentWithPersistantHit(AssignName1, Input.codingFormName);

		baseClass.stepInfo("unmapping all keywords from first assignment");
		assignmentPage.unmappingKeywordsFromAssignment(AssignName1);

		baseClass.stepInfo("aading keyword to first assignment");
		assignmentPage.addKeywordToAssignment(keywordName1);

		baseClass.stepInfo("Select assignment to view in Doc view");
		assignmentPage.selectAssignmentToViewinDocview(AssignName1);

		baseClass.stepInfo("Verify expected document is displayed in mini doc list.");
		docView.verifyExpectedDocumentIsDisplayedInMiniDocList(Input.testTenthDocId);

		baseClass.stepInfo("verify highlight keyword in document for first assignment");
		docView.verifyKeywordHighlightedOnDocViewwithKeywordColour(rgbCode2, HaxCode2);

		baseClass.stepInfo("Navigate to session search");
		session.navigateToSessionSearchPageURL();

		driver.waitForPageToBeReady();
		baseClass.stepInfo(" Basic content search for");
		session.basicContentSearch(Input.testTenthDocId);

		baseClass.stepInfo("Create bulk assign with new assignment two with persistant hit.");
		session.bulkAssignWithNewAssignmentWithPersistantHit(AssignName2, Input.codingFormName);

		baseClass.stepInfo("unmapping all keywords from first assignment");
		assignmentPage.unmappingKeywordsFromAssignment(AssignName2);

		baseClass.stepInfo("Scroll to top of page");
		driver.scrollPageToTop();

		baseClass.stepInfo("Click on save button");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(assignmentPage.getSaveBtn());
		assignmentPage.getSaveBtn().waitAndClick(5);

		baseClass.stepInfo("Reviews adding and distributing to Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		baseClass.stepInfo("Select assignment to view in Doc view");
		assignmentPage.selectAssignmentToViewinDocview(AssignName2);

		baseClass.stepInfo("Verify expected document is displayed in mini doc list.");
		docView.verifyExpectedDocumentIsDisplayedInMiniDocList(Input.testTenthDocId);

		baseClass.stepInfo("Verify keyword not highlighted on doc view.");
		docView.verifyKeywordIsNotHighlightedOnDocView(rgbCode2, HaxCode2);

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.selectproject();
		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(AssignName2);

//		Added on

		MiniDocListPage minidoc = new MiniDocListPage(driver);
		minidoc.removingFieldsAndDragnDropDefault();

		baseClass.stepInfo("Verify expected document is displayed in mini doc list.");
		docView.verifyExpectedDocumentIsDisplayedInMiniDocList(Input.testTenthDocId);

		baseClass.stepInfo("Verify keyword not highlighted on doc view.");
		docView.verifyKeywordIsNotHighlightedOnDocView(rgbCode2, HaxCode2);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Navigate To Assignments Page");
		assignmentPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assignmentPage.deleteAssignment(AssignName1);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assignmentPage.deleteAssignment(AssignName2);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keywordName1);
	}

	/**
	 * @author Gopinath
	 * @testCase Id:51541 Verify that same user with two different tabs in the same
	 *           browser, and confirm that able to add reviewer remark to the same
	 *           records successfully, and confirm the XML nodes are all properly
	 *           reflected in the XML
	 * @description To Verify that same user with two different tabs in the same
	 *              browser, and confirm that able to add reviewer remark to the
	 *              same records successfully, and confirm the XML nodes are all
	 *              properly reflected in the XML
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-51541", alwaysRun = true, groups = { "regression" })
	public void verifyRemarkOnDifferentTabsOnSameBrowser() throws InterruptedException, AWTException {
		String remark = "testone";
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51541 sprint 11");
		baseClass.stepInfo(
				"###Verify that same user with two different tabs in the same browser, and confirm that able to add reviewer remark to the same records successfully, and confirm the XML nodes are all properly reflected in the XML###");
		DocViewPage docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.stepInfo(" Basic content search");

		session.navigateToSessionSearchPageURL();
		session.metaDataSearchInBasicSearch(Input.docId, Input.testTenthDocId);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.clickButton(docView.remarksIcon());

		String currentUrl = driver.getUrl();

		DocViewMetaDataPage docVIewMetaData = new DocViewMetaDataPage(driver);

		baseClass.stepInfo("Opening second tab");
		docVIewMetaData.openDuplicateTabOfAlreadyOpenedTab();

		baseClass.stepInfo("Switching to second tab");
		String parentWindowHandle = reusableDocView.switchTochildWindow();
		String childWindowHandle = driver.getWebDriver().getWindowHandle();

		baseClass.stepInfo("Getting : " + currentUrl + " url in second tab");
		driver.getWebDriver().get(currentUrl);

		session.ViewInDocViewWithoutPureHit();

		baseClass.stepInfo("Add Remark To Non Audio Document");
		docView.addRemarkToNonAudioDocument(1, 10, remark);

		baseClass.stepInfo("Switchimg to first window");
		driver.switchTo().window(parentWindowHandle);

		baseClass.stepInfo("Verify Disable Remark Warning Message");
		docView.verifyDisableRemarkWarningMessage();

		baseClass.stepInfo("Select Doc To View In DocView Panal");
		docView.selectDocToViewInDocViewPanal(Input.testTenthDocId);

		baseClass.stepInfo("verify visibility of added remark after reload the document in first tab");
		docView.verifyRemarkIsAdded(remark);

		baseClass.stepInfo("Switch to parent window from child window");
		reusableDocView.childWindowToParentWindowSwitching(childWindowHandle);

		baseClass.stepInfo("verify visibility of added remark after reload the document in second tab");
		docView.verifyRemarkIsAdded(remark);
	}

	/**
	 * @author Gopinath TestCase Id: 51406 Verify on click of the "eye" icon, ALL
	 *         highlighted terms - including those that are set from Manage >
	 *         Keywords configured within the assignment. Description:Verify on
	 *         click of the "eye" icon, ALL highlighted terms - including those that
	 *         are set from Manage > Keywords configured within the assignment.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51406", groups = { "regression" })
	public void verifyAllKeywordsAreDisplayedOnPersistantHitPanel() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51406 of Sprint 11");
		String keywordname = Input.randomText + Utility.dynamicNameAppender();
		String keyword = Input.randomText + Utility.dynamicNameAppender();
		String assignName1 = Input.randomText + Utility.dynamicNameAppender();
		baseClass.stepInfo(
				"#### Verify on click of the 'eye' icon, ALL highlighted terms - including those that are set from Manage > Keywords configured within the assignment ####");

		KeywordPage keywordPage = new KeywordPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.stepInfo("Navigate to keyword page");

		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Add keyword");
		keywordPage.AddKeyword(keywordname, keyword);

		baseClass.stepInfo("Get All Keywords in keywords lsit table");
		List<String> keywords = keywordPage.getAllKeywords();

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		baseClass.stepInfo("Basic Basic content search");
		session.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Create bulk assign with new assignment one with persistant hit.");
		session.bulkAssignWithNewAssignmentWithPersistantHit(assignName1, Input.codingFormName);

		AssignmentsPage assignmentPage = new AssignmentsPage(driver);

		assignmentPage.navigateToAssignmentsPage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Edit assignment");
		assignmentPage.editAssignment(assignName1);

		baseClass.stepInfo("Verify added keyword is checked.");
		assignmentPage.verifyAddedKeywordsChecked();

		baseClass.stepInfo("Select assignment to view in Doc view");
		assignmentPage.selectAssignmentToViewinDocview(assignName1);

		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(keyword);

		baseClass.stepInfo("Verify Persistant Hits With Doc View");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.verifyPersistantHitsWithDocView(keywords);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		session.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Basic content search");
		session.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Navigate to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(keyword);

		baseClass.stepInfo("Verify Persistant Hits With Doc View");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.verifyPersistantHitsWithDocView(keywords);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keyword);
		loginPage.logout();
	}

	/**
	 * @author Gopinath TestCase Id :51970 Verify that navigating to document after
	 *         entering the document number in DocView should bring up the document
	 *         in 4 sec and ready for the user to act up on Description : Verify
	 *         that navigating to document after entering the document number in
	 *         DocView should bring up the document in 4 sec and ready for the user
	 *         to act up on
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51970", enabled = true, groups = { "regression" })
	public void verifyDocumentLoadedinDocViewIn4Seconds() throws InterruptedException {
		String docNum = "4";
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51970 sprint 11");
		baseClass.stepInfo(
				"#### Verify that navigating to document after entering the document number in DocView should bring up the document in 4 sec and ready for the user to act up on ####");
		docView = new DocViewPage(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		SessionSearch session = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic  content search ");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("View serached dos in Docview");
		session.ViewInDocView();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify document in docview loaded in 4 sec");
		docView.verifyDocumentLoadedWithInFourSeconds(docNum);

	}

	/**
	 * @author Gopinath
	 * @testCase Id:51538 Verify that when two different users adds reviewer remarks
	 *           to the same record successfully, and confirm that the XML nodes are
	 *           all properly created/reflected.
	 * @description Verify that when two different users adds reviewer remarks to
	 *              the same record successfully, and confirm that the XML nodes are
	 *              all properly created/reflected.
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-51538", alwaysRun = true, groups = { "regression" })
	public void verifyRemarkOnDifferentTabsOnSameBrowserDifferentUsers() throws InterruptedException, AWTException {
		String remark = Input.randomText + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51538 sprint 11");
		baseClass.stepInfo(
				"### Verify that when two different users adds reviewer remarks to the same record successfully, and confirm that the XML nodes are all properly created/reflected ###");
		DocViewPage docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();

		String docID = docView.getDocumentWithoutRedaction();
		driver.waitForPageToBeReady();

		String currentUrl = driver.getUrl();

		DocViewMetaDataPage docVIewMetaData = new DocViewMetaDataPage(driver);

		baseClass.stepInfo("Opening second tab");
		docVIewMetaData.openDuplicateTabOfAlreadyOpenedTab();

		baseClass.stepInfo("Switching to second tab");
		String parentWindowHandle = reusableDocView.switchTochildWindow();

		baseClass.stepInfo("Getting : " + currentUrl + " url in second tab");
		driver.getWebDriver().get(currentUrl);

		baseClass.impersonateRMUtoReviewer();

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();

		baseClass.stepInfo("Adding remark to document");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docID);
		baseClass.stepInfo("Add Remark To Non Audio Document");
		baseClass.waitTime(5);
		driver.Navigate().refresh();
		docView.addRemarkToNonAudioDocument(5, 20, remark);

		baseClass.stepInfo("verify visibility of added remark after reload the document in first tab");
		docView.verifyRemarkIsAdded(remark);

		baseClass.stepInfo("Switch to parent window from child window");
//		reusableDocView.childWindowToParentWindowSwitching(parentWindowHandle);
		driver.switchTo().window(parentWindowHandle);

		baseClass.waitTime(5);
		baseClass.stepInfo("Click on redaction icon");
		baseClass.waitForElement(docView.redactionIcon());
		docView.redactionIcon().waitAndClick(5);

		baseClass.stepInfo("Verify Disable Remark Warning Message");
		docView.verifyDisableRemarkWarningMessage();

		baseClass.waitTime(2);
		baseClass.stepInfo("Verify weather delete and edit fields are not enabled.");
		docView.verifyDeleteAndEditFieldsAreNotEnabled();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		docView.selectDocInMiniDocList(docID);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify visibility of added remark after reload the document in first tab");
		docView.verifyRemarkIsAdded(remark);

		baseClass.stepInfo("Log out");
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @testCase Id:51539 Verify that when two different users edits reviewer
	 *           remarks to the same record successfully.
	 * @description Verify that when two different users edits reviewer remarks to
	 *              the same record successfully.
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-51539", alwaysRun = true, groups = { "regression" })
	public void verifyEditRemarkOnDifferentTabsOnSameBrowserDifferentUsers() throws InterruptedException, AWTException {
		String remark = Input.randomText + Utility.dynamicNameAppender();
		String editRemark = Input.randomText + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51539 sprint 11");
		baseClass.stepInfo(
				"### Verify that when two different users edits reviewer remarks to the same record successfully. ###");
		DocViewPage docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

//		Added on
		baseClass.selectproject();
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();

		String docId = docView.getDocumentWithoutRedaction();

//		Added on 
		DocViewRedactions docViewredact = new DocViewRedactions(driver);
		docView.selectDocInMiniDocList(docId);

		driver.waitForPageToBeReady();
		String currentUrl = driver.getUrl();

		DocViewMetaDataPage docVIewMetaData = new DocViewMetaDataPage(driver);

		baseClass.stepInfo("Opening second tab");
		docVIewMetaData.openDuplicateTabOfAlreadyOpenedTab();

		baseClass.stepInfo("Switching to second tab");
		String parentWindowHandle = reusableDocView.switchTochildWindow();

		baseClass.stepInfo("Getting : " + currentUrl + " url in second tab");
		driver.getWebDriver().get(currentUrl);

		baseClass.impersonateRMUtoReviewer();

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();

		docView.selectDocInMiniDocList(docId);

		baseClass.stepInfo("Adding remark to document");
		baseClass.waitTime(5);
		docView.addRemarkToNonAudioDocument(5, 50, remark);

		baseClass.stepInfo("verify visibility of added remark after reload the document in first tab");

		docView.selectDocInMiniDocList(docId);
		docView.verifyRemarkIsAdded(remark);

		baseClass.stepInfo("Edit already added remark");
		docView.selectDocInMiniDocList(docId);
		docView.editRemark(editRemark);

		baseClass.stepInfo("Switch to parent window from child window");
		reusableDocView.childWindowToParentWindowSwitching(parentWindowHandle);
//		docView.closeWindow(1);

		baseClass.stepInfo("Click on redaction icon");
//		Addded on

		baseClass.waitTime(4);
		baseClass.waitForElement(docView.redactionIcon());
		docView.redactionIcon().waitAndClick(5);

		baseClass.stepInfo("Verify Disable Remark Warning Message");
		docView.verifyDisableRemarkWarningMessage();

		baseClass.stepInfo("Verify weather delete and edit fields are not enabled.");
		docView.verifyDeleteAndEditFieldsAreNotEnabled();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId);

		baseClass.stepInfo("verify visibility of edited remark after reload the document in first tab");
		docView.verifyRemarkIsAdded(remark);

		baseClass.stepInfo("Log out");
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @testCase Id:51532 Verify that when two different users adds highlighting to
	 *           the same record successfully
	 * @description Verify that when two different users adds highlighting to the
	 *              same record successfully
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-51532", alwaysRun = true, groups = { "regression" })
	public void verifyHighlightingOnDifferentTabsOnSameBrowserDifferentUsers()
			throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51532 sprint 11");
		baseClass.stepInfo(
				"###  Verify that when two different users adds highlighting to the same record successfully ###");
		DocViewPage docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.testData1);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();

//		Added
		driver.waitForPageToBeReady();
		String currentUrl = driver.getUrl();

		DocViewMetaDataPage docVIewMetaData = new DocViewMetaDataPage(driver);

		baseClass.stepInfo("Opening second tab");
		docVIewMetaData.openDuplicateTabOfAlreadyOpenedTab();

		baseClass.stepInfo("Switching to second tab");
		String parentWindowHandle = reusableDocView.switchTochildWindow();
		String childWindowHandle = driver.getWebDriver().getWindowHandle();

		baseClass.stepInfo("Getting : " + currentUrl + " url in second tab");
		driver.getWebDriver().get(currentUrl);

		baseClass.impersonateRMUtoReviewer();

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.testData1);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();

		baseClass.stepInfo("Perform this page annotation");
		baseClass.waitTime(4);
		docView.performNonAudioAnnotation();

		baseClass.stepInfo("Get annotation count");
		int previousAnnotationCount = docView.getAppiedAnnotationCount();

		baseClass.stepInfo("Switch to parent window");
		driver.switchTo().window(parentWindowHandle);

		driver.Navigate().refresh();
		baseClass.stepInfo("Click on redaction icon");
		baseClass.waitForElement(docView.redactionIcon());
		docView.redactionIcon().waitAndClick(5);

		baseClass.stepInfo("Verify Disable Remark Warning Message");
		docView.verifyDisableAnnotationWarningMessageAndSubMenu();

		baseClass.stepInfo("Verify annotation is added to document.");
		docView.verifyAnnotationAddedToDocument(previousAnnotationCount);

		baseClass.stepInfo("Switch to child window");
		driver.switchTo().window(childWindowHandle);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		driver.waitForPageToBeReady();
		baseClass.waitTime(4);
		baseClass.stepInfo("Verify annotation is added to document.");
		docView.verifyAddedAnnotationToDocument(previousAnnotationCount);

		baseClass.stepInfo("Switch to parent window from child window");
		reusableDocView.childWindowToParentWindowSwitching(parentWindowHandle);

		baseClass.stepInfo("Log out");
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @testCase Id:51533 Verify that when two different users edits highlighting to
	 *           the same record successfully.
	 * @description Verify that when two different users edits highlighting to the
	 *              same record successfully.
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-51533", alwaysRun = true, groups = { "regression" })
	public void verifyEditHighlightingOnDifferentTabsOnSameBrowserDifferentUsers()
			throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51533  sprint 11");
		baseClass.stepInfo(
				"### Verify that when two different users edits highlighting to the same record successfully ###");
		DocViewPage docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.testData1);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();

		String currentUrl = driver.getUrl();

		DocViewMetaDataPage docVIewMetaData = new DocViewMetaDataPage(driver);

		baseClass.stepInfo("Opening second tab");
		docVIewMetaData.openDuplicateTabOfAlreadyOpenedTab();

		baseClass.stepInfo("Switching to second tab");
		String parentWindowHandle = reusableDocView.switchTochildWindow();

		baseClass.stepInfo("Getting : " + currentUrl + " url in second tab");
		driver.getWebDriver().get(currentUrl);

		baseClass.impersonateRMUtoReviewer();

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.testData1);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();

		baseClass.stepInfo("Perform this page annotation");
		docView.performNonAudioAnnotation();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Edit Annotation Layer Of Current Document");
		docView.editAnnotationLayer();

		baseClass.stepInfo("Get annotation count");
		int previousAnnotationCount = docView.getAppiedAnnotationCount();

		baseClass.stepInfo("Switch to parent window from child window");
		reusableDocView.childWindowToParentWindowSwitching(parentWindowHandle);

		baseClass.stepInfo("Click on redaction icon");
		docView.redactionIcon().Click();

		baseClass.stepInfo("Verify Disable Remark Warning Message");
		docView.verifyDisableAnnotationWarningMessageAndSubMenu();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Verify annotation is added to document.");
		docView.verifyAddedAnnotationToDocument(previousAnnotationCount);

		baseClass.stepInfo("Log out");
		loginPage.logout();
	}

	/*
	 *
	 * @Author : Brundha
	 * 
	 * @Testcase id : 52204 - Verify that text redaction is working proper in Batch
	 * Print.
	 */
	@Test(description = "RPMXCON-52204", groups = { "regression" })
	public void verifyTheBatchSprintGeneration() throws Exception {
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52204 from Docview Component");
		baseClass.stepInfo("Verify that text redaction is working proper in Batch Print.");

		String foldername = "folder" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		RedactionPage redactionpage = new RedactionPage(driver);

//		Added
		redactionpage.navigateToRedactionsPageURL();

		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("View in doc view");
		sessionSearch.ViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		docViewRedactions.selectDoc1();
		driver.waitForPageToBeReady();
		docViewRedactions.RedactTextInDocView(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		// create folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// Adding folder to bulkfolder
		sessionSearch.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		BatchPrintPage batchPrint = new BatchPrintPage(driver);

		baseClass.stepInfo("Navigate to Batch Sprint and select the folder");
		batchPrint.BatchPrintWithSourceSelection(foldername);

		baseClass.stepInfo("filling Slipsheet tab,Branding,Export format tab in Batch Sprint and Generate");
		batchPrint.FillingBatchRedactionAndverifyingTheDownloadInBatchSprint("CustodianName");

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		batchPrint = new BatchPrintPage(driver);
		baseClass.stepInfo("Navigate to Batch Sprint and select the folder");
		batchPrint.BatchPrintWithSourceSelection(foldername);

		baseClass.stepInfo("filling Slipsheet tab,Branding,Export format tab in Batch Sprint and Generate");
		batchPrint.FillingBatchRedactionAndverifyingTheDownloadInBatchSprint("CustodianName");
		loginPage.logout();

	}

	/**
	 * @author Gopinath
	 * @testCase Id:51534 Verify that when two different users deletes highlighting
	 *           to the same record successfully.
	 * @description Verify that when two different users deletes highlighting to the
	 *              same record successfully.
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-51534", alwaysRun = true, groups = { "regression" })
	public void verifyDeleteHighlightingOnDifferentTabsOnSameBrowserDiffUsers()
			throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51534  sprint 11");
		baseClass.stepInfo(
				"### Verify that when two different users deletes highlighting to the same record successfully ###");
		DocViewPage docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.testData1);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();

		driver.waitForPageToBeReady();
		String currentUrl = driver.getUrl();

		DocViewMetaDataPage docVIewMetaData = new DocViewMetaDataPage(driver);

		baseClass.stepInfo("Opening second tab");
		docVIewMetaData.openDuplicateTabOfAlreadyOpenedTab();

		baseClass.stepInfo("Switching to second tab");
		String parentWindowHandle = reusableDocView.switchTochildWindow();

		baseClass.stepInfo("Getting : " + currentUrl + " url in second tab");
		driver.getWebDriver().get(currentUrl);
		driver.waitForPageToBeReady();

		baseClass.impersonateRMUtoReviewer();

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.testData1);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();

		baseClass.stepInfo("Perform this page annotation");
		docView.performNonAudioAnnotation();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Delete added annotation.");
		driver.waitForPageToBeReady();
		docView.deleteAddedAnnotaion();

		baseClass.stepInfo("Get annotation count");
		driver.waitForPageToBeReady();
		int previousAnnotationCount = docView.getAppiedAnnotationCount();

		baseClass.stepInfo("Switch to parent window from child window");
		reusableDocView.childWindowToParentWindowSwitching(parentWindowHandle);

		baseClass.stepInfo("Click on redaction icon");
		driver.waitForPageToBeReady();
		docView.redactionIcon().Click();

		baseClass.stepInfo("Verify Disable Remark Warning Message");
		docView.verifyDisableAnnotationWarningMessageAndSubMenu();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify annotation is added to document.");
		docView.verifyAnnotationsToDocument(previousAnnotationCount);

		baseClass.stepInfo("Log out");
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @testCase Id:51537 Verify that same user with two different tabs in the same
	 *           browser, and confirm that able to delete highlighting to the same
	 *           records successfully.
	 * @description Verify that same user with two different tabs in the same
	 *              browser, and confirm that able to delete highlighting to the
	 *              same records successfully.
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-51537", alwaysRun = true, groups = { "regression" })
	public void verifyDeleteHighlightingOnDifferentTabsOnSameBrowserSameUser()
			throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51537  sprint 11");
		baseClass.stepInfo(
				"### Verify that same user with two different tabs in the same browser, and confirm that able to delete highlighting to the same records successfully ###");
		DocViewPage docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.testData1);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();

//		Added
		driver.waitForPageToBeReady();
		String currentUrl = driver.getUrl();

		DocViewMetaDataPage docVIewMetaData = new DocViewMetaDataPage(driver);

		baseClass.stepInfo("Opening second tab");
		docVIewMetaData.openDuplicateTabOfAlreadyOpenedTab();

		baseClass.stepInfo("Switching to second tab");
		String parentWindowHandle = reusableDocView.switchTochildWindow();

		baseClass.stepInfo("Getting : " + currentUrl + " url in second tab");
		driver.getWebDriver().get(currentUrl);

		baseClass.stepInfo("Perform this page annotation");
		docView.performNonAudioAnnotation();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete added annotation.");
		docView.deleteAddedAnnotaion();

		baseClass.stepInfo("Get annotation count");
		int previousAnnotationCount = docView.getAppiedAnnotationCount();

		baseClass.stepInfo("Switch to parent window from child window");
		reusableDocView.childWindowToParentWindowSwitching(parentWindowHandle);

		baseClass.stepInfo("Click on redaction icon");
		docView.redactionIcon().Click();

		baseClass.stepInfo("Verify Disable Remark Warning Message");
		docView.verifyDisableAnnotationWarningMessageAndSubMenu();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Verify annotation is added to document.");
		docView.verifyAnnotationsToDocument(previousAnnotationCount);

		baseClass.stepInfo("Log out");
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @testCase Id:51535 Verify that same user with two different tabs in the same
	 *           browser, and confirm that able to add highlighting to the same
	 *           records successfully, and confirm the XML nodes are all properly
	 *           reflected in the XML
	 * @description : To Verify that same user with two different tabs in the same
	 *              browser, and confirm that able to add highlighting to the same
	 *              records successfully, and confirm the XML nodes are all properly
	 *              reflected in the XML
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-51535", alwaysRun = true, groups = { "regression" })
	public void verifyHighlightingOnDifferentTabsOfSameBrowser() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51535 sprint 11");
		baseClass.stepInfo(
				"###Verify that same user with two different tabs in the same browser, and confirm that able to add highlighting to the same records successfully, and confirm the XML nodes are all properly reflected in the XML###");
		DocViewPage docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaData = new DocViewMetaDataPage(driver);
		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);
		DocViewMetaDataPage docVIewMetaData = new DocViewMetaDataPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password, Input.additionalDataProject);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.testTenthDocId);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();

//		Added
		driver.waitForPageToBeReady();
		String currentUrl = driver.getUrl();

		baseClass.stepInfo("Opening second tab");
		docVIewMetaData.openDuplicateTabOfAlreadyOpenedTab();

		baseClass.stepInfo("Switching to second tab");
		String parentWindowHandle = reusableDocView.switchTochildWindow();
		String childWindowHandle = driver.getWebDriver().getWindowHandle();

		baseClass.stepInfo("Getting : " + currentUrl + " url in second tab");
		driver.getWebDriver().get(currentUrl);

		baseClass.stepInfo("Adding Annotation to document");
		docViewMetaData.performAnnotationByRectangle(10, 10);

		baseClass.stepInfo("Switchimg to first window");
		driver.switchTo().window(parentWindowHandle);

		baseClass.stepInfo("Verify Duplicate redaction/highlighting/Annotation Warning");
		docView.verifydDuplicateRedactionWarningMessage();

		baseClass.stepInfo("Select Doc To View In DocView Panal");
		docView.selectDocToViewInDocViewPanal(Input.testTenthDocId);

		baseClass.stepInfo("verify visibility of added Annotation in document in first window");
		docView.verifyAddedAnnotation();

		baseClass.stepInfo("Switch to parent window from child window");
		reusableDocView.childWindowToParentWindowSwitching(childWindowHandle);

		baseClass.stepInfo("verify visibility of added Annotation in document in second window");
		docView.verifyAddedAnnotation();

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Removing annotation in Document");
		docViewMetaData.unTagAnnotationOfDocument();
	}

	/**
	 * @author Gopinath
	 * @testCase Id:51536 Verify that same user with two different tabs in the same
	 *           browser, and confirm that able to edit highlighting to the same
	 *           records successfully, and confirm the XML nodes are all properly
	 *           reflected in the XML
	 * @description : To Verify that same user with two different tabs in the same
	 *              browser, and confirm that able to edit highlighting to the same
	 *              records successfully, and confirm the XML nodes are all properly
	 *              reflected in the XML
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-51536", alwaysRun = true, groups = { "regression" })
	public void verifyEditHighlightingOnDifferentTabsOfSameBrowser() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51536 sprint 11");
		baseClass.stepInfo(
				"###Verify that same user with two different tabs in the same browser, and confirm that able to edit highlighting to the same records successfully, and confirm the XML nodes are all properly reflected in the XML###");
		DocViewPage docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaData = new DocViewMetaDataPage(driver);
		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);
		DocViewMetaDataPage docVIewMetaData = new DocViewMetaDataPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.testTenthDocId);

		baseClass.stepInfo("view in docview");
		session.ViewInDocView();

//		Addded
		driver.waitForPageToBeReady();

		String currentUrl = driver.getUrl();

		baseClass.stepInfo("Opening second tab");
		docVIewMetaData.openDuplicateTabOfAlreadyOpenedTab();

		baseClass.stepInfo("Switching to second tab");
		String parentWindowHandle = reusableDocView.switchTochildWindow();
		String childWindowHandle = driver.getWebDriver().getWindowHandle();

		baseClass.stepInfo("Getting : " + currentUrl + " url in second tab");
		driver.getWebDriver().get(currentUrl);

		baseClass.stepInfo("Adding Annotation to document");
		docViewMetaData.performAnnotationByRectangle(10, 10);

		baseClass.stepInfo("Edit highligting");
		docViewMetaData.moveAnnotationByRectangle();

		baseClass.stepInfo("Switchimg to first window");
		driver.switchTo().window(parentWindowHandle);

		baseClass.stepInfo("Verify Duplicate redaction/highlighting/Annotation Warning");
		docView.verifydDuplicateRedactionWarningMessage();

		baseClass.stepInfo("Select Doc To View In DocView Panal");
		docView.selectDocToViewInDocViewPanal(Input.testTenthDocId);

		baseClass.stepInfo("verify visibility of edit Annotation in document in first window");
		docView.verifyAddedAnnotation();

		baseClass.stepInfo("Switch to parent window from child window");
		reusableDocView.childWindowToParentWindowSwitching(childWindowHandle);

		baseClass.stepInfo("verify visibility of edit Annotation in document in second window");
		docView.verifyAddedAnnotation();

		baseClass.stepInfo("Removing annotation in Document");
		docViewMetaData.unTagAnnotationOfDocument();
	}

	/*
	 * Author : Brundha Created date: NA Modified date: NA Modified by:NA TestCase
	 * id :RPMXCON- 50959 Description:To verify that after closing child window, it
	 * should redirect to Parent Window.
	 */
	@Test(description = "RPMXCON-50959", alwaysRun = true, groups = { "regression" })
	public void verifyingNavigationFromChildToParentWindow() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-50959  from Docview Component");
		baseClass.stepInfo("####To verify that after closing child window, it should redirect to Parent Window. ####");

		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		DocViewPage doc = new DocViewPage(driver);
		baseClass.stepInfo("Verifying navigation of Child window to parent window");
		doc.verifyingSwitchingFromChildWindowToParentWindow();

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		doc = new DocViewPage(driver);
		baseClass.stepInfo("Verifying navigation of Child window to parent window");
		doc.verifyingSwitchingFromChildWindowToParentWindow();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		doc = new DocViewPage(driver);
		baseClass.stepInfo("Verifying navigation of Child window to parent window");
		doc.verifyingSwitchingFromChildWindowToParentWindow();
		loginPage.logout();

	}

	/**
	 * Author : Brundha Created date: NA Modified date: NA Modified by:NA TestCase
	 * id :RPMXCON- 51436 Description: Verify that if the document Text is being
	 * presented, the "X" icon with the accompanying mouse over tool tip must appear
	 */
	@Test(description = "RPMXCON-51436", alwaysRun = true, groups = { "regression" })

	public void verifyXIconAndToolTipInDocView() throws Exception {

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-51436 from docview Component");
		baseClass.stepInfo(
				"#### Verify that if the document native is being presented, the 'X' icon with the accompanying mouse over tool tip must appear ####");

		String DocId = Input.testTenthDocId;
		String ExpectedText = "Text file variant of the document being displayed";
		baseClass.stepInfo("Basic meta data search");
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		sessionSearch.basicContentSearch(DocId);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		DocViewPage docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Verify Text Document Tooltip");
		docView.verifyingToolTipPopupMessage(DocId, ExpectedText);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(DocId);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Verify Text Document Tooltip");
		docView.verifyingToolTipPopupMessage(DocId, ExpectedText);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(DocId);

		baseClass.stepInfo("Navigating to docview page");
		sessionSearch.ViewInDocView();

		docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Verify Text Document Tooltip");
		docView.verifyingToolTipPopupMessage(DocId, ExpectedText);
		loginPage.logout();
	}

	/**
	 * Author : Steffy Created date: NA Modified date: NA Modified by:NA TestCase id
	 * : RPMXCON-50957 - To verify that user can redirect doc view page from Doc
	 * List->'Back to Source stabilization done
	 */
	@Test(description = "RPMXCON-50957", alwaysRun = true, groups = { "regression" })
	public void verifyBackToSourceBtnRedirectToDocViewPage() throws Exception {
		baseClass = new BaseClass(driver);
		docView = new DocViewPage(driver);
		loginPage = new LoginPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		DocListPage docListPage = new DocListPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-50957");
		baseClass.stepInfo("To verify that user can redirect doc view page from Doc List->'Back to Source");
		baseClass.stepInfo("Search the documents and Navigate to Docview Page");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testTenthDocId);
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();

		driver.scrollPageToTop();
		baseClass.stepInfo("Selecting the docs from mini doc list");
		docView.selectDocIdInMiniDocList(Input.testTenthDocId);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Selecting the a doc from analytical panel abd viewing in doc list page");
		docView.performViewInDocListConceputualSignledocs();

		baseClass.stepInfo("Click On Back to Source button");
		baseClass.waitForElement(docListPage.getBackToSourceBtn());
		docListPage.getBackToSourceBtn().Click();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verifying whether the user is navigated back to doc view page successfully");
		String actualURL = driver.getUrl();
		if (actualURL.contains("DocView")) {
			baseClass.passedStep("Navigated to docview page successfuly after Back to source button is clicked");
		} else {
			baseClass.failedStep("Failed to navigate to docview page");
		}

		String docAfterNavigation = docView.getDocView_CurrentDocId().getText();

		softAssertion.assertEquals(Input.testTenthDocId, docAfterNavigation);
		softAssertion.assertAll();

		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TESTCASE No:RPMXCON-51268 : User can see the production option in the drop
	 *           down selection of Images tab when document generated as part of
	 *           production with/without redaction.
	 * @Description: User can see the production option in the drop down selection
	 *               of Images tab when document generated as part of production
	 *               with/without redaction.
	 */
	@Test(description = "RPMXCON-51268", groups = { "regression" })
	public void verifyProductionOptionFromImageTabDropDown() throws Exception {
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-51268 -Production Sprint 12");
		baseClass.stepInfo(
				"User can see the production option in the drop down selection of Images tab when document generated as part of production with/without redaction");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

//		Modified
		baseClass.selectproject(Input.projectName01);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(Input.telecaSearchString);

		baseClass.stepInfo("View in doc view");
		sessionSearch.ViewInDocView();

		docViewMetaDataPage = new DocViewMetaDataPage(driver);

		baseClass.waitTime(4);
		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		baseClass.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15, Input.defaultRedactionTag);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Bulk Tag Existing");
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		baseClass.stepInfo("Add New Production");
//		Added
		page.navigateToProductionPage();

		page.addANewProduction(productionname);

		baseClass.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		baseClass.stepInfo("Select Priv Docs In Tiff Section");
		page.selectPrivDocsInTiffSection(tagname);

		baseClass.stepInfo("Navigate to next section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Document Selection With Tag");
		page.fillingDocumentSelectionWithTag(tagname);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		baseClass.stepInfo("Verify Sub Folder Toggle");
		page.verifySubFolderToggle();

		baseClass.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		baseClass.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		baseClass.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		baseClass.stepInfo("Filling Generate Page With Continue Generation Popup");
		page.fillingGeneratePageWithContinueGenerationPopup();

		String productionName = page.getGeneratedProductionName();

		baseClass.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("View in doc view");

		// Modified
		sessionSearch.ViewInDocViewWithoutPureHit();

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Verify Completed Production Name Dispalyed On Image Tab");
		driver.waitForPageToBeReady();
		docView.verifyCompletedProductionNameDispalyedOnImageTab(productionName);

		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @TestCase Id: 51034 -Verify user can see the keywords highlighted in doc view
	 *           based on the assigned keyword group and color to the security
	 *           group.
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-51034", groups = { "regression" })
	public void verifySearchTermAndAllKeywordsDisplayedOnPersistentPanal() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51034");
		String keyword = "es";
		String colour1 = "Gold";
		String rgbCode1 = "rgb(255, 215, 0)";
		String HaxCode1 = "#ffd700";
		String keywordName1 = "key" + Utility.dynamicNameAppender();
		baseClass.stepInfo(
				"#### Verify user can see the keywords highlighted in doc view based on the assigned keyword group and color to the security group ####");

		KeywordPage keywordPage = new KeywordPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Add keyword one");
		keywordPage.addKeywordWithoutFullScreen(keywordName1, keyword, colour1);

		baseClass.stepInfo("Get All Keywords in keywords lsit table");
		List<String> keywords = keywordPage.getAllKeywords();

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		baseClass.stepInfo("Basic Basic content search");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Navigate to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(Input.searchString1);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify Persistant Hits With Doc View");
		docView.verifyPersistantHitsWithDocView(keywords);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Verify persistant hit for keyword");
		docView.persistenHitWithSearchString(keyword);

		baseClass.stepInfo("verify highlight keyword in document");
		docView.verifyKeywordHighlightedOnDocViewwithKeywordColour(rgbCode1, HaxCode1);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keywordName1);
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @TestCase Id: 51552 - Verify highlighting with Persistent KW Groups as well
	 *           as Ad Hoc Searching.
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-51552", groups = { "regression" })
	public void verifySearchTermAndKeywordDisplayedOnPersistentPanal() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51552");
		String keyword = "es";
		String colour1 = "Gold";
		String rgbCode1 = "rgb(255, 215, 0)";
		String HaxCode1 = "#ffd700";
		String keywordName1 = "key" + Utility.dynamicNameAppender();
		baseClass.stepInfo(
				"#### Verify user can see the keywords highlighted in doc view based on the assigned keyword group and color to the security group ####");

		KeywordPage keywordPage = new KeywordPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Add keyword one");
		keywordPage.addKeywordWithoutFullScreen(keywordName1, keyword, colour1);

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		baseClass.stepInfo("Basic Basic content search");
		session.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Navigate to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(Input.searchString2);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Verify persistant hit for keyword");
		docView.persistenHitWithSearchString(keyword);

		baseClass.stepInfo("verify highlight keyword in document");
		docView.verifyKeywordHighlightedOnDocViewwithKeywordColour(rgbCode1, HaxCode1);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keywordName1);
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @TestCase_Id : 51878 - Verify that Action > Code Same As works fine when all
	 *              records in the reviewer's batch are in mixed state but records
	 *              that are in Completed state are also present.
	 * @description : Verify that Action > Code Same As works fine when all records
	 *              in the reviewer's batch are in mixed state but records that are
	 *              in Completed state are also present.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51878", alwaysRun = true, groups = { "regression" })
	public void verifyChainIconDisplayedByCodeSameAsForSelecedDoc() throws InterruptedException {
		String AssignName = Input.randomText + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51878 sprint 12");
		baseClass.stepInfo(
				"#### Verify that Action > Code Same As works fine when all records in the reviewer's batch are in mixed state but records that are in Completed state are also present ####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo(" Basic content search");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Create bulk assign with new assignment with persistant hit.");
		session.bulkAssignWithNewAssignmentWithPersistantHit(AssignName, Input.codingFormName);

		baseClass.stepInfo("Edit assignment");
		assignmentPage.editAssignment(AssignName);

		baseClass.stepInfo("Reviews adding and distributing to Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("Select Assignment By Reviewer");
		assignmentPage.SelectAssignmentByReviewer(AssignName);

		baseClass.stepInfo("Enable toogle to show completed documents in mini doc list.");
		docView.showCompletedDocumentsConfigureMiniDocList();

		baseClass.stepInfo("Select uncompleted documents check boxes");
		driver.waitForPageToBeReady();
		docView.selectingUncompletedDocumetsCheckBoxes(3);

		baseClass.stepInfo("Performing code same as action.");
		docView.performActionCodeSameAs();

		baseClass.stepInfo("Verify Chain Icon Is Displayed After Code Same As Operation.");
		docView.verifyChainIconIsDisplayedAfterCodeSameAsOperation(3);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Navigate To Assignments Page");
		assignmentPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assignmentPage.deleteAssignment(AssignName);

	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * @TestCase id : 51979 - Verify that search term highlighting and keyword
	 *           highlighting on Searchable PDF generated from the Stitched TIFF.
	 * @Description : Verify that search term highlighting and keyword highlighting
	 *              on Searchable PDF generated from the Stitched TIFF.
	 */
	@Test(description = "RPMXCON-51979", alwaysRun = true, groups = { "regression" }, enabled = true)
	public void verifySearchTermAndKeywordHighlightedOnDocView() throws Exception {
		baseClass = new BaseClass(driver);
		String keyword = "es";
		String colour1 = "Gold";
		String rgbCode1 = "rgb(255, 215, 0)";
		String HaxCode1 = "#ffd700";
		String tiffDocId = "ID00005041";
		String keywordName1 = "key" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-51979 Sprint 12");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo(
				"#### Verify that search term highlighting and keyword highlighting on Searchable PDF generated from the Stitched TIFF. ####");

		KeywordPage keywordPage = new KeywordPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Add keyword one");
		keywordPage.addKeyword(keywordName1, keyword, colour1);

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		baseClass.stepInfo("Basic Basic content search");
		session.basicContentSearch(tiffDocId);

		baseClass.stepInfo("Navigate to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(tiffDocId);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Verify persistant hit for keyword");
		docView.persistenHitWithSearchString(keyword);

		baseClass.stepInfo("verify highlight keyword in document");
		docView.verifyKeywordHighlightedwithKeywordColour(rgbCode1, HaxCode1);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keywordName1);
		loginPage.logout();
	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * @TestCase id : 51648 - Verify that if the file size is blank and # of pages >
	 *           500, then set NearNativeReady = 0 and error document should be
	 *           displayed on doc view.
	 * @Description : Verify that if the file size is blank and # of pages > 500,
	 *              then set NearNativeReady = 0 and error document should be
	 *              displayed on doc view.
	 */
	@Test(description = "RPMXCON-51648", alwaysRun = true, groups = { "regression" }, enabled = true)
	public void verifyDocPagesAreNotLoadedProperlyForAbove500PagesDoc() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51648 Sprint 12");
		utility = new Utility(driver);
		String above500PagesDocId = Input.d500PagesDocId;
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo(
				"#### Verify that if the file size is blank and # of pages > 500, then set NearNativeReady = 0 and error document should be displayed on doc view. ####");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.selectproject(Input.additionalDataProject);

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		baseClass.stepInfo("Basic Basic content search");
		session.basicContentSearch(above500PagesDocId);

		baseClass.stepInfo("Navigate to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("Verify total page count.");
		docView.verifyTotalPagesOfDocumentCountGreaterThan500();

		loginPage.logout();
	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * @TestCase id : 51647 - Verify that if # of pages is blank, THEN If file size
	 *           is < 10MB, then nearNativeReady = 1 and document should be
	 *           displayed successfully.
	 * @Description : Verify that if # of pages is blank, THEN If file size is <
	 *              10MB, then nearNativeReady = 1 and document should be displayed
	 *              successfully.
	 */
	@Test(description = "RPMXCON-51647", alwaysRun = true, groups = { "regression" }, enabled = true)
	public void verifyDocPagesAreLoadedProperlyByLessthan10MB() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51647 Sprint 12");
		utility = new Utility(driver);
		String lessthan10mbDocID = "ID00000206";
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo(
				"####Verify that if # of pages is blank, THEN If file size is < 10MB, then nearNativeReady = 1 and document should be displayed successfully ####");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.selectproject(Input.additionalDataProject);

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		baseClass.stepInfo("Basic Basic content search");
		session.basicContentSearch(lessthan10mbDocID);

		baseClass.stepInfo("Navigate to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("Verify document loaded successfully.");
		docView.verifyDocLoadedSuccessfully();

		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @TestCase Id:51090-Verify on click of the Translations tab, presence of a
	 *           translated version of the document
	 * @Description :Verify on click of the Translations tab, presence of a
	 *              translated version of the document.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51090", alwaysRun = true, groups = { "regression" })
	public void verifyTranslationTabPresenceOfTranlationVersion() throws InterruptedException {

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51090 of Sprint 12");

		baseClass.stepInfo(
				"#### Verify on click of the Translations tab, presence of a translated version of the document ####");

		DocViewPage docView = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("searching document for assignmnet creation");
		sessionSearch.basicContentSearch(Input.translationDocId);

		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Click On Translation Tab");
		docView.clickOnTranslationTab();

		baseClass.stepInfo("Verify traslation is avaliable in translation drop down.");
		docView.verifyTranslationDisplayedTranslationsDropdown();
		loginPage.logout();
	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * @TestCase id : 48796 - Verify Search Term highlighting is working for
	 *           Searchable PDF.
	 * @Description : Verify Search Term highlighting is working for Searchable PDF.
	 */
	@Test(description = "RPMXCON-48796", alwaysRun = true, groups = { "regression" })
	public void verifySearchTermInPersistatHitPanelOnDocView() throws Exception {
		baseClass = new BaseClass(driver);
		String pdfDocId = "ID00001464";
		baseClass.stepInfo("Test case Id: RPMXCON-48796 Sprint 12");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo("#### Verify Search Term highlighting is working for Searchable PDF ####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic Basic content search");
		session.basicContentSearch(pdfDocId);

		baseClass.stepInfo("Navigate to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("Select the document from minidoc list with docId or row number to load on docview panal");
		docView.selectDocToViewInDocViewPanal(pdfDocId);

		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(pdfDocId);

		loginPage.logout();
	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * @TestCase id : 51983 - Verify Keyword highlighting is working for Searchable
	 *           PDF.
	 * @Description : Verify Keyword highlighting is working for Searchable PDF
	 */
	@Test(description = "RPMXCON-51983", alwaysRun = true, groups = { "regression" })
	public void verifySearchTermAndKeywordHighlightedOnPersistPanel() throws Exception {
		baseClass = new BaseClass(driver);
		String keyword = "es";
		String colour1 = "Gold";
		String rgbCode1 = "rgb(255, 215, 0)";
		String HaxCode1 = "#ffd700";
		String pdfDocId = "ID00001464";
		String keywordName1 = "key" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-51983 Sprint 12");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo("#### Verify Keyword highlighting is working for Searchable PDF ####");

		KeywordPage keywordPage = new KeywordPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Add keyword one");
		keywordPage.addKeywordWithoutFullScreen(keywordName1, keyword, colour1);

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		baseClass.stepInfo("Basic Basic content search");
		session.basicContentSearch(pdfDocId);

		baseClass.stepInfo("Navigate to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("Select the document from minidoc list with docId or row number to load on docview panal");
		docView.selectDocToViewInDocViewPanal(pdfDocId);

		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(pdfDocId);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Select the document from minidoc list with docId or row number to load on docview panal");
		docView.selectDocToViewInDocViewPanal(pdfDocId);

		baseClass.stepInfo("Verify persistant hit for keyword");
		docView.persistenHitWithSearchString(keyword);

		baseClass.stepInfo("verify highlight keyword in document");
		docView.verifyKeywordHighlightedwithKeywordColour(rgbCode1, HaxCode1);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keywordName1);
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @throws InterruptedException
	 * @TestCase Id:50808 verify when user clicks document pagination option from
	 *           document view panel of doc view page
	 * @Description:To To verify when user clicks document pagination option from
	 *                 document view panel of doc view page
	 */
	@Test(description = "RPMXCON-50808", alwaysRun = true, groups = { "regression" })
	public void verifyDocViewDocPagnation() throws InterruptedException {
		baseClass = new BaseClass(driver);
		String rowNumber = "2";
		baseClass.stepInfo("RPMXCON-50808 Sprint 12");
		baseClass.stepInfo(
				"#### To verify when user clicks document pagination option from document view panel of doc view page");
		SessionSearch session = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic Content Search");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("view doc in docView");
		session.ViewInDocView();

		docView.selectDocToViewInDocViewPanal(rowNumber);
		baseClass.stepInfo("Verify docView page next(>) navigation");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		docView.clickOnPageNextButton();
		docView.clickOnPageNextButton();

		baseClass.stepInfo("verify document page last(>>) button");
		docView.clickOnPageLastButton();

		baseClass.stepInfo("verify document page previous(<) button");
		docView.clickOnDocPagePreviousButton();

		baseClass.stepInfo("verify document page first(<<) button");
		docView.clickOnDocPageFirstButton();

	}

	/**
	 * @author Gopinath
	 * @TestCase Id:51732 Verify that the spinning wheel is gone when the two docs
	 *           having large number of pages being compared are presented in the
	 *           viewer in near dupe comparison window
	 * @Description:To Verify that the spinning wheel is gone when the two docs
	 *                 having large number of pages being compared are presented in
	 *                 the viewer in near dupe comparison window
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51732", alwaysRun = true, groups = { "regression" })
	public void verifyComparisonWindowLoadingStatus() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo(
				"###Verify that the spinning wheel is gone when the two docs having large number of pages being compared are presented in the viewer in near dupe comparison window###");
		baseClass.stepInfo("Test case Id: RPMXCON-51732 Sprint 12");
		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic Basic content search");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("select near dupe docs and Navigate to  DocView page ");
		docView.selectNearDupePureHit();
		session.ViewNearDupeDocumentsInDocView();

		baseClass.stepInfo("verifying comparison window neardupe spinnig wheel");
		docView.nearDupeComparisonWindowLodingVerification();
	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * @TestCase id : 51650 - Verify that if the file size is blank and # of pages
	 *           is blank, and actual doc pages < 500 then set NearNativeReady = 1
	 *           and document should be displayed on doc view.
	 * @Description : Verify that if the file size is blank and # of pages is blank,
	 *              and actual doc pages < 500 then set NearNativeReady = 1 and
	 *              document should be displayed on doc view
	 **/
	@Test(description = "RPMXCON-51650", alwaysRun = true, groups = { "regression" }, enabled = true)
	public void verifyDocPagesLoadedProperlyForLessThan500PagesDoc() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51650 Sprint 13");
		utility = new Utility(driver);
		String lessThan500PagesDocId = Input.lessThan500PagesDocId;
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo(
				"#### Verify that if the file size is blank and # of pages is blank, and actual doc pages < 500 then set NearNativeReady = 1 and document should be displayed on doc view ####");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.stepInfo("Basic Basic content search");
		session.basicMetaDataSearch(Input.sourceDocIdSearch, null, lessThan500PagesDocId, null);

		baseClass.stepInfo("Navigate to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("Verify total page count.");
		docView.verifyTotalPagesOfDocumentCountLessThan500();
		loginPage.logout();
	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * @TestCase id : 51649 - Verify that if the file size is blank and # of pages
	 *           <= 500, then set NearNativeReady = 1 and document should be loaded
	 *           successfully.
	 * @Description : Verify that if the file size is blank and # of pages <= 500,
	 *              then set NearNativeReady = 1 and document should be loaded
	 *              successfully.
	 **/
	@Test(description = "RPMXCON-51649", alwaysRun = true, groups = { "regression" })
	public void verifyDocPagesLoadedProperlyForLessThanOrEqual500PagesDoc() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51649 Sprint 13");
		utility = new Utility(driver);
		// String d500PagesDocId = Input.d500PagesDocId;
		String d500PagesDocId = "ID00001785";
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo(
				"#### Verify that if the file size is blank and # of pages is blank, and actual doc pages < 500 then set NearNativeReady = 1 and document should be displayed on doc view ####");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		baseClass.selectproject(Input.additionalDataProject);

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		baseClass.stepInfo("Basic Basic content search");
		session.basicContentSearch(d500PagesDocId);

		baseClass.stepInfo("Navigate to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo(
				"Verify total page count which is less than or equal to 500 and doc view document is loaded proper or not.");
		docView.verifyTotalPagesOfDocumentCountLessThanOrEqualTo500();
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @TestCase_Id : 52260 : Verify that on deleting the applied redaction tag
	 *              message should be displayed to the user.
	 * @description : Verify that on deleting the applied redaction tag message
	 *              should be displayed to the user.
	 */
	@Test(description = "RPMXCON-52260", groups = { "regression" })
	public void verifyErrorMsgDisplayedBYDeletingAppliedRedactionTag() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-52260 Production-sprint:13");
		baseClass.stepInfo(
				"#### Verify that on deleting the applied redaction tag message should be displayed to the user. ####");
		String redactiontag1 = Input.randomText + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.stepInfo("Navigate To Redactions Page URL");
		redactionpage.navigateToRedactionsPageURL();

		baseClass.stepInfo("Manage Redaction Tags Page");
		redactionpage.manageRedactionTagsPage(redactiontag1);

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		search.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic Content Search");
		search.basicContentSearch(Input.testData1);

		baseClass.stepInfo("View in doc view");
		search.ViewInDocView();

		docViewMetaDataPage = new DocViewMetaDataPage(driver);

		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		redact = new DocViewRedactions(driver);

		baseClass.stepInfo("Set this page reduct in doc");
		redact.performThisPageRedaction(redactiontag1);

		redactionpage = new RedactionPage(driver);
		redactionpage.verifyErrorMessageByDeletingAppliedRedactionTag(redactiontag1);

		loginPage.logout();

	}

	/**
	 * @Author : Baskar date:06/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when RMU impersonates as Reviewer and complete same as
	 *              last doc when preceding document is completed
	 */
	@Test(description = "RPMXCON-51608", enabled = true, groups = { "regression" })
	public void validateAfterImpersonateSameAsLast() throws InterruptedException, AWTException {
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		DocViewPage docViewPage = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51608");
		baseClass.stepInfo("Verify when RMU impersonates as Reviewer and complete "
				+ "same as last doc when preceding document is completed");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();

//		searching document for assignment creation
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignName, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();
		driver.waitForPageToBeReady();
		System.out.println(assignName);
		loginPage.logout();
		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validate complete doc to display uncomplete button
		docViewPage.stampAndCompleteBtnValidation(comment, fieldText, Input.stampSelection);
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:09/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify user can apply coding stamp for the document once
	 *              marked as un-complete in an assignment
	 */
	@Test(description = "RPMXCON-51049", enabled = true, groups = { "regression" })
	public void afterImpersonateCanSaveStamp() throws InterruptedException, AWTException {
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51049");
		baseClass.stepInfo(
				"Verify user can apply coding stamp for the " + "document once marked as un-complete in an assignment");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		String filedText = "stampName" + Utility.dynamicNameAppender();
		String comment = "commentValue" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

//		searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignName, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();
		driver.waitForPageToBeReady();
		System.out.println(assignName);
		baseClass.impersonateRMUtoReviewer();
		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validation of completing document
		docViewPage.validateSavedStampAfterImpersonate(filedText, comment);

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Sakthivel date:30/12/2021 Modified date:NA
	 * @Description :Verify that when user in on Images tab and completes the
	 *              document from coding form child window then should be on Images
	 *              tab for next navigated document
	 */
	@Test(description = "RPMXCON-51920", enabled = true, groups = { "regression" })
	public void verifyImageTabCompleteForNextNavigatedDoc() throws InterruptedException, AWTException {
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();

//		loginPage.logout();
//		 Login As RMU
//		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON- 51920");
		baseClass.stepInfo("Verify that when user in on Images tab and completes the document from coding form "
				+ "child window then should be on Images tab for next navigated document");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.bulkAssign();

		// Assignment creating and saving the assignment
		assignmentPage.assignmentCreation(assignmentName, "Default Project Coding Form");
		baseClass.stepInfo("Assignment is saved succcessfully");

		// Assignment saved and distributing to reviewer
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.stepInfo("distrubuting to reviwer");

		// Logout as Reviewer manager
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection and Reviewer
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Method for viewing doc view images.
		docViewPage.verifyDocViewImages();
		docViewPage.verifyCodingFormChildWindowCursorNavigatedToImageTabDisplayed();
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer'" + Input.rev1password + "'");
	}

	/**
	 * @Author : Sakthivel date:30/12/2021 Modified date:NA
	 * @Description : Verify that when user in on Images tab and completes the
	 *              document from coding form child window after applying stamp then
	 *              should be on Images tab for next navigated document
	 */
	@Test(description = "RPMXCON-51921", enabled = true, groups = { "regression" })
	public void verifyCfSavedStampChildNavigatedDoc() throws InterruptedException, AWTException {
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		// Login As RMU

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON- 51921");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and completes the document from coding form child window after applying stamp"
						+ " then should be on Images tab for next navigated document");

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.bulkAssign();

		// Assignment Saved
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		baseClass.stepInfo("Assignment is saved succcessfully");

		// Assignment saved and distributing to reviewer
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.stepInfo("distrubuting to reviwer");

		// Logout As Review Manager
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Method for viewing doc view images.
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docViewPage.verifyDocViewImages();
		docViewPage.verifyCfStampChildCursorNavigatedToDocViewImage(Input.stampColour, Input.stampColour);

		// Logout As Reviewer
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer'" + Input.rev1password + "'");
		softAssertion.assertAll();
	}

	/**
	 * @Author : Steffy date: 28/01/2021 Modified date: NA Modified by:
	 * @Description:Verify assignment progress bar refreshesh after completing the
	 *                     document same as last prior documents should be completed
	 *                     by clicking complete button after selecting code same as
	 *                     this action. stabilization done
	 */

	@Test(description = "RPMXCON-51275", enabled = true, groups = { "regression" })
	public void verifyAssignmentProgressBarDocCompleteAfterCodeSameAs() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51275");
		baseClass.stepInfo(
				"Verify assignment progress bar refreshesh after completing the document same as last prior documents should be completed by "
						+ "clicking complete button after selecting code same as this action");
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SoftAssert softAssertion = new SoftAssert();
		DocViewPage docViewPage = new DocViewPage(driver);
		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;
		String docTextbox = "assignment click";
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String fieldText = Input.randomText + Utility.dynamicNameAppender();
		String comment = Input.randomText + Utility.dynamicNameAppender();
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

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

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentsPage.SelectAssignmentByReviewer(assname);
		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);
		reusableDocView.editCodingFormAndSaveWithStampColour(fieldText, Input.stampSelection, comment);
		driver.waitForPageToBeReady();
		docViewRedact.getHomeDashBoard().waitAndClick(10);

		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");
			softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}

		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewRedact.getDocView_MiniDoc_Selectdoc(3));
		docViewRedact.getDocView_MiniDoc_Selectdoc(3).waitAndClick(5);
		docViewPage.completeButton();
		baseClass.waitTime(2);
		docViewPage.getCodeSameAsLast().Click();
		;
		docViewRedact.getHomeDashBoard().waitAndClick(10);

		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");
			softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @TestCase Id:51964 Verify that when viewing the document with Hidden
	 *           Properties has "External Link" as well as another hidden property,
	 *           warning message should not be displayed
	 * @Description :To Verify that when viewing the document with Hidden Properties
	 *              has "External Link" as well as another hidden property, warning
	 *              message should not be displayed
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51964", enabled = true, groups = { "regression" })
	public void verifyNowarningMessageForExternalLinkHiddenProp() throws InterruptedException {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		String ExternalLinkDocId = Input.externalLinkDocId;
		String projectName = Input.additionalDataProject;
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		baseClass.selectproject(projectName);

		baseClass.stepInfo("Test case Id: RPMXCON-51964");
		baseClass.stepInfo(
				"###Verify that when viewing the document with Hidden Properties has \"External Link\" as well as another hidden property, warning message should not be displayed####");

		baseClass.stepInfo("basic content search");
		sessionsearch.basicContentSearch(ExternalLinkDocId);

		baseClass.stepInfo("view in docView");
		sessionsearch.viewInDocView();

		baseClass.stepInfo("verify warning message for hidden properties document and metadat value not empty");
		docView.verifyNoWarningMessageForExternalLink(ExternalLinkDocId);
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

}
