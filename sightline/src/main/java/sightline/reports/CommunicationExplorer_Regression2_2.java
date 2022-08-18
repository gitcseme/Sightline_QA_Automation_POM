package sightline.reports;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsRegression.DocList_Regression;
import testScriptsSmoke.Input;

public class CommunicationExplorer_Regression2_2 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	SoftAssert softAssertion;
	BaseClass bc;
	String hitsCount;
	CommunicationExplorerPage comExpPage;
	DocViewPage docview;
	int pureHit;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		//Input in = new Input();
		//in.loadEnvConfig();

	}

	@Test(description = "RPMXCON-48695", dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void verifyBulkActionsFromCommunicationExplorerReportPage(String username, String password, String role)
			throws InterruptedException {
		bc = new BaseClass(driver);
		bc.stepInfo("Test case Id: RPMXCON-48695");
		bc.stepInfo(
				"To Verify as an Admin or RMU in the Communication Explorer can select a node and Perform bulk tag, "
						+ "bulk folder, bulk assign documents and view in doc list");
		String assignmentName = "comExp" + Utility.dynamicNameAppender();
		String tagName = "comExp" + Utility.dynamicNameAppender();
		String folderName = "comExp" + Utility.dynamicNameAppender();
		String reportName = "comExp" + Utility.dynamicNameAppender();
		lp.loginToSightLine(username, password);

		CommunicationExplorerPage comExpPage = new CommunicationExplorerPage(driver);
		SessionSearch search = new SessionSearch(driver);
		docview = new DocViewPage(driver);

		comExpPage.navigateToCommunicationExpPage();
		comExpPage.include(Input.metaDataCustodianNameInput, Input.metaDataName, null, false);
		comExpPage.filterCriteriaVerify(Input.metaDataName, Input.metaDataCustodianNameInput, true);
		comExpPage.generateReport_DefaultSG();
		comExpPage.selectShowOptions("8", "Email Address", "Exchange Volume");

		comExpPage.clickApplyBtn();
		comExpPage.saveReport(reportName);

		comExpPage.clickReport();
		String docCount = comExpPage.selectedDocsCount();

		comExpPage.clickActionBtn(true, false, false);
		String taggedDocs = search.BulkActions_Tag(tagName);

		comExpPage.clickActionBtn(false, true, false);
		String folderedDocs = search.BulkActions_Folder(folderName);

		DocListPage dlPage = new DocListPage(driver);
		comExpPage.viewinDoclist();
		List<String> docIDs = new ArrayList<String>();
		docIDs = dlPage.gettingAllDocIDs();
		if (username.equals(Input.rmu1userName)) {
			comExpPage.getBackToSourceButton().Click();
			driver.waitForPageToBeReady();
			bc.verifyPageNavigation("CommunicationExplorerReport");

			comExpPage.clickReport();
			comExpPage.clickActionBtn(false, false, true);
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			agnmt.FinalizeAssignmentAfterBulkAssign();
			agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
			agnmt.getAssignmentSaveButton().waitAndClick(10);
			bc.selectproject();
			bc.stepInfo("Created a assignment by assigning saved search documents from search term report page -"
					+ assignmentName);
			String ActualCount = agnmt.verifydocsCountInAssgnPage(assignmentName);
			softAssertion.assertEquals(docCount, ActualCount);
		}
		int docCountInDocList = docIDs.size();

		// validating bulk actioned docs[tag/folders/assign/doc list] are same as
		// documents
		// selected in communications explorer page.
		softAssertion.assertEquals(docCount, taggedDocs);
		softAssertion.assertEquals(docCount, folderedDocs);
		softAssertion.assertEquals(docCount, String.valueOf(docCountInDocList));

		softAssertion.assertAll();
		bc.passedStep(
				"Document Count associated to selected node doc count in communication explorer report is same  as"
						+ " excpeted in nulk tagged tag/bulk foldered folder/"
						+ "manage assignments page after assigning the docs to assignment and in mini doc list page.");
		ReportsPage reportPage = new ReportsPage(driver);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		reportPage.verifyCustomReportDisplaydetails(reportName);
		reportPage.deleteCustomReport(reportName);

		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagPage.deleteAllTags(tagName);
		tagPage.deleteAllFolders(folderName);
		lp.logout();

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		softAssertion = new SoftAssert();
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		comExpPage = new CommunicationExplorerPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		bc.stepInfo("***Executed Communications Explorer_Regression2_2****");

	}
}
