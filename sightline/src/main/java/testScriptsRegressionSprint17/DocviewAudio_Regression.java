package testScriptsRegressionSprint17;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.RedactionPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import views.html.helper.input;

public class DocviewAudio_Regression {

	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	KeywordPage keywordPage;
	DocListPage docListPage;
	SavedSearch savedSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManagement;
	AnnotationLayer annatationLayer;
	RedactionPage redactionPage;
	MiniDocListPage miniDocListpage;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(Method testMethod) throws ParseException, InterruptedException, IOException {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();

	}
	
	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}
	
	/**
	 * @author Baskar
	 * @Description Verify that audio hits should be displayed when documents searched with common terms and different/same 
	 *              threshold navigated to doc view from session search > DocList
	 * @param username
	 * @param password
	 * @param fullName
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51786", enabled = true,dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyPersistentHits_CommonTerms(String username, String password, String fullName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51786");
		baseClass.stepInfo(
				"Verify that audio hits should be displayed when documents searched with common term and different/same "
						+ "threshold navigated to doc view from session search > DocList");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Successfully login as '" + username);

		String searchString = Input.audioSearchString2;
		String audioSearchInput1 = "interview six";

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocListPage docListPage = new DocListPage(driver);
		DocViewPage docViewPage = new DocViewPage(driver);

		// First audio Search
		sessionSearch.audioSearch(searchString, Input.language);
		sessionSearch.getPureHitAddButton().waitAndClick(10);
		baseClass.stepInfo("Doing first search tems");

		// second audio search
		sessionSearch.clickOnNewSearch();
		baseClass.stepInfo("Doing second search tems");
		sessionSearch.audioSearchTwoTimesandAddingTwoPureHits(audioSearchInput1, Input.language);
		sessionSearch.getCurrentPureHitAddBtn().waitAndClick(10);
		baseClass.stepInfo("Navigating to doclist page");
		

		// view All audio Docs in DocList
		sessionSearch.ViewInDocListWithOutPureHit();

		// selecting all documents in DocList
		docListPage.selectingAllDocFromAllPagesAndAllChildren();
		baseClass.stepInfo("Selecting all document from doclist to docview page");

		// view selected documents in DocList
		docListPage.viewSelectedDocumentsInDocView();
		baseClass.stepInfo("Verify that audio hits should be displayed when documents searched with common term and "
				+ "same threshold navigated to doc view from session search > DocList");
		
		// verifying the audio hits and triangular arrow Icon
		baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().Click();
		docViewPage.verifyingThePresenceOfPersistentHit(true, searchString);

		// removing the pure Hits in Selected Result
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.removePureHitsFromSelectedResult();

		// First audio search term with max threshold value
		baseClass.stepInfo("Doing first search tems with maximum thersold");
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearchThreshold(searchString, Input.language, "max");
		sessionSearch.getCurrentPureHitAddBtn().waitAndClick(10);

		// second audio search with same term and min threshold value
		baseClass.stepInfo("Doing first search tems with minimum thersold");
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearchThreshold(audioSearchInput1, Input.language, "min");
		sessionSearch.getCurrentPureHitAddBtn().waitAndClick(10);

		// view All audio Docs in DocList
		sessionSearch.ViewInDocList();

		// selecting all documents in DocList
		docListPage.selectingAllDocuments();
		baseClass.stepInfo("Selecting all document from doclist to docview page from different thersold");

		baseClass.stepInfo("Verifying audio hits  displayed when documents searched with common term and different"
				+ "threshold navigated to doc view from session search > DocList");
		// view selected documents in DocList
		docListPage.viewSelectedDocumentsInDocView();

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().Click();
		docViewPage.verifyingThePresenceOfPersistentHit(true, audioSearchInput1);
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

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DocView_Audio EXECUTED******");
	}
	}

