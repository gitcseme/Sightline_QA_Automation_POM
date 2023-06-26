package sightline.savedSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Map;

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

public class SavedSearch_Phase1_Regression5 {
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
	@Test(description ="RPMXCON-48481",alwaysRun = true,groups={"regression"} )
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
		saveSearch.getSubmitToUpload().waitAndClick(10);;
		
		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();
		
		baseClass.stepInfo("Open uploded batch file.");
		saveSearch.openUplodedBatchFile(fileName);
		
		baseClass.stepInfo("Verify count field in saved search table is blank.");
		saveSearch.verifyCountFiledIsBlank(Input.invalidNameText);
		
		baseClass.stepInfo("Delete uploded batch file.");
		saveSearch.deleteUplodedBatchFile(fileName);
		loginPage.logout();
	}
	
	 /**
		 * @author Gopinath
		 * TestCase id :  48479 -Verify that "Count" display as BLANK in Saved Search Screen when user saved a DRAFT Basic search Query.
		 * Description : Verify that "Count" display as BLANK in Saved Search Screen when user saved a DRAFT Basic search Query.
		 */
		@Test(description ="RPMXCON-48479",alwaysRun = true,groups={"regression"} )
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
			loginPage.logout();
		}
	     
		
		/**
		 * @author Gopinath
		 * @Testcase_Id : RPMXCON-48482 : Verify that 'Count' display as BLANK in Saved Search Screen when user saved a DRAFT Basic/Advanced search Query
		 * @Description : Verify that "Count" display as BLANK in Saved Search Screen when user saved a DRAFT Basic/Advanced search Query
		 */
		@Test(description ="RPMXCON-48482",alwaysRun = true,groups={"regression"} )
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
			loginPage.logout();
			
		}


	/**
		 * @author Gopinath
		 * @Testcase_Id : RPMXCON-48480 : Verify that 'Count' display as BLANK in Saved Search Screen when user saved a DRAFT Advanced search Query.
		 * @Description : Verify that "Count" display as BLANK in Saved Search Screen when user saved a DRAFT Advanced search Query.
		 */
		@Test(description ="RPMXCON-48480",alwaysRun = true,groups={"regression"} )
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
			loginPage.logout();
			
		}
		
		
		/**
		 * @author Gopinath
		 * @Testcase_Id : RPMXCON-48478 : Verify that "Count" gets update  in Saved Search Screen when user saved a Background Advanced search Query.
		 * @Description : Verify that "Count" gets update  in Saved Search Screen when user saved a Background Advanced search Query
		 */
		@Test(description ="RPMXCON-48478",alwaysRun = true,groups={"regression"} )
		public void validateCountOfSavedAdvancedSearch() {
			
			String advanceSearchName = Input.randomText + Utility.dynamicNameAppender();
			baseClass.stepInfo("Test case Id: RPMXCON-48478 - Saved Search 08");
			baseClass.stepInfo("#### Verify that \"Count\" gets update  in Saved Search Screen when user saved a Background Advanced search Query ####");
			
			SessionSearch sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Navigate To Session Search Page URL");
			sessionSearch.navigateToSessionSearchPageURL();
			
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
			
			driver.Navigate().refresh();
			
			baseClass.stepInfo("Get count from tills.");
			Map<String, String> tillsCount = sessionSearch.getCountFromTills();
			
			baseClass.stepInfo("Save searched content");
			sessionSearch.saveSearchHandle(advanceSearchName);
			
			saveSearch = new SavedSearch(driver);
			
			baseClass.stepInfo("Navigate To Saved Search Page");
			saveSearch.navigateToSavedSearchPage();	
			
			baseClass.stepInfo("Select Saved search with Near Dupe Count And CS Count");
			saveSearch.selectSavedsearchCount(advanceSearchName);
			
			baseClass.stepInfo("Count Verifaction Of Draft Basic MetaData Search");
			saveSearch.countVerifactionOfDraftBasicMetaDataSearch(tillsCount,advanceSearchName);
			loginPage.logout();
			
		}

		/**
		 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
		 * TestCase id :  48516 - Verify that "Pending" status appears in Saved Search Screen when user saved a Basic search through Batch Search Upload.
		 * Description : Verify that "Pending" status appears in Saved Search Screen when user saved a Basic search through Batch Search Upload.
		 */
		@Test(description ="RPMXCON-48516",alwaysRun = true,groups={"regression"} )
		public void verifyPendingStatusBatchUploadSavedSearch() throws Exception {		
		
			baseClass.stepInfo("Test case Id: RPMXCON-48516");
			utility = new Utility(driver);
			baseClass.stepInfo("#### Verify that 'Pending' status appears in Saved Search Screen when user saved a Basic search through Batch Search Upload ####");
			  
			saveSearch = new SavedSearch(driver);
			
			baseClass.stepInfo("Navigate To Saved Search Page");
			saveSearch. navigateToSavedSearchPage();
			
			baseClass.stepInfo("Upload Batch File");
			final String fileName = saveSearch.renameFile(Input.batchFileNewLocation);
			saveSearch.uploadBatchFile_D(Input.batchFileNewLocation,fileName,true);
			
			baseClass.stepInfo("Click on select button");
			saveSearch.getSubmitToUpload().Click();
			
			baseClass.stepInfo("Open uploded batch file.");
			saveSearch.openUplodedBatchFile(fileName);
			
			baseClass.stepInfo("Verify pending status appeared on saved search table.");
			saveSearch.verifyPendingStatusAppearedSavedSearchTable();
			
			baseClass.stepInfo("Delete uploded batch file.");
			saveSearch.deleteUplodedBatchFile(fileName);
			loginPage.logout();
		}
		

		/**
		 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
		 * TestCase id :  48517 - Verify that "Pending" status appears in Saved Search Screen when user saved a Advanced search, through Batch Search Upload.
		 * Description : Verify that "Pending" status appears in Saved Search Screen when user saved a Advanced search, through Batch Search Upload.
		 */
		@Test(description ="RPMXCON-48517",alwaysRun = true,groups={"regression"} )
		public void verifyPendingStatusBatchUploadAdvancedSavedSearch() throws Exception {		
		
			baseClass.stepInfo("Test case Id: RPMXCON-48517");
			utility = new Utility(driver);
			baseClass.stepInfo("#### Verify that 'Pending' status appears in Saved Search Screen when user saved a Advanced search, through Batch Search Upload ####");
			  
			saveSearch = new SavedSearch(driver);
			
			baseClass.stepInfo("Navigate To Saved Search Page");
			saveSearch. navigateToSavedSearchPage();
			
			baseClass.stepInfo("Upload Batch File");
			final String fileName = saveSearch.renameFile(Input.batchFileNewLocation);
			saveSearch.uploadBatchFile_D(Input.batchFileNewLocation,fileName,true);
			
			baseClass.stepInfo("Click on select button");
			saveSearch.getSubmitToUpload().Click();
			
			baseClass.stepInfo("Open uploded batch file.");
			saveSearch.openUplodedBatchFile(fileName);
			
			baseClass.stepInfo("Verify pending status appeared on saved search table.");
			saveSearch.verifyPendingStatusForAdvanceSearchAppeared();
			
			baseClass.stepInfo("Delete uploded batch file.");
			saveSearch.deleteUplodedBatchFile(fileName);
			loginPage.logout();
		}
		/**
		 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
		 * TestCase id :  48519 - Verify that "Completed" status appears in Saved Search Screen when user saved a Advanced search, through Batch Search Upload.
		 * Description : Verify that "Completed" status appears in Saved Search Screen when user saved a Advanced search, through Batch Search Upload.
		 */
		@Test(description ="RPMXCON-48519",alwaysRun = true,groups={"regression"} )
		public void verifyCompletedStatusBatchUploadAdvancedSavedSearch() throws Exception {		
		
			baseClass.stepInfo("Test case Id: RPMXCON-48519");
			utility = new Utility(driver);
			baseClass.stepInfo("#### Verify that 'Completed' status appears in Saved Search Screen when user saved a Advanced search, through Batch Search Upload ####");
			  
			saveSearch = new SavedSearch(driver);
			
			baseClass.stepInfo("Navigate To Saved Search Page");
			saveSearch. navigateToSavedSearchPage();
			
			baseClass.stepInfo("Upload Batch File");
			final String fileName = saveSearch.renameFile(Input.batchFileNewLocation);
			saveSearch.uploadBatchFile_D(Input.batchFileNewLocation,fileName,true);
			
			baseClass.stepInfo("Click on select button");
			saveSearch.getSubmitToUpload().Click();
			
			baseClass.stepInfo("Open uploded batch file.");
			saveSearch.openUplodedBatchFile(fileName);
			
			baseClass.stepInfo("Verify pending status appeared on saved search table.");
			saveSearch.verifyCompletedStatusForAdvanceSearchAppeared();
			
			baseClass.stepInfo("Delete uploded batch file.");
			saveSearch.deleteUplodedBatchFile(fileName);
			loginPage.logout();
		}
		@AfterMethod(alwaysRun = true)
		public void takeScreenShot(ITestResult result) {
			if (ITestResult.FAILURE == result.getStatus()) {
				Utility bc = new Utility(driver);
				bc.screenShot(result);
				System.out.println("Executed :" + result.getMethod().getMethodName());
				loginPage.logoutWithoutAssert();
			}
			try {
				loginPage.quitBrowser();
			} catch (Exception e) {
				loginPage.quitBrowser();
			}
		}

		@AfterClass(alwaysRun = true)
		public void close() {
			try {
			//	LoginPage.clearBrowserCache();

			} catch (Exception e) {
				System.out.println("Sessions already closed");
			}
		}		

     
}
