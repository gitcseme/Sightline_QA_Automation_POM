package testScriptsRegressionSprint25;

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
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Regression25 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	Utility utility;
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



	/**
	 * Author : date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-63806 Verify user can select the text from the redacted document
	 * and perform Copy -Paste by using "Ctrl C" from viewer file and "Ctrl V" in to
	 * coding form field
	 * 
	 * 
	 */
	@Test(description ="RPMXCON-63806",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyTextRedacDocPerformCopyPasteInCodingForm() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-63806");
		baseClass.stepInfo("Verify user can select the text from the redacted document\n"
				+ "		 * and perform Copy -Paste by using \"Ctrl C\" from viewer file and \"Ctrl V\" in to\n"
				+ "		 * coding form field Verify user can select the text from the redacted document and perform Copy -Paste by using \"Ctrl C\" from viewer file and \"Ctrl V\" in to coding form field");
		DocViewPage docView = new DocViewPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		String docid = "ID00000152";
		SoftAssert softassert = new SoftAssert();

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
		docViewRedact.redactRectangleUsingOffset(0, 0, 50, 100);
		baseClass.waitForElement(docViewRedact.redactionSave());
		docViewRedact.redactionSave().isElementAvailable(15);
		docViewRedact.redactionSave().waitAndClick(5);
		baseClass.VerifySuccessMessage("Redaction tags saved successfully.");

		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		driver.waitForPageToBeReady();
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		baseClass.stepInfo("Document saved successfully with  comments");
		String beforeText = docView.getAddComment1().getText();
		driver.waitForPageToBeReady();
		docView.getCodeSameAsLast().waitAndClick(5);
		baseClass.stepInfo("Clicked codesameas last Document saved successfully with comments");
		docView.ScrollAndSelectDocument(docid);
		driver.waitForPageToBeReady();
		String AfterText = docView.getAddComment1().getText();
		softassert.assertEquals(beforeText,AfterText);
		baseClass.stepInfo("Document saved successfully with latest comments");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.ScrollAndSelectDocument(docid);
		docView.verifyCopyAndPasteRedacTextOnCommentBoxOffSet(0, 0, 40, 100);
		baseClass.stepInfo("Copied redacted text and paste into the Comments box");
		driver.waitForPageToBeReady();
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		baseClass.passedStep("Document saved successfully");

		baseClass.stepInfo("Text and this page redaction as expected");
		docViewRedact.redactRectangleUsingOffset(0, 0, 50, 100);
		baseClass.waitForElement(docViewRedact.redactionSave());
		docViewRedact.redactionSave().isElementAvailable(15);
		docViewRedact.redactionSave().waitAndClick(5);
		baseClass.VerifySuccessMessage("Redaction tags saved successfully.");
		softassert.assertAll();
	}
}
