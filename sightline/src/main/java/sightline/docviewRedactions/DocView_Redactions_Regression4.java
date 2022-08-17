package sightline.docviewRedactions;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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

public class DocView_Redactions_Regression4 {

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
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-47910
	 * @throws InterruptedException
	 * @throws AWTException
	 */

	@Test(description ="RPMXCON-47910",enabled = true, alwaysRun = true, groups = { "regression" }, dataProvider = "userDetails2")
	public void verifyRedactionPanelRetainedOnNavigation(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-47910");
		baseClass.stepInfo("Verify when Redactions menu is selected from doc view and navigates to another document from mini doc list child window then previously selected panels/menus should remain");
		loginPage.loginToSightLine(userName, password);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.randomText);
		sessionSearch.viewInDocView();
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingRedactionIcon();
		DocViewPage docView = new DocViewPage(driver);
		docViewRedact.navigatingDocsFromMiniDocListChildWindowandClose();
		if (docViewRedact.thisPageRedaction().isDisplayed()) {
			baseClass.passedStep("Redaction panel is retained after doc navigation");
		} else {
			baseClass.failedStep("Redaction panel not retained after doc navigation");
		}
		loginPage.logout();
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW/Redactions EXECUTED******");

	}

}
