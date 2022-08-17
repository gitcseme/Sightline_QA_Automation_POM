package sightline.docviewAudio;


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

public class Docview_Audio_Sprint2Regression {

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
	RedactionPage  redactionPage;
	MiniDocListPage miniDocListpage;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod (alwaysRun = true)
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

	
	
	/**
	 * @author Baskar date: 30/06/22 Modified date: NA Modified by: NA 
	 * Description : Verify that when adding redactions to the end of recording should not 
	 *               display horizontal scroll bar under the jplayer if file is less than 1 hour
	 * 
	 */
	@Test(description = "RPMXCON-52316", enabled = true, groups = { "regression"})
	public void audioRedactionForLessThanHourToEnd() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		String headerName = "RedactionTags";
		int index;

		baseClass.stepInfo("Test case id :RPMXCON-52316");
		baseClass.stepInfo(
				"Verify that when adding redactions to the end of recording should "
				+ "not display horizontal scroll bar under the jplayer if file is less than 1 hour");

		// Login as USER
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as : " + Input.rmu1FullName);

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Validate audio docs eye icon with persistent hits
		docViewPage.fullAudioReduction(Input.defaultRedactionTag);

		index = baseClass.getIndex(docViewPage.getAudioRedactionTableHeader(), headerName);
		
		boolean readctionPresent=docViewPage.getAudioBytes().isElementAvailable(1);
		softAssertion.assertTrue(readctionPresent);
		baseClass.stepInfo("Reaction tag added to fully in document");
		boolean scrollBar=docViewPage.getAudioZoomBar().isElementAvailable(1);
		softAssertion.assertTrue(scrollBar);
		baseClass.passedStep("Horizontal scroll bar not present in less than one hour documents");
		
		// AfterSave Default Selection
		String defautTagSelection = docViewPage.getAudioRedactionColumnValue(index).getText();
		baseClass.textCompareEquals(defautTagSelection, Input.defaultRedactionTag,
				"After Save : â€˜Default Redaction Tagâ€™ is displayed", "After Save : invalid redaction tag selected");
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(5);
		docViewPage.audioRedactionUsingDocTime();
		
		// adding select redaction tags to validate till end
		baseClass.waitForElement(docViewPage.getDocview_AudioRedactions());
		docViewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(Input.defaultRedactionTag);
		driver.waitForPageToBeReady();

		// click on save button
		docViewPage.getSaveButton().waitAndClick(20);
		baseClass.VerifyErrorMessage("Redaction range can not overlap.");
		baseClass.CloseSuccessMsgpopup();
		baseClass.passedStep("Redaction tag added fully without unreadction");
		
		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();
		softAssertion.assertAll();

		loginPage.logout();

	}
	
	/**
	 * @author Baskar date: 1/07/22 Modified date: NA Modified by: NA 
	 * Description : Verify that when adding redactions to the end of recording 
	 *               should not leave some audio unredacted
	 * 
	 */
	@Test(description = "RPMXCON-52315", enabled = true, groups = { "regression"})
	public void audioRedactionToEnd() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		String headerName = "RedactionTags";
		int index;

		baseClass.stepInfo("Test case id :RPMXCON-52315");
		baseClass.stepInfo(
				"Verify that when adding redactions to the end of recording "
				+ "should not leave some audio unredacted");

		// Login as USER
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as : " + Input.rmu1FullName);

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Validate audio docs eye icon with persistent hits
		docViewPage.fullAudioReduction(Input.defaultRedactionTag);

		index = baseClass.getIndex(docViewPage.getAudioRedactionTableHeader(), headerName);
		
		// AfterSave Default Selection
		String defautTagSelection = docViewPage.getAudioRedactionColumnValue(index).getText();
		baseClass.textCompareEquals(defautTagSelection, Input.defaultRedactionTag,
				"After Save : â€˜Default Redaction Tagâ€™ is displayed", "After Save : invalid redaction tag selected");
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(5);
		docViewPage.audioRedactionUsingDocTime();
		
		// adding select redaction tags to validate till end
		baseClass.waitForElement(docViewPage.getDocview_AudioRedactions());
		docViewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(Input.defaultRedactionTag);
		driver.waitForPageToBeReady();

		// click on save button
		docViewPage.getSaveButton().waitAndClick(20);
		baseClass.VerifyErrorMessage("Redaction range can not overlap.");
		baseClass.CloseSuccessMsgpopup();
		baseClass.passedStep("Redaction tag added fully without unreadction");
		
		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();
		softAssertion.assertAll();

		loginPage.logout();

	}
	
	/**
	 * @author Baskar date: 1/07/22 Modified date: NA Modified by: NA 
	 * Description : Verify that after deletion of the last saved redaction tag, 'Default Redaction Tag' 
	 *               should be selected automatically from audio redaction list
	 * 
	 */
	@Test(description = "RPMXCON-52019", enabled = true, groups = { "regression"})
	public void verifyRedactionAfterDeletion() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		redactionPage=new RedactionPage(driver);
		String RedactName = "Redact" + Utility.dynamicNameAppender();
		String RedactNameTwo = "Redact" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case id :RPMXCON-52019");
		baseClass.stepInfo(
				"Verify that after deletion of the last saved redaction tag, 'Default Redaction Tag' "
				+ "should be selected automatically from audio redaction list");

		// Login as USER
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as : " + Input.rmu1FullName);
		
		redactionPage.AddRedaction(RedactName, Input.rmu1FullName);
		redactionPage.AddRedaction(RedactNameTwo, Input.rmu1FullName);

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Validate the default redaction tag
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab_Add());
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(20);
		
		// Check Default Selection
		String defautTagSelection = baseClass.getCurrentDropdownValue(docViewPage.getDocview_AudioRedactions());
		baseClass.textCompareEquals(defautTagSelection, Input.defaultRedactionTag,
				"In default : Application automatically selected the ‘Default Redaction Tag’",
				"In default : invalid redaction tag selected");
		
		// first redaction
		docViewPage.audioRedactionUsingAudioRange(RedactName,1,2);
		baseClass.stepInfo("Adding the first redaction");
		
		// adding second redaction
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab_Add());
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(20);
		docViewPage.audioRedactionBasesOnTime(3,4);
		baseClass.stepInfo("Adding the second redaction");
		
		// select redaction tags
		baseClass.waitForElement(docViewPage.getDocview_AudioRedactions());
		docViewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(RedactNameTwo);
		driver.waitForPageToBeReady();

		// click on save button
		docViewPage.getSaveButton().waitAndClick(20);

		// verify success message
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Record added Successfully");
		baseClass.CloseSuccessMsgpopup();
		
		// logout as rmu
		loginPage.logout();
		
		// login as pa user
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as : " + Input.pa1FullName);
		
		// Deleting Redaction
		redactionPage.DeleteRedaction(RedactNameTwo);
		baseClass.stepInfo("Second added Redaction tag deleted successfully");
		
		// logout as pa
		loginPage.logout();
		
		// Login as Rmu user
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as : " + Input.rmu1FullName);
		
		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Validate the default redaction tag
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab_Add());
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(20);
		
		// Check Default Selection after deletion
		String newdefautTagSelection = baseClass.getCurrentDropdownValue(docViewPage.getDocview_AudioRedactions());
		baseClass.textCompareEquals(newdefautTagSelection,RedactName,
				"In default : Application automatically selected the deleted one before added",
				"In default : invalid redaction tag selected");
		
		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();
		baseClass.waitTime(5);
		docViewPage.deleteAudioRedactionTag();
		baseClass.passedStep("Application automatically selecting the first redaction tag which "
				+ "added after deletion of second redaction");

		softAssertion.assertAll();

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
			// loginPage.logout();
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
