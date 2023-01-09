package sightline.bulkActions;

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
import executionMaintenance.UtilityLog;
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.CodingForm;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BulkAction_Regression_Consilio {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();

	}

	@DataProvider(name = "RMUsers")
	public Object[][] RMUsers() {
		Object[][] Rmusers = { { Input.rmu1userName, Input.rmu1password } };
		return Rmusers;
	}

	/**
	 * @author NA Testcase No:RPMXCON-65660
	 * @Description: Verify Warning message to validate for conflicts during bulk
	 *               tagging from Saved search
	 **/
	@Test(description = "RPMXCON-65660", dataProvider = "RMUsers", enabled = true, groups = { "regression" })
	public void validateWarningMessageConflictFromBasicSearch(String username, String password) throws Exception {
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		CodingForm coding = new CodingForm(driver);
		SavedSearch search = new SavedSearch(driver);
		TallyPage tally = new TallyPage(driver);
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

		baseClass.stepInfo("RPMXCON - 65660");
		baseClass.stepInfo("verify Warning message to validate for conflicts during bulk tagging from Basic search");
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
		// Verify warning message and Cancel button and getTallyContinue button is
		// enabled from basic search and cencel
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExistingWithOutSave(tagname1);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		String actLongText = sessionSearch.getPopupHeader().getText();
		String actShortText = sessionSearch.getPopupText().getText();
		asserts.assertEquals(expLongText, actLongText);
		asserts.assertEquals(expShortText, actShortText);
		baseClass.waitForElement(sessionSearch.getReportMetadat());
		asserts.assertTrue(sessionSearch.getTallyContinue().Enabled());
		asserts.assertTrue(sessionSearch.getNoBtn().Enabled());
		asserts.assertAll();
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		if (sessionSearch.getNoBtn().Enabled()) {
			sessionSearch.getNoBtn().waitAndClick(5);
			baseClass.passedStep("Cancel Button Enabled And Successfully Clicked in POPUP");
		} else {
			baseClass.failedStep("Cancel ButtonDisabled");
		}
		loginPage.logout();

		loginPage.loginToSightLine(username, password);
		// Verify Background task status from basic search
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExistingWithOutSave(tagname1);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		sessionSearch.getTallyContinue().waitAndClick(5);
		baseClass.verifyMegaPhoneIconAndBackgroundTasks(true, true);
		baseClass.waitForElement(batchPrint.getBatchId(1));
		String idValue = batchPrint.getBatchId(1).getText();
		String status = sessionSearch.getRowData_BGT_Page("STATUS", idValue);
		asserts.assertNotNull(status);
		asserts.assertAll();
		loginPage.logout();
		/*
		 * Verify GoToTallyReport disabled and once it get enabled click on
		 * GoToTallyReport
		 */
		loginPage.loginToSightLine(username, password);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExistingWithOutSave(tagname1);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		if (!sessionSearch.getReportTotalCount().isElementAvailable(1)) {
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
		/*Verify Tally report button is displayed and Tally  report*/
		if(sessionSearch.getGoToTallyReport().isElementAvailable(1)) {
		asserts.assertTrue(sessionSearch.getGoToTallyReport().isDisplayed());
		asserts.assertAll();
		baseClass.passedStep("Tally report button is displayed  As Expected");
		} else {
		baseClass.failedStep("Not as Expected");
		}
		sessionSearch.getGoToTallyReport().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(tally.getTallyCount());
		String actTotalCount = tally.getTallyCount().getText();
		baseClass.waitForElement(sessionSearch.getReportMetadat());
		String actMetadata = sessionSearch.getReportMetadat().getText();
		asserts.assertEquals(expTotalCount, actTotalCount);
		asserts.assertEquals(expMetadata, actMetadata);
		asserts.assertAll();
		baseClass.passedStep("Verify Warning message to validate for conflicts during bulk tagging from Basicsearch");
		loginPage.logout();

	}
	/**
	 * @author NA Testcase No:RPMXCON-65720
	 * @Description:
	 * Verify Warning message to validate for conflicts during bulk tagging from Advanced search
	 **/
	@Test(description = "RPMXCON-65720", dataProvider = "RMUsers", enabled = true, groups = { "regression" })
	public void validateWarningMessageConflictFromAdvancedSearch(String username, String password) throws Exception {
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		CodingForm coding = new CodingForm(driver);
		SavedSearch search = new SavedSearch(driver);
		TallyPage tally = new TallyPage(driver);
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

		baseClass.stepInfo("RPMXCON - 65720");
		baseClass.stepInfo("verify Warning message to validate for conflicts during bulk tagging from Advanced search");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tags.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tags.createNewTagwithClassificationInRMU(tagname1, Input.tagNamePrev);
		tags.createNewTagwithClassificationInRMU(tagname2, Input.tagNamePrev);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		sessionSearch.bulkTagExisting(tagname1);
		driver.waitForPageToBeReady();
		sessionSearch.bulkTagExisting(tagname2);
		coding.navigateToCodingFormPage();
		driver.waitForPageToBeReady();
		coding.saveCodingForm2TagsWithGrpAssociat(cfName, tagname1, tagname2);
		loginPage.logout();

		loginPage.loginToSightLine(username, password);
		// Verify warning message and Cancel button and getTallyContinue button is
		// enabled from Advanced search and cancel
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		sessionSearch.bulkTagExistingWithOutSave(tagname1);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		String actLongText = sessionSearch.getPopupHeader().getText();
		String actShortText = sessionSearch.getPopupText().getText();
		asserts.assertEquals(expLongText, actLongText);
		asserts.assertEquals(expShortText, actShortText);
		baseClass.waitForElement(sessionSearch.getReportMetadat());
		asserts.assertTrue(sessionSearch.getTallyContinue().Enabled());
		asserts.assertTrue(sessionSearch.getNoBtn().Enabled());
		asserts.assertAll();
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		if (sessionSearch.getNoBtn().Enabled()) {
			sessionSearch.getNoBtn().waitAndClick(5);
			baseClass.passedStep("Cancel Button Enabled And Successfully Clicked in POPUP");
		} else {
			baseClass.failedStep("Cancel ButtonDisabled");
		}
		loginPage.logout();

		loginPage.loginToSightLine(username, password);
		// Verify Background task status from Advanced search
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		sessionSearch.bulkTagExistingWithOutSave(tagname1);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		sessionSearch.getTallyContinue().waitAndClick(5);
		baseClass.verifyMegaPhoneIconAndBackgroundTasks(true, true);
		baseClass.waitForElement(batchPrint.getBatchId(1));
		String idValue = batchPrint.getBatchId(1).getText();
		String status = sessionSearch.getRowData_BGT_Page("STATUS", idValue);
		asserts.assertNotNull(status);
		asserts.assertAll();
		loginPage.logout();
		/*
		 * Verify GoToTallyReport disabled and once it get enabled click on
		 * GoToTallyReport
		 */
		loginPage.loginToSightLine(username, password);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		sessionSearch.bulkTagExistingWithOutSave(tagname1);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		if (!sessionSearch.getReportTotalCount().isElementAvailable(1)) {
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
		/*Verify Tally report button is displayed and Tally  report*/
		if(sessionSearch.getGoToTallyReport().isElementAvailable(1)) {
		asserts.assertTrue(sessionSearch.getGoToTallyReport().isDisplayed());
		asserts.assertAll();
		baseClass.passedStep("Tally report button is displayed  As Expected");
		} else {
		baseClass.failedStep("Not as Expected");
		}
		sessionSearch.getGoToTallyReport().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(tally.getTallyCount());
		String actTotalCount = tally.getTallyCount().getText();
		baseClass.waitForElement(sessionSearch.getReportMetadat());
		String actMetadata = sessionSearch.getReportMetadat().getText();
		asserts.assertEquals(expTotalCount, actTotalCount);
		asserts.assertEquals(expMetadata, actMetadata);
		asserts.assertAll();
		baseClass.passedStep("Verify Warning message to validate for conflicts during bulk tagging from Adanced search");
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
		System.out.println("**Executed  BulkActions_sprint23 .**");
	}
}

