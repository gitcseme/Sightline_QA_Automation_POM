package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BasicSearch_Regression1 {
	Driver driver;
	LoginPage lp;
	int pureHit;
	SoftAssert softAssertion;
	SessionSearch ss;
	BaseClass bc;

	String searchName = "Search" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		// Input in = new Input();
		// in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws Exception, InterruptedException, IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		// Open browser
		softAssertion = new SoftAssert();
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		bc = new BaseClass(driver);
		ss = new SessionSearch(driver);
		lp = new LoginPage(driver);

	}

	@DataProvider(name = "reservedWords")
	public Object[][] dataProviderMethod() {
		return new Object[][] { { "\"Hello U&C\"~5 OR \"U&C\" OR ( \"##U&C[0-9]{2}\")", "RPMXCON-57470" },
				{ "\"Hello U&C\"~5 OR \"U&C\"", "RPMXCON-57471" },
				{ "( \"economy finance\"~5  ) OR (\"product@consilio.com\")", "RPMXCON-57472" },
				{ "(( \"Hello U{C\"~5 OR \"U{C\" OR ( \"##U}C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57452" },
				{ "(( \"Hello U|C\"~5 OR \"U|C\" OR ( \"##U|C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57453" },
				{ "(( \"Hello U}C\"~5 OR \"U}C\" OR ( \"##U}C[0-9]{2}\" OR EmailAllDomains: ( consilio.com) ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57454" },
				{ "\"Hello U&C\"~5 OR \"U&C\" OR ( \"##U&C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  )",
						"RPMXCON-57469" },
				{ "( \"Hello U&C\"~5 OR \"U&C\" OR ( \"##U&C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) )",
						"RPMXCON-57468" },
				{ "(( \"Hello U«C\"~5 OR \"U«C\" OR ( \"##U?C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57457" },
				{ "(( \"Hello  U?C \"~5 OR \"U?C \" OR ( \"##U?C [0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57460" },
				{ "(( \"Hello U?C\"~5 OR \"UU?C\" OR ( \"##UU?C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57459" },
				{ "(( \"Hello U?C\"~5 OR \"U?C\" OR ( \"##U?C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57458" },
				{ "(( \"Hello U C\"~5 OR \"U C\" OR ( \"##U C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57461" },
				{ "(( \"Hello U%C\"~5 OR \"U%C\" OR ( \"##U%C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57432" },
				{ "(( \"Hello U$C\"~5 OR \"U$C\" OR ( \"##U$C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57431" },
				{ "(( \"Hello U^C\"~5 OR \"U^C\" OR ( \"##U^C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57451" },
				{ "(( \"Hello U\\C\"~5 OR \"U\\C\" OR ( \"##U\\C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57449" },
				{ "(( \"Hello U?C\"~5 OR \"U?C\" OR ( \"##U?C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57447" },

		};
	}

	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query .(RPMXCON-57470 ,RPMXCON-57471
	 *         ,RPMXCON-57472,RPMXCON-57452,
	 *         RPMXCON-57453,RPMXCON-57454,RPMXCON-57469,RPMXCON-57468,RPMXCON-57457
	 *         RPMXCON-57460,RPMXCON-57459,RPMXCON-57458,RPMXCON-57461 ).
	 *         RPMXCON-57432,RPMXCON-57431
	 *         [#3]RPMXCON-57451,RPMXCON-57449,RPMXCON-57447
	 * 
	 */
//	@Test(dataProvider = "reservedWords", groups = { "regression" }, priority = 1)
	public void verifyBasicSearch(String data, String TC_ID) throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo(TC_ID + "  basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);

	}

	/**
	 * @author Jeevitha Description:Verify the basic search for Near Duplicates
	 *         Near(RPMXCON-57001)RPMXCON-57002
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
//	@Test(groups = { "regression" }, priority = 2)
	public void verifyCountOfSearch() throws ParseException, InterruptedException, IOException {
		// login as PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON-57001,RPMXCON-57002  BasicSearch ");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		// Verify Duplicate count and Family members count
		sessionSearchPage.basicContentSearch(Input.searchString1);

		System.out.println("Near duplicate count : " + sessionSearchPage.verifyNearDupeCount());
		bc.stepInfo("Near duplicate count : " + sessionSearchPage.verifyNearDupeCount());

		System.out.println("Family Members count : " + sessionSearchPage.verifyFamilyount());
		bc.stepInfo("Family Members count : " + sessionSearchPage.verifyFamilyount());

		bc.getPopupYesBtn();
		System.out.println("Succesfully Searched For Input " + Input.searchString2);

	}

	/**
	 * @author Jeevitha Description:Basic search pin search item(RPMXCON-57030)
	 *         Description: Verify Family Member Count (RPMXCON-47255) Decription
	 *         :verify Conceptually similar Count(RPMXCON-47256)
	 * @throws InterruptedException
	 */

//	@Test(groups = { "regression" }, priority = 3)
	public void verifyPinSearch() throws InterruptedException {
		// login as Pa
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON-57030,47255,47256 Basic Search");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		sessionSearchPage.basicMetaDataSearch("CustodianName", null, "Andrew", null);
		sessionSearchPage.saveSearch(searchName);
		sessionSearchPage.getConceptuallyPlayButton().waitAndClick(10);
		sessionSearchPage.getPinBtn(searchName).waitAndClick(10);

		// Get title attribute value
		String tooltipText = sessionSearchPage.getUnpinToolTipMsg().GetAttribute("title");
		System.out.println("Retrieved tooltip text as :" + tooltipText);
		bc.stepInfo("Retrieved tooltip text as :" + tooltipText);

		// Verification if tooltip text is matching expected value
		if (tooltipText.equalsIgnoreCase("Un Pin this Search")) {
			System.out.println("Pass : Tooltip matching expected value");
			bc.stepInfo("Pass : Tooltip matching expected value");

		} else {
			System.out.println("Fail : Tooltip NOT matching expected value");
			bc.stepInfo("Fail : Tooltip NOT matching expected value");

		}
		System.out.println("Family Members count : " + sessionSearchPage.verifyFamilyount());
		bc.stepInfo("Family Members count : " + sessionSearchPage.verifyFamilyount());

		System.out.println("Conceptually similar count : " + sessionSearchPage.verifyConceptuallySimilarCount());
		bc.stepInfo("Conceptually similar count : " + sessionSearchPage.verifyConceptuallySimilarCount());

	}

	/**
	 * @author jeevitha Description : Verify that in "DocFileType" metadata session
	 *         search, when User tries to do manually keyed terms then Auto-suggest
	 *         value is NOT selected (Though presence of a space in Multiple
	 *         words)(RPMXCON-46989)(RPMXCON-46990)
	 * @throws InterruptedException
	 */
	@DataProvider(name = "reserveWords")
	public Object[][] dataMethod() {
		return new Object[][] { { "DocFileType", "oth" }, { "DocFileName", "tes" } };
	}

//	@Test(dataProvider = "reserveWords", groups = { "regression" }, priority = 4)
	public void verifyAutoSuggest(String data1, String data2) throws InterruptedException {
		// login as Pa
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("RPMXCON-46989,RPMXCON-46990 Basic Search");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		String autoSuggestedValue = sessionSearchPage.basicMetaDataAutosuggest(data1, data2, 1);
		System.out.println(autoSuggestedValue
				+ " auto-suggest value is selected (presence of a space in Multiple words) then multiple values that are being selected is wrapped in double quotes.  ");
		bc.stepInfo(autoSuggestedValue
				+ " auto-suggest value is selected (presence of a space in Multiple words) then multiple values that are being selected is wrapped in double quotes.  ");

	}

	@DataProvider(name = "reserve")
	public Object[][] dataset() {
		return new Object[][] { { "DocFileName", "tes", "\"Testing Special characater\"", "46984" },
				{ "CustodianName", "P A", "\"P Allen\"", "46985" }, { "CustodianName", "andr", "Andrew", "46992" },
				{ "EmailToAddresses", "sat", "Satish.Pawal@Consilio.com", "46988" },

		};
	}

	/**
	 * @author Jeevitha Description: when auto-suggest value is selected (presence
	 *         of a space in Multiple words) then corresponding value gets wrapped
	 *         in double quotes. (RPMXCON- 46984,RPMXCON- 46985,RPMXCON- 46986)
	 *         (RPMXCON-46988)
	 * @throws InterruptedException
	 */

//	@Test(dataProvider = "reserve", groups = { "regression" }, priority = 5)
	public void verifyAutoSugges(String data1, String data2, String data3, String data4) throws InterruptedException {

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON- " + data4 + " Basic Search");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		String autoSuggestedValue = sessionSearchPage.validateAutosuggestSearchResult_BS(data3, data1, data2);
		lp.logout();

	}

	@DataProvider(name = "query")
	public Object[][] dataset2() {
		return new Object[][] { {
				"(( \"Hello U>C\"~5 OR \"U>C\" OR ( \"##U>C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
				"RPMXCON-57446" },
				{ "(( \"Hello U=C\"~5 OR \"U=C\" OR ( \"##U=C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57445" },
				{ "(( \"Hello U;C\"~5 OR \"U;C\" OR ( \"##U;C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57443" },
				{ "(( \"Hello U`C\"~5 OR \"U`C\" OR ( \"##U`C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57442" },
				{ "(( \"Hello U/C\"~5 OR \"U/C\" OR ( \"##U/C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57441" },
				{ "(( \"Hello U.C\"~5 OR \"U.C\" OR ( \"##U.C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57440" },
				{ "(( \"Hello U-C\"~5 OR \"U-C\" OR ( \"##U-C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57439" },
				{ "(( \"Hello U,C\"~5 OR \"U,C\" OR ( \"##U,C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57438" },
				{ "(( \"Hello U+C\"~5 OR \"U+C\" OR ( \"##U+C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57437" },
				{ "(( \"Hello U*C\"~5 OR \"U*C\" OR ( \"##U*C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57436" },
				{ "(( \"Hello U'C\"~5 OR \"U'C\" OR ( \"##U'C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57433" },
				{ "(( \"Hello U C\"~5 OR \"U C\" OR ( \"##U C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57430" },
				{ "(( \"Hello U:C\"~5 OR \"U:C\" OR ( \"##U:C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57429" },
				{ "(( \"Hello U!C\"~5 OR \"U!C\" OR ( \"##U!C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR    \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57427" },
				{ "(( \"Hello U&C\"~5 OR \"U&C\" OR ( \"##U&C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*",
						"RPMXCON-57426" }, };
	}

	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *         (RPMXCON-57446,57445,57443,57442,57441,57440,57439,57438,57437,57436,57433,57430,57429,57427,57426)
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
//	@Test(dataProvider = "query", groups = { "regression" }, priority = 6)
	public void verifyBasicSearch2(String data, String TC_ID) throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo(TC_ID + "  basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);

	}

	/**
	 * @author Jeevitha Description: Verify that in "compound query (workproduct and
	 *         content/metadata) " metadata session search, when auto-suggest value
	 *         is selected (presence of a space in Multiple words) then
	 *         corresponding value gets wrapped in double quotes. (RPMXCON-46993)
	 * @throws InterruptedException
	 */
//	@Test(groups = { "regression" }, priority = 7)
	public void verifyCompoundQuery() throws InterruptedException {
		String tag = "TAGX" + Utility.dynamicNameAppender();
		String metadataFieldLabel = "DocFileName";
		String Value = "tes";
		String metaDataFileName = "\"TEST MAIL\"";
		String expectedFileDisplayInQuerySelection = "\"TEST MAIL\"";
		String expectedFileDisplay = " DocFileName: (\"TEST MAIL\") ";

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("RPMXCON-46993  Basic Search");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		sessionSearchPage.basicContentSearch(Input.searchString1);
		sessionSearchPage.bulkTag(tag);

		bc.selectproject();
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearchPage.validateAutosuggestSearchResult_AS(metaDataFileName, metadataFieldLabel, Value,
				expectedFileDisplay, expectedFileDisplayInQuerySelection, false);
		sessionSearchPage.advancedSearchWorkProduct(tag);

		// condition
		bc.waitForElement(sessionSearchPage.getAs_SelectOperation(1));
		sessionSearchPage.getAs_SelectOperation(1).selectFromDropdown().selectByVisibleText("OR");
		int pureHit4 = sessionSearchPage.saveAndReturnPureHitCount();
		System.out.println("Document count of Compound Query is: " + pureHit4);
		bc.stepInfo("Document count of Compound Query is: " + pureHit4);

		lp.logout();
	}

	/**
	 * @author Jeevitha Description : Verify that in "EmailToAddress" metadata
	 *         session search, when auto-suggest value is selected (presence of a
	 *         space in Multiple words) then corresponding value gets wrapped in
	 *         double quotes.
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void verifyMetadataAutoSuggest() throws InterruptedException {
		String expectedFileName = "\"Scott Tholan\"";
		String metaDataField = "EmailToAddresses";
		String value = "sco";

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON- 46991   Basic Search");
		bc.stepInfo(
				"Verify that in \"EmailToAddress\" metadata session search, when auto-suggest value is selected (presence of a space in Multiple words) then corresponding value gets wrapped in double quotes.");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		sessionSearchPage.validateAutosuggestSearchResult_BS(expectedFileName, metaDataField, value);
		lp.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify the basic search for Threaded Documents[RPMXCON-47288]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void verifyThreadedDocs() throws InterruptedException {
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON- 47288   Basic Search Sprint-10");
		bc.stepInfo("Verify the basic search for Threaded Documents");

		// search with Metadata & Operator and verify purehit
		ss.navigateToSessionSearchPageURL();
		ss.basicMetaDataDraftSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		ss.selectOperatorInBasicSearch("OR");
		int pureHit = ss.basicContentSearchWithSaveChanges(Input.searchString1, "Yes", "Third");
		bc.stepInfo("Search Term Is : " + Input.metaDataName + " : " + Input.metaDataCustodianNameInput + " OR "
				+ Input.searchString1);
		softAssertion.assertNotEquals(pureHit, Input.TextEmpty);

		// verify Threaded document count
		String threadedCount = ss.verifyThreadedCount();
		bc.stepInfo("Threaded Document Count is: " + threadedCount);
		softAssertion.assertNotEquals(threadedCount, Input.TextEmpty);

		softAssertion.assertAll();
		lp.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify Search result should work correctly for Comments with
	 *              format like \"##PF[0-9]{4}\" [RPMXCON-49637]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void verifySearchResultForComment() throws InterruptedException {
		String docComment = "PF1231";
		String searchTerm = "\"##PF[0-9]{4}\"";
		int count = 2;

		lp = new LoginPage(driver);
		DocViewPage docview = new DocViewPage(driver);
		lp.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		bc.stepInfo("RPMXCON- 49637   Basic Search Sprint-10");
		bc.stepInfo("Verify Search result should work correctly for Comments with format like \"##PF[0-9]{4}\"");

		ss.basicContentSearch(Input.searchString1);
		ss.ViewInDocView();

		// Apply comments to document
		docview.addCommentAndSave(docComment, true, count);
		lp.logout();

		// login As PA
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("Logged In as : " + Input.pa1FullName);
		int PureHit1 = ss.getCommentsOrRemarksCount(Input.documentComments, searchTerm);
		softAssertion.assertEquals(PureHit1, count);
		lp.logout();

		// login As RMU
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Logged In as : " + Input.rmu1FullName);
		int PureHit2 = ss.getCommentsOrRemarksCount(Input.documentComments, searchTerm);
		softAssertion.assertEquals(PureHit2, count);
		lp.logout();

		// login As REV
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc.stepInfo("Logged In as : " + Input.rev1FullName);
		int PureHit3 = ss.getCommentsOrRemarksCount(Input.documentComments, searchTerm);
		softAssertion.assertEquals(PureHit3, count);
		lp.logout();

	}

	/**
	 * @author Raghuram A Date: 11/25/21 Modified date:N/A Modified by: N/A
	 *         Description : Verify that basic search is working properly for
	 *         Metadata RPMXCON-47712 Sprint 11
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void basicSearchWithMedtaDataDetails() throws InterruptedException {

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);

		bc.stepInfo("RPMXCON- 47712 Basic Search Sprint-11");
		bc.stepInfo("Verify that basic search is working properly for Metadata");

		ss.basicMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		ss.verifySearchReult(Input.metaDataCustodianNameInput, Input.metaDataName.toUpperCase());

		lp.logout();

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
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

}
