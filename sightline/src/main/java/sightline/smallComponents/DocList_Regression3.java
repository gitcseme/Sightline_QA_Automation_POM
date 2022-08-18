package sightline.smallComponents;

import java.awt.AWTException;
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

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocList_Regression3 {

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
	 * @author Vijaya.Rani ModifyDate:04/07/2022 RPMXCON-54280
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify that When a audio file is being played via Preview
	 *              Document, clicking on close of the preview pop-up should also
	 *              stop the audio file.
	 */
	@Test(description = "RPMXCON-54280", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyPerviewDocAudioFile(String username, String password, String role)
			throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54280");
		baseClass.stepInfo(
				"To verify that When a audio file is being played via Preview Document, clicking on close of the preview pop-up should also stop the audio file.");

		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		// Login As PA
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// Searching audio document with different term
		baseClass.stepInfo("Searching Content documents based on search audio string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocList();

		baseClass.stepInfo("Verify preview Doc list of audio document");
		docList.DoclistPreviewAudio();
		baseClass.passedStep("Click on 'X' icon audio file is stoped and close perviewdocument popup");
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:05/07/2022 RPMXCON-54567
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that Audio Document Preview functionality is working
	 *              proper in Saved Search >> DocList screen.
	 */

	@Test(description = "RPMXCON-54567", enabled = true, groups = { "regression" })
	public void verifyPerviewDocAudioFileFunctionalityWorkProper() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54567");
		baseClass.stepInfo(
				"Verify that Audio Document Preview functionality is working proper in Saved Search >> DocList screen.");

		sessionSearch = new SessionSearch(driver);
		savedsearch = new SavedSearch(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Searching audio document with different term
		baseClass.stepInfo("Searching Content documents based on search audio string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocList();

		baseClass.stepInfo("Verify preview Doc list of audio document");
		driver.waitForPageToBeReady();
		docList.DoclistPreviewAudio();
		baseClass.passedStep("Audio Document Preview functionality are working proper in DocList screen.");
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:05/07/2022 RPMXCON-54526
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Validate onpage filter for EmailAllDomains with any special
	 *              charatcers (,/"/-/_ /) on Communication Explorer Report.
	 */
	@Test(description = "RPMXCON-54526", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterEmailAllDomainWithSpicalCharatcers(String username, String password, String role)
			throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54526");
		baseClass.stepInfo(
				"Validate onpage filter for EmailAllDomains with any special charatcers (,/\"/-/_ /) on Communication Explorer Report.");

		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		// Login As PA
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		driver.waitForPageToBeReady();
		baseClass.stepInfo(
				"Select Author name  From Filter and Applying Filter And Verifying The Name in Email All Domain");
		docList.EmailAllDomainsNameVerificationInDoc();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Select Include and Applying filter In Email All Domain DocList Page");
		docList.EmailAllDomainsNameIncludeVerificationInDoc();
		// Clear Applied filter
		driver.waitForPageToBeReady();
		docList.clearAllAppliedFilters();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Select Exclude and Applying filter In Email All Domain DocList Page");
		docList.EmailAllDomainsNameInExcludeVerificationInDoc();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:06/07/2022 RPMXCON-54528
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Validate Doclist sorting.
	 */
	@Test(description = "RPMXCON-54528", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyDocExplorerUsingSpecialCharacter(String username, String password, String role)
			throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54528");
		baseClass.stepInfo("Validate Doclist sorting.");

		sessionSearch = new SessionSearch(driver);
		savedsearch = new SavedSearch(driver);
		DocListPage docList = new DocListPage(driver);
		docexp = new DocExplorerPage(driver);

		// Login As PA
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();

		docList.verifyingDescendingOrderSortingInColumn();

		baseClass.stepInfo("removing the column in doclist page");
		docList.removeColumn();

		baseClass.stepInfo("Adding column in doclist page");
		docList.addColumn();

		baseClass.stepInfo("Verify Doclist sorting after shuffling the columns");
		docList.sufflingColumnValueInDocListPage();

		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:06/07/2022 RPMXCON-54983
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Doc Explorer: Verify that results should be displayed if
	 *              filtering columns (metadata) value contain special characters
	 *              (angular brackets < > and also other special character).
	 */
	@Test(description = "RPMXCON-54983", enabled = true, groups = { "regression" })
	public void verifyResultDisplayFilteringCloumnsSpecialCharaters() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54983");
		baseClass.stepInfo(
				"Doc Explorer: Verify that results should be displayed if filtering columns (metadata) value contain special characters (angular brackets < > and also other special character).");
		sessionSearch = new SessionSearch(driver);
		savedsearch = new SavedSearch(driver);
		docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// EmailAuthorNameInclude
		baseClass.stepInfo("Select Include and Applying filter In Email Author Name DocExplorer Page");
		docList.EmailAuthorNameVerificationInDocexplorer();

		baseClass.stepInfo("Select Exclude and Applying filter In Email Author Name DocExplorer Page");
		docList.EmailAuthorNameInExcludeVerificationInDoc();
		// Clear Applied filter
		docList.clearAllAppliedFilters();

		baseClass.stepInfo("Select Include and Applying filter In EmailRecipientsName DocExplorer Page");
		docList.EmailRecipientsNameVerificationInDocexplorer();

		baseClass.stepInfo("Select Exclude and Applying filter In EmailRecipientsName DocExplorer Page");
		docList.EmailRecipientsNameVerificationInDocexplorerExlude();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:11/07/2022 RPMXCON-54979
	 * @throws Exception
	 * @Description Doc List: Preview- Verify that on click of the print icon
	 *              success message should be displayed to inform the user that it
	 *              is processed in background task.
	 */

	@Test(description = "RPMXCON-54979", enabled = true, groups = { "regression" })
	public void verifyDocListPerviewAndProcessedInBackgroundTask() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54979");
		baseClass.stepInfo(
				"Doc List: Preview- Verify that on click of the print icon success message should be displayed to inform the user that it is processed in background task.");
		sessionSearch = new SessionSearch(driver);
		savedsearch = new SavedSearch(driver);
		docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);
		DocViewPage docView = new DocViewPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo(" Search meta data DocFile Type");
		sessionSearch.basicSearchWithMetaDataQuery(Input.searchDocFileType, "DocFileType");
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Go to DocList Page");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getViewAllInDocListBtn());
		docView.getViewAllInDocListBtn().waitAndClick(5);
		baseClass.stepInfo("Successfully navigate to DocList Page");

		baseClass.stepInfo("Verify preview Doc list of non audio document");
		docList.DoclistPreviewNonAudio();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify downloded file name consists Previwed doc id as file name");
		docList.VerifyPerviewDocumentAndPrintAndDownload();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:11/07/2022 RPMXCON-54531
	 * @throws Exception
	 * @Description Validate onpage filter for EmailAuthorName and EmailAllDomains
	 *              on DocList page.
	 */
	@Test(description = "RPMXCON-54531", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterEamilAuthorNameAndAllDomainsInDocList(String username, String password, String role)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54531");
		baseClass.stepInfo("Validate onpage filter for EmailAuthorName and EmailAllDomains on DocList page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String emailAuthor = "EmailAuthorName";
		String emailAllDomain = "EmailAllDomains";

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify DocList EmailAuthorName Include and EmailAllDomain Exclude Filter");
		docList.emailValueFirstIncludeAndEmailValueSecondExcludeVerifyInDocList(emailAuthor, emailAllDomain);

		// Clear Applied filter
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Clear the Applied Filters");
		docList.clearAllAppliedFilters();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify DocList EmailAuthorName Exclude  and EmailAllDomain Include Filter");
		docList.emailValueFirstExcludeAndEmailValueSecondIncludeVerifyInDocList(emailAllDomain, emailAuthor);
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

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}
