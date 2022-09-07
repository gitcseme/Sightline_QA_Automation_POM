 package testScriptsRegressionSprint20;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
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
import net.lingala.zip4j.exception.ZipException;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchPrintRegression_20 {

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
		Object[][] users = { { Input.pa1userName, Input.pa1password },
				{ Input.rmu1userName, Input.rmu1password }, 
		};
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate different Sorting option for Batch Print(Prior
	 *              Production) [RPMXCON-49159]
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-49159", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortingOptionForBPProd(String username, String password)
			throws InterruptedException, IOException {
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();
		String[] expectedSortByList = { "DocID", "DocFileName", "MasterDate", "EmailSentDate", "CreateDate",
				"CustodianName", "DocDate", "LastEditDate", "LastModifiedDate", "LastSaveDate", "Begin Bates" };

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49159 Batch Print");
		baseClass.stepInfo("Validate different Sorting option for Batch Print(Prior Production)");

		// Create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		if (username.equals(Input.pa1userName)) {
			tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
			tagsAndFolderPage.createNewTagwithClassification(tagName, Input.tagNamePrev);
		} else {
			tagsAndFolderPage.CreateFolderInRMU(foldername);
			tagsAndFolderPage.createNewTagwithClassificationInRMU(tagName, Input.tagNamePrev);
		}

		// Bulk Tag
		session.basicContentSearch(Input.testData1);
		session.bulkFolderExisting(foldername);

		// Generate Production
		String productionname = "P" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		System.out.println(productionname);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);

		// select DAT/Native/TIFF
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction();

		// select Slepsheet From Advanced TAB
		page.slipSheetToggleEnable();
		page.getSlipCalculatedTabSelection().ScrollTo();
		page.availableFieldSelection(Input.batesNumber);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		// Select Search
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Folder", foldername, true);

		// select production
		batchPrint.fillingBasisForPrinting(false, true, productionname);
		batchPrint.navigateToNextPage(1);
		if (!batchPrint.getSlipSheetDD_prod().isElementAvailable(8)) {
			batchPrint.navigateToNextPage(2);
		} else {
			batchPrint.navigateToNextPage(1);
		}

		// verify Sort by list and order
		driver.waitForPageToBeReady();
		List<String> actualSortByList = baseClass.availableListofElements(batchPrint.getSortByDDList());

		String passMsg = "Sort By list is in Same order : " + actualSortByList;
		String failMsg = "Sort By list is not as expected";
		baseClass.compareArraywithDataList(expectedSortByList, actualSortByList, false, passMsg, failMsg);

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
//								 TODO: handle exception
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
