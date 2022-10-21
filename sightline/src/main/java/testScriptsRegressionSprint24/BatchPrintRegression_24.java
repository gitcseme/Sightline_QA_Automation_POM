package testScriptsRegressionSprint24;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchPrintRegression_24 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	BatchPrintPage batchPrint;
	SessionSearch session;
	TagsAndFoldersPage tagsAndFolderPage;

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
	}

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password }, };
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that user can use the prior production slip sheets
	 *              for batch print.[RPMXCON-47830]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47830", enabled = true, groups = { "regression" })
	public void verifyCanUsePrioProdForBatchPrint() throws Exception {
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-47830 Batch Print");
		baseClass.stepInfo("To verify that user can use the prior production slip sheets for batch print.");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & bulk folder
		session.basicContentSearch(Input.testData1);
		session.bulkFolderExisting(Folder);
		session.ViewInDocListWithOutPureHit();

		// sort in Ascending Order
		doclist.sortColumn(true, Input.documentKey, true);

		// expected Downloaded Document Names list ,Desc Order of LastSaveDate
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.documentKey);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> docidList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Generate Production with dat,TIFF,native & select Slipsheet as docid
		String productionname = "P" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		System.out.println(productionname);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction();
		page.fillingSlipSheetWithMetadataInTiffSection(Input.documentKey);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, Folder, productionname,
				beginningBates);

		// Select Folder & Production
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Folder", Folder, true);
		batchPrint.fillingBasisForPrinting(false, true, productionname);
		batchPrint.navigateToNextPage(1);
		if (!batchPrint.getSlipSheetDD_prod().isElementAvailable(10)) {
			batchPrint.navigateToNextPage(1);
		}

		// filling SlipSheet With metadata
		batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		if (!batchPrint.getSelectExportFileName().isElementAvailable(10)) {
			batchPrint.navigateToNextPage(1);
		}

		// Filling Export File Name as 'Docfilename', select Sort by 'DOCID'
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify ProductionSlipsheet name in Downloaded file
		boolean result = baseClass.compareListViaContainsTrimSpace(actualFileName, docidList);
		baseClass.printResutInReport(result, "Downloaded FileNames has selected production slipsheet Name",
				"Production slipsheet name is not generated", "Pass");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify when user selects a tag/folder with zero count for
	 *              batch print[RPMXCON-49418]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49418", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyWhenUserselectZeroDoc(String username, String password) throws Exception {
		String tagname = "TAG" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49418 Batch Print");
		baseClass.stepInfo("Verify when user selects a tag/folder with zero count for batch print");

		// create Empty Tag
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);

		// Select Tag for source tab
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, tagname, true);
		batchPrint.fillingBasisForPrinting(true, true, null);

		// verify Displayed Message in Analysis tab
		batchPrint.verifyCurrentTab("Analysis");
		batchPrint.verifyAnalysisReportMessage();

		// Delete Tag
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.DeleteTag(tagname, Input.securityGroup);

		// Logout
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that for RMU, when Tag/Folder is unmapped should not be
	 *              displayed under source selection for batch print [RPMXCON-49432]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49432", enabled = true, groups = { "regression" })
	public void verifyUnmappedTagIsnotDisplayed() throws Exception {
		String tagname = "TAG" + Utility.dynamicNameAppender();
		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49432 Batch Print");
		baseClass.stepInfo(
				"Verify that for RMU, when Tag/Folder is unmapped should not be displayed under source selection for batch print");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged In As PA");

		// create Tag
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);

		// Unmap tag from security group
		security.navigateToSecurityGropusPageURL();
		security.unmapTagFromSecurityGrp(Input.securityGroup, tagname);

		// Logout
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As RMU");
		baseClass.selectsecuritygroup(Input.securityGroup);

		// verify Unmapped Tag present in source selection
		batchPrint.navigateToBatchPrintPage();
		boolean tagStatus = batchPrint.fillingSourceSelectionTab(Input.tag, tagname, false);
		baseClass.printResutInReport(tagStatus, "Source slection tab doesnot Contain unmapped tag : " + tagname,
				"Source Selection Tab contains unmapped Tag", "Fail");

		// Logout
		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that for RMU, Tag/Folder should be displayed in that
	 *              security group only [RPMXCON-49429]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49429", enabled = true, groups = { "regression" })
	public void verifyTagOrFolderDislayedInThatSG() throws Exception {
		String tagname = "TAG" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname2 = "TAG" + Utility.dynamicNameAppender();
		String foldername2 = "Folder" + Utility.dynamicNameAppender();
		String securityGrp = "Security" + Utility.dynamicNameAppender();

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement user = new UserManagement(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49429 Batch Print");
		baseClass.stepInfo("Verify that for RMU, Tag/Folder should be displayed in that security group only");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged In As PA");

		// add security grp and assign access to RMU user
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGrp);
		user.assignAccessToSecurityGroups(securityGrp, Input.rmu1userName);

		// Logout
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As RMU");

		// create Tag & Folder in default SG
		baseClass.selectsecuritygroup(Input.securityGroup);
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// create Tag & Folder in Other SG
		baseClass.selectsecuritygroup(securityGrp);
		tagsAndFolderPage.CreateTag(tagname2, securityGrp);
		tagsAndFolderPage.CreateFolder(foldername2, securityGrp);

		// select default sg
		baseClass.selectsecuritygroup(Input.securityGroup);

		// verify only default sg Tags present in source selection tab
		batchPrint.navigateToBatchPrintPage();
		boolean tagStatus = batchPrint.fillingSourceSelectionTab(Input.tag, tagname, true);
		driver.Navigate().refresh();
		boolean tag2Status = batchPrint.fillingSourceSelectionTab(Input.tag, tagname2, false);
		baseClass.printResutInReport(tagStatus, "Source Selection tab Contain tag created in this sg : " + tagname,
				"Source Selection Tab doesnot contains Tag", "Pass");
		baseClass.printResutInReport(tag2Status,
				"Source Selection tab Doesnot Contain tag created in Different sg: " + tagname2,
				"Source Selection Tab contains Tag of other SG", "Fail");

		// verify only default sg Folder present in source selection tab
		driver.Navigate().refresh();
		boolean folderStatus = batchPrint.fillingSourceSelectionTab("Folder", foldername, true);
		driver.Navigate().refresh();
		boolean folder2Status = batchPrint.fillingSourceSelectionTab("Folder", foldername2, false);
		baseClass.printResutInReport(folderStatus,
				"Source Selection tab Contain folder created in this sg : " + foldername,
				"Source Selection Tab doesnot contains Folder", "Pass");
		baseClass.printResutInReport(folder2Status,
				"Source Selection tab Doesnot Contain folder created in other sg : " + foldername2,
				"Source Selection Tab contains Folder of other SG", "Fail");

		// Logout
		loginPage.logout();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// delete sg
		security.deleteSecurityGroups(securityGrp);

		// Logout
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
//		 								 TODO: handle exception
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
