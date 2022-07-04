package testScriptsRegressionSprint16;

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

	/**
	 * @author Baskar date: 30/06/22 Modified date: NA Modified by: NA Description :
	 *         Verify that when adding redactions to the end of recording should not
	 *         display horizontal scroll bar under the jplayer if file is less than
	 *         1 hour
	 * 
	 */
//	@Test(description = "RPMXCON-52316", enabled = true, groups = { "regression" })
	public void audioRedactionForLessThanHourToEnd() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		String headerName = "RedactionTags";
		int index;

		baseClass.stepInfo("Test case id :RPMXCON-52316");
		baseClass.stepInfo("Verify that when adding redactions to the end of recording should "
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

		boolean readctionPresent = docViewPage.getAudioBytes().isElementAvailable(1);
		softAssertion.assertTrue(readctionPresent);
		baseClass.stepInfo("Reaction tag added to fully in document");
		boolean scrollBar = docViewPage.getAudioZoomBar().isElementAvailable(1);
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
	 * @author Baskar date: 1/07/22 Modified date: NA Modified by: NA Description :
	 *         Verify that when adding redactions to the end of recording should not
	 *         leave some audio unredacted
	 * 
	 */
//	@Test(description = "RPMXCON-52315", enabled = true, groups = { "regression" })
	public void audioRedactionToEnd() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		String headerName = "RedactionTags";
		int index;

		baseClass.stepInfo("Test case id :RPMXCON-52315");
		baseClass.stepInfo("Verify that when adding redactions to the end of recording "
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
	 * @author Baskar date: 1/07/22 Modified date: NA Modified by: NA Description :
	 *         Verify that after deletion of the last saved redaction tag, 'Default
	 *         Redaction Tag' should be selected automatically from audio redaction
	 *         list
	 * 
	 */
//	@Test(description = "RPMXCON-52019", enabled = true, groups = { "regression" })
	public void verifyRedactionAfterDeletion() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		redactionPage = new RedactionPage(driver);
		String RedactName = "Redact" + Utility.dynamicNameAppender();
		String RedactNameTwo = "Redact" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case id :RPMXCON-52019");
		baseClass.stepInfo("Verify that after deletion of the last saved redaction tag, 'Default Redaction Tag' "
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
		docViewPage.audioRedactionUsingAudioRange(RedactName, 1, 2);
		baseClass.stepInfo("Adding the first redaction");

		// adding second redaction
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab_Add());
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(20);
		docViewPage.audioRedactionBasesOnTime(3, 4);
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
		baseClass.textCompareEquals(newdefautTagSelection, RedactName,
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

	/**
	 * @author Baskar date: 4/07/22 Modified date: NA Modified by: NA Description :
	 *         Verify that when ‘Default Redaction Tag’ doesn’t exist then on
	 *         applying audio redaction, the application must automatically select
	 *         the first in the list of redaction tags
	 * 
	 */
	@Test(description = "RPMXCON-52010", enabled = true, groups = { "regression" })
	public void verifyDFRedactionNotExistFirstRedactionShouldSelect() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		redactionPage = new RedactionPage(driver);
		String RedactName = "AA" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case id :RPMXCON-52010");
		baseClass.stepInfo("Verify that when ‘Default Redaction Tag’ doesn’t exist then on applying audio redaction, "
				+ "the application must automatically select the first in the list of redaction tags");

		// Login as USER
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as : " + Input.rmu1FullName);

		redactionPage.AddRedaction(RedactName, Input.rmu1FullName);

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

		// Deleting the default redaction
		redactionPage.DeleteRedaction(Input.defaultRedactionTag);
		baseClass.stepInfo("Deleting the default redaction tag for validation");

		// Launch DocVia via Search
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Validate the default redaction tag
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab_Add());
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(20);

		// Check Default Selection
		String firstDefaultTagSelection = baseClass.getCurrentDropdownValue(docViewPage.getDocview_AudioRedactions());
		baseClass.textCompareEquals(firstDefaultTagSelection, RedactName,
				"In default : Application automatically selecting the first readction in dropdown after deleting DF’",
				"In default : first redaction tag not selected");

		driver.waitForPageToBeReady();
		docViewPage.audioRedactionBasesOnTime(3, 4);
		baseClass.stepInfo("Adding the redaction");

		// select redaction tags
		baseClass.waitForElement(docViewPage.getDocview_AudioRedactions());
		docViewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(RedactName);
		driver.waitForPageToBeReady();

		// click on save button
		docViewPage.getSaveButton().waitAndClick(20);

		// verify success message
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Record added Successfully");
		baseClass.CloseSuccessMsgpopup();
		
		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();
		redactionPage.AddRedaction(Input.defaultRedactionTag, Input.rmu1FullName);
		redactionPage.DeleteRedaction(RedactName);

		softAssertion.assertAll();

		loginPage.logout();

	}
	
	
	/**
	 * @author Baskar date: 4/07/22 Modified date: NA Modified by: NA Description :
	 *         Verify that for PA user after impersonation as RMU/Reviewer should allow to
     *          select only one redaction tag while editing the redaction
	 * 
	 */
	@Test(description = "RPMXCON-52008", enabled = true, groups = { "regression" })
	public void verifyPaUserImpAndEditRedaction() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		redactionPage = new RedactionPage(driver);
		String RedactName = "Readc" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case id :RPMXCON-52008");
		baseClass.stepInfo("Verify that for PA user after impersonation as RMU/Reviewer "
				+ "should allow to select only one redaction tag while editing the redaction");

		// Login as USER
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as : " + Input.pa1userName);
		
		// impersonating to rmu
		baseClass.impersonatePAtoRMU();

		redactionPage.AddRedaction(RedactName, Input.rmu1FullName);

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
		
		docViewPage.audioRedactionUsingAudioRange( Input.defaultRedactionTag, 1, 2);
		
		// Modififying the readction
		docViewPage.getRedactionModify().waitAndClick(5);
		baseClass.stepInfo("Editing the readction tag");

		// select redaction tags
		baseClass.waitForElement(docViewPage.getDocview_AudioRedactions());
		docViewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(RedactName);
		driver.waitForPageToBeReady();
		
		// Check Default Selection
		String modifyReadction = baseClass.getCurrentDropdownValue(docViewPage.getDocview_AudioRedactions());
		baseClass.textCompareEquals(modifyReadction, RedactName, "In default : Modified 'Redaction Tag’ are displaying",
				"In default : invalid redaction tag selected");

		// click on save button
		docViewPage.getSaveButton().waitAndClick(20);

		// verify success message
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Record Updated Successfully");
		baseClass.CloseSuccessMsgpopup();
		
		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();
		redactionPage.DeleteRedaction(RedactName);
		baseClass.passedStep("Pa User can impersonate and edit the redaction tag");

		softAssertion.assertAll();

		loginPage.logout();

	}
	
	/**
	 * @author Baskar date: 4/07/22 Modified date: NA Modified by: NA 
	 * Description : Verify that for DA user after impersonation as RMU/Reviewer should allow 
	 *                to select only one redaction tag while editing the redaction
	 * 
	 */
	@Test(description = "RPMXCON-52006", enabled = true, groups = { "regression" })
	public void verifyDaUserImpAndEditRedaction() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		redactionPage = new RedactionPage(driver);
		String RedactName = "Readc" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case id :RPMXCON-52006");
		baseClass.stepInfo("Verify that for DA user after impersonation as RMU/Reviewer "
				+ "should allow to select only one redaction tag while editing the redaction");

		// Login as USER
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in as : " + Input.da1userName);
		
		// impersonating to rmu
		baseClass.impersonateDAtoRMU();

		redactionPage.AddRedaction(RedactName, Input.rmu1FullName);

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
		
		docViewPage.audioRedactionUsingAudioRange( Input.defaultRedactionTag, 1, 2);
		
		// Modififying the readction
		docViewPage.getRedactionModify().waitAndClick(5);
		baseClass.stepInfo("Editing the readction tag");

		// select redaction tags
		baseClass.waitForElement(docViewPage.getDocview_AudioRedactions());
		docViewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(RedactName);
		driver.waitForPageToBeReady();
		
		// Check Default Selection
		String modifyReadction = baseClass.getCurrentDropdownValue(docViewPage.getDocview_AudioRedactions());
		baseClass.textCompareEquals(modifyReadction, RedactName, "In default : Modified 'Redaction Tag’ are displaying",
				"In default : invalid redaction tag selected");

		// click on save button
		docViewPage.getSaveButton().waitAndClick(20);

		// verify success message
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Record Updated Successfully");
		baseClass.CloseSuccessMsgpopup();
		
		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();
		redactionPage.DeleteRedaction(RedactName);
		baseClass.passedStep("Da User can impersonate and edit the redaction tag");

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
