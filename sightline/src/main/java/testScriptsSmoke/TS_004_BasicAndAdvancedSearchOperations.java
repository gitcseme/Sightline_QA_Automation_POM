package testScriptsSmoke;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.Reporter;

import static org.testng.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Properties;
import java.util.concurrent.Callable;

import org.openqa.selenium.Keys;
import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;

public class TS_004_BasicAndAdvancedSearchOperations {

	Driver driver;
	LoginPage lp;
	SessionSearch ss;
	BaseClass bc;
	
	String tagName = "tagSearch" + Utility.dynamicNameAppender();
	String folderName = "folderSearch" + Utility.dynamicNameAppender();
	String saveSearchName = "savedSearch" + Utility.dynamicNameAppender();
	String assignmentName = "assignmentSearch" + Utility.dynamicNameAppender();

	/*
	 * Author : Suresh Bavihalli 
	 * Created date: April 2019 
	 * Modified date:
	 * Modified by: 
	 * Description : Login as PAU, from here all the scripts will
	 * run!
	 */
		
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		
		// Open browser

		//Input in = new Input();
		//in.loadEnvConfig();

		driver = new Driver();
		bc = new BaseClass(driver);
		ss = new SessionSearch(driver);
		// Login as a PA
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as PA user");
		Reporter.log("Logged in as PA user");
	}

	/*
	 * Author : Suresh Bavihalli 
	 * Created date: Feb 2021 
	 * Modified date: 
	 * Modified by: 
	 * Description : As a PA user validate proximity search, regex with
	 * special chars in Basic search
	 */
	@Test(dataProvider = "ProxAndRegx", groups = { "smoke", "regression" })
	public void proximityAndRegExInBS(String searchString, int smokeExpectedCount,int regressionExpectedCount) {
		bc.selectproject();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int actualCount = ss.basicContentSearch(searchString);
		if(Input.numberOfDataSets ==1){
			Reporter.log("Searched '"+searchString+"' expected and actual counts are - "+smokeExpectedCount+" : "+ actualCount);
			Assert.assertEquals(smokeExpectedCount,actualCount);
		}else{
			Reporter.log("Searched '"+searchString+"' expected and actual counts are - "+regressionExpectedCount+" : "+ actualCount);
			Assert.assertEquals(regressionExpectedCount,actualCount);
		}

	}

	/*
	 * Author : Suresh Bavihalli 
	 * Created date: Feb 2019 
	 * Modified date: 
	 * Modified by: 
	 * Description : As a PA user validate content search in Basic search
	 * with operators
	 */
	@Test(dataProvider = "contentSearchwithOperators", groups = { "smoke", "regression" })
	public void contentSearchWithOperatorsInBS(String searchString, int expectedCount) {

		
		bc.selectproject();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int actualCount = ss.basicContentSearch(searchString);
		Reporter.log("Searched '"+searchString+"' expected and actual counts are  "+expectedCount+" and "+ actualCount);
		Assert.assertEquals(expectedCount,actualCount);
	
	
	}
	
	
	
	/*
	 * Author : Suresh Bavihalli 
	 * Created date: Feb 2019 
	 * Modified date: 
	 * Modified by: 
	 * Description : As a PA user validate content search in advanced search
	 * with operators
	 */
	@Test(dataProvider = "contentSearchwithOperators", groups = { "smoke", "regression" })
	public void contentSearchWithOperatorsInAS(String searchString, int expectedCount) {
		
		bc.selectproject();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int actualCount = ss.advancedContentSearch(searchString);
		Reporter.log("Searched '"+searchString+"' expected and actual counts are - "+expectedCount+" : "+ actualCount);
		Assert.assertEquals(expectedCount,actualCount);
		
		
	}

	/*
	 * Author : Suresh Bavihalli 
	 * Created date: Feb 2019 
	 * Modified date: 
	 * Modified by: 
	 * Description : As a PA user validate meta data searches in both basic
	 * and advance searches
	 */
	@Test(dataProvider = "metaDataSearch", groups = { "smoke", "regression" })
	public void metaDataSearch(String metaDataName, String IS_or_Range, String first_input, String second_input, int smokeExpectedCount,
			int regressionExpectedCount) {

		
		bc.selectproject();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int actualCount =ss.basicMetaDataSearch(metaDataName,IS_or_Range,first_input,second_input);
		if(Input.numberOfDataSets==1){
			Reporter.log("Searched '"+metaDataName+"' expected and actual counts are  "+smokeExpectedCount+" and "+ actualCount);
			Assert.assertEquals(smokeExpectedCount, actualCount);
		}else{
			Reporter.log("Searched '"+metaDataName+"' expected and actual counts are  "+regressionExpectedCount+" and "+ actualCount);
			Assert.assertEquals(regressionExpectedCount, actualCount);
		}

	}
	
	
	/*
	 * Author : Suresh Bavihalli 
	 * Created date: April 2019 
	 * Modified date:
	 * Modified by: 
	 * Description : save the search and validate in workproduct
	 */
	@Test(groups = { "smoke", "regression" })
	public void searchsavedSearch() {
		
		bc.selectproject();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		ss.basicContentSearch(Input.searchString1);
		ss.saveSearch(saveSearchName);

		bc.selectproject();
		ss.switchToWorkproduct();
		ss.searchSavedSearch(saveSearchName);
		int actual = ss.serarchWP();
		Reporter.log("Searched '"+saveSearchName+"' expected and actual counts are  "+Input.pureHitSeachString1+" : "+ actual);
		Assert.assertEquals(Input.pureHitSeachString1,actual);
		

	}

	/*
	 * Author : Suresh Bavihalli 
	 * Created date: April 2019 
	 * Modified date:
	 * Modified by: 
	 * Description : Search audio files and validate the count
	 */
	@Test(groups = { "smoke", "regression" })
	public void audioSearch() {
		
		bc.selectproject();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int actualCount =ss.audioSearch(Input.audioSearchString1, "North American English");
		Reporter.log("Searched '"+Input.audioSearchString1+"' expected and actual counts are - "+Input.audioSearchString1pureHit+" : "+ actualCount);
		Assert.assertEquals(Input.audioSearchString1pureHit,actualCount);

	}

	/*
	 * Author : Suresh Bavihalli 
	 * Created date: April 2019 
	 * Modified date:
	 * Modified by: 
	 * Description : validate conceptual Search in advance search
	 * with the low mid and high range options
	 */
	@Test(groups = { "smoke", "regression" })
	public void ConceptualSearch() {
		
		bc.selectproject();
		/*
		 * softAssertion.assertTrue(ss.conceptualSearch(Input.
		 * conceptualSearchString1,"right")>=0); bc.selectproject();
		 */
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int actualCount =ss.conceptualSearch(Input.conceptualSearchString1, "mid");
		Reporter.log("Searched '"+Input.conceptualSearchString1+"' expected and actual counts are - "+Input.conceptualSearchString1PureHit+" : "+ actualCount);
		Assert.assertEquals(Input.conceptualSearchString1PureHit,actualCount);
		
		/*
		 * bc.selectproject();
		 * softAssertion.assertTrue(ss.conceptualSearch(Input.
		 * conceptualSearchString1,"left")>=2);
		 */

		
	}

	/*
	 * Author : Suresh Bavihalli Created date: Feb 2019 Modified date: Modified
	 * by: Description : RPMXCON_37690 is scripted
	 */
	//@Test(groups={"regression"})
	public void Search_RPMXCON_37690() throws InterruptedException {
		SoftAssert softAssertion = new SoftAssert();
		String saveSearch = "01Test" + Utility.dynamicNameAppender();
		bc.selectproject();
		ss.basicContentSearch("MasterDate: [1980-01-01 TO 2018-01-01]" + Keys.ENTER + "AND" + Keys.ENTER + "("
				+ Keys.ENTER + "\"Test\"" + Keys.ENTER + "OR" + Keys.ENTER + "\"Commit\"" + Keys.ENTER + ")"
				+ Keys.ENTER + "AND" + Keys.ENTER + "(" + Keys.ENTER + "\"Test\"" + Keys.ENTER + "OR" + Keys.ENTER
				+ "\"Commit\"" + Keys.ENTER + ")");

		// Save the search
		ss.saveSearch(saveSearch);

		// View docs from session search
		ss.ViewInDocView();
		DocViewPage dv = new DocViewPage(driver);
		softAssertion.assertTrue(dv.getPersistentHit("Test").equals("Test- 1 Hit"));
		softAssertion.assertTrue(dv.getPersistentHit("Commit").equals("Commit- 1 Hit"));
		softAssertion.assertTrue(dv.getPersistentHit("MasterDat").equals("NULL"));

		// View docs from saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");

		SavedSearch svdSe = new SavedSearch(driver);
		svdSe.savedSearchToDocView(saveSearch);
		softAssertion.assertTrue(dv.getPersistentHit("Test").equals("Test- 1 Hit"));
		softAssertion.assertTrue(dv.getPersistentHit("Commit").equals("Commit- 1 Hit"));
		softAssertion.assertTrue(dv.getPersistentHit("MasterDat").equals("NULL"));

		softAssertion.assertAll();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result,Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);

		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			lp.logout();
			// lp.quitBrowser();
		} finally {
			lp.quitBrowser();
			lp.clearBrowserCache();
		}
	}

	//Search string, smoke expected count, regression expected count
	@DataProvider(name = "ProxAndRegx")
	public Object[][] proximityAndRegex() {
		return new Object[][] { 
			{ "\"illustratin* since Q499\"~20", 1,1 }, 
			{ "\"**elief that the rights\"", 1,1 },
			{ "\"very long\"", 1,1}

		};
	}
	
	//Search string and expected count
	@DataProvider(name = "contentSearchwithOperators")
	public Object[][] contentWithOps() {
		return new Object[][] { 
			
			{ Input.searchString1 + Keys.ENTER + "AND" + Keys.ENTER+ Input.searchString2, Input.searchString1ANDsearchString2},
			{ Input.searchString2 + Keys.ENTER + "AND" + Keys.ENTER+ Input.searchString1, Input.searchString1ANDsearchString2}, 
			{ Input.searchString1 + Keys.ENTER + "OR" + Keys.ENTER+ Input.searchString2, Input.searchString1ORsearchString2}, 
			{ Input.searchString1 + Keys.ENTER + "NOT" + Keys.ENTER+ Input.searchString2, Input.searchString1NOTsearchString2}, 
			{ Input.searchString2 + Keys.ENTER + "NOT" + Keys.ENTER+ Input.searchString1, Input.searchString2NOTsearchString1}, 
			

		};
	}
	//Meta data name, option IS or Range, first input, second input, smoke expected pure hit, regression expected pure hit
	@DataProvider(name = "metaDataSearch")
	public Object[][] metaData() {
		return new Object[][] { 
				{ "CustodianName", null,Input.metaDataCN,null,Input.metaDataCNcount,Input.metaDataCNcount}, 
				{"MasterDate", "IS", "2010-04-06 22:18:00", null,1,1},
				{"MasterDate", "RANGE", "1986-04-06", "2010-04-06",116,116},
				{"CreateDate", "IS", "2010-10-18", null,85,85},
				{"CreateDate", "RANGE", "2000-10-18", "2010-10-18",85,85},

			};
		}

}
