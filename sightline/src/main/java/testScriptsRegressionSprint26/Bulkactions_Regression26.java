package testScriptsRegressionSprint26;

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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.CodingForm;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Bulkactions_Regression26 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	SoftAssert softAssertion;
	String tagname;
	String foldername;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		baseClass = new BaseClass(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
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
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}	
	
	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object [][]users = {{ Input.rmu1userName, Input.rmu1password }, {Input.pa1userName, Input.pa1password } };
		return users;
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-54487
	 * @Description:verify that in Bulk Assign modal should not display 'InProgress' warning message
	 **/
	@Test(description = "RPMXCON-54487", enabled = true, groups = { "regression" })
	public void verifyBulkAssignModelWarning() throws Exception {
		
		baseClass.stepInfo("RPMXCON-54487");
		baseClass.stepInfo("To verify that in Bulk Assign modal should not display 'InProgress' warning message");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		
		sessionSearch.navigateToSessionSearchPageURL();	
		sessionSearch.basicContentSearch(Input.testData1);		
		sessionSearch.bulkAssign();
		
		if (!baseClass.getWarningsMsgHeader().isElementAvailable(1)) {
			baseClass.passedStep("Warning Not Occuered When Total Count in INPROGRESS As Expected");
		} else {
			baseClass.failedStep(baseClass.getWarningMsg().getText() + " is occurred");
		}
		baseClass.passedStep("Verified - that in Bulk Assign modal should not display 'InProgress' warning message");
		loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-65743
	 * @Description: Verify Warning message to validate for conflicts during bulk tagging from Saved search
	 **/
	@Test(description = "RPMXCON-65743", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyValidateConflictSS(String username, String password) throws Exception {
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		CodingForm coding = new CodingForm(driver);
		SavedSearch search = new SavedSearch(driver);
		TallyPage tally  = new TallyPage(driver);
		SoftAssert asserts = new SoftAssert();
		BatchPrintPage batchPrint = new BatchPrintPage(driver);
		
		String tagname1 = "ATag1" + Utility.dynamicNameAppender();
		String tagname2 = "ATag1" + Utility.dynamicNameAppender();
		String cfName = "Coding" + Utility.dynamicNameAppender();
		String searchName = "Search" + Utility.dynamicNameAppender();
		String expLongText = "Warning: Bulk tagging/untagging may violate coding logic and create coding conflicts";
		String expShortText = "Below is a Tally Report of all your selected documents to be bulk tagged/untagged, reported "
				              + "by the tags each document presently carries in the coding form(s). In this report you can see where coding "
				              + "conflicts may arise. Please confirm that no coding conflicts exist with the set of documents you are about to bulk tag/untag.";
		
		baseClass.stepInfo("RPMXCON - 65743");
		baseClass.stepInfo("Verify Warning message to validate for conflicts during bulk tagging from Saved search");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tags.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tags.createNewTagwithClassificationInRMU(tagname1, Input.tagNamePrev);
		tags.createNewTagwithClassificationInRMU(tagname2, Input.tagNamePrev);		
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname1);
		driver.waitForPageToBeReady();
		sessionSearch.bulkTagExisting(tagname2);	
		coding.navigateToCodingFormPage();
		driver.waitForPageToBeReady();
		coding.saveCodingForm2TagsWithGrpAssociat(cfName, tagname1, tagname2);
		loginPage.logout();
		
		loginPage.loginToSightLine(username, password);
		sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.saveSearch(searchName);
		
		search.savedSearch_Searchandclick(searchName);
		baseClass.waitForElement(search.getSavedSearchToBulkTag());
		search.getSavedSearchToBulkTag().waitAndClick(5);
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		String actLongText = sessionSearch.getPopupHeader().getText();
	    String actShortTex = sessionSearch.getPopupText().getText();
		asserts.assertEquals(expLongText, actLongText);
		asserts.assertEquals(expShortText, actShortTex);
		asserts.assertAll();
		
		search.savedSearch_Searchandclick(searchName);
		baseClass.waitForElement(search.getSavedSearchToBulkTag());
		search.getSavedSearchToBulkTag().waitAndClick(5);
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getReportMetadat());
		asserts.assertTrue(sessionSearch.getTallyContinue().Enabled());
		asserts.assertTrue(sessionSearch.getNoBtn().Enabled());
		asserts.assertAll();
		
		search.savedSearch_Searchandclick(searchName);
		baseClass.waitForElement(search.getSavedSearchToBulkTag());
		search.getSavedSearchToBulkTag().waitAndClick(5);
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		if(sessionSearch.getNoBtn().Enabled()) {
			sessionSearch.getNoBtn().waitAndClick(5);
			baseClass.passedStep("Cancel Button Enabled And Successfully Clicked in POPUP");
		} else {
			baseClass.failedStep("Cancel ButtonDisabled");
		}
		driver.waitForPageToBeReady();
		asserts.assertTrue(search.getSelectWithName(searchName).Visible());
		asserts.assertAll();
	
		search.savedSearch_Searchandclick(searchName);
		baseClass.waitForElement(search.getSavedSearchToBulkTag());
		search.getSavedSearchToBulkTag().waitAndClick(5);
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		sessionSearch.getTallyContinue().waitAndClick(5);
		baseClass.verifyMegaPhoneIconAndBackgroundTasks(true, true);	
		baseClass.waitForElement(batchPrint.getBatchId(1));
		String idValue = batchPrint.getBatchId(1).getText();
		String status = sessionSearch.getRowData_BGT_Page("STATUS", idValue);
		asserts.assertNotNull(status);
		asserts.assertAll();
	
		search.savedSearch_Searchandclick(searchName);
		baseClass.waitForElement(search.getSavedSearchToBulkTag());
		search.getSavedSearchToBulkTag().waitAndClick(5);
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		if(!sessionSearch.getReportTotalCount().isElementAvailable(1) ){
			asserts.assertFalse(sessionSearch.getGoToTallyReport().Enabled());
			asserts.assertAll();
			baseClass.passedStep("Button Disabled As Expected");
		} else {
			baseClass.failedStep("Not as Expected");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getReportTotalCount());		
		String expTotalCount = sessionSearch.getReportTotalCount().getText();
		baseClass.waitForElement(sessionSearch.getReportMetadat());
		String expMetadata = sessionSearch.getReportMetadat().getText();
		baseClass.waitForElement(sessionSearch.getGoToTallyReport());
		sessionSearch.getGoToTallyReport().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(tally.getTallyCount());
		String actTotalCount = tally.getTallyCount().getText();
		baseClass.waitForElement(sessionSearch.getReportMetadat());
		String actMetadata = sessionSearch.getReportMetadat().getText();	
		asserts.assertEquals(expTotalCount, actTotalCount);
		asserts.assertEquals(expMetadata, actMetadata);
		asserts.assertAll();
		baseClass.passedStep("Verify Warning message to validate for conflicts during bulk tagging from Saved search");
		loginPage.logout();
			
	}
}


