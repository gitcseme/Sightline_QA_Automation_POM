package sightline.basicSearch;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BasicSearch_Phase2_Regression {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}
	
	/**
	 * @author Jeevitha
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (exclamation mark ! ) embedded within a
	 *              Regular Expression query.[RPMXCON-61593]
	 */
	@Test(description = "RPMXCON-61593", enabled = true, groups = { "regression" })
	public void verifyApplicationWithExclamationMark() {

		String searchString = "\"##U\\!C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61593 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (exclamation mark ! ) embedded within Regular Expression query.");

		// configure search with exclamation mark & get purehit count
		base.stepInfo("Navigating to Basic search page");
		int purehit = session.basicContentSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query. : " + searchString);

		// verify exclamation mark ! should treat as whitespace and it should return all
		// documents having word mentioned "U and C Tester "
		session.addNewSearch();
		int purehit2 = session.multipleBasicContentSearch("\"U&C Tester\"");
		base.digitCompareEquals(purehit, purehit2,
				"Query is returning all documents  having word mentioned 'U and C Tester'",
				"returnig Document is not as expected");

		// logOut
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (ampersand & ) embedded within a Regular
	 *              Expression query.[RPMXCON-61591]
	 */
	@Test(description = "RPMXCON-61591", enabled = true, groups = { "regression" })
	public void verifyApplicationWithAmpersand() {

		String searchString = "\"##U\\&C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61591 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (ampersand & ) embedded within a Regular Expression query.");

		// configure search with ampersand & get purehit count
		base.stepInfo("Navigating to Basic search page");
		int purehit = session.basicContentSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query. : " + searchString);

		// verify ampersand & should treat as whitespace and it should return all
		// documents having word mentioned "U and C Tester "
		session.addNewSearch();
		int purehit2 = session.multipleBasicContentSearch("\"U&C Tester\"");
		base.digitCompareEquals(purehit, purehit2,
				"Query is returning all documents  having word mentioned 'U and C Tester'",
				"returnig Document is not as expected");

		// logout
		login.logout();
	}

	@DataProvider(name = "SearchQueryWithSpace")
	public Object[][] SearchQueryWithSpace() {
		return new Object[][] { { "“stock investment” ~5", "7" }, { "\"stock investment\" ~5", "7" },
				{ "“stock investment” ~5", "7" }, { "\"stock investment\" ~5 OR stock", "8" } };
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that result appears for proximity search having spaces
	 *              between the proximity query in Basic Search Query
	 *              Screen.[RPMXCON-57288]
	 */
	@Test(description = "RPMXCON-57288", dataProvider = "SearchQueryWithSpace", enabled = true, groups = {
			"regression" })
	public void verifyResultAppearsForProximitySearchWithSpace(String searchString, String warningMsg) {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57288 Basic Search");
		base.stepInfo(
				"Verify that result appears for proximity search having spaces between the proximity query in Basic Search Query Screen.");

		// configure search & fetch Query inserted in search box
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		driver.waitForPageToBeReady();
		base.hitEnterKey(1);
		base.waitForElement(session.getQueryFromTextBox());
		String configuredquery = session.getQueryFromTextBox().getText();
		base.stepInfo("Configuring the Search with given search Query. : " + configuredquery);

		// verify expected warning message is displayed
		session.verifyWarningMessage(true, true, Integer.parseInt(warningMsg));
		session.getYesQueryAlert().waitAndClick(10);

		// Verify that result appears for proximity search having spaces between the
		// proximity query in Basic Search Query Screen.
		int purehit = session.returnPurehitCount();
		base.waitTime(2);
		base.compareTextViaContains(configuredquery, " ",
				"Result displayed for search having spaces between the proximity query",
				"Displayed Result query is not as expecting");

		// logout
		login.logout();
	}

	/**
	 * @author
	 * @Description :Verify that User can run [Execute] - Drafted Basic search
	 *  with Comments from Saved Search Screen. RPMXCON-48954
	 */
	
	@Test(description = "RPMXCON-48954", enabled = true,groups = { "regression" })
	public void verifyUserRunDraftedBasicSearchWithCommentsFromSavedSearchScreen() throws InterruptedException, AWTException {
		
		String savedSearchName = "savedSearch"+Utility.dynamicNameAppender();
		String comment = "Reviewed";
		String expectedLastStatus = "COMPLETED";
		DocViewPage docView = new DocViewPage(driver);
		
		base.stepInfo("Test case Id: RPMXCON-48954");
		base.stepInfo("Verify that User can run [Execute] - Drafted Basic search with Comments from Saved Search Screen.");
  		
  	    // login
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
  		
  		// Configure Documents_Comments: Reviewed and Save it without execute
  		base.stepInfo("Configure Documents_Comments: Reviewed and Save it without execute.");
  		session.remarksOrCommentFields_DraftBS(Input.documentComments,comment);
  		session.saveSearch(savedSearchName);
  		
  		//getting count before adding comment
  		base.selectproject();
  		int countBeforeAddingComment = session.getCommentsOrRemarksCount(Input.documentComments,comment);
  		
  		// performing metaData search 
  		base.selectproject();
  		session.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCustodianNameInput,null);
  		
  		// viewing the Resultant documents in the docView
  		base.stepInfo("viewing the Resultant documents in the docView.");
  		session.ViewInDocView();
  		
  		// adding comment 'Reviewed' to document in docView
  		base.stepInfo("adding comment 'Reviewed' to document in docView.");
  		docView.editingCodingFormAndEnteringToNextDocument(comment);
  		
  	    
  		//verify that User should run [Execute] - Drafted Basic search with comment from Saved Search Screen. and correct "Count Of Result"" column should get updated which satisfied criteria in Saved Search Screen.
  		int expectedCountOfResult = countBeforeAddingComment+1;
  		saveSearch.savedSearchExecute(savedSearchName,expectedCountOfResult);
  		base.passedStep("verified that User run [Execute] - Drafted Basic search with comment from Saved Search Screen. and correct \"Count Of Result\"\" column should get updated which satisfied criteria in Saved Search Screen.");
  		
  		//Verify that Last Status column status should  get displayed as "COMPLETED"
  		base.ValidateElement_Presence(saveSearch.getSearchStatus(savedSearchName,expectedLastStatus), "Last Status 'COMPLETED'");
  		base.passedStep("Verified that Last Status column status  get displayed as \"COMPLETED\".");
  		
  		// Deleting the SavedSearch
  		session.ViewInDocView();
  		docView.editingCodingFormAndEnteringToNextDocument("");
  		saveSearch.deleteSearch(savedSearchName,Input.mySavedSearch,"Yes");
  		
  	// logOut
  		login.logout();
	}

	@DataProvider(name = "data")
	public Object[][] data() {
		return new Object[][] { { "\"\"development methodology\" \"money related\"\"~5" },
				{ "“”development methodology” “money related””~5" },
				{ "\"\"development methodology” “money related””~5" }, };
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
	 * @Description Verify that user should be able to search with the field
	 *              EmailCCNamesAndAddresses from basic search.
	 */
	@Test(description = "RPMXCON-49763", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyUserSearchWithEmailBCCNamesAndAddressesInBasicSearch(String username, String password,
			String role) {

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
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that error message should be displayed when save search
	 *              name entered with < > * ; ‘ / ( ) # & from Basic search.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-65052", enabled = true, groups = { "regression" })
	public void verifyErrorMsgForSaveSearchWithSpclChar() throws InterruptedException {
		String searchName = "Search<>*;/()";
		String expectedMsg = "Invalid search name";

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-65052 Basic Search");
		base.stepInfo(
				"Verify that error message should be displayed when save search name entered with < > * ; ‘ / ( ) # & from Basic search");

		// Configure content query
		session.basicContentSearch(Input.searchString5);
		session.saveSearchQuery(searchName);

		// verify warning Message
		base.VerifyWarningMessage(expectedMsg);

		login.logout();
	}

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that application does not display warning B message on
	 *              Advanced Search if user enters "bi-weekly" with quotations.
	 *              [RPMXCON-57121]
	 * @throws InterruptedException
	 */

	@Test(description = "RPMXCON-57121", enabled = true, groups = { "regression" })
	public void verifyWarningMsgOnAdvPage() throws InterruptedException {
		String search = "\"bi-weekly\"";

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-57121 Basic Search");
		base.stepInfo(
				"Verify that application does not display warning B message on Advanced Search if user enters \"bi-weekly\" with quotations.");

		// configure Query with Double Quotes
		session.navigateToAdvancedSearchPage();
		session.advancedContentSearchConfigure(search);
		session.addNewSearch();

		// verify warning message is not displayed
		String expectedMsg = "Warning message containing YOUR QUERY CONTAINS A HYPHEN CHARACTER is Not Displayed";
		boolean flag = session.getQueryAlertGetText().isElementAvailable(5);
		base.printResutInReport(flag, "Warning message is displayed", expectedMsg, "Fail");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that application does not display warning A message on
	 *              Advanced Search if user enters "2009/09/20" with quotations.
	 *              [RPMXCON-57119]
	 * @throws InterruptedException
	 */

	@Test(description = "RPMXCON-57119", enabled = true, groups = { "regression" })
	public void verifyWarningMsgForDateOnAdvPage() throws InterruptedException {
		String search = "\"2009/09/20\"";

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-57119 Basic Search");
		base.stepInfo(
				"Verify that application does not display warning A message on Advanced Search if user enters \"2009/09/20\" with quotations.");

		// configure Query with Double Quotes
		session.navigateToAdvancedSearchPage();
		session.advancedContentSearchConfigure(search);
		session.addNewSearch();

		// verify warning message is not displayed
		String expectedMsg = "Warning message containing YOUR QUERY CONTAINS AN ARGUMENT is Not Displayed";
		boolean flag = session.getQueryAlertGetText().isElementAvailable(5);
		base.printResutInReport(flag, "Warning message is displayed", expectedMsg, "Fail");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that application does not display warning B message on
	 *              Basic Search if user enters "bi-weekly" with quotations.
	 *              [RPMXCON-57112]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57112", enabled = true, groups = { "regression" })
	public void verifyWarningMsgOnBsPage() throws InterruptedException {
		String search = "\"bi-weekly\"";

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-57112 Basic Search");
		base.stepInfo(
				"Verify that application does not display warning B message on Basic Search if user enters \"bi-weekly\" with quotations.");

		// configure Query with Double Quotes
		session.basicContentDraftSearch(search);
		session.addNewSearch();

		// verify warning message is not displayed
		String expectedMsg = "Warning message containing YOUR QUERY CONTAINS A HYPHEN CHARACTER is Not Displayed";
		boolean flag = session.getQueryAlertGetText().isElementAvailable(5);
		base.printResutInReport(flag, "Warning message is displayed", expectedMsg, "Fail");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that Basic Search works properly - for MasterDate time
	 *              metadata - Provide only dates (not times) with "Is" operator
	 *              [RPMXCON-57220]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57220", enabled = true, groups = { "regression" })
	public void verifyMasterdateWorksProperly() throws InterruptedException {

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-57220 Basic Search");
		base.stepInfo(
				"Verify that Basic Search works properly - for MasterDate time metadata - Provide only dates (not times) with \"Is\" operator");

		// configure Query with Double Quotes
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		session.basicMetaDataSearch(Input.masterDateText, "IS", dateFormat.format(date), null);
		String query = session.configuredQuery();

		String passMsg = "Basic Search works properly - for MasterDate time metadata - Provide only dates (not times) with \"Is\" operator  ";
		String failMsg = "Basic Search doesnot work as expected";
		base.textCompareEquals(query,
				"MasterDate: [" + dateFormat.format(date) + " TO " + dateFormat.format(date) + "]", passMsg, failMsg);

		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (Subtraction mark -) embedded within a
	 *              Regular Expression query.RPMXCON-61610
	 */
	@Test(description = "RPMXCON-61610", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageSubtractionMarkEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U-C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61610 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (Subtraction mark -) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that Subtraction mark - is treating as whitespace and it returns pure
		// hit count on Basic search screen.
		base.stepInfo("Performing Search Action");
		int purehit = session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that Subtraction mark - is treating as whitespace and it returns pure hit count on Basic search screen.");

		// verify returning documents having word U and C Tester
		session.addNewSearch();
		int purehit2 = session.multipleBasicContentSearch("\"U&C Tester\"");
		base.digitCompareEquals(purehit, purehit2,
				"it is returning all documents  having word mentioned 'U and C Tester'",
				"returnig Document is not as expected");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (asterisk mark *) embedded within a
	 *              Regular Expression query.RPMXCON-61611
	 */
	@Test(description = "RPMXCON-61611", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageAsteriskMarkEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\*C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61611 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (asterisk mark *) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that asterisk mark * is treating as whitespace and it returns pure hit
		// count on Basic search screen.
		base.stepInfo("Performing Search Action");
		int purehit = session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that asterisk mark * is treating as whitespace and it returns pure hit count on Basic search screen.");

		// verify returning documents having word U and C Tester
		session.addNewSearch();
		int purehit2 = session.multipleBasicContentSearch("\"U&C Tester\"");
		base.digitCompareEquals(purehit, purehit2,
				"it is returning all documents  having word mentioned 'U and C Tester'",
				"returnig Document is not as expected");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (dollar $) embedded within a Regular
	 *              Expression query.RPMXCON-61600
	 */
	@Test(description = "RPMXCON-61600", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessagedollarEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\$C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61600 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (dollar $) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that dollar $ is treating as whitespace and it returns pure hit count
		// on Basic search screen.
		base.stepInfo("Performing Search Action");
		int purehit = session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that dollar $ is treating as whitespace and it returns pure hit count on Basic search screen.");

		// verify returning documents having word U and C Tester
		session.addNewSearch();
		int purehit2 = session.multipleBasicContentSearch("\"U&C Tester\"");
		base.digitCompareEquals(purehit, purehit2,
				"it is returning all documents  having word mentioned 'U and C Tester'",
				"returnig Document is not as expected");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (space) embedded within a Regular
	 *              Expression query.RPMXCON-61595
	 */
	@Test(description = "RPMXCON-61595", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageSpaceEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\ C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61595 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (space) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that Space is treating as whitespace and it returns pure hit count on
		// Basic search screen.
		base.stepInfo("Performing Search Action");
		int purehit = session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that Space is treating as whitespace and it returns pure hit count on Basic search screen.");

		// verify returning documents having word U and C Tester
		session.addNewSearch();
		int purehit2 = session.multipleBasicContentSearch("\"U&C Tester\"");
		base.digitCompareEquals(purehit, purehit2,
				"it is returning all documents  having word mentioned 'U and C Tester'",
				"returnig Document is not as expected");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (colon mark : ) embedded within a Regular
	 *              Expression query.RPMXCON-61594
	 */
	@Test(description = "RPMXCON-61594", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageColonMarkEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\:C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61594 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (colon mark : ) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that colon mark : is treating as whitespace and it returns pure hit
		// count on Basic search screen.
		base.stepInfo("Performing Search Action");
		int purehit = session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that colon mark : is treating as whitespace and it returns pure hit count on Basic search screen.");

		// verify returning documents having word U and C Tester
		session.addNewSearch();
		int purehit2 = session.multipleBasicContentSearch("\"U&C Tester\"");
		base.digitCompareEquals(purehit, purehit2,
				"it is returning all documents  having word mentioned 'U and C Tester'",
				"returnig Document is not as expected");

		// logOut
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that result appears for proximity having 2 Phrases in
	 *              Basic Search Query Screen. - Metadata[RPMXCON-57332]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57332", enabled = true, dataProvider = "data", groups = { "regression" })
	public void verifyResultForTwoProxiPhrases(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57332 Basic Search");
		base.stepInfo(
				"Verify that result appears for proximity having 2 Phrases in Basic Search Query Screen. - Metadata");

		// Configure Query
		session.basicContentDraftSearch(data);
		base.stepInfo("Configured Query is : " + data);

		// verify Warning message
		session.verifyWarningMessage(true, true, 2);
		session.tallyContinue(5);

		login.logout();
	}

	@DataProvider(name = "data2")
	public Object[][] data2() {
		return new Object[][] { { "Precision AND ProximitySearch Truthful”~5" },
				{ "Precision AND ProximitySearch Truthful\"~5" }, { "Precision AND ProximitySearch Truthful”~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful ” ~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful” ~5" },
				{ "\"Truthful Balance\" OR ProximitySearch Truthful \" ~5" },
				{ "\"Truthful Balance\" OR ProximitySearch Truthful\" ~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful” ~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful ” ~5" },
				{ "Precision AND ProximitySearch Truthful\" ~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful ” ~5" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that belly band message appears when user configured
	 *              Proximity with Right double quotes only and combined with other
	 *              criteria in Basic Search Query Screen.[RPMXCON-57298]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57298", enabled = true, dataProvider = "data2", groups = { "regression" })
	public void verifyMsgOfProxiWithRightDoubleQuotes(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57298 Basic Search");
		base.stepInfo(
				"Verify that belly band message appears when user configured Proximity with Right double quotes only and combined with other criteria in Basic Search Query Screen.");

		// Configure Query
		session.basicContentDraftSearch(data);
		base.stepInfo("Configured Query is : " + data);

		// verify Warning message
		session.verifyWarningMessage(true, true, 3);
		session.tallyContinue(5);

		login.logout();
	}

	@DataProvider(name = "data3")
	public Object[][] data3() {
		return new Object[][] { { "Precision AND “ProximitySearch Truthful~5 " },
				{ "“Truthful Balance” AND Precision AND “ProximitySearch Truthful~5" },
				{ "Precision AND \"ProximitySearch Truthful~5" },
				{ "\"Truthful Balance\" AND Precision AND \"ProximitySearch Truthful~5" },
				{ "Precision AND “ProximitySearch Truthful~5" },
				{ "“Truthful Balance” AND Precision AND “ProximitySearch Truthful~5" },
				{ "Precision AND “ProximitySearch Truthful ~5 " },
				{ "“Truthful Balance” AND Precision AND “ProximitySearch Truthful ~5" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that belly band message appears when user configured
	 *              Proximity with Left double quotes only and combined with other
	 *              criteria in Basic Search Query Screen.[RPMXCON-57297]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57297", enabled = true, dataProvider = "data3", groups = { "regression" })
	public void verifyMsgOfProxiWithLeftDoubleQuotes(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57297 Basic Search");
		base.stepInfo(
				"Verify that belly band message appears when user configured Proximity with Left double quotes only and combined with other criteria in Basic Search Query Screen.");

		// Configure Query
		session.basicContentDraftSearch(data);
		base.stepInfo("Configured Query is : " + data);

		// verify Warning message
		session.verifyWarningMessage(true, true, 3);
		session.tallyContinue(5);

		login.logout();
	}

	@DataProvider(name = "data4")
	public Object[][] data4() {
		return new Object[][] { { "stock investment”~5" }, { "stock investment\"~5" }, { "stock investment”~5" },
				{ "iterative methodology”" }, { "iterative methodology test*”" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that belly band message appears when user tries to run
	 *              search having Right double quote only in Basic Search Query
	 *              Screen.[RPMXCON-57284]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57284", enabled = true, dataProvider = "data4", groups = { "regression" })
	public void verifyMsgWithRightDoubleQuotes(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57284 Basic Search");
		base.stepInfo(
				"Verify that belly band message appears when user tries to run search having Right double quote only in Basic Search Query Screen.");

		// Configure Query
		session.basicContentDraftSearch(data);
		base.stepInfo("Configured Query is : " + data);

		// verify Warning message
		session.verifyWarningMessage(true, true, 3);

		login.logout();
	}

	@DataProvider(name = "data5")
	public Object[][] data5() {
		return new Object[][] { { "“stock investment~5" }, { "\"stock investment~5" }, { "“stock investment~5" },
				{ "\"iterative methodology" }, { "iterative methodology\"" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that belly band message appears when user tries to run
	 *              search having left double quote only in Basic Search Query
	 *              Screen.[RPMXCON-57283]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57283", enabled = true, dataProvider = "data5", groups = { "regression" })
	public void verifyMsgWithLeftDoubleQuotes(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57283 Basic Search");
		base.stepInfo(
				"Verify that belly band message appears when user tries to run search having left double quote only in Basic Search Query Screen.");

		// Configure Query
		session.basicContentDraftSearch(data);
		base.stepInfo("Configured Query is : " + data);

		// verify Warning message
		session.verifyWarningMessage(true, true, 3);

		login.logout();
	}

	@DataProvider(name = "data6")
	public Object[][] data6() {
		return new Object[][] { { "“stock investment~5”" }, { "\"stock investment~5\"" }, { "“stock investment~5”" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that result appears for proximity search having double
	 *              quote after the number in Basic Search Query
	 *              Screen.[RPMXCON-57286]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57286", enabled = true, dataProvider = "data6", groups = { "regression" })
	public void verifyResultForProxiWithDoubleQuotes(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57286 Basic Search");
		base.stepInfo(
				"Verify that result appears for proximity search having double quote after the number in Basic Search Query Screen.");

		// Configure Query
		session.basicContentDraftSearch(data);
		base.stepInfo("Configured Query is : " + data);
		session.getSearchButtonSec().waitAndClick(10);

		// verify warning message is not displayed
		String expectedMsg = "Warning message is Not Displayed";
		boolean flag = session.getQueryAlertGetText().isElementAvailable(5);
		base.printResutInReport(flag, expectedMsg, "Warning message is displayed", "Fail");

		// verify Purehit
		session.returnPurehitCount();

		login.logout();
	}

	@DataProvider(name = "data7")
	public Object[][] data7() {
		return new Object[][] { { "“stock investment~5”" }, { "\"stock investment~5\"" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that Search result when user tries to run proximity
	 *              search having double quote after the number in Basic Search
	 *              Query Screen.[RPMXCON-57285]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57285", enabled = true, dataProvider = "data7", groups = { "regression" })
	public void verifyResultForProxiWithQuotes(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57285 Basic Search");
		base.stepInfo(
				"Verify that Search result when user tries to run proximity search having double quote after the number in Basic Search Query Screen.");

		// Configure Query
		session.basicContentDraftSearch(data);
		base.stepInfo("Configured Query is : " + data);
		session.getSearchButtonSec().waitAndClick(10);

		// verify warning message is not displayed
		String expectedMsg = "Warning message is Not Displayed";
		boolean flag = session.getQueryAlertGetText().isElementAvailable(5);
		base.printResutInReport(flag, expectedMsg, "Warning message is displayed", "Fail");

		// verify Purehit
		session.returnPurehitCount();

		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (dot mark .) embedded within a Regular
	 *              Expression query.RPMXCON-61612
	 */

	@Test(description = "RPMXCON-61612", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageDotMarkEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\.C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61612 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (dot mark .) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that dot mark . is treating as whitespace and it returns pure hit
		// count on Basic search screen.
		base.stepInfo("Performing Search Action");
		session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that dot mark . is treating as whitespace and it returns pure hit count on Basic search screen.");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (forward slash mark /) embedded within a
	 *              Regular Expression query.RPMXCON-61613
	 */

	@Test(description = "RPMXCON-61613", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageForwardSlashMarkEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\/C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61613 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (forward slash mark /) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that forward slash mark / is treating as whitespace and it returns
		// pure hit count on Basic search screen.
		base.stepInfo("Performing Search Action");
		session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that forward slash mark / is treating as whitespace and it returns pure hit count on Basic search screen.");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (right square symbol ]) embedded within a
	 *              Regular Expression query.RPMXCON-61615
	 */

	@Test(description = "RPMXCON-61615", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageRightSquareSymbolEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\]C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61615 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (right square  symbol ]) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that right square symbol ] is treating as whitespace and it returns
		// pure hit count on Basic search screen.
		base.stepInfo("Performing Search Action");
		session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that right square symbol ] is treating as whitespace and it returns pure hit count on Basic search screen.");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (left square symbol [) embedded within a
	 *              Regular Expression query.RPMXCON-61616
	 */

	@Test(description = "RPMXCON-61616", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageLeftSquareSymbolEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\[C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61616 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (left square  symbol [) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that left square symbol [ is treating as whitespace and it returns
		// pure hit count on Basic search screen.
		base.stepInfo("Performing Search Action");
		session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that left square symbol [ is treating as whitespace and it returns pure hit count on Basic search screen.");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (less than symbol <) embedded within a
	 *              Regular Expression query. RPMXCON-61617
	 */

	@Test(description = "RPMXCON-61617", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageLessThanSymbolEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\<C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61617 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (less than symbol <) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that less than symbol < is treating as whitespace and it returns pure
		// hit count on Basic search screen.
		base.stepInfo("Performing Search Action");
		session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that less than symbol < is treating as whitespace and it returns pure hit count on Basic search screen.");

		// logOut
		login.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:15/11/2022 RPMXCON-57116
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that application displays warning A message on Advanced
	 *              Search if user enters "2009-09-20" without wrapper quotations.
	 */
	@Test(description = "RPMXCON-57116", enabled = true, groups = { "regression" })
	public void verifyApplicationDisplayWarningMsg() throws InterruptedException, AWTException {

		base.stepInfo("Test case Id: RPMXCON-57116");
		base.stepInfo(
				"Verify that application displays warning A message on Advanced Search if user enters \"2009-09-20\" without wrapper quotations.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		String searchTerm = "2009-09-20";

		// Login As PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");

		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		base.stepInfo("Go to Search page enter the Searchterm");
		sessionSearch.getEnterSearchString().SendKeys(searchTerm);
		// Click on Search button
		sessionSearch.getSearchButton().waitAndClick(10);
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

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			login.logoutWithoutAssert();
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Batch Redactions EXECUTED******");
		try {
//						login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}
