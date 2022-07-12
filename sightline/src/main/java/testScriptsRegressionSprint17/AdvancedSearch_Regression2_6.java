package testScriptsRegressionSprint17;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearch_Regression2_6 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	TagsAndFoldersPage tagPage;
	AssignmentsPage assignPage;
	BaseClass baseClass;
	Input in;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		//in = new Input();
		//in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		tagPage = new TagsAndFoldersPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);

	}
	/**
	 * @Author Jayanthi
	 * @description:To Verify Regression Issue: After bulk tagging, Session search with workproduct 'Tag' is not working:1
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49048", groups = { "regression" })
	public void verifyTagCount_Tally() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-49048");
		baseClass.stepInfo("To Verify Regression Issue: After bulk tagging, Session search with workproduct 'Tag' "
				+ "is not working:1");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Loggin in as PA USer.");

		String tagTallyName = "tagTally" + Utility.dynamicNameAppender();

		TallyPage tp = new TallyPage(driver);

		tp.navigateTo_Tallypage();
		tp.SelectSource_SecurityGroup(Input.securityGroup);

		// source verification
		tp.verifySourceSelected();
		tp.applyFilterToTallyBy(Input.metaDataName, "include", Input.custodianName_Andrew); // Applying filter 'Include
		baseClass.stepInfo("**Applying  Tally by field as Metadata :" + Input.metaDataName + "**");
		tp.selectTallyByMetaDataField(Input.metaDataName);
		tp.verifyTallyChart();
		int taggedCount = tp.verifyDocCountBarChart();

		// bulk tag
		tp.tallyActions();
		baseClass.waitTime(2);
		baseClass.stepInfo("**Bulk Tagging All Docs From Tally chart.**");
		tp.bulkTag(tagTallyName, 1);
		baseClass.stepInfo("Bulk Tag Action done successfully from tally page for doc count -" + taggedCount);

		sessionSearch.navigateToAdvancedSearchPage();
		// Adding WP Tag into search text box
		sessionSearch.workProductSearch("tag", tagTallyName, true);
		baseClass.stepInfo("Configured a Query with tag " + tagTallyName);
		sessionSearch.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount = sessionSearch.verifyPureHitsCount();
		baseClass.stepInfo(
				"Total no docs after configured query as per test case[tag created from tally] is   " + PureHitCount);
		System.out.println(taggedCount + PureHitCount);
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals(String.valueOf(taggedCount), PureHitCount);// Validation of hit count.
		assertion.assertAll();
		baseClass.passedStep("Bulk Tagged docs Count for Tag created from Bulk ACtions Tally Page and  Advacned search"
				+ " Work PRoduct/Tag Search using tag created from tally page are the same.");

		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagPage.deleteAllTags(tagTallyName);

		loginPage.logout();

	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
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
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed Advanced search Regression6**");
	}
}