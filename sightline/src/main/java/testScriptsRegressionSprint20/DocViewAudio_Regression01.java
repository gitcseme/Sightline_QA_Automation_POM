package testScriptsRegressionSprint20;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAudio_Regression01 {

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
	 * @author Vijaya.Rani ModifyDate:29/08/2022 RPMXCON-51111
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify user should be able to download the audio file which is
	 *              viewing in doc view outside of an assignment.
	 */

	@Test(description = "RPMXCON-51111", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyDownloadAudioFileInDocView(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51111");
		baseClass.stepInfo(
				"Verify user should be able to download the audio file which is viewing in doc view outside of an assignment.");

		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.basicContentSearch(Input.oneHourAudio);
		sessionSearch.ViewInDocViews();
		docViewPage.audioDownload();
		baseClass.passedStep("The user able to download audio files successfully.");

		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		File audioFile = new File(home + "/Downloads/.mp3");
		baseClass.passedStep("Audio file is downloaded with the original format.");
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:29/08/2022 RPMXCON-51810
	 * @throws Exception
	 * @Description Verify that when document present in different save searches
	 *              with common term then, should not display repetitive search term
	 *              on persistent hits panel when selecting document from history
	 *              drop down.
	 */
	@Test(description = "RPMXCON-51810", enabled = true, groups = { "regression" })
	public void verifyAudioDocsRepetitiveSearchTermInDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51810");
		baseClass.stepInfo(
				"Verify that when document present in different save searches with common term then, should not display repetitive search term on persistent hits panel when selecting document from history drop down.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		DocListPage docListPage = new DocListPage(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Audio search And Save
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString2, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);

		// Added another Audio search and save
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString3, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);
		// go to doclist page
		sessionSearch.ViewInDocListWithOutPureHit();
		baseClass.stepInfo("Navigate to the Doclist page Successfully");
		// Select all docs and view as docview
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docListPage.getSelectAll().Visible() && docListPage.getSelectAll().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docListPage.getSelectAll());
		docListPage.getSelectAll().waitAndClick(10);
		docListPage.getPopUpOkBtn().Click();
		docListPage.docListToDocView();
		baseClass.stepInfo("Navigate to the DocView page Successfully");

		// Check Display persistanthit
		docviewPage.audioPersistantHitDisplayCheck();
		baseClass.passedStep(
				"eye icon audio hits is displayed and repetitive search term is not Display on persistent hits panel from saved search as term,threshold and language is same for the searches");
		baseClass.stepInfo("Audio Document Select from MiniDocList History DropDown");
		docviewPage.audiodocthruhistorydropdown();
		baseClass.passedStep("Document selected from the history drop down is loaded and repetitive search term on persistent hits panel is not display");
		// logout
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

	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}
