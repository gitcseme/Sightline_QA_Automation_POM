package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression01 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewPage docView;
	SessionSearch sessionSearch;
	Utility utility;
	DataSets dataSets;
	IngestionPage_Indium ingestionPage;
	Input ip;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ip= new Input();
		ip.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

		
	}
	
	
	
	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password ,Input.projectName02},
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password ,Input.projectName02 },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password, Input.projectName02 } };
	}

	
	/**
	 * Author : Mohan date: 22/04/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that documents ingested with the Date Format than the sightline standard format should be searchable by date fields'RPMXCON-49542' 
	 * @throws Exception
	 */
	@Test(enabled = true,dataProvider = "userDetails", groups = { "regression" }, priority = 1)
	public void verifyDateFormateInTheMeatadataFieldAndDocList(String fullName,String userName, String password,String projectName) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49542");
		baseClass.stepInfo("Verify that documents ingested with the Date Format than the sightline standard format should be searchable by date fields");
		String fieldName = Input.masterDateText;
		
		// Login as a Admin
		loginPage.loginToSightLine(userName, password, projectName);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		
		baseClass.stepInfo("Step 2: Login as @User   Search the documents with the date fields");
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(fieldName);
		
		baseClass.stepInfo("Step 3 : Search the documents and drag the result to shopping cartSelect action as 'View in DocView'");
		sessionSearch.ViewInDocView();
		
		baseClass.stepInfo("Step 4 : Verify the Date fields from the metadata");
		docView = new DocViewPage(driver);
		docView.selectMeatdataGetFieldValue(fieldName);
		
		baseClass.stepInfo("Step 5 : Select all documents   Click on View in Doc List   Verify the Date fields from the metadata");
		baseClass.selectproject(Input.projectName);
		sessionSearch.basicContentSearch(fieldName);
		sessionSearch.ViewInDocList();
		DocListPage docListPage = new DocListPage(driver);
		docListPage.verifyMasterDateColumnValue();
		
		
		}
	
	
	/**
	 * Author : Mohan date: 22/04/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that documents ingested with the Date Format than the sightline standard format should be viewable in Doc View with metadata'RPMXCON-49543' 
	 * @throws Exception
	 */
	@Test(enabled = true,dataProvider = "userDetails", groups = { "regression" }, priority = 2)
	public void verifyDateFormateInTheMeatadataField(String fullName,String userName, String password,String projectName) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49543");
		baseClass.stepInfo("Verify that documents ingested with the Date Format than the sightline standard format should be viewable in Doc View with metadata");
		String fieldName = Input.masterDateText;
		
		//Login as a Admin
		loginPage.loginToSightLine(userName, password, projectName);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		
		baseClass.stepInfo("Step 2: Login as @User   Search the documents with the date fields");
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(fieldName);
		
		baseClass.stepInfo("Step 3 : Search the documents and drag the result to shopping cartSelect action as 'View in DocView'");
		sessionSearch.ViewInDocView();
		
		baseClass.stepInfo("Step 4 : Verify the Date fields from the metadata");
		docView = new DocViewPage(driver);
		docView.selectMeatdataGetFieldValue(fieldName);
		
		}
	
	
	
	/**
	 * Author : Mohan date: 22/04/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that documents ingested with the Date Format than the sightline standard format should be viewable in Doc List with metadata'RPMXCON-49544' 
	 * @throws Exception
	 */
	@Test(enabled = true,dataProvider = "userDetails", groups = { "regression" }, priority = 3)
	public void verifyDateFormateInTheMeatadataFieldInDocList(String fullName,String userName, String password,String projectName) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49544");
		baseClass.stepInfo("Verify that documents ingested with the Date Format than the sightline standard format should be viewable in Doc List with metadata");
		String fieldName = Input.masterDateText;
		
		//Login as a Admin
		loginPage.loginToSightLine(userName, password, projectName);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		
		baseClass.stepInfo("Step 2: Login as @User   Search the documents with the date fields");
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(fieldName);
		
		baseClass.stepInfo("Step 3 : Search the documents and drag the result to shopping cartSelect action as 'View in DocList'");
		sessionSearch.ViewInDocList();
		
		baseClass.stepInfo("Step 4 : Verify the Date fields from the metadata");
		DocListPage docListPage = new DocListPage(driver);
		docListPage.verifyMasterDateColumnValue();
		
		}
	
	
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			loginPage.logoutWithoutAssert();
		}
		try {
//			loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
			// LoginPage.clearBrowserCache();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close2() {
		try {
			// LoginPage.clearBrowserCache();
		} catch (Exception e) {
			// no such session
		}

	}
}
