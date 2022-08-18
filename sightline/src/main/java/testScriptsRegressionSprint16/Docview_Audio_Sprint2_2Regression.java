package testScriptsRegressionSprint16;

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

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
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

public class Docview_Audio_Sprint2_2Regression {

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
	@Test(description = "RPMXCON-52316", enabled = true, groups = { "regression" })
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
	@Test(description = "RPMXCON-52315", enabled = true, groups = { "regression" })
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
	@Test(description = "RPMXCON-52019", enabled = true, groups = { "regression" })
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
		docViewPage.audioRedactionUsingAudioRange(RedactName, 0, 1);
		baseClass.stepInfo("Adding the first redaction");

		// adding second redaction
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab_Add());
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(20);
		docViewPage.audioRedactionBasesOnTime(2, 3);
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
	 *         Script commented as it results in deletion of Default redaction Tag
	 * 
	 */
//	@Test(description = "RPMXCON-52010", enabled = true, groups = { "regression" })
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
	 *         Verify that for PA user after impersonation as RMU/Reviewer should
	 *         allow to select only one redaction tag while editing the redaction
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

		docViewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

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
	 * @author Baskar date: 4/07/22 Modified date: NA Modified by: NA Description :
	 *         Verify that for DA user after impersonation as RMU/Reviewer should
	 *         allow to select only one redaction tag while editing the redaction
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

		docViewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

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

	/**
	 * @author Baskar date: 5/07/22 Modified date: NA Modified by: NA Description :
	 *         Verify that persistent hits should be displayed on audio doc view
	 *         when searched with terms using "within" and work product
	 * @throws Exception
	 * 
	 */
	@Test(description = "RPMXCON-51887", enabled = true, groups = { "regression" })
	public void verifyPersistentHitUsingWithinAndWorkProduct() throws Exception {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String saveSearchName = "search" + Utility.dynamicNameAppender();
		List<String> hitPanel = Arrays.asList(Input.audioSearch, Input.audioSearchString1);

		baseClass.stepInfo("Test case id :RPMXCON-51887");
		baseClass.stepInfo(
				"Saved search > Doc View when search is with Metadata & Content search first and then Audio search, hits should be highlighted");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.saveSearch(saveSearchName);
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// combination search using audio and workproduct
		sessionSearch.configureAudioSearchBlock(Input.audioSearchString1, Input.audioSearch, Input.language, 65, "ALL",
				"Within:", "10");
		sessionSearch.getWorkproductBtnC().waitAndClick(5);
		sessionSearch.getSavedSearchResult().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(sessionSearch.getSelectWorkProductSSResults(saveSearchName));
		sessionSearch.getSelectWorkProductSSResults(saveSearchName).waitAndClick(5);
		sessionSearch.getInsertInToQueryBtn().waitAndClick(10);
		driver.scrollPageToTop();
		sessionSearch.getQuerySearchButton().waitAndClick(10);
		if (sessionSearch.getYesQueryAlert().isElementAvailable(8))
			try {
				sessionSearch.getYesQueryAlert().waitAndClick(8);
			} catch (Exception e) {
				// TODO: handle exception
			}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearch.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		sessionSearch.getPureHitsCount().isElementAvailable(10);
		baseClass.waitTime(5);
		int pureHit = Integer.parseInt(sessionSearch.getPureHitsCount().getText());
		baseClass.stepInfo("Navigating to docview page with combination of audio and workproduct");
		sessionSearch.ViewInDocView();

		// validating persistent hit presence using combination
		docViewPage.verifyingAudioPersistantHitPanelWithMoreThanOneSearcTerm(hitPanel);
		baseClass.passedStep("Persistent hit are displaying using combination search");

		loginPage.logout();

	}

	/**
	 * @author Baskar date: 5/07/22 Modified date: NA Modified by: NA
	 * @Description : Verify the after adding 'Reviewer Remark' on Audio document ,
	 *              request 'Add Reviewer Remark' should be complete immediately
	 * @throws Exception
	 * 
	 */
	@Test(description = "RPMXCON-51826", enabled = true, groups = { "regression" })
	public void verifyAddReviewerRemark() throws Exception {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		Map<String, String> datas = new HashMap<String, String>();
		String remark = "Remark" + Utility.dynamicNameAppender();
		int iteration = 1;

		baseClass.stepInfo("Test case id :RPMXCON-51826");
		baseClass.stepInfo("Verify the after adding 'Reviewer Remark' on Audio document , "
				+ "request 'Add Reviewer Remark' should be complete immediately");

		// Login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User landed on the docviiew page");

		// adding remarks and verifying success message
		datas = docViewPage.addRemarkToDocumentsT(iteration, remark, false, "Success");
		baseClass.stepInfo("Record added Successfully");
		docViewPage.deleteExistingRemark();

		// validating request
		boolean reviewerTab = docViewPage.getAdvancedSearchAudioRemarkPlusIcon().isElementAvailable(2);
		softAssertion.assertTrue(reviewerTab);
		baseClass.passedStep("Add reviewer remarks request completed immediately");
		softAssertion.assertAll();

		loginPage.logout();

	}

	/**
	 * Author : Baskar date: 7/07/2022 Modified date: Modified by: Baskar
	 * Description:Verify that when audio file is playing and clicked to apply the
	 * stamp, then waveform should be loaded [Greater than 1 hr audio file]
	 */

	@Test(description = "RPMXCON-51823", enabled = true, groups = { "regression" })
	public void validationStampForOneHourDocs() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51823");
		baseClass.stepInfo("Verify that when audio file is playing and clicked to apply the stamp, "
				+ "then waveform should be loaded [Greater than 1 hr audio file]");
		// Login as Reviewer Manager
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		String assign = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String stampName = "colour" + Utility.dynamicNameAppender();

		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.oneHourAudio);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreationAsPerCf(assign, Input.codingFormName);
		assignmentPage.toggleSaveButton();
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
		driver.waitForPageToBeReady();

		// verifying more than one hour audio docs
		String overAllAudioTime = docViewPage.getDocview_Audio_EndTime().getText();
		String[] splitData = overAllAudioTime.split(":");
		String data = splitData[0].toString();
		System.out.println(data);
		if (Integer.parseInt(data) >= 01) {
			baseClass.stepInfo("Audio docs have more than:" + overAllAudioTime + " hour to check zoom function");
		} else {
			baseClass.failedMessage("Lesser than one hour");
		}

		// playing audio file
		docViewPage.audioPlayPauseIcon().waitAndClick(5);

		// Edit coding form and saving the stamp as pre-requisties
		docViewPage.editCodingForm(comment);
		docViewPage.stampColourSelection(stampName, Input.stampColour);
		baseClass.stepInfo("Document saved successfully");

		// clicking the saved stamp
		docViewPage.lastAppliedStamp(Input.stampColour);
		docViewPage.codingFormSaveButton();

		// verifying waveform
		boolean waveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveform);
		baseClass.passedStep("Waveform is displayed for same document");

		// validating audio is still playing
		boolean audioPlay = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(audioPlay);
		baseClass.stepInfo("Audio button docs are in play mode");

		// checking zoom in function working for more than one hour audio docs
		docViewPage.getAudioDocZoom().waitAndClick(5);
		boolean zoomBar = docViewPage.getAudioZoomBar().Displayed();
		softAssertion.assertTrue(zoomBar);
		baseClass.passedStep("Zoom functionality working for more than one hour of document");

		// validating the after zoom in the document
		// clicking the saved stamp
		docViewPage.lastAppliedStamp(Input.stampColour);
		docViewPage.codingFormSaveButton();

		// verifying waveform after zoom
		boolean waveforms = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveforms);
		baseClass.passedStep("Waveform is displayed for same document after zoom in");

		// validating audio is still playing after zoom
		boolean audioPlays = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(audioPlays);
		baseClass.stepInfo("Audio button docs are in play mode after zoom in");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @author Baskar date: 07/07/22 Modified date: NA Modified by: NA
	 * @Description : Delete any one Remark from the multiple remarks added for
	 *              audio documents
	 * @throws Exception
	 * 
	 */
	@Test(description = "RPMXCON-51674", enabled = true, groups = { "regression" })
	public void verifyDeletedRemarks() throws Exception {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		Map<String, String> datas = new HashMap<String, String>();
		String remarkOne = "RemarkOne" + Utility.dynamicNameAppender();
		String remarkTwo = "RemarkTwo" + Utility.dynamicNameAppender();
		int iteration = 1;
		int iterationOne = 1;

		baseClass.stepInfo("Test case id :RPMXCON-51674");
		baseClass.stepInfo("Delete any one Remark from the multiple remarks added for audio documents");

		// Login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User landed on the docviiew page");

		// adding remarks and verifying success message
		datas = docViewPage.addRemarkToDocumentsT(iteration, remarkOne, false, "Success");
		datas = docViewPage.addContinuousRemark(iterationOne, remarkTwo, "Success");
		baseClass.stepInfo("Record added Successfully");

		// deleting the remarks
		docViewPage.deleteRemark(remarkOne + 0);
		loginPage.logout();

		// Login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User landed on the docview page to verify reamrks text");

		// click on remarks button
		docViewPage.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		boolean radctionFalse = docViewPage.getDeleteRedaction(remarkOne + 0).isElementAvailable(4);
		softAssertion.assertFalse(radctionFalse);
		docViewPage.deleteExistingAudioRedactions();
		softAssertion.assertAll();
		baseClass.passedStep("Deleted reamrks not displaying  in docview page.when user login ");

		loginPage.logout();

	}

	/**
	 * @author Baskar date: 07/07/22 Modified date: NA Modified by: NA
	 * @Description : Update any one Remarks from the multiple remarks to an Audio
	 *              document
	 * @throws Exception
	 * 
	 */
	@Test(description = "RPMXCON-51673", enabled = true, groups = { "regression" })
	public void verifyUpdateRemarks() throws Exception {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		Map<String, String> datas = new HashMap<String, String>();
		String remarkOne = "RemarkOne" + Utility.dynamicNameAppender();
		String remarkTwo = "RemarkTwo" + Utility.dynamicNameAppender();
		String modify = "modify" + Utility.dynamicNameAppender();
		int iteration = 1;
		int iterationOne = 1;

		baseClass.stepInfo("Test case id :RPMXCON-51673");
		baseClass.stepInfo("Update any one Remarks from the multiple remarks to an Audio document");

		// Login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User landed on the docviiew page");

		// adding remarks and verifying success message
		datas = docViewPage.addRemarkToDocumentsT(iteration, remarkOne, false, "Success");
		datas = docViewPage.addContinuousRemark(iterationOne, remarkTwo, "Success");
		baseClass.stepInfo("Record added Successfully");

		// modifying the remarks
		baseClass.waitForElement(docViewPage.getModifyRedaction(remarkOne + 0));
		docViewPage.getModifyRedaction(remarkOne + 0).waitAndClick(5);
		baseClass.waitForElement(docViewPage.getRemarkTextArea());
		docViewPage.getRemarkTextArea().Clear();
		baseClass.waitForElement(docViewPage.getRemarkTextArea());
		docViewPage.getRemarkTextArea().SendKeys(modify);
		baseClass.waitForElement(docViewPage.getSaveEditRemarks());
		docViewPage.getSaveEditRemarks().waitAndClick(5);
		baseClass.VerifySuccessMessage("Record Updated Successfully");
		baseClass.stepInfo("updating the existing remarks");
		loginPage.logout();

		// Login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User landed on the docview page to verify reamrks text after update");

		// click on remarks button
		baseClass.waitForElement(docViewPage.getAdvancedSearchAudioRemarkIcon());
		docViewPage.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		boolean radctionTrue = docViewPage.getDeleteRedaction(modify).isElementAvailable(4);
		softAssertion.assertTrue(radctionTrue);
		baseClass.stepInfo("Updated remarks shows correctlly");
		boolean radctionFalse = docViewPage.getDeleteRedaction(remarkTwo).isElementAvailable(4);
		softAssertion.assertTrue(radctionFalse);
		baseClass.stepInfo("un-Updated remarks displayed as previous one");

		// deleting the remarks
		docViewPage.deleteRemark(remarkTwo);
		docViewPage.deleteRemark(modify);
		softAssertion.assertAll();
		baseClass.passedStep("Updated reamrks displaying in docview page.when user login ");

		loginPage.logout();

	}
	
	/**
	 * Author : Baskar date: 7/07/2022 Modified date: Modified by: Baskar
	 * Description:Verify RMU/Reviewer can listen the audio files in Doc View when 
	 *             redirecting in context of an assignment
	 */

	@Test(description = "RPMXCON-51064", enabled = true, groups = { "regression" })
	public void validatingAudioDocsFromAssignment() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51064");
		baseClass.stepInfo("Verify that when audio file is playing and clicked to apply the stamp, "
				+ "then waveform should be loaded [Greater than 1 hr audio file]");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		String assign = "Assignment" + Utility.dynamicNameAppender();

		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		// search to Assignment creation
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreationAsPerCf(assign, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewerManager();
		
		// impersonating rmu to rev
		baseClass.impersonateRMUtoReviewer();
		
		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assign);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validating the audio file using assignments
		// playing audio file
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		baseClass.stepInfo("User playing the audio file");

		// verifying audio file can listenable
		baseClass.waitForElement(docViewPage.getAudioWaveForm());
		boolean waveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveform);

		// validating audio is still playing
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		boolean audioPlay = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(audioPlay);
		baseClass.passedStep("Audio file playing and user can listenable");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: 8/07/2022 Modified date: Modified by: Baskar
	 * Description:Verify RMU/Reviewer can save coding form, save coding stamp 
	 *              on doc view in an assignment when reviewing the audio file
	 */

	@Test(description = "RPMXCON-48713", enabled = true, groups = { "regression" })
	public void verifyAfterImpAudioFile() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48713");
		baseClass.stepInfo("Verify RMU/Reviewer can save coding form, save coding stamp "
				+ "on doc view in an assignment when reviewing the audio file");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		String assign = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String stampName = "colour" + Utility.dynamicNameAppender();

		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		// search to Assignment creation
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreationAsPerCf(assign, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewerManager();
		
		// impersonating to Rmu to Rev
		baseClass.impersonateRMUtoReviewer();

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assign);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Edit coding form and save using save button
		docViewPage.editCodingForm(comment);
		docViewPage.codingFormSaveButton();
		baseClass.VerifySuccessMessage("Applied coding saved successfully");
		baseClass.stepInfo("Coding form saved successfully for audio file docs ");
		
		// save using stamp colour label
		docViewPage.stampColourSelection(stampName, Input.stampColour);
		baseClass.VerifySuccessMessage("Coding Stamp saved successfully");
		baseClass.stepInfo("Document saved successfully using stamp colour");
		
		// validating the floppy icon for tool tips should be displayed
		baseClass.waitForElement(docViewPage.getCodingStampToolTipIcon(Input.stampColour));
		if (docViewPage.getCodingStampToolTipIcon(Input.stampColour).Displayed()) {
			Actions builder = new Actions(driver.getWebDriver());
			builder.moveToElement(docViewPage.getCodingStampToolTipIcon(Input.stampColour).getWebElement()).build().perform();
			baseClass.passedStep("While doing mouse over tool tip get displayed");
		} else {
			baseClass.failedStep("Tool tip name not displyed");
			System.out.println("Save this coding form as a new stamp not displayed");
		}
		docViewPage.verifySavedStampTooltip(stampName,Input.stampColour);
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: 8/07/2022 Modified date: Modified by: Baskar
	 * Description:Verify RMU/Reviewer can complete the audio file in an assignment
	 */

	@Test(description = "RPMXCON-46858", enabled = true, groups = { "regression" })
	public void verifyAfterImpAudioFileUsingAllOptionInCf() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-46858");
		baseClass.stepInfo("Verify RMU/Reviewer can complete the audio file in an assignment");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		String assign = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String stampName = "colour" + Utility.dynamicNameAppender();
		int size=5;

		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		List<String > docId=new LinkedList<String>();

		// search to Assignment creation
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewerManager();
		
		// impersonating to Rmu to Rev
		baseClass.impersonateRMUtoReviewer();

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assign);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Edit coding form and save using save button
		for (int i = 1; i <=size; i++) {
			driver.waitForPageToBeReady();
			docViewPage.getDocView_Select_MiniDocList_Docs(i).waitAndClick(10);
			String docIdPresent=docViewPage.getVerifyPrincipalDocument().getText();
			docId.add(docIdPresent);
		}
		
		// saving the coding form using complete button
		String firstId=docId.get(0);
		baseClass.waitForElement(docViewPage.getDocView_MiniDoc_SelectDOcId(firstId));
		docViewPage.getDocView_MiniDoc_SelectDOcId(firstId).waitAndClick(5);
		driver.waitForPageToBeReady();
		docViewPage.editCodingForm(comment);
		docViewPage.completeButton();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Document completed successfully");
		baseClass.waitTime(5);
		
		// validating cursor navigating next docs from using complete buton
		baseClass.waitForElement(docViewPage.getVerifyPrincipalDocument());
		String prnComplete=docViewPage.getVerifyPrincipalDocument().getText();
		softAssertion.assertNotEquals(firstId, prnComplete);
		baseClass.stepInfo("Cursor navigated to next document from minidoclist using complete button");
		
		// validating using coding stamp button
		docViewPage.editCodingForm(comment);
		docViewPage.stampColourSelection(stampName, Input.stampColour);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Coding Stamp saved successfully");
		docViewPage.lastAppliedStamp(Input.stampColour);
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		baseClass.stepInfo("Coding Stamp applied successfully");
		
		// verifying cursor navigate to next docs
		baseClass.waitForElement(docViewPage.getVerifyPrincipalDocument());
		String prnStampLast=docViewPage.getVerifyPrincipalDocument().getText();
		softAssertion.assertNotEquals(prnComplete, prnStampLast);
		baseClass.stepInfo("Cursor navigated to next document from minidoclist using stamp last button");
		
		// validating using code same as last button
		docViewPage.clickCodeSameAsLast();
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		baseClass.waitForElement(docViewPage.getVerifyPrincipalDocument());
		String prnCodeSameLast=docViewPage.getVerifyPrincipalDocument().getText();
		softAssertion.assertNotEquals(prnCodeSameLast, prnStampLast);
		baseClass.stepInfo("Cursor navigated to next document from minidoclist using code same as last button");

		// verifying as per preceeding document
		baseClass.waitForElement(docViewPage.getDocView_MiniDoc_SelectDOcId(prnStampLast));
		docViewPage.getDocView_MiniDoc_SelectDOcId(prnStampLast).waitAndClick(5);
		docViewPage.verifyingComments(comment);
		baseClass.passedStep("As per all validation its working successfully");
		softAssertion.assertAll();

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

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DocView_Audio EXECUTED******");
	}
	}

