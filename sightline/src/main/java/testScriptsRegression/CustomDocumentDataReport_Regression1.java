package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

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
import pageFactory.CommentsPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CustomDocumentDataReport_Regression1 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	SoftAssert softAssertion;
	BaseClass bc;
	CommentsPage cp;
	CustomDocumentDataReport cddr;
	String hitsCount;
	String commentname = "C" + Utility.dynamicNameAppender();
	String tagName = "AAtag" + Utility.dynamicNameAppender();
	String saveSearchNameRMU = "ST" + Utility.dynamicNameAppender();
	String saveSearchNamePA = "ST" + Utility.dynamicNameAppender();
	String report1 = "Report" + Utility.dynamicNameAppender();
	String report2 = "Report" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		//Input in = new Input();
		// in.loadEnvConfig();

		// Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		// Login as a PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.saveSearch(saveSearchNameRMU);
		hitsCount = search.verifyPureHitsCount();
		search.bulkTag(tagName);
		lp.logout();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.saveSearch(saveSearchNamePA);
		lp.quitBrowser();

	}
/**
 * @author Jayanthi.ganesan
 * @param username
 * @param password
 * @param role
 */
	@Test(dataProvider = "Users_PARMU",groups = { "regression" }, priority = 0)
	public void navigateToExportReport(String username, String password, String role) {
		bc.stepInfo("Test case Id: RPMXCON-56390");
		bc.stepInfo("To verify that User is able view Custom Document Data Report");
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
        driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
    	CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
        String expectedURL=Input.url+"Report/ExportData";
        bc.waitForElement(cddr.getCustomDocumentDataReport());
        SoftAssert    softAssertion = new SoftAssert();
        if(cddr.getCustomDocumentDataReport().isDisplayed()) {
        	bc.passedStep("Custom Document Data  Report link is displayed in reports landing Page");
        	cddr.getCustomDocumentDataReport().Click();
            driver.waitForPageToBeReady();
            bc.stepInfo("Clicked on Custom Document Data  Report link.");
            softAssertion.assertEquals(expectedURL,driver.getUrl());
            softAssertion.assertAll();
            bc.passedStep("***Sucessfully navigated to  Export Page***");   	
        }
        else
        {
        	bc.failedStep("Custom Document Data  Report link is not found in Reports landing page.");
        }
        
    }
	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 * @throws ParseException
	 * @throws IOException
	 */
	 @Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 1)
	public void verifyAndValidateCustomDataReport(String username, String password, String role)
			throws InterruptedException, ParseException, IOException {
		String[] metaDataFields = { "CustodianName" };
		// String[] metaDataFields1 = { "CustodianName", "DocFileName" };
		String[] workProductFields = { tagName };
		bc.stepInfo("Test case Id: RPMXCON-58591");
		bc.stepInfo("To verify that Users can save the selected criteria for custom document"
				+ " data report and view the custom report");
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		// report1
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		cddr.selectSource("Security Groups", Input.securityGroup);
		cddr.selectExportFieldFormat("039");
		cddr.selectExportTextFormat("039");
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		cddr.getRunReport().waitAndClick(3);
		cddr.reportRunSuccessMsg();
		cddr.SaveReport(report1);
		bc.stepInfo("Saved the Custom report " + report1);

		// report2
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		cddr.selectSource("Security Groups", "Default Security Group");
		cddr.selectExportFieldFormat("034");
		cddr.selectExportTextFormat("034");
		cddr.selectWorkProductFields(workProductFields);
		cddr.SaveReport(report2);
		bc.stepInfo("Saved the Custom report " + report2);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		cddr.getReportName(report1).Click();
		driver.waitForPageToBeReady();
		cddr.validateSelectedExports(workProductFields);
		cddr.validateSelectedExports(metaDataFields);
		cddr.getRunReport().Click();
		cddr.reportRunSuccessMsg();

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		cddr.getReportName(report2).Click();
		driver.waitForPageToBeReady();
		cddr.selectMetaDataFields(metaDataFields);
		cddr.validateSelectedExports(workProductFields);
		final int Bgcount = bc.initialBgCount();
		cddr.getRunReport().Click();
		cddr.reportRunSuccessMsg();
		SoftAssert softAssertion = new SoftAssert();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		cddr.downloadExport();
		bc.waitTime(5);
		String Filename2 = bc.GetLastModifiedFileName();
		bc.stepInfo(Filename2 + "Last Modified File name after Downloading the report");
		String actualValue = cddr.csvfileVerification("", Filename2);
		softAssertion.assertTrue(actualValue.contains("Alltags" + tagName + "\"CustodianName\"\""));
		softAssertion.assertAll();
		bc.passedStep("Sucessfully verified that verify that Users can save the selected criteria for custom"
				+ " document data report and view the custom report");
		ReportsPage rp = new ReportsPage(driver);
		rp.deleteCustomReport(report1);
	}

	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 2)
	public void verifyModifyObj(String username, String password, String role)
			throws InterruptedException, ParseException, IOException {
		String[] metaDataFields1 = { "CustodianName", "DocFileName", "AttachCount" };
		bc.stepInfo("Test case Id: RPMXCON-56542");
		bc.stepInfo(
				"To verify that user can export successfully with modifying the order of objects selected for export in the custom data export");
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		if (role == "PA") {
			cddr.selectSourceAsSearch(saveSearchNamePA);
		}
		if (role == "RMU") {
			cddr.selectSourceAsSearch(saveSearchNameRMU);
		}
		cddr.selectMetaDataFields(metaDataFields1);
		cddr.getRunReport().Click();
		cddr.reportRunSuccessMsg();
		List<String> slectdFieldList = new ArrayList<>();
		slectdFieldList = cddr.verifyMetaDatswap();
		String Filename2 = cddr.runReportandVerifyFileDownloaded();
		String value = cddr.csvfileVerification("", Filename2);
		String[] strArray = value.split(",");
		List<String> slectdFieldList_excel = Arrays.asList(strArray);
		System.out.println(slectdFieldList_excel);
		for (int i = 0; i < slectdFieldList_excel.size(); i++) {
			String temp = slectdFieldList_excel.get(i).replaceAll("\"", "");
			slectdFieldList_excel.set(i, temp);
			slectdFieldList_excel.get(i).trim();
			System.out.println(slectdFieldList_excel.get(i));
		}

		if (slectdFieldList.equals(slectdFieldList_excel)) {
			bc.passedStep("sucessfully verified that  that user can export successfully with modifying the order "
					+ "of objects selected for export in the custom data export");

		} else {
			bc.failedStep("Selected Export List in CSV file after swapping is not displayed as expected");
		}

	}

	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 3)
	public void verifyModifyObj_CustomSaveReport(String username, String password, String role)
			throws InterruptedException, ParseException, IOException {
		String[] metaDataFields1 = { "CustodianName", "DocFileName", "AttachCount" };
		String reportname = "Report" + Utility.dynamicNameAppender();
		bc.stepInfo("Test case Id: RPMXCON-56544");
		bc.stepInfo("To verify that export should be done successfully after modifying the order of "
				+ "objects in the saved custom export report");
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		if (role == "PA") {
			cddr.selectSourceAsSearch(saveSearchNamePA);
		}
		if (role == "RMU") {
			cddr.selectSourceAsSearch(saveSearchNameRMU);
		}
		cddr.selectMetaDataFields(metaDataFields1);
		cddr.getRunReport().Click();
		cddr.reportRunSuccessMsg();
		cddr.SaveReport(reportname);
		bc.stepInfo("Saved the Custom report " + reportname);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.scrollingToElementofAPage(cddr.getReportName(reportname));
		cddr.getReportName(reportname).Click();
		driver.waitForPageToBeReady();
		List<String> slectdFieldList = new ArrayList<>();
		slectdFieldList = cddr.verifyMetaDatswap();
		String Filename2 = cddr.runReportandVerifyFileDownloaded();
		String value = cddr.csvfileVerification("", Filename2);
		String[] strArray = value.split(",");
		List<String> slectdFieldList_excel = Arrays.asList(strArray);
		System.out.println(slectdFieldList_excel);
		for (int i = 0; i < slectdFieldList_excel.size(); i++) {
			String temp = slectdFieldList_excel.get(i).replaceAll("\"", "");
			slectdFieldList_excel.set(i, temp);
			slectdFieldList_excel.get(i).trim();
			System.out.println(slectdFieldList_excel.get(i));
		}
		ReportsPage rp = new ReportsPage(driver);
		rp.deleteCustomReport(reportname);
		if (slectdFieldList.equals(slectdFieldList_excel)) {
			bc.passedStep("To verify that export should be done successfully after modifying the order of "
					+ "objects in the saved custom export report");
		} else {
			bc.failedStep("Selected Export List in CSV file after swapping is not displayed as expected");
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 4)
	public void verifyReportGeneration_ToggleON(String username, String password, String role)
			throws InterruptedException, IOException {
		String[] metaDataFields1 = { "CustodianName", "DocFileName", "AttachCount" };
		String[] workproductFields = { "All Comments", "All Folders" };
		bc.stepInfo("Test case Id: RPMXCON-56580");
		bc.stepInfo("To verify Custom Document Data Report if ‘Export Object Names’ and if 'Scrub export"
				+ " of special characters' option is toggled 'ON'");
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		if (role == "PA") {
			cddr.selectSourceAsSearch(saveSearchNamePA);
		}
		if (role == "RMU") {
			cddr.selectSourceAsSearch(saveSearchNameRMU);
		}
		cddr.selectMetaDataFields(metaDataFields1);
		cddr.selectDefaultWorkProductFields(workproductFields);
		if (cddr.getToggle_ObjectName().isDisplayed() && cddr.getToggle_ScrubSpecChar().isDisplayed()) {
			bc.passedStep(
					"Export Object Names’ and 'Scrub export of special characters' toggle is displayed and  ‘ON’.");
		} else {
			bc.failedStep("Export Object Names’ and 'Scrub export of special characters' toggle is  not displayed.");
		}
		cddr.runReportandVerifyFileDownloaded();
		bc.passedStep(
				"Custom Document Data Report generated if ‘Export Object Names’ and if 'Scrub export of special characters' option is toggled 'ON'");

	}
	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 5)
	public void verifyRowDisplay_metadataTab(String username, String password, String role)
			throws InterruptedException, IOException {
		String[] workproductFields = { "All Comments", "All Folders","All Tags","All Redaction Tags", };
		bc.stepInfo("Test case Id: RPMXCON-56939");
		bc.stepInfo("Verify Export page shows upto 14 rows default for available objects on All reports");
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		driver.waitForPageToBeReady();
		cddr.getCustomDocumentDataReport().Click();
		driver.waitForPageToBeReady();
		cddr.verifyMetaFieldDisplay();
		cddr.validateWrkprductHeaders(workproductFields);
	}
	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		softAssertion = new SoftAssert();
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		try {
			lp.logout();
			LoginPage.clearBrowserCache();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		try {
			Input in = new Input();
			in.loadEnvConfig();
			driver = new Driver();
			lp = new LoginPage(driver);
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tp.deleteAllTags(tagName);
			SavedSearch savedSearch = new SavedSearch(driver);
			savedSearch.SaveSearchDelete(saveSearchNameRMU);
			lp.logout();
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			savedSearch.SaveSearchDelete(saveSearchNamePA);
			lp.logout();
			LoginPage.clearBrowserCache();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

	}
}
