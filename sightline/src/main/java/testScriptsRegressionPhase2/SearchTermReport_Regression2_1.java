package testScriptsRegressionPhase2;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
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
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagCountbyTagReport;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SearchTermReport_Regression2_1 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	BaseClass bc;
	SearchTermReportPage st;
	String saveSearchNamePA = "ST" + Utility.dynamicNameAppender();
	String saveSearchNameRMU ="ST" + Utility.dynamicNameAppender();
	
	String[] savedSearchNamesPA = { saveSearchNamePA };
	String[] savedSearchNamesRMU = { saveSearchNameRMU };
	String[] searchData = { "test" };
	String[] Hits = new String[1];
	String[] HitsRMU = new String[1];
	

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
		
		// Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		// Login as a PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		// Search and save it
		SessionSearch search = new SessionSearch(driver);
		for (int i = 0; i < savedSearchNamesPA.length; i++) {
			search.basicContentSearch(searchData[i]);
			search.saveSearch(savedSearchNamesPA[i]);
			Hits[i] = search.verifyPureHitsCount();
			System.out.print(Hits[i]);
			if (i != 2) {
				bc.selectproject();
			}
		}

		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// Search and save it
		for (int i = 0; i < savedSearchNamesRMU.length; i++) {
			search.basicContentSearch(searchData[i]);
			search.saveSearch(savedSearchNamesRMU[i]);
			HitsRMU[i] = search.verifyPureHitsCount();
			System.out.print(HitsRMU[i]);
			if (i != 2) {
				bc.selectproject();
			}
		}
		lp.logout();
		lp.quitBrowser();

	}	

	 @Test(description ="RPMXCON-48736",dataProvider = "Users_PARMU", groups = { "regression" })
	public void ValidateSearchTermreport_viewInDocList(String username, String password, String role)
			throws InterruptedException, AWTException {
		bc.stepInfo("Test case Id: RPMXCON-48736");
		bc.stepInfo("To verify that Bulk action_View in Doc List is working on Search Term Report");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		String saveSearchName = null;
		String hitsCount = null;
		SoftAssert softAssertion = new SoftAssert();
		bc.stepInfo("Logged in as -" + role);
		if (role == "RMU") {
			saveSearchName = saveSearchNameRMU;
			hitsCount = HitsRMU[0];
		}
		if (role == "PA") {
			saveSearchName = saveSearchNamePA;
			hitsCount = Hits[0];
		}
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String actualHitCount = st.GenerateReport(saveSearchName);
		softAssertion.assertEquals(actualHitCount, hitsCount);
		bc.stepInfo("Report  generated for selected search and pure hit count also verified.");
		st.ViewInDocList();
		String actualCount= st.verifyingDocListCount();
		softAssertion.assertEquals(actualHitCount, actualCount,"Document count is not matched");
		softAssertion.assertAll();
		bc.passedStep("Document Count associated to selected saved search in STR page is  displayed"
				+ " as excpeted in doc list page");
		lp.logout();
	}

	
	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		bc = new BaseClass(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		try {
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { 
				{ Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" }
		};
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			// Input in = new Input();
			// in.loadEnvConfig();
			driver = new Driver();
			lp = new LoginPage(driver);
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			SavedSearch savedSearch = new SavedSearch(driver);
			savedSearch.SaveSearchDelete(saveSearchNameRMU);
			lp.logout();
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			savedSearch.SaveSearchDelete(saveSearchNamePA);
			lp.logout();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

	
	}
}
