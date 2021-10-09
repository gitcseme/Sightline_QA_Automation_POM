/*
 * Basic and Advance Search Functionalities with Operators, 
 * Metadata, Proximity Content and Conceptual
 *public void ConceptualSearch() 
	 *@author : Suresh Bavihalli 
	 * Created date: April 2019 
	 * Modified date:
	 * Modified by: 
	 * Description : validate conceptual Search in advance search
	 * with the low mid and high range options
 *
 *@BeforeClass(alwaysRun = true)
 	 * @author : Suresh Bavihalli 
	 * Created date: April 2019 
	 * Modified date:
	 * Modified by: 
	 * Description : Login as PAU, from here all the scripts will
	 * run!
 *
 *public void proximityAndRegExInBS(String searchString, int
	  smokeExpectedCount,int regressionExpectedCount)
 	 * @author : Suresh Bavihalli 
	 * Created date: Feb 2021 
	 * Modified date: 
	 * Modified by: 
	 * Description : As a PA user validate proximity search, regex with
	 * special chars in Basic search	
 *
 * public void contentSearchWithOperatorsInBS(String
	  searchString, int expectedCount)
	 * @author : Suresh Bavihalli 
	 * Created date: Feb 2019 
	 * Modified date: 
	 * Modified by: 
	 * Description : As a PA user validate content search in Basic search
	 * with operators
 *
 *public void contentSearchWithOperatorsInAS(String
	  searchString, int expectedCount)
	 * @author : Suresh Bavihalli 
	 * Created date: Feb 2019 
	 * Modified date: 
	 * Modified by: 
	 * Description : As a PA user validate content search in advanced search
	 * with operators
*
*public void metaDataSearch(String metaDataName, String IS_or_Range, String
	  first_input, String second_input, int smokeExpectedCount, int
	  regressionExpectedCount)
	 * @author : Suresh Bavihalli 
	 * Created date: Feb 2019 
	 * Modified date: 
	 * Modified by: 
	 * Description : As a PA user validate meta data searches in both basic
	 * and advance searches
*
*public void searchsavedSearch()
*	 * @author : Suresh Bavihalli 
	 * Created date: April 2019 
	 * Modified date:
	 * Modified by: 
	 * Description : save the search and validate in workproduct
*	
*public void audioSearch() 
	 * @author : Suresh Bavihalli 
	 * Created date: April 2019 
	 * Modified date:
	 * Modified by: 
	 * Description : Search audio files and validate the count
*
*public void Search_RPMXCON_37690()
*   
    * @author : Suresh Bavihalli Created date: Feb 2019 Modified date: Modified
	 * by: Description : RPMXCON_37690 is scripted
	
 */
package testScriptsSmoke;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import automationLibrary.Driver;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.openqa.selenium.Keys;

import executionMaintenance.ExtentTestManager;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;

/**
 * @author srinivas.a
 *
 */
public class TS_004_BasicAndAdvancedSearchOperations {

	Driver driver;	
	LoginPage loginPage;
	SessionSearch sessionSearch;
	BaseClass baseClass;

	String tagName = "tagSearch" + Utility.dynamicNameAppender();
	String folderName = "folderSearch" + Utility.dynamicNameAppender();
	String saveSearchName = "savedSearch" + Utility.dynamicNameAppender();
	String assignmentName = "assignmentSearch" + Utility.dynamicNameAppender();
	
	String proximity10Instance1 = "\"ProximitySearch Balance Iterative\"~10 OR ( \"ProximitySearch Balance Recall\"~10 AND \"ProximitySearch Recall\"~10) OR \"Balance Recall\"~10! OR ( \"develop* where evo*\"~7) OR ( \"money through\"~0 ) OR ( \"where m?ney\"~3 OR DocFileName:( \"Requirments Without\"~3) OR CustodianName:( \"P Allen\"~3) ) OR \"Number of holidays\"~0 OR \"ProximitySearch Balance Iterative\"~10 OR ( \"ProximitySearch Balance Recall\"~10 AND \"ProximitySearch Recall\"~10)";
	String proximity10Instance2	= "\"Truthful (\"Hunter S Shively\")\"~9 AND \"(\"ulie A Gomez\") Development\"~5 OR ( \"(\"my worksheet in case you want to review \") (\"money related\")\"~5 AND \"ProximitySearch (\"Balance of Recall\")\"~9  AND \"Collaboration (\"customer and\")\"~5 ) OR  (\"(\"in case* you want to\") (\"m?ney evolve\")\"~3 OR \"(\"Search* methodology\") (\"keyword highlighting\")\"~3 ) OR DocFileName: ( \"Requirments (\"format by ZL Technologies7\")\"~3) OR CustodianName:( \"P Allen\"~3) OR (\"money (\"development requirements\"~4)\"~6 OR \"m0ney (\"iterative methodology\"~4)\"~6)";	
	String proximity10Instance3 = "\"ProximitySearch Balance Iterative\"~10" + Keys.ENTER
			+ "OR" + Keys.ENTER
			+ " ( \"ProximitySearch Balance Recall\"~10"+ Keys.ENTER
			+ "AND"+ Keys.ENTER
			+ "\"ProximitySearch Recall\"~10)"+ Keys.ENTER
			+ "OR"+ Keys.ENTER
			+ "\"Balance Recall\"~10!"+ Keys.ENTER
			+ "OR"+ Keys.ENTER
			+ "( \"develop* where evo*\"~7)"+ Keys.ENTER
			+ "OR"+ Keys.ENTER
			+ "( \"money through\"~0 )"+ Keys.ENTER
			+ "OR"+ Keys.ENTER
			+ "( \"where m?ney\"~3"+ Keys.ENTER
			+ "OR"+ Keys.ENTER
			+ "DocFileName:( \"Requirments Without\"~3)"+ Keys.ENTER
			+ "OR"+ Keys.ENTER
			+ "CustodianName:( \"P Allen\"~3) )"+ Keys.ENTER
			+ "OR"+ Keys.ENTER
			+ "\"Number of holidays\"~0"+ Keys.ENTER
			+ "OR"+ Keys.ENTER
			+ "\"ProximitySearch Balance Iterative\"~10"+ Keys.ENTER
			+ "OR"+ Keys.ENTER
			+ "( \"ProximitySearch Balance Recall\"~10"+ Keys.ENTER
			+ "AND"+ Keys.ENTER
			+ "\"ProximitySearch Recall\"~10)";

	/**
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		
	  	driver = new Driver();
	
		// Login as a PA
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		UtilityLog.info("Logged in as Project Admin user");
		Reporter.log("Logged in as Project Admin user");
			baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);

	}

	/**
	 * @param searchString
	 * @param smokeExpectedCount
	 * @param regressionExpectedCount
	 * @param BasicTestCaseID
	 */
	@Test(dataProvider = "ProxAndRegx", groups = { "smoke", "regression" }, priority=1)
	public void proximityAndRegExInBasicSearch(String searchString, int smokeExpectedCount,int regressionExpectedCount, String BasicTestCaseID) {
		if(Input.extentReportMethodWise) {
			ExtentTestManager.getTest().log(Status.INFO, "Test Started");
			ExtentTestManager.getTest().log(Status.INFO,"Logged in as :"+Input.pa1userName);
			ExtentTestManager.getTest().log(Status.INFO,"Class Name: "+this.getClass().getSimpleName()+"/ Method Name: proximityAndRegExInBasicSearch");
			ExtentTestManager.getTest().log(Status.INFO,"Search/Parameter Key Word Is : "+searchString);
			ExtentTestManager.getTest().log(Status.INFO,"Test Case ID : "+BasicTestCaseID);
		}

		baseClass.selectproject();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int actualCount =  sessionSearch.basicContentSearch(searchString); 
		if(Input.numberOfDataSets ==1){
			Reporter.log("Searched '"+searchString+"' expected and actual counts are - "
					+smokeExpectedCount+" : "+ actualCount);
			AssertJUnit.assertEquals(smokeExpectedCount,actualCount); }
		else{
			Reporter.log("Searched '"+searchString+"' expected and actual counts are - "
					+regressionExpectedCount+" : "+ actualCount);
			AssertJUnit.assertEquals(regressionExpectedCount,actualCount);
		}				
	}

	/**
	 * @param searchString
	 * @param expectedCount
	 */
	@Test(dataProvider = "contentSearchwithOperators", groups = { "smoke","regression" }, priority=2) 
	public void contentSearchWithOperatorsInBasicSearch(String searchString, int expectedCount) {
		if(Input.extentReportMethodWise) {
			ExtentTestManager.getTest().log(Status.INFO, "Test Started");
			ExtentTestManager.getTest().log(Status.INFO, "Logged in as :"+Input.pa1userName);
			ExtentTestManager.getTest().log(Status.INFO, "Class Name: "+this.getClass().getSimpleName()+"/ Method Name: contentSearchWithOperatorsInBasicSearch");
			ExtentTestManager.getTest().log(Status.INFO, "Search/Parameter Key Word Is : "+searchString);
			ExtentTestManager.getTest().log(Status.INFO, "Test Case ID : RPMXCON-48798");
		}
		baseClass.selectproject(); driver.getWebDriver().get(Input.url + "Search/Searches");
		int actualCount = sessionSearch.basicContentSearch(searchString);
		Reporter.log("Searched '"+searchString+"' expected and actual counts are  "+expectedCount+" and "+ actualCount);
		AssertJUnit.assertEquals(expectedCount,actualCount);  	

	}


	/**
	 * @param searchString
	 * @param expectedCount
	 */
	@Test(dataProvider = "contentSearchwithOperators", groups = { "smoke", "regression" }, priority=3) 
	public void contentSearchWithOperatorsInAdvanceSearch(String searchString, int expectedCount) {
		if(Input.extentReportMethodWise) {
			ExtentTestManager.getTest().log(Status.INFO, "Test Started");
			ExtentTestManager.getTest().log(Status.INFO, "Logged in as :"+Input.pa1userName);
			ExtentTestManager.getTest().log(Status.INFO, "Class Name: "+this.getClass().getSimpleName()+"/ Method Name: contentSearchWithOperatorsInAdvanceSearch");
			ExtentTestManager.getTest().log(Status.INFO, "Search/Parameter Key Word Is : "+searchString);
			ExtentTestManager.getTest().log(Status.INFO, "Test Case ID : RPMXCON-48798");
		}
		baseClass.selectproject(); 
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int actualCount = sessionSearch.advancedContentSearch(searchString);
		Reporter.log("Searched '"+searchString+"' expected and actual counts are - "+expectedCount+" : "+ actualCount);
		AssertJUnit.assertEquals(expectedCount,actualCount);  
	}


	/**
	 * @param metaDataName
	 * @param IS_or_Range
	 * @param first_input
	 * @param second_input
	 * @param smokeExpectedCount
	 * @param regressionExpectedCount
	 */
	@Test(dataProvider = "metaDataSearch", groups = { "smoke", "regression" }, priority=4)
	public void metaDataSearch(String metaDataName, String IS_or_Range, String
			first_input, String second_input, int smokeExpectedCount, int
			regressionExpectedCount) {
		if(Input.extentReportMethodWise) {
			ExtentTestManager.getTest().log(Status.INFO, "Test Started");
			ExtentTestManager.getTest().log(Status.INFO, "Logged in as :"+Input.pa1userName);
			ExtentTestManager.getTest().log(Status.INFO, "Class Name: "+this.getClass().getSimpleName()+"/ Method Name: metaDataSearch");
			ExtentTestManager.getTest().log(Status.INFO, "Search Key Word Is : "+metaDataName + " "+ IS_or_Range +" "+ first_input + second_input);
			ExtentTestManager.getTest().log(Status.INFO, "Test Case ID : RPMXCON-48798");
		}
		baseClass.selectproject(); 
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int actualCount =sessionSearch.basicMetaDataSearch(metaDataName,IS_or_Range,first_input,second_input);
		if(Input.numberOfDataSets==1){
			Reporter.log("Searched '"+metaDataName+"' expected and actual counts are  "
					+smokeExpectedCount+" and "+ actualCount);
			AssertJUnit.assertEquals(smokeExpectedCount, actualCount);
		}else{
			Reporter.log("Searched '"+metaDataName+"' expected and actual counts are  "
					+regressionExpectedCount+" and "+ actualCount);
			AssertJUnit.assertEquals(regressionExpectedCount, actualCount); }	
		if(Input.extentReportMethodWise) {
		ExtentTestManager.getTest().log(Status.INFO, "Test Ended");
		}
	}

	@Test(groups = { "smoke", "regression" }, priority=5) 
	public void searchsavedSearch() {
		if(Input.extentReportMethodWise) {
			ExtentTestManager.getTest().log(Status.INFO, "Test Started");
			ExtentTestManager.getTest().log(Status.INFO, "Logged in as :"+Input.pa1userName);
			ExtentTestManager.getTest().log(Status.INFO, "Class Name: "+this.getClass().getSimpleName()+"/ Method Name: searchsavedSearch");
			ExtentTestManager.getTest().log(Status.INFO, "Search Key Word Is : "+saveSearchName);
			ExtentTestManager.getTest().log(Status.INFO, "Test Case ID : RPMXCON-48798");
		}
		baseClass.selectproject();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.basicContentSearch(Input.searchString1); sessionSearch.saveSearch(saveSearchName);

		baseClass.selectproject(); sessionSearch.switchToWorkproduct();
		sessionSearch.searchSavedSearch(saveSearchName); int actual = sessionSearch.serarchWP();
		Reporter.log("Searched '"+saveSearchName+"' expected and actual counts are  "
				+Input.pureHitSeachString1+" : "+ actual);
		AssertJUnit.assertEquals(Input.pureHitSeachString1,actual);	
	}

	@Test(groups = { "smoke", "regression" }, priority=6) 
	public void audioSearch() {
		if(Input.extentReportMethodWise) {
			ExtentTestManager.getTest().log(Status.INFO, "Test Started");
			ExtentTestManager.getTest().log(Status.INFO, "Logged in as :"+Input.pa1userName);
			ExtentTestManager.getTest().log(Status.INFO, "Class Name: "+this.getClass().getSimpleName()+"/ Method Name: audioSearch");
			ExtentTestManager.getTest().log(Status.INFO, "Search Key Word Is : "+Input.audioSearchString1 +"/ North American English");
			ExtentTestManager.getTest().log(Status.INFO, "Test Case ID : RPMXCON-48798");
		}
		baseClass.selectproject(); 
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int actualCount =sessionSearch.audioSearch(Input.audioSearchString1,"North American English");
		Reporter.log("Searched '"+Input.audioSearchString1+"' expected and actual counts are - "+Input.audioSearchString1pureHit+" : "+ actualCount);
		AssertJUnit.assertEquals(Input.audioSearchString1pureHit,actualCount);		
	}

	@Test(groups = { "smoke", "regression" }, priority=7)
	public void ConceptualSearch() {
		if(Input.extentReportMethodWise) {
			ExtentTestManager.getTest().log(Status.INFO, "Test Started");
			ExtentTestManager.getTest().log(Status.INFO, "Logged in as :"+Input.pa1userName);
			ExtentTestManager.getTest().log(Status.INFO, "Class Name: "+this.getClass().getSimpleName()+"/ Method Name: ConceptualSearch");
			ExtentTestManager.getTest().log(Status.INFO, "Search Key Word Is : "+Input.conceptualSearchString1 +"/ mid");
			ExtentTestManager.getTest().log(Status.INFO, "Test Case ID : RPMXCON-48798");
		}

		baseClass.selectproject();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int actualCount =sessionSearch.conceptualSearch(Input.conceptualSearchString1, "mid");		
		Reporter.log("Searched '"+Input.conceptualSearchString1+"' expected and actual counts are - "+Input.conceptualSearchString1PureHit+" : "+ actualCount);
		AssertJUnit.assertEquals(Input.conceptualSearchString1PureHit,actualCount);

	}

	//@Test(groups={"regression"}, priority=8)
	/**
	 * @throws InterruptedException
	 */
	public void Search_RPMXCON_37690() throws InterruptedException {
		SoftAssert softAssertion = new SoftAssert();
		String saveSearch = "01Test" + Utility.dynamicNameAppender();
		baseClass.selectproject();
		sessionSearch.basicContentSearch("MasterDate: [1980-01-01 TO 2018-01-01]" + Keys.ENTER + "AND" + Keys.ENTER + "("
				+ Keys.ENTER + "\"Test\"" + Keys.ENTER + "OR" + Keys.ENTER + "\"Commit\"" + Keys.ENTER + ")"
				+ Keys.ENTER + "AND" + Keys.ENTER + "(" + Keys.ENTER + "\"Test\"" + Keys.ENTER + "OR" + Keys.ENTER
				+ "\"Commit\"" + Keys.ENTER + ")");

		// Save the search
		sessionSearch.saveSearch(saveSearch);

		// View docs from session search
		sessionSearch.ViewInDocView();
		DocViewPage dv = new DocViewPage(driver);
		AssertJUnit.assertTrue(dv.getPersistentHit("Test").equals("Test- 1 Hit"));
		AssertJUnit.assertTrue(dv.getPersistentHit("Commit").equals("Commit- 1 Hit"));
		AssertJUnit.assertTrue(dv.getPersistentHit("MasterDat").equals("NULL"));

		// View docs from saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");

		SavedSearch svdSe = new SavedSearch(driver);
		svdSe.savedSearchToDocView(saveSearch);
		AssertJUnit.assertTrue(dv.getPersistentHit("Test").equals("Test- 1 Hit"));
		AssertJUnit.assertTrue(dv.getPersistentHit("Commit").equals("Commit- 1 Hit"));
		AssertJUnit.assertTrue(dv.getPersistentHit("MasterDat").equals("NULL"));

		softAssertion.assertAll();

	} 	 

	/**
	 * @param result
	 * @param testMethod
	 * @throws IOException
	 */
	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result,Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
	}

	/**
	 * @param result
	 * @param testMethod
	 */
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);

		}
		System.out.println("Executed :" + result.getMethod().getMethodName());		
	}

	/**
	 * Logout from the application, close the browser and clear cache the drivers 
	 */
	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			loginPage.logout();			
		} finally {
			loginPage.closeBrowser();
			loginPage.clearBrowserCache();
		}
	}

	//Search string, smoke expected count, regression expected count
	/**
	 * @return
	 */
	@DataProvider(name = "ProxAndRegx")
	public Object[][] proximityAndRegex() {
		return new Object[][] { 
			{ "\"illustratin* since Q499\"~20", 1,1,"RPMXCON-57331"}, 
			{ "\"**elief that the rights\"", 1,1,"RPMXCON-57307"},
			{ "\"very long\"", 1,2,"RPMXCON-57307"},
			{ "\"illustratin* since Q499\"~50", 1,2	,"RPMXCON-57307"},
			{ "\"**elief that the rights\"" + Keys.ENTER + "OR" + Keys.ENTER+ "\"illustratin* since Q499\"~50", 2,1,"RPMXCON-57304"},
			{ "\"**elief that + the rights\"", 1,1,"RPMXCON-57309"},
			{ "\"**elief that - the rights\"", 1,1,"RPMXCON-57309"},
			{ "\"**elief that = the rights\"", 1,1,"RPMXCON-57310"},
			{ "\"**elief that && the rights\"", 1,1,"RPMXCON-57311"},
			{ "\"**elief that || the rights\"", 1,1,"RPMXCON-57312"},
			{ "\"**elief that < the rights\"", 1,1,"RPMXCON-57313"},
			{ "\"**elief that! the rights\"", 0,0,"RPMXCON-57314"},
			{ proximity10Instance1, 120,1134,"RPMXCON-48798"},
			{ proximity10Instance2, 0,0 ,"RPMXCON-48798"},
			{ proximity10Instance3, 120,1134,"RPMXCON-48797"}

		};
	}

	//Search string and expected count
	/**
	 * @return
	 */
	@DataProvider(name = "contentSearchwithOperators")
	public Object[][] contentWithOps() {
		return new Object[][] { 

			{ Input.searchString1 + Keys.ENTER + "AND" + Keys.ENTER+ Input.searchString2, Input.searchString1ANDsearchString2},
			{ Input.searchString2 + Keys.ENTER + "AND" + Keys.ENTER+ Input.searchString1, Input.searchString1ANDsearchString2}, 
			{ Input.searchString1 + Keys.ENTER + "OR" + Keys.ENTER+ Input.searchString2, Input.searchString1ORsearchString2}, 
			{ Input.searchString1 + Keys.ENTER + "NOT" + Keys.ENTER+ Input.searchString2, Input.searchString1NOTsearchString2}, 
			{ Input.searchString2 + Keys.ENTER + "NOT" + Keys.ENTER+ Input.searchString1, Input.searchString2NOTsearchString1},
			//All Operators Missed
			{ "Allen" + Keys.ENTER + "AND" + Keys.ENTER + "Test"  + Keys.ENTER + "OR"  + Keys.ENTER + "Comments" + Keys.ENTER + "NOT" + Keys.ENTER + "Exists", 40},
			{ "Allen" + Keys.ENTER + "OR"  + Keys.ENTER + "Test"  + Keys.ENTER + "AND" + Keys.ENTER + "Comments" + Keys.ENTER + "NOT" + Keys.ENTER + "Exists", 5},
			{ "Allen" + Keys.ENTER + "AND" + Keys.ENTER + "Test"  + Keys.ENTER + "OR"  + Keys.ENTER + "Comments",50},
			{ "Allen" + Keys.ENTER + "AND" + Keys.ENTER + "Test"  + Keys.ENTER + "NOT" + Keys.ENTER + "Comments",35},
			{ "Allen" + Keys.ENTER + "OR"  + Keys.ENTER + "Test"  + Keys.ENTER + "NOT" + Keys.ENTER + "Comments",1150},
			{ "Allen" + Keys.ENTER + "NOT" + Keys.ENTER + "Test"  + Keys.ENTER + "OR"  + Keys.ENTER + "Comments",1152}			

		};
	}
	//Meta data name, option IS or Range, first input, second input, smoke expected pure hit, regression expected pure hit
	/**
	 * @return
	 */
	@DataProvider(name = "metaDataSearch")
	public Object[][] metaData() {
		return new Object[][] { 
			{"CustodianName", null,Input.metaDataCN,null,Input.metaDataCNcount,Input.metaDataCNcount}, 
			{"MasterDate", "IS", "2010-04-06 22:18:00", null,1,1},
			{"MasterDate", "RANGE", "1986-04-06", "2010-04-06",116,1087},
			{"CreateDate", "IS", "2010-10-18", null,85,85},
			{"CreateDate", "RANGE", "2000-10-18", "2010-10-18",85,314},
			{"EmailSentDate", "IS", "1990-05-05", null,0,0},
			{"EmailSentDate", "RANGE", "1990-05-05", "2000-05-05",0,0},
			{"AppointmentStartDate", "IS", "1990-05-05", null,0,0},
			{"AppointmentStartDate", "RANGE", "1990-05-05", "2000-05-05",0,0}

		};
	}

}
