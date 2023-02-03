package sightline.reports;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.ConceptExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ConceptExplorer_Phase2_Regression {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	ReportsPage reports;
	ConceptExplorerPage conceptExplorer;
	TagsAndFoldersPage tagsAndFolder;
	MiniDocListPage miniDocListPage;
	AssignmentsPage assign;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		 in = new Input();
		 in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		sessionSearch = new SessionSearch(driver);
		reports = new ReportsPage(driver);
		conceptExplorer = new ConceptExplorerPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
	}

	@DataProvider(name = "paRmuUsers")
	public Object[][] paRmuRevUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.shareSearchPA },
				{ Input.rmu1userName, Input.rmu1password, Input.shareSearchDefaultSG } };
		return users;
	}

	@DataProvider(name = "PaAndRmuUser")
	public Object[][] PaAndRmuUser() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}
	
	@DataProvider(name = "paRmu")
	public Object[][] paRmu() {
		Object[][] users = { { Input.pa1userName, Input.pa1password },
				{ Input.rmu1userName, Input.rmu1password} };
		return users;
	}
	
	/**
	 * @author Jayanthi.Ganesan
	 * @param User
	 * @param pwd
	 * @throws Exception
	 */

	//@Test(description = "RPMXCON-56910", enabled = true, dataProvider = "paRmu", groups = { "regression" })
	public void verifyFilters_EmailAllDomains_DocFileType(String User, String pwd) throws Exception {

		baseClass.stepInfo("Test case Id :RPMXCON-56910");
		baseClass.stepInfo("Validate onpage filter for EmailAllDomains  and DocFileType on Concept Explorer Report");

		String analyze = Input.analyzeAt2;
		String analyze3 = Input.analyzeAt3;
		String sourceToSelect = "Security Groups";
		String emailDomain = Input.MetaDataDomainName;
		String emailD0main_1 =Input.domainNameSymphony;
		String fileType = Input.filterDataInput1;

		String[] columnsToSelect = { Input.emailAllDomain, Input.ingDocFileType };

		baseClass.stepInfo("**Login to sightline and Select Project**");
		loginPage.loginToSightLine(User, pwd);

		// Navigate to Concept Explorer page
		baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources
		conceptExplorer.clickSelectSources();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the filter criteria and click “Apply filter”");

		conceptExplorer.filterAction(emailDomain, Input.emailAllDomain, emailD0main_1, false);
		conceptExplorer.filterAction(fileType, Input.docFileType, null, true);
		conceptExplorer.applyFilter("Yes", 10);

		baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());

		// Get Doc count consolidated
		String totalSelectedDocCount = conceptExplorer.getDocCOuntFromHeader().getText();
		String aggregatedDocCount = conceptExplorer.extractStringFromPosition(totalSelectedDocCount, 2);
		baseClass.passedStep("Report Generated with doc count : " + aggregatedDocCount + " which is expected");

		// tiles add to cart
		int resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.addMultipleTilesToCart(resultToAddInCart);

		// view in doc list to verify filters applied returns the docs correctly.
		conceptExplorer.performDocActions("View", "View in DocList");
		baseClass.waitTime(4);
		baseClass.verifyPageNavigation("en-us/Document/DocList");

		// validation for inlcude filters
		DocListPage dlPage = new DocListPage(driver);
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailAllDomain = dlPage.getColumnValue(Input.emailAllDomain, false);
		List<String> docFileType = dlPage.getColumnValue(Input.docFileType, false);
		conceptExplorer.verifyExcludeIcludeFiltersLikeOR_Operator(emailAllDomain, docFileType, emailD0main_1, emailDomain,
				fileType);

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the Exclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(emailDomain, Input.emailAllDomain, emailD0main_1, true);
		conceptExplorer.filterAction(fileType, Input.docFileType, null, true);
		conceptExplorer.applyFilter("Yes", 10);

		baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());
		baseClass.stepInfo("Report Generated.");

		// adding tiles to cart
		conceptExplorer.addMultipleTilesToCart(2);

		// Perform View in DocView List Action
		conceptExplorer.performDocActions("View", "View in DocList");
		baseClass.waitTime(4);
		baseClass.verifyPageNavigation("en-us/Document/DocList");

		// validation of exclude filter functionality
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailAllDomain_Excl = dlPage.getColumnValue(Input.emailAllDomain, false);
		List<String> docFileType_Excl = dlPage.getColumnValue(Input.docFileType, false);
		conceptExplorer.verifyIcludeFiltersLikeOR_Operator(emailAllDomain_Excl, docFileType_Excl, emailD0main_1,
				emailDomain, fileType);

		conceptExplorer.getBackToSourceBtn().Click();
		driver.waitForPageToBeReady();
		conceptExplorer.removeTilesFromCart();

		// Select tile to analyze at second level
		baseClass.stepInfo("**Select tile to analyze at second level**");
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart,1,3);
		// Go to 2nd level
		List<String> expActiveFilters = conceptExplorer.verifyActiveFilters(null);
		conceptExplorer.analyzeAction(analyze);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("2nd")),
				"Page navigated to second level and report generated", "Page didn't navigate to second level", "Pass");
		conceptExplorer.verifyActiveFilters(expActiveFilters);

		// Select tile to analyze at third level
		baseClass.stepInfo("**Select tile to analyze at third level**");
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 1,2);

		// Go to 3rd level
		conceptExplorer.analyzeAction(analyze3);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("3rd")),
				"Page navigated to third level and report generated", "Page didn't navigate to third level", "Pass");
		conceptExplorer.verifyActiveFilters(expActiveFilters);

		// Logout
		loginPage.logout();

	}

	/**
	 * @author Raghuram A
	 * @Date: 09/04/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Validate onpage filter for Tags and MasterDate on Concept
	 *              Explorer Report RPMXCON-56909
	 */
	@Test(description = "RPMXCON-56909", enabled = true, dataProvider = "paRmuUsers", groups = { "regression" })
	public void verifyConceptExpFiltersFunctionality(String userName, String password, String role) throws Exception {

		baseClass.failedMessage("Test case Id :RPMXCON-56909 - Expected Failure");
		baseClass.stepInfo("Test case Id :RPMXCON-56909 ");
		baseClass.stepInfo("Validate onpage filter for Tags and MasterDate on Concept Explorer Report");
		baseClass.failedMessage("Make sure the project has valid expected datas");

		String analyze = Input.analyzeAt2;
		String analyze3 = Input.analyzeAt3;
		String sourceToSelect = "Security Groups";
		String masterDate = "1980/01/01";
		String TagName = "Tag" + Utility.dynamicNameAppender();
		String TagName1 = "Tag1" + Utility.dynamicNameAppender();

		baseClass.stepInfo("**Login to sightline and Select Project**");
		loginPage.loginToSightLine(userName, password);

		// Perform Search and Bulk Tag - 1
		sessionSearch.basicMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		sessionSearch.verifyBulkTag(TagName);
		baseClass.selectproject();

		// Perform Search and Bulk Tag - 2
		sessionSearch.basicMetaDataSearch(Input.metaDataName, null, Input.metaDataCN, null);
		sessionSearch.verifyBulkTag(TagName1);

		// Navigate to Concept Explorer page
		baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
		reports.navigateToReportsPage("Concept Explorer Report");

		// click on Apply Filter button Tags: Include - true - MasterDate: On
		conceptExplorer.customizedFilterCheck(sourceToSelect, Input.securityGroup, TagName, "Tags", TagName1,
				masterDate, "", true, false, "On", true, true, Input.metaDataName, Input.masterDateText,
				"IncludeAndInclude", "masterDate", "All", Input.metaDataCustodianNameInput, Input.metaDataCN, false,
				"");

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
		reports.navigateToReportsPage("Concept Explorer Report");

		// click on Apply Filter button Tags: Exclude - false - MasterDate: On
		conceptExplorer.customizedFilterCheck(sourceToSelect, Input.securityGroup, TagName, "Tags", TagName1,
				masterDate, "", false, false, "On", true, true, Input.metaDataName, Input.masterDateText,
				"ExcludeAndInclude", "masterDate", "All", Input.metaDataCustodianNameInput, Input.metaDataCN, false,
				"");

		// Back to source
		conceptExplorer.getBackToSourceBtn().Click();
		driver.waitForPageToBeReady();

		// Select tile to analyze at second level
		baseClass.stepInfo("**Select tile to analyze at second level**");
		baseClass.waitForElementCollection(conceptExplorer.getDataAddedInCart());
		int resultToAddInCart = conceptExplorer.getDataAddedInCart().size();
		conceptExplorer.addedTileSelectionBasedOnChildCount(resultToAddInCart, 3);

		// Go to 2nd level
		List<String> expActiveFilters = conceptExplorer.verifyActiveFilters(null);
		conceptExplorer.analyzeAction(analyze);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("2nd")),
				"Page navigated to second level and report generated", "Page didn't navigate to second level", "Pass");
		conceptExplorer.verifyActiveFilters(expActiveFilters);

		// Select tile to analyze at third level
		baseClass.stepInfo("**Select tile to analyze at third level**");
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 1);

		// Go to 3rd level
		conceptExplorer.analyzeAction(analyze3);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("3rd")),
				"Page navigated to third level and report generated", "Page didn't navigate to third level", "Pass");
		conceptExplorer.verifyActiveFilters(expActiveFilters);

		// Tag deletion
		baseClass.stepInfo("Initiating tag deletion");
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.waitForPageToBeReady();
		tagsAndFolder.deleteAllTags(TagName);
		tagsAndFolder.deleteAllTags(TagName1);

		// Logout
		loginPage.logout();

	}

	/**
	 * @author Raghuram A
	 * @Date: 09/05/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Validate onpage filter for CustodianName and Tags on Concept
	 *              Explorer Report RPMXCON-56907
	 */
	//@Test(description = "RPMXCON-56907", enabled = true, dataProvider = "paRmuUsers", groups = { "regression" })
	public void verifyConceptExpFiltersFunctionalityEX(String userName, String password, String role) throws Exception {

		baseClass.stepInfo("Test case Id :RPMXCON-56907 ");
		baseClass.stepInfo("Validate onpage filter for CustodianName and Tags  on Concept Explorer Report");
		baseClass.failedMessage("Make sure the project has valid expected datas");

		String analyze = Input.analyzeAt2;
		String analyze3 = Input.analyzeAt3;
		String sourceToSelect = "Security Groups";
		String TagName = "Tag" + Utility.dynamicNameAppender();
		String docFileType = "MS Outlook Message";

		baseClass.stepInfo("**Login to sightline and Select Project**");
		loginPage.loginToSightLine(userName, password);

		// Perform Search and Bulk Tag
		sessionSearch.basicMetaDataSearch(Input.docFileType, null, docFileType, null);
		sessionSearch.verifyBulkTag(TagName);

		// Navigate to Concept Explorer page
		baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
		reports.navigateToReportsPage("Concept Explorer Report");

		// click on Apply Filter button Tags: Include MasterDate: On
		conceptExplorer.customizedFilterCheck(sourceToSelect, Input.securityGroup, Input.metaDataCustodianNameInput,
				Input.metaDataName, Input.metaDataCN, TagName, "", true, false, "On", true, true, Input.metaDataName,
				Input.docFileType, "IncludeAndExclude", "Tags", "default", Input.metaDataCustodianNameInput,
				Input.metaDataCN, false, "");

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
		reports.navigateToReportsPage("Concept Explorer Report");

		// click on Apply Filter button Tags: Exclude MasterDate: On
		conceptExplorer.customizedFilterCheck(sourceToSelect, Input.securityGroup, Input.metaDataCustodianNameInput,
				Input.metaDataName, Input.metaDataCN, TagName, "", false, false, "On", true, true, Input.metaDataName,
				Input.docFileType, "ExcludeAndExclude", "Tags", "default", Input.metaDataCustodianNameInput,
				Input.metaDataCN, false, "");

		// Back to source
		conceptExplorer.getBackToSourceBtn().Click();
		driver.waitForPageToBeReady();

		// Select tile to analyze at second level
		baseClass.stepInfo("**Select tile to analyze at second level**");
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		int resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 2);

		// Go to 2nd level
		List<String> expActiveFilters = conceptExplorer.verifyActiveFilters(null);
		conceptExplorer.analyzeAction(analyze);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("2nd")),
				"Page navigated to second level and report generated", "Page didn't navigate to second level", "Pass");
		conceptExplorer.verifyActiveFilters(expActiveFilters);

		// Select tile to analyze at third level
		baseClass.stepInfo("**Select tile to analyze at third level**");
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 0);

		// Go to 3rd level
		conceptExplorer.analyzeAction(analyze3);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("3rd")),
				"Page navigated to third level and report generated", "Page didn't navigate to third level", "Pass");
		conceptExplorer.verifyActiveFilters(expActiveFilters);

		// Tag deletion
		baseClass.stepInfo("Initiating tag deletion");
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.waitForPageToBeReady();
		tagsAndFolder.deleteAllTags(TagName);

		// Logout
		loginPage.logout();

	}
	

	/**
	 * @author Raghuram A
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Date: 08/08/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Validate Masterdate range on Concept Explorer report.
	 *              RPMXCON-56887
	 */
	//@Test(description = "RPMXCON-56887", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyIncludeExcludeFiltersFunctionality_ConceptExp(String userName, String password, String role)
			throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-56887");
		baseClass.stepInfo("Validate Masterdate range on Concept Explorer report");

		String searchText = "Auto";
		String sourceToSelect = "Searches";
		String sgToSelect = Input.mySavedSearch;
		String savedSearchName = "SavedSearch-" + UtilityLog.dynamicNameAppender();
		String[] selectSourceList = { "Security Groups", "Searches", "Project", "Folders" };
		String[] conditionsToCheck = { "Before", "After", "On", "Between" };
		String expectedDateInput = Input.expectedDateInput;
		String expectedToDateInput = Input.expectedToDateInput;

		// Login as PA
		baseClass.stepInfo("**Step-1 Login as Project Admin**");
		loginPage.loginToSightLine(userName, password);

		// Pre-requesties
		baseClass.stepInfo("Pre-requesties search creation");
		sessionSearch.basicContentSearch(searchText);
		sessionSearch.saveSearchAtAnyRootGroup(savedSearchName, sgToSelect);

		// masterDateVerifications via Concept explorer - DocList
		conceptExplorer.masterDateVerifications(selectSourceList, sourceToSelect, sgToSelect, savedSearchName,
				expectedDateInput, expectedToDateInput, conditionsToCheck);

		// Delete search
		savedSearch.deleteSearch(savedSearchName, Input.mySavedSearch, "Yes");

		// Logout
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param User
	 * @param pwd
	 * @throws Exception
	 */

	@Test(description="RPMXCON-56908",enabled = true, dataProvider = "paRmu", groups = { "regression" })
	public void verifyConceptExpFiltersFunctionality(String User, String pwd) throws Exception {
		baseClass.stepInfo("Test case Id :RPMXCON-56908 ");
        baseClass.stepInfo("Validate onpage filter for EmailRecipientNames  and EmailAllDomain on "
                + "Concept Explorer Report");
        String analyze = Input.analyzeAt2;
        String analyze3 = Input.analyzeAt3;
        String sourceToSelect = "Security Groups";
        String[] columnsToSelect = { "EmailAllDomains", "EmailRecipientNames" };        
        String emailrecep1 = Input.emailRecipientName1;
        String emailrecep2 = Input.emailReciepientName2;
        String doaminNAme = Input.domainNameConsilio;   
        baseClass.stepInfo("**Login to sightline and Select Project**");
        loginPage.loginToSightLine(User, pwd);
        // Navigate to Concept Explorer page
        baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
        reports.navigateToReportsPage("Concept Explorer Report");
       // Select Sources
        conceptExplorer.clickSelectSources();
        baseClass.stepInfo("** Select Security group as source and save selection");
        conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);
       // Apply filter
        baseClass.stepInfo("** Set the filter criteria and click “Apply filter”");
        
        conceptExplorer.filterAction(emailrecep1, "EmailRecipientNames", emailrecep2, true);
        conceptExplorer.filterAction(doaminNAme, "EmailAllDomains", null, true);
        conceptExplorer.applyFilter("Yes", 10);
       baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());
       // Get Doc count consolidated
        String totalSelectedDocCount = conceptExplorer.getDocCOuntFromHeader().getText();
        String aggregatedDocCount = conceptExplorer.extractStringFromPosition(totalSelectedDocCount, 2);
        baseClass.passedStep("Report Generated with doc count : " + aggregatedDocCount + " which is expected");
       // tiles add to cart
        int resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
        conceptExplorer.addMultipleTilesToCart(resultToAddInCart);
       // view in doc list to verify filters applied returns the docs correctly.
        conceptExplorer.performDocActions("View", "View in DocList");
        baseClass.waitTime(4);
        baseClass.verifyPageNavigation("en-us/Document/DocList");
       // validation for inlcude filters
        DocListPage dlPage = new DocListPage(driver);
        dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
        List<String> emailAllDomain = dlPage.getColumnValue("EmailAllDomains", false);
        List<String> emailRecipients = dlPage.getColumnValue("EmailRecipientNames", false);
        conceptExplorer.verifyIcludeFiltersLikeOR_Operator(emailRecipients, emailAllDomain, emailrecep1,
                emailrecep2, doaminNAme);
       // remove filters
        baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
        reports.navigateToReportsPage("Concept Explorer Report");
        // Select Sources
        conceptExplorer.clickSelectSources();
        conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);
       // Apply filter
        baseClass.stepInfo("** Set the Exclude filter criteria and click “Apply filter”");
        conceptExplorer.filterAction(emailrecep1, "EmailRecipientNames", emailrecep2, false);
        conceptExplorer.filterAction(doaminNAme, "EmailAllDomains", null, false);
        conceptExplorer.applyFilter("Yes", 10);
       baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());
        baseClass.stepInfo("Report Generated.");
       // adding tiles to cart
        conceptExplorer.addMultipleTilesToCart(2);
       // Perform View in DocView List Action
        conceptExplorer.performDocActions("View", "View in DocList");
        baseClass.waitTime(4);
        baseClass.verifyPageNavigation("en-us/Document/DocList");
       // validation of exclude filter functionality
        dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
        List<String> emailAllDomain_Excl = dlPage.getColumnValue("EmailAllDomains", false);
        List<String> emailRecipients_Excl = dlPage.getColumnValue("EmailRecipientNames", false);
        conceptExplorer.verifyExcludeFiltersLikeOR_Operator(emailRecipients_Excl, emailAllDomain_Excl,
                emailrecep1, emailrecep2, doaminNAme);
        conceptExplorer.getBackToSourceBtn().Click();
        driver.waitForPageToBeReady();
        // Select tile to analyze at second level
        baseClass.stepInfo("**Select tile to analyze at second level**");
        baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
        resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
        conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 3);
        // Go to 2nd level
        List<String> expActiveFilters = conceptExplorer.verifyActiveFilters(null);
        conceptExplorer.analyzeAction(analyze);
        baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("2nd")),
                "Page navigated to second level and report generated", "Page didn't navigate to second level", "Pass");
        conceptExplorer.verifyActiveFilters(expActiveFilters);
        // Select tile to analyze at third level
        baseClass.stepInfo("**Select tile to analyze at third level**");
        baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
        resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
        conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 1);
       // Go to 3rd level
        conceptExplorer.analyzeAction(analyze3);
        baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("3rd")),
                "Page navigated to third level and report generated", "Page didn't navigate to third level", "Pass");
        conceptExplorer.verifyActiveFilters(expActiveFilters);
       // Logout
        loginPage.logout();
	}
	/**
	 * @author Jayanthi.Ganesan
	 * @param User
	 * @param pwd
	 * @throws Exception
	 */
	

	//@Test(description="RPMXCON-56906",enabled = true, dataProvider = "paRmu", groups = { "regression" })
	public void verifyFilters_EmailAllDomains_DocFileType2(String User, String pwd) throws Exception {

		baseClass.stepInfo("Test case Id :RPMXCON-56906");
		baseClass.stepInfo("Validate onpage filter for EmailAuthorName  and DocFileType on Concept Explorer Report");

		String analyze = Input.analyzeAt2;
		String analyze3 = Input.analyzeAt3;
		String sourceToSelect = "Security Groups";
		String emailDomain= "gmail.com";
		String emailD0main_1 ="symphonyteleca.com";
		String fileType= "MS Outlook Message";
		
		String[] columnsToSelect = { Input.emailAllDomain,Input.ingDocFileType };

		baseClass.stepInfo("**Login to sightline and Select Project**");
		loginPage.loginToSightLine(User, pwd);

		// Navigate to Concept Explorer page
		baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources
		conceptExplorer.clickSelectSources();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the filter criteria and click “Apply filter”");
		
		conceptExplorer.filterAction(emailDomain, Input.emailAllDomain,emailD0main_1, true);
		conceptExplorer.filterAction(fileType, Input.docFileType, null, true);
		conceptExplorer.applyFilter("Yes", 10);

		baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());

		// Get Doc count consolidated
		String totalSelectedDocCount = conceptExplorer.getDocCOuntFromHeader().getText();
		String aggregatedDocCount = conceptExplorer.extractStringFromPosition(totalSelectedDocCount, 2);
		baseClass.passedStep("Report Generated with doc count : " + aggregatedDocCount + " which is expected");

		// tiles add to cart
		int resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.addMultipleTilesToCart(resultToAddInCart);

		// view in doc list to verify filters applied returns the docs correctly.
		conceptExplorer.performDocActions("View", "View in DocList");
		baseClass.waitTime(4);
		baseClass.verifyPageNavigation("en-us/Document/DocList");

		// validation for inlcude filters
		DocListPage dlPage = new DocListPage(driver);
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailAllDomain = dlPage.getColumnValue(Input.emailAllDomain, false);
		List<String> docFileType = dlPage.getColumnValue(Input.docFileType, false);
		conceptExplorer.verifyIcludeFiltersLikeOR_Operator(emailAllDomain,docFileType, emailD0main_1,
				emailDomain, fileType);

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the Exclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(emailDomain, Input.emailAllDomain,emailD0main_1, false);
		conceptExplorer.filterAction(fileType, Input.docFileType, null, false);
		conceptExplorer.applyFilter("Yes", 10);

		baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());
		baseClass.stepInfo("Report Generated.");

		// adding tiles to cart
		conceptExplorer.addMultipleTilesToCart(2);

		// Perform View in DocView List Action
		conceptExplorer.performDocActions("View", "View in DocList");
		baseClass.waitTime(4);
		baseClass.verifyPageNavigation("en-us/Document/DocList");

		// validation of exclude filter functionality
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailAllDomain_Excl = dlPage.getColumnValue(Input.emailAllDomain, false);
		List<String> docFileType_Excl = dlPage.getColumnValue(Input.docFileType, false);
		conceptExplorer.verifyExcludeFiltersLikeOR_Operator(emailAllDomain_Excl,docFileType_Excl, emailD0main_1,
				emailDomain, fileType);
		
		conceptExplorer.getBackToSourceBtn().Click();
		driver.waitForPageToBeReady();

		// Select tile to analyze at second level
		baseClass.stepInfo("**Select tile to analyze at second level**");
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 3);
		// Go to 2nd level
		List<String> expActiveFilters = conceptExplorer.verifyActiveFilters(null);
		conceptExplorer.analyzeAction(analyze);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("2nd")),
				"Page navigated to second level and report generated", "Page didn't navigate to second level", "Pass");
		conceptExplorer.verifyActiveFilters(expActiveFilters);

		// Select tile to analyze at third level
		baseClass.stepInfo("**Select tile to analyze at third level**");
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 1);

		// Go to 3rd level
		conceptExplorer.analyzeAction(analyze3);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("3rd")),
				"Page navigated to third level and report generated", "Page didn't navigate to third level", "Pass");
		conceptExplorer.verifyActiveFilters(expActiveFilters);

		// Logout
		loginPage.logout();

	}




	/**
	 * @author Raghuram A
	 * @throws InterruptedException
	 * @Date: 07/04/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Concept Explorer report is working correctly and
	 *              properly. RPMXCON-48759
	 */
	//@Test(description = "RPMXCON-48759", enabled = true, groups = { "regression" })
	public void verifyConceptExplorerAction() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48759 - Concept Explorer");
		baseClass.stepInfo("Verify that Concept Explorer report is working correctly and properly");

		String tagName = "TagTT" + UtilityLog.dynamicNameAppender();
		String sourceToSelect = "Security Groups";
		String sgToSelect = Input.securityGroup;
		String analyze = Input.analyzeAt2;
		String analyze3 = Input.analyzeAt3;

		// Login as user
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Navigate to Concept Explorer page
		baseClass.stepInfo(
				"**Step-1 Go to Concept Explorer  Select source as Security Group  Select any filters  Click on Apply, report is generated**");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources and Apply filter
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, sgToSelect);
		baseClass.stepInfo("Selected : " + sgToSelect + "and Saved selection");
		conceptExplorer.getApplyFilterBtn().waitAndClick(3);
		driver.waitForPageToBeReady();

		// Select data to 'Add to cart'
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		int resultToAddInCart = conceptExplorer.getDataToAddInCart().size();

		// Verify Analyze at 2nd Level button is disabled
		baseClass.ValidateElement_Absence(conceptExplorer.getAnalyseActionBtn(analyze), analyze
				+ " button for conceptual map is disabled in the concept explorer page before user clicks a conceptual map.");
		baseClass.stepInfo("**Step-2 Click on Create and add keywords and click on OK**");

		// Create heat keyword
		conceptExplorer.addKeyWordHighlighting(Input.searchString1, true);
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(conceptExplorer.getKeywordHighlightedNodesPlusBtns());
		int resultToAddInCartHighlight = conceptExplorer.getDataToAddInCart().size();
		baseClass.verifyElementCollectionIsNotEmpty(conceptExplorer.getKeywordHighlightedNodesPlusBtns(),
				"Cluster highlighted in Purple color", "Cluster not highlighted in Purple color");

		// Add and perform bulk tag
		baseClass.stepInfo("**Step-3 Select any cluster from Tiles.And perform Actions \"BulkAssign\"/Bulk Tag.**");
		conceptExplorer.getKeywordHighlightedNodesPlusBtn(resultToAddInCartHighlight).waitAndClick(3);
		driver.waitForPageToBeReady();
		conceptExplorer.performActions("Bulk Tag");
		String bulkCount = sessionSearch.BulkActions_Tag(tagName);
		baseClass.stepInfo("Bulk Tag Action done successfully");

		// Choose a map
		baseClass.stepInfo("**Step-4 Select any cluster and click on '2nd Level**");
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		String clusterID = conceptExplorer.tileSelctionBasedOnChildCountReturnClusterID(resultToAddInCart, 3);

		// Verify Analyze at 2nd Level button is enabled
		baseClass.printResutInReport(
				baseClass.ValidateElement_PresenceReturn(conceptExplorer.getAnalyseActionBtn(analyze)),
				analyze + " button for conceptual map is enabled in the concept explorer page after user clicks a conceptual map.",
				analyze + " button not enabled", "Pass");

		// Go to 2nd level
		baseClass.stepInfo("Navigate to Second Level");
		conceptExplorer.analyzeAction(analyze);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("2nd")),
				"Page navigated to second level", "Page didn't navigate to second level", "Pass");
		baseClass.stepInfo("Pre-requesties completed");

		// Select a cluster from Level 2
		baseClass.stepInfo("**Step-5 Select any cluster and click on 3rd level**");
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 2);

		// Verify Analyze at 3nd Level button is enabled
		baseClass.printResutInReport(
				baseClass.ValidateElement_PresenceReturn(conceptExplorer.getAnalyseActionBtn(analyze3)), analyze3
						+ " button for conceptual map is enabled in the concept explorer page after user clicks a conceptual map.",
				analyze3 + " button not enabled", "Pass");

		// Go to 3rd level
		conceptExplorer.analyzeAction(analyze3);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("3rd")),
				"Page navigated to Third level", "Page didn't navigate to second level", "Pass");

		// Logout Application
		loginPage.logout();

	}

	/**
	 * @author Raghuram A
	 * @throws InterruptedException
	 * @Date: 07/06/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To Verify in the Concept Explorer user can select a cluster
	 *              and Perform bulk tag, bulk folder, bulk assign documents and
	 *              view in doc list. RPMXCON-48738
	 */
	//@Test(description = "RPMXCON-48738", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyConceptExplorerActions(String userName, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48738 - Concept Explorer");
		baseClass.stepInfo(
				"To Verify in the Concept Explorer user can select a cluster and Perform bulk tag, bulk folder, bulk assign documents and view in doc list");

		String tagName = "TagTT" + UtilityLog.dynamicNameAppender();
		String folderName = "Folder" + UtilityLog.dynamicNameAppender();
		String assignmentName = "Assignment" + UtilityLog.dynamicNameAppender();
		String sourceToSelect = "Security Groups";
		String sgToSelect = Input.securityGroup;
		String[] arrOfStr = null;

		// Login as user
		baseClass.stepInfo("**Step-1 Login to RPMX Application as @User.**");
		loginPage.loginToSightLine(userName, password);

		// Navigate to Concept Explorer page
		baseClass.stepInfo("**Step-2 Go to Report->Concept Explorer**");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources and Apply filter
		baseClass.stepInfo("**Step-3 Select source and generate the report**");
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, sgToSelect);
		baseClass.stepInfo("Selected : " + sgToSelect + "and Saved selection");
		conceptExplorer.getApplyFilterBtn().waitAndClick(3);
		driver.waitForPageToBeReady();

		// Select data to 'Add to cart'
		baseClass.stepInfo("**Step-4 Drag n drop any cluster in to Shopping Cart **");
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		int resultToAddInCart = conceptExplorer.getDataToAddInCart().size() - 3;
		conceptExplorer.addToCartIndex(resultToAddInCart);
		driver.waitForPageToBeReady();

		// Segregating total docs count from the display
		String totalSelectedDocCount = conceptExplorer.getTotalSelectedDocCount().getText();
		arrOfStr = totalSelectedDocCount.split(" ");
		String aggregatedDocCount = arrOfStr[arrOfStr.length - 3];
		baseClass.stepInfo("Total Doc count added to cart : " + aggregatedDocCount);

		// Bulk Action [Tag | Folder | Release | Assign | DocView | DocList | Tally]
		baseClass.stepInfo(
				"**Step-5 Select @bulk action  [Tag | Folder | Release | Assign | DocView | DocList | Tally] **");

		// Perform Bulk Tag Action
		driver.waitForPageToBeReady();
		conceptExplorer.performActions("Bulk Tag");
		String bulkCount = sessionSearch.BulkActions_Tag(tagName);
		baseClass.textCompareEquals(aggregatedDocCount, bulkCount, "Tag Document count matches as expected",
				"Mis-match in document count");
		baseClass.stepInfo("Bulk Tag Action done successfully");

		// Perform Bulk Folder Action
		driver.waitForPageToBeReady();
		conceptExplorer.performActions("Bulk Folder");
		String bulkFolderCount = sessionSearch.BulkActions_Folder_returnCount(folderName);
		baseClass.textCompareEquals(aggregatedDocCount, bulkFolderCount, "Folder Document count matches as expected",
				"Mis-match in document count");
		baseClass.stepInfo("Bulk Folder Action done successfully");

		// Perform Bulk Release Action
		driver.waitForPageToBeReady();
		conceptExplorer.performSpecificActions("Bulk Release", role, "PA");
		sessionSearch.bulkReleaseCountReturn(sgToSelect, role, "PA", true, aggregatedDocCount);

		// Perform View in DocView List Action
		conceptExplorer.performDocActions("View", "View in DocList");
		baseClass.waitTime(6);
		baseClass.verifyPageNavigation("en-us/Document/DocList");
		conceptExplorer.getBackToSourceBtn().waitAndClick(3);
		driver.waitForPageToBeReady();

		// Perform View in DocView Action
		conceptExplorer.performDocActions("View", "View in DocView");
		baseClass.verifyPageNavigation("DocumentViewer/DocView");
		baseClass.waitForElement(miniDocListPage.getDocumentCountFromDocView());
		String sizeofList = miniDocListPage.getDocumentCountFromDocView().getText();
		String documentSize = sizeofList.substring(sizeofList.indexOf("of") + 2, sizeofList.indexOf("Docs")).trim();
		baseClass.stepInfo("Available documents in DocView page : " + documentSize);
		baseClass.textCompareEquals(aggregatedDocCount, documentSize, "DocView Document count matches as expected",
				"Mis-match in document count");
		driver.Navigate().back();
		driver.waitForPageToBeReady();

		// Perform Tally
		conceptExplorer.performActions("Tally");
		baseClass.verifyPageNavigation("Report/Tally");
		baseClass.stepInfo("Tally Action done successfully");
		driver.Navigate().back();
		driver.waitForPageToBeReady();

		// Select data to 'Add to cart' to reAdd data
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size() - 3;
		conceptExplorer.addToCartIndex(resultToAddInCart);
		driver.waitForPageToBeReady();

		// Perform Bulk Assign
		conceptExplorer.performSpecificActions("Bulk Assign", role, "RMU");
		assign.assignMentCreationDeletionBasedOnUser(true, role, assignmentName, false, "RMU", false, "");
		baseClass.stepInfo("Bulk Assign Action done successfully");

		// Tag and Folder and Assignment Delete
		baseClass.stepInfo("Initiating Tag and Folder and Assignment deletion");
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.waitForPageToBeReady();
		tagsAndFolder.deleteAllTags(tagName);
		tagsAndFolder.deleteAllFolders(folderName);
		assign.assignMentCreationDeletionBasedOnUser(false, role, assignmentName, true, "RMU", false, "");

		// Logout Application
		loginPage.logout();

	}

	/**
	 * @author Raghuram A
	 * @throws InterruptedException
	 * @Date: 07/08/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Validate list for Tags in on page filter on Concept Explorer
	 *              report. RPMXCON-48738
	 */
	//@Test(description = "RPMXCON-56882", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyDeletedTagsNotShownInTheFilterOption(String userName, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-56882 - Concept Explorer");
		baseClass.stepInfo("Validate list for Tags in on page filter on Concept Explorer report");

		String tagName = "TagTT" + UtilityLog.dynamicNameAppender();
		String sourceToSelect = "Security Groups";
		String sgToSelect = Input.securityGroup;

		// Login as user
		baseClass.stepInfo("**Step-1 Login in as @User**");
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Current User role : " + role);

		// create tag and DeleteTag for pre-requesties
		baseClass.stepInfo("**Pre-condition: Few tags are deleted**");
		tagsAndFolder.CreateTag(tagName, sgToSelect);
		tagsAndFolder.navigateToTagsAndFolderPage();
		tagsAndFolder.deleteAllTags(tagName);

		// Navigate to Concept Explorer page
		baseClass.stepInfo("**Step-2 & 3 Go to Report->Concept Explorer**");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources and Apply filter
		baseClass.stepInfo("**Step-4 & 5 Select source and generate the report**");
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, sgToSelect);
		baseClass.stepInfo("Selected : " + sgToSelect + "and Saved selection");
		conceptExplorer.getApplyFilterBtn().waitAndClick(3);
		driver.waitForPageToBeReady();

		// Filter data status
		baseClass.stepInfo("**Step-6 & 7 Try to filter by Tag and Check for listed Tags**");
		Boolean status = conceptExplorer.filterActionResultStatus(tagName, "Tags", true);
		baseClass.printResutInReport(status, "Deleted Tags not listed in the filter option",
				"Deleted Tags listed in the filter option", "Fail");

		// Logout Application
		loginPage.logout();

	}

	/**
	 * @author Raghuram A
	 * @throws InterruptedException
	 * @Date: 07/08/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Validate on Page filter by Tag on Concept Explorer(same
	 *              documents are part of multiple tags). RPMXCON-56881
	 */
	//@Test(description = "RPMXCON-56881", groups = { "regression" }, enabled = true)
	public void VerifyIncludeExcludeFiltersFunctionality_ConceptExp() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56881");
		baseClass.stepInfo(
				"Validate on Page filter by Tag on Concept Explorer(same documents are part of multiple tags)");

		String sourceToSelect = "Security Groups";
		String sgToSelect = Input.securityGroup;
		String TagName1 = "ReportsTag" + Utility.dynamicNameAppender();
		String MetaSearch = Input.metaDataCustodianNameInput;

		// Login as PA
		baseClass.stepInfo("**Step-1 Login as Project Admin**");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Tag creation - 1
		tagsAndFolder.CreateTagwithClassification(TagName1, Input.tagNamePrev);
		int pureHit = sessionSearch.MetaDataSearchInAdvancedSearch(Input.metaDataName, MetaSearch);
		sessionSearch.bulkTagExisting(TagName1);
		baseClass.stepInfo("Created a Tag with name--" + TagName1);
		baseClass.stepInfo("Created tag assigned with docs : " + pureHit);

		// Reports - Concept Explorer - Generate report
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources and Apply filter
		baseClass.stepInfo("**Step-3 & 4 & 5 Select source and Generate Concept Explorer**");
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, sgToSelect);
		baseClass.stepInfo("Selected : " + sgToSelect + "and Saved selection");
		conceptExplorer.getApplyFilterBtn().waitAndClick(3);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());
		baseClass.stepInfo("Report Generated.");

		// Get Doc count consolidated
		String totalSelectedDocCount = conceptExplorer.getDocCOuntFromHeader().getText();
		String aggregatedDocCount = conceptExplorer.extractStringFromPosition(totalSelectedDocCount, 2);
		baseClass.stepInfo("Report Generated with doc count : " + aggregatedDocCount);

		// Count calculation
		int excludeCountToCheck = Integer.parseInt(aggregatedDocCount) - pureHit;

		// Apply on page filter by including one/multiple Tags
		baseClass.stepInfo("**Step-6 Apply on page filter by including one/multiple Tags**");
		conceptExplorer.filterAction(TagName1, "Tags", null, true);
		conceptExplorer.getApplyFilterBtn().waitAndClick(3);
		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());
		totalSelectedDocCount = conceptExplorer.getDocCOuntFromHeader().getText();
		aggregatedDocCount = conceptExplorer.extractStringFromPosition(totalSelectedDocCount, 2);
		baseClass.stepInfo("Report Generated after INCLUDE tag filter applied with doc count : " + aggregatedDocCount);
		baseClass.textCompareEquals(aggregatedDocCount, Integer.toString(pureHit),
				"Result set included all documents that are part of the selected Tags.",
				"Result set didn't includ all documents that are part of the selected Tags.");

		// Apply on page filter by excluding one/multiple Tags
		baseClass.stepInfo("**Step-7 Remove filter and Apply on Page filter by excluding one/multiple Tags**");
		conceptExplorer.excludeAfterInclude();
		conceptExplorer.getApplyFilterBtn().waitAndClick(3);
		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());
		totalSelectedDocCount = conceptExplorer.getDocCOuntFromHeader().getText();
		aggregatedDocCount = conceptExplorer.extractStringFromPosition(totalSelectedDocCount, 2);
		baseClass.stepInfo("Report Generated after EXCLUDE tag filter applied with doc count : " + aggregatedDocCount);
		baseClass.textCompareEquals(aggregatedDocCount, Integer.toString(excludeCountToCheck),
				"Result set excluded all the document that are part of the selected Tags.",
				"Result set didn't exclude all the document that are part of the selected Tags.");

		// Create tag and Delete Tag for pre-requesties
		baseClass.stepInfo("**Pre-condition: Few tags are deleted**");
		tagsAndFolder.navigateToTagsAndFolderPage();
		tagsAndFolder.DeleteTag(TagName1, Input.securityGroup);

		loginPage.logout();
	}

	/**
	 * @author Jayanthi.Ganesan Verify and generate Concept Explorer Report with
	 *         source as Search
	 * @param User
	 * @param pwd
	 * @param SearchGroup
	 * @throws Exception
	 */

	//@Test(description = "RPMXCON-56962", enabled = true, dataProvider = "paRmuUsers", groups = { "regression" })
	public void verifyConceptExp(String User, String pwd, String SearchGroup) throws Exception {

		baseClass.stepInfo("Test case Id : RPMXCON-56962: ");
		baseClass.stepInfo("Verify and generate Concept Explorer Report with source as Search");

		String savedSearchName = "SavedSearch-" + Utility.dynamicNameAppender();
		String sourceToSelect = "Searches";

		String analyze = Input.analyzeAt2;
		String analyze3 = Input.analyzeAt3;

		// Login as RMU user
		baseClass.stepInfo("**Login to sightline and Select Project**");
		loginPage.loginToSightLine(User, pwd);

		// Pre-requesties
		baseClass.stepInfo("Pre-requesties two saved searches  creation");

		sessionSearch.basicContentSearch(Input.searchString1);
		String hitCount = sessionSearch.verifyPureHitsCount();

		sessionSearch.saveSearchAtAnyRootGroup(savedSearchName, SearchGroup);
		sessionSearch.saveSearchAtAnyRootGroup(savedSearchName, Input.mySavedSearch);

		baseClass.stepInfo("Pre-requestes Creation  completed");

		// Navigate to Concept Explorer page
		baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources-search 1
		conceptExplorer.clickSelectSources();
		baseClass.stepInfo("**Step-5 Select any one of the source and save selection");
		conceptExplorer.selectSearchessource(sourceToSelect, Input.mySavedSearch, savedSearchName, "", false, "");

		// Select Sources-search 2
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSearchessource(sourceToSelect, SearchGroup, savedSearchName, "", false, "");

		// Apply filter
		baseClass.stepInfo("**Step-6 Set the filter criteria and click “Apply filter”");
		conceptExplorer.applyFilter("Yes", 10);

		baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());
		baseClass.stepInfo("Report Generated.");

		// Get Doc count consolidated
		String totalSelectedDocCount = conceptExplorer.getDocCOuntFromHeader().getText();
		String aggregatedDocCount = conceptExplorer.extractStringFromPosition(totalSelectedDocCount, 2);
		String passMSg = "Report Generated with doc count : " + aggregatedDocCount + " which is expected";
		String failMSg = "Report Generated with doc count : " + aggregatedDocCount + " which is not  expected";
		// Select data to 'Add to cart'
		baseClass.textCompareEquals(hitCount, aggregatedDocCount, passMSg, failMSg);

		int resultToAddInCart = conceptExplorer.getDataToAddInCart().size();

		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionAnalyze_BasedChildCount(resultToAddInCart, 1, 2);

		// Go to 2nd level
		conceptExplorer.analyzeAction(analyze);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("2nd")),
				"Page navigated to second level and report generated", "Page didn't navigate to second level", "Pass");
		// Select data to 'Add to cart'
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionAnalyze_BasedChildCount(resultToAddInCart, 1, 2);

		// Go to 3rd level
		conceptExplorer.analyzeAction(analyze3);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("3rd")),
				"Page navigated to third level and report generated", "Page didn't navigate to third level", "Pass");

		// Delete Search
		baseClass.stepInfo("Initiating Delete search input");
		savedSearch = new SavedSearch(driver);
		savedSearch.deleteSearch(savedSearchName, SearchGroup, "Yes");
		savedSearch.deleteSearch(savedSearchName, Input.mySavedSearch, "Yes");

		// Logout
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
		System.out.println("**Executed Advanced search Regression6**");
	}
}
