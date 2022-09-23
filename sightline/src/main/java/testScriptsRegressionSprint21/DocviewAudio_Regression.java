package testScriptsRegressionSprint21;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import akka.japi.Util;
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
	RedactionPage redact;
	MiniDocListPage miniDocListpage;

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

	}

	@DataProvider(name = "RmuRev")
	public Object[][] RmuRev() {
		Object[][] users = {
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	/**
	 * Author : Baskar date: NA Modified date: 20/09/2022 Modified by: Baskar
	 * Description:Verify the Audio document is Saved, request should be complete immediately
	 * 
	 */

	@Test(description = "RPMXCON-51827", dataProvider = "RmuRev", enabled = true, groups = { "regression" })
	public void validateAudioDocsSavedRequest(String userName, String password, String fullName)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51827");
		baseClass.stepInfo("Verify the Audio document is Saved, request should be complete immediately");
		String codingform = "cf"+Utility.dynamicNameAppender();
		// Login as
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as '" + userName + "'");

		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		// search to docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		sessionSearch.ViewInDocView();
		docViewPage.editCodingForm(codingform);
		docViewPage.codingFormSaveButton();
		String actual=docViewPage.getDocument_CommentsTextBox().GetAttribute("value");
		softAssertion.assertEquals(actual, codingform);
		baseClass.stepInfo("Saving the codingform");
		JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
		boolean readyStateComplete = (boolean) jse.executeScript("return document.readyState").toString()
				.equals("complete");
		softAssertion.assertTrue(readyStateComplete);
		baseClass.passedStep("Coding form get saved and page loaded immediateley");
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	
	/**
	 * Author : Baskar date: NA Modified date: 09//2022 Modified by: Baskar
	 * Description:Verify that when audio file is playing and clicked to apply the stamp, 
	 *             then waveform should be loaded [Less than 1 hr audio file]
	 * 
	 */

	@Test(description = "RPMXCON-51822", enabled = true, groups = { "regression" })
	public void validatePlayIconCF() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51822");
		baseClass.stepInfo("Verify that when audio file is playing and clicked to apply the stamp, "
				+ "then waveform should be loaded [Less than 1 hr audio file]");
		
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String stamp = "stamp" + Utility.dynamicNameAppender();
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		docViewPage = new DocViewPage(driver);

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// pre-requisties saving the stamp
		docViewPage.editCodingForm(comment);
		docViewPage.stampColourSelection(stamp, Input.stampSelection);

		// verifying more than one hour audio docs
		String overAllAudioTime = docViewPage.getDocview_Audio_EndTime().getText();
		String[] splitData = overAllAudioTime.split(":");
		String data = splitData[0].toString();
		System.out.println(data);
		if (Integer.parseInt(data) <= 01) {
			baseClass.stepInfo("Audio document duration is lesser than one hour");
		} else {
			baseClass.failedMessage("More than one hour");
		}
		// playing audio file
		baseClass.waitTime(3);
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		
		// click on the saved stamp
		docViewPage.lastAppliedStamp(Input.stampSelection);
		baseClass.waitForElement(docViewPage.getDocument_CommentsTextBox());
		String actual=docViewPage.getDocument_CommentsTextBox().GetAttribute("value");
		softAssertion.assertEquals(actual, comment);
		baseClass.passedStep("Saved value in stamp are retained");
		docViewPage.codingFormSaveButton();
		baseClass.stepInfo("Document saved successfully");
		
		// validation for waveform
		baseClass.waitForElement(docViewPage.getAudioWaveForm());
		boolean waveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveform);
		baseClass.passedStep("Waveform is displayed for same document");

		// validating audio is still playing
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		boolean audioPlay = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(audioPlay);
		baseClass.stepInfo("Audio button docs are in play mode");
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}
	

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
			loginPage.logoutWithoutAssert();
		}
		try {

//			 loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

}
