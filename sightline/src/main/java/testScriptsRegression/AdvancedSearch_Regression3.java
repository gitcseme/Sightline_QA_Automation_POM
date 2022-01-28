package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.testng.ITestResult;
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
	String hitsCountPA;
	int hitsCount;
	
	
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException, AWTException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();	

	}

	/**
	 * @author Jayanthi.ganesan
	 */
	@Test(dataProvider = "Users", groups = { "regression" }, priority = 1, enabled = true)
	public void verifyResubmit_content(String username, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48765");
		baseClass.stepInfo("Verify that - Application returns consistent search result when user resubmits a "
				+ "saved search(Content & Metadata Block) multiple times(twice)");
		lp.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as " + username);
		String saveSearchName = "resubmit" + Utility.dynamicNameAppender();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		SessionSearch search = new SessionSearch(driver);
		search.advancedContentSearch( Input.testData1);
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
		baseClass.waitForElement(search.getPureHitsLastCount());
		baseClass.waitTime(2);
		String expectedHits2 = search.getPureHitsLastCount().getText();
		baseClass.stepInfo("Pure hit count after resubmitting WP saved search is " + expectedHits2);
		SoftAssert assertion=new SoftAssert();
		assertion.assertEquals(PureHitCount, expectedHits2);
		assertion.assertEquals(PureHitCount, expectedHits1);
        SavedSearch savedSearch=new SavedSearch(driver);		
		savedSearch.SaveSearchDelete(saveSearchName);
		assertion.assertAll();
		baseClass.passedStep("Sucessfully Verified that - Application returns consistent search result when user resubmits a "
				+ "saved search(Content & Metadata Block) multiple times(twice)");
	}
	
	/**
	 * @author Jayanthi.ganesan
	 */
	@Test( groups = { "regression" }, priority = 2, enabled = true)
public void verifyDocsCntAlreadyProduced() throws InterruptedException {
	
	baseClass.stepInfo("Test case Id: RPMXCON-48747");
	baseClass.stepInfo("Verify that - Application returns all the documents which are available under selected group with AND "
			+ "operator and production optional filters - status  in search result.");
	
	lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	baseClass.stepInfo("Logged in as PA" );
	SessionSearch search = new SessionSearch(driver);
	search.switchToWorkproduct();
	search.selctProductionsAlreadyProduced();
	search.serarchWP();
	driver.waitForPageToBeReady();
	String expectedPureHitCount = search.verifyPureHitsCount();//expected value
	baseClass.stepInfo("Total no docs in alerady produced status under Production  "+expectedPureHitCount);
	baseClass.selectproject();
	search.navigateToAdvancedSearchPage();
	//Adding WP Security Group into search text box
	search.workProductSearch("security group",Input.securityGroup,true);
	//Adding Operator into search text box
	search.selectOperator("AND");
	//Adding WP Productions-already produced into search text box
	search.selctProductionsAlreadyProduced();
	baseClass.stepInfo("Configured a Query with SecurityGroup:[ "+Input.securityGroup+"] AND Productions:[produced: \"true\"]"); 
	search.serarchWP();
	driver.waitForPageToBeReady();
	String PureHitCount = search.verifyPureHitsCount();
	baseClass.stepInfo("Total no docs after configured query as per test case is   "+PureHitCount);
	System.out.println(expectedPureHitCount+PureHitCount);
	SoftAssert assertion=new SoftAssert();
	assertion.assertEquals(expectedPureHitCount, PureHitCount);//Validation of hit count.
	assertion.assertAll();
	baseClass.passedStep("Sucessfully Verified that - Application returns all the documents which are available under selected group with AND operator and"
			+ " production optional filters - status  in search result.");
}
	
	
	/**
	 * @author Jayanthi.ganesan
	 * @throws AWTException 
	 */
	@Test( groups = { "regression" }, priority = 3, enabled = true)
public void verifyDocsCntAssgnments() throws InterruptedException, AWTException {
	
	baseClass.stepInfo("Test case Id: RPMXCON-48746");
	baseClass.stepInfo("Verify that - Application returns all the documents which are available under selected group and"
			+ " Assignments with OR operator in search result.");
	String tagName="combined"+Utility.dynamicNameAppender();
	String assgnName="combined"+Utility.dynamicNameAppender();
	lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	baseClass.stepInfo("Logged in as RMU" );
	SessionSearch search = new SessionSearch(driver);
	
	//Pre requesite creation
	//Performing Star search since this will add all avail docs from default sec group .
	search.basicContentSearch(Input.searchStringStar);
	String expectedHitsCount=search.verifyPureHitsCount();//expected value
	search.getIntoFullScreen();
	search.bulkTag(tagName);
	search.getExitFullScreen();
	baseClass.stepInfo("Created a Tag " + tagName+ "Count of docs bulk tagged"+expectedHitsCount); 
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
	//Adding WP tag into search text box
	search.workProductSearch("tag",tagName,true);
	//Adding Operator into search text box
	search.selectOperator("OR");
	//Adding WP assignment into search text box
	search.workProductSearch("assignments",assgnName,false);
	baseClass.stepInfo("Configured a Query with TagName:[ "+tagName+"] AND AssignmentName:[" + assgnName+"]"); 
	search.serarchWP();
	driver.waitForPageToBeReady();
	String PureHitCount = search.verifyPureHitsCount();
	System.out.println(expectedHitsCount+PureHitCount);
	SoftAssert assertion=new SoftAssert();
	//validation of pure hits
	assertion.assertEquals(expectedHitsCount, PureHitCount);
   agnmt.deleteAssgnmntUsingPagination(assgnName);
   TagsAndFoldersPage tp= new TagsAndFoldersPage(driver);
   this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
   tp.deleteAllTags(tagName);
	assertion.assertAll();
	baseClass.passedStep("Application   returned all the documents which are available under "
			+ "selected security group in search result.     "+PureHitCount);
	baseClass.passedStep("Sucessfully Verified that - Application returns all the documents which are available under selected group and "
			+ "Assignments with OR operator in search result.");
	
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
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			lp.quitBrowser();
		}
		try {
			lp.logout();
			//LoginPage.clearBrowserCache();
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

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed Advanced search Regression1**");
	}
}
