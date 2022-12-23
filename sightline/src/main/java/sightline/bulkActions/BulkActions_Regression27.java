package sightline.bulkActions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.CodingForm;
import pageFactory.DocExplorerPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BulkActions_Regression27 {
	
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Utility utility;
	SoftAssert softAssertion;
	ProjectPage projects;
	String projectName;
	String clientName;

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
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		projects = new ProjectPage(driver);

	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility base = new Utility(driver);
			base.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR PROJECTS EXECUTED******");

	}

	/**
	 * @author NA Testcase No:RPMXCON-65734
	 * @Description:verify Warning message to validate for conflicts during bulk tagging from Doc Explorer
	 **/
	@Test(description = "RPMXCON-65734", enabled = true, groups = { "regression" })
	public void verifyValidateConflictDE() throws Exception {
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		CodingForm coding = new CodingForm(driver);
		TallyPage tally  = new TallyPage(driver);
		SoftAssert asserts = new SoftAssert();
		BatchPrintPage batchPrint = new BatchPrintPage(driver);
		DocExplorerPage docExp = new DocExplorerPage(driver);
		
		String tagname1 = "ATag1" + Utility.dynamicNameAppender();
		String tagname2 = "ATag1" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();
		
		String cfName = "Coding" + Utility.dynamicNameAppender();
		String expLongText = "Warning: Bulk tagging/untagging may violate coding logic and create coding conflicts";
		String expShortText = "Below is a Tally Report of all your selected documents to be bulk tagged/untagged, reported "
				              + "by the tags each document presently carries in the coding form(s). In this report you can see where coding "
				              + "conflicts may arise. Please confirm that no coding conflicts exist with the set of documents you are about to bulk tag/untag.";
		
   
		baseClass.stepInfo("RPMXCON - 65734");
		baseClass.stepInfo("verify Warning message to validate for conflicts during bulk tagging from Doc Explorer");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tags.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tags.createNewTagwithClassificationInRMU(tagname1, Input.tagNamePrev);
		tags.createNewTagwithClassificationInRMU(tagname2, Input.tagNamePrev);	
		tags.CreateFolderInRMU(folderName);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname1);
		driver.waitForPageToBeReady();
		sessionSearch.bulkTagExisting(tagname2);	
		coding.navigateToCodingFormPage();
		driver.waitForPageToBeReady();
		coding.saveCodingForm2TagsWithGrpAssociat(cfName, tagname1, tagname2);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(folderName);
		
		docExp.navigateToDocExplorerURL();
		driver.waitForPageToBeReady();
		docExp.performIncludeFolderFilter(folderName);
		driver.waitForPageToBeReady();
		docExp.selectAllDocumentsFromCurrentPage();
		docExp.performBulkTagAction();
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		String actLongText = sessionSearch.getPopupHeader().getText();
	    String actShortTex = sessionSearch.getPopupText().getText();
		asserts.assertEquals(expLongText, actLongText);
		asserts.assertEquals(expShortText, actShortTex);
		asserts.assertAll();
		
		docExp.navigateToDocExplorerURL();
		driver.waitForPageToBeReady();
		docExp.performIncludeFolderFilter(folderName);
		driver.waitForPageToBeReady();
		docExp.selectAllDocumentsFromCurrentPage();
		docExp.performBulkTagAction();
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getReportMetadat());
		asserts.assertTrue(sessionSearch.getTallyContinue().Enabled());
		asserts.assertTrue(sessionSearch.getNoBtn().Enabled());
		asserts.assertAll();
		
		docExp.navigateToDocExplorerURL();
		driver.waitForPageToBeReady();
		docExp.performIncludeFolderFilter(folderName);
		driver.waitForPageToBeReady();
		docExp.selectAllDocumentsFromCurrentPage();
		docExp.performBulkTagAction();
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		if(sessionSearch.getNoBtn().Enabled()) {
			sessionSearch.getNoBtn().waitAndClick(5);
			baseClass.passedStep("Cancel Button Enabled And Successfully Clicked in POPUP");
		} else {
			baseClass.failedStep("Cancel ButtonDisabled");
		}
		driver.waitForPageToBeReady();
		asserts.assertTrue(docExp.getDocExp_SelectAllDocs().Visible());
		asserts.assertAll();
	
		docExp.navigateToDocExplorerURL();
		driver.waitForPageToBeReady();
		docExp.performIncludeFolderFilter(folderName);
		driver.waitForPageToBeReady();
		docExp.selectAllDocumentsFromCurrentPage();
		docExp.performBulkTagAction();
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		sessionSearch.getTallyContinue().waitAndClick(5);
		baseClass.verifyMegaPhoneIconAndBackgroundTasks(true, true);	
		driver.waitForPageToBeReady();
		baseClass.waitForElement(batchPrint.getBatchId(1));
		String idValue = batchPrint.getBatchId(1).getText();
		String status = sessionSearch.getRowData_BGT_Page("STATUS", idValue);
		asserts.assertNotNull(status);
		asserts.assertAll();
	
		docExp.navigateToDocExplorerURL();
		driver.waitForPageToBeReady();
		docExp.performIncludeFolderFilter(folderName);
		driver.waitForPageToBeReady();
		docExp.selectAllDocumentsFromCurrentPage();
		docExp.performBulkTagAction();
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
		baseClass.passedStep("verify Warning message to validate for conflicts during bulk tagging from Doc Explorer");
		loginPage.logout();
			
	}

	/**
	 * @author NA Testcase No:RPMXCON-54490
	 * @Description:To verify that while the 'Total Selected Count' is In Progress,user can select Existing Assignment in the 'Bulk Assign' -UnAssign Documents section
	 **/
	@Test(description = "RPMXCON-54490", enabled = true, groups = { "regression" })
	public void verifySelectExisAssgnWhenCountINPROGRESS() throws Exception{
		SessionSearch session = new SessionSearch(driver);
		AssignmentsPage assign = new AssignmentsPage(driver);
		
		String assignMent = "Assign" + Utility.dynamicNameAppender();	
		
		baseClass.stepInfo("RPMXCON-54490");
		baseClass.stepInfo("To verify that while the 'Total Selected Count' is In Progress, "
				+ "user can select Existing Assignment in the 'Bulk Assign' -UnAssign Documents section");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As : " + Input.rmu1FullName);
		assign.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assign.createAssignment(assignMent, Input.codeFormName);
		driver.waitForPageToBeReady();
		session.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		session.basicContentSearch(Input.searchStringStar);
		driver.waitForPageToBeReady();
		session.bulkAssign();
		
		
		baseClass.waitForElement(session.getUnAssignRadioBtn());
		session.getUnAssignRadioBtn().Click();
		baseClass.waitForElement(session.getExistingAssignmentToUnAssign(assignMent));
		session.getExistingAssignmentToUnAssign(assignMent).Click();

		if(session.getCountOfDocsLoading().Visible() && session.getExistingAssignmentToUnAssign(assignMent).Enabled()) {
			baseClass.passedStep("User Can Select Existing Assignment When Count Document INPROGRESS");
		} else {
			baseClass.failedStep("User Can't Select Existing Assignment When Count Document INPROGRESS");
		}
		baseClass.passedStep("To verify that while the 'Total Selected Count' is In Progress, "
				+ "user can select Existing Assignment in the 'Bulk Assign' -UnAssign Documents section");
		loginPage.logout();
	}
}
