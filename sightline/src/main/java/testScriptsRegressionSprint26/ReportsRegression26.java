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
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
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
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password } };
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
	
}

