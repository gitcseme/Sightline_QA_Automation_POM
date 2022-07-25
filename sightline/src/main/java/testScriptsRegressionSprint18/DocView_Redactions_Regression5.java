package testScriptsRegressionSprint18;

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
import pageFactory.DataSets;
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
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Redactions_Regression5 {
	
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
	 * Id:RPMXCON-51985 Verify in-doc search highlighting is working for Searchable
	 * PDF (with Uploaded Data set)
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 69)
	public void verifyDocHighlightingForSearchablePdfDataSet() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51985");
		baseClass.stepInfo("Verify in-doc search highlighting  is working for Searchable PDF (with Uploaded Dataset)");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		DataSets dataset = new DataSets(driver);
		String text = "T";
		String Dataset = "ExtendedCharacters";
		DocListPage doc = new DocListPage(driver);
		String tagName = "tag" + Utility.dynamicNameAppender();
		TagsAndFoldersPage	tagsAndFoldersPage = new TagsAndFoldersPage(driver);

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as PA");
		dataset.navigateToDataSetsPage();
		baseClass.stepInfo("Navigating to dataset page");
		dataset.SelectingUploadedDataSets();
		baseClass.passedStep("Dataset is successfully published");
		driver.waitForPageToBeReady();
		dataset.SearchDataSetsInDocView(Dataset);
		baseClass.stepInfo("Selecting uploaded dataset and navigating to docview page");
		
		// verifying a corresponding text and highlighting a document.
		baseClass.waitTime(3);
		docView.verifyDisplaysTheDefaultPdfInDocView();
		docView.verifyCorrespondingTextIsHighlightedOnDocs(text);
		tagsAndFoldersPage.CreateTag(tagName, Input.securityGroup);
		driver.waitForPageToBeReady();
		dataset.navigateToDataSetsPage();
		driver.waitForPageToBeReady();
		dataset.SelectingUploadedDataSets();
		driver.waitForPageToBeReady();
		dataset.SearchDataSetsInDocList(Dataset);
		doc.selectAllDocs();
		doc.docListToBulkRelease(Input.securityGroup);
		doc.bulkTagExistingFromDoclist(tagName);
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as RMU");
		sessionsearch.switchToWorkproduct();
		sessionsearch.selectTagInASwp(tagName);
		baseClass.waitForElement(sessionsearch.getQuerySearchButton());
		sessionsearch.getQuerySearchButton().waitAndClick(3);
		sessionsearch.viewInDocView();
		
		// verifying a corresponding text and highlighting a document.
		baseClass.waitTime(3);
		docView.verifyDisplaysTheDefaultPdfInDocView();
		docView.verifyCorrespondingTextIsHighlightedOnDocs(text);
		loginPage.logout();

		// login as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Login as REV");
		sessionsearch.switchToWorkproduct();
		sessionsearch.selectTagInASwp(tagName);
		sessionsearch.getQuerySearchButton().waitAndClick(3);
		sessionsearch.viewInDocView();
		
		// verifying a corresponding text and highlighting a document.
		baseClass.waitTime(3);
		docView.verifyDisplaysTheDefaultPdfInDocView();
		docView.verifyCorrespondingTextIsHighlightedOnDocs(text);
	}

}