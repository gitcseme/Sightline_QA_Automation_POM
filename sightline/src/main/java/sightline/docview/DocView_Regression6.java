package sightline.docview;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ManageUsersPage;
import pageFactory.MiniDocListPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;
public class DocView_Regression6 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	DocViewMetaDataPage docViewMetaDataPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;

	String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
	String productionname;
	String prefixID = "A_" + Utility.dynamicNameAppender();
	String suffixID = "_P" + Utility.dynamicNameAppender();
	String folder;
	String folder2;
	String tag;

	String random = "Test657394345" + Utility.dynamicNameAppender();
	String random1 = random ;
	String AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();

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
		loginPage = new LoginPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}

		loginPage.quitBrowser();

	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW/REDACTIONS EXECUTED******");

	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * @TestCase id : 52265 - Verify that uploaded documents should be threaded into
	 *           families.
	 * @Description : Verify that uploaded documents should be threaded into
	 *              families.
	 */
	@Test(description = "RPMXCON-52265", alwaysRun = true, groups = { "regression" }, enabled = true)
	public void verifyUplodedDocumentsShouldBeThearedIntoFamilies() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52265 Sprint 12");
		String metaDataField = "IngestionName";
		String ingestionName = "RPMXCON44861";
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo("#### Verify that uploaded documents should be threaded into families. ####");

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);

		baseClass.selectproject("AutomationAdditionalDataProject");

		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);

		baseClass.stepInfo("Navigate To Session Search Page URL");
		session.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic meta data search");
		session.basicMetaDataSearch(metaDataField, null, ingestionName, null);

		baseClass.stepInfo("Navigate to  DocView page");
		session.ViewInDocView();

		baseClass.stepInfo("Select email threaded id and family threaded id from mini config.");
		docView.selectEmailThreadedIdAndFamilyIdFromMiniCofig();

		baseClass.stepInfo("Get list of same email threaded ids");
		List<String> emailThreadedIds = docView.getListOfSameEmailThreadedIds();

		baseClass.stepInfo("Email threaded ids : " + emailThreadedIds);

		baseClass.stepInfo("Verify thread map ids with email thread ids.");
		docView.verifyThreadMapIdsWithEmailThreadIds(emailThreadedIds);

		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51045 Description :Verify user can not see the keywords
	 * highlighted in context of an assignment when keyword group assigned to the
	 * security group only
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51045", enabled = true, groups = { "regression" })
	public void verifyKeywordsHighlightingWhenUnMappedFromAssignment() throws InterruptedException {

		baseClass = new BaseClass(driver);
		assignPage = new AssignmentsPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		keywordPage = new KeywordPage(driver);

		String assignmentName = "Atestassignment" + Utility.dynamicNameAppender();

		// Pre-requisites
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-51045");
		baseClass.stepInfo("Verify keywords highlighting when keywords are not mapped to the assignment");
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkAssignExisting(assignmentName);
		assignPage.unmappingKeywordsFromAssignment(assignmentName);
		driver.scrollPageToTop();
		assignPage.addReviewerAndDistributeDocs();
		baseClass.waitForElement(assignPage.getAssignmentSaveButton());
		baseClass.waitTillElemetToBeClickable(assignPage.getAssignmentSaveButton());
		assignPage.getAssignmentSaveButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		loginPage.logout();

		// Login as RMU and verify keyword Highlighting
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined as RMU");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.selectAssignmentToViewinDocView(assignmentName);
		baseClass.stepInfo("Select assigned assignment and navigated to docview");
//		Added
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		// click eye icon and verify the highlighting of search term
		docView.clickOnPersistantHitEyeIcon();
		driver.waitForPageToBeReady();

		if (docView.getPersistentToogle().isDisplayed()) {
			baseClass.failedStep("Keywprds are highlighted while checking as RMU");
		} else {
			baseClass.passedStep("Keywords are not highlighted while checking as RMU");
		}
		loginPage.logout();

		// login as reviewer and verify keyword highlighting
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logined as Reviewer");
		assignPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Select assigned assignment and navigated to docview");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		// click eye icon and verify the highlighting of search term
		docView.clickOnPersistantHitEyeIcon();
		driver.waitForPageToBeReady();
		if (docView.getPersistentToogle().isDisplayed()) {
			baseClass.failedStep("Keywprds are highlighted while checking as reviewer");
		} else {
			baseClass.passedStep("Keywords are not highlighted while checking as reviewer");
		}
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 12/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify search term, highlighted keywords should be displayed on
	 * click of the eye icon when redirected to doc view from session search.
	 * 'RPMXCON-51395' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51395", enabled = true, groups = { "regression" })
	public void verifySearchTermHighlightedInEyeIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51395");
		baseClass.stepInfo(
				"Verify search term, highlighted keywords should be displayed on click of the eye icon when redirected to doc view from session search");

		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		keywordPage = new KeywordPage(driver);
		String hitTerms = "Test" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Add keywords
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.AddKeyword(hitTerms, hitTerms);

		// Go to docview via advanced search
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();

		docView.getPersistentHit(hitTerms);
		docView.verifyPersistentHitPanelAndCount(hitTerms);
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Manager with " + Input.pa1userName + "");

		// Go to docview via advanced search
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();

		docView.getPersistentHit(hitTerms);
		docView.verifyPersistentHitPanelAndCount(hitTerms);
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		// Go to docview via advanced search
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();

		docView.getPersistentHit(hitTerms);
		docView.verifyPersistentHitPanelAndCount(hitTerms);
		loginPage.logout();
	}

	@Test(description = "RPMXCON-52233", enabled = true, groups = { "regression" })
	public void printRedactedDocsAsPA() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52233- DocView- Sprint 2");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		DocViewPage docView = new DocViewPage(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
// printing from session search
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();
		docView.verifyDocumentDownload();

// printing from Doclist 
		baseClass.stepInfo("Printing Document from Doc List as PA");
		wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.gotoSearchTab().getWebElement()));
		actions.moveToElement(docViewRedact.gotoSearchTab().getWebElement());
		actions.click().build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.gotoSessionSearch().getWebElement()));
		actions.moveToElement(docViewRedact.gotoSessionSearch().getWebElement());
		actions.click().build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.actionsDropDown().getWebElement()));
		docViewRedact.actionsDropDown().getWebElement().click();
		sessionsearch.viewInDocView();
		docView.verifyDocumentDownload();

// printing from DocExplorer	 
		baseClass.stepInfo("#### Go to Doc Explorer and Press Print Icon  ####");
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docViewRedact.enterDocId("ID00000001");
		sessionsearch.viewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.printIcon().Visible() && docViewRedact.printIcon().Enabled();
			}
		}), Input.wait30);

		docViewRedact.printIcon().waitAndClick(25);
		docView.verifyDocumentDownload();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date:08-09-2021 NA Modified by:
	 * Krishna TestCase id : 52238 -52239 Verify that Project Admin prints the
	 * document without redaction which is released to two different security groups
	 * and redaction added in first security group only with shared annotation layer
	 * Description : To Verify that Project Admin prints the document without
	 * redaction
	 */

@Test(description = "RPMXCON-52238,RPMXCON-52239", groups={"regression"})
	public void VerifyProjectAdminPrintDocWithoutRedaction() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52238, RPMXCON-52239");
		utility = new Utility(driver);
		baseClass.stepInfo("#### To Verify that Project Admin prints the document without redaction ####");
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		securityGroupsPage = new SecurityGroupsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);


		baseClass.stepInfo("Add security group");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(random1);

		
		baseClass.stepInfo("Selecting security group");
//		Added on 11_04
		securityGroupsPage.navigateToSecurityGropusPageURL();
		driver.Navigate().refresh();
		
		driver.waitForPageToBeReady();
		securityGroupsPage.selectSecurityGroup(random1);

		baseClass.stepInfo("Selecting default annotation layer from annotation layer table");
		driver.waitForPageToBeReady();
		securityGroupsPage.assignAnnotationToSG(AnnotationLayerNew);

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Selecting default reduction layer from reduction layer table");
		securityGroupsPage.assignRedactionTagtoSG("Default Redaction Tag");
		securityGroupsPage.assignRedactionTagtoSG("Default Redaction Tag");

		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Basic search with text input");
		sessionSearch.basicContentSearch(Input.searchText);

		baseClass.stepInfo("Bulk release to security group");
		sessionSearch.bulkRelease(random1);

		baseClass.stepInfo("Edit user by name in Users page");
		UserManagement user = new UserManagement(driver);
		user.navigateToUsersPAge();
		user.assignAccessToSecurityGroups(random1, Input.rmu2userName);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		UtilityLog.info("Logged in as User: " + Input.rmu2userName);

		docViewMetaDataPage = new DocViewMetaDataPage(driver);

//		Added on 11_04
		baseClass.selectsecuritygroup(random1);
		baseClass.stepInfo("Select security group for RMU");

		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.viewInDocView();
		
		
//		Added on 11_04	
		baseClass.waitTime(5);

		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		baseClass.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15, Input.defaultRedactionTag);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.addDocsMetCriteriaToActionBoard();
		DocViewPage docView = new DocViewPage(driver);
		baseClass.stepInfo("Verify PDF Document tab opened in new tab");
		docView.verifyDocumentDownload();
//logout as PA		
		loginPage.logout();
	}

	/**
	 * Author : Krishna Created date: NA Modified date: NA Modified by: Krishna
	 * TestCase id : 51990 Documents released to two different security groups and
	 * search with WP To Verify that Persistant hits when same keywords are added to
	 * different SG
	 */

@Test(description = "RPMXCON-51990", groups={"regression"})
	public void verifyPersistentHitPanelRMU1RMU2() throws Exception {
//	Added 
	    securityGroupsPage = new SecurityGroupsPage(driver);
	    loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
	    Reporter.log("Logged in as User: " + Input.pa1userName);

		securityGroupsPage = new SecurityGroupsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		baseClass.stepInfo("Add security group");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(random1);

		
		baseClass.stepInfo("Selecting security group");
//		Added on 11_04
		securityGroupsPage.navigateToSecurityGropusPageURL();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		securityGroupsPage.selectSecurityGroup(random1);

		baseClass.stepInfo("Selecting default annotation layer from annotation layer table");
		driver.waitForPageToBeReady();
		securityGroupsPage.assignAnnotationToSG(AnnotationLayerNew);

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Selecting default reduction layer from reduction layer table");
		securityGroupsPage.assignRedactionTagtoSG("Default Redaction Tag");
		securityGroupsPage.assignRedactionTagtoSG("Default Redaction Tag");


		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Basic search with text input");
		sessionSearch.basicContentSearch(Input.searchText);

		baseClass.stepInfo("Bulk release to security group");
		sessionSearch.bulkRelease(random1);

		baseClass.stepInfo("Edit user by name in Users page");
		UserManagement user = new UserManagement(driver);
		user.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		user.assignAccessToSecurityGroups(random1, Input.rmu2userName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		folder = "51990Default_" + Utility.dynamicNameAppender();
		folder2 = "51990DefaultSec_" + Utility.dynamicNameAppender();
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
//		
		baseClass.stepInfo("Test case Id: RPMXCON 51990");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with keyword completed");
		sessionsearch.bulkFolder(folder);
		baseClass.stepInfo("Search documents released to bulk folder");
		wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.addNewSearchBtn().getWebElement()));
		docViewRedact.addNewSearchBtn().waitAndClick(5);
		wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.clearShoppingCart().getWebElement()));
		docViewRedact.clearShoppingCart().waitAndClick(5);
		sessionsearch.switchToWorkproduct();
		driver.scrollingToBottomofAPage();
		sessionsearch.selectFolderInASwp(folder);
		sessionsearch.serarchWP();
		baseClass.stepInfo("Search with Work product completed");
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		if (docViewRedact.persistantHitToggle().Displayed() && docViewRedact.persistantHitToggle().Enabled()) {

			baseClass.passedStep("The persistent hit panel is visible");
		} else {
			baseClass.failedStep("The persistent hit panel is NOT visible");
		}
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		
		baseClass.selectsecuritygroup(random1);
		
		baseClass.stepInfo("RMU2 Assigned to new SG");
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with Work product completed with RMU2");
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		if (docViewRedact.persistantHitToggle().Displayed() && docViewRedact.persistantHitToggle().Enabled()) {
			baseClass.passedStep("The persistent hit panel is visible");
		} else {
			baseClass.failedStep("The persistent hit panel is NOT visible");
		}
		loginPage.logout();

		// assigning back to default and deleting the created SG

		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		baseClass.stepInfo("Select security group for RMU");
		docViewRedact.selectsecuritygroup("Default Security Group");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		securityGroupsPage.deleteSecurityGroups(random1);
		loginPage.logout();
	}



}
