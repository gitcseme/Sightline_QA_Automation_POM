package sightline.advancedSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearch_Production_Regression {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	ProductionPage page;
	TagsAndFoldersPage tagsAndFolderPage;
	List<String> batesRange = new ArrayList<String>();
	String docsAddedToPRoduction;
	String productionname;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// create tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch("crammer");
		docsAddedToPRoduction = sessionSearch.verifyPureHitsCount();
		sessionSearch.bulkTagExisting(tagname);

		// Create Prod for failed state
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		// logout
		loginPage.logout();
		driver.close();

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();

	}


	/**
	 * @Author Jayanthi
	 * @Description : Verify that - Application returns all the documents which are
	 *              available under selected group with AND operator and production
	 *              optional filters - Bates Range in search result.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-57166", groups = { "regression" }, enabled = true)
	public void verifySgAndProdBatesRange() throws Exception {
		List<String> batesRange = new ArrayList<String>();
		baseClass.stepInfo("Test case Id: RPMXCON-57166 Advanced Search");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group with AND operator"
						+ " and production optional filters - Bates Range in search result.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged In As : " + Input.pa1FullName);

		page = new ProductionPage(driver);
		page.navigateToProductionPage();
		// taking bataes range from prod page of production generated in @BeforeClass.
		batesRange = page.getBATES_RangeOfProduction(productionname);
		System.out.println(batesRange.get(0) + batesRange.get(1));
		// Select Work production Security group & "And" Operator
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectSecurityGinWPS(Input.securityGroup);
		sessionSearch.selectOperator("AND");

		// Enter Production Bates Range
		sessionSearch.selectWPWithProdBatesAndDateRange(false, true, "", batesRange.get(0), batesRange.get(1));
		sessionSearch.serarchWP();
		baseClass.stepInfo(" Configured productions query with Bates range" + batesRange.toString());
		sessionSearch.configuredQuery();
		// verify Purehit for configured query
		String purehit = sessionSearch.verifyPureHitsCount();
		baseClass.stepInfo("Purehit is  : " + purehit);

		String passMsg = "Application should  returns all the documents which are available under "
				+ "selected security group in search result when query with productions bates range in AND operator   ";
		String failMsg = "Application doesn't should  returns all the documents which are available under selected"
				+ " security group in search result when we query production bates range with AND operator.   ";
		baseClass.textCompareEquals(purehit, docsAddedToPRoduction, passMsg, failMsg);

		// logout
		loginPage.logout();

	}
	
	/**
	 * Author :Jayanthi TestCase Id:RPMXCON-49309 Description Verify that Conceptual
	 * tile return the result for WorkProduct >> Production Search in Advanced
	 * Search Screen.
	 * 
	 * 
	 */
	@Test(description = "RPMXCON-49309", groups = { "regression" })
	public void verifyConceptualTileForProductions() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49309");
		baseClass.stepInfo("Verify that Conceptual tile return the result for WorkProduct >> Production "
				+ "Search in Advanced Search Screen.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		baseClass.stepInfo("Production with name " + productionname + " is generated with doc count " + ""
				+ docsAddedToPRoduction + " in @Before class");
		
		baseClass.stepInfo("Configure query with workproduct-Production and search");
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectProductionstInASwp(productionname);
		baseClass.passedStep("Inserted Production " + productionname + " into  WP query");
		sessionSearch.serarchWP();
		baseClass.stepInfo("verify query search result and conceptually similar tile return result");
		sessionSearch.verifySearchResultAndConceptualTileReturnResult();
		
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
		System.out.println("**Executed Advanced search Regression 21**");
	}
}
