package testScriptsRegression;

import static org.testng.Assert.fail;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.ReportsPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagCountbyTagReport;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Reports_Regression1 {

	ReportsPage reportPage;
	Driver driver;
	LoginPage loginPage;
	SessionSearch search;
	SoftAssert softAssertion;
	SavedSearch savedSearch;
	TagsAndFoldersPage tagPage;
	BaseClass baseClass;
	CommunicationExplorerPage comExpPage;
    TagCountbyTagReport tagCounts;
    DocListPage DLPage;
    String TagName2="ASTag"+Utility.dynamicNameAppender();
	String TagName1="ASTag"+Utility.dynamicNameAppender();
	String TagName3="ASTag"+Utility.dynamicNameAppender();
	int pureHit;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		Input in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		search = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		tagPage = new TagsAndFoldersPage(driver);
		reportPage = new ReportsPage(driver);
		softAssertion = new SoftAssert();
		comExpPage=new CommunicationExplorerPage(driver);
		 tagCounts = new TagCountbyTagReport(driver);
		  DLPage = new DocListPage(driver);
	}

	@DataProvider(name = "UsersWithSA")
	public Object[][] Reports_Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" }, { Input.sa1userName, Input.sa1password, "SA" },
				{ Input.rev1userName, Input.rev1password, "Reviewer" } };
		return users;
	}

	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description To verify that Tagscount by Tags report option is displayed on
	 *              Report menu.
	 */
	@Test(dataProvider = "UsersWithSA", groups = { "regression" }, priority = 1, enabled = false)
	public void VerifyTagCountByTagDisplay(String username, String password, String role) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-56230");
		baseClass.stepInfo("To verify that Tagscount by Tags report option is displayed on Report menu.");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as -" + role);
		if (role == "SA" || role == "Reviewer") {
			if (reportPage.getReportBTN().isElementPresent()) {
				softAssertion.fail();
				baseClass.failedStep(
						"After Logging in as " + role + " Report button in menu is displayed which is not expected.");

			} else {
				softAssertion.assertTrue(true);
				baseClass.passedStep(
						"After Logging in as " + role + " Report Button in menu is not displayed which is  expected.");
			}
		}
		if (role == "PA" || role == "RMU") {
			this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
			driver.waitForPageToBeReady();
			if (reportPage.getTagCountByTagBtn().isElementPresent()) {
				softAssertion.assertTrue(true);
				baseClass.passedStep(
						"After Logging in as " + role + "Tagscount by Tags report option is displayed on Report menu which is  expected.");

			} else {
				softAssertion.fail();
				baseClass.failedStep("After Logging in as " + role + " Tagscount by Tags report option is not displayed on Report menu.");
			}
		}
		loginPage.logout();
	}

	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description To verify that in Tags Type selection criteria all types of Tags
	 *              are displayed.
	 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 2, enabled = true)
	public void VerifyTagTypeDisplay(String username, String password, String role) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-56232");
		baseClass.stepInfo("To verify that in Tags Type selection criteria all types of Tags are displayed..");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as -" + role);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(reportPage.getTagCountByTagBtn());
		reportPage.getTagCountByTagBtn().waitAndClick(10);
		reportPage.getTagTypesExpandBtn().waitAndClick(10);
		try {
			String Tagtypes[][] = { { "Technical Issue" }, { "Private Data" }, { "Privileged" }, { "Responsive" } };

			for (int i = 0; i < Tagtypes.length; i++) {
				int j = 0;
				String tagNames = Tagtypes[i][j];
				baseClass.ValidateElement_Presence(reportPage.TagTypeCheckBox(tagNames), tagNames);
			}
			baseClass.stepInfo(
					"Sucessfully verified that in Tags Type selection criteria  whether all types of Tags are displayed.");
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep("Exception occured,Failed to verify Tag Types display. " + e.getMessage());
		}
		loginPage.logout();

	}
	
	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description To verify that Users are able to Save  Communication Map report.
	 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 3, enabled = true)
	public void  VerifySaveFunctionality_ComMapExp(String username, String password, String role) throws InterruptedException {
		String reportName = "IndiumReport" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-56415");
		baseClass.stepInfo("To verify that Users are able to Save  Communication Map report.");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as -" + role);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		baseClass.stepInfo("Report Generated.");
		comExpPage.saveReport(reportName);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		reportPage.verifyCustomReportDisplaydetails(reportName);
		reportPage.deleteCustomReport(reportName);	
		loginPage.logout();
	}
	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description Validate list for Tags in on page filter on Communication Explorer report
	 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority =4 , enabled = true)
	public void  VerifyFiltersFunctionality_ComMapExp(String username, String password, String role) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56918");
		baseClass.stepInfo("Validate list for Tags in on page filter on Communication Explorer report");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as -" + role);
		String TagName="ASTag"+Utility.dynamicNameAppender();
		tagPage.CreateTagwithClassification(TagName, Input.tagNamePrev);
		baseClass.stepInfo("Created a Tag with name--"+TagName);
		baseClass.selectproject();
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		baseClass.stepInfo("Report Generated.");
		comExpPage.verifyFilters("Tags",true,TagName);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagPage.deleteAllTags(TagName);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		baseClass.stepInfo("Report Generated.");
		comExpPage.verifyFilters("Tags",false,TagName);
		loginPage.logout();
	}
	
	
	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @throws Exception 
	 * @description To verify that User must be able export the Tag count by Tag report in excel format
	 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 6, enabled = true)
	public void VerifyFileDownloadInTagCountsReport(String username, String password, String role) throws InterruptedException, Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56245");
		baseClass.stepInfo("To verify that User must be able export the Tag count by Tag report in excel format");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as -" + role);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		String Filename1=baseClass.GetLastModifiedFileName();
		baseClass.stepInfo(Filename1+"Last Modified File name before Downloading the report");
		tagCounts.GenerateTagCountreport("All Tags", true);	
		Thread.sleep(10000);  //wait time added to wait  upto file gets downloaded and updated in the folder.
		String Filename2=baseClass.GetLastModifiedFileName();
		baseClass.stepInfo(Filename2+"Last Modified File name after Downloading the report");
		if((Filename2!=Filename1)) {
			String actualfileName=FilenameUtils.getBaseName(Filename2);
			String fileFormat=FilenameUtils.getExtension(Filename2);
	        String expectedFormat="xlsx";
	        baseClass.stepInfo("Downloaded file name is"+actualfileName); 
	        baseClass.stepInfo("Downloaded fileFormat is"+fileFormat);
	        Assert.assertEquals(fileFormat, expectedFormat);
	        baseClass.passedStep("Sucessfully verified whether the File Exported in excel format");
		}
		else {
			baseClass.failedStep("File not downloaded");
		}
		loginPage.logout();

	}
	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description Validate on Page filter by Tag on Communication Explorer
	 * (same documents are part of multiple tags)
	 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority =5 , enabled = true)
	public void  VerifyIncludeFiltersFunctionality_ComMapExp(String username, String password, String role) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56917");
		baseClass.stepInfo("Validate on Page filter by Tag on Communication Explorer"
				+ "(same documents are part of multiple tags)");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as -" + role);
		if(role=="RMU") {
		String MetaSearch="Gouri";
		tagPage.CreateTagwithClassification(TagName1, Input.tagNamePrev);
		search.MetaDataSearchInAdvancedSearch( Input.MetaDataEAName,MetaSearch);
	    search.bulkTagExisting(TagName1);
		baseClass.stepInfo("Created a Tag with name--"+TagName1);
		baseClass.selectproject();
		tagPage.CreateTagwithClassification(TagName2, Input.tagNamePrev);
		search.MetaDataSearchInAdvancedSearch(Input.MetaDataEAName,MetaSearch);
		baseClass.stepInfo("Created a Tag with name--"+TagName2);
		 search.bulkTagExisting(TagName2);
		}
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		baseClass.stepInfo("Report Generated.");
		comExpPage.include(TagName1, "Tags",TagName2, true);
		comExpPage.getCommunicationExplorer_ApplyBtn().waitAndClick(10);
		baseClass.waitTime(3);
		comExpPage.clickReport();
		comExpPage.viewinDoclist();
		DLPage.SelectColumnDisplay(	DLPage.getSelectEmailAuthorName());
		DLPage.emailAuthorNameVerificationWithOutFilter(Input.EmailAuthourName,true);
		baseClass.selectproject();
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		baseClass.stepInfo("Report Generated.");
		comExpPage.include(TagName1,"Tags",TagName2, false);
		comExpPage.getCommunicationExplorer_ApplyBtn().Click();
		baseClass.waitTime(3);
		comExpPage.clickReport();
		comExpPage.viewinDoclist();
		DLPage.SelectColumnDisplay(	DLPage.getSelectEmailAuthorName());
		DLPage.emailAuthorNameVerificationWithOutFilter(Input.EmailAuthourName,false);
		if(role=="PA") {
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagPage.deleteAllTags("ASTag");
		loginPage.logout();
		}
	}
	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @descriptionValidate on page tag filter for Communication Explorer(few documents are not part of any Tag)
	 */
	//@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 7, enabled = true)
	public void  VerifyFilters_ComMapExp(String username, String password, String role) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56916");
		baseClass.stepInfo("Validate on page tag filter for Communication Explorer(few documents are not part of any Tag)");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as -" + role);
		if(role=="RMU") {
			tagPage.CreateTagwithClassification(TagName3, Input.tagNamePrev);
		search.MetaDataSearchInAdvancedSearch(Input.MetaDataReciepientsDomain,Input.MetaDataDomainName);
		baseClass.stepInfo("Completed Metadata search using EmailRecipientDomains as MetaData");
		 pureHit=Integer.parseInt(search.verifyPureHitsCount());
		    search.bulkTagExisting(TagName3);
		baseClass.stepInfo("Created a Tag with name--"+TagName3);
		}
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		baseClass.stepInfo("Report Generated.");
		Thread.sleep(10000);
		int DocCount1=Integer.parseInt(comExpPage.VerifyTaggedDocsCountDisplay());
		System.out.println(DocCount1);
		comExpPage.include(TagName3,"Tags",null,true);
		comExpPage.getCommunicationExplorer_ApplyBtn().Click();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Report Generated after including the tag "+TagName3);
		Thread.sleep(10000);
		int DocCount2=Integer.parseInt(comExpPage.VerifyTaggedDocsCountDisplay());
		System.out.println(DocCount2);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		comExpPage.include(TagName3,"Tags",null,false);
		comExpPage.getCommunicationExplorer_ApplyBtn().Click();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Report Generated after Excluding the tag "+TagName3);
		Thread.sleep(10000);
		int DocCount3=Integer.parseInt(comExpPage.VerifyTaggedDocsCountDisplay());
		System.out.println( DocCount3);
		try {
		int ExpectedDocCount_Include=pureHit;
		int ExpectedDocCount_Exclude=DocCount1-pureHit;
		 softAssertion.assertEquals(ExpectedDocCount_Include,DocCount2);
		 softAssertion.assertEquals(ExpectedDocCount_Exclude,DocCount3);		
		 softAssertion.assertAll();  
		 baseClass.passedStep("Result set  filter the documents  as expected when we  exclude/include Tags in report generation"
		 		+ "part of the selected Tags. ");  
		 }
		catch(Exception e) {
			e.getStackTrace();
			baseClass.failedStep(e.getMessage());
		}
		loginPage.logout();
	
	}
	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description To verify that Users are able to  View In Doc View 
	 * option is displayed in Action dropdown in Comm Map
	 */
	//@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority =8 , enabled = true)
	public void  VerifyViewInDocView(String username, String password, String role) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56414");
		baseClass.stepInfo("To verify that Users are able to  View In Doc View option is displayed in"
				+ " Action dropdown in Comm Map");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as -" + role);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		baseClass.stepInfo("Report Generated.");
		comExpPage.clickReport();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(comExpPage.getMailCountOFSelectedReport());
		baseClass.waitTime(5);
		String DocCountInreportsPage=comExpPage.getMailCountOFSelectedReport().GetAttribute("innerText");
		baseClass.stepInfo(DocCountInreportsPage+"  Doc Count in report page that is selected to view in doc view.");
		comExpPage.viewinDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(comExpPage.metaDataslist_reviewerPg());
		int x =comExpPage.metaDataslist_reviewerPg().FindWebElements().size();
		System.out.println(x);
		String count=String.valueOf((comExpPage.metaDataslist_reviewerPg().FindWebElements().size()));
		baseClass.stepInfo(count+"  Doc Count in Doc view page that is selected to view in doc view.");
		System.out.println(DocCountInreportsPage);
		System.out.println(count);
		if(DocCountInreportsPage.contentEquals(count)) {
			softAssertion.assertTrue(true);
			baseClass.passedStep("All selected documents are displayed in Doc View");
		}else {
			softAssertion.assertFalse(false);
			baseClass.failedStep("All selected documents are not displayed in Doc View");
		}
		loginPage.logout();
	}
	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description To verify that on selecting TagCount by Tags Report option, 
	 * Tag counts By Tag page is displayed.
	 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 9, enabled = true)
	public void VerifyTagReportsPAgeDisplay(String username, String password, String role) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48780");
		baseClass.stepInfo("To verify that on selecting TagCount by Tags Report option, Tag counts By Tag page is displayed.");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as -" + role);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(reportPage.getTagCountByTagBtn());
		reportPage.getTagCountByTagBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		String ExpectedUrl=driver.getUrl();
				try {
		softAssertion.assertEquals(ExpectedUrl,Input.url+"Review/TagcountsByTagReport");
		baseClass.ValidateElement_Presence(tagCounts.getTagTypes(),"Tag Types Selection");
		baseClass.ValidateElement_Presence(tagCounts.getTagGroups(),"Tag Groups Selection");
		softAssertion.assertAll();
		baseClass.passedStep("Tag counts By Tag page is displayed with selection "
				+ "criteria as Tags Types and Tags Group.");
		}
				catch(Exception e){
					baseClass.stepInfo(e.getMessage());
					baseClass.passedStep("Tag counts By Tag page is not displayed with selection "
							+ "criteria as Tags Types and Tags Group.");
				}
				loginPage.logout();				
	}
	
/**
 * @author Jayanthi.ganesan
 * @param username
 * @param password
 * @param role
 * @throws InterruptedException
 * @throws ParseException
 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 10, enabled = true)
	public void  VerifyResetFunctionality_TagCountsReport(String username, String password, String role) throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-56234");
		baseClass.stepInfo("To Verify that on clicking RESET link current selected criteria are set to default selected criteria");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as -" + role);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		tagCounts.ValidateResetFunctionality();
		softAssertion.assertAll();	
		loginPage.logout();
	}

	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void verifyDocCount_ABM() throws Exception {
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String SaveSaerchName ="ABMSaveSearch" + UtilityLog.dynamicNameAppender();
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		DocViewPage docViewPage = new DocViewPage(driver);
		ReusableDocViewPage reusableDocViewPage = new ReusableDocViewPage(driver);
		String assignmentName1 = "assgnment" + Utility.dynamicNameAppender();

		UtilityLog.info("Logged in as RMU User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case Id:RPMXCON-48789");
		baseClass.stepInfo("Validate data on Advanced batch management report when Domain and Project"
				+ " Admin associated to Assignments of a project");

		// Basic Search and Bulk Assign
		sessionsearch.basicContentSearch(Input.testData1);
		baseClass.stepInfo("Search for text input completed");
		String Hits = sessionsearch.verifyPureHitsCount();
		sessionsearch.saveSearch(SaveSaerchName);
		sessionsearch.bulkAssign();

		// create Assignment and disturbute docs
		assignmentsPage.assignmentCreation(assignmentName1, Input.codeFormName);
		assignmentsPage.add4ReviewerAndDistribute();
		baseClass.stepInfo(assignmentName1 + "  Assignment Created and distributed to DA/PA/RMU/Rev");
		baseClass.impersonateRMUtoReviewer();
		docViewPage.selectAssignmentfromDashborad(assignmentName1);
		baseClass.stepInfo("Doc is viewed in the docView Successfully fro RMU User.");
		reusableDocViewPage.editTextBoxInCodingFormWithCompleteButton("Completing and editing");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoReviewer();
		docViewPage.selectAssignmentfromDashborad(assignmentName1);
		baseClass.stepInfo("Doc is viewed in the docView Successfully for PA user");
		reusableDocViewPage.editTextBoxInCodingFormWithCompleteButton("Completing and editing");
		loginPage.logout();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.impersonateDAtoReviewer();
		docViewPage.selectAssignmentfromDashborad(assignmentName1);
		baseClass.stepInfo("Doc is viewed in the docView Successfully for DA user.");
		reusableDocViewPage.editTextBoxInCodingFormWithCompleteButton("Completing and editing");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		ABMReportPage AbmReportPage = new ABMReportPage(driver);
		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSaerchName, assignmentName1, false, true);
		int ToDoDocs = AbmReportPage.validateReport(AbmReportPage.getListToDo_Text(assignmentName1));
		int completedDocs = AbmReportPage.validateReport(AbmReportPage.getListCompletedDocs_Text(assignmentName1));
		int ActualTotalDocs = ToDoDocs + completedDocs;
		softAssertion.assertEquals(Integer.parseInt(Hits), ActualTotalDocs);
		softAssertion.assertEquals(3, completedDocs);
		softAssertion.assertAll();
		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSaerchName, assignmentName1, true, true);
		baseClass.passedStep("Sucessfully Validated data on Advanced batch management report when Domain and Project Admin associated to Assignments of a project");
		loginPage.logout();
	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" }};
		return users;
	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password },{ Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
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
			//LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

}
