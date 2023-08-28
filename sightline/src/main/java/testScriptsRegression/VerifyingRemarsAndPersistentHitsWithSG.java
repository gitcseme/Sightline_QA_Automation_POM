package testScriptsRegression;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import automationLibrary.CustomWebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class VerifyingRemarsAndPersistentHitsWithSG {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	ProductionPage page;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	DocViewMetaDataPage docViewMetaDataPage;
	UserManagement userManage;

	String productionname;
	String prefixID = "A_" + Utility.dynamicNameAppender();
	String suffixID = "_P" + Utility.dynamicNameAppender();
	String folder;
	String folder2;
	String tag;

	String namesg1 = "Default_1234" + Utility.dynamicNameAppender();
	String namesg2 = "Default_5678" + Utility.dynamicNameAppender();
	String random = "Test" + Utility.dynamicNameAppender();
	String random1 = random;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
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
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		securityGroupsPage = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Add security group");
		securityGroupsPage.AddSecurityGroup(random1);
		baseClass.CloseSuccessMsgpopup();

		baseClass.stepInfo("Selecting security group");
		securityGroupsPage.selectSecurityGroup(random1);

		baseClass.stepInfo("Selecting default annotation layer from annotation layer table");
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation("Default Annotation Layer");

		baseClass.stepInfo("Selecting default reduction layer from reduction layer table");
		securityGroupsPage.clickOnReductionTagAndSelectReduction("Default Redaction Tag");

		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchText);

		baseClass.stepInfo("Bulk release to security group");
		sessionSearch.bulkRelease(random1);

		baseClass.stepInfo("Edit user by name in Users page");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.assignAccessToSecurityGroups(random1);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		UtilityLog.info("Logged in as User: " + Input.rmu2userName);

		docViewMetaDataPage = new DocViewMetaDataPage(driver);

		docViewMetaDataPage.selectSecurityGroup(random1);
		baseClass.stepInfo("Select security group for RMU");

		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		baseClass.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15, Input.defaultRedactionTag);

		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("print documet and verify document is downloded");
		docViewMetaDataPage.printDocumentAndVerifyDocumentDownloaded();

		baseClass.stepInfo("Verify PDF Document tab opened in new tab");
		docViewMetaDataPage.verifyPDFDocumentNewTabOpened();
// logout as PA		
		loginPage.logout();
	}

	/**
	 * Author : Krishna Created date: NA Modified date: NA Modified by: Krishna
	 * TestCase id : 51990 Documents released to two different security groups and
	 * search with WP To Verify that Persistant hits when same keywords are added to
	 * different SG
	 */

	@Test(description = "RPMXCON-51990",groups={"regression"})
	public void verifyPersistentHitPanelRMU1RMU2() throws Exception {
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		folder = "51990Default_" + Utility.dynamicNameAppender();
		folder2 = "51990DefaultSec_" + Utility.dynamicNameAppender();
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new CustomWebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
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
			assertTrue(true);
			baseClass.passedStep("The persistent hit panel is visible");
		} else {
			baseClass.failedStep("The persistent hit panel is NOT visible");
		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		docViewMetaDataPage.selectSecurityGroup(random1);
		baseClass.stepInfo("RMU2 Assigned to new SG");
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with Work product completed with RMU2");
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		if (docViewRedact.persistantHitToggle().Displayed() && docViewRedact.persistantHitToggle().Enabled()) {
			assertTrue(true);
			baseClass.passedStep("The persistent hit panel is visible");
		} else {
			assertTrue(false);
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna Created date: NA Modified date: NA Modified by: Krishna
	 * TestCase id : 51991 Documents released to two different security groups
	 * Description : To Verify the remarks when same Annotation layer added to
	 * different SG
	 */

	@Test(description = "RPMXCON-51991",groups={"regression"})
	public void verifyTextRemarksRMU1RMU2() throws Exception {

		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
// Selecting Document from Session search	
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new CustomWebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON 51991");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.remarksIcon().Visible() && docViewRedact.remarksIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.remarksIcon().waitAndClick(25);
		wait.until(
				ExpectedConditions.elementToBeClickable(docViewRedact.getDocView_Redactrec_textarea().getWebElement()));
		Thread.sleep(4000);
//Thread sleep added for the page to adjust resolution		
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).clickAndHold()
				.moveByOffset(200, 50).release().build().perform();
		baseClass.stepInfo("text for remarks has been selected");
		actions.moveToElement(docViewRedact.addRemarksBtn().getWebElement());
		actions.click().build().perform();
		actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());
		actions.click();
		actions.sendKeys("Remark by RMU");
		actions.build().perform();
		actions.moveToElement(docViewRedact.saveRemarksBtn().getWebElement());
		actions.click().build().perform();
		if (docViewRedact.deleteRemarksBtn().Displayed() && docViewRedact.deleteRemarksBtn().Enabled()) {
			assertTrue(true);
			baseClass.passedStep("The Remark has been saved by RMU");
		} else {
			assertTrue(false);
		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		baseClass.stepInfo("Test case Id: RPMXCON 51991");
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		docViewMetaDataPage.selectSecurityGroup(random1);
		baseClass.stepInfo("RMU2 Assigned to new SG");
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.remarksIcon().Visible() && docViewRedact.remarksIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.remarksIcon().waitAndClick(25);
		docViewRedact.editRemarksIcon().waitAndClick(5);
		baseClass.stepInfo("Edit remarks icon has been clicked");
		docViewRedact.editRemarksTextArea().waitAndClick(5);
		docViewRedact.editRemarksTextArea().getWebElement().clear();
		actions.sendKeys("Remark edited by RMU");
		actions.build().perform();
		actions.moveToElement(docViewRedact.saveRemarksBtn().getWebElement());
		actions.click().build().perform();
		baseClass.passedStep("Verified Remarks are edited using RMU2 with New SG");
		wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.deleteRemarksBtn().getWebElement()));
		docViewRedact.deleteRemarksBtn().Click();
		docViewRedact.confirmDeleteRemarksBtn().Click();
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
			loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
			LoginPage.clearBrowserCache();
		}
	}

	/**
	 * Author : Krishna Created date: NA Modified date: NA Modified by: Krishna
	 * Afterclass to set the RMU back to default SG and delete the newly created
	 * Security Group
	 */

	@AfterClass(alwaysRun = true)
	public void close() throws Exception {
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("Select security group for RMU");
		docViewMetaDataPage.selectSecurityGroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.deleteSecurityGroups(random1);
		loginPage.logout();
		loginPage.quitBrowser();
	}

}
