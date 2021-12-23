package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.Keys;
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
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TS_001_BasicSearch {
	Driver driver;
	LoginPage lp;
	SessionSearch sessionSearch;
	int pureHit;
	SoftAssert softAssertion;
	SessionSearch ss;

	BaseClass bc;
	String searchName = "Search" + Utility.dynamicNameAppender();
	/*
	 * String tagName = "tagName"+Utility.dynamicNameAppender(); String folderName =
	 * "AFolderName"+Utility.dynamicNameAppender();
	 */

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		// Open browser
		softAssertion = new SoftAssert();
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		bc = new BaseClass(driver);
		ss = new SessionSearch(driver);

	}

	// @Test(groups={"regression"},priority=1)
	public void copySearchTextToNewSearch() {
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		bc.selectproject();
		softAssertion.assertEquals(Input.pureHitSeachString1, ss.basicContentSearch(Input.searchString1));

		// below locators are one time use in second search
		ss.getCopyTo().Click();
		ss.getCopyToNewSearch().Click();
		ss.getSecondSearchBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ss.getSecondPureHit().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		softAssertion.assertEquals(Integer.parseInt(ss.getSecondPureHit().getText()), Input.pureHitSeachString1);
		lp.logout();
	}

	// @Test(groups={"regression"},priority=2)
	public void autoSuggest() throws InterruptedException {

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ss.getBasicSearch_MetadataBtn().Visible();
			}
		}), Input.wait30);
		ss.getBasicSearch_MetadataBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ss.getSelectMetaData().Visible();
			}
		}), Input.wait30);

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ss.getSelectMetaData().selectFromDropdown().selectByVisibleText("CustodianName");

		ss.getMetaDataSearchText1().SendKeys("P Allen");
		Thread.sleep(2000);
		ss.getMetaDataSearchText1().SendKeys("" + Keys.DOWN + Keys.ENTER);

		ss.getMetaDataInserQuery().Click();
		// Click on Search button
		ss.getSearchButton().Click();
		try {

			ss.getTallyContinue().waitAndClick(4000);
		} catch (Exception e) {

		}

		// verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ss.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		Assert.assertEquals(ss.getPureHitsCount().getText(), "1135");
		lp.logout();

	}

	// @Test(groups={"regression"},priority=3)
	public void emailInclusive() {

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);

		softAssertion.assertTrue(ss.basicMetaDataSearch("EmailInclusiveReason", null, "*", null) >= 1);// 1135);
		lp.logout();

		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		softAssertion.assertTrue(ss.basicMetaDataSearch("EmailInclusiveReason", null, "*", null) >= 1);// 1135);

		softAssertion.assertAll();

		lp.logout();
	}

	// @Test(groups={"regression"},priority=4)
	public void conceptuallySimilar() throws InterruptedException {
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// Impersonate as Reviewer
		bc = new BaseClass(driver);
		bc.impersonateRMUtoReviewer();
		ss.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		ss.getConceptuallyPlayButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ss.getCSHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		Assert.assertTrue(Integer.parseInt(ss.getCSHitsCount().getText()) >= 1);

		lp.logout();
	}

	// @Test(groups={"regression"},priority=5)
	public void exsitingBulkFolder() throws InterruptedException {
		String Folder = "AFolder" + Utility.dynamicNameAppender();
		// Login as PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);

		// create folder
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.CreateFolder(Folder, "Default Security Group");
		System.out.println("Folder creation is Successful : " + Folder);

		// Search and add docs to created folder
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString1);

		// add docs to folder
		sessionSearch.bulkFolderExisting(Folder);

		// check folder and count in advance search

		bc.selectproject();
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectFolderInASwp(Folder);
		Assert.assertEquals(Input.pureHitSeachString1, sessionSearch.serarchWP());
		lp.logout();

	}

	// @Test(groups={"regression"},priority=6)
	public void existingBulkTag() throws InterruptedException {
		String Tag = "ATag" + Utility.dynamicNameAppender();
		// Login as PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);

		// create tag
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.CreateTag(Tag, "Default Security Group");
		System.out.println("Tag creation is Successful : " + Tag);

		// Search and add docs to created tag
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString1);

		// add docs to folder
		sessionSearch.bulkTagExisting(Tag);

		// check folder and count in advance search

		bc.selectproject();
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectTagInASwp(Tag);
		Assert.assertEquals(Input.pureHitSeachString1, sessionSearch.serarchWP());
		lp.logout();
	}

	// @Test(groups={"smoke","regression"},priority=7)
	public void starSearch() {
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		sessionSearch = new SessionSearch(driver);

		Assert.assertEquals(sessionSearch.basicContentSearch("*"), 1202);
	}

	// @Test(groups={"regression"},priority=8)
	public void bulkUnTag() throws InterruptedException {

		String tagName = "tagName" + Utility.dynamicNameAppender();
		// Login
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);

		// perform search
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString1);
		// Bulk Tag
		sessionSearch.bulkTag(tagName);
		// Untag
		sessionSearch.bulkUnTag(tagName);
		final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return tf.getTag_ToggleDocCount().Visible();
			}
		}), Input.wait60);

		// Validate tag count in tags tab
		tf.getTag_ToggleDocCount().waitAndClick(20);
		tf.getTagandCount(tagName, 0).waitAndGet(30);
		System.out.println(tf.getTagandCount(tagName, 0).getText());

		Assert.assertTrue(tf.getTagandCount(tagName, 0).Displayed());
		System.out.println(tagName + " could be seen under tags and folder page");

		lp.logout();
	}

	// @Test(groups={"regression"},priority=9)
	public void bulkUnFolder() throws InterruptedException {

		String folderName = "folderName1" + Utility.dynamicNameAppender();
		// Login
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Perform search
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString1);

		// Bulk folder
		sessionSearch.bulkFolder(folderName);

		// Unfolder
		sessionSearch.bulkUnFolder(folderName);
		final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return tf.getFoldersTab().Visible();
			}
		}), Input.wait60);

		// Validate in folders tab
		tf.getFoldersTab().waitAndClick(10);
		tf.getFolder_ToggleDocCount().waitAndClick(10);
		tf.getFolderandCount(folderName, 0).WaitUntilPresent();

		System.out.println(tf.getFolderandCount(folderName, 0).getText());
		Assert.assertTrue(tf.getFolderandCount(folderName, 0).Displayed());

		System.out.println(folderName + " could be seen under tags and folder page");
		lp.logout();
	}

	// @Test(groups={"regression"},priority=10)
	public void metaDataSearchsBS() {
		SoftAssert softAssertion = new SoftAssert();
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);

		driver.getWebDriver().get(Input.url + "Search/Searches");
		bc.selectproject();
		softAssertion.assertEquals(1, ss.basicMetaDataSearch("CreateDate", "IS", "1993-08-11", null));

		bc.selectproject();
		softAssertion.assertEquals(340, ss.basicMetaDataSearch("CreateDate", "RANGE", "1986-01-01", "2018-01-01"));

		// with time in Range
		bc.selectproject();
		softAssertion.assertEquals(170,
				ss.basicMetaDataSearch("CreateDate", "RANGE", "2010-06-17 05:53:18", "2010-10-18 05:53:18"));

		bc.selectproject();
		softAssertion.assertTrue(0 <= ss.basicMetaDataSearch("EmailSentDate", "IS", "1990-05-05", null));

		bc.selectproject();
		softAssertion.assertTrue(0 <= ss.basicMetaDataSearch("EmailSentDate", "RANGE", "1990-05-05", "2000-05-05"));

		bc.selectproject();
		softAssertion.assertTrue(0 <= ss.basicMetaDataSearch("AppointmentStartDate", "IS", "1990-05-05", null));

		bc.selectproject();
		softAssertion
				.assertTrue(0 <= ss.basicMetaDataSearch("AppointmentStartDate", "RANGE", "1990-05-05", "2000-05-05"));
		// check IS and Range options
		/*
		 * bc.selectproject();
		 * softAssertion.assertTrue(0<=ss.basicMetaDataSearch("AppointmentEndDateOnly",
		 * "IS", "1990-05-05", null));
		 * 
		 * bc.selectproject();
		 * softAssertion.assertTrue(0<=ss.basicMetaDataSearch("AppointmentEndDateOnly",
		 * "RANGE", "1990-05-05", "2000-05-05"));
		 */
		bc.selectproject();
		softAssertion.assertTrue(0 <= ss.basicMetaDataSearch("DocDateDateOnly", "IS", "1990-05-05", null));

		bc.selectproject();
		softAssertion.assertTrue(0 <= ss.basicMetaDataSearch("DocDateDateOnly", "RANGE", "1990-05-05", "2000-05-05"));

		/*
		 * bc.selectproject();
		 * softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateAccessedDateOnly",
		 * "IS", "1990-05-05", null));
		 * 
		 * bc.selectproject();
		 * softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateAccessedDateOnly",
		 * "RANGE", "1990-05-05", "2000-05-05"));
		 */
		bc.selectproject();
		softAssertion.assertTrue(0 <= ss.basicMetaDataSearch("DateCreatedDateOnly", "IS", "1990-05-05", null));

		bc.selectproject();
		softAssertion
				.assertTrue(0 <= ss.basicMetaDataSearch("DateCreatedDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		/*
		 * bc.selectproject();
		 * softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateEditedDateOnly",
		 * "IS", "1990-05-05", null));
		 * 
		 * bc.selectproject();
		 * softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateEditedDateOnly",
		 * "RANGE", "1990-05-05", "2000-05-05"));
		 * 
		 * bc.selectproject();
		 * softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateModifiedDateOnly",
		 * "IS", "1990-05-05", null));
		 * 
		 * bc.selectproject();
		 * softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateModifiedDateOnly",
		 * "RANGE", "1990-05-05", "2000-05-05"));
		 * 
		 * bc.selectproject();
		 * softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DatePrintedDateOnly",
		 * "IS", "1990-05-05", null));
		 * 
		 * bc.selectproject();
		 * softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DatePrintedDateOnly",
		 * "RANGE", "1990-05-05", "2000-05-05"));
		 * 
		 * bc.selectproject();
		 * softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateReceivedDateOnly",
		 * "IS", "1990-05-05", null));
		 * 
		 * bc.selectproject();
		 * softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateReceivedDateOnly",
		 * "RANGE", "1990-05-05", "2000-05-05"));
		 * 
		 * bc.selectproject();
		 * softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateSavedDateOnly", "IS",
		 * "1990-05-05", null));
		 * 
		 * bc.selectproject();
		 * softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateSavedDateOnly",
		 * "RANGE", "1990-05-05", "2000-05-05"));
		 */
		bc.selectproject();
		softAssertion.assertTrue(0 <= ss.basicMetaDataSearch("MasterDateDateOnly", "IS", "1990-05-05", null));

		bc.selectproject();
		softAssertion
				.assertTrue(0 <= ss.basicMetaDataSearch("MasterDateDateOnly", "RANGE", "1990-05-05", "2000-05-05"));

		bc.selectproject();
		softAssertion.assertTrue(0 <= ss.basicMetaDataSearch("EmailDateSentDateOnly", "IS", "1990-05-05", null));

		bc.selectproject();
		softAssertion
				.assertTrue(0 <= ss.basicMetaDataSearch("EmailDateSentDateOnly", "RANGE", "1990-05-05", "2000-05-05"));

		// field mapping is not done for blow meta data search
		/*
		 * bc.selectproject(); softAssertion.assertTrue(0<=ss.basicMetaDataSearch(
		 * "AppointmentStartDateOnly", "IS", "1990-05-05", null));
		 * 
		 * bc.selectproject(); softAssertion.assertTrue(0<=ss.basicMetaDataSearch(
		 * "AppointmentStartDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		 */
		softAssertion.assertAll();
		lp.logout();
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
	@Test(dataProvider = "reservedWords", groups = { "regression" }, priority = 11)
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
		lp.logout();

	}

	/**
	 * @author Jeevitha Description:Verify the basic search for Near Duplicates
	 *         Near(RPMXCON-57001)RPMXCON-57002
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(groups = { "regression" }, priority = 12)
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
		lp.logout();

	}

	/**
	 * @author Jeevitha Description:Basic search pin search item(RPMXCON-57030)
	 *         Description: Verify Family Member Count (RPMXCON-47255) Decription
	 *         :verify Conceptually similar Count(RPMXCON-47256)
	 * @throws InterruptedException
	 */

	@Test(groups = { "regression" }, priority = 13)
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

		lp.logout();
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

	@Test(dataProvider = "reserveWords", groups = { "regression" }, priority = 13)
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

		lp.logout();

	}

	/**
	 * @author Jeevitha
	 * Description : to verify as an user login into the Application, user will
	 *             be able to search based on comments text on Content &
	 *              Metadata in basic search(RPMXCON-47777)
	 * @throws InterruptedException
	 */
	@Test(groups = { "regression" }, priority = 14)
	public void verifyComments() throws InterruptedException {
        String data=Input.searchString5;
		// login as Pa
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		bc.stepInfo("RPMXCON-47777 Basic Search");
		
		sessionSearchPage.basicContentSearch(Input.searchString1);
		sessionSearchPage.ViewInDocView();
		
		DocViewPage docview=new DocViewPage(driver);
		
		//Apply comments to document
		bc.waitForElement(docview.getDocument_CommentsTextBox());
		docview.getDocument_CommentsTextBox().SendKeys(data);
		bc.waitForElement(docview.getCodingFormSaveBtn());
		docview.getCodingFormSaveBtn().waitAndClick(10);
		
		bc.selectproject();
		//Search COmments as RMU
		int PureHit=sessionSearchPage.getCommentsOrRemarksCount(Input.documentComments, data);
		System.out.println("PureHit COunt : "+PureHit);
		bc.stepInfo("PureHit COunt : "+PureHit);

		lp.logout();
		//Search COmments as pa
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		int PureHit1=sessionSearchPage.getCommentsOrRemarksCount(Input.documentComments, data);
		System.out.println("PureHit COunt : "+PureHit1);
		bc.stepInfo("PureHit COunt : "+PureHit1);

		lp.logout();
		//Search COmments as REV
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		int PureHit2=sessionSearchPage.getCommentsOrRemarksCount(Input.documentComments, data);
		System.out.println("PureHit COunt : "+PureHit2);
		bc.stepInfo("PureHit COunt : "+PureHit2);

		lp.logout();
		
		
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {

			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				lp.logout();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			lp.logout();
			// lp.quitBrowser();
		} finally {
			lp.quitBrowser();
		}
		LoginPage.clearBrowserCache();
	}

}
