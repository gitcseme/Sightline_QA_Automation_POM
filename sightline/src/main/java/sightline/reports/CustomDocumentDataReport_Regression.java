package sightline.reports;

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
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CustomDocumentDataReport_Regression {
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
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		//Input in = new Input();
		//in.loadEnvConfig();

		// Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		// Login as a RMU
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		search = new SessionSearch(driver);
		// add comment field
		CommentsPage comments = new CommentsPage(driver);
		comments.AddComments("Comment" + Utility.dynamicNameAppender());
		
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
	@Test(description ="RPMXCON-56390",dataProvider = "Users_PARMU", groups = { "regression" })
	public void navigateToExportReport(String username, String password, String role) {
		bc.stepInfo("Test case Id: RPMXCON-56390");
		bc.stepInfo("To verify that User is able view Custom Document Data Report");
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		String expectedURL = Input.url + "Report/ExportData";
		bc.waitForElement(cddr.getCustomDocumentDataReport());
		SoftAssert softAssertion = new SoftAssert();
		if (cddr.getCustomDocumentDataReport().isDisplayed()) {
			bc.passedStep("Custom Document Data  Report link is displayed in reports landing Page");
			cddr.getCustomDocumentDataReport().Click();
			driver.waitForPageToBeReady();
			bc.stepInfo("Clicked on Custom Document Data  Report link.");
			softAssertion.assertEquals(expectedURL, driver.getUrl());
			softAssertion.assertAll();
			bc.passedStep("***Sucessfully navigated to  Export Page***");
		} else {
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
	@Test(description ="RPMXCON-58591",dataProvider = "Users_PARMU", groups = { "regression" })
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
		String report1 = "Report" + Utility.dynamicNameAppender();
		String report2 = "Report" + Utility.dynamicNameAppender();

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
		softAssertion.assertTrue(actualValue.contains("All Tags\\"+tagName+"\"\"\"CustodianName\"\""));
		softAssertion.assertAll();
		bc.passedStep("Sucessfully verified that verify that Users can save the selected criteria for custom"
				+ " document data report and view the custom report");
		ReportsPage rp = new ReportsPage(driver);
		rp.deleteCustomReport(report1);
	}

	@Test(description ="RPMXCON-56542",dataProvider = "Users_PARMU", groups = { "regression" })
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

	@Test(description ="RPMXCON-56544",dataProvider = "Users_PARMU", groups = { "regression" })
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
	@Test(description ="RPMXCON-56580",dataProvider = "Users_PARMU", groups = { "regression" })
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
	@Test(description ="RPMXCON-56939",dataProvider = "Users_PARMU", groups = { "regression" })
	public void verifyRowDisplay_metadataTab(String username, String password, String role)
			throws InterruptedException, IOException {
		String[] workproductFields = { "All Comments", "All Folders", "All Tags", "All Redaction Tags", };
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

	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-58571",dataProvider = "Users_PARMU", groups = { "regression" })
	public void verifyAndValidateCustomDataReport_BasicSearch(String username, String password, String role)
			throws InterruptedException, ParseException, IOException {
		String[] workProductFields = { tagName };
		bc.stepInfo("Test case Id: RPMXCON-58571");
		bc.stepInfo("Verify saved Document Data Export Report from Basic Search"
				+ " should retain the selected criteria in custom report");
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		// report1
		String[] metaDataFields = { Input.metaDataName };
		String textFormat1 = "039";
		String textFormat2 = "034";
		String report1 = "Report" + Utility.dynamicNameAppender();
		String report2 = "Report" + Utility.dynamicNameAppender();

		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		search = new SessionSearch(driver);
		
		search.basicContentSearch(Input.testData1);
		driver.waitForPageToBeReady();
		search.exportData();
		driver.waitForPageToBeReady();
		cddr.selectExportFieldFormat(textFormat1);
		cddr.selectExportTextFormat(textFormat1);
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		cddr.SaveReport(report1);
		bc.stepInfo("Saved the Custom report " + report1);
		cddr.getRunReport().waitAndClick(3);
		cddr.reportRunSuccessMsg();

		// report2
		bc.selectproject();
		search.basicContentSearch(Input.testData1);
		driver.waitForPageToBeReady();
		search.exportData();
		cddr.selectExportFieldFormat(textFormat2);
		cddr.selectExportTextFormat(textFormat2);
		cddr.selectWorkProductFields(workProductFields);
		cddr.SaveReport(report2);
		bc.stepInfo("Saved the Custom report " + report2);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		cddr.getReportName(report1).Click();
		driver.waitForPageToBeReady();
		cddr.selectSources("Security Groups", Input.securityGroup);
		cddr.validateSelectedExports(workProductFields);
		cddr.validateSelectedExports(metaDataFields);
		cddr.getRunReport().Click();
		cddr.reportRunSuccessMsg();

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		cddr.getReportName(report2).Click();
		driver.waitForPageToBeReady();
		cddr.selectSources("Security Groups", Input.securityGroup);
		driver.scrollPageToTop();
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
		System.out.println(actualValue);
		softAssertion.assertTrue(actualValue.contains("All Tags\\" + tagName + "\"\"\"CustodianName\"\""));
		softAssertion.assertAll();
		bc.passedStep("Sucessfully verified that saved Document Data Export Report from Basic "
				+ "Search should retain the selected criteria in custom report");
		ReportsPage rp = new ReportsPage(driver);
		rp.deleteCustomReport(report1);

	}

	@Test(description ="RPMXCON-58572",dataProvider = "Users_PARMU", groups = { "regression" })
	public void verifyAndValidateCustomDataReport_AdvancedSearch(String username, String password, String role)
			throws InterruptedException, ParseException, IOException {
		String[] workProductFields = { tagName };
		String[] metaDataFields = { Input.metaDataName };
		String[] metaDataField_afterReport = { Input.docId };
		String textFormat1 = "039";
		String textFormat2 = "034";
		String report1 = "Report" + Utility.dynamicNameAppender();
		String report2 = "Report" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-58572");
		bc.stepInfo("Verify saved Document Data Export Report from Advanced Search "
				+ "should retain the selected criteria in custom report");
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		search = new SessionSearch(driver);

		// report1
		search.advancedContentSearch(Input.testData1);
		driver.waitForPageToBeReady();
		search.exportData();
		cddr.validateSourceSelction("advanced search");
		cddr.selectExportFieldFormat(textFormat1);
		cddr.selectExportTextFormat(textFormat1);
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		cddr.SaveReport(report1);
		bc.stepInfo("Saved the Custom report " + report1);
		cddr.getRunReport().waitAndClick(3);
		cddr.reportRunSuccessMsg();

		// report2
		bc.selectproject();
		search.advancedContentSearch(Input.testData1);
		driver.waitForPageToBeReady();
		search.exportData();
		cddr.selectExportFieldFormat(textFormat2);
		cddr.selectExportTextFormat(textFormat2);
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		cddr.SaveReport(report2);
		bc.stepInfo("Saved the Custom report " + report2);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		cddr.getReportName(report1).Click();
		driver.waitForPageToBeReady();
		cddr.selectSources("Security Groups", Input.securityGroup);
		cddr.validateSelectedExports(workProductFields);
		cddr.validateSelectedExports(metaDataFields);
		cddr.getRunReport().Click();
		cddr.reportRunSuccessMsg();

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		cddr.getReportName(report2).Click();
		driver.waitForPageToBeReady();
		cddr.selectSources("Security Groups", Input.securityGroup);
		driver.scrollPageToTop();
		cddr.validateSelectedExports(metaDataFields);
		cddr.validateSelectedExports(workProductFields);
		cddr.selectMetaDataFields(metaDataField_afterReport);
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
		System.out.println(actualValue);
		softAssertion.assertTrue(actualValue.contains("CustodianName\"\"\"All Tags\\" + tagName + "\"\"\"DocID\"\""));
		softAssertion.assertAll();
		bc.passedStep("Sucessfully verified that saved Document Data Export Report from Advacned  "
				+ "Search should retain the selected criteria in custom report");
		ReportsPage rp = new ReportsPage(driver);
		rp.deleteCustomReport(report1);

	}

	@Test(description ="RPMXCON-58573",dataProvider = "Users_PARMU", groups = { "regression" })
	public void verifyAndValidateCustomDataReport_SavedSearch(String username, String password, String role)
			throws InterruptedException, ParseException, IOException {
		String[] workProductFields = { tagName };
		String[] metaDataFields = { Input.metaDataName };
		String textFormat1 = "039";
		String textFormat2 = "034";
		String[] metaDataField_afterReport = { Input.docId };
		String saerchName = "ss" + Utility.dynamicNameAppender();
		String saerchName1 = "ss" + Utility.dynamicNameAppender();
		bc.stepInfo("Test case Id: RPMXCON-58573");
		bc.stepInfo("Verify saved Document Data Export Report from Saved Search"
				+ " should retain the selected criteria in custom report");
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		String report1 = "Report" + Utility.dynamicNameAppender();
		String report2 = "Report" + Utility.dynamicNameAppender();

		// report1
		SavedSearch ss = new SavedSearch(driver);
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		search = new SessionSearch(driver);
		search.advancedContentSearch(Input.testData1);
		search.saveSearchadvanced(saerchName);
		ss.savedSearch_Searchandclick(saerchName);
		ss.getSavedSearchExportButton().Click();
		cddr.validateSourceSelction("save search");
		bc.stepInfo("Navigated from saved search to Export Page");
		cddr.selectExportFieldFormat(textFormat1);
		cddr.selectExportTextFormat(textFormat1);
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		cddr.SaveReport(report1);
		bc.stepInfo("Saved the Custom report " + report1);
		cddr.getRunReport().waitAndClick(3);
		cddr.reportRunSuccessMsg();

		// report2
		bc.selectproject();
		search.advancedContentSearch(Input.testData1);
		search.saveSearchadvanced(saerchName1);
		ss.savedSearch_Searchandclick(saerchName1);
		ss.getSavedSearchExportButton().Click();
		cddr.validateSourceSelction("save search");
		bc.stepInfo("Navigated from saved search to Export Page");
		cddr.selectExportFieldFormat(textFormat2);
		cddr.selectExportTextFormat(textFormat2);
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		cddr.SaveReport(report2);
		bc.stepInfo("Saved the Custom report " + report2);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		cddr.getReportName(report1).Click();
		driver.waitForPageToBeReady();
		cddr.selectSources("Security Groups", Input.securityGroup);
		cddr.validateSelectedExports(workProductFields);
		cddr.validateSelectedExports(metaDataFields);
		cddr.getRunReport().Click();
		cddr.reportRunSuccessMsg();

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		cddr.getReportName(report2).Click();
		driver.waitForPageToBeReady();
		cddr.selectSources("Security Groups", Input.securityGroup);
		driver.scrollPageToTop();
		cddr.validateSelectedExports(metaDataFields);
		cddr.validateSelectedExports(workProductFields);
		cddr.selectMetaDataFields(metaDataField_afterReport);
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
		System.out.println(actualValue);
		softAssertion.assertTrue(actualValue.contains("CustodianName\"\"\"All Tags\\" + tagName + "\"\"\"DocID\"\""));
		softAssertion.assertAll();
		bc.passedStep("Sucessfully verified that saved Document Data Export Report from Saved "
				+ "Search should retain the selected criteria in custom report");
		ReportsPage rp = new ReportsPage(driver);
		rp.deleteCustomReport(report1);

	}
	@Test(description ="RPMXCON-58574",dataProvider = "Users_PARMU", groups = { "regression" })
	public void verifyAndValidateCustomDataReport_DocList(String username, String password, String role)
			throws InterruptedException, ParseException, IOException {
		String[] workProductFields = { tagName };
		String[] metaDataFields = { Input.metaDataName };
		String textFormat1 = "039";
		String textFormat2 = "034";
		String report1 = "Report" + Utility.dynamicNameAppender();
		String report2 = "Report" + Utility.dynamicNameAppender();

		String[] metaDataField_afterReport = { Input.docId };
		bc.stepInfo("Test case Id: RPMXCON-58574");
		bc.stepInfo("Verify saved Document Data Export Report from Doc List"
				+ " should retain the selected criteria in custom report");
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		DocListPage docPage = new DocListPage(driver);
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		search = new SessionSearch(driver);
		// report1
		bc.stepInfo("Basic Content search");
		search.basicContentSearch(Input.TallySearch);
		bc.stepInfo("View in doc list from session search");
		search.ViewInDocList();
		bc.stepInfo("Selecting all DocId's &Navigating from DocList to Export Page");
		docPage.SelectIngAllDocuments();
		cddr.validateSourceSelction("doc list");
		cddr.selectExportFieldFormat(textFormat1);
		cddr.selectExportTextFormat(textFormat1);
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		cddr.SaveReport(report1);
		bc.stepInfo("Saved the Custom report " + report1);
		cddr.getRunReport().waitAndClick(3);
		cddr.reportRunSuccessMsg();
		search.closeExportDataPopup();
		// report2
		bc.selectproject();
		bc.stepInfo("Basic Content search");
		search.basicContentSearch(Input.TallySearch);
		bc.stepInfo("View in doc list from session search");
		search.ViewInDocList();
		bc.stepInfo("Selecting all DocId's &Navigating from DocList to Export Page");
		docPage.SelectIngAllDocuments();
		cddr.validateSourceSelction("doc list");
		cddr.selectExportFieldFormat(textFormat2);
		cddr.selectExportTextFormat(textFormat2);
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		cddr.SaveReport(report2);
		bc.stepInfo("Saved the Custom report " + report2);
		
		cddr.SavedCDDRToExportPage(report1);
		cddr.selectSources("Security Groups", Input.securityGroup);
		cddr.validateSelectedExports(workProductFields);
		cddr.validateSelectedExports(metaDataFields);
		cddr.getRunReport().Click();
		cddr.reportRunSuccessMsg();

		cddr.SavedCDDRToExportPage(report2);
		cddr.selectSources("Security Groups", Input.securityGroup);
		driver.scrollPageToTop();
		cddr.validateSelectedExports(metaDataFields);
		cddr.validateSelectedExports(workProductFields);
		cddr.selectMetaDataFields(metaDataField_afterReport);
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
		System.out.println(actualValue);
		softAssertion.assertTrue(actualValue.contains("CustodianName\"\"\"All Tags\\" + tagName + "\"\"\"DocID\"\""));
		softAssertion.assertAll();
		bc.passedStep("Sucessfully verified that saved Document Data Export Report from DocList "
				+ " should retain the selected criteria in custom report");
		ReportsPage rp = new ReportsPage(driver);
		rp.deleteCustomReport(report1);
		rp.deleteCustomReport(report2);

	}
	@Test(description ="RPMXCON-58570",dataProvider = "Users_PARMU", groups = { "regression" })
	public void verifyAndValidateCustomDataReport_DocExp(String username, String password, String role)
			throws InterruptedException, ParseException, IOException {
		String[] workProductFields = { tagName };
		String[] metaDataFields = { Input.metaDataName };
		String textFormat1 = "039";
		String textFormat2 = "034";
		String[] metaDataField_afterReport = { Input.docId };
		String report1 = "Report" + Utility.dynamicNameAppender();
		String report2 = "Report" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-58570");
		bc.stepInfo("Verify saved Document Data Export Report from Doc Explorer"
				+ " should retain the selected criteria in custom report");
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		// report1
		
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		search = new SessionSearch(driver);
		DocExplorerPage docexpPage = new DocExplorerPage(driver);
		
		bc.stepInfo("Viewing docs in doc explorer page.");
		docexpPage.navigateToDocExplorerPage();
		bc.stepInfo("Selecting all DocId in current doc explorer page.");
		docexpPage.selectAllDocumentsFromCurrentPage();
		docexpPage.docExpToExport();
		bc.stepInfo("Navigating from doc explorer page to Export Page");
		cddr.validateSourceSelction("doc exp");		
		cddr.selectExportFieldFormat(textFormat1);
		cddr.selectExportTextFormat(textFormat1);
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		cddr.SaveReport(report1);
		bc.stepInfo("Saved the Custom report " + report1);
		cddr.getRunReport().waitAndClick(3);
		cddr.reportRunSuccessMsg();

		// report2
		docexpPage.navigateToDocExplorerPage();
		docexpPage.selectAllDocumentsFromCurrentPage();
		driver.waitForPageToBeReady();
		docexpPage.docExpToExport();
		cddr.validateSourceSelction("doc exp");
		
		cddr.selectExportFieldFormat(textFormat2);
		cddr.selectExportTextFormat(textFormat2);
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		cddr.SaveReport(report2);
		bc.stepInfo("Saved the Custom report " + report2);
		cddr.SavedCDDRToExportPage(report1);
		cddr.selectSources("Security Groups", Input.securityGroup);
		cddr.validateSelectedExports(workProductFields);
		cddr.validateSelectedExports(metaDataFields);
		cddr.getRunReport().Click();
		cddr.reportRunSuccessMsg();

		cddr.SavedCDDRToExportPage(report2);
		cddr.selectSources("Security Groups", Input.securityGroup);
		driver.scrollPageToTop();
		cddr.validateSelectedExports(metaDataFields);
		cddr.validateSelectedExports(workProductFields);
		cddr.selectMetaDataFields(metaDataField_afterReport);
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
		System.out.println(actualValue);
		softAssertion.assertTrue(actualValue.contains("CustodianName\"\"\"All Tags\\" + tagName + "\"\"\"DocID\"\""));
		softAssertion.assertAll();
		bc.passedStep("Sucessfully verified that saved Document Data Export Report from Doc Explorer "
				+ " should retain the selected criteria in custom report");
		ReportsPage rp = new ReportsPage(driver);
		rp.deleteCustomReport(report1);

	}
	
	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */

	@Test(description ="RPMXCON-58575",dataProvider = "Users_PARMU",groups = { "regression" })
	public void verifyExportFromTally(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-58575");
		bc.stepInfo("Verify saved Document Data Export Report from Tally/Sub-Tally should retain "
				+ "the selected criteria in custom report");
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as " + role);
		TallyPage tp = new TallyPage(driver);
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		String[] workProductFields = { tagName };
		String[] metaDataFields = { Input.metaDataName };
		String textFormat1 = "039";
		String textFormat2 = "034";
		String[] metaDataField_afterReport = { Input.docId };
		String report1 = "Report" + Utility.dynamicNameAppender();
		String report2 = "Report" + Utility.dynamicNameAppender();

		//report 1
		tp.navigateTo_Tallypage();
		tp.SelectSource_SecurityGroup(Input.securityGroup);
		tp.selectTallyByMetaDataField(Input.metaDataName);
		tp.validateMetaDataFieldName(Input.metaDataName);
		tp.verifyTallyChart();
		tp.tallyActions();
		bc.waitTime(2);
		tp.getTally_actionSubTally().Click();
		tp.selectMetaData_SubTally(Input.MetaDataEAName,1);
		driver.waitForPageToBeReady();
		tp.subTallyActions();
		tp.subTallyToExport();
		
		//cddr.validateSourceSelction("tally");
		cddr.selectExportFieldFormat(textFormat1);
		cddr.selectExportTextFormat(textFormat1);
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		cddr.SaveReport(report1);
		bc.stepInfo("Saved the Custom report " + report1);
		cddr.getRunReport().waitAndClick(3);
		cddr.reportRunSuccessMsg();
		
		//report 2
		tp.navigateTo_Tallypage();
		tp.SelectSource_SecurityGroup(Input.securityGroup);
		tp.selectTallyByMetaDataField(Input.metaDataName);
		tp.validateMetaDataFieldName(Input.metaDataName);
		tp.verifyTallyChart();
		tp.tallyActions();
		bc.waitTime(2);
		tp.getTally_actionSubTally().Click();
		tp.selectMetaData_SubTally(Input.MetaDataEAName,1);
		driver.waitForPageToBeReady();
		tp.subTallyActions();
		tp.subTallyToExport();
		
		//cddr.validateSourceSelction("tally");
		cddr.selectExportFieldFormat(textFormat2);
		cddr.selectExportTextFormat(textFormat2);
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		cddr.SaveReport(report2);
		bc.stepInfo("Saved the Custom report " + report2);
		cddr.SavedCDDRToExportPage(report1);
		cddr.selectSources("Security Groups", Input.securityGroup);
		cddr.validateSelectedExports(workProductFields);
		cddr.validateSelectedExports(metaDataFields);
		cddr.getRunReport().Click();
		cddr.reportRunSuccessMsg();

		cddr.SavedCDDRToExportPage(report2);
		cddr.selectSources("Security Groups", Input.securityGroup);
		driver.scrollPageToTop();
		cddr.validateSelectedExports(metaDataFields);
		cddr.validateSelectedExports(workProductFields);
		cddr.selectMetaDataFields(metaDataField_afterReport);
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
		System.out.println(actualValue);
		softAssertion.assertTrue(actualValue.contains("CustodianName\"\"\"All Tags\\" + tagName + "\"\"\"DocID\"\""));
		softAssertion.assertAll();
		bc.passedStep("Sucessfully verified that saved Document Data Export Report from Tally page "
				+ " should retain the selected criteria in custom report");
		ReportsPage rp = new ReportsPage(driver);
		rp.deleteCustomReport(report1);
		rp.deleteCustomReport(report2);

			
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
		Reporter.setCurrentTestResult(result);
		LoginPage lp = new LoginPage(driver);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			lp.logout();
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

		/*
		 * try { Input in = new Input(); in.loadEnvConfig(); 
		 * driver = new Driver(); 
		 * lp = new LoginPage(driver);
		 *  lp.loginToSightLine(Input.rmu1userName,Input.rmu1password);
		 *  TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
		 * driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		 * tp.deleteAllTags(tagName); 
		 * SavedSearch savedSearch = new SavedSearch(driver);
		 * savedSearch.SaveSearchDelete(saveSearchNameRMU); 
		 * lp.logout();
		 * lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		 * savedSearch.SaveSearchDelete(saveSearchNamePA); 
		 * lp.logout();
		 * LoginPage.clearBrowserCache(); 
		 * lp.quitBrowser(); } 
		 * catch (Exception e) {
		 * lp.quitBrowser(); }
		 */

	}
}
