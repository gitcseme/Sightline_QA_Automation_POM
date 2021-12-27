package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearch_Regression1 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	AssignmentsPage agnmt;
	DocViewRedactions redact;
	SecurityGroupsPage security;
	SavedSearch saveSearch;
	SessionSearch session;
  
	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  48481 - Verify that "Count" display as BLANK in Saved Search Screen when user saved a ERROR Query through Batch Search Upload.
	 * Description : Verify that "Count" display as BLANK in Saved Search Screen when user saved a ERROR Query through Batch Search Upload.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 1)
	public void verifyCountBlankWithInvalidQueryBatchUploadSavedSearch() throws Exception {		
	
		baseClass.stepInfo("Test case Id: RPMXCON-48481");
		utility = new Utility(driver);
		baseClass.stepInfo("#### Verify that 'Count' display as BLANK in Saved Search Screen when user saved a ERROR Query through Batch Search Upload. ####");
		  
		saveSearch = new SavedSearch(driver);
		
		baseClass.stepInfo("Navigate To Saved Search Page");
		saveSearch. navigateToSavedSearchPage();
		
		baseClass.stepInfo("Upload Batch File");
		final String fileName = saveSearch.renameFile(Input.errorQueryFileLocation);
		saveSearch.uploadBatchFile_D(Input.errorQueryFileLocation,fileName,true);
		
		baseClass.stepInfo("Click on select button");
		saveSearch.getSubmitToUpload().Click();
		
		baseClass.stepInfo("Open uploded batch file.");
		saveSearch.openUplodedBatchFile(fileName);
		
		baseClass.stepInfo("Verify count field in saved search table is blank.");
		saveSearch.verifyCountFiledIsBlank(Input.invalidNameText);
		
		baseClass.stepInfo("Delete uploded batch file.");
		saveSearch.deleteUplodedBatchFile(fileName);
	}
	
	 /**
		 * @author Gopinath
		 * TestCase id :  48479 -Verify that "Count" display as BLANK in Saved Search Screen when user saved a DRAFT Basic search Query.
		 * Description : Verify that "Count" display as BLANK in Saved Search Screen when user saved a DRAFT Basic search Query.
		 */
		@Test(alwaysRun = true,groups={"regression"},priority = 2)
		public void validateCountOfSavedDraftBasicMetaDataSearch() {
			
			String BasicMetaDataSearchName = Input.randomText + Utility.dynamicNameAppender();
			
			baseClass.stepInfo("Test case Id: RPMXCON-48479 - Saved Search Sprint 08");
			baseClass.stepInfo("#### Verify that 'Count' display as BLANK in Saved Search Screen when user saved a DRAFT Basic search Query. ####");
		
			session = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate To Session Search Page");
			session.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("basic Meta Data Draft Search");
			session.basicMetaDataDraftSearch(Input.metaDataName, null,Input.metaDataCustodianNameInput, null);
			
			baseClass.stepInfo("Save Search");
			session.saveSearch(BasicMetaDataSearchName);
			  
			saveSearch = new SavedSearch(driver);
			
			baseClass.stepInfo("Navigate To Saved Search Page");
			saveSearch.navigateToSavedSearchPage();
			
			baseClass.stepInfo("Select Saved search with Near Dupe Count And CS Count");
			saveSearch.selectSavedsearchwithNearDupeCountAndCSCount(BasicMetaDataSearchName);
			
			baseClass.stepInfo("Count Verifaction Of Draft Basic Meta Data Search");
			saveSearch.countVerifactionOfDraftBasicMetaDataSearch(BasicMetaDataSearchName);
		}
	     
		
		/**
		 * @author Gopinath
		 * @Testcase_Id : RPMXCON-48482 : Verify that 'Count' display as BLANK in Saved Search Screen when user saved a DRAFT Basic/Advanced search Query
		 * @Description : Verify that "Count" display as BLANK in Saved Search Screen when user saved a DRAFT Basic/Advanced search Query
		 */
		@Test(alwaysRun = true,groups={"regression"},priority = 3)
		public void validCountOfBasicAndAndvancedDraftSavedSearch() {
			
			String BasicSearchName = Input.randomText + Utility.dynamicNameAppender();
			String AdvanceSearchName = Input.randomText + Utility.dynamicNameAppender();
			
			baseClass.stepInfo("Test case Id: RPMXCON-48482 - Saved Search sprint 08");
			baseClass.stepInfo("#### Verify that 'Count'display as BLANK in Saved Search Screen when user saved a DRAFT Basic/Advanced search Query. ####");
			
			session = new SessionSearch(driver);
			
			baseClass.stepInfo("Basic Content Draft Search");
			session.basicContentDraftSearch(Input.searchString1);
			
			baseClass.stepInfo("Navigated to Basic search page and Entered search String");
			session.saveSearch(BasicSearchName);
			
			baseClass.stepInfo("Select project");
			baseClass.selectproject();
			
			baseClass.stepInfo("advanced Content Draft Search");
			session.advanedContentDraftSearch(Input.searchString1);
			
			baseClass.stepInfo("Save search advanced");
			session.saveSearchadvanced(AdvanceSearchName);
			
			saveSearch = new SavedSearch(driver);
			
			baseClass.stepInfo("Navigate To Saved Search Page");
			saveSearch.navigateToSavedSearchPage();	
			
			baseClass.stepInfo("select Saved search with Near Dupe Count And CS Count");
			saveSearch.selectSavedsearchwithNearDupeCountAndCSCount(BasicSearchName);
			
			baseClass.stepInfo("Count Verifaction Of Draft Basic Meta Data Search");
			saveSearch.countVerifactionOfDraftBasicMetaDataSearch(BasicSearchName);
			
			baseClass.stepInfo("Select Saved search with Near Dupe Count And CS Count");
			saveSearch.selectSavedsearchwithNearDupeCountAndCSCount(AdvanceSearchName);
			
			baseClass.stepInfo("count Verifaction Of Draft Basic Meta Data Search");
			saveSearch.countVerifactionOfDraftBasicMetaDataSearch(AdvanceSearchName);
			
		}


	/**
		 * @author Gopinath
		 * @Testcase_Id : RPMXCON-48480 : Verify that 'Count' display as BLANK in Saved Search Screen when user saved a DRAFT Advanced search Query.
		 * @Description : Verify that "Count" display as BLANK in Saved Search Screen when user saved a DRAFT Advanced search Query.
		 */
		@Test(alwaysRun = true,groups={"regression"},priority = 4)
		public void validateCountOfSavedDraftAdvancedDataSearch() {
			
			String AdvanceSearchName = Input.randomText + Utility.dynamicNameAppender();
			baseClass.stepInfo("Test case Id: RPMXCON-48480 - Saved Search 08");
			baseClass.stepInfo("#### Verify that 'Count' display as BLANK in Saved Search Screen when user saved a DRAFT Advanced search Query ####");
			
			session = new SessionSearch(driver);
			
			baseClass.stepInfo("Advanced Meta Data Draft for draft");
			session.advancedMetaDataForDraft(Input.metaDataName, null, Input.metaDataCN, null);
			
			baseClass.stepInfo("Save Search advanced");
			session.saveSearchadvanced(AdvanceSearchName);
			
			saveSearch = new SavedSearch(driver);
			
			baseClass.stepInfo("Navigate To Saved Search Page");
			saveSearch.navigateToSavedSearchPage();	
			
			baseClass.stepInfo("Select Saved search with Near Dupe Count And CS Count");
			saveSearch.selectSavedsearchwithNearDupeCountAndCSCount(AdvanceSearchName);
			
			baseClass.stepInfo("Count Verifaction Of Draft Basic MetaData Search");
			saveSearch.countVerifactionOfDraftBasicMetaDataSearch(AdvanceSearchName);
			
		}
		
	@AfterMethod(alwaysRun = true)
	public void close() {
		try {
			loginPage.logout();
		} finally {
			loginPage.closeBrowser();
			LoginPage.clearBrowserCache();
		} 
	}
	
     @AfterMethod(alwaysRun = true)
	 public void takeScreenShot(ITestResult result) {
 	 if(ITestResult.FAILURE==result.getStatus()){
 		 
 		Utility bc = new Utility(driver);
 		bc.screenShot(result);
 		try{ //if any tc failed and dint logout!
 		loginPage.logout();
 		}catch (Exception e) {
			// TODO: handle exception
		}
 	}
 	 System.out.println("Executed :" + result.getMethod().getMethodName());
 	
     }
     
}
