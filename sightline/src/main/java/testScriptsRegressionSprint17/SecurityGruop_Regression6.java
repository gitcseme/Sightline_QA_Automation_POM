package testScriptsRegressionSprint17;

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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DomainDashboard;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SecurityGruop_Regression6 {

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
	 * @author Vijaya.Rani ModifyDate:15/07/2022 RPMXCON-53690
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify that if project admin delete the Tag/Folder/Redaction
	 *              tags then it should removed from Global/Selected list.
	 * 
	 */
	@Test(description = "RPMXCON-53690", enabled = true, groups = { "regression" })
	public void verifyPADeleteTagsFloderRedactionFromGlobal() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-53690");
		baseClass.stepInfo(
				"To verify that if project admin delete the Tag/Folder/Redaction tags then it should removed from Global/Selected list.");

		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		TagsAndFoldersPage tagFloder = new TagsAndFoldersPage(driver);
		sessionSearch = new SessionSearch(driver);
		String tagnameprev = "Tagprev" + Utility.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("Go to Tag and Floder page and create tag");
		tagFloder.CreateTag(tagnameprev, Input.securityGroup);
		System.out.println(tagnameprev);

		baseClass.stepInfo("Navigate to Security Group Page");
		driver.waitForPageToBeReady();
		sgpage.navigateToSecurityGropusPageURL();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getTagsLink());
		sgpage.getTagsLink().waitAndClick(5);

		if (!sgpage.getTagName(tagnameprev).isDisplayed()) {
			baseClass.passedStep("Deleted Tag is Not Displayed In the list");

		} else {
			baseClass.failedStep("Deleted Tag is Displayed In the list");
		}

		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:15/07/2022 RPMXCON-54742
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that if Sys Admin impersonate as RMU, can create and
	 *              generate the new production.
	 * 
	 */
	@Test(description = "RPMXCON-54742", enabled = true, groups = { "regression" })
	public void verifySAImpersonateRMUNewProduction() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54742");
		baseClass.stepInfo("Verify that if Sys Admin impersonate as RMU, can create and generate the new production.");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		baseClass.stepInfo("Impersonate SA to RMU");
		baseClass.impersonateSAtoRMU();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		driver.waitForPageToBeReady();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname,Input.tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:15/07/2022 RPMXCON-54746
	 * @throws Exception
	 * @Description Verify that by default 'Productions' rights should be checked
	 *              for RMU user in Bulk Access Control pop up.
	 * 
	 */
	@Test(description = "RPMXCON-54746", enabled = true, groups = { "regression" })
	public void verifyDefaultProductionsRightsCheckedForRMU() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54746");
		baseClass.stepInfo(
				"Verify that by default 'Productions' rights should be checked for RMU user in Bulk Access Control pop up.");

		userManage = new UserManagement(driver);
		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		baseClass.stepInfo("Select Bulk User Control Apply RMU");
		driver.waitForPageToBeReady();
		userManage.selectRoleBulkUserAccessControl("Review Manager", Input.projectName, Input.securityGroup);

		if (userManage.getBulkProduction().Enabled()) {
			baseClass.passedStep("By Default Productions rights is checked successfullly");
		} else {
			baseClass.failedStep("By Default Productions rights is Not checked");
		}
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Navigate to user Page
		baseClass.stepInfo("Navigate to Users Page");
		driver.waitForPageToBeReady();
		userManage.navigateToUsersPAge();

		baseClass.stepInfo("Select Bulk User Control Apply RMU");
		driver.waitForPageToBeReady();
		userManage.selectRoleBulkUserAccessControlForPA("Review Manager", Input.securityGroup);

		if (userManage.getBulkProduction().Enabled()) {
			baseClass.passedStep("By Default Productions rights is checked successfullly");
		} else {
			baseClass.failedStep("By Default Productions rights is Not checked");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:15/07/2022 RPMXCON-54747
	 * @throws Exception
	 * @Description Verify the 'Productions' rights for RMU user after
	 *              enabling/disabling from Bulk user access control.
	 * 
	 */
	@Test(description = "RPMXCON-54747", enabled = true, groups = { "regression" })
	public void verifyDefaultProductionsRightsDisablingForRMU() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54747");
		baseClass.stepInfo(
				"Verify the 'Productions' rights for RMU user after enabling/disabling from Bulk user access control.");

		userManage = new UserManagement(driver);
		ProductionPage page = new ProductionPage(driver);
		String[] users = { Input.rmu1FullName };
		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		baseClass.stepInfo("Select Bulk User Control Apply RMU");
		driver.waitForPageToBeReady();
		userManage.selectRoleBulkUserAccessControl("Review Manager", Input.projectName, Input.securityGroup);
		if (userManage.getBulkProduction().Enabled()) {
			baseClass.passedStep("By Default Productions rights is checked successfullly");
		} else {
			baseClass.failedStep("By Default Productions rights is Not checked");
		}
		userManage.defaultSelectionCheckboxForAllRole(true, true, false, true, true, true, true, false, false, false,
				false, false, false, false, false);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getDisableRadioBtn());
		userManage.getDisableRadioBtn().waitAndClick(5);
		userManage.selectBulkAccessUsers(users);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getBulkUserSaveBtn());
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		baseClass.passedStep("Success message should be displayed");
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		if (!page.getbtnProductions().isDisplayed()) {
			baseClass.passedStep("Productions menu is not displayed for RMU user");
		} else {
			baseClass.failedStep("Productions menu is displayed for RMU user");
		}
		loginPage.logout();
		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");
		baseClass.stepInfo("Select Bulk User Control Apply RMU");
		driver.waitForPageToBeReady();
		userManage.selectRoleBulkUserAccessControl("Review Manager", Input.projectName, Input.securityGroup);
		baseClass.waitForElement(userManage.getEnableRadioBtn());
		userManage.getEnableRadioBtn().waitAndClick(5);
		userManage.selectBulkAccessUsers(users);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getBulkUserSaveBtn());
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		baseClass.passedStep("Success message should be displayed");
		loginPage.logout();
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		if (page.getbtnProductions().isDisplayed()) {
			baseClass.passedStep("Productions menu is  displayed for RMU user");
		} else {
			baseClass.failedStep("Productions menu is not displayed for RMU user");
		}
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		// Navigate to user Page
		baseClass.stepInfo("Navigate to Users Page");
		driver.waitForPageToBeReady();
		userManage.navigateToUsersPAge();
		baseClass.stepInfo("Select Bulk User Control Apply RMU");
		userManage.selectRoleBulkUserAccessControlForPA("Review Manager", Input.securityGroup);
		if (userManage.getBulkProduction().Enabled()) {
			baseClass.passedStep("By Default Productions rights is checked successfullly");
		} else {
			baseClass.failedStep("By Default Productions rights is Not checked");
		}
		userManage.defaultSelectionCheckboxForAllRole(true, true, false, true, true, true, true, false, false, false,
				false, false, false, false, false);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getDisableRadioBtn());
		userManage.getDisableRadioBtn().waitAndClick(5);
		userManage.selectBulkAccessUsers(users);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getBulkUserSaveBtn());
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		baseClass.passedStep("Success message should be displayed");
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		driver.waitForPageToBeReady();
		if (!page.getbtnProductions().isDisplayed()) {
			baseClass.passedStep("Productions menu is not displayed for RMU user");
		} else {
			baseClass.failedStep("Productions menu is displayed for RMU user");
		}
		loginPage.logout();
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.stepInfo("Select Bulk User Control Apply RMU");
		baseClass.stepInfo("Navigate to Users Page");
		driver.waitForPageToBeReady();
		userManage.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		userManage.selectRoleBulkUserAccessControlForPA("Review Manager", Input.securityGroup);
		baseClass.waitForElement(userManage.getEnableRadioBtn());
		userManage.getEnableRadioBtn().waitAndClick(5);
		userManage.selectBulkAccessUsers(users);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getBulkUserSaveBtn());
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		baseClass.passedStep("Success message should be displayed");
		loginPage.logout();
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		if (page.getbtnProductions().isDisplayed()) {
			baseClass.passedStep("Productions menu is  displayed for RMU user");
		} else {
			baseClass.failedStep("Productions menu is not displayed for RMU user");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:15/07/2022 RPMXCON-54748
	 * @throws Exception
	 * @Description Verify if System Admin assigining user with RMU role ,then user
	 *              can view the Productions by default.
	 * 
	 */
	@Test(description = "RPMXCON-54748", enabled = true, groups = { "regression" })
	public void verifySAAssignRMUProductionByDefalut() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54748");
		baseClass.stepInfo(
				"Verify if System Admin assigining user with RMU role ,then user can view the Productions by default.");

		userManage = new UserManagement(driver);
		ProductionPage page = new ProductionPage(driver);
		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		baseClass.stepInfo("Select Assign User Apply RMU");
		userManage.ProjectSelectionForUser(Input.projectName, Input.rmu1FullName, "Review Manager", "", false, false);
		baseClass.passedStep("User with role is displayed on Assigned users list");

		baseClass.stepInfo("Navigate to users Page");
		driver.waitForPageToBeReady();
		userManage.navigateToUsersPAge();
		userManage.passingUserName(Input.rmu1userName);
		userManage.applyFilter();
		userManage.editLoginUser();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		if (userManage.getEditUserProduction().Enabled()) {
			baseClass.passedStep("By Default Productions rights is checked successfullly");
		} else {
			baseClass.failedStep("By Default Productions rights is Not checked");
		}
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		if (page.getbtnProductions().isDisplayed()) {
			baseClass.passedStep("Productions menu is  displayed for RMU user");
		} else {
			baseClass.failedStep("Productions menu is not displayed for RMU user");
		}
		loginPage.logout();

	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:19/07/2022 RPMXCON-54768
	 * @throws Exception
	 * @Description Verify that as DA user clicking on project should redirect to
	 *              default security group and generate the production successfully.
	 * 
	 */
	@Test(description = "RPMXCON-54768", enabled = true, groups = { "regression" })
	public void verifyDADefaultSecurityGroupAndGenerateproduction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54768");
		baseClass.stepInfo(
				"Verify that as DA user clicking on project should redirect to default security group and generate the production successfully.");

		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		driver.waitForPageToBeReady();
		baseClass.waitForElement(domainDash.getprojectnamelink(Input.projectName));
		domainDash.getprojectnamelink(Input.projectName).waitAndClick(5);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		driver.waitForPageToBeReady();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname,Input.tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:19/07/2022 RPMXCON-54817
	 * @throws Exception
	 * @Description Verify DA user able to navigate back to the Domain Dashboard
	 *              when impersonated as RMU.
	 * 
	 */
	@Test(description = "RPMXCON-54817", enabled = true, groups = { "regression" })
	public void verifyDADropDownClickAnyProjectDefaultSG() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54817");
		baseClass.stepInfo("Verify DA user able to navigate back to the Domain Dashboard when impersonated as RMU.");

		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		baseClass.stepInfo("Impersonate DA to RMU");
		driver.waitForPageToBeReady();
		baseClass.impersonateDAtoRMU();

		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		// validation for back button
		baseClass.stepInfo("Click on back Button");
		driver.Navigate().back();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify last access for domain dashboard ");
		if (domainDash.getDataRefresh_info().isDisplayed()) {
			baseClass.passedStep("It is redirect to last access domain dashboard ");
		} else {
			baseClass.failedStep("It is not redirect to last access domain dashboard");

		}
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
