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

public class DocList_Regression2 {

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
	 * @author Vijaya.Rani ModifyDate:11/07/2022 RPMXCON-54963
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that email metadata should present with single quote on DocList.
	 */
	@Test(description = "RPMXCON-54963", enabled = true, groups = { "regression" })
	public void verifyEmailMetaDataPresentWithSingleQuote() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54963");
		baseClass.stepInfo(
				"Verify that email metadata should present with single quote on DocList.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String[] emailValue = { "EmailAuthorNameAndAddress", "EmailAuthorName", "EmailAuthorAddress" };
		String[] emailToValue = { "EmailToNamesAndAddresses", "EmailToNames", "EmailToAddresses" };
		String[] emailCCValue = { "EmailCCNamesAndAddresses", "EmailCCNames", "EmailCCAddresses" };
		String[] emailBCCValue = { "EmailBCCNamesAndAddresses", "EmailBCCNames", "EmailBCCAddresses" };
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
	
		baseClass.stepInfo("Select colunm In doclist EmailAuthorNames and EmailAuthorAddress");
		docList.SelectColumnDisplayByRemovingAddNewValues(emailValue);
		
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Select colunm In doclist EmailToNames and EmailToAddress");
		docList.SelectColumnDisplayByRemovingAddNewValues(emailToValue);
		
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Select colunm In doclist EmailCCNames and EmailCCAddress");
		docList.SelectColumnDisplayByRemovingAddNewValues(emailCCValue);
		
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Select colunm In doclist EmailBCCNames and EmailBCCAddress");
		docList.SelectColumnDisplayByRemovingAddNewValues(emailBCCValue);
		
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:11/07/2022 RPMXCON-54534
	 * @throws Exception
	 * @Description Validate onpage filter for EmailAuthorDomains And DocFileType on DocList page.
	 */
	@Test(description = "RPMXCON-54534", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterEamilAuthorNameAndDocFileTypeInDocList(String username, String password, String role) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54534");
		baseClass.stepInfo(
				"Validate onpage filter for EmailAuthorDomains And DocFileType on DocList page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String emailAuthor="EmailAuthorDomain";
		String docFileType="DocFileType";
		
		//Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
		
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		
		baseClass.stepInfo("verify DocList EmailAuthorDomain Include and DocFileType Exclude Filter");
		docList.emailValueFirstIncludeAndEmailValueSecondExcludeVerifyInDocList(emailAuthor,docFileType);
		
		// Clear Applied filter
		baseClass.stepInfo("Clear the Applied Filters");
		docList.clearAllAppliedFilters();
		
		baseClass.stepInfo("verify DocList DocFileType Include  and EmailAuthorDomain Exclude Filter");
		docList.emailValueFirstExcludeAndEmailValueSecondIncludeVerifyInDocList(docFileType,emailAuthor);
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
