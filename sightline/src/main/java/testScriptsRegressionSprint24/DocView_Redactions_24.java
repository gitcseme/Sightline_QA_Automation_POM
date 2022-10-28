package testScriptsRegressionSprint24;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
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
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Redactions_24 {

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
	 * 
	 * @author Sakthivel Date:NA Modified date:N/A Modified by::N/A Test
	 *         caseID:46962 Description :Verify the automatically selection of the
	 *         redaction tag when Project Admin impersonates as RMU/Reviewer
	 */

	@Test(description = "RPMXCON-46962", enabled = true, groups = { "regression" })

	public void verifySelectionRedactionTagPAImpersonateRMU() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-46962");
		baseClass.stepInfo(
				"Verify the automatically selection of the redaction tag when Project Admin impersonates as RMU/Reviewer");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");
		baseClass.impersonatePAtoRMU();
		redactac.AddRedaction(RedactName, Input.rmu1FullName);

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchnameenron);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();

		// verify default RedactionTag in popup
		baseClass.stepInfo("Add redaction in rectangle");
		redact.redactRectangleUsingOffset(10, 10, 20, 20);
		baseClass.stepInfo("Selected on this page add redaction");
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		redactac.DeleteRedaction(RedactName);
	}

	/**
	 * 
	 * @author Krishna Date:NA Modified date:N/A Modified by::N/A Test
	 *         caseID:46960 Description :Verify the automatically selection of the
	 *         redaction tag when System Admin impersonates as RMU/Reviewer
	 */

	@Test(description = "RPMXCON-46960", enabled = true, groups = { "regression" })

	public void verifySelectionRedactionTagSAImpersonateRmu() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-46960");
		baseClass.stepInfo(
				"Verify the automatically selection of the redaction tag when System Admin impersonates as RMU/Reviewer");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SAU as with " + Input.sa1userName + "");
		baseClass.impersonateSAtoRMU();
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

		// verify default RedactionTag in popup
		baseClass.waitForElement(redact.thisPageRedaction());
		baseClass.waitTillElemetToBeClickable(redact.thisPageRedaction());
		redact.thisPageRedaction().waitAndClick(5);
		baseClass.stepInfo("Selected on this page add redaction");
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		redactac.DeleteRedaction(RedactName);
	}

	/**
	 * 
	 * @author Krishna Date:NA Modified date:N/A Modified by::N/A Test
	 *         caseID:46961 Description :Verify the automatically selection of the
	 *         redaction tag when Domain Admin impersonates as RMU/Reviewer
	 */

	@Test(description = "RPMXCON-46961", enabled = true, groups = { "regression" })

	public void verifySelectionRedactionTagDAImpersonateRMU() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-46961");
		baseClass.stepInfo(
				"Verify the automatically selection of the redaction tag when Domain Admin impersonates as RMU/Reviewer");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DAU as with " + Input.da1userName + "");
		baseClass.impersonateDAtoRMU();
		redactac.AddRedaction(RedactName, Input.rmu1FullName);

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchnameenron);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		baseClass.stepInfo("Clicked on reduction button ");
		driver.waitForPageToBeReady();

		// verify default RedactionTag in popup
		baseClass.stepInfo("Add redaction in rectangle");
		redact.redactRectangleUsingOffset(10, 10, 20, 20);
		baseClass.stepInfo("Selected on this page add redaction");
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		redactac.DeleteRedaction(RedactName);
	}

	/**
	 * 
	 * @author Krishna Date:NA Modified date:N/A Modified by::N/A Test
	 *         caseID:46959 Description :Verify that after editing a redaction and
	 *         applies a redaction tag to a redaction, then this applied redaction
	 *         tag should be considered as the latest redaction tag
	 */

	@Test(description = "RPMXCON-46959", enabled = true, groups = { "regression" })

	public void verifyAfterEditingRedactionApplyRedactionTag() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-46959");
		baseClass.stepInfo(
				"Verify that after editing a redaction and applies a redaction tag to a redaction, then this applied redaction tag should be considered as the latest redaction tag");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");
		redactac.AddRedaction(RedactName, Input.rmu1FullName);
		redactac.EditRedaction(RedactName);
		driver.waitForPageToBeReady();

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Clicked on reduction button ");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Add redaction in rectangle as expected");

		// verify default RedactionTag in popup
		redact.redactRectangleUsingOffset(10, 10, 20, 20);
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		baseClass.passedStep(
				"After Edited redaction tag is considered as the latest redaction tag is saved successfully.");
		redactac.DeleteRedaction(RedactName);
	}
}