package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.SecurityGroupsPage;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	TagsAndFoldersPage tagAndFol;
	TallyPage tally;
	SecurityGroupsPage securityGroup;
	DocExplorerPage docexp;
	DataSets dataSets;

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Ex" + "ecution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}
	
	
	/**  
	 * @author Gopinath
	 * @TestCase id:50755 : Project level exported dataset details - Validate "Not Processed and Not Loaded" tab details for a project having Uploaded ingestions.
	 * @Description: Project level exported dataset details - Validate "Not Processed and Not Loaded" tab details for a project having Uploaded ingestions.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 1)
	public void verifyProjectLvelExportedDataSetWithExpFields() {
		baseClass = new BaseClass(driver);
		String location = "C:\\\\BatchPrintFiles\\\\downloads";
		String sheetName = "Not Processed and Not Loaded";
		String fieldName = "Dataset Name";
		String fieldName2 = "File Name";
		String fieldName3 = "Exclusion Reason";
		baseClass.stepInfo("Test case Id: RPMXCON-50755 Sprint 12");
		baseClass.stepInfo("### Project level exported dataset details - Validate 'Not Processed and Not Loaded' tab details for a project having Uploaded ingestions ###");
		dataSets = new DataSets(driver);
		
		baseClass.stepInfo("Navigate data sets page.");
		dataSets.navigateToDataSetsPage();
		
		baseClass.stepInfo("Perform export data sets.");
		dataSets.exportDataSets();
		
		baseClass.stepInfo("Download exported file.");
		dataSets.downloadExportedFile();
		
		baseClass.stepInfo("Verify '"+fieldName+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName);
		
		baseClass.stepInfo("Verify '"+fieldName2+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName2);
		
		baseClass.stepInfo("Verify '"+fieldName3+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName3);
	}
	
	/**  
	 * @author Gopinath
	 * @TestCase id:50756 : Project level exported dataset details - Validate "Not Processed and Not Loaded" tab details for an existing project having mapped ingestions and uploaded datasets.
	 * @Description: Project level exported dataset details - Validate "Not Processed and Not Loaded" tab details for an existing project having mapped ingestions and uploaded datasets
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 2)
	public void verifyProjectLvelExportedDataSetsExistingProject() {
		baseClass = new BaseClass(driver);
		String location = "C:\\\\BatchPrintFiles\\\\downloads";
		String sheetName = "Not Processed and Not Loaded";
		String fieldName = "Dataset Name";
		String fieldName2 = "File Name";
		String fieldName3 = "Exclusion Reason";
		baseClass.stepInfo("Test case Id: RPMXCON-50756 Sprint 12");
		baseClass.stepInfo("### Project level exported dataset details - Validate 'Not Processed and Not Loaded' tab details for an existing project having mapped ingestions and uploaded datasets ###");
		dataSets = new DataSets(driver);
		
		baseClass.stepInfo("Navigate data sets page.");
		dataSets.navigateToDataSetsPage();
		
		baseClass.stepInfo("Perform export data sets.");
		dataSets.exportDataSets();
		
		baseClass.stepInfo("Download exported file.");
		dataSets.downloadExportedFile();
		
		baseClass.stepInfo("Verify '"+fieldName+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName);
		
		baseClass.stepInfo("Verify '"+fieldName2+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName2);
		
		baseClass.stepInfo("Verify '"+fieldName3+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName3);
	}
	
  
	/**  
	 * @author Gopinath
	 * @TestCase id:50758 : Project level exported dataset details - Validate "Loaded with Error" tab details for an existing project having mapped and uploaded ingestions.
	 * @Description: Project level exported dataset details - Validate "Loaded with Error" tab details for an existing project having mapped and uploaded ingestions
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 1)
	public void verifyProjectLvelExportedDataSetWithLoadedWithErrorFields() {
		baseClass = new BaseClass(driver);
		String location = "C:\\\\BatchPrintFiles\\\\downloads";
		String sheetName = "Loaded with Errors";
		String sheetName2 = "Summary";
		String errorTypeFieldType = "Documents Loaded With Errors";
		String fieldName = "Dataset Name";
		String fieldName2 = "Type of Error Encountered";
		String fieldName3 = "Count of Records";
		baseClass.stepInfo("Test case Id: RPMXCON-50758 Sprint 12");
		baseClass.stepInfo("### Project level exported dataset details - Validate \"Loaded with Error\" tab details for an existing project having mapped and uploaded ingestions ###");
		dataSets = new DataSets(driver);
		
		baseClass.stepInfo("Navigate data sets page.");
		dataSets.navigateToDataSetsPage();
		
		baseClass.stepInfo("Perform export data sets.");
		dataSets.exportDataSets();
		
		baseClass.stepInfo("Download exported file.");
		dataSets.downloadExportedFile();
		
		baseClass.stepInfo("Verify '"+fieldName+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName);
		
		baseClass.stepInfo("Verify '"+fieldName2+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName2);
		
		baseClass.stepInfo("Verify '"+fieldName3+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName3);
		
		baseClass.stepInfo("Verify '"+fieldName3+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName2,errorTypeFieldType);
	}
	
	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {

			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				loginPage.logoutWithoutAssert();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());

	}
	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			//loginPage.quitBrowser();
		} finally {
			loginPage.closeBrowser();
			LoginPage.clearBrowserCache();
		}
	}
}
