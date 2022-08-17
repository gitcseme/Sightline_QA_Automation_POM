package testScriptsRegressionSprint19;

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

public class DocViewAudio_Regression19 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
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
	DocViewPage docview;
	SoftAssert soft;

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
		base = new BaseClass(driver);
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);

	}
	
	/**
	 * @author Aathith 
	 * Date: 08/17/2022
	 * @Description Verify user can see the metada for the audio files
	 */
	@Test(description = "RPMXCON-51075", dataProvider = "PaRmuRev", enabled = true, groups = { "regression" })
	public void verifyAudioRedactionTagReleasedToSG(String username, String password) throws Exception {
		
		sessionSearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		soft = new SoftAssert();

		base.stepInfo("Test case Id: RPMXCON-51075");
		base.stepInfo("Verify user can see the metada for the audio files");

		// Login As user
		loginPage.loginToSightLine(username, password);
		base.stepInfo("User successfully logged into slightline webpage as with " + username + "");
		
		//view in docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		base.stepInfo(Input.audioSearchString1+" audio document is searched");
		sessionSearch.ViewInDocView();
		base.stepInfo("page is in DocView");
		
		//verify metadata is dispalyed
		soft.assertTrue(docview.getDocView_Analytics_MetaDataPanel().isDisplayed());
		base.passedStep("Metadata was displayed for audio files on audio doc view");
		
		soft.assertAll();
		base.passedStep("Verified user can see the metada for the audio files");
		loginPage.logout();

	}
	
	@DataProvider(name = "PaRmuRev")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1userName, Input.pa1password },
				{ Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
	}
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
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
		System.out.println("******TEST CASES FOR DOCVIEW AUDIO EXECUTED******");

	}
}
