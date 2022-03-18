package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import net.lingala.zip4j.exception.ZipException;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchPrint_Regression1 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	String searchText;
	BatchPrintPage batchPrint;

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		batchPrint = new BatchPrintPage(driver);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 47841 - Verify PDF file should be generated for the selected
	 * production set. Description : To Verify PDF file should be generated for the
	 * selected production set.
	 */
	@Test(enabled = true,groups = { "regression" },priority = 1)
	public void verifyPdfFileGeneratedBySelectedProductionSet() throws InterruptedException {

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47841");

		SessionSearch search = new SessionSearch(driver);
		String searchname = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo("#### Verify PDF file should be generated for the selected production set ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		batchPrint = new BatchPrintPage(driver);

		baseClass.stepInfo("Verify PDF file should be generated for the selected production set");
		batchPrint.BatchPrintWithProduction(searchname, Input.orderCriteria, Input.orderType);
		loginPage.logout();

	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * @Testcase id : 49900 - Verify and generate BatchPrint with Search as source.
	 * @Description : Verify and generate BatchPrint with Search as source
	 */
	@Test(enabled = true,groups = { "regression" },priority = 2)
	public void verifyBatchPrintWithSearchAsSource() throws InterruptedException {
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49900");

		SessionSearch search = new SessionSearch(driver);
		String searchname = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo("#### Verify and generate BatchPrint with Search as source ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		batchPrint = new BatchPrintPage(driver);

		baseClass.stepInfo("Perform batch print by saved search");
		batchPrint.BatchPrintWithProduction(searchname, Input.orderCriteria, Input.orderType);

		baseClass.stepInfo("Navigate to back");
		driver.Navigate().back();

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		batchPrint = new BatchPrintPage(driver);

		baseClass.stepInfo("Perform batch print by saved search");
		batchPrint.BatchPrintWithProduction(searchname, Input.orderCriteria, Input.orderType);

		baseClass.stepInfo("Navigate to back");
		driver.Navigate().back();
		loginPage.logout();

	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * @Testcase id : 48728 - To verify that user can view the total count of Excel
	 *           files is displayed.
	 * @Description : To verify that user can view the total count of Excel files is
	 *              displayed.
	 */
	@Test(enabled = true,groups = { "regression" },priority = 3)
	public void verifyTotalCountExcelFilesDisplayed() throws InterruptedException {
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48728");

		SessionSearch search = new SessionSearch(driver);
		String searchname = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo("#### To verify that user can view the total count of Excel files is displayed. ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		batchPrint = new BatchPrintPage(driver);

		baseClass.stepInfo("Perform batch print by saved search");
		batchPrint.BatchPrintWithProduction(searchname);

		baseClass.stepInfo("Verify Print Excel File Issues Without Skip Excel Print");
		batchPrint.verifyPrintExcelFileIssuesWithoutSkipExcelPrint();

		baseClass.stepInfo("Slip Sheet To Batch Print Creation");
		batchPrint.slipSheetToBatchPrintCreation("CustodianName");

		baseClass.stepInfo("Navigate to back");
		driver.Navigate().back();

		baseClass.stepInfo("Perform batch print by saved search");
		batchPrint.BatchPrintWithProduction(searchname);

		baseClass.stepInfo("Verify Print Excel File Issues Without Skip Excel Print");
		batchPrint.verifyPrintExcelFileIssuesWithoutSkipExcelPrint();

		baseClass.stepInfo(
				"Clickon excel file printing issues without slip excel print and disable place holder toogle.");
		batchPrint.clickOnSkipExcelFilesAndDisablePlaceHolderToogle(true);

		baseClass.stepInfo("Slip Sheet To Batch Print Creation");
		batchPrint.slipSheetToBatchPrintCreation("CustodianName");

		baseClass.stepInfo("Navigate to back");
		driver.Navigate().back();

		baseClass.stepInfo("Perform batch print by saved search");
		batchPrint.BatchPrintWithProduction(searchname);

		baseClass.stepInfo("Verify Print Excel File Issues Without Skip Excel Print");
		batchPrint.verifyPrintExcelFileIssuesWithoutSkipExcelPrint();

		baseClass.stepInfo(
				"Clickon excel file printing issues without slip excel print and disable place holder toogle.");
		batchPrint.clickOnSkipExcelFilesAndDisablePlaceHolderToogle(false);

		batchPrint.verifyRedactorSkipPlaceHolderAndSelectMetaData("CustodianName");

		baseClass.stepInfo("Slip Sheet To Batch Print Creation");
		batchPrint.slipSheetToBatchPrintCreation("CustodianName");
		loginPage.logout();

	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * @Testcase id : 48727 - To verify that user can view the total count of Media
	 *           files is displayed.
	 * @Description : To verify that user can view the total count of Media files is
	 *              displayed.
	 */
	@Test(enabled = true,groups = { "regression" },priority = 4)
	public void verifyTotalCountMediaFilesDisplayed() throws InterruptedException {
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48727 Sprint 12");

		SessionSearch search = new SessionSearch(driver);
		String searchname = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo("#### To verify that user can view the total count of Media files is displayed. ####");

		baseClass.stepInfo("Basic Search");
		search.audioSearch(Input.audioSearch, Input.audioLanguage);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		batchPrint = new BatchPrintPage(driver);

		baseClass.stepInfo("Perform batch print by saved search");
		batchPrint.BatchPrintWithProduction(searchname);

		baseClass.stepInfo("Verify details displayed for media files.");
		batchPrint.verifyDetailsDisplayedForMediaFiles();

		baseClass.stepInfo("Verify media files text field displayed by disabling toogle.");
		batchPrint.verifyMediaFilesFieldsDisplayedByDisableToogle();

		baseClass.stepInfo("Verify media files text field displayed by enabling toogle.");
		batchPrint.verifyMediaFilesFieldsDisplayedByEnablingToogle();

		baseClass.stepInfo("Insert metadata field for media files");
		batchPrint.insertMetaDataFieldForMediaFiles(Input.metaDataName);
		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that PDF should be generated of Batch Print with
	 *              documents having same file name as per selected option 'One PDF
	 *              for all documents', ExportFileName as 'DocID' and Sort By
	 *              DocFileName [RPMXCON-58917]
	 * @throws InterruptedException
	 */
	@Test(enabled = true,dataProvider = "PaAndRmuUser", groups = { "regression" }, priority = 5)
	public void verifyPDFForAllDoc(String username, String password) throws InterruptedException {
		String tagName = Input.randomText + Utility.dynamicNameAppender();
		SessionSearch search = new SessionSearch(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-58917 Batch Print");
		baseClass.stepInfo(
				"Verify that PDF should be generated of Batch Print with documents having same file name as per selected option 'One PDF for all documents', ExportFileName as 'DocID' and Sort By DocFileName");

		//Bulk Tag
		search.basicMetaDataSearch(Input.docFileName, null, "conFIdenTial", null);
		search.bulkTag(tagName);

		//Select TAG
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, tagName, true);
		batchPrint.fillingBasisForPrinting(true, true);
		batchPrint.navigateToNextPage(2);
		
		//filling SlipSheet WIth metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true);
		batchPrint.navigateToNextPage(1);
		
		//Filling Export File Name as 'DocID', select Sort by 'DocFileName' 
		batchPrint.fillingExportFormatPage(Input.documentKey, Input.docFileName, true, 20, false);
		loginPage.logout();
	}
	
	
	@DataProvider(name = "PaAndRmuUser")
	public Object[][] PaAndRmuUser() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password } };
		return users;
	}
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			loginPage.logoutWithoutAssert();
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}
}
