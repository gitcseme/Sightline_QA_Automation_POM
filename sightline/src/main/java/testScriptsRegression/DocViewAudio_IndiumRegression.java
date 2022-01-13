package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;


public class DocViewAudio_IndiumRegression {
	
	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	KeywordPage keywordPage;
	DocListPage docListPage;
	String keywordsArrayPT[] = { "test" };
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
	}
	
	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws ParseException, InterruptedException, IOException {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
//		docViewPage = new DocViewPage(driver);
//		assignmentPage = new AssignmentsPage(driver);
//		sessionSearch = new SessionSearch(driver);
//		keywordPage = new KeywordPage(driver);
//		docListPage=new DocListPage(driver);
		
	}
	
	
	/**
	 * Author : Baskar date: NA Modified date: 13/01/2022 Modified by: Baskar
	 * Description:Verify user can select and apply code same as this for the audio files
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 01)
	public void validationCodeSameAsAudioDocs() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51077");
		baseClass.stepInfo("Verify user can select and apply code same as this for the audio files");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		String assign = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		// search to Assignment creation
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assign);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// CodingStamp popup verify
		docViewPage.validationAudioDocsCheckMark(comment);

		//logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date:13/01/22 Modified date: NA Modified by: Baskar
	 * @Description : Verify that document navigation should work from audio doc view
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 02)
	public void validateLastNavigationOption() throws Exception {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51482");
		baseClass.stepInfo("Verify that document navigation should work from audio doc view");

		// Login As 
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		// Searching audio document with different term
		baseClass.stepInfo("Searching Content documents based on search audio string");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
        sessionSearch.ViewInDocView();
		baseClass.stepInfo("Open the searched documents in doc view mini list");
		
		// validation navigation option
		docViewPage.verifyThatIsLastDoc();

		// logout
		loginPage.logout();

	}
	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			//loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			//LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}
	

}
