package testScriptsRegressionSprint20;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.openqa.selenium.Keys;
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

public class DocViewAudio_Regression_20 {

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
	 * Date: 08/23/2022
	 * @Description Verify that application should not hang when the user tries to complete the audio document when audio is playing
	 */
	@Test(description = "RPMXCON-51479", dataProvider = "RmuRev", enabled = true, groups = { "regression" })
	public void verifyAudioDocCompleteWithoutHang(String username, String password) throws Exception {
		
		sessionSearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);

		base.stepInfo("Test case Id: RPMXCON-51479");
		base.stepInfo("Verify that application should not hang when the user tries to complete the audio document when audio is playing");
		
		//inputs
		String comment = "comment"+Utility.dynamicNameAppender();

		// Login As user
		loginPage.loginToSightLine(username, password);
		base.stepInfo("User successfully logged into slightline webpage as with " + username + "");
		
		//view in docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		base.stepInfo(Input.audioSearchString1+" audio document is searched");
		sessionSearch.ViewInDocView();
		
		// playing audio file
		docview.audioPlayPauseIcon().waitAndClick(5);

		// Edit coding form in parent window
		docview.editCodingForm(comment);
		docview.codingFormSaveButton();
		base.VerifySuccessMessage("Applied coding saved successfully");
		base.stepInfo("Audio document is completed successfully and application is not hang when user completes the document when audio is playing  ");
		
		
		base.passedStep("Verified that application should not hang when the user tries to complete the audio document when audio is playing");
		loginPage.logout();

	}
	
	/**
	 * @author Aathith 
	 * Date: 08/23/2022
	 * @Description Verify that application should not hang when the user tries to complete the audio document by clicking complete same as last when audio is playing
	 */
	@Test(description = "RPMXCON-51481", dataProvider = "RmuRev", enabled = true, groups = { "regression" })
	public void verifyAudioDocCompleteWithSaveAsLast(String username, String password) throws Exception {
		
		sessionSearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);

		base.stepInfo("Test case Id: RPMXCON-51481");
		base.stepInfo("Verify that application should not hang when the user tries to complete the audio document by clicking complete same as last when audio is playing");
		
		//inputs
		String comment = "comment"+Utility.dynamicNameAppender();

		// Login As user
		loginPage.loginToSightLine(username, password);
		base.stepInfo("User successfully logged into slightline webpage as with " + username + "");
		
		//view in docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		base.stepInfo(Input.audioSearchString1+" audio document is searched");
		sessionSearch.ViewInDocView();
		
		// playing audio file
		docview.audioPlayPauseIcon().waitAndClick(5);

		// Edit coding form in parent window
		docview.editCodingForm(comment);
		docview.codingFormSaveButton();
		base.CloseSuccessMsgpopup();
		
		driver.waitForPageToBeReady();
		docview.getCodeSameAsLast().waitAndClick(5);
		base.VerifySuccessMessage("Coded as per the coding form for the previous document");
		base.stepInfo("Audio document was completed successfully.Make sure that application wasn't hang when audio document is completed same as last when audio is in playing mode  ");
		
		
		base.passedStep("Verified that application should not hang when the user tries to complete the audio document by clicking complete same as last when audio is playing");
		loginPage.logout();

	}
	
	/**
	 * @author Aathith 
	 * Date: 08/23/2022
	 * @Description When a user submits an audio search with spaces, Sightline treats the space as a keyword separator and returns results for a multi-keyword audio search.
	 */
	@Test(description = "RPMXCON-51612", enabled = true, groups = { "regression" })
	public void verifyKeyWordsWithSpaces() throws Exception {
		
		sessionSearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		soft = new SoftAssert();

		base.stepInfo("Test case Id: RPMXCON-51612");
		base.stepInfo("When a user submits an audio search with spaces, Sightline treats the space as a keyword separator and returns results for a multi-keyword audio search.");
		

		// Login As user
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password );
		base.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1FullName + "");
		
		//view in docview
		sessionSearch.audioSearch("market"+" "+Keys.ENTER+"supply"+" "+Keys.ENTER+"demand", Input.audioLanguage);
		base.stepInfo(Input.audioSearchString1+" audio document is searched");
		sessionSearch.ViewInDocView();
		
		// eye icon
		docview.getEyeIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		soft.assertTrue(base.text("MARKET").isElementAvailable(3), "market");
		soft.assertTrue(base.text("SUPPLY").isElementAvailable(3), "supply");
		soft.assertTrue(base.text("DEMAND").isElementAvailable(3), "demand");
		
		soft.assertAll();
		base.passedStep("When a user submits an audio search with spaces, Sightline treats the space as a keyword separator and returns results for a multi-keyword audio search.");
		loginPage.logout();

	}
	
	/**
	 * @author Aathith 
	 * Date: 08/23/2022
	 * @Description IE11: Verify when mini doc list child window is open, user enters the document number hit the enter key, 
	 * then corresponding audio doc should load in the default view prior to that document navigation is done with <<, <, > and >>
	 */
	@Test(description = "RPMXCON-51631", dataProvider = "PaRmuRev", enabled = true, groups = { "regression" })
	public void verifyDocumentKeysWithCorrespondingAudioDocFromMiniDocList(String username, String password) throws Exception {
		
		sessionSearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		soft = new SoftAssert();

		base.stepInfo("Test case Id: RPMXCON-51631");
		base.stepInfo("IE11: Verify when mini doc list child window is open, user enters the document number "
				+ "hit the enter key, then corresponding audio doc should load in the default view prior to that document "
				+ "navigation is done with <<, <, > and >>");

		// Login As user
		loginPage.loginToSightLine(username, password);
		base.stepInfo("User successfully logged into slightline webpage as with " + username + "");
		
		//view in docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		base.stepInfo(Input.audioSearchString1+" audio document is searched");
		sessionSearch.ViewInDocView();
		
		//verify last doc from child window
		docview.clickGearIconOpenMiniDocList();
		docview.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();
		String currentdocid = docview.getDocViewSelectedDocId().getText().trim();
		docview.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		String lastdocid =docview.getlastDocinMiniDocView().getText().trim();
		driver.waitForPageToBeReady();
		soft.assertEquals(currentdocid,lastdocid);
		base.passedStep("Audio document was loaded as per the clicked document navigation option i.e.  and >> and same document should be selected from mini doc list child window");
		
		docview.switchToNewWindow(1);
		docview.getDocView_NumTextBox().SendKeys("3"+Keys.ENTER);
		driver.waitForPageToBeReady();
		currentdocid = docview.getDocViewSelectedDocId().getText().trim();
		docview.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		String idOnMiniDoc = docview.getMiniDocListData(3,2).getText().trim();
		soft.assertEquals(currentdocid,idOnMiniDoc);
		base.passedStep("Corresponding audio document was load in the default view and same document is selected from mini doc list child windowwas");
		driver.close();
		docview.switchToNewWindow(1);
		
		soft.assertAll();
		base.passedStep("Verified when mini doc list child window is open, user enters the document number hit the enter key, then "
				+ "corresponding audio doc should load in the default view prior to that document navigation is done with <<, <, > and >>");
		loginPage.logout();

	}

	
	@DataProvider(name = "RmuRev")
	public Object[][] userDetails() {
		return new Object[][] { { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
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
