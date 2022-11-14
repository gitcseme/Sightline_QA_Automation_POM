package testScriptsRegressionSprint25;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.ss.formula.ptg.GreaterThanPtg;
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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchPrintRegression_25 {

	SoftAssert softassert;
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	BatchPrintPage batchPrint;
	SessionSearch session;
	TagsAndFoldersPage tagsAndFolderPage;
	ProductionPage page;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input in = new Input();
		in.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		batchPrint = new BatchPrintPage(driver);
		session = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		page = new ProductionPage(driver);
		softassert = new SoftAssert();
	}

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password }, };
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify PDF file should be generated at the displayed path of
	 *              batch print background process as per selected 'One PDF for all
	 *              documents' [RPMXCON-47832]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-47832", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validatePdfFileIsGeneratedForAsc(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47832 Batch Print");
		baseClass.stepInfo(
				"Verify PDF file should be generated at the displayed path of batch print background process as per selected 'One PDF for all documents'");

		// configure query & perform Bulk Tag
		session.basicContentSearch(Input.testData1);
		session.bulkTag(Tag);

		// Select TAG & Select Native in basis for printing
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// select sorting "Asc" Order
		batchPrint.selectSortingFromExportPage("ASC");

		// Select Export File Name as 'DocID', select Sort by 'DocID' & verify Batch
		// print is completed & check file download link in background task page
		batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, true, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// check the Downloaded file
		batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify PDF file should be generated at the displayed path of
	 *              batch print background process as per selected 'One PDF for each
	 *              document' with Doc ID as selected in desc sort [RPMXCON-47833]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-47833", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validatePdfFileIsGeneratedAsDesc(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47833 Batch Print");
		baseClass.stepInfo(
				"Verify PDF file should be generated at the displayed path of batch print background process as per selected 'One PDF for each document' with Doc ID as selected in desc sort");

		// configure query & perform Bulk Tag
		session.basicContentSearch(Input.testData1);
		session.bulkTag(Tag);

		// Select TAG & Select Native in basis for printing
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// select sorting "DESC" Order
		batchPrint.selectSortingFromExportPage("DESC");

		// Select Export File Name as 'DocID', select Sort by 'DocID' & verify Batch
		// print is completed & check file download link in background task page
		batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, true, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// check the Downloaded file
		batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify PDF file should be generated at the displayed path of
	 *              batch print background process as per selected 'One PDF for each
	 *              document' with File Name as selected in asc sort [RPMXCON-47834]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-47834", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validatePdfFileIsGeneratedDocfileForAsc(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47834 Batch Print");
		baseClass.stepInfo(
				"Verify PDF file should be generated at the displayed path of batch print background process as per selected 'One PDF for each document' with File Name as selected in asc sort");

		// configure query & perform Bulk Tag
		session.basicContentSearch(Input.testData1);
		session.bulkTag(Tag);

		// Select TAG & Select Native in basis for printing
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// select sorting "Asc" Order
		batchPrint.selectSortingFromExportPage("ASC");

		// Select Export File Name as 'DocFileName', select Sort by 'DocFileName' &
		// select one Pdf for each document
		// verify Batchprint is completed & check file download link in background task
		// page
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.docFileName, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// check the Downloaded file
		batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify that Help is displayed on exception file
	 *              types..RPMXCON-47801
	 */

	@Test(description = "RPMXCON-47801", enabled = true, groups = { "regression" })
	public void verifyHelpDispalyedOnExceptionFileTypes() {

		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		SavedSearch savedSearch = new SavedSearch(driver);

		// Login As User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-47801 Batch Print");
		baseClass.stepInfo("To verify that Help is displayed on exception file types.");

		// performing basic content search and saving the search Result
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);

		// selecting the source in source selection tab and navigating to next tab
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Search", searchName, false);

		// navigating to Exception File Types Tab
		batchPrint.navigateToNextPage(2);

		// clicking Other Exception File Type Help Icon and verifying is that display
		// Other Exception File Type Help Block
		batchPrint.getOtherExceptionFileTypeHelpIcon().waitAndClick(5);
		baseClass.ValidateElement_Presence(batchPrint.getOtherExceptionFileTypeHelpBlock(),
				"Other Exception File Type Help Block");

		// clicking Media Files Help Icon and verifying is that display Media File Help
		// Block
		batchPrint.getMediaFilesHelpIcon().waitAndClick(5);
		baseClass.ValidateElement_Presence(batchPrint.getMediaFilesHelpBlock(), "Media File Help Block");

		// clicking Excel Files Help Icon and verifying is that display Excel File Help
		// Block
		batchPrint.getExcelFilesHelpIcon().waitAndClick(5);
		baseClass.ValidateElement_Presence(batchPrint.getExcelFilesHelpBlock(), "Excel File Help Block");

		baseClass.passedStep("Verified that Help is displayed for Exception File type, Media Files and Excel Files.");

		// deleting the savedSearch
		savedSearch.SaveSearchDelete(searchName);

		// Logout
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that user can select Export file options
	 *              [RPMXCON-47826]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-47826", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyUserCanSelectExportFile(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String[] expectedList = { Input.documentKey, Input.docFileName };

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47826 Batch Print");
		baseClass.stepInfo("To verify that user can select Export file options");

		// configure query & perform Bulk Tag
		session.basicContentSearch(Input.testData1);
		session.bulkTag(Tag);

		boolean[] selectPdfRadio = { false, true };
		for (int i = 0; i < 2; i++) {
			boolean radio = selectPdfRadio[i];

			// Select TAG & Select Native in basis for printing
			batchPrint.navigateToBatchPrintPage();
			batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
			batchPrint.fillingBasisForPrinting(true, true, null);
			batchPrint.navigateToNextPage(1);
			batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

			// filling SlipSheet With metadata
			batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
			batchPrint.navigateToNextPage(1);

			// verify Export file name dropdown
			List<String> exportddList = baseClass.availableListofElements(batchPrint.getExportFileNameDD());
			baseClass.compareArraywithDataList(expectedList, exportddList, true,
					"Export File Dropdown options are : " + exportddList,
					"Export File Dropdown options are not as expected");

			// select sorting "DESC" Order
			batchPrint.selectSortingFromExportPage("DESC");
			int initialBgCount = baseClass.initialBgCount();

			// When Radio is True it will select One PDF for all
			// when Radio is false it will select one PDF for each
			batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, radio, 20);

			// check for notification
			baseClass.checkNotificationCount(initialBgCount, 1);
			int afterBgCount = baseClass.initialBgCount();
			int valuee = Integer.max(initialBgCount, afterBgCount);
			baseClass.digitCompareEquals(valuee, afterBgCount, "Recieved Notification.",
					"Notification is not Displayed.");

			// Verify Actual Document Count
			int index = baseClass.getIndex(batchPrint.getBackGroundPageHeader(), "Actual Docs");
			String actualDocValue = batchPrint.getValuesFromBackGroundPage(index).getText();

			softassert.assertNotEquals(Integer.parseInt(actualDocValue), 0);
			baseClass.passedStep("Actual Doc Count is : " + actualDocValue);

			softassert.assertAll();
			driver.Navigate().refresh();
		}

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate printing document with MEDIA file type
	 *              [RPMXCON-50730]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-50730", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyMediaFileType(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-50730 Batch Print");
		baseClass.stepInfo("Validate printing document with MEDIA file type");

		// configure query & perform Bulk Tag
		int purehit = session.basicMetaDataSearch(Input.docFileExt, null, "mpeg", null);
		session.bulkTag(Tag);

		boolean[] selectPdfRadio = { false, true };
		for (int i = 0; i < 2; i++) {
			boolean radio = selectPdfRadio[i];

			// Select TAG & Select Native in basis for printing
			batchPrint.navigateToBatchPrintPage();
			batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
			batchPrint.fillingBasisForPrinting(true, true, null);
			batchPrint.navigateToNextPage(1);

			// verify Media file count in exception file Tab
			batchPrint.DisableMediaToggle(true, purehit);
			batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

			// filling SlipSheet With metadata
			batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
			batchPrint.navigateToNextPage(1);

			// Generate Batch Print , When Radio is True it will select One PDF for all
			// when Radio is false it will select one PDF for each
			batchPrint.generateBatchPrint(Input.documentKey, Input.documentKey, radio);

			// verify Media Files are SKIPPED
			baseClass.waitForElement(batchPrint.getbackgroundDownLoadLink());
			String status = batchPrint.getbackgroundDownLoadLink().getText();

			baseClass.compareTextViaContains(status, "Error Info", "Media files are Skipped as expected",
					"Media files are not Skipped ");

			// Verify Document Count Since we selected media files
			int index = baseClass.getIndex(batchPrint.getBackGroundPageHeader(), "Actual Docs");
			String actualDocValue = batchPrint.getValuesFromBackGroundPage(index).getText();

			softassert.assertEquals(Integer.parseInt(actualDocValue), 0);
			baseClass.passedStep("Actual Doc Count is 0 since Media files are SKIPPED");

			softassert.assertAll();
			driver.Navigate().refresh();
		}

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that RMU can view the fields in 'Slip Sheets' if it
	 *              is associated to the security group. [RPMXCON-47836]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47836", enabled = true, groups = { "regression" })
	public void verifyFelidsInSlipsheet() throws Exception {
		String Folder = "Folder" + Utility.dynamicNameAppender();
		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-47836 Batch Print");
		baseClass.stepInfo(
				"To verify that RMU can view the fields in 'Slip Sheets' if it is associated to the security group.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// fetch Fields associated with Security group
		security.selectSecurityGroupAndClickOnProjectFldLink(Input.securityGroup);
		baseClass.waitForElementCollection(security.getSelectdFieldsList());
		List<String> sgAssociatedFields = baseClass.availableListofElements(security.getSelectdFieldsList());

		// logout
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure query and bulk folder
		session.basicContentSearch(Input.testData1);
		session.bulkFolder(Folder);

		// Select Folder & navigate to slipsheet tab
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Folder", Folder, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// fetch avaialble fields in slipsheet tab
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		baseClass.waitForElementCollection(batchPrint.getSlipsheetsFields());
		List<String> slipsheetFields = baseClass.availableListofElements(batchPrint.getSlipsheetsFields());

		// verify All the fields associated with Security group is displayed
		baseClass.sortAndCompareList(sgAssociatedFields, slipsheetFields, true, "Ascending",
				"Fields associated to the security group is Displayed",
				"Fields associated to the security group is not Displayed");

		// logout
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify user can on/off the 'Include Applied Redactions' from
	 *              Branding and Redactions tab [RPMXCON-47828]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47828", enabled = true, groups = { "regression" })
	public void verifyUserCanOnAndOffRedactionToggle() throws Exception {
		String Folder = "Folder" + Utility.dynamicNameAppender();
		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-47828 Batch Print");
		baseClass.stepInfo("Verify user can on/off the 'Include Applied Redactions' from Branding and Redactions tab");

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure query and bulk folder
		session.basicContentSearch(Input.testData1);
		session.bulkFolder(Folder);

		// Select Folder & navigate to slipsheet tab
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Folder", Folder, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);

		// OFF Include Applied Redaction toggle
		batchPrint.verifyBrandingAndReadctTab(false, false, null);

		// ON Include Applied Redaction toggle & Configure the positions & verify
		// location are fixed in selected position
		batchPrint.verifyBrandingAndReadctTab(true, true, Input.searchString1);

		// logout
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				loginPage.logoutWithoutAssert();
			} catch (Exception e) {
//										 TODO: handle exception
			}
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
			loginPage.clearBrowserCache();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		try {
			loginPage.clearBrowserCache();
		} finally {
			loginPage.clearBrowserCache();
		}
	}
}
