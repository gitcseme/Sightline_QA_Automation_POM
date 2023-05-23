package sightline.iceComponent;

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
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ICE_SL_Integration_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionSearch;
	DocListPage docList;
	DataSets dataSets;
	Input ip;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ip = new Input();
		ip.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		dataSets = new DataSets(driver);
		loginPage = new LoginPage(driver);
		
	}
	
	/**
	 * Author :Arunkumar date: 06/09/2022 TestCase Id:RPMXCON-50074
	 * Description :SL-ICE Integration: Verify that Metadata works correctly in Basic Search screen.  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-50074",enabled = true, groups = { "regression" })
	public void verifyMetaDataCustodianNameInBasicSearch() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id:RPMXCON-50074");
		baseClass.stepInfo("Verify that Metadata works correctly in Basic Search screen");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("verify dataset menu access and datasets availability");
		dataSets.verifyMenuAndDatasetsAvailability();
		baseClass.stepInfo("Perform metadata 'custodianName' in basic search");
		int docsCount=sessionSearch.MetaDataSearchInBasicSearch(Input.metaDataName, Input.custodianName_Andrew);
		sessionSearch.verifySearchResultReturnsForConfiguredQuery(docsCount);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 06/09/2022 TestCase Id:RPMXCON-50077
	 * Description :SL-ICE Integration: Verify that Metadata 'AttachCount' works correctly in Basic Search screen.  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-50077",enabled = true, groups = { "regression" })
	public void verifyMetaDataAttachCountInBasicSearch() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id:RPMXCON-50077");
		baseClass.stepInfo("Verify that Metadata works correctly in Basic Search screen");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("verify dataset menu access and datasets availability");
		dataSets.verifyMenuAndDatasetsAvailability();
		baseClass.stepInfo("Perform metadata 'AttachCount' in basic search");
		int docsCount=sessionSearch.MetaDataSearchInBasicSearch("AttachCount", "1");
		sessionSearch.verifySearchResultReturnsForConfiguredQuery(docsCount);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 06/09/2022 TestCase Id:RPMXCON-50078
	 * Description :SL-ICE Integration: Verify that Metadata FamilyRelationShip works correctly in Advanced Search screen. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-50078",enabled = true, groups = { "regression" })
	public void verifyMetaDataInAdvancedSearch() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-50078");
		baseClass.stepInfo("Verify that Metadata works correctly in Advanced Search screen");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("verify dataset menu access and datasets availability");
		dataSets.verifyMenuAndDatasetsAvailability();
		baseClass.stepInfo("Perform metadata 'FamilyRelationShip' in advanced search");
		int docsCount=sessionSearch.MetaDataSearchInAdvancedSearch("FamilyRelationship", "Parent");
		sessionSearch.verifySearchResultReturnsForConfiguredQuery(docsCount);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 06/09/2022 TestCase Id:RPMXCON-50079
	 * Description :SL-ICE Integration: Verify that Metadata MasterDate works correctly in Advanced Search screen. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-50079",enabled = true, groups = { "regression" })
	public void verifyMetaDataMasterDateInAdvancedSearch() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-50079");
		baseClass.stepInfo("Verify that Metadata works correctly in Advanced Search screen");
		String fromDate = "2011-03-13";
		String toDate = "2022-09-01";
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("verify dataset menu access and datasets availability");
		dataSets.verifyMenuAndDatasetsAvailability();
		baseClass.stepInfo("Perform metadata 'MasterDate' in advanced search");
		int docsCount=sessionSearch.advancedMetaDataSearch(Input.masterDateText, Input.range,fromDate,toDate);
		sessionSearch.verifySearchResultReturnsForConfiguredQuery(docsCount);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 09/12/2022 TestCase Id:RPMXCON-50076
	 * Description:SL-ICE Integration:Verify that Metadata EmailAuthorAddress works correctly in Basic Search screen.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-50076",enabled = true, groups = { "regression" })
	public void verifyEmailAuthorAddressMetadataQuery() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-50076");
		baseClass.stepInfo("Verify that Metadata EmailAuthorAddress works correctly in Basic Search screen.");
		String[] value = {"gouri.dhavalikar@consilio.com","Swapnal.Sonawane@consilio.com"};
		String[] field = {"EmailAuthorAddress"};
		String configQuery = null;
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");
		driver.waitForPageToBeReady();
		try {
			softAssert.assertTrue(dataSets.getDatasetBtn().isDisplayed());
			
		} catch (Exception e) {
			String data=dataSets.getDatasetBtn().getText();
			System.out.println(data);
		}
		baseClass.passedStep("DataSats left menu is enabled by default for the user with Project Admin role");
		baseClass.stepInfo("perform metadata search with query");
		for(int i=0;i<value.length;i++) {
			int count =sessionSearch.MetaDataSearchInBasicSearch(field[0],value[i]);
			if(count>0) {
				baseClass.passedStep("Docs returned for the configured query"+count);
				configQuery=value[i];
				break;
			}
			else {
				baseClass.stepInfo("no docs returned for the query"+value[i]);
				//performing search for next query
				baseClass.selectproject();
			}
		}
		baseClass.stepInfo("verify docs returned satisfied the configured query");
		sessionSearch.ViewInDocList();
		docList = new DocListPage(driver);
		docList.SelectColumnDisplayByRemovingExistingOnes(field);
		driver.waitForPageToBeReady();
		String returnQuery = docList.getDataInDoclist(1,4).getText();
		if(configQuery.equalsIgnoreCase(returnQuery)) {
			baseClass.passedStep("return documents satisfied configured query");
		}
		else {
			baseClass.failedStep("return docs not satisfied configured query");
		}
		loginPage.logout();
	}

	
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR ICE EXECUTED******");

	}
}
