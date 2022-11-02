package testScriptsRegressionSprint25;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
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

public class DocView_Redactions_25 {
	
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

	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}
	
	/**
	 * Author : Krishna date: NA Modified date:NA Modified by: Test Case Id: 49074
	 * Verify the Redaction and Highlighting is deleted successfully in DocView
	 */
	@Test(description = "RPMXCON-49074", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyRedactionHighlitingDeletedInDocView() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-49074");
		baseClass.stepInfo("Verify the Redaction and Highlighting is deleted successfully in DocView");
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		DocListPage doclist = new DocListPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");

		baseClass.stepInfo("Clicked on reduction button ");
		baseClass.waitForElement(doclist.getDocIDFromDocView(4));
		doclist.getDocIDFromDocView(4).waitAndClick(5);
		baseClass.waitForElement(docViewMetaDataPage.getRedactIcon());
		driver.waitForPageToBeReady();
		docViewMetaDataPage.getRedactIcon().waitAndClick(15);
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewRedact.thisPageRedaction());
		docViewRedact.thisPageRedaction().waitAndClick(5);
		baseClass.stepInfo("Selected on this page add redaction");
		driver.waitForPageToBeReady();
		docViewRedact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
		baseClass.waitForElement(doclist.getDocIDFromDocView(4));
		doclist.getDocIDFromDocView(4).waitAndClick(5);
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).click();
		actions.build().perform();
		if (docViewRedact.highliteDeleteBtn().Displayed() && docViewRedact.highliteDeleteBtn().Enabled()) {
			assertTrue(true);
			baseClass.passedStep("The page has been highlight and highlight has been saved after the page refresh");
		} else {
			assertTrue(false);
		}
		docViewRedact.highliteDeleteBtn().Click();
		baseClass.passedStep("The highlight has been deleted successfully");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Reload the document");
		baseClass.waitForElement(doclist.getDocIDFromDocView(4));
		doclist.getDocIDFromDocView(4).waitAndClick(5);
		baseClass.waitTime(5);
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).click();
		actions.build().perform();
		if (docViewRedact.highliteDeleteBtn().isElementAvailable(5) == true) {
			docViewRedact.highliteDeleteBtn().Click();
			baseClass.failedStep("the highlight has not been deleted after refresh");
		} else {
			baseClass.passedStep("The highlight is not present on the page after deleting and refreshing");

		}
		loginPage.logout();

	}

	/**
	 * 
	 * @author Krishna Date:NA Modified date:N/A Modified by::N/A Test
	 *         caseID:47923 Description :Verify after impersonation user can not see
	 *         the multi page and all page options from highlighting
	 */

	@Test(description = "RPMXCON-47923", enabled = true, groups = { "regression" })

	public void verifyAfterImpersonationCannotSeeMultiAndAllPage() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-47923");
		baseClass.stepInfo(
				"Verify after impersonation user can not see the multi page and all page options from highlighting");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String multipage = "multi page";
		String allpage = "all page";

		// Login as
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage SAU as with " + Input.sa1userName + "");
		baseClass.impersonateSAtoRMU();
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		baseClass.waitTime(5);
		driver.waitForPageToBeReady();
		redact.clickingHighlitingIcon();
		baseClass.stepInfo("Sub menus is displayed for highlighting on clicked icons as expected ");
		redact.verifyMultiPageandAllpageHighlight(multipage, allpage);
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage SAU as with " + Input.sa1userName + "");
		baseClass.impersonateSAtoReviewer();
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		redact.clickingHighlitingIcon();
		baseClass.stepInfo("Sub menus is displayed for highlighting on clicked icons as expected ");
		redact.verifyMultiPageandAllpageHighlight(multipage, allpage);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage PAU as with " + Input.pa1userName + "");
		baseClass.impersonatePAtoRMU();
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		redact.clickingHighlitingIcon();
		baseClass.stepInfo("Sub menus is displayed for highlighting on clicked icons as expected ");
		redact.verifyMultiPageandAllpageHighlight(multipage, allpage);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage PAU as with " + Input.pa1userName + "");
		baseClass.impersonatePAtoReviewer();
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		redact.clickingHighlitingIcon();
		baseClass.stepInfo("Sub menus is displayed for highlighting on clicked icons as expected ");
		redact.verifyMultiPageandAllpageHighlight(multipage, allpage);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");
		baseClass.impersonateRMUtoReviewer();
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		redact.clickingHighlitingIcon();
		baseClass.stepInfo("Sub menus is displayed for highlighting on clicked icons as expected ");
		redact.verifyMultiPageandAllpageHighlight(multipage, allpage);
		loginPage.logout();

	}

	/**
	 * Author : Krishna date: NA Modified date:NA Modified by: Test Case Id: 47909
	 * Verify Redactions menu is selected from doc view and then navigates to
	 * another document then selected panels/menus previously selected should
	 * remain.
	 */
	@Test(description = "RPMXCON-47909", enabled = true, dataProvider = "userDetails", groups = { "regression" })
	
	public void verifyRedactionMenuNavigatesMenusRemain(String fullName, String userName, String password) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-47909");
		baseClass.stepInfo(
				"Verify Redactions menu is selected from doc view and then navigates to another document then selected panels/menus previously selected should remain.");
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewPage docview = new DocViewPage(driver);
		DocListPage doclist = new DocListPage(driver);

		// login as
	    loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");

		baseClass.stepInfo("Clicked on reduction button ");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewMetaDataPage.getRedactIcon());
		docViewMetaDataPage.getRedactIcon().waitAndClick(15);
		driver.waitForPageToBeReady();
		baseClass.ValidateElementCollection_Presence(docview.getDocView_BatchRedaction(),
				"Batch Redaction panel displayed in redaction panel");
		baseClass.waitForElement(doclist.getDocIDFromDocView(2));
		doclist.getDocIDFromDocView(2).waitAndClick(5);
		baseClass.stepInfo("navigate to another document in minidoclist");
		docViewRedact.verifyRedactionIconRemain();
		baseClass.ValidateElementCollection_Presence(docview.getDocView_BatchRedaction(),
				"Batch Redaction panel displayed in redaction panel");
		docview.clickGearIconOpenMiniDocList();
		docview.switchToNewWindow(2);
		baseClass.waitForElement(doclist.getDocIDFromDocView(2));
		doclist.getDocIDFromDocView(2).waitAndClick(5);
		baseClass.stepInfo("navigate to another document in minidoclist childwindow");
		docview.closeWindow(1);
		docview.switchToNewWindow(1);
		baseClass.waitTime(5);
		docViewRedact.verifyRedactionIconRemain();
		baseClass.ValidateElementCollection_Presence(docview.getDocView_BatchRedaction(),
				"Batch Redaction panel displayed in redaction panel");
		baseClass.waitForElement(docview.getDocView_Next());
		docview.getDocView_Next().waitAndClick(5);
		baseClass.stepInfo("navigate to another document in navigation options-> , >");
		docViewRedact.verifyRedactionIconRemain();
		baseClass.ValidateElementCollection_Presence(docview.getDocView_BatchRedaction(),
				"Batch Redaction panel displayed in redaction panel");
		baseClass.waitForElement(docview.getDocView_NumTextBox());
		docview.getDocView_NumTextBox().waitAndClick(10);
		docview.getDocView_NumTextBox().SendKeys("5");
		docview.getDocView_NumTextBox().Enter();
		baseClass.stepInfo("Navigate to document on entering the document number");
		docViewRedact.verifyRedactionIconRemain();
		baseClass.ValidateElementCollection_Presence(docview.getDocView_BatchRedaction(),
				"Batch Redaction panel displayed in redaction panel");
		docview.clickClockIconMiniDocList();
		baseClass.stepInfo("Selected the document from history drop down");
		docViewRedact.verifyRedactionIconRemain();
		baseClass.ValidateElementCollection_Presence(docview.getDocView_BatchRedaction(),
				"Batch Redaction panel displayed in redaction panel");
		loginPage.logout();

	}

}
