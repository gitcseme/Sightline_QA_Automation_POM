package sightline.docviewAudio;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAudio_Regression02 {

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
	 * @author Vijaya.Rani ModifyDate:04/08/2022 RPMXCON-52014
	 * @throws Exception
	 * @Description Verify that when applies audio redaction, the redaction popup
	 *              should automatically select the redaction tag that was last
	 *              applied across user session(s).
	 */
	@Test(description = "RPMXCON-52014", enabled = true, groups = { "regression" })
	public void verifyAudioRedactionPopupAutomaticallySave() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52014");
		baseClass.stepInfo(
				"Verify that when applies audio redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s).");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Navigate to Advance Search Page and search for audio docs
		baseClass.stepInfo("Navigate to Advance Search Page and search for audio docs");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		baseClass.stepInfo(
				"The redaction popup is automatically select the redaction tag that was last applied across user session(s)");
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		baseClass.passedStep("Redaction tag be saved for the audio redaction Successfully");

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:04/08/2022 RPMXCON-52015
	 * @throws Exception
	 * @Description Verify that user should be able to edit an applied audio
	 *              redaction and change the redaction tag that was applied
	 *              automatically.
	 */
	@Test(description = "RPMXCON-52015", enabled = true, groups = { "regression" })
	public void verifyAudioRedactionPopupAutomaticallySaveAndEditTheRedaction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52015");
		baseClass.stepInfo(
				" Verify that user should be able to edit an applied audio redaction and change the redaction tag that was applied automatically.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		RedactionPage redactionPage = new RedactionPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String RedactName = "Redact" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Add Redaction tag
		redactionPage.AddRedaction(RedactName, Input.rmu1FullName);

		// Navigate to Advance Search Page and search for audio docs
		baseClass.stepInfo("Navigate to Advance Search Page and search for audio docs");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// Modififying the readction
		docviewPage.getRedactionModify().waitAndClick(5);
		baseClass.stepInfo("Editing the readction tag");

		// select redaction tags
		baseClass.waitForElement(docviewPage.getDocview_AudioRedactions());
		docviewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(RedactName);
		driver.waitForPageToBeReady();

		// click on save button
		docviewPage.getSaveButton().waitAndClick(5);

		// verify success message
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Record Updated Successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.passedStep("Edited redaction tag is saved  Successfullt for the audio redaction");

		// Audio Redaction Tag deletion
		docviewPage.deleteAudioRedactionTag();
		redactionPage.DeleteRedaction(RedactName);
		softAssertion.assertAll();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:05/08/2022 RPMXCON-52018
	 * @throws Exception
	 * @Description Verify that after deletion of the last saved audio redaction,
	 *              last saved redaction tag should be selected automatically from
	 *              redaction list.
	 */
	@Test(description = "RPMXCON-52018", enabled = true, groups = { "regression" })
	public void verifyAfterDeletionAudioRedactionSelectDefault() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52018");
		baseClass.stepInfo(
				"Verify that after deletion of the last saved audio redaction, last saved redaction tag should be selected automatically from redaction list.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		RedactionPage redactionPage = new RedactionPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String RedactName = "Redact" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Add Redaction tag
		redactionPage.AddRedaction(RedactName, Input.rmu1FullName);

		// Navigate to Advance Search Page and search for audio docs
		baseClass.stepInfo("Navigate to Advance Search Page and search for audio docs");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// Modififying the readction
		docviewPage.getRedactionModify().waitAndClick(5);
		baseClass.stepInfo("Editing the readction tag");

		// select redaction tags
		baseClass.waitForElement(docviewPage.getDocview_AudioRedactions());
		docviewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(RedactName);
		driver.waitForPageToBeReady();

		// click on save button
		docviewPage.getSaveButton().waitAndClick(5);

		// verify success message
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Record Updated Successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.passedStep("Edited redaction tag is saved  Successfullt for the audio redaction");

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// Audio Redaction Tag deletion
		redactionPage.DeleteRedaction(RedactName);
		softAssertion.assertAll();

		baseClass.passedStep("Last saved redaction tag is selected automatically from redaction list");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:05/08/2022 RPMXCON-52017
	 * @throws Exception
	 * @Description Verify that when audio redaction tag is shared in different
	 *              security group then latest tag should be considered as per users
	 *              security group and session.
	 */
	@Test(description = "RPMXCON-52017", enabled = true, groups = { "regression" })
	public void verifyAudioRedactionTagIsDifferentSG() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52017");
		baseClass.stepInfo(
				"Verify that when audio redaction tag is shared in different security group then latest tag should be considered as per users security group and session.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		String securityGroup = "securityGroup" + Utility.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Create security group
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(securityGroup);
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Navigate to Advance Search Page and search for audio docs
		baseClass.stepInfo("Navigate to Advance Search Page and search for audio docs");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		baseClass.stepInfo(
				"Audio file as per selected start and end point redacted is only one redaction tag is allowed selected redaction.");
		baseClass.passedStep("Redaction tag is saved for the audio redaction");

		baseClass.waitForElement(docviewPage.getDocView_MiniDoc_SelectRow(2));
		docviewPage.getDocView_MiniDoc_SelectRow(2).waitAndClick(5);

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		baseClass.passedStep(
				"Latest applied redaction tag is automatically selected from the list as per the logged in users session in SG1");
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Navigate to Advance Search Page and search for audio docs
		baseClass.stepInfo("Navigate to Advance Search Page and search for audio docs");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		baseClass.passedStep(
				"Latest applied redaction tag is automatically selected from the list as per the logged in users session in SG2");
		loginPage.logout();
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sgpage.deleteSecurityGroups(securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:05/08/2022 RPMXCON-52016
	 * @throws Exception
	 * @Description Verify that after editing audio redaction and applies a
	 *              redaction tag to a audio redaction, then this applied redaction
	 *              tag should be considered as the latest redaction tag.
	 */
	@Test(description = "RPMXCON-52016", enabled = true, groups = { "regression" })
	public void verifyAfterEditingAudioRedactionConsiderLatestRedactionTag() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52016");
		baseClass.stepInfo(
				"Verify that after editing audio redaction and applies a redaction tag to a audio redaction, then this applied redaction tag should be considered as the latest redaction tag.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Navigate to Advance Search Page and search for audio docs
		baseClass.stepInfo("Navigate to Advance Search Page and search for audio docs");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// Modififying the readction
		docviewPage.getRedactionModify().waitAndClick(5);
		baseClass.stepInfo("Editing the readction tag");

		// select redaction tags
		baseClass.waitForElement(docviewPage.getDocview_AudioRedactions());
		docviewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(Input.defaultRedactionTag);
		driver.waitForPageToBeReady();

		// click on save button
		docviewPage.getSaveButton().waitAndClick(5);

		// verify success message
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Record Updated Successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.passedStep("Edited redaction tag is saved  Successfullt for the audio redaction");
		baseClass.passedStep(
				"Redaction tag selected after editing a audio redaction is considered as the latest redaction tag and saved for the audio redaction");

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
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}
