package sightline.basicSearch;

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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BasicSearch_Regression27 {

	
	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssertion;
	Input in;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		softAssertion = new SoftAssert();
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:1/12/2022 RPMXCON-57118
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that application displays warning A message on Advanced
	 *              Search if user enters "2009/09/20" without wrapper quotations.
	 */
	@Test(description = "RPMXCON-57118", enabled = true, groups = { "regression" })
	public void verifyApplicationAdvancedSearchDisplayWarningMsg() throws InterruptedException, AWTException {

		base.stepInfo("Test case Id: RPMXCON-57118");
		base.stepInfo(
				"Verify that application displays warning A message on Advanced Search if user enters \"2009/09/20\" without wrapper quotations.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		String searchTerm = "2009-09-20";

		// Login As PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");

		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		base.stepInfo("Go to Advanced Search page enter the Searchterm");
		sessionSearch.getAdvancedSearchLink().Click();
		driver.waitForPageToBeReady();
		sessionSearch.getContentAndMetaDatabtn().Click();
		sessionSearch.getAdvancedContentSearchInput().SendKeys(searchTerm);
		// Click on Search button
		sessionSearch.getQuerySearchButton().waitAndClick(10);
		String actualMsg = sessionSearch.getWarningMsg().getText();
		System.out.println(actualMsg);
		base.stepInfo(actualMsg);
		String expectedMsg = "Your query contains an argument that may be intended to look for dates within any body content or metadata attribute. Please be advised that the search engine interprets dash - or slash / characters in non-fielded arguments as implied OR statement. For example, 1980/01/02 is treated by the search engine as 1980 OR 01 OR 02. The same is true of 1980-01-02. You can add quotation marks around a query to treat the dash or slash argument as a string. For example, \"1980/01/02\" is treated as is. The same is true of \"1980-01-02\".";
		driver.waitForPageToBeReady();
		if (actualMsg.contains(expectedMsg)) {
			base.passedStep("Observe that application warning message displayed successfully");
		} else {
			base.failedStep("No such message display");
		}

		login.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:1/12/2022 RPMXCON-49650
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that warning and pure hit result appears for
	 *              EmailBCCNames Metadata search having phrase included in the
	 *              query without wrapping in quotes on Basic Search Screen.
	 */
	@Test(description = "RPMXCON-49650", enabled = true, groups = { "regression" })
	public void verifyApplicationAdvancedMetadataSearchDisplayWarningMsg() throws InterruptedException, AWTException {

		base.stepInfo("Test case Id: RPMXCON-49650");
		base.stepInfo(
				"Verify that warning and pure hit result appears for EmailBCCNames Metadata search having phrase included in the query without wrapping in quotes on Basic Search Screen.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		String searchTerm = "@testdata";

		// Login As PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");

		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		base.stepInfo("Go to Advanced Search page enter the Searchterm");
		sessionSearch.getAdvancedSearchLink().Click();
		driver.waitForPageToBeReady();
		sessionSearch.getContentAndMetaDatabtn().Click();
		sessionSearch.getAdvancedContentSearchInput().SendKeys(searchTerm);
		sessionSearch.getAdvanceSearch_MetadataBtn().waitAndClick(3);
		sessionSearch.getSelectMetaData().selectFromDropdown().selectByVisibleText("EmailBCCNames");
		base.waitForElement(sessionSearch.getMetaDataSearchText1());
		sessionSearch.getMetaDataSearchText1().SendKeys(searchTerm);
		base.waitForElement(sessionSearch.getMetaDataInserQuery());
		sessionSearch.getMetaDataInserQuery().Click();
		// Click on Search button
		sessionSearch.getQuerySearchButton().waitAndClick(10);
		String actualMsg = sessionSearch.getWarningMsg().getText();
		System.out.println(actualMsg);
		base.stepInfo(actualMsg);
		String expectedMsg = "Your query contains two or more arguments that do not have an operator between them. In Sightline, each term without an operator between them will be treated as A OR B, not \"A B\" as an exact phrase. If you want to perform a phrase search, wrap the terms in quotations (ex. \"A B\" returns all documents with the phrase A B).";
		driver.waitForPageToBeReady();
		if (actualMsg.contains(expectedMsg)) {
			base.passedStep("Observe that application warning message displayed successfully");
		} else {
			base.failedStep("No such message display");
		}

		login.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:12/12/2022 RPMXCON-49763
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that user should be able to search with the field EmailCCNamesAndAddresses from basic search.
	 */
	@Test(description = "RPMXCON-49763", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyUserSearchWithEmailBCCNamesAndAddressesInBasicSearch(String username, String password, String role) {

		base.stepInfo("Test case Id: RPMXCON-49763 ");
		base.stepInfo(
				"Verify that user should be able to search with the field EmailCCNamesAndAddresses from basic search.");
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		String metaDataField = "EmailBCCNamesAndAddresses";
		String searchStringWithDoubleQuotes = "\"" + Input.emailAllDomainOption + "\"";
		String searchStringWithOutDoubleQuotes = Input.emailAllDomainOption;

		// Login As user
		login.loginToSightLine(username, password);
		base.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// Configure the query to search with Metadata EmailBCCNamesAndAddresses with
		// double quotes
		base.stepInfo("Configuring the query to search with Metadata EmailBCCNamesAndAddresses with double quotes.");
		sessionSearch.basicMetaDataDraftSearch(metaDataField, null, searchStringWithDoubleQuotes, null);

		// verify that Result should appear for entered EmailBCCNamesAndAddresses with
		// double quote in Search Query Screen with exact match.
		sessionSearch.SearchBtnAction();
		sessionSearch.returnPurehitCount();
		base.waitForElement(sessionSearch.contentAndMetaDataResultBasicSearch());
		base.stepInfo("Resultant Search Query : " + sessionSearch.contentAndMetaDataResultBasicSearch().getText()
				+ " in Basic Search Query Screen.");
		base.passedStep(
				"verified that Result appear for entered EmailBCCNamesAndAddresses with double quote in Search Query Screen with exact match.");

		// click add new search button
		sessionSearch.addNewSearch();
		
		// EmailBCCNamesAndAddresses without double quotes
		driver.waitForPageToBeReady();
		base.stepInfo("Configuring the query to search with Metadata EmailBCCNamesAndAddresses without double quotes.");
		sessionSearch.getContentAndMetaDatabtnC().waitAndClick(10);
		sessionSearch.newMetaDataSearchInBasicSearch(metaDataField, searchStringWithOutDoubleQuotes);
		driver.waitForPageToBeReady();

		// verify that Result should appear for entered EmailBCCNamesAndAddresses
		// without double quote in Search Query Screen.
		base.stepInfo("Resultant Search Query : " + sessionSearch.contentAndMetaDataResultBasicSearch().getText()
				+ " in Basic Search Query Screen.");
		base.passedStep(
				"verified that Result appear for entered EmailBCCNamesAndAddresses without double quote in  Search Query Screen");

		// logOut
		login.logout();
	}

	

	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } 
		};
		return users;
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
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  Assignment_Regression_sprint23 .**");
	}
}
