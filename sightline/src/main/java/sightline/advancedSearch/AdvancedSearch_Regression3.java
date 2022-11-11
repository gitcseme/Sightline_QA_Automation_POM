package sightline.advancedSearch;

import java.awt.AWTException;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearch_Regression3 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	BaseClass baseClass;
	Input in;
	String hitsCountPA;
	int hitsCount;
	String prefixID = "A_" + Utility.dynamicNameAppender();
	String suffixID = "_P" + Utility.dynamicNameAppender();
	String productionname = "AdvancedSearchRegression3";//"prod" + Utility.dynamicNameAppender();
	String tagname = "Tag_Productions" ;//"Tag" + Utility.dynamicNameAppender();
	String foldername = "FolderProd" + Utility.dynamicNameAppender();
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException, AWTException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		in = new Input();
		in.loadEnvConfig();
		// Open browser
		driver = new Driver();
		baseClass = new BaseClass(driver);
		// Login as a PA
		
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		// Pre-requesites creation
		
//If we want to generate production via code pls  un comment the below lines[Production generation 
//code] and change the hard coded 'tagname' and 'productionname' Variable value.
		
	/*	baseClass.stepInfo("Creating tags and folders in Tags/Folders Page");
		TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tp.CreateFolder(foldername, Input.securityGroup);
		tp.createNewTagwithClassification(tagname, "Privileged");     */

		// search for Bulk Tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		hitsCountPA = sessionSearch.verifyPureHitsCount();
/*	    sessionSearch.bulkTagExisting(tagname);
		baseClass.stepInfo("Created a Tag " + tagname + "Count of docs bulk tagged " + hitsCountPA);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();    */
		lp.logout();
		lp.closeBrowser(); 
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	//@Test(description ="RPMXCON-48765",dataProvider = "Users", groups = { "regression" }, enabled = true)
	public void verifyResubmit_content(String username, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48765");
		baseClass.stepInfo("Verify that - Application returns consistent search result when user resubmits a "
				+ "saved search(Content & Metadata Block) multiple times(twice)");
		lp.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as " + username);
		String saveSearchName = "resubmit" + Utility.dynamicNameAppender();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		SessionSearch search = new SessionSearch(driver);
		search.advancedContentSearch(Input.testData1);
		String PureHitCount = search.verifyPureHitsCount();
		search.saveSearchAdvanced(saveSearchName);
		baseClass.stepInfo("Created a saved search -" + saveSearchName);
		search.selectSavedsearchInASWp(saveSearchName);
		baseClass.stepInfo("Configured a Work product search query -- saved search -" + saveSearchName + " ");
		search.serarchWP();
		driver.waitForPageToBeReady();
		String expectedHits1 = search.getPureHitsLastCount().getText();
		baseClass.stepInfo("Pure hit count after WP saved search is " + expectedHits1);
		search.resubmitSearch();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(search.getPureHitCount(2));
		baseClass.waitTillTextToPresent(search.getPureHitCount(2),PureHitCount);
		String expectedHits2 = search.getPureHitCount(2).getText();
		baseClass.stepInfo("Pure hit count after resubmitting WP saved search is " + expectedHits2);
		search.resubmitSearch();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(search.getPureHitCount(3));
		baseClass.waitTillTextToPresent(search.getPureHitCount(3),PureHitCount);
		String expectedHits3 = search.getPureHitCount(3).getText();
		baseClass.stepInfo("Pure hit count after resubmitting WP saved search is " + expectedHits3);
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals(PureHitCount, expectedHits2);
		assertion.assertEquals(PureHitCount, expectedHits1);
		assertion.assertEquals(PureHitCount, expectedHits3);
		SavedSearch savedSearch = new SavedSearch(driver);
		savedSearch.SaveSearchDelete(saveSearchName);
		assertion.assertAll();
		baseClass.passedStep(
				"Sucessfully Verified that - Application returns consistent search result when user resubmits a "
						+ "saved search(Content & Metadata Block) multiple times(twice)");
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	////@Test(description ="RPMXCON-48747",groups = { "regression" }, enabled = true)
	public void verifyDocsCntAlreadyProduced() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48747");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group with AND "
						+ "operator and production optional filters - status  in search result.");

		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		SessionSearch search = new SessionSearch(driver);
		search.switchToWorkproduct();
		search.selctProductionsAlreadyProduced();
		search.serarchWP();
		driver.waitForPageToBeReady();
		String expectedPureHitCount = search.verifyPureHitsCount();// expected value
		baseClass.stepInfo("Total no docs in alerady produced status under Production  " + expectedPureHitCount);
		baseClass.selectproject();
		search.navigateToAdvancedSearchPage();
		// Adding WP Security Group into search text box
		search.workProductSearch("security group", Input.securityGroup, true);
		// Adding Operator into search text box
		search.selectOperator("AND");
		// Adding WP Productions-already produced into search text box
		search.selctProductionsAlreadyProduced();
		baseClass.stepInfo("Configured a Query with SecurityGroup:[ " + Input.securityGroup
				+ "] AND Productions:[produced: \"true\"]");
		search.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount = search.verifyPureHitsCount();
		baseClass.stepInfo("Total no docs after configured query as per test case is   " + PureHitCount);
		System.out.println(expectedPureHitCount + PureHitCount);
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals(expectedPureHitCount, PureHitCount);// Validation of hit count.
		assertion.assertAll();
		baseClass.passedStep(
				"Sucessfully Verified that - Application returns all the documents which are available under selected group with AND operator and"
						+ " production optional filters - status  in search result.");
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws AWTException
	 */
	////@Test(description ="RPMXCON-48746",groups = { "regression" }, enabled = true)
	public void verifyDocsCntAssgnments() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-48746");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group and"
						+ " Assignments with OR operator in search result.");
		String tagName = "combined" + Utility.dynamicNameAppender();
		String assgnName = "combined" + Utility.dynamicNameAppender();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		SessionSearch search = new SessionSearch(driver);

		// Pre requesite creation
		// Performing Star search since this will add all avail docs from default sec
		// group .
		search.basicContentSearch(Input.searchStringStar);
		String expectedHitsCount = search.verifyPureHitsCount();// expected value
		search.bulkTag(tagName);
		baseClass.stepInfo("Created a Tag " + tagName + "Count of docs bulk tagged" + expectedHitsCount);
		baseClass.selectproject();
		search.basicContentSearch(Input.TallySearch);
		search.bulkAssign();
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assgnName, Input.codeFormName);
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a assignment " + assgnName);

		baseClass.selectproject();
		search.navigateToAdvancedSearchPage();
		// Adding WP tag into search text box
		search.workProductSearch("tag", tagName, true);
		// Adding Operator into search text box
		search.selectOperator("OR");
		// Adding WP assignment into search text box
		search.workProductSearch("assignments", assgnName, false);
		baseClass.stepInfo("Configured a Query with TagName:[ " + tagName + "] AND AssignmentName:[" + assgnName + "]");
		search.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount = search.verifyPureHitsCount();
		System.out.println(expectedHitsCount + PureHitCount);
		SoftAssert assertion = new SoftAssert();
		// validation of pure hits
		assertion.assertEquals(expectedHitsCount, PureHitCount);
		agnmt.deleteAssgnmntUsingPagination(assgnName);
		TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tp.deleteAllTags(tagName);
		assertion.assertAll();
		baseClass.passedStep("Application   returned all the documents which are available under "
				+ "selected security group in search result.     " + PureHitCount);
		baseClass.passedStep(
				"Sucessfully Verified that - Application returns all the documents which are available under selected group and "
						+ "Assignments with OR operator in search result.");
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws AWTException
	 */
	//@Test(description ="RPMXCON-49300",groups = { "regression" }, enabled = true)
	public void verifyDocsCntTagNOTProd() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-49300");
		baseClass.stepInfo("Verify that - Application returns all the documents which are available under "
				+ "selected Tags with NOT operator in search result.");
		String tagName = "combined" + Utility.dynamicNameAppender();
		String productionname = "productionname" + Utility.dynamicNameAppender();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		SessionSearch search = new SessionSearch(driver);

		// Pre requesite creation
		// Performing Star search since this will add all avail docs from default sec
		// group .
		search.basicContentSearch(Input.searchStringStar);
		String totalAvailDocsHitsCount = search.verifyPureHitsCount();// expected value
		search.bulkTag(tagName);
		baseClass.stepInfo("Created a Tag " + tagName + "Count of docs bulk tagged" + totalAvailDocsHitsCount);
		baseClass.selectproject();
		search.navigateToAdvancedSearchPage();
		// Adding WP tag into search text box
		search.workProductSearch("tag", tagName, true);
		// Adding Operator into search text box
		search.selectOperator("NOT");
		// Adding WP assignment into search text box
		search.workProductSearch("productions", productionname, false);
		baseClass.stepInfo(
				"Configured a Query with TagName:[ " + tagName + "] AND Productions:[" + productionname + "]");
		search.serarchWP();
		baseClass.waitTime(2);
		driver.waitForPageToBeReady();
		int PureHitCount = Integer.parseInt(search.verifyPureHitsCount());
		System.out.println(totalAvailDocsHitsCount + PureHitCount);
		SoftAssert assertion = new SoftAssert();
		// validation of pure hits
		int expectedValue = Integer.parseInt(totalAvailDocsHitsCount) - Integer.parseInt(hitsCountPA);
		assertion.assertEquals(expectedValue, PureHitCount);

		TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tp.deleteAllTags(tagName);
		assertion.assertAll();
		baseClass.passedStep("Application   returned all the documents which are available under "
				+ "selected TAGS in search result as per the configured query  " + PureHitCount);
		baseClass.passedStep(
				"Sucessfully Verified that - Application returns all the documents which are available under selected Tags with NOT operator"
						+ " in search result.");
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws AWTException
	 */
	////@Test(description ="RPMXCON-48748",groups = { "regression" }, enabled = true)
	public void verifyDocsCntCompletedAssgnments() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-48748");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group and Assignments - Completed status"
						+ " with AND operator in search result.");
		String tagName = "combined" + Utility.dynamicNameAppender();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		SessionSearch search = new SessionSearch(driver);

		// Pre requesite creation
		// Performing Star search since this will add all avail docs from default sec
		// group .
		search.basicContentSearch(Input.searchStringStar);
		String tagHitsCount = search.verifyPureHitsCount();// expected value
		search.bulkTag(tagName);
		baseClass.stepInfo("Created a Tag " + tagName + "Count of docs bulk tagged " + tagHitsCount);
		// getting total count of completed assignments doc count--expected value
		baseClass.selectproject();
		search.switchToWorkproduct();
		search.selectCompletedAssignmentInWP("Completed");
		search.serarchWP();
		driver.waitForPageToBeReady();
		String expectedPureHitCount = search.verifyPureHitsCount();// expected value
		baseClass.stepInfo("Total no docs in  Completed status under Assignments  " + expectedPureHitCount);

		baseClass.selectproject();
		search.navigateToAdvancedSearchPage();
		// Adding WP tag into search text box
		search.workProductSearch("tag", tagName, true);
		// Adding Operator into search text box
		search.selectOperator("AND");
		// Adding WP assignment into search text box
		search.selectCompletedAssignmentInWP("Completed");
		baseClass.stepInfo("Configured a Query with TagName:[ " + tagName + "] AND  Assignments:[completed:\"true\"]");
		search.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount = search.verifyPureHitsCount();
		System.out.println(expectedPureHitCount + PureHitCount);
		SoftAssert assertion = new SoftAssert();
		// validation of pure hits
		assertion.assertEquals(expectedPureHitCount, PureHitCount);

		TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
		tp.deleteAllTags(tagName);
		assertion.assertAll();
		baseClass.passedStep("Application   returned all the documents which are available under "
				+ "selected security group in search result   for the configured query." + PureHitCount);
		baseClass.passedStep(
				"Sucessfully Verified that - Application returns all the documents which are available under selected group and Assignments - Completed status with "
						+ "AND operator in search result.");
		lp.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	////@Test(description ="RPMXCON-47778",dataProvider = "Users_RMU_Rev", enabled = true, groups = { "regression" })
	public void verifySearchResultForComment(String username, String password) throws InterruptedException {

		String docComment = "Reviewed"+Utility.dynamicNameAppender();
		int count = 2;

		lp = new LoginPage(driver);
		SessionSearch search = new SessionSearch(driver);
		DocViewPage docview = new DocViewPage(driver);
		lp.loginToSightLine(username, password);
		baseClass.stepInfo("Logged In as : " + username);
		baseClass.stepInfo("Test case Id: RPMXCON-47778");
		baseClass.stepInfo("To verify as an user login into the Application, user will be able to search based on"
				+ " comments text on Content & Metadata in advanced search");
		search.advancedContentSearch(Input.testData1);
		search.ViewInDocView();

		// Apply comments to document
		docview.addCommentAndSave(docComment, true, count);
		baseClass.selectproject();
		int pureHit1 = search.getCommentsOrRemarksCount_AdvancedSearch(Input.documentComments, docComment);
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals(pureHit1, count);
		assertion.assertAll();
		baseClass.passedStep("user  able to search based on comments text on Content & Metadata in "
				+ "advanced search and pure hit count displayed is " + pureHit1 + " Which is expected.");
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan]
	 * @throws InterruptedException
	 */
	////@Test(description ="RPMXCON-47761",dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyPinnedIcon(String username, String password) throws InterruptedException {
		lp = new LoginPage(driver);
		SessionSearch search = new SessionSearch(driver);
		lp.loginToSightLine(username, password);
		baseClass.stepInfo("Test case Id: RPMXCON-47761");
		baseClass.stepInfo("To verify as an user login into the Application, user will be able to lock/pin"
				+ " this search a In-Session advanced search query");
		search.advancedContentSearch(Input.searchString1);
		driver.waitForPageToBeReady();
		search.getPureHitAddButton().waitAndClick(5);
		baseClass.stepInfo(
				"Performed a advanced content and metadata search  and moved the pure hit block to shopping cart");
		if (search.getPinnedSearchIcon().isElementAvailable(2)) {
			baseClass.passedStep(
					" pin icon  locked/Pinned this search when user drag the results into the Shopping cart  ");
		} else {
			baseClass.failedStep(
					" pin icon is not locked/Pinned this search when user drag the results into the Shopping cart  ");
		}
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	//@Test(description ="RPMXCON-48766",groups = { "regression" }, enabled = true)
	public void verifyResubmit_WPAndAudio() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48766");
		baseClass.stepInfo("Verify that - Application returns consistent search result when "
				+ "user resubmits a   saved search(WorkProduct Block -Security Group and Audio Block) multiple times(twice)");
		String saveSearchName = "resubmit" + Utility.dynamicNameAppender();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		SessionSearch search = new SessionSearch(driver);
		search.switchToWorkproduct();
		search.workProductSearch("security group", Input.securityGroup, false);
		search.audioSearch_Combined();
		search.selectOperator("NOT", 1);
		baseClass.stepInfo("Configured a Work product search query with security group block and audio block");
		search.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount = search.verifyPureHitsCount();
		driver.scrollPageToTop();
		search.saveSearchAdvanced(saveSearchName);
		baseClass.stepInfo("Created a saved search -" + saveSearchName);
		search.selectSavedsearchInASWp(saveSearchName);
		baseClass.stepInfo("Configured a Work product search query -- saved search -" + saveSearchName + " ");
		search.serarchWP();
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
		String actualHits1 = search.getPureHitsLastCount().getText();
		baseClass.stepInfo("Pure hit count after WP saved search is " + actualHits1);
		search.resubmitSearch();
		driver.waitForPageToBeReady();
		baseClass.waitTillTextToPresent(search.getPureHitCount(2),PureHitCount);
		String actualHits2 = search.getPureHitCount(2).getText();	
		baseClass.stepInfo("Pure hit count after resubmitting WP saved search is " + actualHits2);
		search.resubmitSearch();
		driver.waitForPageToBeReady();
		baseClass.waitTillTextToPresent(search.getPureHitCount(3),PureHitCount);
		String actualHits3 = search.getPureHitCount(3).getText();
		baseClass.stepInfo("Pure hit count after resubmitting WP saved search is " + actualHits3);
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals(PureHitCount, actualHits1);
		assertion.assertEquals(PureHitCount, actualHits2);
		assertion.assertEquals(PureHitCount, actualHits3);
		SavedSearch savedSearch = new SavedSearch(driver);
		savedSearch.SaveSearchDelete(saveSearchName);
		assertion.assertAll();
		baseClass.passedStep(
				"Sucessfully Verified that - Application returns consistent search result when user resubmits a "
						+ "saved search(Content & Metadata Block) multiple times(twice)");
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */

	//@Test(description ="RPMXCON-46891",groups = { "regression" }, enabled = true)
	public void verifyResubmit_cntAndMetaAndConceptualAndWPFolderAndAudio() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-46891");
		baseClass.stepInfo(
				"Verify that - Application returns consistent search result when user resubmits a saved search(Content & Metadata Block"
						+ ",Conceptual Block,WorkProduct Block - Folder and Audio Block) multiple times(twice)");
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as " + Input.rmu1userName);
		String folderName = "FolderName" + Utility.dynamicNameAppender();
		String saveSearchName = "resubmit" + Utility.dynamicNameAppender();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchStringStar);
		search.bulkFolder(folderName);
		int ExpectedPureHit = search.verifyCombinedSearch(folderName, "folder", "yes", "AND", "AND", "AND");
		String ExpectedPureHit1 = String.valueOf(ExpectedPureHit);
		driver.scrollPageToTop();
		search.saveSearchAdvanced(saveSearchName);
		search.selectSavedsearchInASWp(saveSearchName);
		baseClass.stepInfo("Configured a Work product search query -- saved search -" + saveSearchName + " ");
		search.serarchWP();
		driver.waitForPageToBeReady();
		String actualHits1 = search.getPureHitCount(1).getText();
		baseClass.stepInfo("Pure hit count after WP saved search is " + actualHits1);
		search.resubmitSearch();
		driver.waitForPageToBeReady();
		baseClass.waitTillTextToPresent(search.getPureHitCount(2), actualHits1);
		String actualHits2 = search.getPureHitCount(2).getText();
		baseClass.stepInfo("Pure hit count after resubmitting WP saved search is " + actualHits2);
		search.resubmitSearch();
		driver.waitForPageToBeReady();
		baseClass.waitTillTextToPresent(search.getPureHitCount(3), actualHits1);
		String actualHits3 = search.getPureHitCount(3).getText();
		baseClass.stepInfo("Pure hit count after resubmitting WP saved search is " + actualHits3);
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals(ExpectedPureHit1, actualHits1);
		assertion.assertEquals(ExpectedPureHit1, actualHits2); // Validation of pure hit count after resubmitting a
																// saved search
		assertion.assertEquals(ExpectedPureHit1, actualHits3);
		SavedSearch savedSearch = new SavedSearch(driver);
		savedSearch.SaveSearchDelete(saveSearchName);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
		tp.deleteAllFolders(folderName);
		assertion.assertAll();
		baseClass.passedStep(
				"Sucessfully Verified that - Application returns consistent search result when user resubmits a saved search(Content & Metadata Block,"
						+ "Conceptual Block,WorkProduct Block - Folder and Audio Block) multiple times(twice)");
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	////@Test(description ="RPMXCON-48745",groups = { "regression" }, enabled = true)
	public void verifyDocsCnt_SecGrp_OR_production() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48745");
		baseClass.stepInfo("Verify that - Application returns all the documents which are available under selected"
				+ " group with OR operator in search result.");
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		SessionSearch search = new SessionSearch(driver);
		search.navigateToAdvancedSearchPage();
		// Adding WP Security Group into search text box
		search.workProductSearch("security group", Input.securityGroup, true);
		search.serarchWP();
		driver.waitForPageToBeReady();
		String expectedPureHitCount = search.verifyPureHitsCount();// expected value
		baseClass.stepInfo("Total no docs available in default security group  " + expectedPureHitCount);
		baseClass.selectproject();
		search.navigateToAdvancedSearchPage();
		// Adding WP Security Group into search text box
		search.workProductSearch("security group", Input.securityGroup, true);
		// Adding Operator into search text box
		search.selectOperator("OR");
		// Adding WP Productions[any one production] into search text box
		search.workProductSearch("productions", productionname, false);
		baseClass.stepInfo("Configured a Query with SecurityGroup:[ " + Input.securityGroup + "] OR"
				+ " productions: [ name: [\"" + productionname + "\"], produced: \"true\" ]");
		search.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount = search.verifyPureHitsCount();
		baseClass.stepInfo("Total no docs after configuring a query as per test case and search  is   " + PureHitCount);
		System.out.println(expectedPureHitCount + PureHitCount);
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals(expectedPureHitCount, PureHitCount);// Validation of hit count.
		assertion.assertAll();
		baseClass
				.passedStep("Sucessfully verified that application returns all the documents which are available under "
						+ "selected group with OR operator in search result.");
	}

	//@Test(description ="RPMXCON-48158",dataProvider = "Users", groups = { "regression" })
	public void verifyAudioSearchCreteria(String username, String password) throws InterruptedException {
		lp = new LoginPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48158");
		lp.loginToSightLine(username, password);
		baseClass.stepInfo(
				"Verify that while Searching Audio with Content searches-  configured Audio search settings does not revert back to "
						+ "inappropriate setting in \"Audio\" block  on \"Advanced Search\" screen");
		String audioSearchString1 = Input.audioSearch;
		String audioSearchString2 = Input.audioSearchString1;
		String language = Input.language;
		String termOperator = Input.TermOperator;
		String loactionAudioFile = "Within:";
		int threshold = 34;
		String seconds = String.valueOf(Input.documentIdNum);
		SessionSearch search = new SessionSearch(driver);
		List<String> ExpectedResult = new ArrayList<String>(Arrays.asList(audioSearchString2, audioSearchString1));
		lp.loginToSightLine(username, password);
		String ExpectedConfidenceThreshold = search.configureAudioSearchBlock(audioSearchString1, audioSearchString2,
				language, threshold, termOperator, loactionAudioFile, seconds);
		baseClass.stepInfo("Configured valid query with  language pack as " + language + " and search terms are "
				+ audioSearchString1 + " " + audioSearchString2 + " "
				+ " selected 'ALL' as term Operator in drop down, as well as setting the Location In Audio to 5 seconds, Confidence Threshold to"
				+ ExpectedConfidenceThreshold + " ");
		search.advancedContentSearchConfigure(Input.searchString1);
		baseClass.stepInfo("Configured content query with search term " + Input.searchString1);
		driver.scrollPageToTop();
		search.getQuerySearchBtn().Click();
		search.validateAudioSearchResult(ExpectedResult, termOperator, language, seconds, ExpectedConfidenceThreshold);
		String actualContent = search.ConenetInSaerchResult().getText();
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals(actualContent, Input.searchString1); // verification of content and metadata search term
																	// in results page
		assertion.assertAll();
		baseClass.passedStep(
				"Sucesfully Verified that while Searching Audio with Content searches-  configured Audio search settings does not revert back to inappropriate "
						+ "etting in \"Audio\" block  on \"Advanced Search\" screen");
		lp.logout();
	}

	//@Test(description ="RPMXCON-48767",groups = { "regression" }, enabled = true)
	public void verifyResubmit_cntAndMetaAndConceptualAndWP_Prod() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48767");
		baseClass.stepInfo(
				"Verify that - Application returns consistent search result when user resubmits a saved search(Content & Metadata Block ,"
						+ "Conceptual Block and WorkProduct Block -Production) multiple times(twice)");
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as " + Input.pa1userName);
		String saveSearchName = "resubmit" + Utility.dynamicNameAppender();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		SessionSearch search = new SessionSearch(driver);
		search.navigateToAdvancedSearchPage();
		search.advancedSearchContentMetadata(Input.testData1);// C&M Search
		search.advancedSearchConceptual(Input.testData1); // Conceptual Search
		driver.scrollPageToTop();
		search.workProductSearch("productions", productionname, true);// Adding WP Productions[any one production] into
																		// search text box
		baseClass.stepInfo(
				"Configured a search query with content block,Conceptual block and workProduct(Productions) block");
		search.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount = search.verifyPureHitsCount();
		driver.scrollPageToTop();
		search.saveSearchAdvanced(saveSearchName);
		baseClass.stepInfo("Created a saved search -" + saveSearchName);
		search.selectSavedsearchInASWp(saveSearchName);
		baseClass.stepInfo("Configured a Work product 'saved search ' search query -- -" + saveSearchName + " ");
		search.serarchWP();
		driver.waitForPageToBeReady();
		baseClass.waitTillTextToPresent(search.getPureHitCount(1), PureHitCount);
		String actualHits1 = search.getPureHitCount(1).getText();
		baseClass.stepInfo("Pure hit count after WP saved search is " + actualHits1);
		search.resubmitSearch();
		driver.waitForPageToBeReady();
		baseClass.waitTillTextToPresent(search.getPureHitCount(2), PureHitCount);
		String actualHits2 = search.getPureHitCount(2).getText();
		baseClass.stepInfo("Pure hit count after resubmitting WP saved search is " + actualHits2);
		search.resubmitSearch();
		driver.waitForPageToBeReady();
		baseClass.waitTillTextToPresent(search.getPureHitCount(3), PureHitCount);
		String actualHits3 = search.getPureHitCount(3).getText();
		baseClass.stepInfo("Pure hit count after resubmitting WP saved search is " + actualHits3);
		SoftAssert assertion = new SoftAssert();
		// validations
		assertion.assertEquals(PureHitCount, actualHits1);
		assertion.assertEquals(PureHitCount, actualHits2);
		assertion.assertEquals(PureHitCount, actualHits3);
		SavedSearch savedSearch = new SavedSearch(driver);
		savedSearch.SaveSearchDelete(saveSearchName);
		assertion.assertAll();
		baseClass.passedStep(
				"Sucessfully Verified that - Application returns consistent search result when user resubmits a saved search(Content & Metadata Block ,Conceptual Block "
						+ "and WorkProduct Block -Production) multiple times(twice)");
		lp.logout();
	}
/**
 * @author Jayanthi.ganesan
 * @param username
 * @param password
 * @throws InterruptedException
 */
	////@Test(description ="RPMXCON-48795",dataProvider = "Users", groups = { "regression" })
	public void verifyProximityTildechar(String username, String password ) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48795");
		baseClass.stepInfo("Verify that result appears for proximity having phrase in Advanced Search Query Screen.");
		lp.loginToSightLine(username, password);
		SessionSearch search = new SessionSearch(driver);
		SoftAssert assertion=new SoftAssert();
		String[] searchInput= {"“iterative (“requirements evolve”)”~5","\"iterative (\"QA requirements evolve\")\"~5","\"iterative (\"requirements evolve Money\")\"~5"};
		for(int i=0;i<searchInput.length;i++) {
	    search.wrongQueryAlertAdvanceSaerch(searchInput[i], 13,"non fielded", null);	
		int pureHit = Integer.parseInt(search.getPureHitsCount().getText());
		assertion.assertNotNull(pureHit);
		System.out.println(pureHit);
		if(i!=2) {
		baseClass.selectproject();
		}
		}
		assertion.assertAll();
		baseClass.passedStep("Sucessfully Verified that result appears for proximity having phrase in Advanced Search Query Screen.");
		lp.logout();
	}
	/**
	 * @author Sowndarya
	 * @throws InterruptedException
	 * @description To verify, As an PA login into the Application, when user select
	 *              a multiple production with production status as "Already
	 *              Produced" from work product tab, he will be able to set that as
	 *              an search criteria
	 *                [  Note-Minimum of two productions[Completed] should be there in project.]
	 */
	//@Test(description ="RPMXCON-47755",groups = { "regression" })
	public void VerifySearchCriteriaWithMultipleProductions() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47755");
		baseClass.stepInfo("Verify user can set search criteria with multiple productions");
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		SessionSearch search = new SessionSearch(driver);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		search.switchToWorkproduct();
		search.selectMultipleProductionstWithAlreadyProducedFilter();
		search.verifySearchQueryForAlreadyProducedFilter();
		baseClass.passedStep(
				"Search criteria is set by selecting Multiple Production and Already Produced Optional Filter.");
		lp.logout();
	}

	/**
	 * @author Sowndarya
	 * @throws InterruptedException
	 * @description To verify, As an PA login into the Application, when user select
	 *              a multiple production with production status as "Selected for
	 *              productions" from work product tab, he will be able to set that
	 *              as an search criteria
	 *              
	 *           [   Note-Minimum of two productions should be there in project.]
	 */
	//@Test(description ="RPMXCON-47754",groups = { "regression" })
	public void VerifySearchCriteriaWithMultipleProductionsAndOptionalFilter() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47754");
		baseClass.stepInfo("Verify user can set search criteria with multiple productions");
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		SessionSearch search = new SessionSearch(driver);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		search.switchToWorkproduct();
		search.selectMultipleProductionstWithSelectedForProductionFilter();
		search.verifySearchQueryForSelectedForProductionFilter();
		baseClass.passedStep(
				"Search criteria is set by selecting Multiple Production and Selected For Productions Optional Filter.");
		lp.logout();
	}
	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @throws InterruptedException
	 */
	////@Test(description ="RPMXCON-47635",dataProvider = "Users",groups = { "regression" })
	public void verifyEmailAllDomain(String username, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id:47635 ");
		baseClass.stepInfo("Verify that Advanced Search is"
				+ " working properly for Email All Domain Metadata");
		DocListPage DocList =new DocListPage(driver);
		SoftAssert softAssert = new SoftAssert();
		
		lp.loginToSightLine(username,password);
		SessionSearch search = new SessionSearch(driver);
		
		// To get expected count of Email All domains search in advanced search screen we are
		//doing star search and viewing in doc list applying email all domains filter and getting docs count.
		search.basicContentSearch(Input.searchStringStar);	
		// view document in DocList page
		search.ViewInDocList();
		// applying filter and getting the document count
		 DocList.getIncludeFilterEmailAllDomain(Input.MetaDataDomainName);
		 SavedSearch ss=new SavedSearch(driver);
		 int docCount =ss.docListPageFooterCountCheck();
		 baseClass.stepInfo("Total number if documents available for metadata EmailAllDomain -"+Input.MetaDataDomainName+" is "+docCount);
		
		// performing metadata search and getting pureHit Count
		 baseClass.selectproject();
		search.advancedMetaDataSearch("EmailAllDomains", null,Input.MetaDataDomainName, null);
		int pureHit =Integer.parseInt(search.verifyPureHitsCount());
		 baseClass.stepInfo("Total number if documents available for metadata EmailAllDomain -"+Input.MetaDataDomainName+" when we perform metadata search in sdvanced saerch is "+pureHit);
		// comparing the document count and pure Hit Count
		softAssert.assertEquals(docCount, pureHit);
		softAssert.assertAll();
		baseClass.passedStep("Email All domains Functionality is working properly in advanced search screen.");
		lp.logout();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		lp = new LoginPage(driver);
		baseClass = new BaseClass(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		try {
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}
	@DataProvider(name = "Users_RMU_Rev")
	public Object[][] Users_RMU_Rev() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password },{ Input.rev1userName, Input.rev1password } };
		return users;
	}


	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed Advanced search Regression1**");
	}
}
