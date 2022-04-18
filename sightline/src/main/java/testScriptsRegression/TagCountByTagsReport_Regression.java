package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SessionSearch;
import pageFactory.TagCountbyTagReport;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TagCountByTagsReport_Regression {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	SoftAssert softAssertion;
	BaseClass bc;
	ReportsPage reportPage;
	TagCountbyTagReport tagCounts ;
	String hitsCount;
	String hitsCount1;
	TagCountbyTagReport tagCountPage;
	String tagName = "AAtag" + Utility.dynamicNameAppender();
	String tagName1 = "AAtag" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

		// Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		// Login as a RMU
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		search = new SessionSearch(driver);
		// Bulk Tag
		search.basicContentSearch(Input.testData1);
		hitsCount = search.verifyPureHitsCount();
        search.bulkTag(tagName);
	    bc.selectproject();
		// Bulk Tag
		search.basicContentSearch(Input.searchString5);
		hitsCount1 = search.verifyPureHitsCount();
	    search.bulkTag(tagName1);
		lp.logout();
		lp.quitBrowser();
	}
	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description To verify that on selecting TagCount by Tags Report option, 
	 * Tag counts By Tag page is displayed.
	 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 1, enabled = true)
	public void VerifyTagReportsPAgeDisplay(String username, String password, String role) throws InterruptedException {

		bc.stepInfo("Test case Id: RPMXCON-48780");
		bc.stepInfo("To verify that on selecting TagCount by Tags Report option, Tag counts By Tag page is displayed.");
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		TagCountbyTagReport tagCountPage=new TagCountbyTagReport(driver);
		tagCountPage.navigateToReportPage();
		bc.waitForElement(reportPage.getTagCountByTagBtn());
		reportPage.getTagCountByTagBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		String ExpectedUrl=driver.getUrl();
				try {
		softAssertion.assertEquals(ExpectedUrl,Input.url+"Review/TagcountsByTagReport");
		bc.ValidateElement_Presence(tagCounts.getTagTypes(),"Tag Types Selection");
		bc.ValidateElement_Presence(tagCounts.getTagGroups(),"Tag Groups Selection");
		softAssertion.assertAll();
		bc.passedStep("Tag counts By Tag page is displayed with selection "
				+ "criteria as Tags Types and Tags Group.");
		}
				catch(Exception e){
					bc.stepInfo(e.getMessage());
					bc.failedStep("Tag counts By Tag page is not displayed with selection "
							+ "criteria as Tags Types and Tags Group.");
				}
				lp.logout();				
	}
	 @Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 2)
	public void verifyTagCountReport(String username, String password, String role)
			throws InterruptedException, AWTException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-56235");
		bc.stepInfo("To verify that User can view Tags Count by Tag Report by Tagwise.");
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		java.util.List<String> elementNames = new ArrayList<String>();
		ArrayList<String> tagNamesExpectedList = new ArrayList<String>(Arrays.asList(tagName,tagName1));
		tagCountPage=new TagCountbyTagReport(driver);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		tagCountPage.GenerateTagCountreport_Multipletags(tagName, tagName1);
		elementNames=tagCountPage.getTagNamesFromReport();
		if(elementNames.containsAll(tagNamesExpectedList)) {
			softAssertion.assertTrue(true);
			softAssertion.assertTrue(tagCountPage.verifyTagDocCount(tagName,hitsCount,1));
			softAssertion.assertTrue(tagCountPage.verifyTagDocCount(tagName1,hitsCount1,1));
			
		}else {
			softAssertion.assertTrue(false);
		}
		softAssertion.assertAll();
		bc.passedStep("Report generated and  Tagged Doc Count displayed as per the Tags");
		
	}
		/**
		 * @author Jayanthi
		 * @throws InterruptedException
		 * @description To verify that Tagscount by Tags report option is displayed on
		 *              Report menu.
		 */
		@Test(dataProvider = "UsersWithSA", groups = { "regression" }, priority = 3, enabled = true)
		public void VerifyTagCountByTagDisplay(String username, String password, String role) throws InterruptedException {

			bc.stepInfo("Test case Id: RPMXCON-56230");
			bc.stepInfo("To verify that Tagscount by Tags report option is displayed on Report menu.");
			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as -" + role);
			
			if (role == "SA" || role == "Reviewer") {
				if (reportPage.getReportBTN().isElementAvailable(1)) {
					bc.failedStep(
							"After Logging in as " + role + " Report button in menu is displayed which is not expected.");

				} else {
					bc.passedStep(
							"After Logging in as " + role + " Report Button in menu is not displayed which is  expected.");
				}
			}
			if (role == "PA" || role == "RMU") {
				tagCounts.navigateToReportPage();
				driver.waitForPageToBeReady();
				if (reportPage.getTagCountByTagBtn().isElementPresent()) {
					bc.passedStep(
							"After Logging in as " + role + "Tagscount by Tags report option is displayed on Report menu which is  expected.");

				} else {
					bc.failedStep("After Logging in as " + role + " Tagscount by Tags report option is not displayed on Report menu.");
				}
			}
			lp.logout();
		}

		/**
		 * @author Jayanthi
		 * @throws InterruptedException
		 * @description To verify that in Tags Type selection criteria all types of Tags
		 *              are displayed.
		 */
		@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 4, enabled = true)
		public void VerifyTagTypeDisplay(String username, String password, String role) throws InterruptedException {

			bc.stepInfo("Test case Id: RPMXCON-56232");
			bc.stepInfo("To verify that in Tags Type selection criteria all types of Tags are displayed..");
			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as -" + role);
			this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
			driver.waitForPageToBeReady();
			ReportsPage reportPage=new ReportsPage(driver);
			bc.waitForElement(reportPage.getTagCountByTagBtn());
			reportPage.getTagCountByTagBtn().waitAndClick(10);
			reportPage.getTagTypesExpandBtn().waitAndClick(10);
			try {
				String Tagtypes[][] = { { "Technical Issue" }, { "Private Data" }, { "Privileged" }, { "Responsive" } };

				for (int i = 0; i < Tagtypes.length; i++) {
					int j = 0;
					String tagNames = Tagtypes[i][j];
					bc.ValidateElement_Presence(reportPage.TagTypeCheckBox(tagNames), tagNames);
				}
				bc.stepInfo(
						"Sucessfully verified that in Tags Type selection criteria  whether all types of Tags are displayed.");
			} catch (Exception e) {
				e.printStackTrace();
				bc.failedStep("Exception occured,Failed to verify Tag Types display. " + e.getMessage());
			}
			lp.logout();

		}
		
		
	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 * @throws ParseException
	 */
		@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 5, enabled = true)
		public void  VerifyResetFunctionality_TagCountsReport(String username, String password, String role) throws InterruptedException, ParseException {
			bc.stepInfo("Test case Id: RPMXCON-56234");
			bc.stepInfo("To Verify that on clicking RESET link current selected criteria are set to default selected criteria");
			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as -" + role);
			tagCounts.navigateToReportPage();
			driver.waitForPageToBeReady();
			tagCounts.ValidateResetFunctionality();
			softAssertion.assertAll();	
			lp.logout();
		}

		/**
		 * @author Jayanthi
		 * @throws InterruptedException
		 * @throws Exception 
		 * @description To verify that User must be able export the Tag count by Tag report in excel format
		 */
		@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 6, enabled = true)
		public void VerifyFileDownloadInTagCountsReport(String username, String password, String role) throws InterruptedException, Exception {

			bc.stepInfo("Test case Id: RPMXCON-56245");
			bc.stepInfo("To verify that User must be able export the Tag count by Tag report in excel format");
			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as -" + role);
			driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
			driver.waitForPageToBeReady();
			String Filename1=bc.GetLastModifiedFileName();
			bc.stepInfo(Filename1+"Last Modified File name before Downloading the report");
			tagCounts.GenerateTagCountreport("All Tags", true);	
			Thread.sleep(10000);  //wait time added to wait  upto file gets downloaded and updated in the folder.
			String Filename2=bc.GetLastModifiedFileName();
			bc.stepInfo(Filename2+"Last Modified File name after Downloading the report");
			if((Filename2!=Filename1)) {
				String actualfileName=FilenameUtils.getBaseName(Filename2);
				String fileFormat=FilenameUtils.getExtension(Filename2);
		        String expectedFormat="xlsx";
		        bc.stepInfo("Downloaded file name is"+actualfileName); 
		        bc.stepInfo("Downloaded fileFormat is"+fileFormat);
		        Assert.assertEquals(fileFormat, expectedFormat);
		        bc.passedStep("Sucessfully verified whether the File Exported in excel format");
			}
			else {
				bc.failedStep("File not downloaded");
			}
			lp.logout();

		}
	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		softAssertion = new SoftAssert();
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		reportPage = new ReportsPage(driver);
	    tagCounts = new TagCountbyTagReport(driver);
	}

	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}
	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}
	@DataProvider(name = "UsersWithSA")
	public Object[][] Reports_Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" }, { Input.sa1userName, Input.sa1password, "SA" },
				{ Input.rev1userName, Input.rev1password, "Reviewer" } };
		return users;
	}
	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			Input in = new Input();
			in.loadEnvConfig();
			driver = new Driver();
			lp = new LoginPage(driver);
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tp.deleteAllTags("TCRtag");
			lp.logout();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

	}
}
