
package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
	String hitsCountRMU;
	String saveSearchNamePA = "A" + Utility.dynamicNameAppender();
	String saveSearchNameRMU = "A" + Utility.dynamicNameAppender();

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
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		// Search and save it
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		hitsCountPA = search.verifyPureHitsCount();
		search.saveSearch(saveSearchNamePA);
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// Search and save it
		search.basicContentSearch(Input.testData1);
		hitsCountRMU = search.verifyPureHitsCount();
		search.saveSearch(saveSearchNameRMU);
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
		tp.SelectSource_SavedSearch(saveSearchNamePA);
		tp.selectTallyByMetaDataField(Input.metaDataName);
		driver.waitForPageToBeReady();
		tp.tallyActions();
		String totalDocsCount = tp.bulkRelease(securitygroupname,false);
		bc.stepInfo("sucessfully released tally docs to security group  -"+securitygroupname);
		SessionSearch search = new SessionSearch(driver);
		search.switchToWorkproduct();
		search.selectSecurityGinWPS(securitygroupname);
		bc.stepInfo("Configured a search query with Security group-" + securitygroupname);
		search.serarchWP();
		String expectedCount = search.verifyPureHitsCount();
		softAssertion.assertEquals(totalDocsCount, expectedCount);
		bc.stepInfo("Document Count associated to selected security group in search page is "+expectedCount);
		securityPage.deleteSecurityGroups(securitygroupname);
		softAssertion.assertAll();
		bc.passedStep("The docs released to Security group "+securitygroupname+" is reflected as expected");
	}
	
	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
		@Test(groups = { "regression" }, priority = 2)
		public void ValidateSubTally_BulkRelease() throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56226");
			bc.stepInfo("To Verify Admin can release all the documents in subtally");
			SoftAssert softAssertion = new SoftAssert();
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			String securitygroupname ="SG" + Utility.dynamicNameAppender();
			SecurityGroupsPage securityPage = new SecurityGroupsPage(driver);
			driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
			securityPage.AddSecurityGroup(securitygroupname);
			bc.stepInfo("Added security group -"+securitygroupname);
			TallyPage tp = new TallyPage(driver);
			tp.navigateTo_Tallypage();
			tp.SelectSource_SavedSearch(saveSearchNamePA);
			tp.selectTallyByMetaDataField(Input.metaDataName);
			bc.stepInfo("Created Tally Report for saved search-"+saveSearchNamePA);
			driver.waitForPageToBeReady();
			tp.tallyActions();
			bc.waitTime(2);
			tp.getTally_actionSubTally().Click();
			tp.selectMetaData_SubTally(Input.docFileName);
			driver.waitForPageToBeReady();
			bc.stepInfo("Created SubTally Report");
			tp.subTallyActions();
			String totalDocsCount = tp.bulkRelease(securitygroupname,true);
			bc.stepInfo("sucessfully released sub-tally docs to security group  -"+securitygroupname);
			SessionSearch search = new SessionSearch(driver);
			search.switchToWorkproduct();
			search.selectSecurityGinWPS(securitygroupname);
			bc.stepInfo("Configured a search query with Security group-" + securitygroupname);
			search.serarchWP();
			String expectedCount = search.verifyPureHitsCount();
			softAssertion.assertEquals(totalDocsCount, expectedCount);
			bc.stepInfo("Document Count associated to selected security group in search page is "+expectedCount);
		//	securityPage.deleteSecurityGroups(securitygroupname);
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
		@Test(dataProvider = "Users_PARMU",groups = { "regression" }, priority = 3)
		public void ValidateTally_ViewInDocView(String username, String password, String role) throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56212");
			bc.stepInfo("To verify Admin/RMU will be able to view tallied documents in a doc view(Tally)");
			SoftAssert softAssertion = new SoftAssert();
			lp.loginToSightLine(username, password);
			String saveSearchName = null;
			String hitsCountexpected=null;
			bc.stepInfo("Logged in as -" + role);
			if (role == "RMU") {
				saveSearchName = saveSearchNameRMU;
				hitsCountexpected=hitsCountRMU;
				
			}
			if (role == "PA") {
				saveSearchName = saveSearchNamePA;
				hitsCountexpected=hitsCountPA;
			}
			TallyPage tp = new TallyPage(driver);
			tp.navigateTo_Tallypage();
			tp.SelectSource_SavedSearch(saveSearchName);
			tp.selectTallyByMetaDataField(Input.metaDataName);
			driver.waitForPageToBeReady();
			tp.tallyActions();
			tp.Tally_ViewInDocView();
			bc.stepInfo("Tally Report  generated for selected search.");
			DocViewPage dc = new DocViewPage(driver);
			String ActualCount = dc.verifyDocCountDisplayed_DocView();
			System.out.println(ActualCount);
			softAssertion.assertEquals(hitsCountexpected, ActualCount);
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
			for(int i=0;i<metadata.length;i++) {
				bc.stepInfo("**To Verify Tally Report if Tally By MetaData as-"+metadata[i]+"**");
			tp.navigateTo_Tallypage();
			tp.SelectSource_Assignment(assignmentName);
			tp.selectTallyByMetaDataField(metadata[i]);
			tp.validateMetaDataFieldName(metadata[i]);
			tp.verifyTallyChart();
			bc.passedStep("Verified whether RMU will have a report in Tally with document counts by metadata "
					+ "fields for assignment groups if Tally by metadata is "+metadata[i]);
			if(i!=3) {bc.selectproject();}
			}
		}
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
		}
		try {
			lp.logout();
			LoginPage.clearBrowserCache();
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
		try {
		//	Input in = new Input();
		//	in.loadEnvConfig();
			driver = new Driver();
			lp = new LoginPage(driver);
			SavedSearch savedSearch = new SavedSearch(driver);
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			savedSearch.SaveSearchDelete(saveSearchNamePA);
			lp.logout();
			LoginPage.clearBrowserCache();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

	}
}
