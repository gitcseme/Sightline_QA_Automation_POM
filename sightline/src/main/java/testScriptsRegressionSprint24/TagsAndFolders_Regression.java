package testScriptsRegressionSprint24;

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
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TagsAndFolders_Regression {
	

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
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
		base = new BaseClass(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage=new TagsAndFoldersPage(driver);
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
	/**
	 * @author N/A Testcase No:RPMXCON-53445
	 * @Description: Verify that if the doc being applied to are standalone with same MD5 hash,
	 *               then Folder propagation should happen      
	 **/
	
	@Test(description = "RPMXCON-53445", enabled = true, groups = { "regression" })
		public void verifyFolderProginIngMD5Hash() throws Exception {
		foldername = "Folder" + Utility.dynamicNameAppender();
		base.stepInfo("RPMXCON-53445");
		base.stepInfo("To Verify that if the doc being applied to are standalone with same MD5 hash, "
				+ "then Folder propagation should happen");
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in As : " + Input.pa1userName);
		base = new BaseClass(driver);
		base.selectproject(Input.additionalDataProject);
		
		tagsAndFolderPage.createNewFolderNotSave(foldername);
		base.waitForElement(tagsAndFolderPage.getPropFolderExactDuplic());
		tagsAndFolderPage.getPropFolderExactDuplic().waitAndClick(3);
		base.waitForElement(tagsAndFolderPage.getSaveFolder());
		tagsAndFolderPage.getSaveFolder().waitAndClick(10);
		base.VerifySuccessMessage("Folder added successfully");
		base.stepInfo("Folder : " + foldername + " Created Successfully With Exact Duplicate (MD5Hash)");
		
		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		dataset.selectDataSetWithName("Media_ID_10_COMBINED_MD5Hash");

		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();
		doc.documentSelection(3);
		doc.bulkFolderExisting(foldername);
		base.passedStep("Verify that if the doc being applied to are standalone with same MD5 hash,"
				+ " then Folder propagation should happen");
		loginPage.logout();
		
	}
	
	/**
	 * @author N/A Testcase No:RPMXCON-53438
	 * @Description: Verify that Tag propagation must be based on the ingested 
	 *               MD5Hash field         
	 **/

	@Test(description = "RPMXCON-53438", enabled = true, groups = { "regression" })
	public void verifyTagProginIngMD5HashField() throws Exception {
		tagname = "Tag" + Utility.dynamicNameAppender();
		base.stepInfo("RPMXCON - 53438");
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in As : " + Input.pa1userName);
		base.stepInfo("To Verify that Tag propagation must be based on the ingested MD5Hash field");
		base = new BaseClass(driver);
		base.selectproject(Input.additionalDataProject);
		
		tagsAndFolderPage.createNewTagNotSave(tagname, Input.tagNamePrev);
		base.waitForElement(tagsAndFolderPage.getPropTagExactDuplic());
		tagsAndFolderPage.getPropTagExactDuplic().waitAndClick(3);
		base.waitForElement(tagsAndFolderPage.getSaveTag());
		tagsAndFolderPage.getSaveTag().waitAndClick(10);
		base.VerifySuccessMessage("Tag added successfully");
		base.stepInfo("Tag : " + tagname + " Created Successfully With Exact Duplicate (MD5Hash)");
		
		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		dataset.selectDataSetWithName("Media_ID_10_COMBINED_MD5Hash");

		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();
		doc.documentSelection(3);
		doc.bulkTagExistingFromDoclist(tagname);
		base.passedStep("Verified -  that Tag propagation must be based on the "
				+ "ingested MD5Hash field");
		loginPage.logout();	

	}

	/**
	 * @author Raghuram A Date: 10/14/22 Modified date:N/A Modified by: N/A
	 *         Description : Verify the count is displayed correctly instead of
	 *         number of documents in tag group /folder group. RPMXCON-52934 Sprint
	 *         23
	 */
	@Test(description = "RPMXCON-52934", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void tagFoderGroupDocToggleAction(String userName, String password, String role) throws Exception {

		String tagGroupName = "aTagGroupR" + Utility.dynamicNameAppender();
		String TagName = "aTagR" + Utility.dynamicNameAppender();
		String TagName2 = "aTagR" + Utility.dynamicNameAppender();

		String folderGroupName = "aFolderGroupR" + Utility.dynamicNameAppender();
		String FolderName = "aFolderR" + Utility.dynamicNameAppender();
		String FolderName2 = "aFolderR" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-52934 - TagsAndFolder Sprint 24");
		base.stepInfo(
				"Verify the count is displayed correctly instead of number of documents in tag group /folder group.");

		loginPage.loginToSightLine(userName, password);
		base.stepInfo("Logged in as : " + userName);

		// Tags And Folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallTagRoot();
		tagsAndFolderPage.createTagGroup(Input.securityGroup, tagGroupName, "Success", null);
		tagsAndFolderPage.getTagNameDataCon(tagGroupName).waitAndClick(5);
		tagsAndFolderPage.CreateTagCC(TagName, Input.securityGroup);
		tagsAndFolderPage.getTagNameDataCon(tagGroupName).waitAndClick(5);
		tagsAndFolderPage.CreateTagCC(TagName2, Input.securityGroup);

		// Tags And Folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallFolderRoot();
		tagsAndFolderPage.createFolderGroup(Input.securityGroup, folderGroupName, "Success", null);
		tagsAndFolderPage.getTagNameDataCon(folderGroupName).waitAndClick(5);
		tagsAndFolderPage.CreateFolderCC(FolderName, Input.securityGroup);
		tagsAndFolderPage.getTagNameDataCon(folderGroupName).waitAndClick(5);
		tagsAndFolderPage.CreateFolderCC(FolderName2, Input.securityGroup);

		// Calculate the unique doc count for the respective searches
		sessionSearch.basicContentSearch(Input.searchString9);
		sessionSearch.addPureHit();
		sessionSearch.getNewSearchButton().waitAndClick(10);
		sessionSearch.multipleBasicContentSearch(Input.searchString2);
		sessionSearch.addPureHit();
		sessionSearch.ViewInDocListWithOutPureHit();
		int aggregateHitCount = saveSearch.docListPageFooterCountCheck();
		base.selectproject();

		// Perform Search and release docs to the tags and folders
		sessionSearch.basicContentSearch(Input.searchString9);
		sessionSearch.bulkTagExisting(TagName);
		sessionSearch.bulkFolderExisting(FolderName);
		base.selectproject();

		// Perform Search and release docs to the tags and folders
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkTagExisting(TagName2);
		sessionSearch.bulkFolderExisting(FolderName2);

		// Navigate to Tags And Folder and verify Tag group Doc count
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.verifyTagDocCount(tagGroupName, aggregateHitCount);

		// Turn off toggle
		tagsAndFolderPage.turnOffToggle(tagsAndFolderPage.getTag_ToggleDocCount());
		base.printResutInReport(
				base.ValidateElement_AbsenceReturn(tagsAndFolderPage.getTagandCount(tagGroupName, aggregateHitCount)),
				"Tag group doc count not displayed after toggle turned off",
				"Tag group doc count still displays after toggle turned off", "Pass");

		// verify Folder group Doc count
		tagsAndFolderPage.verifyFolderDocCount(folderGroupName, aggregateHitCount);

		// Turn off toggle
		tagsAndFolderPage.turnOffToggle(tagsAndFolderPage.getFolder_ToggleDocCount());
		base.printResutInReport(
				base.ValidateElement_AbsenceReturn(
						tagsAndFolderPage.getTagandCount(folderGroupName, aggregateHitCount)),
				"Folder group doc count not displayed after toggle turned off",
				"Folder group doc count still displays after toggle turned off", "Pass");

		loginPage.logout();

	}
}
