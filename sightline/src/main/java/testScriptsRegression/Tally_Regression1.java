
package testScriptsRegression;

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
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;
public class Tally_Regression1 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	BaseClass bc;
	String hitsCountPA;
	List<String> exp1 =Arrays.asList("2", "Gavin","Jaydeep","Owen","P Allen","Sai","Tyler","ViKas Mestry");
    List<String> exp2=Arrays.asList("Document", "Microsoft Word Document", "MS Excel Worksheet/Template (OLE)", "MS Outlook Message");
	List<String> exp3=Arrays.asList("","gouri.dhavalikar@symphonyteleca.com", "Jaydeep Gatlewar", "Jaydeep Gatlewar@symphonyteleca.com","Sai Theodare", "Sai.Theodare@symphonyteleca.com","ViKas Mestry","Vikas.Mestry@symphonyteleca.com","Vishal.Parikh@symphonyteleca.com");
	List<String> exp4=Arrays.asList("","gouri.dhavalikar@symphonyteleca.com","Jaydeep.Gatlewar@symphonyteleca.com","Sai.Theodare@symphonyteleca.com","Vikas.Mestry@symphonyteleca.com","Vishal.Parikh@symphonyteleca.com");
	String SearchName="Tally"+Utility.dynamicNameAppender();
	String  assgnName="Tally"+Utility.dynamicNameAppender();
	String  folderName="Tally"+Utility.dynamicNameAppender();
	String securityGrpName="Tally"+Utility.dynamicNameAppender(); 
	String projectName=Input.projectName;
	String[] sourceName_RMU = { assgnName, folderName,SearchName,"Default Security Group"};
	String[] sourceName_PA = {"Regression_AllDataset_Consilio1", folderName,SearchName,"Default Security Group" };
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		//Input in = new Input();
		//in.loadEnvConfig();

		// Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		// Login as a PA
		lp = new LoginPage(driver);
		// Search and save it
		SecurityGroupsPage securityPage = new SecurityGroupsPage(driver);
		SessionSearch ss = new SessionSearch(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		ss.basicContentSearch(Input.TallySearch);
		hitsCountPA=ss.verifyPureHitsCount();
		ss.saveSearchAtAnyRootGroup(SearchName, Input.shareSearchDefaultSG);
		ss.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assgnName, Input.codeFormName);
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created a assignment " + assgnName);  	
		driver.getWebDriver().get(Input.url+"Search/Searches");
		ss.bulkFolderWithOutHitADD(folderName);
		lp.logout();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityPage.AddSecurityGroup(securityGrpName);
		ss.basicContentSearch(Input.TallySearch);
		ss.bulkRelease(securityGrpName);
		bc.stepInfo("Created a security group" + securityGrpName + "Bulk Realese is done");
		lp.logout();
		lp.quitBrowser();  

	}
/**
 * @author Jayanthi.ganesan
 * @throws InterruptedException
 */
	@Test(groups = { "regression" }, priority = 1)
	public void ValidateTally_BulkRelease() throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56223");
		bc.stepInfo("To verify  Admin can release all the documents in a Tally");
		SoftAssert softAssertion = new SoftAssert();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		String securitygroupname = "SG" + Utility.dynamicNameAppender();
		SecurityGroupsPage securityPage = new SecurityGroupsPage(driver);
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityPage.AddSecurityGroup(securitygroupname);
		bc.stepInfo("Added security group -"+securitygroupname);
		TallyPage tp = new TallyPage(driver);
		tp.navigateTo_Tallypage();
		tp.SelectSource_SavedSearch(SearchName);
		tp.selectTallyByMetaDataField(Input.metaDataName);
		driver.waitForPageToBeReady();
		tp.tallyActions();
		String totalDocsCount = tp.bulkRelease(securitygroupname,false);
		bc.stepInfo("sucessfully released tally docs to security group  -"+securitygroupname);
		softAssertion.assertEquals(totalDocsCount,hitsCountPA );
		bc.stepInfo("Document Count associated to selected security group in search page is "+hitsCountPA);
		securityPage.deleteSecurityGroups(securitygroupname);
		softAssertion.assertAll();
		bc.passedStep("The docs released to Security group "+securitygroupname+" is reflected as expected");
	}
	
		/**
		 * @author Jayanthi.ganesan
		 * @param username
		 * @param password
		 * @param role
		 * @throws InterruptedException
		 */
		@Test(dataProvider = "Users_PARMU",groups = { "regression" }, priority = 2)
		public void ValidateTally_ViewInDocView(String username, String password, String role) throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56212");
			bc.stepInfo("To verify Admin/RMU will be able to view tallied documents in a doc view(Tally)");
			SoftAssert softAssertion = new SoftAssert();
			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as -" + role);
			TallyPage tp = new TallyPage(driver);
			tp.navigateTo_Tallypage();
			tp.SelectSource_SavedSearch(SearchName);
			tp.selectTallyByMetaDataField(Input.metaDataName);
			driver.waitForPageToBeReady();
			tp.tallyActions();
			tp.Tally_ViewInDocView();
			bc.stepInfo("Tally Report  generated for selected search.");
			DocViewPage dc = new DocViewPage(driver);
			String ActualCount = dc.verifyDocCountDisplayed_DocView();
			System.out.println(ActualCount);
			softAssertion.assertEquals(hitsCountPA, ActualCount);
			softAssertion.assertAll();
			bc.stepInfo("Document Count associated to selected saved search in Tally page is  displayed"
					+ " as excpeted in doc view page");
		}
		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */
		@Test(groups = { "regression" }, priority = 3)
		public void verifyTally_Assignments() throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56204");
			bc.stepInfo("To Verify RMU will have a report in Tally with document counts by metadata fields for assignment groups.");
			String[] metadata={"CustodianName","DocFileType","EmailAuthorName","EmailAuthorAddress" };
			lp.loginToSightLine(Input.rmu1userName,Input.rmu1password);
			bc.stepInfo("Logged in as RMU");
			String assignmentName="AssignTally"+Utility.dynamicNameAppender();
			SessionSearch sessionsearch = new SessionSearch(driver);
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			sessionsearch.basicContentSearch(Input.testData1);
			sessionsearch.bulkAssign();
			agnmt.FinalizeAssignmentAfterBulkAssign();
			agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			bc.stepInfo("Created a assignment " + assignmentName);  
			TallyPage tp = new TallyPage(driver);
			tp.navigateTo_Tallypage();
			tp.SelectSource_Assignment(assignmentName);
			for(int i=0;i<metadata.length;i++) {
			bc.stepInfo("**To Verify Tally Report if Tally By MetaData as-"+metadata[i]+"**");			
			tp.selectTallyByMetaDataField(metadata[i]);
			tp.validateMetaDataFieldName(metadata[i]);
			tp.verifyTallyChart();
			bc.passedStep("Verified whether RMU will have a report in Tally with document counts by metadata "
					+ "fields for assignment groups if Tally by metadata is "+metadata[i]);
			}
		}
		
		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */
		@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 4)
		public void verifyTally_Searches(String username, String password, String role) throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56203");
			bc.stepInfo("To Verify Admin/RMU will have a report in Tally with document counts by metadata fields for searches.");
			String[] metadata = { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" };
			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as " + role);
			SoftAssert softAssertion = new SoftAssert();
			TallyPage tp = new TallyPage(driver);
			tp.navigateTo_Tallypage();
			tp.SelectSource_SavedSearch(SearchName);
			for (int i = 0; i < metadata.length; i++) {
				bc.stepInfo("**To Verify Tally Report if Tally By MetaData as-" + metadata[i] + "**");
				tp.selectTallyByMetaDataField(metadata[i]);
				tp.validateMetaDataFieldName(metadata[i]);
				if (i == 0) {
					softAssertion.assertEquals(exp1, tp.verifyTallyChart());
				}
				if (i == 1) {
					softAssertion.assertEquals(exp2, tp.verifyTallyChart());
				}
				if (i == 2) {
					softAssertion.assertEquals(exp3, tp.verifyTallyChart());
				}
				if (i == 3) {
					softAssertion.assertEquals(exp4, tp.verifyTallyChart());
				}
				softAssertion.assertAll();
				bc.passedStep(
						"Verified whether " + role + " will have a report in Tally with document counts by metadata "
								+ "fields for Searches if Tally by metadata is " + metadata[i]);
			}
		}
		//savedSearchToTally
		

		
		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */
		@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 5)
		public void verifyTallyDropDown(String username, String password, String role) throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56196");
			bc.stepInfo("To Verify \"Tally By\" Drop Down And \"Apply\" button on Tally Page");
			String[] metadata = { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" };
			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as " + role);
			SoftAssert softAssertion = new SoftAssert();
			TallyPage tp = new TallyPage(driver);
			for(int k=0;k<4;k++) {
				tp.navigateTo_Tallypage();
				if(role=="PA") {
				tp.selectSources_PA(k,sourceName_PA[k]);}
				if(role=="RMU") {
					tp.selectSources_RMU(k,sourceName_RMU[k]);}
			for (int i = 0; i < metadata.length; i++) {
				tp.selectTallyByMetaDataField(metadata[i]);
				tp.validateMetaDataFieldName(metadata[i]);
				 tp.verifyTallyChart();
				softAssertion.assertAll();
			}
			bc.passedStep("Sucessfully verified tally by drop down and apply button if source is-- "+sourceName_PA[k] );
			}
		}
		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */
			@Test(groups = { "regression" }, priority = 6)
			public void ValidateSubTally_BulkRelease() throws InterruptedException {
				bc.stepInfo("Test case Id: RPMXCON-56226");
				bc.stepInfo("To Verify Admin can release all the documents in subtally");
				SoftAssert softAssertion = new SoftAssert();
				lp.loginToSightLine(Input.pa1userName, Input.pa1password);
				String securityGrp_SubTally ="SG" + Utility.dynamicNameAppender();
				SecurityGroupsPage securityPage = new SecurityGroupsPage(driver);
				driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
				securityPage.AddSecurityGroup(securityGrp_SubTally);
				bc.stepInfo("Added security group -"+securityGrp_SubTally);
				TallyPage tp = new TallyPage(driver);
				tp.navigateTo_Tallypage();
				tp.SelectSource_SavedSearch(SearchName);
				tp.selectTallyByMetaDataField(Input.metaDataName);
				bc.stepInfo("Created Tally Report for saved search-"+SearchName);
				driver.waitForPageToBeReady();
				tp.tallyActions();
				bc.waitTime(2);
				tp.getTally_actionSubTally().Click();
				tp.selectMetaData_SubTally(Input.docFileName);
				driver.waitForPageToBeReady();
				bc.stepInfo("Created SubTally Report");
				tp.subTallyActions();
				String totalDocsCount = tp.bulkRelease(securityGrp_SubTally,true);
				bc.stepInfo("sucessfully released sub-tally docs to security group  -"+securityGrp_SubTally);
				SessionSearch search = new SessionSearch(driver);
				search.switchToWorkproduct();
				search.selectSecurityGinWPS(securityGrp_SubTally);
				bc.stepInfo("Configured a search query with Security group-" + securityGrp_SubTally);
				search.serarchWP();
				String expectedCount = search.verifyPureHitsCount();
				softAssertion.assertEquals(totalDocsCount, expectedCount);
				bc.stepInfo("Document Count associated to selected security group in search page is "+expectedCount);
			//	securityPage.deleteSecurityGroups(securitygroupname);
				softAssertion.assertAll();
				bc.passedStep("The docs released to Security group "+securityGrp_SubTally+" is reflected as expected");
			}
		
		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */
		@Test( groups = { "regression" }, priority = 7)
		public void verifyTally_SG() throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-48704");
			bc.stepInfo("To Verify Admin/RMU will have a report that provides ability to view the document volume by metadata "
					+ "fields for security groups in Tally.");
			String[] metadata = { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" };
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			bc.stepInfo("Logged in as PA");
			SoftAssert softAssertion = new SoftAssert();
			TallyPage tp = new TallyPage(driver);
			tp.navigateTo_Tallypage();
			tp.SelectSource_SecurityGroup(securityGrpName);
			for (int i = 0; i < metadata.length; i++) {
				bc.stepInfo("**To Verify Tally Report if Tally By MetaData as-" + metadata[i] + "**");
				tp.selectTallyByMetaDataField(metadata[i]);
				tp.validateMetaDataFieldName(metadata[i]);
				if (i == 0) {
					softAssertion.assertEquals(exp1, tp.verifyTallyChart());
				}
				if (i == 1) {
					softAssertion.assertEquals(exp2, tp.verifyTallyChart());
				}
				if (i == 2) {
					softAssertion.assertEquals(exp3, tp.verifyTallyChart());
				}
				if (i == 3) {
					softAssertion.assertEquals(exp4, tp.verifyTallyChart());
				}
				softAssertion.assertAll();
				bc.passedStep(
						"Verified whether user will have a report in Tally with document counts by metadata "
								+ "fields for Searches if Tally by metadata is " + metadata[i]);
			}
		}
		//
	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
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

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}
	

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed Tally Regression1**");

	}
}
