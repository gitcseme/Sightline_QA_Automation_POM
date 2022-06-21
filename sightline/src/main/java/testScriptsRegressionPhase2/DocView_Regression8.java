package testScriptsRegressionPhase2;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}

}
