package sightline.smallComponents;

import java.awt.AWTException;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocList_Regression4 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);

	}
	
	
	/**
	 * @author Vijaya.Rani ModifyDate:12/07/2022 RPMXCON-54532
	 * @throws Exception
	 * @Description Validate onpage filter for CustodianName and MasterDate on DocList page.
	 */
	@Test(description = "RPMXCON-54532", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterCustodianNameAndMasterDateInDocList(String username, String password, String role) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54532");
		baseClass.stepInfo(
				"Validate onpage filter for CustodianName and MasterDate on DocList page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String custodianName="CustodianName";
		
		
		//Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
		
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		
		baseClass.stepInfo("verify DocList CustodianName Include and MasterDate Between Filter");
		docList.emailCustodianNameIncludeAndMasterDate(custodianName);
		
		// Clear Applied filter
		baseClass.stepInfo("Clear the Applied Filters");
		docList.clearAllAppliedFilters();
		
		baseClass.stepInfo("verify DocList MasterDate Before and CustodianName Exclude Filter");
		docList.emailCustodianNameExcludeAndMasterDateBefore(custodianName);
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:12/07/2022 RPMXCON-54533
	 * @throws Exception
	 * @Description Validate onpage filter for EmailRecipientNames and DocFileSize on DocList page.
	 */
	@Test(description = "RPMXCON-54533", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterEmailRecipientNamesAndDocFileSizeInDocList(String username, String password, String role) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54533");
		baseClass.stepInfo(
				"Validate onpage filter for EmailRecipientNames and DocFileSize on DocList page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String emailRecipientNames="EmailRecipientNames";
		
		//Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
		
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		
		baseClass.stepInfo("verify DocList EmailRecipientNames Include and DocFileSize Filter");
		docList.emailRecipientNamesIncludeAndDocFileSize(emailRecipientNames);
		
		// Clear Applied filter
		baseClass.stepInfo("Clear the Applied Filters");
		docList.clearAllAppliedFilters();
		
		baseClass.stepInfo("verify DocList DocFileSize and EmailRecipientNames Include Filter");
		docList.emailRecipientNamesExcludeAndDocFileSize(emailRecipientNames);
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:21/07/2022 RPMXCON-54224
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify that multi value fields should be displayed on Custom column configuration.
	 */
	@Test(description = "RPMXCON-54224", enabled = true, groups = { "regression" })
	public void verifyMutliValueFieldaDisplayCustomColumn() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54224");
		baseClass.stepInfo(
				"To verify that multi value fields should be displayed on Custom column configuration.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String[] emailValue = { "EmailAuthorNameAndAddress", "EmailAuthorName", "EmailAuthorAddress" ,"EmailToNamesAndAddresses", "EmailToNames", "EmailToAddresses" ,"EmailCCNamesAndAddresses", "EmailCCNames", "EmailCCAddresses" };
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
	
		baseClass.stepInfo("Select colunm In doclist EmailAuthorNames and EmailAuthorAddress");
		docList.SelectColumnDisplayByRemovingAddNewValues(emailValue);
		
		baseClass.passedStep("Multi value fields is displayed.user select any multivalued field the in grid it is displayed proper with \";\" delimiter Successfully.");
		
		loginPage.logout();
	}
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}
	
	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } 
				};
		return users;
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}
