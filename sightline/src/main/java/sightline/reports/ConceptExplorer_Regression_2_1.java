package sightline.reports;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ReportsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsRegression.TagAndFolder;
import testScriptsSmoke.Input;

public class ConceptExplorer_Regression_2_1 {

	Driver driver;
	LoginPage login;
	BaseClass base;
	ReportsPage reports;
	ConceptExplorerPage conceptExplorer;
	SessionSearch sessionSearch;
	TagsAndFoldersPage tagsAndFolder;
	MiniDocListPage miniDocListPage;
	AssignmentsPage assign;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod)
			throws IOException, ParseException, InterruptedException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		reports = new ReportsPage(driver);
		conceptExplorer = new ConceptExplorerPage(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolder = new TagsAndFoldersPage(driver);
		miniDocListPage = new MiniDocListPage(driver);
		assign = new AssignmentsPage(driver);
	}

	@DataProvider(name = "PaAndRmuUser")
	public Object[][] PaAndRmuUser() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
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
	@Test(description = "RPMXCON-48759", enabled = true, groups = { "regression" })
	public void verifyConceptExplorerAction() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-48759 - Concept Explorer");
		base.stepInfo("Verify that Concept Explorer report is working correctly and properly");

		String tagName = "TagTT" + UtilityLog.dynamicNameAppender();
		String sourceToSelect = "Security Groups";
		String sgToSelect = Input.securityGroup;
		String analyze = Input.analyzeAt2;
		String analyze3 = Input.analyzeAt3;

		// Login as user
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Navigate to Concept Explorer page
		base.stepInfo(
				"**Step-1 Go to Concept Explorer  Select source as Security Group  Select any filters  Click on Apply, report is generated**");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources and Apply filter
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, sgToSelect);
		base.stepInfo("Selected : " + sgToSelect + "and Saved selection");
		conceptExplorer.getApplyFilterBtn().waitAndClick(3);
		driver.waitForPageToBeReady();

		// Select data to 'Add to cart'
		base.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		int resultToAddInCart = conceptExplorer.getDataToAddInCart().size();

		// Verify Analyze at 2nd Level button is disabled
		base.ValidateElement_Absence(conceptExplorer.getAnalyseActionBtn(analyze), analyze
				+ " button for conceptual map is disabled in the concept explorer page before user clicks a conceptual map.");
		base.stepInfo("**Step-2 Click on Create and add keywords and click on OK**");

		// Create heat keyword
		conceptExplorer.addKeyWordHighlighting(Input.searchString1, true);
		driver.waitForPageToBeReady();
		base.waitForElementCollection(conceptExplorer.getKeywordHighlightedNodesPlusBtns());
		int resultToAddInCartHighlight = conceptExplorer.getDataToAddInCart().size();
		base.verifyElementCollectionIsNotEmpty(conceptExplorer.getKeywordHighlightedNodesPlusBtns(),
				"Cluster highlighted in Purple color", "Cluster not highlighted in Purple color");

		// Add and perform bulk tag
		base.stepInfo("**Step-3 Select any cluster from Tiles.And perform Actions \"BulkAssign\"/Bulk Tag.**");
		conceptExplorer.getKeywordHighlightedNodesPlusBtn(resultToAddInCartHighlight).waitAndClick(3);
		driver.waitForPageToBeReady();
		conceptExplorer.performActions("Bulk Tag");
		String bulkCount = sessionSearch.BulkActions_Tag(tagName);
		base.stepInfo("Bulk Tag Action done successfully");

		// Choose a map
		base.stepInfo("**Step-4 Select any cluster and click on '2nd Level**");
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		String clusterID = conceptExplorer.tileSelctionBasedOnChildCountReturnClusterID(resultToAddInCart, 3);

		// Verify Analyze at 2nd Level button is enabled
		base.printResutInReport(base.ValidateElement_PresenceReturn(conceptExplorer.getAnalyseActionBtn(analyze)),
				analyze + " button for conceptual map is enabled in the concept explorer page after user clicks a conceptual map.",
				analyze + " button not enabled", "Pass");

		// Go to 2nd level
		base.stepInfo("Navigate to Second Level");
		conceptExplorer.analyzeAction(analyze);
		base.printResutInReport(base.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("2nd")),
				"Page navigated to second level", "Page didn't navigate to second level", "Pass");
		base.stepInfo("Pre-requesties completed");

		// Select a cluster from Level 2
		base.stepInfo("**Step-5 Select any cluster and click on 3rd level**");
		base.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 2);

		// Verify Analyze at 3nd Level button is enabled
		base.printResutInReport(
				base.ValidateElement_PresenceReturn(conceptExplorer.getAnalyseActionBtn(analyze3)), analyze3
						+ " button for conceptual map is enabled in the concept explorer page after user clicks a conceptual map.",
				analyze3 + " button not enabled", "Pass");

		// Go to 3rd level
		conceptExplorer.analyzeAction(analyze3);
		base.printResutInReport(base.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("3rd")),
				"Page navigated to Third level", "Page didn't navigate to second level", "Pass");

		// Logout Application
		login.logout();

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
	@Test(description = "RPMXCON-48738", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyConceptExplorerActions(String userName, String password, String role)
			throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-48738 - Concept Explorer");
		base.stepInfo(
				"To Verify in the Concept Explorer user can select a cluster and Perform bulk tag, bulk folder, bulk assign documents and view in doc list");

		String tagName = "TagTT" + UtilityLog.dynamicNameAppender();
		String folderName = "Folder" + UtilityLog.dynamicNameAppender();
		String assignmentName = "Assignment" + UtilityLog.dynamicNameAppender();
		String sourceToSelect = "Security Groups";
		String sgToSelect = Input.securityGroup;
		String[] arrOfStr = null;

		// Login as user
		base.stepInfo("**Step-1 Login to RPMX Application as @User.**");
		login.loginToSightLine(userName, password);

		// Navigate to Concept Explorer page
		base.stepInfo("**Step-2 Go to Report->Concept Explorer**");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources and Apply filter
		base.stepInfo("**Step-3 Select source and generate the report**");
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, sgToSelect);
		base.stepInfo("Selected : " + sgToSelect + "and Saved selection");
		conceptExplorer.getApplyFilterBtn().waitAndClick(3);
		driver.waitForPageToBeReady();

		// Select data to 'Add to cart'
		base.stepInfo("**Step-4 Drag n drop any cluster in to Shopping Cart **");
		base.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		int resultToAddInCart = conceptExplorer.getDataToAddInCart().size() - 3;
		conceptExplorer.addToCartIndex(resultToAddInCart);
		driver.waitForPageToBeReady();

		// Segregating total docs count from the display
		String totalSelectedDocCount = conceptExplorer.getTotalSelectedDocCount().getText();
		arrOfStr = totalSelectedDocCount.split(" ");
		String aggregatedDocCount = arrOfStr[arrOfStr.length - 3];
		base.stepInfo("Total Doc count added to cart : " + aggregatedDocCount);

		// Bulk Action [Tag | Folder | Release | Assign | DocView | DocList | Tally]
		base.stepInfo("**Step-5 Select @bulk action  [Tag | Folder | Release | Assign | DocView | DocList | Tally] **");

		// Perform Bulk Tag Action
		driver.waitForPageToBeReady();
		conceptExplorer.performActions("Bulk Tag");
		String bulkCount = sessionSearch.BulkActions_Tag(tagName);
		base.textCompareEquals(aggregatedDocCount, bulkCount, "Tag Document count matches as expected",
				"Mis-match in document count");
		base.stepInfo("Bulk Tag Action done successfully");

		// Perform Bulk Folder Action
		driver.waitForPageToBeReady();
		conceptExplorer.performActions("Bulk Folder");
		String bulkFolderCount = sessionSearch.BulkActions_Folder_returnCount(folderName);
		base.textCompareEquals(aggregatedDocCount, bulkFolderCount, "Folder Document count matches as expected",
				"Mis-match in document count");
		base.stepInfo("Bulk Folder Action done successfully");

		// Perform Bulk Release Action
		driver.waitForPageToBeReady();
		conceptExplorer.performSpecificActions("Bulk Release", role, "PA");
		sessionSearch.bulkReleaseCountReturn(sgToSelect, role, "PA", true, aggregatedDocCount);

		// Perform View in DocView List Action
		conceptExplorer.performDocActions("View", "View in DocList");
		base.waitTime(6);
		base.verifyPageNavigation("en-us/Document/DocList");
		conceptExplorer.getBackToSourceBtn().waitAndClick(3);
		driver.waitForPageToBeReady();

		// Perform View in DocView Action
		conceptExplorer.performDocActions("View", "View in DocView");
		base.verifyPageNavigation("DocumentViewer/DocView");
		base.waitForElement(miniDocListPage.getDocumentCountFromDocView());
		String sizeofList = miniDocListPage.getDocumentCountFromDocView().getText();
		String documentSize = sizeofList.substring(sizeofList.indexOf("of") + 2, sizeofList.indexOf("Docs")).trim();
		base.stepInfo("Available documents in DocView page : " + documentSize);
		base.textCompareEquals(aggregatedDocCount, documentSize, "DocView Document count matches as expected",
				"Mis-match in document count");
		driver.Navigate().back();
		driver.waitForPageToBeReady();

		// Perform Tally
		conceptExplorer.performActions("Tally");
		base.verifyPageNavigation("Report/Tally");
		base.stepInfo("Tally Action done successfully");
		driver.Navigate().back();
		driver.waitForPageToBeReady();

		// Select data to 'Add to cart' to reAdd data
		base.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size() - 3;
		conceptExplorer.addToCartIndex(resultToAddInCart);
		driver.waitForPageToBeReady();

		// Perform Bulk Assign
		conceptExplorer.performSpecificActions("Bulk Assign", role, "RMU");
		assign.assignMentCreationDeletionBasedOnUser(true, role, assignmentName, false, "RMU", false, "");
		base.stepInfo("Bulk Assign Action done successfully");

		// Tag and Folder and Assignment Delete
		base.stepInfo("Initiating Tag and Folder and Assignment deletion");
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.waitForPageToBeReady();
		tagsAndFolder.deleteAllTags(tagName);
		tagsAndFolder.deleteAllFolders(folderName);
		assign.assignMentCreationDeletionBasedOnUser(false, role, assignmentName, true, "RMU", false, "");

		// Logout Application
		login.logout();

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
	@Test(description = "RPMXCON-56882", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyDeletedTagsNotShownInTheFilterOption(String userName, String password, String role)
			throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-56882 - Concept Explorer");
		base.stepInfo("Validate list for Tags in on page filter on Concept Explorer report");

		String tagName = "TagTT" + UtilityLog.dynamicNameAppender();
		String sourceToSelect = "Security Groups";
		String sgToSelect = Input.securityGroup;

		// Login as user
		base.stepInfo("**Step-1 Login in as @User**");
		login.loginToSightLine(userName, password);
		base.stepInfo("Current User role : " + role);

		// create tag and DeleteTag for pre-requesties
		base.stepInfo("**Pre-condition: Few tags are deleted**");
		tagsAndFolder.CreateTag(tagName, sgToSelect);
		tagsAndFolder.navigateToTagsAndFolderPage();
		tagsAndFolder.deleteAllTags(tagName);

		// Navigate to Concept Explorer page
		base.stepInfo("**Step-2 & 3 Go to Report->Concept Explorer**");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources and Apply filter
		base.stepInfo("**Step-4 & 5 Select source and generate the report**");
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, sgToSelect);
		base.stepInfo("Selected : " + sgToSelect + "and Saved selection");
		conceptExplorer.getApplyFilterBtn().waitAndClick(3);
		driver.waitForPageToBeReady();

		// Filter data status
		base.stepInfo("**Step-6 & 7 Try to filter by Tag and Check for listed Tags**");
		Boolean status = conceptExplorer.filterActionResultStatus(tagName, "Tags", true);
		base.printResutInReport(status, "Deleted Tags not listed in the filter option",
				"Deleted Tags listed in the filter option", "Fail");

		// Logout Application
		login.logout();

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
	@Test(description = "RPMXCON-56881", groups = { "regression" }, enabled = true)
	public void VerifyIncludeExcludeFiltersFunctionality_ConceptExp() throws InterruptedException {
		base.stepInfo("Test case Id: RPMXCON-56881");
		base.stepInfo("Validate on Page filter by Tag on Concept Explorer(same documents are part of multiple tags)");

		String sourceToSelect = "Security Groups";
		String sgToSelect = Input.securityGroup;
		String TagName1 = "ReportsTag" + Utility.dynamicNameAppender();
		String MetaSearch = Input.metaDataCustodianNameInput;

		// Login as PA
		base.stepInfo("**Step-1 Login as Project Admin**");
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Tag creation - 1
		tagsAndFolder.CreateTagwithClassification(TagName1, Input.tagNamePrev);
		int pureHit = sessionSearch.MetaDataSearchInAdvancedSearch(Input.metaDataName, MetaSearch);
		sessionSearch.bulkTagExisting(TagName1);
		base.stepInfo("Created a Tag with name--" + TagName1);
		base.stepInfo("Created tag assigned with docs : " + pureHit);

		// Reports - Concept Explorer - Generate report
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources and Apply filter
		base.stepInfo("**Step-3 & 4 & 5 Select source and Generate Concept Explorer**");
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, sgToSelect);
		base.stepInfo("Selected : " + sgToSelect + "and Saved selection");
		conceptExplorer.getApplyFilterBtn().waitAndClick(3);
		driver.waitForPageToBeReady();
		base.waitForElement(conceptExplorer.getDocCOuntFromHeader());
		base.stepInfo("Report Generated.");

		// Get Doc count consolidated
		String totalSelectedDocCount = conceptExplorer.getDocCOuntFromHeader().getText();
		String aggregatedDocCount = conceptExplorer.extractStringFromPosition(totalSelectedDocCount, 2);
		base.stepInfo("Report Generated with doc count : " + aggregatedDocCount);

		// Count calculation
		int excludeCountToCheck = Integer.parseInt(aggregatedDocCount) - pureHit;

		// Apply on page filter by including one/multiple Tags
		base.stepInfo("**Step-6 Apply on page filter by including one/multiple Tags**");
		conceptExplorer.filterAction(TagName1, "Tags", null, true);
		conceptExplorer.getApplyFilterBtn().waitAndClick(3);
		driver.waitForPageToBeReady();
		base.waitTime(10);
		base.waitForElement(conceptExplorer.getDocCOuntFromHeader());
		totalSelectedDocCount = conceptExplorer.getDocCOuntFromHeader().getText();
		aggregatedDocCount = conceptExplorer.extractStringFromPosition(totalSelectedDocCount, 2);
		base.stepInfo("Report Generated after INCLUDE tag filter applied with doc count : " + aggregatedDocCount);
		base.textCompareEquals(aggregatedDocCount, Integer.toString(pureHit),
				"Result set included all documents that are part of the selected Tags.",
				"Result set didn't includ all documents that are part of the selected Tags.");

		// Apply on page filter by excluding one/multiple Tags
		base.stepInfo("**Step-7 Remove filter and Apply on Page filter by excluding one/multiple Tags**");
		conceptExplorer.excludeAfterInclude();
		conceptExplorer.getApplyFilterBtn().waitAndClick(3);
		driver.waitForPageToBeReady();
		base.waitTime(10);
		base.waitForElement(conceptExplorer.getDocCOuntFromHeader());
		totalSelectedDocCount = conceptExplorer.getDocCOuntFromHeader().getText();
		aggregatedDocCount = conceptExplorer.extractStringFromPosition(totalSelectedDocCount, 2);
		base.stepInfo("Report Generated after EXCLUDE tag filter applied with doc count : " + aggregatedDocCount);
		base.textCompareEquals(aggregatedDocCount, Integer.toString(excludeCountToCheck),
				"Result set excluded all the document that are part of the selected Tags.",
				"Result set didn't exclude all the document that are part of the selected Tags.");

		// Create tag and Delete Tag for pre-requesties
		base.stepInfo("**Pre-condition: Few tags are deleted**");
		tagsAndFolder.navigateToTagsAndFolderPage();
		tagsAndFolder.DeleteTag(TagName1, Input.securityGroup);

		login.logout();
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		UtilityLog.info("******Execution completed for " + this.getClass().getSimpleName() + "********");
	}

}
