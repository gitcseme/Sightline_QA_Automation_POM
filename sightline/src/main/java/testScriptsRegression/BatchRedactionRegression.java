package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Random;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
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
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchRedactionPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchRedactionRegression {
	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	BatchRedactionPage batch;
	DocViewPage docview;
	SecurityGroupsPage security;
	AnnotationLayer annotation;
	SoftAssert softAssertion;
	RedactionPage redact;
	AssignmentsPage assign;
	MiniDocListPage minidocList;

	String savedsearch = "Savedsearch" + Utility.dynamicNameAppender();
	String searchName = "Search_Name" + Utility.dynamicNameAppender();
	String searchName1 = "SearchName1" + Utility.dynamicNameAppender();
	String searchName2 = "Searchname2" + Utility.dynamicNameAppender();
	String searchName3 = "Searchname3" + Utility.dynamicNameAppender();
	String redactionStyle = "White with black font";
	String tagname;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		
		Input in = new Input();
		in.loadEnvConfig();


			}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
		docview = new DocViewPage(driver);
		batch = new BatchRedactionPage(driver);
		redact = new RedactionPage(driver);
		softAssertion = new SoftAssert();
		assign = new AssignmentsPage(driver);
	}

	/**
	 * @throws InterruptedException
	 * @created By Jeevitha.R Description: Verify that Relevant message appears on
	 *          "Batch Redaction" screen when User tries to Perform Batch Redaction
	 *          on Saved Query.(RPMXCON-53523).
	 */
	@Test(description = "RPMXCON-53523",groups = { "regression" } )
	public void verifyBatchRedactYesPopup() throws InterruptedException {
		// Login as a RMU
		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		base.stepInfo("Test case Id:RPMXCON-53523");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName1);
		System.out.println(searchName1);

		// Edit Profile Language to German
		login.editProfile("German - Germany");
		base.stepInfo("Successfully selected German Language");

		// Verify Analyze Report and View Report
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(searchName1);

		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");

		System.out.println("Test case Failed");

		Thread.sleep(3000);
		// Edit Profile Language to English.
		login.editProfile("English - United States");
		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @throws InterruptedException
	 * @created By Jeevitha.R
	 * @Description: Verify that Relevant message appears on "Batch Redaction"
	 *               screen when User tries to Perform Batch Redaction on Saved
	 *               Query.(RPMXCON-53453)(RPMXCON-53455)
	 * @Description:Verify when user clicks 'Analyze Group for Redaction' for the
	 *                     saved search group with some analyzed saved searches
	 *                     (RPMXCON-53454)
	 * @description:Verify the inline document count details displayed in
	 *                     'Pre-Redaction Report' pop up for the saved search
	 *                     (RPMXCON-53452)
	 * 
	 */
	@Test(description = "RPMXCON-53452,RPMXCON-53453,RPMXCON-53455,RPMXCON-53454",groups = { "regression" })
	public void verifyBatchRedactNoPopup() throws InterruptedException {

		// Login as a RMU
		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Edit Profile Language to English.
		login.editProfile("English - United States");
		base.stepInfo("RPMXCON-53453,RPMXCON-53455,RPMXCON-53454,  Batch Redaction");
		base.stepInfo("Test case Id:RPMXCON-53452");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName2);

		// Verify Analyze Report and View Report

		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(searchName2);

		// verify Popup No Button
		batch.getPopupNoBtn().Click();
		base.stepInfo("Clicked No Button");

		// Verify Inline Documents count
		batch.mySavedSearchRedaction();

		login.logout();

	}

	/**
	 * @author Jeevitha Description:Verify the help tool tip for the 'Select
	 *         Redaction Tag' from 'Pre-Redaction Report' pop up (RPMXCON-53451)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53451",groups = { "regression" })
	public void verifyBatchRedactHelpText() throws InterruptedException {
		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		base.stepInfo("Test case Id:RPMXCON-53451");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName3);

		// Verify Analyze Report and View Report
		driver.waitForPageToBeReady();
		batch.batchRedaction(searchName3);

		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");

		login.logout();
	}

	/**
	 * 
	 * @throws InterruptedException
	 * @Author Jeevitha
	 * @Description: Verify Rollback for the batch redactions for the saved search
	 *               group/saved search after renaming from My searches/shared with
	 *               security group(RPMXCON-53469)
	 * @Description:Verify that once Rollback is successful user should be able to
	 *                     view the report for same from batch redaction history(
	 *                     RPMXCON-48808)
	 * 
	 * 
	 */
	@Test(description = "RPMXCON-48808,RPMXCON-53469",groups = { "regression" })
	public void verifySearchRollback() throws InterruptedException {

		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		base.stepInfo("Test case Id:RPMXCON-53469");
		base.stepInfo("RPMXCON-48808 Batch Redaction");
		session.basicContentSearch(Input.testData1);
		session.saveSearch(savedsearch);
		System.out.println(savedsearch);
		batch.savedSearchBatchRedaction(savedsearch);
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(savedsearch);
		
		// Rename Saved Search
		saveSearch.renameSavedSearch(savedsearch, searchName1);
		System.out.println(searchName1);

		// Rollback Saved Search
		this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");

		base.waitForElement(batch.getRollbackbtn(savedsearch));
		batch.getRollbackbtn(savedsearch).waitAndClick(10);

		base.waitForElement(batch.getPopupYesBtn());
		batch.getPopupYesBtn().waitAndClick(20);
		String ExpectedMsg = "Your request to Roll Back this Batch Redaction has been added to the background.  Once it is complete, the \"bullhorn\" icon in the upper right hand corner will turn red to notify you of the results of your request.";
		base.VerifySuccessMessageB(ExpectedMsg);
		System.out.println("Rollback done successfully");

		// Verify Rollback Success message
		base.waitForElement(batch.getRollbackMsg(savedsearch));
		System.out.println(batch.getRollbackMsg(savedsearch).getText());
		base.stepInfo(batch.getRollbackMsg(savedsearch).getText());

		// Click On CLickHereReport Btn And Verify FIle Name.
		base.waitForElement(batch.getClickHereReportbtn(savedsearch));
		batch.getClickHereReportbtn(savedsearch).Click();
		String fileName = batch.verifyBatchRedactionFileDownload();
		System.out.println("The downloaded File is " + fileName);
		base.stepInfo("The downloaded File is " + fileName);

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @throws InterruptedException Description: Verify rollback for the batch
	 *                              redactions for the saved search group/saved
	 *                              search after deleting it from My searches/shared
	 *                              with security group(RPMXCON-53470).
	 */
	@Test(description = "RPMXCON-53470",groups = { "regression" } )
	public void deleteSavedSearchAndRollback() throws InterruptedException {

		// Login as a RMU
		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		base.stepInfo("Test case Id:RPMXCON-53470");
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName3);
		System.out.println(searchName3);
		batch.savedSearchBatchRedaction(searchName3);
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(searchName3);
		// Delete Saved Search
		saveSearch.SaveSearchDelete(searchName3);

		// Rollback Saved Search
		this.driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");
		base.waitForElement(batch.getRollbackbtn(searchName3));
		batch.getRollbackbtn(searchName3).Click();
		batch.getPopupYesBtn().Click();
		String ExpectedMsg = "Your request to Roll Back this Batch Redaction has been added to the background.  Once it is complete, the \"bullhorn\" icon in the upper right hand corner will turn red to notify you of the results of your request.";
		base.VerifySuccessMessageB(ExpectedMsg);
		System.out.println("Rollback done successfully");

		// Verify Rollback Success message
		base.waitForElement(batch.getRollbackMsg(searchName3));
		System.out.println(batch.getRollbackMsg(searchName3).getText());
		base.stepInfo(batch.getRollbackMsg(searchName3).getText());

		// Delete Search
		//saveSearch.deleteSearch(searchName3, Input.mySavedSearch, "Yes");
		//saveSearch.deleteSearch(searchName2, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @author Jeevitha R Description:Verify the redactions panel when manual/batch
	 *         redactions is not added for the document RPMXCON-53466
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53466",groups = { "regression" })
	public void verifyDocviewRedactionPanelAsRmu() throws InterruptedException {
		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53466");

		// Create saved search
		session.basicContentSearch(Input.searchString1);
		session.ViewInDocView();

		docview.verifyRedactionPanel();
		System.out.println("Redaction panel displayed Successfully");
		base.stepInfo("Redaction panel displayed Successfully");
		login.logout();
	}

	/**
	 * @author Jeevitha R Description:Verify the redactions panel when manual/batch
	 *         redactions is not added for the document RPMXCON-53466
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53466",groups = { "regression" } )
	public void verifyDocviewRedactionPanelAsRev() throws InterruptedException {
		// Login as a RMU
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Test case Id:RPMXCON-53466");

		// Create saved search
		session.basicContentSearch(Input.searchString1);
		session.ViewInDocView();

		docview.verifyRedactionPanel();
		System.out.println("Redaction panel displayed Successfully");
		base.stepInfo("Redaction panel displayed Successfully");
		login.logout();
	}

	/**
	 * @author Jeevitha Description: Verify that SA/DA/PA user after impersonation
	 *         can see batch redactions on doc view(RPMXCON-53419) RPMXCON-53419
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53419",groups = { "regression" } )
	public void verifyDocviewRedactionPanelAsPA() throws InterruptedException {
		// Login as a RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id:RPMXCON-53419");

		// Impersonate As RMU
		base.impersonatePAtoRMU();

		// Create saved search
		session.basicContentSearch(Input.searchString1);
		session.ViewInDocView();

		docview.verifyRedactionPanel();
		System.out.println("Redaction panel displayed Successfully");
		base.stepInfo("Redaction panel displayed Successfully");
		login.logout();
	}

	/**
	 * @author Jeevitha decription :Verify that help icon and help info on mouse
	 *         over the same should be displayed on batch redactions home page
	 *         [Covered localization] RRPMXCON-53370
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53370",groups = { "regression" })
	public void verifyHelpIcon() throws InterruptedException {
		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RRPMXCON-53370");
		driver.getWebDriver().get(Input.url + "BatchRedaction/BatchRedaction");

		batch.BatchRedactionHelpIcon();
		Thread.sleep(3000);
		batch.BatchRedactionHistoryHelpIcon();

		Thread.sleep(3000);
		batch.SearchGroupHelpIcon();
		login.logout();
	}

	/**
	 * @author Raghuram A Description:Verify that Batch Redaction should be
	 *         successful when selected Saved search is with wildcard (?) search
	 *         from Batch Redaction Home page
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53372",groups = { "regression" })
	public void verifyBatchReductionwithWildCardSS() throws InterruptedException {
		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53372");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName3);

		// Verify Analyze Report and View Report
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(searchName3);
		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(searchName3);

		// Delete Search
		saveSearch.deleteSearch(searchName3, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @author Jeevitha Description: Verify when batch redaction executed with same
	 *         doc in different security groups, without shared annotation layer,
	 *         NOT shared redaction tags, no redaction info (coordinates, tags and
	 *         history) shown in the doc in 2nd sec group.(RPMXCON-53425)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53425",groups = { "regression" } )
	public void verifyBatchRedactWithDiffAnnotationLAyer() throws InterruptedException {
		String search_Name = "Searchname4" + Utility.dynamicNameAppender();
		String securityGroup = "SG1" + Utility.dynamicNameAppender();
		String layer = "layer1" + Utility.dynamicNameAppender();
		annotation = new AnnotationLayer(driver);

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53425");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(search_Name);

		// Verify Analyze Report and View Report
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(search_Name);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(search_Name);

		// Delete Search
		saveSearch.deleteSearch(search_Name, Input.mySavedSearch, "Yes");

		login.logout();
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Select Different Annotation Layer
		security = new SecurityGroupsPage(driver);
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);
		annotation.AddAnnotation(layer);

		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		security.selectSecurityGroup(securityGroup);
		security.assignAnnotationToSG(layer);

		session.basicContentSearch(Input.testData1);
		session.bulkRelease(securityGroup);

		base.impersonatePAtoRMU();

		base.selectsecuritygroup(securityGroup);

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.ViewInDocView();

		docview.verifyRedactionPanel();
		System.out.println("Redaction panel displayed Successfully");
		base.stepInfo("Redaction panel displayed Successfully");

		// Delete Security Group
		base.impersonateSAtoPA();
		security.deleteSecurityGroups(securityGroup);
		login.logout();
	}

	/**
	 * @author Jeevitha Description: Verify document from doc view when batch
	 *         redaction is with selected saved search's serch term is present in
	 *         metadata/text file for the document and not in Native/PDF/Viewer
	 *         file(RPMXCON-53424)
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-53424",groups = { "regression" })
	public void verifyBatchRedactionFile() throws InterruptedException, IOException {
		String search_Name = "Searchname5" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53424");

		// Metadata Search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(search_Name);

		// Verify Analyze Report and View Report
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(search_Name);

		batch.getPopupNoBtn().waitAndClick(20);
		driver.Navigate().refresh();
		String fileName = batch.verifyBatchRedactionFileDownload();
		Thread.sleep(10000);// waiting for file download
		batch.readExcel(fileName);

		// Delete Search
		saveSearch.deleteSearch(search_Name, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @author Jeevitha Description: [Covered localization]Verify when user tries to
	 *         resize/edit/move the redaction co-ordinates from document added with
	 *         batch redactions then warning message should be
	 *         displayed("RPMXCON-53411 batch Redcation");
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53411",groups = { "regression" } )
	public void verifyBatchRedaction() throws InterruptedException {
		String search_Name = "Searchname6" + Utility.dynamicNameAppender();

		// impersonate As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53411");

		// create new search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(search_Name);
		batch.savedSearchBatchRedaction(search_Name);
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(search_Name);
		saveSearch.savedSearch_Searchandclick(search_Name);
		saveSearch.docViewFromSS("View in DocView");
		driver.waitForPageToBeReady();

		// Edit Profile Language to German
		login.editProfile("German - Germany");
		batch.verifyAnalyzePopupmsg().waitAndClick(10);
		base.stepInfo("Successfully selected German Language");

		batch.testDD();

		// Edit Profile Language to English.
		login.editProfile("English - United States");

		// Delete Search
		saveSearch.deleteSearch(search_Name, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha Description: Verify the action on click of the 'Trash' icon
	 *         when batch redaction is executed with same/different redaction
	 *         tags(RPMXCON-53468) Description : Verify the doc view redactions
	 *         panel when user re-runs the batch redaction with same redaction
	 *         tag(RPMXCON-53413)
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53413,RPMXCON-53468",groups = { "regression" })
	public void deleteRedactionAndVerifyCount() throws InterruptedException {
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53413 , Test case Id:RPMXCON-53468");

		String str = "Search" + Utility.dynamicNameAppender();
		String str1 = "Search1" + Utility.dynamicNameAppender();
		String tagName = "TAG" + Utility.dynamicNameAppender();

		// Edit Profile Language to English.
		login.editProfile("English - United States");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(str);

		// #1
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(str);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(str);

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		session.ViewInDocView();
		session.checkBatchRedactionCount();

		// #2
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(str);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(str);

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		session.ViewInDocView();
		session.checkBatchRedactionCount();
		session.getTrashCanIcon().waitAndClick(10);
		session.getYesQueryAlert().waitAndClick(10);

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.saveSearch(str1);

		// #1
		Thread.sleep(4000);
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(str1);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(str1);

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		session.ViewInDocView();
		String redact1 = session.checkBatchRedactionCount();

		redact.AddRedaction(tagName, "RMU");

		// #2
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction1(tagName, str1);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(str1);

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		session.ViewInDocView();
		String redact2 = session.checkBatchRedactionCount();
		session.getTrashCanIcon().waitAndClick(10);
		session.getYesQueryAlert().waitAndClick(10);
		softAssertion.assertNotEquals(redact1, redact2);

		// Delete Search
		saveSearch.deleteSearch(str, Input.mySavedSearch, "Yes");
		saveSearch.deleteSearch(str1, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @author jeevitha Description : Verify that Batch Redaction should be
	 *         successful when selected Saved search is used with search crietria
	 *         with AND operator from Batch Redaction Home page(RPMXCON-53352)
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53352,RPMXCON-53353",groups = { "regression" })
	public void BatchRedactionWithANDOperator() throws InterruptedException {
		String search = "Name1" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53352,Test case Id:RPMXCON-53353");

		// Create saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.testData1, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("And");
		session.basicContentSearchWithSaveChanges(Input.audioSearchString1, "Yes", "Third");

		session.saveSearch(search);

		// Verify Analyze Report and View Report
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(search);

		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");

		// verify History status
		batch.verifyHistoryStatus(search);

		batch.verifyRollback(search, "No");
		batch.verifyRollback(search, "Yes");

		// verify Rollback Notification
		batch.rollbackBatchRedactionReport();

		// Delete Search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author jeevitha Description: Verify that Batch Redaction should be
	 *         successful when selected Saved search is used with search crietria
	 *         with OR operator from Batch Redaction Home page(RPMXCON-53354)
	 *
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53354",groups = { "regression" })
	public void BatchRedactionWithOROperator() throws InterruptedException {
		String search = "Name1" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53354");

		// Create saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.testData1, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("OR");
		session.basicContentSearchWithSaveChanges(Input.savedName, "Yes", "Third");

		session.saveSearch(search);

		// Verify Analyze Report and View Report
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(search);

		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");
		// verify History status
		batch.verifyHistoryStatus(search);

		// Delete Search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha Description: Verify that batch redaction with saved search
	 *         query contains UK Registration Plates with regular
	 *         expression(RPMXCON-53381)
	 * @param data
	 * @throws InterruptedException
	 */
	@DataProvider(name = "reserveWords")
	public Object[][] dataMethod() {
		return new Object[][] { { "\"##[a-z][0-9]{1,3}[a-z]{3}\"" }, { "\"##[a-z]{3}[0-9]{1,3}[a-z]\"" },
//			{ "(\"##[a-z]{2}[0-9]{2}[a-z]{3}\")" },
//			{"\"##[0-9]{1,3} [a-z]{1,3}\""},
//			{"\"##[a-z]{1,2} [0-9]{1,4}\""},
//			{"\"##[a-z]{1,3} [0-9]{1,3}\""},
//				{ "\"##[b-d]{2}[1-5]{2}[m-s]{3}\"" }
		};
	}

	@Test(description = "RPMXCON-53381",dataProvider = "reserveWords", groups = { "regression" } )
	public void BatchRedactionWithUKRegPlates(String data) throws InterruptedException {
		String search = "Name1" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53381");

		// Create saved search
		int purehit = session.basicContentSearch(data);
		session.saveSearch(search);

		// Verify Analyze Report and View Report
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(search);

		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");
		// verify History status
		batch.verifyBatchHistoryStatus(search);

		// Delete Search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @author Jeevitha Description: Verify batch redaction when the selected saved
	 *         search's serch term is present in metadata/text file for the document
	 *         and not in Native/PDF/Viewer file(RPMXCON-53423)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53423",groups = { "regression" })
	public void BatchRedactionWithMetaData() throws InterruptedException {
		String search = "Name3" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53423");

		// Create saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearch(Input.testData1);
		session.saveSearch(search);

		// Verify Analyze Report and View Report
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(search);

		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");
		base.waitForElement(batch.getBatchRedactionStatus(search));
		System.out.println(batch.getBatchRedactionStatus(search).getText());
		base.stepInfo(batch.getBatchRedactionStatus(search).getText());

		// Delete Search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @author Jeevitha Description: Verify that batch redaction should be
	 *         successful for the same saved search for which rollback is
	 *         successful(RPMXCON-53412)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53412",groups = { "regression" } )
	public void BatchRedactionForSuccessRollBack() throws InterruptedException {
		String str1 = "Name3" + Utility.dynamicNameAppender();
		String tagName = "TAG" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53412");

		// Pre-requisite

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(str1);

		// Verify Analyze Report and View Report
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(str1);

		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");
		// verify History status
		batch.verifyHistoryStatus(str1);

		// RollBack The saved Search
		batch.verifyRollback(str1, "Yes");

		// #1 bacth redaction with same Tag
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(str1);

		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(str1);

		// Add TAG
		redact.AddRedaction(tagName, "RMU");

		// #2 Batch Redaction with different TAg
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction1(tagName, str1);

		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(str1);

		// Delete TAG
		redact.DeleteRedaction(tagName);

		// Delete Search
		saveSearch.deleteSearch(str1, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha Description:Verify when user adds the this page redaction on
	 *         the document with batch redactions and then deletes the batch
	 *         redactions from doc view(RPMXCON-53410)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53410",groups = { "regression" })
	public void docViewAddRedactionAndDelete() throws InterruptedException {
		String search = "Search" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();
		DocViewRedactions docviewRedact = new DocViewRedactions(driver);

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53410");
		// create tag
		redact.AddRedaction(tagName, "RMU");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(search);

		// BAtch redaction
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(search);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(search);

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		session.ViewInDocView();
		session.checkBatchRedactionCount();
		docviewRedact.performThisPageRedaction(tagName);
		session.getTrashCanIcon().waitAndClick(10);
		session.getYesQueryAlert().waitAndClick(10);

		// Delete Search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha Desciption: Verify the document history after deleting the
	 *         batch redactions/batch redaction component(RPMXCON-53409)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53409",groups = { "regression" })
	public void verifyDocviewHistory() throws InterruptedException {
		DocViewMetaDataPage docviewMetadata = new DocViewMetaDataPage(driver);
		String str = "check" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53409");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(str);

		// #Step-1
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(str);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(str);

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		session.ViewInDocView();
		String batchRedactionCountBeforeDeletion1 = session.checkBatchRedactionCount();
		String allRedactionCountBeforeDeletion1 = docviewMetadata.getAllRedactionCount().getText();
		System.out.println("BAtch Redaction count Before" + allRedactionCountBeforeDeletion1);
		base.stepInfo("BAtch Redaction count Before" + allRedactionCountBeforeDeletion1);

		driver.scrollingToElementofAPage(docviewMetadata.getHistoryTag());
		String rowValueBeforeDeletion1 = docviewMetadata.verifyBrowseAllHistory();

		driver.scrollPageToTop();
		session.getTrashCanIcon().waitAndClick(10);
		session.getYesQueryAlert().waitAndClick(10);

		String batchRedactionCountAfterDeletion1 = session.getBatchRedactionCount().getText();
		String allRedactionCountAfterDeletion1 = docviewMetadata.getAllRedactionCount().getText();
		System.out.println("BAtch Redaction count After" + allRedactionCountAfterDeletion1);
		base.stepInfo("BAtch Redaction count After" + allRedactionCountAfterDeletion1);

		driver.scrollingToElementofAPage(docviewMetadata.getHistoryTag());
		String rowValueAfterDeletion1 = docviewMetadata.verifyBrowseAllHistory();
		System.out.println("History Details After Deleting" + rowValueAfterDeletion1);
		base.stepInfo("History Details After Deleting" + rowValueAfterDeletion1);

		softAssertion.assertNotEquals(allRedactionCountBeforeDeletion1, allRedactionCountAfterDeletion1);
		softAssertion.assertNotEquals(batchRedactionCountBeforeDeletion1, batchRedactionCountAfterDeletion1);
		softAssertion.assertNotEquals(rowValueBeforeDeletion1, rowValueAfterDeletion1);

//		#Step-2
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(str);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(str);

		// To make sure we are in basic search page
		saveSearch.savedSearchToDocView(str);

		String batchRedactionCountBeforeDeletion2 = session.checkBatchRedactionCount();
		String allRedactionCountBeforeDeletion2 = docviewMetadata.getAllRedactionCount().getText();
		System.out.println(allRedactionCountBeforeDeletion2);
		base.stepInfo(allRedactionCountBeforeDeletion2);

		driver.scrollingToElementofAPage(docviewMetadata.getHistoryTag());
		String rowValueBeforeDeletion2 = docviewMetadata.verifyBrowseAllHistory();

		driver.scrollPageToTop();
		session.getTrashCanIcon().waitAndClick(10);
		session.getYesQueryAlert().waitAndClick(10);

		String batchRedactionCountAfterDeletion2 = session.getBatchRedactionCount().getText();
		String allRedactionCountAfterDeletion2 = docviewMetadata.getAllRedactionCount().getText();
		System.out.println(allRedactionCountAfterDeletion2);
		base.stepInfo(allRedactionCountAfterDeletion2);

		driver.scrollingToElementofAPage(docviewMetadata.getHistoryTag());
		String rowValueAfterDeletion2 = docviewMetadata.verifyBrowseAllHistory();
		System.out.println("History Details After Deleting" + rowValueAfterDeletion2);
		base.stepInfo("History Details After Deleting" + rowValueAfterDeletion2);

		softAssertion.assertNotEquals(allRedactionCountBeforeDeletion2, allRedactionCountAfterDeletion2);
		softAssertion.assertNotEquals(batchRedactionCountBeforeDeletion2, batchRedactionCountAfterDeletion2);
		softAssertion.assertNotEquals(rowValueBeforeDeletion2, rowValueAfterDeletion2);

		// Delete Search
		saveSearch.deleteSearch(str, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha Descriptipn: Verify that Rollback should be successful when
	 *         selected Saved search is with wildcard search(RPMXCON-53351)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53351",groups = { "regression" })
	public void BatchRedactionWithWildCard() throws InterruptedException {
		DocViewMetaDataPage docviewMetadata = new DocViewMetaDataPage(driver);
		String search = "Name3" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53351");

		// Create saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearch("\"Test mail*\"");
		session.saveSearch(search);

		// Verify Analyze Report and View Report
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(search);

		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");
		base.waitForElement(batch.getBatchRedactionStatus(search));
		System.out.println(batch.getBatchRedactionStatus(search).getText());
		base.stepInfo(batch.getBatchRedactionStatus(search).getText());

		// verify History status
		batch.verifyBatchHistoryStatus(search);

		batch.verifyRollback(search, "No");
		batch.verifyRollback(search, "Yes");

		// verify Rollback Notification
		batch.rollbackBatchRedactionReport();

		// Verify History In docview Page
		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		session.ViewInDocView();

		driver.scrollingToElementofAPage(docviewMetadata.getHistoryTag());
		String rowValue = docviewMetadata.verifyBrowseAllHistory();
		System.out.println("History Details After Rollback" + rowValue);
		base.stepInfo("History Details After Rollback " + rowValue);

		// Delete Search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @author Jeevitha Description : [Doc View]Verify when user runs the batch
	 *         redactions with the saved search documents having text/retangle/this
	 *         page redactions over the same search terms
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53420",groups = { "regression" })
	public void verifyDocviewRedactionPanel() throws Exception {
		String search = "Search" + Utility.dynamicNameAppender();
		String tagName = "TAG" + Utility.dynamicNameAppender();
		DocViewRedactions docviewredact = new DocViewRedactions(driver);

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53420    Batch Redaction");

		// Create saved search and navigate to doc view
		redact.AddRedaction(tagName, "RMU");
		session.basicContentSearch(Input.stampSelection);
		session.saveSearch(search);
		session.ViewInDocView();

		// Apply REctangle & PAge Redcation
		base.waitForElement(docviewredact.redactionIcon());
		docviewredact.redactionIcon().waitAndClick(20);
		Thread.sleep(Input.wait30); // waiting For Page To Resize
		String count1 = docview.getDocView_AllRedactionCount().getText();
		System.out.println("Before Applying Redaction count is : " + count1);
		base.stepInfo("Before Applying Redaction count is : " + count1);

		driver.Navigate().refresh();

		docviewredact.redactRectangleUsingOffset(-8, 10, 100, 200);
		docviewredact.selectingRedactionTag2(tagName);
		base.stepInfo("Rectangle Redaction is completed");
		Thread.sleep(Input.wait30); // NEed Some Time BEtween Redactions
		docviewredact.performThisPageRedaction(tagName);
		base.stepInfo("This Page Redaction is completed");

		String count2 = docview.getDocView_AllRedactionCount().getText();
		System.out.println("After Applying Redaction count is : " + count2);
		base.stepInfo("After Applying Redaction count is : " + count2);

		// Perform Batch Redaction
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(search);

		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");

		// verify History status
		batch.verifyBatchHistoryStatus(search);

		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.ViewInDocView();
		session.checkBatchRedactionCount();

		// Delete Search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");
		login.logout();
	}

	
	/**
	 * @author Raghuram A Description: Verify Redactions menu is selected from doc
	 *         view and then completes document then selected panels/menus
	 *         previously selected should remain on document navigation :
	 *         RPMXCON-53418
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-53418",groups = { "regression" })
	public void verifyBatchReductionMenu() throws InterruptedException, AWTException {
		String searchName = "Searchname3" + Utility.dynamicNameAppender();
		String assignName = "assignName" + Utility.dynamicNameAppender();
		int latencyCheckTime = 5;
		String passMessage = "Redaction Window is present";
		String failureMsg = "Redaction Window not present";

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53418");

		// Create saved search
		 session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// Verify Analyze Report and View Report driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(searchName);
		batch.getConfirmationBtn("Yes").waitAndClick(5);
		batch.verifyHistoryStatus(searchName);

		// Launch DocVia via Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchToBulkAssign().waitAndClick(5);
		base.stepInfo("Clicked Assign Icon");
		Element loadingElement = saveSearch.getbulkAssignTotalCountLoad();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		assign.assignDocstoNewAssgn(assignName);
		assign.quickAssignmentCreation(assignName, Input.codeFormName);
		assign.quickAssignToggles(false, false, true, false, false, false, false, false, false);
		assign.saveAssignment(assignName, Input.codeFormName);
		assign.selectAssignmentToView(assignName);
		assign.assignmentActions("Edit");

		// edit assignment and add reviewers in the assignment
		driver.waitForPageToBeReady();
		assign.addReviewerAndDistributeDocsT(assignName);
		base.stepInfo("Reviewers are added to the assignment successfully");

		login.logout();

		// login as RMU
		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		batch.verifyBatchReductionMenuFlow(assignName);
		driver.Navigate().refresh();
		login.logout();
	}

	

	/**
	 * @author Jeevitha Description: Verify the doc view redactions panel when user
	 *         re-runs the batch redaction with different redaction
	 *         tag(RPMXCON-53416)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53416",groups = { "regression" } )
	public void verifyHistoryWIthdifferentTAg() throws InterruptedException {
		String search = "Search01" + Utility.dynamicNameAppender();
		tagname = "Tag01" + Utility.dynamicNameAppender();
		DocViewMetaDataPage docviewMetadata = new DocViewMetaDataPage(driver);

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53416  Batch Redaction");

		// Create saved search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(search);

		// #1
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, search);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.waitUntilStatusUpdate(search);

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		session.ViewInDocView();
		String redact1 = session.checkBatchRedactionCount();
		System.out.println("Batch redaction Count : " + redact1);
		base.stepInfo("Batch redaction Count : " + redact1);

		driver.scrollingToElementofAPage(docviewMetadata.getHistoryTag());
		String rowValue1 = docviewMetadata.verifyBrowseAllHistory();
		System.out.println(rowValue1);

		redact.AddRedaction(tagname, "RMU");

		// #2
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction1(tagname, search);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus2(search, purehit);
		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		session.ViewInDocView();
		String redact2 = session.checkBatchRedactionCount();
		System.out.println("Batch redaction Count : " + redact2);
		base.stepInfo("Batch redaction Count : " + redact2);

		driver.scrollingToElementofAPage(docviewMetadata.getHistoryTag());
		String rowValue2 = docviewMetadata.verifyBrowseAllHistory();
		System.out.println(rowValue2);

		// Delete Search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha Description : Verify that new batch ID should be generated
	 *         in batch redaction history when user re-runs the batch redaction with
	 *         different redaction tag(RPMXCON-53415)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53415",groups = { "regression" } )
	public void verifyHistoryWIthdifferentTag1() throws InterruptedException {
		String search = "Search01" + Utility.dynamicNameAppender();
		tagname = "Tag01" + Utility.dynamicNameAppender();
		DocViewMetaDataPage docviewMetadata = new DocViewMetaDataPage(driver);

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53415  Batch Redaction");

		// Create saved search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(search);

		// #1
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, search);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus2(search, purehit);

		driver.waitForPageToBeReady();
		String batchId1 = batch.getBatchId(search).getText();
		String RedactionTag1 = batch.getRedactionTag(search).getText();
		System.out.println(
				"Saved Search : " + search + " , Batch ID : " + batchId1 + " & Redaction Tag : " + RedactionTag1);
		base.stepInfo("Saved Search : " + search + " , Batch ID : " + batchId1 + " & Redaction Tag : " + RedactionTag1);

		redact.AddRedaction(tagname, "RMU");

		// #2
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction1(tagname, search);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.waitUntilStatusUpdate(search);

		driver.waitForPageToBeReady();
		String batchId2 = batch.getBatchId(search).getText();
		String RedactionTag2 = batch.getRedactionTag(search).getText();
		System.out.println(
				"Saved Search : " + search + " , Batch ID : " + batchId2 + " & Redaction Tag : " + RedactionTag2);
		base.stepInfo("Saved Search : " + search + " , Batch ID : " + batchId2 + " & Redaction Tag : " + RedactionTag2);

		Assert.assertNotEquals(batchId1, batchId2);
		Assert.assertNotEquals(RedactionTag1, RedactionTag2);

		// Delete Search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha Description :Verify that new batch ID should be generated in
	 *         batch redaction history when user re-runs the batch redaction with
	 *         same redaction tag(RPMXCON-53414)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53414",groups = { "regression" })
	public void verifyHistoryWithSameTag() throws InterruptedException {
		String search = "Search01" + Utility.dynamicNameAppender();
		tagname = "Tag01" + Utility.dynamicNameAppender();
		DocViewMetaDataPage docviewMetadata = new DocViewMetaDataPage(driver);

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id:RPMXCON-53414  Batch Redaction");

		// Create saved search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(search);

		// #1
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, search);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.waitUntilStatusUpdate(search);

		driver.waitForPageToBeReady();
		String batchId1 = batch.getBatchId(search).getText();
		String RedactionTag1 = batch.getRedactionTag(search).getText();
		System.out.println(
				"Saved Search : " + search + " , Batch ID : " + batchId1 + " & Redaction Tag : " + RedactionTag1);
		base.stepInfo("Saved Search : " + search + " , Batch ID : " + batchId1 + " & Redaction Tag : " + RedactionTag1);

		// #2
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, search);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus2(search, purehit);

		driver.waitForPageToBeReady();
		String batchId2 = batch.getBatchId(search).getText();
		String RedactionTag2 = batch.getRedactionTag(search).getText();
		System.out.println(
				"Saved Search : " + search + " , Batch ID : " + batchId2 + " & Redaction Tag : " + RedactionTag2);
		base.stepInfo("Saved Search : " + search + " , Batch ID : " + batchId2 + " & Redaction Tag : " + RedactionTag2);

		Assert.assertNotEquals(batchId1, batchId2);
		Assert.assertEquals(RedactionTag1, RedactionTag2);

		// Delete Search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/*
	 * @author Jeevitha Description : Verify when batch redaction executed when
	 * exact dupes in different security groups, without shared annotation layer and
	 * with shared redaction tags, then on doc view no redaction info (coordinates,
	 * tags and history) shown in the dupe (RPMXCON-53428)
	 */
	@Test(description = "RPMXCON-53428",groups = { "regression" })
	public void verifyInDiffSG() throws InterruptedException {
		String securityGroup = "SG0" + Utility.dynamicNameAppender();
		String layer = "Layer00" + Utility.dynamicNameAppender();
		String search = "Search01" + Utility.dynamicNameAppender();

		// Login as a PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id:RPMXCON-53428  Batch Redaction");

		// Select Different Annotation Layer
		security = new SecurityGroupsPage(driver);
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		driver.waitForPageToBeReady();
		security.navigateToSecurityGropusPageURL();
		security.selectSecurityGroup(securityGroup);
		annotation = new AnnotationLayer(driver);
		annotation.AddAnnotation(layer);

		security.navigateToSecurityGropusPageURL();
		security.selectSecurityGroup(securityGroup);
		security.assignAnnotationToSG(layer);

		// Create saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("And");
		session.basicContentSearchWithSaveChanges(Input.testData1, "Yes", "Third");
		session.bulkReleaseNearDupeAndDoc(securityGroup);

		base.stepInfo(" Pre-Requisite Completed ");

		login.logout();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Create saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("And");
		session.basicContentSearchWithSaveChanges(Input.testData1, "Yes", "Third");
		session.saveSearch(search);

		// #1
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, search);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(search);

		driver.waitForPageToBeReady();
		String batchId1 = batch.getBatchId(search).getText();
		String RedactionTag1 = batch.getRedactionTag(search).getText();
		System.out.println(
				"Saved Search : " + search + " , Batch ID : " + batchId1 + " & Redaction Tag : " + RedactionTag1);
		base.stepInfo("Saved Search : " + search + " , Batch ID : " + batchId1 + " & Redaction Tag : " + RedactionTag1);

		String fileName = batch.verifyBatchRedactionFileDownload();
		System.out.println("The downloaded File is " + fileName);
		base.stepInfo("The downloaded File is " + fileName);

		login.logout();
		// Login as a PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Impersonate As RMU
		base.impersonatePAtoRMU();
		base.selectsecuritygroup(securityGroup);

		// Create saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("And");
		session.basicContentSearchWithSaveChanges(Input.testData1, "Yes", "Third");
		session.ViewInDocView();

		docview.verifyRedactionPanel();
		System.out.println("Redaction panel displayed Successfully");
		base.stepInfo("Redaction panel displayed Successfully");

		// Delete SG
		base.impersonateSAtoPA();
		security.deleteSecurityGroups(securityGroup);

		login.logout();
	}

	/*
	 * @author Jeevitha Description : Verify when batch redaction executed when
	 * exact dupes in different security groups, with shared annotation layer and
	 * with shared redaction tags, then on doc view should show redaction info
	 * (RPMXCON-53473)
	 */
	@Test(description = "RPMXCON-53473",groups = { "regression" })
	public void verifyRedactionWithExactDupes() throws InterruptedException {
		String securityGroup = "SG0" + Utility.dynamicNameAppender();
		String layer = "Default Annotation Layer";
		String search = "Search01" + Utility.dynamicNameAppender();

		// Login as a PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id:RPMXCON-53473  Batch Redaction");

		// Select Different Annotation Layer
		security = new SecurityGroupsPage(driver);
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);
		driver.waitForPageToBeReady();
		security.navigateToSecurityGropusPageURL();
		security.selectSecurityGroup(securityGroup);
		security.assignAnnotationToSG("Default Annotation Layer");

		// Create saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("And");
		session.basicContentSearchWithSaveChanges(Input.audioSearch, "Yes", "Third");
		session.bulkReleaseNearDupeAndDoc(securityGroup);

		login.logout();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Create saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("And");
		session.basicContentSearchWithSaveChanges(Input.audioSearch, "Yes", "Third");
		session.saveSearch(search);

		// #1
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, search);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(search);

		driver.waitForPageToBeReady();
		String batchId1 = batch.getBatchId(search).getText();
		String RedactionTag1 = batch.getRedactionTag(search).getText();
		System.out.println(
				"Saved Search : " + search + " , Batch ID : " + batchId1 + " & Redaction Tag : " + RedactionTag1);
		base.stepInfo("Saved Search : " + search + " , Batch ID : " + batchId1 + " & Redaction Tag : " + RedactionTag1);

		String fileName = batch.verifyBatchRedactionFileDownload();
		System.out.println("The downloaded File is " + fileName);
		base.stepInfo("The downloaded File is " + fileName);

		login.logout();
		// Login as a PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Impersonate As RMU
		base.impersonatePAtoRMU();
		base.selectsecuritygroup(securityGroup);

		// Create saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("And");
		session.basicContentSearchWithSaveChanges(Input.audioSearch, "Yes", "Third");
		session.ViewInDocView();

		docview.verifyRedactionPanel();
		System.out.println("Redaction panel displayed Successfully");
		base.stepInfo("Redaction panel displayed Successfully");

		// Delete SG
		base.impersonateSAtoPA();
		security.deleteSecurityGroups(securityGroup);
		login.logout();
	}

	/*
	 * @author jeevitha Description : Verify that with two users under different
	 * security group with different annotation layer should execute batch
	 * redactions with saved search having same documents (RPMXCON-53429)
	 */
	@Test(description = "RPMXCON-53429",groups = { "regression" })
	public void verifyWithTwoUser() throws InterruptedException {
		String securityGroup = "SG0" + Utility.dynamicNameAppender();
		String layer = "Layer0" + Utility.dynamicNameAppender();
		String search = "Search01" + Utility.dynamicNameAppender();

		// Login as a PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id:RPMXCON-53429  Batch Redaction");
		base.stepInfo(
				" Verify that with two users under different security group with different annotation layer should execute batch redactions with saved search having same documents");

		// Select Different Annotation Layer
		security = new SecurityGroupsPage(driver);
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);
		security.navigateToSecurityGropusPageURL();
		driver.waitForPageToBeReady();
		
		security.selectSecurityGroup(securityGroup);
		security.assignRedactionTagtoSG("Default Redaction Tag");
		annotation = new AnnotationLayer(driver);
		annotation.AddAnnotation(layer);

		security.navigateToSecurityGropusPageURL();
		driver.waitForPageToBeReady();
		security.selectSecurityGroup(securityGroup);
		security.assignAnnotationToSG(layer);

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.bulkRelease(securityGroup);

		login.logout();

		// Login as a First User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(search);

		// #1
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, search);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(search);

		driver.waitForPageToBeReady();
		String batchId1 = batch.getBatchId(search).getText();
		String RedactionTag1 = batch.getRedactionTag(search).getText();
		System.out.println(
				"Saved Search : " + search + " , Batch ID : " + batchId1 + " & Redaction Tag : " + RedactionTag1);
		base.stepInfo("Saved Search : " + search + " , Batch ID : " + batchId1 + " & Redaction Tag : " + RedactionTag1);

		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.ViewInDocView();
		docview.verifyRedactionPanel();

		// delete search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");
		login.logout();

		// Login as a Second user
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Impersonate As RMU
		base.impersonatePAtoRMU();
		base.selectsecuritygroup(securityGroup);

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(search);

		// #1
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, search);

		// modifications to do : "Yes" -
		batch.getConfirmationBtn("Yes").waitAndClick(5);

		batch.verifyHistoryStatus(search);

		driver.waitForPageToBeReady();
		String batchId2 = batch.getBatchId(search).getText();
		String RedactionTag2 = batch.getRedactionTag(search).getText();
		System.out.println(
				"Saved Search : " + search + " , Batch ID : " + batchId1 + " & Redaction Tag : " + RedactionTag1);
		base.stepInfo("Saved Search : " + search + " , Batch ID : " + batchId1 + " & Redaction Tag : " + RedactionTag1);

		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.ViewInDocView();
		docview.verifyRedactionPanel();

		// delete search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");

		// Delete SG
		base.impersonateSAtoPA();
		security.deleteSecurityGroups(securityGroup);
		login.logout();
	}

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password }, };
		return users;
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);

		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			//login.switchProjectToEnglish();
			login.logoutWithoutAssert();
		}
		login.closeBrowser();
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		try {
//			driver.scrollPageToTop();
//
//			login.closeBrowser();
		} finally {
//			login.clearBrowserCache();
		}
	}

}
