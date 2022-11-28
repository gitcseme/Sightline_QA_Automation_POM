package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.ReviewerCountsReportPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.TagCountbyTagReport;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TimelineReportPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ReportsRegression26 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	ReportsPage reports;
	ConceptExplorerPage conceptExplorer;
	CommunicationExplorerPage communicationExpPage;
	SearchTermReportPage searchterm;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		in = new Input();
		in.loadEnvConfig();
	}
	

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();

		sessionSearch = new SessionSearch(driver);
		reports = new ReportsPage(driver);
		conceptExplorer = new ConceptExplorerPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
		savedSearch = new SavedSearch(driver);
		communicationExpPage = new CommunicationExplorerPage(driver);
		searchterm = new SearchTermReportPage(driver);
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
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed Communication explorer sprint 21**");
	}
	

	@DataProvider(name = "PA & RMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }
		 ,{ Input.rmu1userName, Input.rmu1password }
		};
		return users;
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-56244
	 * @Description:verify that Project Admin can view all Tag Group and Tags irrespective of Security Group.
	 **/
	@Test(description = "RPMXCON-56244", enabled = true, groups = { "regression" })
	public void verifyProjectAdminViewAllTagGrpsandTags() throws Exception {
		List<String> expectedTags = null;
		List<String> actualTags = null;
	
		baseClass.stepInfo("RPMXCON - 56244");
		baseClass.stepInfo("To verify that Project Admin can view all Tag Group and Tags irrespective of Security Group.");
		loginPage.loginToSightLine(Input.pa1userName,  Input.pa1password);
		baseClass.stepInfo("Logged in As  : " + Input.pa1userName);
		
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		tags.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tags.expandAllClosedArrow();
		baseClass.waitForElementCollection(tags.getAvailableTagList());
		expectedTags = baseClass.getAvailableListofElements(tags.getAvailableTagList());
		
		TagCountbyTagReport tagReport = new TagCountbyTagReport(driver);
		tagReport.navigateToTagCountByTagReportPage();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(tagReport.getTags_Expandbutton());
		tagReport.getTags_Expandbutton().waitAndClick(5);
		
		baseClass.waitForElementCollection(tagReport.getTags());
		actualTags = baseClass.getAvailableListofElements(tagReport.getTags());
		System.out.println(actualTags);
		System.out.println(expectedTags);
		
		baseClass.listCompareEquals(expectedTags, actualTags, "All security group Tag Groups and Tags are displayed in Tree as Expected", "All security group Tag Groups and Tags are displayed in Tree As Not Expected");	
		baseClass.passedStep("Verified -that Project Admin can view all Tag Group and Tags irrespective of Security Group.");
		loginPage.logout();
		
	}

	/**
	 * @author NA Testcase No:RPMXCON-56303
	 * @Description:verify that User is able to can remove the selected multiple options in Source Criteria.
	 **/
	@Test(description = "RPMXCON-56303", dataProvider = "PA & RMU", enabled = true, groups = { "regression" })
	public void verifyUserABletoRemoveSrcCrit(String username, String password) throws Exception {
		TagsAndFoldersPage tagFolder = new TagsAndFoldersPage(driver);
		TimelineReportPage timeLine = new TimelineReportPage(driver);
		
		String tagName1 = "Tag" + Utility.dynamicNameAppender();
		String tagName2 = "Tag" + Utility.dynamicNameAppender();
		String tagName3 = "Tag" + Utility.dynamicNameAppender();
		String [] tags = {tagName1, tagName2, tagName3};
		
		baseClass.stepInfo("RPMXCON - 56303");
		baseClass.stepInfo("To verify that User is able to can remove the selected multiple options in Source Criteria.");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in As : " + username);
		
		tagFolder.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		
		if(username.equals(Input.rmu1userName)) {
			tagFolder.createNewTagwithClassificationInRMU(tagName1, Input.tagNamePrev);
			tagFolder.createNewTagwithClassificationInRMU(tagName2, Input.tagNamePrev);
			tagFolder.createNewTagwithClassificationInRMU(tagName3, Input.tagNamePrev);
		} else {
		    tagFolder.createNewTagwithClassification(tagName1, Input.tagNamePrev);
		    tagFolder.createNewTagwithClassification(tagName2, Input.tagNamePrev);
		    tagFolder.createNewTagwithClassification(tagName3, Input.tagNamePrev);
		}
		
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		timeLine.navigateToTimelineAndGapsReport();
		driver.waitForPageToBeReady();
		timeLine.selectTagsinSource(tags);
		timeLine.verifySelctedOptnsInSourceCriteria(tags);
		timeLine.RemoveAndVerifyOptionFrmSrcCriteria(tags);
		baseClass.passedStep("Verified -  that User is able to can remove the selected multiple options in Source Criteria.");
		loginPage.logout();
		
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-56298
	 * @Description:verify that Timeline and Gaps Report option is displayed on Report menu
	 **/
	@Test(description = "RPMXCON-56298",dataProvider = "PA & RMU", enabled = true, groups = { "regression" })
	public void verifyTimeLineReportDisplayRepMenu(String username, String password) throws Exception {
		TimelineReportPage timeLineGaps = new TimelineReportPage(driver);

		String timeLine = "MasterDate";
		String fromDate =  "2019/01/01";
		String toDate = timeLineGaps.getCurrentDate();
		String reportName = "Report" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON - 56298");
		baseClass.stepInfo("To verify that Timeline and Gaps Report option is displayed on Report menu.");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged In As : " + username);

		timeLineGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		baseClass.waitForElement(timeLineGaps.selectChart());
		timeLineGaps.selectBarChartandRtnDocCount("yearly");
		timeLineGaps.SaveReport(reportName);
		
		ReportsPage report = new ReportsPage(driver);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		
		if(report.getdeleteToolTip_CustomReport(reportName).isElementAvailable(5)) {
			baseClass.passedStep("Timeline and Gaps Report option is displaying in Report Menu.");
		} else {
			baseClass.failedStep("Timeline and Gaps Report option Not displaying in Report Menu.");
		}
		
		baseClass.passedStep("Verified - that Timeline and Gaps Report option is displayed on Report menu.");
		loginPage.logout();
		   
	}

	/**
	 * @Author NA
	 * @Description : verify that Users are able to view the saved Custom Report Using style as CSV and multiple/all Workproduct. [RPMXCON-56404]
	 */
	@Test(description = "RPMXCON-56404", enabled = true, dataProvider = "PA & RMU", groups = { "regression" })
	public void verifySavedCustomReprtCSVandMultipleWP(String username, String password) throws Exception {
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		ReportsPage report =new ReportsPage(driver);
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		
		String tagName1 = "Tag" + Utility.dynamicNameAppender();
		String tagName2 = "Tag" + Utility.dynamicNameAppender();
		String tagName3 = "Tag" + Utility.dynamicNameAppender();	
		String reportName = "Report" + Utility.dynamicNameAppender();
		
		String[] workProductFields = { tagName1, tagName2, tagName3 };
		String expExportStyle = "CSV";
	
		baseClass.stepInfo("RPMXCON-56404");
		baseClass.stepInfo("To verify that Users are able to view the saved Custom Report"
				+ " Using style as CSV and multiple/all Workproduct.");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in As : " + username);
		
		if (username.equals(Input.rmu1userName)) {
			tags.createNewTagwithClassificationInRMU(tagName1, Input.tagNamePrev);
			tags.createNewTagwithClassificationInRMU(tagName2, Input.tagNamePrev);
			tags.createNewTagwithClassificationInRMU(tagName3, Input.tagNamePrev);
		} else {
			tags.createNewTagwithClassification(tagName1, Input.tagNamePrev);
			tags.createNewTagwithClassification(tagName2, Input.tagNamePrev);
			tags.createNewTagwithClassification(tagName3, Input.tagNamePrev);
		}
		
		sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagName1);
		sessionSearch.bulkTagExisting(tagName2);
		sessionSearch.bulkTagExisting(tagName3);

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();
		cddr.selectSource("Security Groups", Input.securityGroup);
		cddr.selectExportStyle("CSV");
		cddr.selectWorkProductFields(workProductFields);
		driver.waitForPageToBeReady();
		
		baseClass.waitForElement(cddr.getRunReport());
		cddr.getRunReport().waitAndClick(10);
		cddr.reportRunSuccessMsg();
		cddr.SaveReport(reportName);
		baseClass.stepInfo("Saved the Custom report " + reportName);
		
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(report.getSavedCustomReport(reportName));
		report.getSavedCustomReport(reportName).waitAndClick(10);
		driver.waitForPageToBeReady();
		cddr.validateSelectedExports(workProductFields);
		baseClass.waitForElement(cddr.getExportStyleDD());
		String actStyle = cddr.getExportStyleDD().selectFromDropdown().getFirstSelectedOption().getText();
		if(actStyle.equals(expExportStyle)) {
			baseClass.passedStep("Style As Expected");
		} else {
			baseClass.failedStep("Style Not As Expected");
		}
		baseClass.passedStep("Verified - that Users are able to view the saved Custom Report"
		 + "Using style as CSV and multiple/all Workproduct.");
		loginPage.logout();
	}
	
	/**
	 * @Author NA
	 * @Description: verify that Reviewer Count report is displayed in Report menu [RPMXCON-56253]
	 */
	@Test(description = "RPMXCON-56253", enabled = true, dataProvider = "PA & RMU", groups = { "regression" })
	public void verifyRevCountReportinReportMenu(String username, String password) throws Exception {
		AssignmentsPage agmt = new AssignmentsPage(driver);
		String assignment = "Assignment" + Utility.dynamicNameAppender();
		String report = "RevCountReport" + Utility.dynamicNameAppender();
		
		baseClass.stepInfo("RPMXCON - 56253");
		baseClass.stepInfo("To verify that Reviewer Count report is displayed in Report menu");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.verifyPureHitsCount();
		sessionSearch.bulkAssign();
		agmt.assignmentCreation(assignment, Input.codeFormName);		
		agmt.editAssignmentUsingPaginationConcept(assignment);
		driver.waitForPageToBeReady();
		agmt.addReviewerAndDistributeDocs();
		loginPage.logout();
		
		loginPage.loginToSightLine(username, password);
		ReviewerCountsReportPage countReport = new ReviewerCountsReportPage(driver);
		countReport.navigateTOReviewerCountsReportPage();
		driver.waitForPageToBeReady();
		countReport.generateReport(assignment);	
		baseClass.waitForElementCollection(countReport.getTableHeaders());
		baseClass.waitForElement(countReport.getSaveReportBtn());
		countReport.getSaveReportBtn().waitAndClick(5);
		baseClass.waitForElement(countReport.getSaveReportName());
		countReport.getSaveReportName().SendKeys(report);
		baseClass.waitForElement(countReport.getSaveBtn());
		countReport.getSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Report save successfully");
		
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(countReport.getCustomReport(report));
		baseClass.mouseHoverOnElement(countReport.getCustomReport(report));
		if(countReport.getCustomReport(report).Visible()) {
			System.out.println("Reviewer Count Report option is displayed in Report Menu As Expected");
		} else {
			System.out.println("Reviewer Count Report option is displayed in Report Menu Not As Expected");
		}
		baseClass.passedStep("Verified - that Reviewer Count report is displayed in Report menu");
		loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-58560
	 * @Description: Timeline and Gaps report: saved Document Data Export Report should work
	 **/
	@Test(description = "RPMXCON-58560",  enabled = true, groups = { "regression" })
	public void verifySavedDocExportRep() throws Exception {
		TimelineReportPage timeGaps = new TimelineReportPage(driver);
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		CustomDocumentDataReport custom = new CustomDocumentDataReport(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		ReportsPage report = new ReportsPage(driver);
		
		String text1 = "039";
		String field1 = "039";
		String text2 = "034";
		String field2 = "034";
		String tagname1 = "Tag1" + Utility.dynamicNameAppender();
		String tagname2 = "Tag2" + Utility.dynamicNameAppender();
		String[] metadata = {"CustodianName"};
		String[] workProduct = {tagname1};
		String[] metadata1 = {"DocID"};
		String[] workProduct1 = {tagname2};
		
		String report1 = "Report1" + Utility.dynamicNameAppender();
		String report2 = "Report2" + Utility.dynamicNameAppender();
		String timeLine = "MasterDate";
		String fromDate =  "2019/01/01";
		String toDate = timeGaps.getCurrentDate();
		
		baseClass.stepInfo("RPMXCON - 58560");
		baseClass.stepInfo("Timeline and Gaps report: saved Document Data Export Report should work");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tags.navigateToTagsAndFolderPage();
		tags.createNewTagwithClassificationInRMU(tagname1, Input.tagNamePrev);
		tags.createNewTagwithClassificationInRMU(tagname2, Input.tagNamePrev);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname1);
		sessionSearch.bulkTagExisting(tagname2);
		
		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions(" Export Data","yearlyActions");
		
		driver.waitForPageToBeReady();
		baseClass.verifyPageNavigation("Report/ExportData");
		custom.selectExportTextFormat(text1);
		custom.selectExportFieldFormat(field1);
		custom.selectMetaDataFields(metadata);
		custom.selectWorkProductFields(workProduct);
		custom.SaveReport(report1);
		custom.validateSelectedExports(metadata);
		custom.validateSelectedExports(workProduct);
		baseClass.waitForElement(custom.getRunReport());
		custom.getRunReport().waitAndClick(5);
		custom.reportRunSuccessMsg();
		
		driver.waitForPageToBeReady();
		custom.selectExportTextFormat(text2);
		custom.selectExportFieldFormat(field2);
		baseClass.waitForElement(custom.getSaveReportBtn());
		custom.getSaveReportBtn().waitAndClick(5);
		baseClass.waitForElement(custom.getSaveReportName());
		custom.getSaveReportName().waitAndClick(5);
		custom.getSaveReportName().SendKeys(report2);
		custom.getSaveBtn().waitAndClick(5);
		custom.validateSelectedExports(metadata);
		custom.validateSelectedExports(workProduct);
		
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(report.getSavedCustomReport(report1));
		report.getSavedCustomReport(report1).waitAndClick(5);
		driver.waitForPageToBeReady();
		custom.selectSources("Security Groups", Input.securityGroup);
		baseClass.waitForElement(custom.getRunReport());
		custom.getRunReport().waitAndClick(5);
		custom.reportRunSuccessMsg();
		
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(report.getSavedCustomReport(report2));
		report.getSavedCustomReport(report2).waitAndClick(5);
		custom.selectSources("Security Groups", Input.securityGroup);
		custom.selectMetaDataFields(metadata1);
		custom.selectWorkProductFields(workProduct1);
		String fileName = custom.runReportandVerifyFileDownloaded();
		String actualValue = custom.csvfileVerification("", fileName);
		baseClass.stepInfo(actualValue);
		System.out.println(actualValue);
		SoftAssert assets = new SoftAssert();
		assets.assertTrue(actualValue.contains(tagname1));
		assets.assertTrue(actualValue.contains(tagname2));
		assets.assertTrue(actualValue.contains("CustodianName"));
		assets.assertTrue(actualValue.contains("DocID"));
		assets.assertAll();
		baseClass.passedStep("Timeline and Gaps report: saved Document Data Export Report should work");
		loginPage.logout();
	}
}

