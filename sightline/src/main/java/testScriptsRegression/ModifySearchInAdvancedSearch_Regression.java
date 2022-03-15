package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ModifySearchInAdvancedSearch_Regression{
	Driver driver;
	LoginPage loginPage;
	String searchText;
	SessionSearch search;
	SecurityGroupsPage securityPage;
	RedactionPage redact;
	SoftAssert softAssertion;
	AssignmentsPage assgnPage;
	SavedSearch savedSearch;
	int pureHit;
	BaseClass baseClass;
	String option_yes = "Yes"; 
	String option_No = "No";
	String saveSearchName_2 = "Patch" + Utility.dynamicNameAppender();
	String assignmentName_2 = "Patch" + Utility.dynamicNameAppender();
	String RedactionName_2 = "Patch" + Utility.dynamicNameAppender();
	String securitygroupname_2 = "Patch" + Utility.dynamicNameAppender();
	String Tagnameprev = "Privileged";
	String productionname_2 = "Patch" + Utility.dynamicNameAppender();
	String PrefixID_2 = "A_" + Utility.dynamicNameAppender();
	String SuffixID_2 = "_P" + Utility.dynamicNameAppender();;
	String foldernameProd = "FolderProd" + Utility.dynamicNameAppender();
	String TagnameProd = "TagProd" + Utility.dynamicNameAppender();
    String TagName_2="Patch"+ Utility.dynamicNameAppender();
    String folderName_2="Patch"+ Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		search = new SessionSearch(driver);
		softAssertion=new SoftAssert ();
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		loginPage = new LoginPage(driver);
		searchText = Input.searchString1;
		
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
	
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
		UtilityLog.info("Executed :" + result.getMethod().getMethodName());
		loginPage.logout();
		System.out.println("logged out");
	}

	/**
	 * @author Jayanthi.ganesan 
	 * 
	 * @description:Verify that correct search result appears when User edit work
	 *                     product (saved search) contains "NOT" Or "OR" text in the name
	 *                     itself and tries to search again on search screen.
	 *                     (RPMXCON-57975)(RPMXCON-57968)(RPMXCON-57958)
	 */

	@Test(dataProvider = "WPSearchwithOperators",groups = { "regression" }, priority = 1,enabled=true)
	public void verifyEditASearchResultInWPSavedSearch(String operator) throws InterruptedException, ParseException, IOException {
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String saveSearchName = "Rel701 "+operator+" Patch" + Utility.dynamicNameAppender();
		if(operator=="NOT") { baseClass.stepInfo("Test case Id: RPMXCON-57975");}
		if(operator=="OR") { baseClass.stepInfo("Test case Id: RPMXCON-57968");}
		if(operator=="AND") { baseClass.stepInfo("Test case Id: RPMXCON-57958");}
		search = new SessionSearch(driver);
		if(operator=="NOT") {
			search.createContentSearchAndSavedSearchAS(saveSearchName_2, searchText);
			baseClass.stepInfo("Created a saved search -" + saveSearchName_2);
			baseClass.selectproject();
		}
		UtilityLog.info("#### Verify Modify search Results in with text "+operator+"SavedSearch(WP) name ####");
		search.createContentSearchAndSavedSearchAS(saveSearchName, Input.testData1);
		baseClass.stepInfo("Created a saved search -" + saveSearchName);
		search.selectSavedsearchInASWp(saveSearchName);
		baseClass.stepInfo("Configured a search query -- saved search -" + saveSearchName + " ");
		search.serarchWP();
		String PureHitCount = search.verifyPureHitsCount();
		baseClass.stepInfo("Pure hit count after WP saved search is " + PureHitCount);
		search.validateModifiedAdvnSearch(option_No, PureHitCount, "savedsearch");
		driver.waitForPageToBeReady();
		search.validateModifiedAdvnSearch(option_yes, PureHitCount, "savedsearch");

		// configured query with two saved search and any  operator between them.
		UtilityLog.info("### verify Modify Search Result In WP SavedSearch with "+operator+" Operator between two saved searches ##");
		baseClass.selectproject();
		search.selectSavedsearchInASWp(saveSearchName);
		driver.waitForPageToBeReady();
		search.selectOperator(operator);
		driver.waitForPageToBeReady();
		search.searchSavedSearch(saveSearchName_2);
		baseClass.stepInfo("Configured a search query with two saved search and "+operator+" operator in between ");
		search.serarchWP();
		baseClass.stepInfo("Pure hit count after WP saved search is " + PureHitCount);
		String expectedPureHit = search.verifyPureHitsCount();
		search.validateModifiedAdvnSearch(option_No, expectedPureHit, "savedsearch");
		driver.waitForPageToBeReady();
		search.validateModifiedAdvnSearch(option_yes, expectedPureHit, "savedsearch");
		savedSearch = new SavedSearch (driver);
		savedSearch.SaveSearchDelete(saveSearchName);
		if(operator=="OR") {
			savedSearch.SaveSearchDelete(saveSearchName_2);
		}
	}
	
	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description:Verify that correct search result appears when User edit work
	 *                     product (assignment) contains "NOT" or "OR" text in the name
	 *                     itself and tries to search again on search screen.
	 *                     (RPMXCON-57977),(RPMXCON-57970),(RPMXCON-57960)
	 */

	@Test(dataProvider = "WPSearchwithOperators",groups = { "regression" }, priority = 5, enabled=true)
	public void validateEditSearchResults_WPAssignments(String operator) throws InterruptedException {
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		if(operator=="NOT") { baseClass.stepInfo("Test case Id: RPMXCON-57977");}
		if(operator=="OR")  {baseClass.stepInfo("Test case Id: RPMXCON-57970");}
		if(operator=="AND")  {baseClass.stepInfo("Test case Id: RPMXCON-57960");}
		assgnPage = new AssignmentsPage(driver);
		if(operator=="NOT") {
			assgnPage = new AssignmentsPage(driver);
			assgnPage.createAssignment(assignmentName_2,Input.codeFormName);
			search.advancedContentSearch(searchText);
			search.bulkAssignExisting(assignmentName_2);
			baseClass.stepInfo("Created a assignment " + assignmentName_2);
			baseClass.selectproject();
		}
		String assignmentName = "Rel701 "+operator+" Patch" + Utility.dynamicNameAppender();
		assgnPage.createAssignment(assignmentName,Input.codeFormName);
		search.advancedContentSearch(Input.testData1);
		search.bulkAssignExisting(assignmentName);
		baseClass.stepInfo("Created a assignment " + assignmentName);
		baseClass.selectproject();
		search.switchToWorkproduct();
		baseClass.stepInfo("configured a query with "+assignmentName+" having text "+operator+" in the name itself");
		search.selectAssignmentInWPS(assignmentName);
		search.serarchWP();
		String expectedPureHit = search.verifyPureHitsCount();
		baseClass.stepInfo("Pure hit count after WP search In Assignemnts  is " +expectedPureHit );
  		search.validateModifiedAdvnSearch(option_No, expectedPureHit, assignmentName);
  		driver.waitForPageToBeReady();
		search.validateModifiedAdvnSearch(option_yes, expectedPureHit, assignmentName);

		baseClass.stepInfo("### verify Modify Search Result In WP Assignment with "+operator+" Operator ##");
		baseClass.selectproject();
		search.switchToWorkproduct();
		baseClass.stepInfo("configured a query with two assignment and "+operator+" operator between them-" + assignmentName
				+  operator  + assignmentName_2);
		search.selectAssignmentInWPS(assignmentName);
		driver.waitForPageToBeReady();
		search.selectOperator(operator);
		driver.waitForPageToBeReady();
		search.selectAssignmentInWPS(assignmentName_2);
		search.serarchWP();
		String expectedPureHitCount = search.verifyPureHitsCount();
		baseClass.stepInfo("Pure hit count after WP search In Assignemnts  is " +expectedPureHitCount );
		search.validateModifiedAdvnSearch(option_No, expectedPureHitCount, assignmentName_2);
		driver.waitForPageToBeReady();
		search.validateModifiedAdvnSearch(option_yes, expectedPureHitCount, assignmentName_2);
		assgnPage.deleteAssgnmntUsingPagination(assignmentName);
		if(operator=="OR") {
			assgnPage.deleteAssgnmntUsingPagination(assignmentName_2);
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * 
	 * @description:Verify that correct search result appears when User edit work
	 *                     product (security group) contains "NOT"or "OR" text in the name
	 *                     itself and tries to search again on search screen.
	 *                     (RPMXCON-57973),(RPMXCON-57966),(RPMXCON-57956)
	 */
	@Test(dataProvider = "WPSearchwithOperators",groups = { "regression" }, priority = 2,enabled=true)
	public void validateEditSearchResults_WPSecurityGroup(String operator) throws InterruptedException {
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);	
		if(operator=="NOT") { baseClass.stepInfo("Test case Id: RPMXCON-57973");}
		if(operator=="OR")  {baseClass.stepInfo("Test case Id: RPMXCON-57966");}
		if(operator=="AND")  {baseClass.stepInfo("Test case Id: RPMXCON-RPMXCON-57956");}
		securityPage = new SecurityGroupsPage(driver);
		if(operator=="NOT"){
			driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
			securityPage.AddSecurityGroup(securitygroupname_2);
			search.basicContentSearch(searchText);
			search.bulkRelease(securitygroupname_2);
			baseClass.stepInfo("Created a security group" + securitygroupname_2 + "bulk realese is done");
			baseClass.selectproject();
		}
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		String securitygroupname = "Rel701 "+operator+" Patch" + Utility.dynamicNameAppender();
		securityPage.AddSecurityGroup(securitygroupname);
		search.basicContentSearch(Input.testData1);
		search.bulkRelease(securitygroupname);
		baseClass.stepInfo("Created a security group" + securitygroupname + "Bulk Realese is done");
		baseClass.selectproject();

		search.switchToWorkproduct();
		search.selectSecurityGinWPS(securitygroupname);
		baseClass.stepInfo("Configured a search query Security group-" + securitygroupname + " ");
		search.serarchWP();
		String expectedCount = search.verifyPureHitsCount();
		
		search.validateModifiedAdvnSearch(option_yes, expectedCount, securitygroupname);
		driver.waitForPageToBeReady();
		search.validateModifiedAdvnSearch(option_No, expectedCount, securitygroupname);
		baseClass.selectproject();

		search.switchToWorkproduct();
		search.selectSecurityGinWPS(securitygroupname);
		driver.waitForPageToBeReady();
		search.selectOperator(operator);
		driver.waitForPageToBeReady();
		search.selectSecurityGinWPS(securitygroupname_2);
		baseClass.stepInfo("configured a query with two security groups and "+operator+" operator between them-" + securitygroupname
				+  operator  + securitygroupname_2);
		search.serarchWP();
		String expectedCount_2 = search.verifyPureHitsCount();
		search.validateModifiedAdvnSearch(option_yes, expectedCount_2,securitygroupname_2);
		driver.waitForPageToBeReady();
		search.validateModifiedAdvnSearch(option_No, expectedCount_2,securitygroupname_2);
//		securityPage.deleteSecurityGroups(securitygroupname);
//		if(operator=="OR") {
//			securityPage.deleteSecurityGroups(securitygroupname_2);
//		}

	}	
	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description:Verify that correct search result appears when User edit work
	 *                     product (production) contains "NOT" text in the name
	 *                     itself and tries to search again on search screen.
	 *                     (RPMXCON-57976),(RPMXCON-57969)
	 */
	
	//@Test(dataProvider = "WPSearchwithOperators",groups = {"regression"},priority=7,enabled=false )
		public void validateEditSearchin_AdvnSearchWPproduction(String operator) throws Exception {
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			if(operator=="NOT") { baseClass.stepInfo("Test case Id: RPMXCON-57976");}
			if(operator=="OR")  {baseClass.stepInfo("Test case Id: RPMXCON-57969");}
			if(operator=="AND")  {baseClass.stepInfo("Test case Id: RPMXCON-57959");}
			TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
			if(operator=="NOT") {
				driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
				tagPage.CreateFolder(foldernameProd,Input.securityGroup);
				baseClass.selectproject();
				tagPage.CreateTagwithClassification(TagnameProd, Tagnameprev);
		    	search.basicContentSearch("prabu");
				search.bulkFolderExisting(foldernameProd);
			    ProductionPage page = new ProductionPage(driver);
				driver.getWebDriver().get(Input.url+"Production/Home");
				page.CreateNewProduction(productionname_2, PrefixID_2, SuffixID_2, foldernameProd, TagnameProd);
				baseClass.stepInfo("Created a Production "+productionname_2);	
			}
			String productionname = "Rel701 "+operator+" Patch" + Utility.dynamicNameAppender();
			String PrefixID = "A_" + Utility.dynamicNameAppender();
			String SuffixID = "_P" + Utility.dynamicNameAppender();;
			String foldername = "FolderProd" + Utility.dynamicNameAppender();
			String Tagname = "Tag" + Utility.dynamicNameAppender();
			
			driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
			tagPage.CreateFolder(foldername, Input.securityGroup);
			tagPage.CreateTagwithClassification(Tagname, Tagnameprev);
			
			baseClass.selectproject();
			search.basicContentSearch(Input.testData1);
			search.bulkFolderExisting(foldername);

		     ProductionPage page = new ProductionPage(driver);
			page.CreateNewProduction(productionname, PrefixID, SuffixID, foldername, Tagname);
			baseClass.stepInfo("Created a Production "+productionname);
			baseClass.selectproject();
			Reporter.log("Production path is " + Input.prodPath, true);

			driver.getWebDriver().get(Input.url + "Search/Searches");
			search.switchToWorkproduct();
			search.selectProductionstInASwp(productionname);
			search.serarchWP();
			String PureHitCount = search.verifyPureHitsCount();
			baseClass.stepInfo("Pure hit count after serching under WP Production Search is " + PureHitCount);
			search.validateModifiedAdvnSearch(option_yes, PureHitCount, productionname);
			search.validateModifiedAdvnSearch(option_No, PureHitCount,productionname);
				
			baseClass.stepInfo("#### Verify Modify search Results(for Production) in AdvancedSearch(WP) using "+operator+" Operator ####");
			baseClass.selectproject();
			search.switchToWorkproduct();
			search.selectProductionstInASwp(productionname);
			driver.waitForPageToBeReady();
			search.selectOperator(operator);
			driver.waitForPageToBeReady();
			search.selectProductionstInASwp(productionname_2);
			baseClass.stepInfo("configured a query with two productions and "+operator+" operator between them-" + productionname
					+  operator  + productionname_2);
			search.serarchWP();
			String PureHitCount_2 = search.verifyPureHitsCount();
			baseClass.stepInfo("Pure hit count after WP Production Search is " + PureHitCount_2);
			search.validateModifiedAdvnSearch(option_yes, PureHitCount_2, productionname_2);
			search.validateModifiedAdvnSearch(option_No, PureHitCount_2, productionname);
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagPage.DeleteFolderWithSecurityGroup(foldername,Input.securityGroup);
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagPage.DeleteTagWithClassification(Tagname,Input.securityGroup);		
				if(operator=="OR") {
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagPage.DeleteFolderWithSecurityGroup(foldernameProd,Input.securityGroup);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagPage.DeleteTagWithClassification(TagnameProd,Input.securityGroup );
				}
		}

	/**
	 * @author Jayanthi.ganesan
	 *
	 * @description:Verify that correct search result appears when User edit work
	 *                     product (redactions) contains "NOT" text in the name
	 *                     itself and tries to search again on search screen.
	 *                     (RPMXCON-57974),(RPMXCON-57967),(RPMXCON-57957)
	 */

	@Test(dataProvider = "WPSearchwithOperators",groups = { "regression" }, priority = 6,enabled=true)
	public void verifyEditASearchResultInWP_Redactions(String operator) throws InterruptedException {
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		if(operator=="NOT") { baseClass.stepInfo("Test case Id: RPMXCON-57974");}
		if(operator=="OR") {baseClass.stepInfo("Test case Id: RPMXCON-57967");}
		if(operator=="AND")  {baseClass.stepInfo("Test case Id: RPMXCON-57957");}
		redact = new RedactionPage(driver);
		if(operator=="NOT") {
			redact.AddRedaction(RedactionName_2, "RMU");
			UtilityLog.info("Redaction added " + RedactionName_2);
			baseClass.selectproject();
			driver.getWebDriver().get(Input.url + "Search/Searches");
			search.basicContentSearch(searchText);
			search.viewInDocView_redactions();
		    DocViewPage dc = new DocViewPage(driver);
			driver.waitForPageToBeReady();
			dc.pageRedaction(RedactionName_2);
			baseClass.stepInfo("Page Redaction created sucessfully" + RedactionName_2);
			baseClass.selectproject();
		}
		String RedactionName = "Rel701 "+operator+" Patch02" + Utility.dynamicNameAppender();
		redact.AddRedaction(RedactionName, "RMU");
		UtilityLog.info("Redaction added " + RedactionName);
		baseClass.selectproject();
		search.basicContentSearch(Input.testData1);
		search.ViewInDocView();

		final DocViewPage dc = new DocViewPage(driver);
		driver.waitForPageToBeReady();
		driver.getWebDriver().get(Input.url + "DocumentViewer/DocView");
		dc.pageRedaction(RedactionName);
		baseClass.stepInfo("Redaction created sucessfully" + RedactionName);
		baseClass.stepInfo("#### Verify Modify search Results in Redactions(WP)Advanced Search ####");
		baseClass.selectproject();
		// Validate in advance search under work product search
		search.switchToWorkproduct();

		search.selectRedactioninWPS(RedactionName);
		baseClass.stepInfo("Configured a search query With Redactions -" + RedactionName + " ");
		softAssertion.assertEquals(search.serarchWP(), 1);
		search.validateModifiedAdvnSearch(option_No, "1", RedactionName);
		driver.waitForPageToBeReady();
		search.validateModifiedAdvnSearch(option_yes, "1", RedactionName);	

		baseClass.stepInfo("### verify Modify Search Result In WP Redactions with "+operator+" Operator ##");

		baseClass.selectproject();
		// Validate in advance search under work product search
		search.switchToWorkproduct();
		search.selectRedactioninWPS(RedactionName);
		driver.waitForPageToBeReady();
		search.selectOperator(operator);
		driver.waitForPageToBeReady();
		search.selectRedactioninWPS(RedactionName_2);
		baseClass.stepInfo("Configured a search query with two Redactions and "+operator+" operator in between -" + RedactionName +" "+operator +""+  RedactionName_2 + " ");
		search.serarchWP();
		String pureHitCount_expected = search.verifyPureHitsCount();
		search.validateModifiedAdvnSearch(option_No, pureHitCount_expected, RedactionName_2);
		driver.waitForPageToBeReady();
		search.validateModifiedAdvnSearch(option_yes, pureHitCount_expected, RedactionName);
//		redact.DeleteRedaction(RedactionName);
//		if(operator=="OR") {
//			redact.DeleteRedaction(RedactionName_2);
//		}
	}

	
	/**
	 * @author Jayanthi.ganesan  modified date-26/8/21
	 * 
	 * @description:Verify that correct search result appears when User edit work
	 *                     product (Tags) contains "NOT" Or "OR" text in the name
	 *                     itself and tries to search again on search screen.
	 *                     (RPMXCON-57971),(RPMXCON-57964)(RPMXCON-57951)
	 */

	@Test(dataProvider = "WPSearchwithOperators",groups = { "regression" }, priority = 3,enabled=true)
	public void verifyEditASearchResultInWPTag(String operator) throws InterruptedException, ParseException, IOException {
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		if(operator=="NOT") { baseClass.stepInfo("Test case Id: RPMXCON-57971");}
		if(operator=="OR")  {baseClass.stepInfo("Test case Id: RPMXCON-57964");}
		if(operator=="AND")  {baseClass.stepInfo("Test case Id: RPMXCON-57951");}
		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		if(operator=="NOT") {
			tagPage.CreateTag(TagName_2,Input.securityGroup);
			baseClass.selectproject();
			search.basicContentSearch(searchText);
			search.bulkTagExisting(TagName_2);
			baseClass.selectproject();		
		}
		String TagName = "Rel701 "+operator+" Patch" + Utility.dynamicNameAppender();
		UtilityLog.info("#### Verify Modify search Results in Tag(WP) with text "+operator+" in name ####");	
		tagPage.CreateTag(TagName, Input.securityGroup);
		search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(TagName);
		baseClass.selectproject();
		search.switchToWorkproduct();
		search.selectTagInASwp(TagName);
		baseClass.stepInfo("Configured a search query on Tag -"+ TagName +" ");		
		search.serarchWP();
		String expectedCount = search.verifyPureHitsCount();
		search.validateModifiedAdvnSearch(option_yes, expectedCount,TagName);
		driver.waitForPageToBeReady();
		search.validateModifiedAdvnSearch(option_No, expectedCount, TagName);

		// configured query with two Tags and operator between them.
		UtilityLog.info("### verify Modify Search Result In WP Tags with "+operator+" Operator ##");
		baseClass.selectproject();
		search.switchToWorkproduct();
		search.selectTagInASwp(TagName);
		driver.waitForPageToBeReady();
		search.selectOperator(operator);
		driver.waitForPageToBeReady();
		search.selectTagInASwp(TagName_2);
		search.serarchWP();
		String expectedCount_2 = search.verifyPureHitsCount();
		baseClass.stepInfo("configured a query with two Tags and "+operator+" operator between them-" + TagName
				+  operator  + TagName_2);
		search.validateModifiedAdvnSearch(option_yes, expectedCount_2, TagName_2);
		search.validateModifiedAdvnSearch(option_No, expectedCount_2, TagName_2);
		if(operator=="OR") {
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");	
			tagPage.deleteAllTags("Patch");
		}
	}	
	/**
	 * @author Jayanthi.ganesan  modified date-26/8/21
	 * 
	 * @description:Verify that correct search result appears when User edit work
	 *                     product (Folder) contains "NOT" Or "OR" or "AND" text in the name
	 *                     itself and tries to search again on search screen.
	 *                     (RPMXCON-57972),(RPMXCON-57965),(RPMXCON-57955)
	 */
		
	@Test(dataProvider = "WPSearchwithOperators",groups = { "regression" }, priority = 4,enabled=true)
	public void verifyEditASearchResultInWPFolder(String operator) throws InterruptedException, ParseException, IOException {
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		if(operator=="NOT") { baseClass.stepInfo("Test case Id: RPMXCON-57972");}
		if(operator=="OR")  {baseClass.stepInfo("Test case Id: RPMXCON-57965");}
		if(operator=="AND")  {baseClass.stepInfo("Test case Id: RPMXCON-57955");}
		if(operator=="NOT") {
			TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
			driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
			tagPage.CreateFolder(folderName_2, Input.securityGroup);
			baseClass.selectproject();
			search.basicContentSearch(searchText);
			search.bulkFolderExisting(folderName_2);
			baseClass.selectproject();	
		}
		String FolderName = "Rel701 "+operator+" Patch" + Utility.dynamicNameAppender();
		UtilityLog.info("#### Verify Modify search Results in Folder(WP) with text "+operator+" in name ####");
		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);		
		driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
		tagPage.CreateFolder(FolderName, Input.securityGroup);
		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(FolderName);
		baseClass.selectproject();
		search.switchToWorkproduct();
		search.selectFolderInASwp(FolderName);
		baseClass.stepInfo("Configured a search query on Folder -"+ FolderName +" ");		
		search.serarchWP();
		String expectedCount = search.verifyPureHitsCount();
		search.validateModifiedAdvnSearch(option_yes, expectedCount,FolderName);
		driver.waitForPageToBeReady();
		search.validateModifiedAdvnSearch(option_No, expectedCount, FolderName);

		// configured query with two Folder and operator between them.
		UtilityLog.info("### verify Modify Search Result In WP Folder with "+operator+" Operator ##");
		baseClass.selectproject();
		search.switchToWorkproduct();
		search.selectFolderInASwp(FolderName);
		driver.waitForPageToBeReady();
		search.selectOperator(operator);
		driver.waitForPageToBeReady();
		search.selectFolderInASwp(folderName_2);
		search.serarchWP();
		String expectedCount_2 = search.verifyPureHitsCount();
		baseClass.stepInfo("configured a query with two Folders and "+operator+" operator between them-" + FolderName
				+  operator  + folderName_2);
		search.validateModifiedAdvnSearch(option_yes, expectedCount_2, folderName_2);
		driver.waitForPageToBeReady();
		search.validateModifiedAdvnSearch(option_No, expectedCount_2, folderName_2);
		if(operator=="OR") {
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagPage.deleteAllFolders("Patch");
		}
	}	
		@DataProvider(name = "WPSearchwithOperators")
	public Object[][] advacnedSearchWithOps() {
		return new Object[][] {
			{"NOT"},
			{"AND"},
			{"OR"}
		};
	}
		

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			loginPage.quitBrowser();
		} finally {
			//LoginPage.clearBrowserCache();
		}
	}
}

