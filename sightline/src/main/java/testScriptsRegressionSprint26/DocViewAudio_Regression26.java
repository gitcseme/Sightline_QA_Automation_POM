package testScriptsRegressionSprint26;

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
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAudio_Regression26 {
	
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;
	DocExplorerPage docExplorer;
	DocListPage docList;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();
		docExplorer = new DocExplorerPage(driver);

	}
	
	
	/**
	 * Author : Mohan date: 11/15/22 Modified date: NA Modified by: NA
	 * Description:Verify navigating to the audio document should bring up the
	 * document in 4 sec and ready for the user to act up on when navigating from
	 * Tally/Sub Tally report
	 */
	@Test(description = "RPMXCON-51528", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentNavigationToDocViewIn4Secs() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51528");
		baseClass.stepInfo(
				"Verify navigating to the audio document should bring up the document in 4 sec and ready for the user to act up on when navigating from Tally/Sub Tally report");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.pa1userName + "'");

		// search for audio docs
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.tallyResults();

		// select tally field and clickon apply button
		TallyPage tallyPage = new TallyPage(driver);
		tallyPage.selectTallyByMetaDataField(Input.dataSourceHeader);
		tallyPage.selectBarChartAndNavigateTo("View in DocView");

		// verify the latency of application
		DocViewPage docViewPage = new DocViewPage(driver);
		docViewPage.loadingCountVerify(4, docViewPage.getDocView_CurrentDocId());

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 11/15/22 Modified date: NA Modified by: NA
	 * Description:Verify that when mini doc list is configured then should display
	 * search term on persistent hits panel
	 */
	@Test(description = "RPMXCON-51852", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyMiniDocListConfiguredDisplaysSearchTermPersistentHitPanel(String username, String password,
			String role) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51852");
		baseClass.stepInfo(
				"Verify that when mini doc list is configured then should display search term on persistent hits panel");

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		String searchName1 = "AudioSearch" + Utility.dynamicNameAppender();

		// create new search group
		SavedSearch saveSearch = new SavedSearch(driver);
		saveSearch.NavigateToSavedSearch();
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, role, "No");

		// save audio search under created node
		SessionSearch session = new SessionSearch(driver);
		session.audioSearch(Input.audioSearchString1, Input.language);
		session.saveSearchInNewNode(searchName1, newNode);

		baseClass.waitForElement(session.getNewSearchButton());
		session.getNewSearchButton().waitAndClick(5);

		// Saving additional search under child node
		session.advancedContentBGSearch(Input.searchString1,1,false);
		session.saveSearchInNodewithChildNode(searchName1, newNode);

		saveSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, newNode);
		saveSearch.docViewFromSS("View in DocView");
		
		MiniDocListPage miniDocListPage = new MiniDocListPage(driver);
		miniDocListPage.fromSavedSearchToSelectWebField();
		
		loginPage.logout();
		

	}
	
	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
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
		System.out.println("**Executed  DocList_Regression26.**");
	}


}
