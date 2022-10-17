package sightline.advancedSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearchRegression_22 {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
		savedSearch = new SavedSearch(driver);

	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	/**
	 * @author
	 * @Date: 29/9/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that Advanced Search works properly - for MasterDate
	 *              time metadata - Provide only dates (not times) with \"Is\"
	 *              operator.RPMXCON-57236
	 */

	@Test(description = "RPMXCON-57236", enabled = true, groups = { "regression" })
	public void verifyAdvancedSearchWorkProperlyForMasterDateTimeMetadataWithOnlyDateAndIsOperator() throws Exception {

		String masterDate = "2009-09-20";
		String ExpectedDate = "2009/09/20";
		List<String> masterDateValues = new ArrayList<String>();
		DocListPage docList = new DocListPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-57236 Advanced Search");
		baseClass.stepInfo(
				"Verify that Advanced  Search works properly - for MasterDate time metadata - Provide only dates (not times) with \"Is\" operator");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure and perform MasterDate metadata search query in Advanced Search
		// page
		baseClass.stepInfo("configure and perform MasterDate metadata search query in Advanced Search page");
		sessionSearch.advancedMetaDataSearch(Input.masterDateText, Input.is, masterDate, null);

		// verify search results should be inclusive of masterDate We passed as Input
		String searchResult = sessionSearch.contentAndMetaDataResult().getText();
		baseClass.compareTextViaContains(searchResult, masterDate,
				"Search Result '" + searchResult + "' contains the Date '" + masterDate + "'We passed as Input",
				"Search Result '" + searchResult + "' doesn't contains the Date '" + masterDate
						+ "'We passed as Input");
		sessionSearch.ViewInDocList();

		// Verify That MasterDate date/time field search result return documents which
		// satisfied above configured query. and search results should be inclusive of
		// both From and To dates.
		docList.checkMaseterDateAsExpected(masterDateValues, ExpectedDate, ExpectedDate, "Between", "yyyy/MM/dd");
		baseClass.passedStep(
				"Verified That MasterDate date/time field search result return documents which satisfied above configured query. and search results should be inclusive of both From and To dates.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 29/9/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that Advanced Search works properly for MasterDate
	 *              date/time field with \"Range\" operator.RPMXCON-57225
	 */

	@Test(description = "RPMXCON-57225", enabled = true, groups = { "regression" })
	public void verifyAdvancedSearchWorkProperlyForMasterDateDateAndTimeFieldWithRangeOperator()
			throws ParseException, InterruptedException {
		DocListPage docList = new DocListPage(driver);
		List<String> masterDateValues = new ArrayList<String>();
		String fromDate = "2009-09-20 06:30:12";
		String toDate = "2009-09-21 06:30:12";
		String expectedFromDate = "2009/09/20";
		String expectedToDate = "2009/09/21";
		baseClass.stepInfo("Test case Id: RPMXCON-57225 Advanced Search");
		baseClass.stepInfo(
				"Verify that Advanced Search works properly for MasterDate date/time field  with \"Range\" operator");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure and perform metadata MasterDate search query in Advanced Search
		// page
		baseClass.stepInfo("configure and perform MasterDate metadata search query in Advanced Search page");
		sessionSearch.advancedMetaDataSearch(Input.masterDateText, Input.is_Or_Range, fromDate, toDate);
		sessionSearch.ViewInDocList();

		// Verify that MasterDate date/time field search result return documents which
		// satisfied above configured query.
		docList.checkMaseterDateAsExpected(masterDateValues, expectedFromDate, expectedToDate, "Between", "yyyy/MM/dd");
		baseClass.passedStep(
				"Verify that \"MasterDate date/time\" field search result return documents which satisfied above configured query.");

		// logOut
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

		System.out.println("**Executed Advanced search Regression2_21**");
	}

}
