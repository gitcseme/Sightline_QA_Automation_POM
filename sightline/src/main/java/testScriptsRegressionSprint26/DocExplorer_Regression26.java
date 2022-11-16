package testScriptsRegressionSprint26;

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
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExplorer_Regression26 {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;
	DocExplorerPage docExplorer;
	DocListPage docList;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();
		docExplorer = new DocExplorerPage(driver);

	}

	/**
	 * @author Krishna Date:NA ModifyDate:NA RPMXCON-54995
	 * @throws Exception
	 * @Description Verify that when a user configures MasterDate filter and ticks
	 *              the 'Select All' check-box and navigates Doc-Explorer to DocList
	 *              then documents gets loaded on DocList screen.
	 */
	@Test(description = "RPMXCON-54995", enabled = true, groups = { "regression" })
	public void verifyMasterDateSelectAllDocsGetsLoadedOnDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54995");
		baseClass.stepInfo(
				"Verify that when a user configures MasterDate filter and ticks the 'Select All' check-box and navigates Doc-Explorer to DocList then documents gets loaded on DocList screen.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		docexp.navigateToDocExplorerPage();
		docList.dateFilter("after", "2009/09/20", null);
		baseClass.passedStep("master date filter added");

		docexp.getApplyFilter().waitAndClick(10);
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		baseClass.stepInfo("Selected all checkboxes as expected ");
		driver.waitForPageToBeReady();
		if (docList.getYesAllPageDocs().isDisplayed()) {
			docList.getYesAllPageDocs().waitAndClick(5);
			docList.getPopUpOkBtn().waitAndClick(5);
		}
		baseClass.waitTime(3);
		docexp.navigateToDoclistFromDocExplorer();
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		baseClass.verifyUrlLanding(Input.url + "en-us/Document/DocList", " on doc List page", "Not on doc List page");
		if (docList.getHeaderText().isElementPresent()) {
			baseClass
					.passedStep("Navigated to DocList page is NOT displayed any run-time error on screen as expected.");

		} else {
			baseClass.failedStep("error is displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Krishna ModifyDate:NA RPMXCON-54996
	 * @throws Exception
	 * @Description Verify that when a user configures MasterDate and DocFileType
	 *              filters and ticks the 'Select All' check-box and navigates
	 *              Doc-Explorer to DocList then documents gets loaded on DocList
	 *              screen.
	 */
	@Test(description = "RPMXCON-54996", enabled = true, groups = { "regression" })
	public void verifyMasterDateDocFileTypeLoadedOnDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54996");
		baseClass.stepInfo(
				"Verify that when a user configures MasterDate and DocFileType filters and ticks the 'Select All' check-box and navigates Doc-Explorer to DocList then documents gets loaded on DocList screen.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);
		String FileType = "Spreadsheet";

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		docexp.navigateToDocExplorerPage();
		docList.dateFilter("after", "2009/09/20", null);
		baseClass.passedStep("master date filter added");
		baseClass.waitForElement(docexp.getDocExp_DocFiletypeSearchName());
		docexp.getDocExp_DocFiletypeSearchName().SendKeys(FileType);
		baseClass.waitForElement(docexp.getApplyFilter());
		docexp.getApplyFilter().waitAndClick(10);
		if (baseClass.text(FileType).isElementPresent()) {
			baseClass.passedStep("DocFileType filters has been configured on Doc Explorer screen as expected. ");

		} else {
			baseClass.failedStep("DocFileType filters is Not configured");
		}
		driver.waitForPageToBeReady();
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		baseClass.stepInfo("Selected all checkboxes");
		if (docList.getYesAllPageDocs().isDisplayed()) {
			docList.getYesAllPageDocs().waitAndClick(5);
			docList.getPopUpOkBtn().waitAndClick(5);
		}
		baseClass.waitTime(3);
		docexp.navigateToDoclistFromDocExplorer();
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		baseClass.verifyUrlLanding(Input.url + "en-us/Document/DocList", " on doc List page", "Not on doc List page");
		if (docList.getHeaderText().isElementPresent()) {
			baseClass.passedStep("Navigated to DocList page is displayed without any runtime error");

		} else {
			baseClass.failedStep("error is displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Krishna ModifyDate:NA RPMXCON-54998
	 * @throws Exception
	 * @Description Verify that when a user configures MasterDate and
	 *              EmailRecipientNames filters and ticks the 'Select All' check-box
	 *              and navigates Doc-Explorer to DocList then documents gets loaded
	 *              on DocList screen.
	 */
	@Test(description = "RPMXCON-54998", enabled = true, groups = { "regression" })
	public void verifyMasterDateEmailRecipientDocsGetsLoadedOnDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54998");
		baseClass.stepInfo(
				"Verify that when a user configures MasterDate and EmailSubject filters and ticks the 'Select All' check-box and navigates Doc-Explorer to DocList then documents gets loaded on DocList screen.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		docexp.navigateToDocExplorerPage();
		docList.dateFilter("after", "2009/09/20", null);
		baseClass.passedStep("master date filter added");
		baseClass.waitForElement(docexp.getEmailRecipientColumnTextField());
		docexp.getEmailRecipientColumnTextField().SendKeys(Input.emailReciepientName2);
		baseClass.waitForElement(docexp.getApplyFilter());
		docexp.getApplyFilter().waitAndClick(10);
		if (baseClass.text(Input.emailReciepientName2).isElementPresent()) {
			baseClass.passedStep(
					"EmailRecipientNames  filters has been get configured on Doc Explorer screen as expected. ");

		} else {
			baseClass.failedStep("EmailRecipientNames  filters is Not configured");
		}
		driver.waitForPageToBeReady();
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		baseClass.stepInfo("Select all checkboxes");
		if (docList.getYesAllPageDocs().isDisplayed()) {
			docList.getYesAllPageDocs().waitAndClick(5);
			docList.getPopUpOkBtn().waitAndClick(5);
		}
		baseClass.waitTime(3);
		docexp.navigateToDoclistFromDocExplorer();
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		baseClass.verifyUrlLanding(Input.url + "en-us/Document/DocList", " on doc List page", "Not on doc List page");
		if (docList.getHeaderText().isElementPresent()) {
			baseClass.passedStep("Navigated to DocList page is displayed without any runtime error as expected");

		} else {
			baseClass.failedStep("error is displayed");
		}
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  DocExplorer_Regression26.**");
	}

}
