package testScriptsRegressionSprint22;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Redactions_22 {

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

	/**
	 * 
	 * @author Krishna Date: Modified date:N/A Modified by:N/A Test caseID:49979
	 *         Description :Verify that when applies rectangle redaction, the
	 *         redaction popup should automatically select the redaction tag that
	 *         was last applied across user session(s)
	 */

	@Test(description = "RPMXCON-49979", enabled = true, groups = { "regression" })

	public void verifyApplyRedactionPopUpAutomaticSelectRedactionTag() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-49979");
		baseClass.stepInfo(
				"Verify that when applies rectangle redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s)");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		baseClass.stepInfo(
				"Verify that when applies rectangle redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s)");

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Click on reduction button ");

		docViewMetaDataPage.clickOnRedactAndRectangle();
		baseClass.waitTime(5);
		String color = redact.get_textHighlightedColorOnRectangleSubMenu().getWebElement().getCssValue("color");
		System.out.println(color);
		String ExpectedColor = org.openqa.selenium.support.Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		if (Input.iconColor.equalsIgnoreCase(ExpectedColor)) {
			baseClass.passedStep("Reduction Rectangle submenu icon is highlighted red color is displayed successfully");
		} else {
			baseClass.failedStep("Reduction submenu icon is not highlighted");
		}

		baseClass.stepInfo("redaction popup is automatically select the redaction tag");
		docViewMetaDataPage.redactbyrectangle(10, 15, Input.defaultRedactionTag);
	}

	/**
	 * 
	 * @author Krishna Date: Modified date:N/A Modified by::N/A Test caseID:49980
	 *         Description: Verify that when applies ‘This Page’ redaction, the
	 *         redaction popup should automatically select the redaction tag that
	 *         was last applied across user session(s)
	 */

	@Test(description = "RPMXCON-49980", enabled = true, groups = { "regression" })

	public void verifyApplyThisPageRedactionAutomaticSelectRedactionTag() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-49980");
		baseClass.stepInfo(
				"Verify that when applies rectangle redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s)");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		SoftAssert softassert = new SoftAssert();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		baseClass.stepInfo(
				"Verify that when applies ‘This Page’ redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s)");

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Click on reduction button ");
		baseClass.waitForElement(docViewMetaDataPage.getRedactIcon());
		docViewMetaDataPage.getRedactIcon().waitAndClick(15);
		driver.waitForPageToBeReady();
		redact.performThisPageRedactionWithoutSaving(Input.defaultRedactionTag);
		softassert.assertTrue(docViewMetaDataPage.getSaveButton().isDisplayed());
		baseClass.stepInfo("selected 'This Page' redacted area is displayed for the current page and pop up is opened");

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Redaction popup is automatically select the redaction tag successfully");
		baseClass.waitForElement(docViewMetaDataPage.getSaveButton());
		docViewMetaDataPage.getSaveButton().Click();
		baseClass.VerifySuccessMessage("Redaction tags saved successfully.");
		baseClass.getCloseSucessmsg();
		softassert.assertAll();
	}

	/**
	 * 
	 * @author Krishna Date: Modified date:N/A Modified by::N/A Test caseID:49997
	 *         Description : Verify the automatically selection of the redaction tag
	 *         when RMU impersonates as Reviewer
	 */

	@Test(description = "RPMXCON-49997", enabled = true, groups = { "regression" })

	public void verifySelectedRedactionTagRmuImpersonateAsRev() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-49997");
		baseClass.stepInfo("Verify the automatically selection of the redaction tag when RMU impersonates as Reviewer");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		baseClass.stepInfo("Verify the automatically selection of the redaction tag when RMU impersonates as Reviewer");
		redactac.AddRedaction(RedactName, Input.rev1FullName);
		baseClass.impersonateRMUtoReviewer();

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Clicked on reduction button ");
		baseClass.waitForElement(docViewMetaDataPage.getRedactIcon());
		docViewMetaDataPage.getRedactIcon().waitAndClick(15);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(redact.thisPageRedaction());
		baseClass.waitTillElemetToBeClickable(redact.thisPageRedaction());
		redact.thisPageRedaction().waitAndClick(5);
		baseClass.stepInfo("Selected on this page add redaction");
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		redactac.DeleteRedaction(RedactName);
	}

	/**
	 * 
	 * @author Krishna Date: Modified date:N/A Modified by::N/A Test caseID:49996
	 *         Description :Verify the automatically selection of the redaction tag
	 *         when Project Admin impersonates as RMU/Reviewer
	 */

	@Test(description = "RPMXCON-49996", enabled = true, groups = { "regression" })

	public void verifySelectionRedactionTagPAImpersonateRmu() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-49996");
		baseClass.stepInfo(
				"Verify the automatically selection of the redaction tag when Project Admin impersonates as RMU/Reviewer");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");
		baseClass.stepInfo("Verify the automatically selection of the redaction tag when RMU impersonates as Reviewer");
		baseClass.impersonatePAtoRMU();
		redactac.AddRedaction(RedactName, Input.rmu1FullName);

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchnameenron);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Clicked on reduction button ");
		baseClass.waitForElement(docViewMetaDataPage.getRedactIcon());
		docViewMetaDataPage.getRedactIcon().waitAndClick(15);
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(redact.thisPageRedaction());
		baseClass.waitTillElemetToBeClickable(redact.thisPageRedaction());
		redact.thisPageRedaction().waitAndClick(5);
		baseClass.stepInfo("Selected on this page add redaction");
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		redactac.DeleteRedaction(RedactName);
	}

	/**
	 * 
	 * @author Krishna Date: Modified date:N/A Modified by::N/A Test caseID:49995
	 *         Description :Verify the automatically selection of the redaction tag
	 *         when Domain Admin impersonates as RMU/Reviewer
	 */

	@Test(description = "RPMXCON-49995", enabled = true, groups = { "regression" })

	public void verifySelectionRedactionTagDAImpersonateRmu() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-49995");
		baseClass.stepInfo(
				"Verify the automatically selection of the redaction tag when Domain Admin impersonates as RMU/Reviewer");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");
		baseClass.stepInfo("Verify the automatically selection of the redaction tag when RMU impersonates as Reviewer");
		baseClass.impersonateDAtoRMU();
		redactac.AddRedaction(RedactName, Input.rmu1FullName);

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchnameenron);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Clicked on reduction button ");
		baseClass.waitForElement(docViewMetaDataPage.getRedactIcon());
		docViewMetaDataPage.getRedactIcon().waitAndClick(15);
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(redact.thisPageRedaction());
		baseClass.waitTillElemetToBeClickable(redact.thisPageRedaction());
		redact.thisPageRedaction().waitAndClick(5);
		baseClass.stepInfo("Selected on this page add redaction");
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		redactac.DeleteRedaction(RedactName);
	}

}
