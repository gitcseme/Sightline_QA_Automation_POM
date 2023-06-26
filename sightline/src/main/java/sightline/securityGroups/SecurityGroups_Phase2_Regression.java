package sightline.securityGroups;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.CommentsPage;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.ProjectPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SecurityGroups_Phase2_Regression {

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
	ProjectPage projectPage;
	ProductionPage page;
	SessionSearch sessionSearch;

	String foldername;
	String tagname;
	String productionname;

	String assignmentName = "AAassignment" + Utility.dynamicNameAppender();

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
		page = new ProductionPage(driver);
		sessionSearch = new SessionSearch(driver);
		
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

	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	/**
	 * @author Vijaya.Rani ModifyDate:25/07/2022 RPMXCON-54819
	 * @throws Exception
	 * @Description Verify that if SAU impersonate as DAU,clicking on project in
	 *              Domain Dashboard, it should take to Default SG and changing the
	 *              project from header drop down should take to Default SG in the
	 *              selected project.
	 * 
	 */
	@Test(description = "RPMXCON-54819", enabled = true, groups = { "regression" })
	public void verifySAImpersonateDASelectProjectDefaultSG() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54819");
		baseClass.stepInfo(
				"Verify that if SAU impersonate as DAU,clicking on project in Domain Dashboard, it should take to Default SG and changing the project from header drop down should take to Default SG in the selected project.");

		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		baseClass.stepInfo("Impersonate SA to DA");
		baseClass.impersonateSAtoDA();

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		baseClass.stepInfo("Select project");
		baseClass.selectproject();
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("Default SG in the selected project is displayed successfully");
		} else {
			baseClass.failedStep("Default SG in the selected project is not display");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:01/08/2022 RPMXCON-54830
	 * @throws Exception
	 * @Description Verify that as DA user, clicking on project should redirect to
	 *              default security group and select the other domain project from
	 *              header drop down in which DAU is Reviewer, ‘back to dashboard’
	 *              should not be available.
	 * 
	 */
	@Test(description = "RPMXCON-54830", enabled = true, groups = { "regression" })
	public void verifyDAClickingAnyProjectIsReviewerHomePageNotAvailableDashboard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54830");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and select the other domain project from header drop down in which DAU is Reviewer, ‘back to dashboard’ should not be available.");
		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify Default Security Group display");
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify Reviewer Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer home page is not displayed ");
		}
		// validation for back button
		baseClass.stepInfo("Click on back Button");
		baseClass.waitTillElemetToBeClickable(sgpage.backToDomain());
		sgpage.backToDomain().waitAndClick(5);

		baseClass.stepInfo("Verify last access for domain dashboard Project not avaliable");
		if (!baseClass.text("Project :").isDisplayed()) {
			baseClass.passedStep(" 'back to dashboard' project header drop down is Not Available ");
		} else {
			baseClass.failedStep(" 'back to dashboard' near project header drop down is available ");
		}
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:01/08/2022 RPMXCON-54823
	 * @throws Exception
	 * @Description Verify that as DA user, clicking on project should redirect to
	 *              default security group and select the other domain project from
	 *              header drop down in which DAU is RMU, clicking on Back to
	 *              dashboard, it should redirect to last access Domain Dashboard
	 *              page.
	 * 
	 */
	@Test(description = "RPMXCON-54823", enabled = true, groups = { "regression" })
	public void verifyDAClickingAnyProjectIsRMUHomePageBackToDomainDashboard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54823");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and select the other domain project from header drop down in which DAU is RMU, clicking on Back to dashboard, it should redirect to last access Domain Dashboard page.");

		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify Default Security Group display");
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify RMU Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer Manager home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer Manager home page is not displayed ");
		}
		// validation for back button
		baseClass.stepInfo("Click on back Button");
		baseClass.waitTillElemetToBeClickable(sgpage.backToDomain());
		sgpage.backToDomain().waitAndClick(5);
		driver.waitForPageToBeReady();
		if (sgpage.daFirstBlock().isDisplayed()) {
			baseClass.passedStep("Navigated back to domain Dashboard home page");
		} else {
			baseClass.failedStep("Not Navigated back to domain admin home page");
		}

		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:25/07/2022 RPMXCON-54832
	 * @throws Exception
	 * @Description Verify that as DA user, clicking on project should redirect to
	 *              DSG and select the project from header dropdown in which DAU is
	 *              RMU assigned to other than default security group, then
	 *              application should take RMU in his assigned Security group.
	 * 
	 */
	@Test(description = "RPMXCON-54832", enabled = true, groups = { "regression" })
	public void verifyDAClickingAnyProjectIsRMUHomePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54832");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to DSG and select the project from header dropdown in which DAU is RMU assigned to other than default security group, then application should take RMU in his assigned Security group.");

		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify Default Security Group display");
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify RMU Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer Manager home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer Manager home page is not displayed ");
		}
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:19/09/2022 RPMXCON-47087
	 * @throws Exception
	 * @Description Verify that newly created annotation layer should be displayed
	 *              though the Project Admin/RMU user who created annotation layer
	 *              is in-active.
	 * 
	 */
	@Test(description = "RPMXCON-47087", enabled = true, groups = { "regression" })
	public void verifyNewlyCreatAnnotationLayerInPA() throws Exception {
		ProjectPage projectPage = new ProjectPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47087");
		baseClass.stepInfo(
				"Verify that newly created annotation layer should be displayed though the Project Admin/RMU user who created annotation layer is in-active.");

		userManage = new UserManagement(driver);
		SoftAssert softassert = new SoftAssert();
		DataSets data = new DataSets(driver);
		AnnotationLayer annotation = new AnnotationLayer(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		String projectName = "PAProject" + Utility.dynamicNameAppender();
		String annoName = "newAnnotation" + Utility.dynamicNameAppender();
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		String firstname = "tempcar";
		String lastname = "consilio";
		String mailId = "tempcar@conilio.com";

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "0");
		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("Go to Manage Annotation Layer Page and Add new Annotation");
		annotation.AddAnnotation(annoName);
		System.out.println(annoName);
		baseClass.passedStep("Annotation layer created successfull under the 'AllSecurityGroups'");

		// Create security group
		baseClass.stepInfo("Go to Manage securityGroup Page and Add new securityGroup");
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(SGname);
		baseClass.stepInfo("new Security group is created");

		driver.waitForPageToBeReady();
		baseClass.stepInfo("seclect created SecurityGroup and Annotationlayer save it");
		sgpage.addAnnotationlayertosg(SGname, annoName);
		baseClass.passedStep("Newly created annotation layer is mapped to the newly created security group");
		loginPage.logout();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.navigateToUsersPAge();
		userManage.createUser(firstname, lastname, "Project Administrator", mailId, null, projectName);
		baseClass.stepInfo("Delete other SA Account In User Page");
		userManage.navigateToUsersPAge();
		userManage.passingUserName(firstname);
		userManage.applyFilter();
		userManage.deleteUser1(projectName);
		baseClass.passedStep("User is de-activated/deleted successfully");

		baseClass.stepInfo("Impersonate SA to RMU");
		baseClass.impersonateSAtoRMU(projectName);

		baseClass.stepInfo("Go to AnnotationLayer Page Check ");
		annotation.navigateToAnnotationLayerPage();
		baseClass.stepInfo("Go to Manage Annotation Layer Page and Add new Annotation");
		softassert.assertTrue(annotation.getAnnotation_Layers().Displayed());
		softassert.assertAll();
		baseClass.passedStep(
				"Newly created annotation layer is displayed though the System Admin user who created the project, annotation layer is in-active");
		loginPage.logout();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.passedStep("User is de-activated/deleted successfully");

		baseClass.stepInfo("Impersonate SA to RMU");
		baseClass.impersonateSAtoRMU(projectName);

		baseClass.stepInfo("Go to AnnotationLayer Page Check ");
		annotation.navigateToAnnotationLayerPage();
		baseClass.stepInfo("Go to Manage Annotation Layer Page and Add new Annotation");
		softassert.assertTrue(annotation.getAnnotation_Layers().Displayed());
		softassert.assertAll();
		baseClass.passedStep(
				"Newly created annotation layer is displayed though the System Admin user who created the project, annotation layer is in-active");
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:19/09/2022 RPMXCON-47086
	 * @throws Exception
	 * @DescriptionVerify that newly created annotation layer should be displayed
	 *                    though the Domain Admin user who created the project,
	 *                    annotation layer is in-active.
	 * 
	 */
	@Test(description = "RPMXCON-47086", enabled = true, groups = { "regression" })
	public void verifyCreatedAnnotationLayerDisplayInDA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47086");
		baseClass.stepInfo(
				"Verify that newly created annotation layer should be displayed though the Domain Admin user who created the project, annotation layer is in-active.");

		userManage = new UserManagement(driver);
		DataSets data = new DataSets(driver);
		SoftAssert softassert = new SoftAssert();
		AnnotationLayer annotation = new AnnotationLayer(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		String projectName = "DAProject" + Utility.dynamicNameAppender();
		String annoName = "newAnnotation" + Utility.dynamicNameAppender();
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		String firstname = "tempcar";
		String lastname = "consilio";
		String mailId = "tempcar@conilio.com";

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "0");
		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);

		baseClass.stepInfo("Impersonate SA to PA");
		baseClass.impersonateSAtoPA();

		baseClass.stepInfo("Go to Manage Annotation Layer Page and Add new Annotation");
		annotation.AddAnnotation(annoName);
		System.out.println(annoName);
		baseClass.passedStep("Annotation layer created successfull under the 'AllSecurityGroups'");

		// Create security group
		baseClass.stepInfo("Go to Manage securityGroup Page and Add new securityGroup");
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(SGname);
		baseClass.stepInfo("new Security group is created");

		driver.waitForPageToBeReady();
		baseClass.stepInfo("seclect created SecurityGroup and Annotationlayer save it");
		sgpage.addAnnotationlayertosg(SGname, annoName);
		baseClass.passedStep("Newly created annotation layer is mapped to the newly created security group");
		loginPage.logout();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.navigateToUsersPAge();
		userManage.createUser(firstname, lastname, "Project Administrator", mailId, null, projectName);
		baseClass.stepInfo("Delete other SA Account In User Page");
		userManage.navigateToUsersPAge();
		userManage.passingUserName(firstname);
		userManage.applyFilter();
		userManage.deleteUser();
		baseClass.passedStep("User is de-activated/deleted successfully");

		baseClass.stepInfo("Impersonate SA to RMU");
		baseClass.impersonateSAtoRMU(projectName);

		baseClass.stepInfo("Go to AnnotationLayer Page Check ");
		annotation.navigateToAnnotationLayerPage();
		baseClass.stepInfo("Go to Manage Annotation Layer Page and Add new Annotation");
		softassert.assertTrue(annotation.getAnnotation_Layers().Displayed());
		softassert.assertAll();
		baseClass.passedStep(
				"Newly created annotation layer is displayed though the System Admin user who created the project, annotation layer is in-active");
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 20/09/2022 TestCase Id:RPMXCON-53909 Description
	 * :verify that warning message is displayed if user release the tag without
	 * selecting any security group.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53909", enabled = true, groups = { "regression" })
	public void verifyReleaseTagWarningMessage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-53909");
		baseClass.stepInfo("Verify warning message for releasing tag without selecting SG");
		String tagName = "tag" + Utility.dynamicNameAppender();
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTag(tagName, Input.securityGroup);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagName);
		baseClass.stepInfo("Verify warning message for releasing tag");
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.verifyWarningMessageForTagOrFolderReleaseWithoutSG("tag", tagName);
		baseClass.passedStep("Warning message displayed when releasing tag without selecting any Security Group");
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 20/09/2022 TestCase Id:RPMXCON-53910 Description
	 * :verify that warning message is displayed if user release the folder without
	 * selecting any security group.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53910", enabled = true, groups = { "regression" })
	public void verifyReleaseFolderWarningMessage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-53910");
		baseClass.stepInfo("Verify warning message for releasing folder without selecting SG");
		String folderName = "folder" + Utility.dynamicNameAppender();
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(folderName, Input.securityGroup);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(folderName);
		baseClass.stepInfo("Verify warning message for releasing folder");
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.verifyWarningMessageForTagOrFolderReleaseWithoutSG("folder", folderName);
		baseClass.stepInfo("Select security group and release");
		tagsAndFolderPage.bulkReleaseFolderToSecurityGroup(Input.securityGroup);
		baseClass.passedStep("Warning message displayed when releasing folder without selecting any Security Group");
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 20/09/2022 TestCase Id:RPMXCON-53674 Description
	 * :Verify that Project Admin can release document for the selected security
	 * group from “Bulk Release" functionality.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53674", enabled = true, groups = { "regression" })
	public void verifyBulkReleaseFunctionality() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-53674");
		baseClass.stepInfo("Verify PA can release document for security group from bulk release");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("perform search and add search result");
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		baseClass.stepInfo("Select security group and perform bulk release");
		sessionSearch.bulkRelease(Input.securityGroup);
		baseClass.passedStep("PA user can release documents for the selected security group");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:21/09/2022 RPMXCON-53908
	 * @throws Exception
	 * @Description To verify that user can release all the documents tagged by that
	 *              tag into a security group.
	 */
	@Test(description = "RPMXCON-53908", enabled = true, groups = { "regression" })
	public void verifyReleaseTheDocsTagWithSG() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53908");
		baseClass.stepInfo(
				"To verify that user can release all the documents tagged by that tag into a security group.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagAndFolder = new TagsAndFoldersPage(driver);
		String tagName = "newTag" + Utility.dynamicNameAppender();
		List<String> securityGroup = new ArrayList<String>();
		securityGroup.add(Input.securityGroup);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkTag(tagName);

		// bulk releasing tag to security group
		driver.waitForPageToBeReady();
		tagAndFolder.navigateToTagsAndFolderPage();
		tagAndFolder.selectallTagRoot();
		tagAndFolder.verifyNodePresent(tagName, true, "Tag");
		driver.scrollPageToTop();
		tagAndFolder.bulkReleaseTag(securityGroup);
		baseClass.stepInfo("Tag Released to Security Group");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:21/09/2022 RPMXCON-53673
	 * @throws Exception
	 * @Description To verify that after deleting the Security Group, RMU or
	 *              Reviewer cannot access the details.
	 */
	@Test(description = "RPMXCON-53673", enabled = true, groups = { "regression" })
	public void verifyDeleteSGCannotAccessInRMU() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53673");
		baseClass.stepInfo(
				"To verify that after deleting the Security Group, RMU or Reviewer cannot access the details.");
		sessionSearch = new SessionSearch(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("navigate To SecurityGroupPage Create and assign user");
		sgpage.createSecurityGroups(SGname);
		docViewRedact.assignAccesstoSGs(SGname, Input.rmu1userName);
		sgpage.deleteSecurityGroups(SGname);
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		baseClass.waitForElement(baseClass.getsgNames());
		baseClass.getsgNames().waitAndClick(5);
		if (!baseClass.getSelectsg(SGname).isDisplayed()) {
			baseClass.passedStep("User cannot able to access the security group");
		} else {
			baseClass.failedStep("User can able to access the security group");
		}
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:21/07/2022 RPMXCON-47083
	 * @throws Exception
	 * @Description Verify that default annotation layer should be displayed though
	 *              the System Admin user who created the project is in-active.
	 * 
	 */
	@Test(description = "RPMXCON-47083", enabled = true, groups = { "regression" })
	public void verifyDefaultAnnotationLayerInSAProject() throws Exception {
		ProjectPage projectPage = new ProjectPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47083");
		baseClass.stepInfo(
				"Verify that default annotation layer should be displayed though the System Admin user who created the project is in-active.");

		userManage = new UserManagement(driver);
		DataSets data = new DataSets(driver);
		AnnotationLayer annotation = new AnnotationLayer(driver);
		String projectName = "PAProject" + Utility.dynamicNameAppender();
		String firstname = "tempcar";
		String lastname = "consilio";
		String mailId = "tempcar@conilio.com";

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "4");
		baseClass.waitTime(10);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);

		baseClass.stepInfo("Impersonate SA to PA");
		baseClass.impersonateSAtoPA(projectName);

		baseClass.stepInfo("Go to Manage Annotation Layer Page Check default Annotation layer");
		annotation.verifyAnnotationLayerInDSG(Input.securityGroup);
		baseClass.passedStep("'Default Annotation Layer' is displayed under 'Default Security Group' successfully");

		baseClass.stepInfo("Impersonate PA to RMU");
		baseClass.impersonatePAtoRMU();

		baseClass.stepInfo("After Impersonate RMU Verify Default AnnotationLayer Display");
		driver.waitForPageToBeReady();
		annotation.navigateToAnnotationLayerPage();
		if (annotation.getDefaultAnnotation_Layers().Displayed()) {
			baseClass.passedStep("'Default Annotation Layer' is displayed successfully");
		} else {
			baseClass.failedStep("'Default Annotation Layer' is not displayed ");
		}
		loginPage.logout();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.navigateToUsersPAge();
		userManage.createUser(firstname, lastname, "Project Administrator", mailId, null, projectName);
		baseClass.stepInfo("Delete other SA Account In User Page");
		userManage.navigateToUsersPAge();
		userManage.passingUserName(firstname);
		userManage.applyFilter();
		userManage.deleteUser1(projectName);
		baseClass.passedStep("User is de-activated/deleted successfully");

		baseClass.stepInfo("Impersonate SA to PA");
		baseClass.impersonateSAtoPA(projectName);

		baseClass.stepInfo("Go to Manage Annotation Layer Page Check default Annotation layer");
		annotation.verifyAnnotationLayerInDSG(Input.securityGroup);
		baseClass.passedStep("'Default Annotation Layer' is displayed under 'Default Security Group' successfully");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:20/07/2022 RPMXCON-47084
	 * @throws Exception
	 * @Description Verify that default annotation layer should be displayed though
	 *              the Domain Admin user who created the project is in-active.
	 * 
	 */
	@Test(description = "RPMXCON-47084", enabled = true, groups = { "regression" })
	public void verifyDefaultAnnotationLayerDisplayInDA() throws Exception {
		ProjectPage projectPage = new ProjectPage(driver); 
		baseClass.stepInfo("Test case Id: RPMXCON-47084");
		baseClass.stepInfo(
				"Verify that default annotation layer should be displayed though the Domain Admin user who created the project is in-active.");

		userManage = new UserManagement(driver);
		DataSets data = new DataSets(driver);
		AnnotationLayer annotation = new AnnotationLayer(driver);
		String projectName = "DAProject" + Utility.dynamicNameAppender();
		String firstname = "tempcar";
		String lastname = "consilio";
		String mailId = "tempcar@conilio.com";

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		driver.waitForPageToBeReady();
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopiedInDA(projectName, Input.projectName, "4");
		baseClass.waitTime(10);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);

		baseClass.stepInfo("Impersonate DA to PA");
		baseClass.impersonateDAtoPA();

		baseClass.stepInfo("Go to Manage Annotation Layer Page Check default Annotation layer");
		annotation.verifyAnnotationLayerInDSG(Input.securityGroup);
		baseClass.passedStep("'Default Annotation Layer' is displayed under 'Default Security Group' successfully");

		baseClass.stepInfo("Impersonate PA to RMU");
		baseClass.impersonatePAtoRMU();

		baseClass.stepInfo("After Impersonate RMU Verify Default AnnotationLayer Display");
		driver.waitForPageToBeReady();
		annotation.navigateToAnnotationLayerPage();
		if (annotation.getDefaultAnnotation_Layers().Displayed()) {
			baseClass.passedStep("'Default Annotation Layer' is displayed successfully");
		} else {
			baseClass.failedStep("'Default Annotation Layer' is not displayed ");
		}
		loginPage.logout();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.navigateToUsersPAge();
		userManage.createUser(firstname, lastname, "Project Administrator", mailId, null, projectName);
		baseClass.stepInfo("Delete other SA Account In User Page");
		userManage.navigateToUsersPAge();
		userManage.passingUserName(firstname);
		userManage.applyFilter();
		userManage.deleteUser1(projectName);
		baseClass.passedStep("User is de-activated/deleted successfully");

		baseClass.stepInfo("Impersonate SA to PA");
		baseClass.impersonateSAtoPA(projectName);

		baseClass.stepInfo("Go to Manage Annotation Layer Page Check default Annotation layer");
		annotation.verifyAnnotationLayerInDSG(Input.securityGroup);
		baseClass.passedStep("'Default Annotation Layer' is displayed under 'Default Security Group' successfully");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:28/07/2022 RPMXCON-54831
	 * @throws Exception
	 * @Description Verify that as DA user,clicking on project should redirect to
	 *              default security group and select the other project from hedaer
	 *              drop down in which DAU is Reviewer,it should take the user to
	 *              Reviewer home page.
	 * 
	 */
	@Test(description = "RPMXCON-54831", enabled = true, groups = { "regression" })
	public void verifyDAClickingAnyProjectIsReviewerHomePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54831");
		baseClass.stepInfo(
				"Verify that as DA user,clicking on project should redirect to default security group and select the other project from hedaer drop down in which DAU is Reviewer,it should take the user to Reviewer home page.");
		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify Default Security Group display");
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify Reviewer Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer home page is not displayed ");
		}
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:28/07/2022 RPMXCON-54868
	 * @throws Exception
	 * @Description After impersonated as PAU in domain project where DAU is PAU and
	 *              select the other domain project from header drop down having DAU
	 *              role, should redirect to the PAU home page for the selected
	 *              project.
	 * 
	 */
	@Test(description = "RPMXCON-54868", enabled = true, groups = { "regression" })
	public void verifyDAAfterImpersonatedPAHomePage() throws Exception {
		ProjectPage projectPage = new ProjectPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-54868");
		baseClass.stepInfo(
				"After impersonated as PAU in domain project where DAU is PAU and select the other domain project from header drop down having DAU role, should redirect to the PAU home page for the selected project.");
		userManage = new UserManagement(driver);
		DataSets data = new DataSets(driver);
		String projectName = "PAProject" + Utility.dynamicNameAppender();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "4");
		baseClass.waitTime(5);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		baseClass.stepInfo("Impersonate DA to PA");
		driver.waitForPageToBeReady();
		baseClass.impersonateDAtoPA();

		driver.waitForPageToBeReady();
		if (data.getDatasetBtn().isDisplayed()) {
			baseClass.passedStep("It is redirect to PAU home page Successfully ");
		} else {
			baseClass.failedStep("verification failed");
		}

		baseClass.stepInfo("Select other Domain project");
		baseClass.selectproject(projectName);

		driver.waitForPageToBeReady();
		if (data.getDatasetBtn().isDisplayed()) {
			baseClass.passedStep("It is redirect to the PAU home page for the selected project Successfully");
		} else {
			baseClass.failedStep("verification failed");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:28/07/2022 RPMXCON-54835
	 * @throws Exception
	 * @DescriptionVerify that after clicking on project,selecting the other project
	 *                    from header in which DAU is Reviewer,it should redirect to
	 *                    Reviewer homepage ,again if selects any project from
	 *                    header ,then it should redirect to reviewer for selected
	 *                    project.
	 * 
	 */
	@Test(description = "RPMXCON-54835", enabled = true, groups = { "regression" })
	public void verifyDAClickingAnyProjectIsPAAsReviewerHomePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54835");
		baseClass.stepInfo(
				"Verify that after clicking on project,selecting the other project from header in which DAU is Reviewer,it should redirect to Reviewer homepage ,again if selects any project from header ,then it should redirect to reviewer for selected project.");
		
		SoftAssert softassert = new SoftAssert();
		AnnotationLayer annotation = new AnnotationLayer(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		
		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		DataSets data = new DataSets(driver);
		String projectName = "PAProject" + Utility.dynamicNameAppender();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "4");
		baseClass.waitTime(5);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify Default Security Group display");
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify Reviewer Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer home page is not displayed ");
		}
		baseClass.stepInfo("Select other Domain project");
		baseClass.selectproject(projectName);

		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify Reviewer Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer home page is not displayed ");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:28/07/2022 RPMXCON-54829
	 * @throws Exception
	 * @Description Verify on clicking on ay Project, DAU should take to Default SG
	 *              in the selected project and select Project (Domain/non-domain)
	 *              from drop down in which DAU is PAU, it should take the Default
	 *              SG of the selected project.
	 * 
	 */
	@Test(description = "RPMXCON-54829", enabled = true, groups = { "regression" })
	public void verifyDAClickingAnyProjectIsPAHomePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54829");
		baseClass.stepInfo(
				"Verify on clicking on ay Project, DAU should take to Default SG in the selected project and select Project (Domain/non-domain) from drop down in which DAU is PAU, it should take the Default SG of the selected project.");
		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		DataSets data = new DataSets(driver);
		
		SoftAssert softassert = new SoftAssert();
		AnnotationLayer annotation = new AnnotationLayer(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		
		String projectName = "PAProject" + Utility.dynamicNameAppender();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "4");
		baseClass.waitTime(5);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify Default Security Group display");
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}

		baseClass.stepInfo("Select other Domain project");
		baseClass.selectproject(projectName);

		driver.waitForPageToBeReady();
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		loginPage.logout();
	}

	/**
	 * @Author Krishna Date: 3/08/2022
	 * @Description : In Manage Security Group- 'Available in Project' list is
	 *              getting populated for WorkProducts and ProjectFields
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54977", enabled = true, groups = { "regression" })
	public void verifySgAvaibleProjectInListProjectFields() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54977");
		baseClass.stepInfo(
				"In Manage Security Group- 'Available in Project' list is getting populated for WorkProducts and ProjectFields");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		ProjectFieldsPage projectFields = new ProjectFieldsPage(driver);
		String random = Input.randomText + String.valueOf(utility.dynamicNameAppender());
		final String random1 = random;
		final String random2 = random;

		baseClass.stepInfo("Navigate To Project Field sPage");
		projectFields.navigateToProjectFieldsPage();
		baseClass.stepInfo("Add a new project field as project administrator");
		projectFields.addProjectField(random1, random2, Input.fldClassification, Input.fldDescription, Input.fieldType,
				Input.fieldLength);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");

		sgpage.selectSecurityGroupAndClickOnProjectFldLink(Input.securityGroup);
		sgpage.verifySelectedProjectField(random1);
		if (sgpage.getSG_GenerateEmailRadioButton(1).isDisplayed()) {
			baseClass.passedStep(
					"Use Project-level Email Inclusive and Email Duplicate Data option button is checked as per the config as expected.");
		} else {
			baseClass.failedStep("Data option button is not expected");
		}
	}

	/**
	 * @Author Krishna Date: 3/08/2022
	 * @Description :Verify that as DA user, clicking on project should redirect to
	 *              default security group and select the other domain project from
	 *              header drop down in which DAU is RMU, on clicking on Back to
	 *              Dashboard shall take the user to the last accessed domain
	 *              dashboard
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54826", enabled = true, groups = { "regression" })
	public void verifyDAClickingProjectWhichRmuBackToDashBoard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54826");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and select the other domain project from header drop down in which DAU is RMU, on clicking on Back to Dashboard shall take the user to the last accessed domain dashboard");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();
		baseClass.stepInfo("verify default security group in selected project");

		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("As user to DSG by default in selected project");
		} else {
			baseClass.failedStep("It is not  to default SG by default ");
		}

		baseClass.waitTillElemetToBeClickable(sgpage.backToDomain());
		sgpage.backToDomain().waitAndClick(5);
		baseClass.waitTime(5);
		if (sgpage.daFirstBlock().isDisplayed()) {
			baseClass.passedStep("Navigated back to user to the last accessed domain dashboard page");
		} else {
			baseClass.failedStep("Not Navigated back to domain home page");
		}
	}

	/**
	 * @Author Krishna Date: 26/07/2022
	 * @Description :Verify that as DA user, clicking on project should redirect to
	 *              default security group and select the non-domain project from
	 *              header drop down where DAU is a RMU , clicking on Back to
	 *              Dashboard user is redirecting to last access Domain Dashboard
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54824", enabled = true, groups = { "regression" })
	public void verifyDAClickingNonDomainProjectWhichRmuBackToDashBoard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54824");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and select the non-domain project from header drop down where DAU is a RMU , clicking on Back to Dashboard user is redirecting to last access Domain Dashboard");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		baseClass.stepInfo("Click on any project");
		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify default security group in selected project in Rmu");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("As user to DSG by default in selected project");
		} else {
			baseClass.failedStep("It is not  to default SG by default ");
		}

		baseClass.waitTillElemetToBeClickable(sgpage.backToDomain());
		sgpage.backToDomain().waitAndClick(5);
		baseClass.waitTime(5);
		if (sgpage.daFirstBlock().isDisplayed()) {
			baseClass.passedStep("Navigated back to last access domain Dashboard home page");
		} else {
			baseClass.failedStep("Not Navigated back to domain admin home page");
		}
		loginPage.logout();

	}

	/**
	 * @author Krishna ModifyDate:05/08/2022 RPMXCON-54822
	 * @throws Exception
	 * @Description Verify that as DA user, clicking on project should redirect to
	 *              default security group and select the other domain project from
	 *              header drop down in which DAU is PAU, clicking on Back to
	 *              Dashboard user is redirecting to last access Domain Dashboard.
	 * 
	 */
	@Test(description = "RPMXCON-54822", enabled = true, groups = { "regression" })
	public void verifyDAClickingAnyProjectIsPAHomePageBackToDomainDashboard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54822");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and select the other domain project from header drop down in which DAU is PAU, clicking on Back to Dashboard user is redirecting to last access Domain Dashboard");
		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DataSets data = new DataSets(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify Default Security Group display");
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify PA Home Page");
		driver.waitForPageToBeReady();
		if (data.getDatasetBtn().isDisplayed()) {
			baseClass.passedStep("It is redirect to PAU home page Successfully ");
		} else {
			baseClass.failedStep("verification failed");
		}
		// validation for back button
		baseClass.stepInfo("Click on back Button");
		baseClass.waitTillElemetToBeClickable(sgpage.backToDomain());
		sgpage.backToDomain().waitAndClick(5);
		driver.waitForPageToBeReady();
		if (sgpage.daFirstBlock().isDisplayed()) {
			baseClass.passedStep("Navigated back to domain Dashboard home page");
		} else {
			baseClass.failedStep("Not Navigated back to domain admin home page");
		}

		loginPage.logout();

	}

	/**
	 * @author Krishna ModifyDate:05/08/2022 RPMXCON-54821
	 * @throws Exception
	 * @Description Verify that as DA user, clicking on project should redirect to
	 *              default security group and select the non-domain project from
	 *              header drop down where DAU is a PAU , clicking on Back to
	 *              Dashboard user is redirecting to last access Domain Dashboard
	 * 
	 */
	@Test(description = "RPMXCON-54821", enabled = true, groups = { "regression" })
	public void verifyDAClickingNonDomainProjectIsPAHomePageBackToDomainDashboard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54821");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and select the non-domain project from header drop down where DAU is a PAU , clicking on Back to Dashboard user is redirecting to last access Domain Dashboard");
		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DataSets data = new DataSets(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify Default Security Group display");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify PA Home Page");
		driver.waitForPageToBeReady();
		if (data.getDatasetBtn().isDisplayed()) {
			baseClass.passedStep("It is redirect to PAU home page Successfully ");
		} else {
			baseClass.failedStep("verification failed");
		}
		// validation for back button
		baseClass.stepInfo("Click on back Button");
		baseClass.waitTillElemetToBeClickable(sgpage.backToDomain());
		sgpage.backToDomain().waitAndClick(5);
		driver.waitForPageToBeReady();
		if (sgpage.daFirstBlock().isDisplayed()) {
			baseClass.passedStep("Navigated back to domain Dashboard home page");
		} else {
			baseClass.failedStep("Not Navigated back to domain admin home page");
		}
		loginPage.logout();
	}

	/**
	 * @Author Vijaya.Rani ModifyDate:01/08/2022 RPMXCON-54825
	 * @Description :Verify that as DA user, clicking on project should redirect to
	 *              default security group and select other domain project from
	 *              header drop down, clicking on Back to Dashboard, it should
	 *              redirect to other Domain Dashboard page
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54825", enabled = true, groups = { "regression" })
	public void verifyDAClickingOtherDomainProjectWhichRmuBackToDashBoard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54825");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and select other domain project from header drop down, clicking on Back to Dashboard, it should redirect to other Domain Dashboard page");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify default security group in selected project");
		driver.waitForPageToBeReady();
		baseClass.selectproject(Input.projectName);
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("As user to DSG by default in selected project");
		} else {
			baseClass.failedStep("It is not  to default SG by default ");
		}

		baseClass.waitTillElemetToBeClickable(sgpage.backToDomain());
		sgpage.backToDomain().waitAndClick(5);
		baseClass.waitTime(5);
		if (sgpage.daFirstBlock().isDisplayed()) {
			baseClass.passedStep("Navigated back to domain Dashboard home page");
		} else {
			baseClass.failedStep("Not Navigated back to domain admin home page");
		}
		loginPage.logout();

	}

	/**
	 * @author:Iyappan.Kasinathan date: NA Modified date: NA Modified by: NA
	 *                            TestCase ID: RPMXCON-54808
	 * @throws Exception
	 * @Description Verify that when SysAdmin impersonate as DAU,clicking on any
	 *              project , should take to the Default SG in the project
	 */
	@Test(description = "RPMXCON-54808", enabled = true, groups = { "regression" })
	public void verifySAImpersonateAsDAUTakeDsgInTheProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54808");
		baseClass.stepInfo(
				"Verify that when SysAdmin impersonate as DAU,clicking on any  project , should take to the Default SG in the project");
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login as PA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.sa1userName + "");
		baseClass.stepInfo("Impersonate SA to DA");
		baseClass.impersonateSAtoDA();

		// go to project
		baseClass.stepInfo("Click on any project from dashboard");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		// verify Default security group in selected project
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("Default SG in the selected project is displayed successfully");
		} else {
			baseClass.failedStep("Default SG in the selected project is not display");
		}
		loginPage.logout();
	}

	/**
	 * @author:Iyappan.Kasinathan date: NA Modified date: NA Modified by: NA
	 *                            TestCase ID: RPMXCON-54807
	 * @throws Exception
	 * @Description Verify that in Domain Dashboard page, clicking on project from
	 *              'Active Projects Utilization' widgets, should take the DAU to
	 *              Default SG in the project
	 */
	@Test(description = "RPMXCON-54807", enabled = true, groups = { "regression" })
	public void verifyDomainDashBoardPageClickedOnProjectWidgetsTakeDAUDsg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54807");
		baseClass.stepInfo(
				"Verify that in Domain Dashboard page, clicking on project from 'Active Projects Utilization' widgets, should take the DAU to Default SG in the project");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);

		// Login as DAU
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.da1userName + "");
		baseClass.selectdomain(Input.domainName);
		WebElement mytable1 = sgpage.daSecondBlock().getWebElement();
		List<WebElement> rows_table = mytable1.findElements(By.tagName("tr"));

		// To calculate no of rows In table.
		int rows_count1 = rows_table.size();
		System.out.println(rows_count1);
		baseClass.waitTillElemetToBeClickable(sgpage.daSecondTabledata(rows_count1 - 1));
		sgpage.daSecondTabledata(rows_count1 - 1).waitAndClick(5);
		baseClass.stepInfo("Project cliked on from 'Active Projects utilization' widget successfully ");

		// verify Default security group in selected project
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("Redirect to Default SG in the selected project is displayed successfully");
		} else {
			baseClass.failedStep("Default SG in the selected project is not display");
		}
		loginPage.logout();
	}

	/**
	 * @author:Iyappan.Kasinathan date: NA Modified date: NA Modified by: NA
	 *                            TestCase ID: RPMXCON-54789
	 * @throws Exception
	 * @Description Verify that when RMU changes the Project from header drop
	 *              down,it should take to Default SG of the selected project
	 */
	@Test(description = "RPMXCON-54789", enabled = true, groups = { "regression" })
	public void verifyRMUChangesProjectTakeDsgSelectedProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54789");
		baseClass.stepInfo(
				"Verify that when RMU changes the Project from header drop down,it should take to Default SG of the selected project");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		baseClass.selectproject();
		baseClass.stepInfo("Project selected to Rmu header dropdown");
		DomainDashboard domainDash = new DomainDashboard(driver);

		// verify RMU home page
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify Reviewer manager Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(
					" User navigated to Reviewer manager home page is displayed in selected project successfully");
		} else {
			baseClass.failedStep("Reviewer home page is not displayed ");
		}
		loginPage.logout();

	}

	/**
	 * @author:Iyappan.Kasinathan date: NA Modified date: NA Modified by: NA
	 *                            TestCase ID: RPMXCON-54788
	 * @throws Exception
	 * @Description Verify that if PAU changes the Project from header drop down
	 *              then it should take the corresponding project as a PAU only
	 */
	@Test(description = "RPMXCON-54788", enabled = true, groups = { "regression" })
	public void verifyPAUChangesProjectHeaderFropDownTakeCorrespondingProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54788");
		baseClass.stepInfo(
				"Verify that if PAU changes the Project from header drop down then it should take the corresponding project as a PAU only");
		DataSets data = new DataSets(driver);

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject();
		baseClass.stepInfo("Project selected to PA header dropdown");

		// verify PAU home page
		driver.waitForPageToBeReady();
		if (data.getDatasetBtn().isDisplayed()) {
			baseClass.passedStep(" User redirect to PA home page is displayed in selected project successfully");
		} else {
			baseClass.failedStep("verification failed");
		}
		loginPage.logout();

	}

	/**
	 * @author:Iyappan.Kasinathan date: NA Modified date: NA Modified by: NA
	 *                            TestCase ID: RPMXCON-54778
	 * @throws Exception
	 * @Description Verify that if DAU user impersonate as RMU,and change the
	 *              Project from header drop down should take to Default SG in the
	 *              selected project
	 */
	@Test(description = "RPMXCON-54778", enabled = true, groups = { "regression" })
	public void verifyDAUImpersonateRmuChangeProjectDsgSelectProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54778");
		baseClass.stepInfo(
				"Verify that if DAU user impersonate as RMU,and change the Project from header drop down should take to Default SG in the selected project");
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login as DAU
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		baseClass.impersonateDAtoRMU();
		baseClass.selectproject();

		// verify
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify Reviewer manager Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass
					.passedStep("Redirect to Reviewer manager home page is displayed in selected project successfully");
		} else {
			baseClass.failedStep("Reviewer home page is not displayed ");
		}
		loginPage.logout();

	}

	/**
	 * @author:Iyappan.Kasinathan date: NA Modified date: NA Modified by: NA
	 *                            TestCase ID: RPMXCON-54779
	 * @throws Exception
	 * @Description Verify thatif DAU user impersonate as RMU,and changes the SG in
	 *              SG Dropdown then it should take the corresponding SG in the same
	 *              project
	 */
	@Test(description = "RPMXCON-54779", enabled = true, groups = { "regression" })
	public void verifyDAUUserImpersonateRmuChangesSgInSameProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54779");
		baseClass.stepInfo(
				"Verify that if DAU user impersonate as RMU,and change the Project from header drop down should take to Default SG in the selected project");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		String sgName = "Security Group1_" + UtilityLog.dynamicNameAppender();
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		DocViewMetaDataPage docViewMetaData = new DocViewMetaDataPage(driver);

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Create security groups and assign");
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(sgName);
		docViewRedact.assignAccesstoSGs(sgName, Input.rmu1userName);
		loginPage.logout();

		// Login as DAU
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		baseClass.impersonateDAtoRMU();

		// Verify corresponding SG
		driver.waitForPageToBeReady();
		docViewMetaData.getSecurityGroupDropDownButton().waitAndClick(3);
		baseClass.waitForElement(docViewMetaData.getSecurityGroup(sgName));
		if (docViewMetaData.getSecurityGroup(sgName).isElementPresent()) {
			baseClass.passedStep(sgName + ".. is successfully displayed in selected project");
		} else {
			baseClass.failedStep("Sg is not displayed");

		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sgpage.deleteSecurityGroups(sgName);
		loginPage.logout();
	}

	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                 RPMXCON-54787
	 * @throws Exception
	 * @Description Verify that when PAU impersonate as RMU,and changes the SG in SG
	 *              Dropdown then it should take the corresponding SG in the same
	 *              project
	 */
	@Test(description = "RPMXCON-54787", enabled = true, groups = { "regression" })
	public void verifyPAUUserImpersonateRmuChangesSgTakeCorrospondingSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54787");
		baseClass.stepInfo(
				"Verify that when PAU impersonate as RMU,and changes the SG in SG Dropdown then it should take the corresponding SG in the same project");
		DocViewMetaDataPage docViewMetaData = new DocViewMetaDataPage(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		String sgName = "Security Group1_" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Create security groups and assign");
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(sgName);
		docViewRedact.assignAccesstoSGs(sgName, Input.rmu1userName);
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		baseClass.impersonatePAtoRMU();

		// verify RMU home page
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify Reviewer manager Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass
					.passedStep("Redirect to Reviewer manager home page is displayed in selected project successfully");
		} else {
			baseClass.failedStep("Reviewer home page is not displayed ");
		}

		// verify corresponding SG
		driver.waitForPageToBeReady();
		docViewMetaData.getSecurityGroupDropDownButton().waitAndClick(3);
		baseClass.waitForElement(docViewMetaData.getSecurityGroup(sgName));
		if (docViewMetaData.getSecurityGroup(sgName).isElementPresent()) {
			baseClass.passedStep("Corresponding SG in the same project is displayed successfuly");
		} else {
			baseClass.failedStep("Sg is not displayed");

		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sgpage.deleteSecurityGroups(sgName);
		loginPage.logout();
	}

	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                 RPMXCON-54319
	 * @throws Exception
	 * @Description To verify that if user select SG level attribute and click on
	 *              Save, "belly band"message should be displayed
	 */
	@Test(description = "RPMXCON-54319", enabled = true, groups = { "regression" })
	public void verifyBellyBandWarningMsgDisplayClickingRegenrateBtn() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54319");
		baseClass.stepInfo(
				"To verify that if user select SG level attribute and click on Save, \"belly band\"message should be displayed");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		SoftAssert softassert = new SoftAssert();
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		sgpage.createSecurityGroups(SGname);
		baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(2));
		sgpage.getSG_GenerateEmailRadioButton(2).waitAndClick(5);
		baseClass.stepInfo("Use Security Group-Specific Email inclusive check mark is selected");
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(5);
		driver.waitForPageToBeReady();

		// verify warning message
		String errormsg = "Use Security Group-Specific Email Inclusive and Email Duplicate data";
		baseClass.waitForElement(sgpage.getVerifySG_EmailGenerateWarningMsg());
		String Actualwarningmsg = sgpage.getVerifySG_EmailGenerateWarningMsg().getText();
		baseClass.stepInfo("Actual Warning message...." + Actualwarningmsg);
		String expectWarningmsg = sgpage.getVerifySG_EmailGenerateWarningMsgText(errormsg).getText();
		baseClass.stepInfo("Expected warning message..." + expectWarningmsg);
		softassert.assertEquals(Actualwarningmsg, expectWarningmsg);
		driver.waitForPageToBeReady();
		if (Actualwarningmsg.contains(expectWarningmsg)) {
			baseClass.passedStep("Warning message is displayed successfully");
		} else {
			baseClass.failedStep("Warning msg is not displayed");
		}
		softassert.assertAll();
		sgpage.deleteSecurityGroups(SGname);
	}

	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                 RPMXCON-54320
	 * @throws Exception
	 * @Description To verify that when PA, unrelease any documents from a Security
	 *              Group then belly band" reminder should be displayed
	 */
	@Test(description = "RPMXCON-54320", enabled = true, groups = { "regression" })
	public void verifyPAUnReleaseDocsFromSgBellyBandMsgDisplayed() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54320");
		baseClass.stepInfo(
				"To verify that when PA, unrelease any documents from a Security Group then belly band\" reminder should be displayed");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		SoftAssert softassert = new SoftAssert();
		SessionSearch sessionsearch = new SessionSearch(driver);
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		sgpage.createSecurityGroups(SGname);
		System.out.println(SGname);

		// Release the doc to security group
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(SGname);
		driver.waitForPageToBeReady();
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.waitForElement(sgpage.getSelectSecurityGroup());
		sgpage.getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(SGname);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(1));
		sgpage.getSG_GenerateEmailRadioButton(2).waitAndClick(3);
		baseClass.waitForElement(sgpage.getSG_GenerateEmailButton());
		sgpage.getSG_GenerateEmailButton().waitAndClick(3);
		baseClass.passedStep("Generate button is clicked successfully");
		baseClass.waitForElement(sgpage.getYesButton());
		sgpage.getYesButton().waitAndClick(2);

		// verify warning message displayed
		driver.waitForPageToBeReady();
		sessionsearch.navigateToSessionSearchPageURL();
		driver.scrollPageToTop();
		baseClass.waitForElement(sessionsearch.getBulkActionButton());
		sessionsearch.getBulkActionButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionsearch.getBulkReleaseActionDL());
		sessionsearch.getBulkReleaseActionDL().waitAndClick(10);
		baseClass.waitForElement(sessionsearch.getBulkRelDefaultSecurityGroup_CheckBox(SGname));
		sessionsearch.getBulkRelDefaultSecurityGroup_CheckBox(SGname).waitAndClick(10);
		baseClass.waitForElement(sessionsearch.getBulkRelease_ButtonRelease());
		sessionsearch.getBulkRelease_ButtonRelease().waitAndClick(20);

		String errormsg = "Please be aware that the Security Group is set to use Security Group-specific attributes for email inclusiveness and email duplicate analysis";
		baseClass.waitForElement(sgpage.getSG_EmailGenerateWarningMsgBulkRelease());
		String Actualwarningmsg = sgpage.getSG_EmailGenerateWarningMsgBulkRelease().getText();
		baseClass.stepInfo("Actual Warning message...." + Actualwarningmsg);
		String expectWarningmsg = sgpage.getSG_EmailGenerateWarningMsgBulkReleaseText(errormsg).getText();
		baseClass.stepInfo("Expected warning message..." + expectWarningmsg);
		softassert.assertEquals(Actualwarningmsg, expectWarningmsg);
		driver.waitForPageToBeReady();
		if (Actualwarningmsg.contains(expectWarningmsg)) {
			baseClass.passedStep("Warning message is displayed successfully");
		} else {
			baseClass.failedStep("Warning msg is not displayed");
		}
		softassert.assertAll();
		sgpage.deleteSecurityGroups(SGname);

	}

	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                 RPMXCON-54310
	 * @throws Exception
	 * @Description To verify that if Security Group is at SG level attribute and if
	 *              PA release documents to Security Group then belly band" message
	 *              should be displayed.
	 */
	@Test(description = "RPMXCON-54310", enabled = true, groups = { "regression" })
	public void verifyPAReleaseDocsFromSgBellyBandMsgDisplayed() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54310");
		baseClass.stepInfo(
				"To verify that if Security Group is at SG level attribute and if  PA release documents to  Security Group then belly band\" message should be displayed.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		SoftAssert softassert = new SoftAssert();
		SessionSearch sessionsearch = new SessionSearch(driver);
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		String searchname = "New" + UtilityLog.dynamicNameAppender();
		SavedSearch saveSearch = new SavedSearch(driver);

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		sgpage.createSecurityGroups(SGname);
		System.out.println(SGname);
		baseClass.CloseSuccessMsgpopup();
		baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(1));
		sgpage.getSG_GenerateEmailRadioButton(2).waitAndClick(3);
		baseClass.waitForElement(sgpage.getSG_GenerateEmailButton());
		sgpage.getSG_GenerateEmailButton().waitAndClick(3);
		baseClass.passedStep("Generate button is clicked successfully");
		baseClass.waitForElement(sgpage.getYesButton());
		sgpage.getYesButton().waitAndClick(2);

		// basic Saved search
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.saveSearch(searchname);
		saveSearch.savedSearch_Searchandclick(searchname);
		baseClass.waitForElement(saveSearch.getChooseSearchRadiobtn(searchname));
		saveSearch.getChooseSearchRadiobtn(searchname);
		saveSearch.getReleaseIcon().waitAndClick(3);
		baseClass.waitForElement(saveSearch.getReleaseDocToSG(SGname));
		saveSearch.getReleaseDocToSG(SGname).waitAndClick(5);

		// Click Release Button and verify warning msg
		baseClass.waitForElement(saveSearch.getReleaseBtn());
		saveSearch.getReleaseBtn().waitAndClick(5);
		String errormsg = "Please be aware that the Security Group is set to use Security Group-specific attributes for email inclusiveness and email duplicate analysis";
		baseClass.waitForElement(sgpage.getSG_EmailGenerateWarningMsgBulkRelease());
		String Actualwarningmsg = sgpage.getSG_EmailGenerateWarningMsgBulkRelease().getText();
		baseClass.stepInfo("Actual Warning message...." + Actualwarningmsg);
		String expectWarningmsg = sgpage.getSG_EmailGenerateWarningMsgBulkReleaseText(errormsg).getText();
		baseClass.stepInfo("Expected warning message..." + expectWarningmsg);
		softassert.assertEquals(Actualwarningmsg, expectWarningmsg);
		driver.waitForPageToBeReady();
		if (Actualwarningmsg.contains(expectWarningmsg)) {
			baseClass.passedStep("Warning message is displayed successfully");
		} else {
			baseClass.failedStep("Warning msg is not displayed");
		}
		softassert.assertAll();
		sgpage.deleteSecurityGroups(SGname);
	}

	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                 RPMXCON-54492
	 * @throws Exception
	 * @Description Security Groups Email Analytics InCorrect Warning Message
	 */
	@Test(description = "RPMXCON-54492", enabled = true, groups = { "regression" })
	public void verifyPAChangeProjectLevelSgLevelAttributeSelectedSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54492");
		baseClass.stepInfo("Security Groups Email Analytics InCorrect Warning Message");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		sgpage.createSecurityGroups(SGname);
		baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSelectSecurityGroup());
		sgpage.getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(SGname);
		baseClass.passedStep("Click on save button without change anything");
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(5);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();

		// verify warning message
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(2));
		sgpage.getSG_GenerateEmailRadioButton(2).waitAndClick(3);
		baseClass.waitForElement(sgpage.getSG_GenerateEmailButton());
		sgpage.getSG_GenerateEmailButton().waitAndClick(3);
		baseClass.waitForElement(sgpage.getVerifySG_EmailGenerateWarningMsg());
		baseClass.stepInfo("Selected email generate button");
		String warningmsg = sgpage.getVerifySG_EmailGenerateWarningMsg().getText();
		baseClass.stepInfo(" Warning message...." + warningmsg);
		if (sgpage.getVerifySG_EmailGenerateWarningMsg().isDisplayed()) {
			baseClass.passedStep(
					"when user select re-generate security group specific email inclusive & email duplicate data is warning message is displayed ");
		} else {
			baseClass.failedStep(
					" user select re-generate security group specific email inclusive is warning msg not displayed");
		}
		sgpage.deleteSecurityGroups(SGname);

	}

	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                 RPMXCON-54091
	 * @throws Exception
	 * @Description Verify after impersonation user can assign/un-assign annotation
	 *              layer to security group
	 */
	@Test(description = "RPMXCON-54091", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationUserAssignUnassignAnnatoationLayer() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54091");
		baseClass.stepInfo("Verify after impersonation user can assign/un-assign annotation layer to security group");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		AnnotationLayer annotationLayer = new AnnotationLayer(driver);
		String addName = "test" + Utility.dynamicNameAppender();
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoPA();
		driver.waitForPageToBeReady();
		annotationLayer.AddAnnotation(addName);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		sgpage.createSecurityGroups(SGname);
		baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSelectSecurityGroup());
		sgpage.getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(SGname);
		sgpage.getAssignedAnnotationLayerAddedSg(addName);
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(5);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		sgpage.unAssigningTheTagInAnnotation(addName, addName);
		sgpage.getSG_AnnSaveButton().waitAndClick(5);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		sgpage.deleteSecurityGroups(SGname);
		driver.waitForPageToBeReady();
		annotationLayer.deleteAnnotationByPagination(addName);

	}

	/**
	 * @author Vijaya.Rani ModifyDate:12/09/2022 RPMXCON-54738
	 * @throws Exception
	 * @Description Verify that RMU can save the Production template.
	 * 
	 */
	@Test(description = "RPMXCON-54738", enabled = true, groups = { "regression" })
	public void verifyingTemplateInProductioninRMU() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54738");
		baseClass.stepInfo("Verify that RMU can save the Production template.");
		baseClass = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String Templatename = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();

		page.navigateToProductionPage();
		page.selectSavedTemplateAndManageTemplate(productionname, Templatename);
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:12/09/2022 RPMXCON-54743
	 * @throws Exception
	 * @Description Verify that if DAU impersonate as RMU,can create and generate
	 *              the new production.
	 * 
	 */
	@Test(description = "RPMXCON-54743", enabled = true, groups = { "regression" })
	public void verifyDAImpersonateRMUGenerateProduction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54743");
		baseClass.stepInfo("Verify that if DAU impersonate as RMU,can create and generate the new production.");

		userManage = new UserManagement(driver);
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		baseClass.stepInfo("Impersonate DA to RMU");
		baseClass.impersonateDAtoRMU();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.tagNameTechnical);
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
	 * @author Vijaya.Rani ModifyDate:12/09/2022 RPMXCON-54744
	 * @throws Exception
	 * @Description Verify that RMU can create new production using template which
	 *              is created by PAU in same security group.
	 * 
	 */
	@Test(description = "RPMXCON-54744", enabled = true, groups = { "regression" })
	public void verifyProductionGenerationUsingTemplate() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54744");
		baseClass.stepInfo(
				"Verify that RMU can create new production using template which is created by PAU in same security group.");
		baseClass = new BaseClass(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		UtilityLog.info(Input.prodPath);
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String prefixID1 = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String suffixID2 = "S" + Utility.dynamicNameAppender();
		String Templatename = "Temp" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.addNewSearch();
		sessionSearch.SearchMetaData(Input.docFileType, Input.MetaDataFileType);
		sessionSearch.addPureHit();

		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(6);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);

		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		page.verifyingTheDefaultSelectedOptionInNative();
		page.verifyingNativeSectionFileType(Input.MetaDataFileType);
		page.verifyingNativeSectionFileType(Input.NativeFileType);

		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		driver.waitForPageToBeReady();
		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectSavedTemplateAndManageTemplate(productionname, Templatename);
		page.verifyingComponentTabSection();
		driver.waitForPageToBeReady();
		page = new ProductionPage(driver);
		page.baseInfoLoadTemplate(productionname1, Templatename);
		page.getCheckBoxCheckedVerification(page.chkIsDATSelected());
		page.getCheckBoxCheckedVerification(page.chkIsTIFFSelected());
		page.verifyingTheDefaultSelectedOptionInNative();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID1, suffixID2, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:12/09/2022 RPMXCON-54745
	 * @throws Exception
	 * @Description Verify that after commting the production by RMU,produced
	 *              documents can view the in doc view.
	 * 
	 */
	@Test(description = "RPMXCON-54745", enabled = true, groups = { "regression" })
	public void verifyDocumentDisplayedInProductionInDocViewPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54745");
		baseClass.stepInfo(
				"Verify that after commting the production by RMU,produced documents can view the in doc view.");
		baseClass = new BaseClass(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		UtilityLog.info(Input.prodPath);
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("View searched for  docs in Doc view");
		sessionsearch.ViewInDocViewWithoutPureHit();

		driver.waitForPageToBeReady();
		DocViewPage doc = new DocViewPage(driver);
		driver.waitForPageToBeReady();
		doc.clickOnImageTab();
		driver.waitForPageToBeReady();
		doc.verifyProductionNameForPDFFileInDocView(productionname);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:13/09/2022 RPMXCON-54750
	 * @throws Exception
	 * @Description Verify that RMU can regenerate the production.
	 * 
	 */
	@Test(description = "RPMXCON-54750", enabled = true, groups = { "regression" })
	public void verifyRMURegenerateTheProduction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54750");
		baseClass.stepInfo("Verify that RMU can regenerate the production.");

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		UtilityLog.info(Input.prodPath);
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateTagwithClassification(tagname1, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkTagExisting(tagname1);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int nativefile = Integer.parseInt(beginningBates);
		int NativeDocStart = nativefile + 3;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		driver.waitForPageToBeReady();
		//getting warning message in the below
		page.clickBackBtnandSelectingNative(7, tagname);
		driver.scrollingToBottomofAPage();
		page.getTIFF_EnableforPrivilegedDocs().isDisplayed();
		page.getTIFF_EnableforPrivilegedDocs().waitAndClick(10);
		page.clickMArkCompleteMutipleTimes(3);
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);
		page.fillingGeneratePageWithContinueGenerationPopup();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:13/09/2022 RPMXCON-54771
	 * @throws Exception
	 * @Description Verify that if RMU changes the SG from header ,Productions menu
	 *              should be displays.
	 * 
	 */
	@Test(description = "RPMXCON-54771", enabled = true, groups = { "regression" })
	public void verifyRMUHeaderSGProductionDisplay() throws Exception {

		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54771");
		baseClass.stepInfo("Verify that if RMU changes the SG from header ,Productions menu should be displays.");

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.waitForElement(sgpage.securityGroupTab());
		String getAttribute = sgpage.securityGroupTab().GetAttribute("title");
		if (getAttribute.equalsIgnoreCase("Default Security Group")) {
			baseClass.passedStep("Default security group is selcted");
		} else {
			baseClass.failedStep("Default security group is not selcted");
		}
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		baseClass.waitTillElemetToBeClickable(sgpage.productionPageSelectedSG());
		String sgname = sgpage.productionPageSelectedSG().getText();
		if (sgname.equalsIgnoreCase(getAttribute)) {
			baseClass.passedStep("Default security group is selcted");
		} else {
			baseClass.failedStep("Default security group is not selcted");
		}

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:13/09/2022 RPMXCON-54784
	 * @throws Exception
	 * @Description Verify that if SAU impersonate as RMU,and changes the Project
	 *              from header drop down should take to Default SG in the selected
	 *              project.
	 * 
	 */
	@Test(description = "RPMXCON-54784", enabled = true, groups = { "regression" })
	public void verifySAImpersonateAsRMUHomePageProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54784");
		baseClass.stepInfo(
				"Verify that if SAU impersonate as RMU,and changes the Project from header drop down should take to Default SG in the selected project.");

		DomainDashboard domainDash = new DomainDashboard(driver);
		SoftAssert softassert = new SoftAssert();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		baseClass.stepInfo("Impersonate SA to RMU");
		baseClass.impersonateSAtoRMU();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify RMU Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer Manager home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer Manager home page is not displayed ");
		}
		baseClass.stepInfo("verify RMU Home Page Selected project");
		String actualProject = Input.projectName;
		String expectedProject = baseClass.getProjectNames().getText();
		softassert.assertEquals(actualProject, expectedProject);
		baseClass.passedStep("RMU Dashboard Successfully Clicked the Slected project");
		softassert.assertAll();
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:14/09/2022 RPMXCON-54785
	 * @throws Exception
	 * @Description Verify thatif SAU user impersonate as RMU,and changes the SG in
	 *              SG Dropdown then it should take the corresponding SG in the same
	 *              project.
	 * 
	 */
	@Test(description = "RPMXCON-54785", enabled = true, groups = { "regression" })
	public void verifySAImpersonateAsRMUDropDownSG() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54785");
		baseClass.stepInfo(
				"Verify thatif SAU user impersonate as RMU,and changes the SG in SG Dropdown then it should take the corresponding SG in the same project.");

		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		baseClass.stepInfo("Impersonate SA to RMU");
		baseClass.impersonateSAtoRMU();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify RMU Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer Manager home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer Manager home page is not displayed ");
		}
		baseClass.stepInfo("verify default security group in selected project");
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("As user to DSG by default");
		} else {
			baseClass.failedStep("It is not  to default SG by default ");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:14/09/2022 RPMXCON-54786
	 * @throws Exception
	 * @Description Verify that when PAU impersonate as RMU,and changes the Project
	 *              from header drop down should take to Default SG in the selected
	 *              project.
	 * 
	 */
	@Test(description = "RPMXCON-54786", enabled = true, groups = { "regression" })
	public void verifyPAImpersonateAsRMUSelectedProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54786");
		baseClass.stepInfo(
				"Verify that when PAU impersonate as RMU,and changes the Project from header drop down should take to Default SG in the selected project.");

		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SoftAssert softassert = new SoftAssert();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("Impersonate PA to RMU");
		baseClass.impersonatePAtoRMU();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify RMU Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer Manager home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer Manager home page is not displayed ");
		}
		baseClass.stepInfo("verify RMU Home Page Selected project");
		String actualProject = Input.projectName;
		String expectedProject = baseClass.getProjectNames().getText();
		softassert.assertEquals(actualProject, expectedProject);
		baseClass.passedStep("RMU Dashboard Successfully Clicked the Slected project");
		softassert.assertAll();
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:15/09/2022 RPMXCON-54306
	 * @throws Exception
	 * @Description To verify that if user select option 'Use Security Group
	 *              specific Email Inclusive and Email Duplicate data', belly band
	 *              message should be displayed.
	 * 
	 */
	@Test(description = "RPMXCON-54306", enabled = true, groups = { "regression" })
	public void verifySelectSGEmailInclusiveDataInBellyBandMsg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54306");
		baseClass.stepInfo(
				"To verify that if user select option 'Use Security Group specific Email Inclusive and Email Duplicate data', belly band message should be displayed.");

		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		savedsearch = new SavedSearch(driver);
		SoftAssert softassert = new SoftAssert();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(2));
		sgpage.getSG_GenerateEmailRadioButton(2).waitAndClick(5);
		baseClass.stepInfo("Use Security Group-Specific Email inclusive check mark is selected");
		baseClass.waitForElement(sgpage.getSG_GenerateEmailButton());
		sgpage.getSG_GenerateEmailButton().waitAndClick(3);
		baseClass.passedStep("Generate button is clicked successfully");
		driver.waitForPageToBeReady();

		// verify warning message
		String Actualwarningmsg = sgpage.getVerifySG_EmailGenerateWarningMsg().getText();
		baseClass.stepInfo("Actual Warning message...." + Actualwarningmsg);
		String expectWarningmsg = "You have elected to regenerate and overwrite all previous values for the four Security Group-specific email inclusiveness and email duplicate fields. This will wipe away all prior stored data for these attributes, and will overlay new values for each record currently in the Security Group. No prior work product or logic will be undone, which may mean that your Assignments and workflow may need to be altered. Do you want to proceed?";
		baseClass.stepInfo("Expected warning message..." + expectWarningmsg);
		softassert.assertEquals(Actualwarningmsg, expectWarningmsg);
		baseClass.passedStep("Warning message is displayed successfully");
		softassert.assertAll();
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:15/09/2022 RPMXCON-54314
	 * @throws Exception
	 * @Description To verify that when the SG is set to use project level fields
	 *              that Tag and Folder Propagation happens using
	 *              EmailDuplicateDocIDs attribute.
	 * 
	 */
	@Test(description = "RPMXCON-54314", enabled = true, groups = { "regression" })
	public void verifySGTagUsingEmailDuplicateDocIdInDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54314");
		baseClass.stepInfo(
				"To verify that when the SG is set to use project level fields that Tag and Folder Propagation happens using EmailDuplicateDocIDs attribute.");

		userManage = new UserManagement(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocListPage doclist = new DocListPage(driver);
		String tagname = "Tag" + Utility.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		doclist.documentSelection(1);
		doclist.addNewBulkTagEmailDuplicates(tagname);

		baseClass.passedStep("All EmailDuplicateDocIds' including the selected document is tagged.");
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-54740 Date:9/19/2022
	 * @Description:Verify that RMU can view the existing Proudction Export details
	 */
	@Test(description = "RPMXCON-54740", enabled = true, groups = { "regression" })

	public void verifyingNativeFilesInGenreratedExport() throws Exception {
		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-54740-from Security Groups");
		base.stepInfo("Verify that RMU can view the existing Proudction Export details");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = Input.randomText + Utility.dynamicNameAppender();
		String exportName1 = Input.randomText + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();

		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName1);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(page.getProductionNameLink(exportName), exportName);
		base.ValidateElement_Presence(page.getProductionNameLink(exportName1), exportName1);
		baseClass.passedStep("RMU can view the existing Proudction Export details");
		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-54749
	 * @Description:Verify RMU can Commit/Uncommit the production in the security
	 *                     group
	 **/
	@Test(description = "RPMXCON-54749", enabled = true, groups = { "regression" })
	public void verifyRMUCommitAndUncommit() throws Exception {

		UtilityLog.info(Input.prodPath);
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test Cases Id : RPMXCON-54749");
		baseClass.stepInfo("Verify RMU can Commit/Uncommit the production in the security group");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		BatchPrintPage batchPrint = new BatchPrintPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(2);
		doc.bulkTagExisting(tagname);

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionWithBrandingText();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		baseClass.stepInfo("Committing the production");
		page.fillingGeneratePageWithContinueGenerationPopup();
		for (int i = 0; i < 2; i++) {
			driver.Navigate().refresh();
			baseClass.waitTime(2);
		}

		baseClass.stepInfo("Uncommitting the committed production");
		baseClass.waitForElement(page.getConfirmProductionUnCommit());
		page.getConfirmProductionUnCommit().waitAndClick(5);
		for (int i = 0; i < 2; i++) {
			driver.Navigate().refresh();
			baseClass.waitTime(2);
		}

		baseClass.passedStep("Verified - completion of uncommit, notification should be displayed on right top corner");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:19/07/2022 RPMXCON-54828
	 * @throws Exception
	 * @Description Verify if SAU impersonate as DAU, clicking on project should
	 *              redirect to default security group and select the domain project
	 *              from header drop down, clicking on Back to dashboard , it should
	 *              redirect to last access Domain Dashboard page.
	 * 
	 */
	@Test(description = "RPMXCON-54828", enabled = true, groups = { "regression" })
	public void verifySAImpersonateAsDAAccessDomainDashBoardPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54828");
		baseClass.stepInfo(
				"Verify if SAU impersonate as DAU, clicking on project should redirect to default security group and select the domain project from header drop down, clicking on Back to dashboard , it should redirect to last access Domain Dashboard page.");

		userManage = new UserManagement(driver);
		DataSets data = new DataSets(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		String projectName = "DAProject" + Utility.dynamicNameAppender();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		driver.waitForPageToBeReady();
		
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "0");
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.rmu1FullName, "Review Manager", "Default Security Group",
				false, true);

		baseClass.stepInfo("Impersonate SA to DA");
		baseClass.impersonateSAtoDA();

		baseClass.stepInfo("Verify it is domain dashboard page");
		if (domainDash.getDataRefresh_info().isDisplayed()) {
			baseClass.passedStep("Application is navigate to Domain Dashboard page Successfully");
		} else {
			baseClass.failedStep("Application is not navigate to Domain Dashboard page   ");
		}

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify default security group in selected project");
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It is redirect to default SG in selected project successfully ");
		} else {
			baseClass.failedStep("It is not redirect to default SG in selected project ");
		}

		baseClass.stepInfo("Select Another project");
		baseClass.selectproject(projectName);

		baseClass.stepInfo("verify default security group in selected project");
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It is redirect to default SG in selected project successfully ");
		} else {
			baseClass.failedStep("It is not redirect to default SG in selected project ");
		}

		// validation for back button
		baseClass.stepInfo("Click on back Button");
		driver.Navigate().back();

		baseClass.stepInfo("Verify last access for domain dashboard ");
		if (domainDash.getDataRefresh_info().isDisplayed()) {
			baseClass.passedStep("It is redirect to last access domain dashboard ");
		} else {
			baseClass.failedStep("It is not redirect to last access domain dashboard");

		}
	}

	/**
	 * @author Vijaya.Rani ModifyDate:19/07/2022 RPMXCON-54774
	 * @throws Exception
	 * @Description Verify that when DAU Changes the SG in SG Dropdown then it
	 *              should take the corresponding SG in the same project.
	 * 
	 */
	@Test(description = "RPMXCON-54774", enabled = true, groups = { "regression" })
	public void verifyRmuChangeSgFromDropDownTheSameProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54774");
		baseClass.stepInfo(
				"Verify that when DAU Changes the SG in SG Dropdown then it should take the corresponding SG in the same project.");
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SoftAssert softassert = new SoftAssert();
		DocViewMetaDataPage docViewMetaData = new DocViewMetaDataPage(driver);

		// Login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// Create security group
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(SGname);
		baseClass.stepInfo("Added new Security group");
		loginPage.logout();

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify default security group in selected project");
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("As user to DSG by default");
		} else {
			baseClass.failedStep("It is not  to default SG by default ");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewMetaData.getSecurityGroup(SGname));
		softassert.assertTrue(docViewMetaData.getSecurityGroup(SGname).isElementAvailable(5));
		if (docViewMetaData.getSecurityGroup(SGname).isElementAvailable(5)) {
			baseClass.passedStep("corressponding " + SGname + "on the same project is displayed successfully");

		} else {
			baseClass.failedStep("SG not displayed");
		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sgpage.deleteSecurityGroups(SGname);
		baseClass.stepInfo("Added Security group has been deleted");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:21/07/2022 RPMXCON-47098
	 * @throws Exception
	 * @Description Verify that redaction/annotations/remarks icons should be
	 *              displayed on doc view though the Project Admin/RMU user who
	 *              created new annotation layer is in-active.
	 * 
	 */
	@Test(description = "RPMXCON-47098", enabled = true, groups = { "regression" })
	public void verifyRedactionAnnotationRemarksIconDisplayInDocView() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47098");
		baseClass.stepInfo(
				"Verify that redaction/annotations/remarks icons should be displayed on doc view though the Project Admin/RMU user who created new annotation layer is in-active.");

		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softassert = new SoftAssert();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.rmu1userName + "");

		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();

		// verify redaction icon
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.redactionIcon());
		softassert.assertTrue(docView.redactionIcon().Displayed());
		softassert.assertAll();
		baseClass.passedStep("Redaction Icon is Displayed Succesfully");

		// verify annotation icon
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_AnnotateIcon());
		softassert.assertTrue(docView.getDocView_AnnotateIcon().Displayed());
		softassert.assertAll();
		baseClass.passedStep("DocView Annotation Icon is Displayed Succesfully");

		// verify Remark icon
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_AddRemarkIcon());
		softassert.assertTrue(docView.getDocView_AddRemarkIcon().Displayed());
		softassert.assertAll();
		baseClass.passedStep("DocView Reviewer Remark Icon is Displayed Succesfully");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:21/07/2022 RPMXCON-47085
	 * @throws Exception
	 * @Description Verify that newly created annotation layer should be displayed
	 *              though the System Admin user who created the project, annotation
	 *              layer is in-active.
	 * 
	 */
	@Test(description = "RPMXCON-47085", enabled = true, groups = { "regression" })
	public void verifyCreatAnnotationLayerInSA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47085");
		baseClass.stepInfo(
				"Verify that newly created annotation layer should be displayed though the System Admin user who created the project, annotation layer is in-active.");

		userManage = new UserManagement(driver);
		SoftAssert softassert = new SoftAssert();
		DataSets data = new DataSets(driver);
		AnnotationLayer annotation = new AnnotationLayer(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		String projectName = "PAProject" + Utility.dynamicNameAppender();
		String annoName = "newAnnotation" + Utility.dynamicNameAppender();
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		String firstname = "tempcar";
		String lastname = "consilio";
		String mailId = "tempcar@conilio.com";

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "0");
		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);

		baseClass.stepInfo("Impersonate SA to PA");
		baseClass.impersonateSAtoPA();

		baseClass.stepInfo("Go to Manage Annotation Layer Page and Add new Annotation");
		annotation.AddAnnotation(annoName);
		System.out.println(annoName);
		baseClass.passedStep("Annotation layer created successfull under the 'AllSecurityGroups'");

		// Create security group
		baseClass.stepInfo("Go to Manage securityGroup Page and Add new securityGroup");
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(SGname);
		baseClass.stepInfo("new Security group is created");

		driver.waitForPageToBeReady();
		baseClass.stepInfo("seclect created SecurityGroup and Annotationlayer save it");
		sgpage.addAnnotationlayertosg(SGname, annoName);
		baseClass.passedStep("Newly created annotation layer is mapped to the newly created security group");
		loginPage.logout();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.navigateToUsersPAge();
		userManage.createUser(firstname, lastname, "Project Administrator", mailId, null, projectName);
		baseClass.stepInfo("Delete other SA Account In User Page");
		userManage.navigateToUsersPAge();
		userManage.passingUserName(firstname);
		userManage.applyFilter();
		userManage.deleteUser1(projectName);
		baseClass.passedStep("User is de-activated/deleted successfully");

		baseClass.stepInfo("Impersonate SA to RMU");
		baseClass.impersonateSAtoRMU(projectName);

		baseClass.stepInfo("Go to AnnotationLayer Page Check ");
		annotation.navigateToAnnotationLayerPage();
		baseClass.stepInfo("Go to Manage Annotation Layer Page and Add new Annotation");
		softassert.assertTrue(annotation.getAnnotation_Layers().Displayed());
		softassert.assertAll();
		baseClass.passedStep(
				"Newly created annotation layer is displayed though the System Admin user who created the project, annotation layer is in-active");
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sgpage.deleteSecurityGroups(SGname);
		baseClass.stepInfo("Added Security group has been deleted");
		annotation.deleteAnnotationByPagination(annoName);
		baseClass.stepInfo("Added AnnotationLayer has been deleted");
		loginPage.logout();

	}

	/**
	 * @author Date: Modified date:N/A Modified by: Description Verify that RMU can
	 *         create and genearte new Production successfully for his security
	 *         group
	 * 
	 */
	@Test(description = "RPMXCON-54737", enabled = true, groups = { "regression" })
	public void verifyRMUCreateGenarateNewProductionForSg() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-54737");
		baseClass
				.stepInfo("Verify that RMU can create and genearte new Production successfully for his security group");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "p" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		baseClass.stepInfo("New Tag created with classification " + tagname);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.fillingMP3FileWithBurnRedaction(Input.defaultRedactionTag);
		page.getMP3FilesBurnRedactionTag(Input.defaultRedactionTag).ScrollTo();
		page.getMP3FilesBurnRedactionTag(Input.defaultRedactionTag).waitAndClick(10);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		baseClass.stepInfo("Production generate completed successfully for new SG");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
	}

	/**
	 * @author Date: Modified date:N/A Modified by: Description :To verify after
	 *         impersonating as Project Admin , sys admin can create security group.
	 */
	@Test(description = "RPMXCON-53628", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonatingPaSaCreateSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53628");
		baseClass.stepInfo("To verify after impersonating as Project Admin , sys admin can create security group.");
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);

		// Login as PAU
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Loggedin As SA");
		baseClass.impersonateSAtoPA();
		sgpage.navigateToSecurityGropusPageURL();
		// Create Security group
		sgpage.createSecurityGroups(SGname);
		baseClass.stepInfo("Added new Security group");
		System.out.println(SGname);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		List<WebElement> SGgrpname = sgpage.getSecurityGroupList().selectFromDropdown().getOptions();
		int size = SGgrpname.size();
		for (int i = 0; i <= size; i++) {
			String option = SGgrpname.get(i).getText();
			if (SGname.equals(option)) {
				baseClass.stepInfo(SGname + "name is saved and displayed");
				break;
			} else {
				System.out.println("checking next security");
			}
		}
		sgpage.getSecurityGroupList().waitAndClick(3);
		baseClass.waitForElement((sgpage.getSGGrpList(SGname)));
		if (sgpage.getSGGrpList(SGname).isElementAvailable(5)) {
			baseClass.passedStep(SGname + "is displayed successfully");

		} else {
			baseClass.failedStep("sg name not saved");

		}
		sgpage.deleteSecurityGroups(SGname);
		baseClass.stepInfo("Added Security group has been deleted");

	}

	/**
	 * @author Date: Modified date:N/A Modified by: Description :Verify that when
	 *         RMU changes the Security Group from Dropdown then it should take the
	 *         corresponding SG of the same project
	 */
	@Test(description = "RPMXCON-54790", enabled = true, groups = { "regression" })
	public void verifyRmuChangeSgFromDropDownTheSameProject2() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54790");
		baseClass.stepInfo(
				"Verify that when RMU changes the Security Group from Dropdown then it should take the corresponding SG of the same project");
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);
		SoftAssert softassert = new SoftAssert();
		docViewMetaDataPage = new DocViewMetaDataPage(driver);

		// Login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// Create security group
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(SGname);
		baseClass.stepInfo("Added new Security group");

		// access to security group to Rmu
		baseClass.waitTime(5);
		userManagement.assignAccessToSecurityGroups(SGname, Input.rmu1userName);
		userManagement.saveSecurityGroup();
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As RMU");
		driver.waitForPageToBeReady();
		docViewMetaDataPage.getSecurityGroupDropDownButton().waitAndClick(3);
		baseClass.stepInfo(SGname + "from header drop down for current project on rmu");
		baseClass.waitTime(3);
		baseClass.waitForElement(docViewMetaDataPage.getSecurityGroup(SGname));
		softassert.assertTrue(docViewMetaDataPage.getSecurityGroup(SGname).isElementAvailable(5));
		if (docViewMetaDataPage.getSecurityGroup(SGname).isElementAvailable(5)) {
			baseClass.passedStep("corressponding " + SGname + "on the same project is displayed successfully");

		} else {
			baseClass.failedStep("SG not displayed");

		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sgpage.deleteSecurityGroups(SGname);
		baseClass.stepInfo("Added Security group has been deleted");
	}

	/**
	 * @author Date: Modified date:N/A Modified by: Description Verify that RMU can
	 *         export the documents in his Security Group
	 * 
	 */
	@Test(description = "RPMXCON-54739", enabled = true, groups = { "regression" })
	public void verifyRMUExportTheDocInSg() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-54739");
		baseClass.stepInfo("Verify that RMU can export the documents in his Security Group");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = "p" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		baseClass.stepInfo("New Tag created with classification " + tagname);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);

	}

	/**
	 * @author Date: Modified date:N/A Modified by: Description :Verify that Project
	 *         Admin can release the document for the selected security group from
	 *         saved search.
	 */
	@Test(description = "RPMXCON-53675", enabled = true, groups = { "regression" })
	public void verifyPAReleaseTheDocSgFromSavedSearch() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53675");
		baseClass.stepInfo(
				"Verify that Project Admin can release the document for the selected security group from saved search.");
		String searchName = Input.randomText + Utility.dynamicNameAppender();
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		SessionSearch sessionsearch = new SessionSearch(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		savedsearch = new SavedSearch(driver);
		SoftAssert softassert = new SoftAssert();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as" + Input.pa1FullName);
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.createSecurityGroups(SGname);
		baseClass.stepInfo("Added new Security group" + SGname);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.saveSearch(searchName);
		savedsearch.navigateToSSPage();
		savedsearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		baseClass.passedStep("Clicked my saved Search Tab");
		baseClass.waitForElement(savedsearch.getChooseSearchRadiobtn(searchName));
		savedsearch.getChooseSearchRadiobtn(searchName);
		// Release Icon
		driver.waitForPageToBeReady();
		savedsearch.getReleaseIcon().waitAndClick(3);

		// Choose SG in PopUp
		baseClass.waitForElement(sessionsearch.getSelectSgPopup());
		softassert.assertTrue(sessionsearch.getSelectSgPopup().Displayed());
		baseClass.stepInfo("Security group popup is opened successfully");
		baseClass.waitForElement(savedsearch.getReleaseDocToSG(SGname));
		System.out.println(SGname);
		savedsearch.getReleaseDocToSG(SGname).waitAndClick(5);
		softassert.assertTrue(savedsearch.getReleaseDocToSG(SGname).isDisplayed());
		if (savedsearch.getReleaseDocToSG(SGname).isDisplayed()) {
			baseClass.passedStep(SGname + " is displayed on popup successfully");
		} else {
			baseClass.failedMessage("Security group is not displayed");
		}
		baseClass.waitForElement(savedsearch.getReleaseBtn());
		savedsearch.getReleaseBtn().waitAndClick(5);
		// Finalize release button
		baseClass.waitForElement(savedsearch.getFinalizeReleaseButtonfromPopup());
		savedsearch.getFinalizeReleaseButtonfromPopup().waitAndClick(6);
		baseClass.VerifySuccessMessage("Records saved successfully");
		baseClass.stepInfo("Success message has been verified");
		baseClass.passedStep(searchName + "  Document released to Selected.." + SGname);
		softassert.assertAll();
		sgpage.deleteSecurityGroups(SGname);
		baseClass.stepInfo("deleted the Security group with name " + SGname);
	}

	/**
	 * @author Date: Modified date:N/A Modified by: Description :To verify that
	 *         project admin can assign/unassign Folders for selected security
	 *         group.
	 */
	@Test(description = "RPMXCON-53682", enabled = true, groups = { "regression" })
	public void verifyPAAssignUnAssignFoldersForSelectingSg() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53682");
		baseClass.stepInfo(
				"Verify that Project Admin can release the document for the selected security group from saved search.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		SoftAssert softassert = new SoftAssert();
		String Folder = "Folders";
		String foldername = "AAFolder" + Utility.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		baseClass.stepInfo("craeted new folder with name " + foldername);
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.getSGSelectAccessControlTages(Folder).waitAndClick(3);
		sgpage.verifySelectFolderisAssignedInSelectedList(foldername);
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.stepInfo("Success message has been verified");
		baseClass.CloseSuccessMsgpopup();
		baseClass.waitTime(5);
		baseClass.waitTillElemetToBeClickable(sgpage.getSelectedFoldersCheckBox(foldername));
		sgpage.getSelectedFoldersCheckBox(foldername).waitAndClick(5);
		baseClass.waitForElement(sgpage.getSG_Folder_Left());
		sgpage.getSG_Folder_Left().waitAndClick(3);
		baseClass.waitForElement(sgpage.getFoldersCheckBox(foldername));
		if (sgpage.getFoldersCheckBox(foldername).isElementAvailable(5)) {
			baseClass.passedStep(foldername + "is displayed on available list");

		} else {
			baseClass.failedMessage("folder is not displayed");
		}
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.stepInfo("Success message has been verified");
		baseClass.CloseSuccessMsgpopup();
		baseClass.waitForElement(sgpage.getFoldersCheckBox(foldername));
		softassert.assertTrue(sgpage.getFoldersCheckBox(foldername).isElementAvailable(5));
		baseClass.passedStep(foldername + "is unassigned from security group successfully");
		softassert.assertAll();
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		baseClass.stepInfo("deleted the folder with name " + foldername);
	}

	/**
	 * @Author Krishna Date: 12/07/2022
	 * @Description : To verify that project admin can assign/unassign tags for
	 *              selected security group..
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53681", enabled = true, groups = { "regression" })
	public void verifyPAAssignUnAssignTagssForSelectingSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53681");
		baseClass.stepInfo("To verify that project admin can assign/unassign tags for selected security group.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		SoftAssert softassert = new SoftAssert();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String foldername1 = "Folder" + Utility.dynamicNameAppender();
		String securitygrp = "All Groups";
		String tags = "Tags";
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		tagsAndFolderPage.CreateTag(foldername, securitygrp);
		tagsAndFolderPage.CreateTag(foldername1, securitygrp);
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.createSecurityGroups(SGname);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSecurityGroupList());
		sgpage.getSecurityGroupList().selectFromDropdown().selectByVisibleText(SGname);
		baseClass.stepInfo(SGname + "..Selected as expected");
		baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(tags));
		sgpage.getSGSelectAccessControlTages(tags).waitAndClick(3);

		// verify selected tags are assign/UnAssign for listed fields
		sgpage.verifySelectTagsAssignedInSelectedList(foldername);
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.waitTime(5);
		baseClass.waitTillElemetToBeClickable(sgpage.getSelectedTagsCheckBox(foldername));
		sgpage.getSelectedTagsCheckBox(foldername).waitAndClick(5);
		baseClass.waitForElement(sgpage.getSG_Tag_Left());
		sgpage.getSG_Tag_Left().waitAndClick(3);
		baseClass.waitForElement(sgpage.getTagsCheckBox(foldername));
		if (sgpage.getTagsCheckBox(foldername).isElementAvailable(5)) {
			baseClass.passedStep(foldername + "is removed from the selected List and displayed on available list");

		} else {
			baseClass.failedMessage("folder is not displayed");

		}
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();

		// verify multiple selected tags are assign/UnAssign for listed fields
		sgpage.getTagsInSelectedList(foldername);
		sgpage.getTagsInSelectedList(foldername1);
		baseClass.stepInfo(foldername1 + foldername + "...select multiple tags in available list.");
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		softassert.assertAll();
		sgpage.getTagsInSelectedList(foldername);
		sgpage.getTagsInSelectedList(foldername1);
		driver.waitForPageToBeReady();
		sgpage.getSG_Tag_Right().waitAndClick(5);
		driver.waitForPageToBeReady();
		sgpage.getSG_CancelBtn().waitAndClick(5);
		baseClass.waitForElement(sgpage.getSecurityGroupList());
		sgpage.getSecurityGroupList().selectFromDropdown().selectByVisibleText(SGname);
		baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(tags));
		sgpage.getSGSelectAccessControlTages(tags).waitAndClick(3);
		softassert.assertTrue(sgpage.getTagsCheckBox(foldername).isElementAvailable(5)
				&& sgpage.getTagsCheckBox(foldername1).isElementAvailable(5));
		baseClass.passedStep(foldername + foldername1 + "...After cancelled Tags is displayed on available list.");
		baseClass.waitTime(5);
		sgpage.getTagsInSelectedList(foldername);
		sgpage.getTagsInSelectedList(foldername1);
		driver.waitForPageToBeReady();
		sgpage.getSG_Tag_Right().waitAndClick(5);
		driver.waitForPageToBeReady();
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.waitTime(10);
		baseClass.waitTillElemetToBeClickable(sgpage.getSelectedTagsCheckBox(foldername));
		sgpage.getSelectedTagsCheckBox(foldername).waitAndClick(5);
		baseClass.waitTillElemetToBeClickable(sgpage.getSelectedTagsCheckBox(foldername1));
		sgpage.getSelectedTagsCheckBox(foldername1).waitAndClick(5);
		baseClass.waitForElement(sgpage.getSG_CancelBtn());
		sgpage.getSG_CancelBtn().waitAndClick(5);
		baseClass.waitForElement(sgpage.getSecurityGroupList());
		sgpage.getSecurityGroupList().selectFromDropdown().selectByVisibleText(SGname);
		baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(tags));
		sgpage.getSGSelectAccessControlTages(tags).waitAndClick(3);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSelectedTagsCheckBox(foldername));
		softassert.assertTrue(sgpage.getSelectedTagsCheckBox(foldername).isElementAvailable(5)
				&& sgpage.getSelectedTagsCheckBox(foldername1).isElementAvailable(5));
		baseClass.passedStep(foldername + foldername1 + "...After cancelled Tags is displayed on selected list.");
		sgpage.deleteSecurityGroups(SGname);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(foldername1);
	}

	/**
	 * @Author Krishna Date: 13/07/2022
	 * @Description : To verify that project admin can assign/unassign redaction
	 *              tags for selected security group..
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53686", enabled = true, groups = { "regression" })
	public void verifyPAAssignUnAssignReductionsForSelectingSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53686");
		baseClass.stepInfo(
				"To verify that project admin can assign/unassign redaction  tags for selected security group.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		SoftAssert softassert = new SoftAssert();
		RedactionPage redactTag = new RedactionPage(driver);
		String redactnam = "ATest" + utility.dynamicNameAppender();
		String RedactionTags = "Redaction Tags";

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		baseClass.stepInfo("Add redaction tag");
		redactTag.navigateToRedactionsPageURL();
		redactTag.manageRedactionTagsPage(redactnam);
		System.out.println(redactnam);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(RedactionTags));
		sgpage.getSGSelectAccessControlTages(RedactionTags).waitAndClick(3);

		// verify selected Reduction tags is Assign/UnAssign for listed fields
		sgpage.verifySelectReductionAssignedInSelectedList(redactnam);
		driver.waitForPageToBeReady();
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(RedactionTags));
		sgpage.getSGSelectAccessControlTages(RedactionTags).waitAndClick(3);
		baseClass.waitTillElemetToBeClickable(sgpage.getSelectedReductionCheckBox(redactnam));
		sgpage.getSelectedReductionCheckBox(redactnam).waitAndClick(5);
		baseClass.waitForElement(sgpage.getSG_Reduction_Left());
		sgpage.getSG_Reduction_Left().waitAndClick(5);
		softassert.assertTrue((sgpage.getReductionCheckBox(redactnam).isElementAvailable(5)));
		baseClass.waitForElement(sgpage.getReductionCheckBox(redactnam));
		if (sgpage.getReductionCheckBox(redactnam).isElementAvailable(5)) {
			baseClass.passedStep(redactnam + "is removed from the Global List and displayed on available list");

		} else {
			baseClass.failedMessage("folder is not displayed");

		}
		softassert.assertAll();
		redactTag.DeleteRedaction(redactnam);
	}

	/**
	 * @Author Krishna Date: 14/07/2022
	 * @Description : To verify that when PA, release or unrelease any documents
	 *              from a Security Group and if the SG is set to use project-level
	 *              attributes then belly band" reminder should not be displayed
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54311", enabled = true, groups = { "regression" })
	public void verifyPaRelaseUnreleaseDocsFromSgBellyBandNotDisplay() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54311");
		baseClass.stepInfo(
				"To verify that when PA, release or unrelease any documents from a Security Group and if the SG is set to use project-level attributes then belly band\" reminder should not be displayed");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		SoftAssert softassert = new SoftAssert();
		SessionSearch sessionsearch = new SessionSearch(driver);
		savedsearch = new SavedSearch(driver);

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		sgpage.createSecurityGroups(SGname);
		baseClass.stepInfo(SGname + "is created successfully");
		baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(1));
		sgpage.getSG_GenerateEmailRadioButton(1).waitAndClick(5);
		baseClass.stepInfo("'Use Project-level Email Inclusive and Email Duplicate data is selected");
		driver.waitForPageToBeReady();
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		if (sgpage.getVerifySG_EmailGenerateWarningMsg().isElementAvailable(5)) {
			baseClass.failedStep("Belly band msg is dispalyed");
		} else {
			baseClass.passedStep("Belly band msg is not displayed");
		}

		// Release the doc to security group
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(SGname);
		baseClass.passedStep("  Document released to Selected.." + SGname);
		baseClass.passedStep("Belly band message is not displayed");
		softassert.assertAll();
		sgpage.deleteSecurityGroups(SGname);

	}

	/**
	 * @Author Sakthivel Date: 14/07/2022
	 * @Description : To verify that belly band warning message displays on clicking
	 *              of Regenerate button
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54313", enabled = true, groups = { "regression" })
	public void verifyBellyBandWarningMsgDisplayClickingRegenrateBtn2() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54313");
		baseClass.stepInfo("To verify that belly band warning message displays on clicking of Regenerate button.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		SoftAssert softassert = new SoftAssert();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(2));
		sgpage.getSG_GenerateEmailRadioButton(2).waitAndClick(5);
		baseClass.stepInfo("Use Security Group-Specific Email inclusive check mark is selected");
		baseClass.waitForElement(sgpage.getSG_GenerateEmailButton());
		sgpage.getSG_GenerateEmailButton().waitAndClick(3);
		baseClass.passedStep("Generate button is clicked successfully");
		driver.waitForPageToBeReady();

		// verify warning message
		String Actualwarningmsg = sgpage.getVerifySG_EmailGenerateWarningMsg().getText();
		baseClass.stepInfo("Actual Warning message...." + Actualwarningmsg);
		String expectWarningmsg = "You have elected to regenerate and overwrite all previous values for the four Security Group-specific email inclusiveness and email duplicate fields. This will wipe away all prior stored data for these attributes, and will overlay new values for each record currently in the Security Group. No prior work product or logic will be undone, which may mean that your Assignments and workflow may need to be altered. Do you want to proceed?";
		baseClass.stepInfo("Expected warning message..." + expectWarningmsg);
		softassert.assertEquals(Actualwarningmsg, expectWarningmsg);
		baseClass.passedStep("Warning message is displayed successfully");
		softassert.assertAll();
	}

	/**
	 * @Author Krishna Date: 21/07/2022
	 * @Description : Verify if PAU impersonate as Reviewer, he should able to
	 *              impersonate back as PAU in other project in which he is PAU
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54913", enabled = true, groups = { "regression" })
	public void verifyPAUImpersonateToREVAbleToBackPAU() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54913");
		baseClass.stepInfo(
				"Verify if RMU is select the other project in which he is PAU role, it should take to SG in the that project");
		docexp = new DocExplorerPage(driver);
		DataSets data = new DataSets(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		String projectName = "CreateProject" + Utility.dynamicNameAppender();
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);

		// create project
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "1");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		data.getNotificationMessage(0, projectName);
		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.rev1FullName, "Reviewer", "", false, false);
		System.out.println(projectName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoReviewer();
		if (assignmentPage.getAssignmentsInreviewerPg().isDisplayed()) {
			baseClass.passedStep("Redirect to reviewer home page is successfully");
		} else {
			baseClass.failedStep("will not redirect to homepage");
		}
		driver.waitForPageToBeReady();
		baseClass.impersonateReviewertoPA(projectName);
		baseClass.stepInfo("selected other project");
		baseClass.selectproject(projectName);
		if (data.getDatasetBtn().isDisplayed()) {
			baseClass.passedStep(projectName + ".. Redirect to PAU home page is successfully");
		} else {
			baseClass.failedStep("will not Redirect to homepage");
		}
	}

	/**
	 * @Author Krishna Date: 20/07/2022
	 * @Description : Verify if RMU is select the other project in which he is PAU
	 *              role, it should take to SG in the that project
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54847", enabled = true, groups = { "regression" })
	public void verifyRmuSelectOtherProjectInPaRoleTakeToSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54847");
		baseClass.stepInfo(
				"Verify if RMU is select the other project in which he is PAU role, it should take to SG in the that project");
		docexp = new DocExplorerPage(driver);
		DataSets data = new DataSets(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		String projectName = "SecurityGroupProject" + Utility.dynamicNameAppender();
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);

		// create project
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "1");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		data.getNotificationMessage(0, projectName);
		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.rev1FullName, "Reviewer", "", false, false);
		System.out.println(projectName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoReviewer();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(assignmentPage.getAssignmentsInreviewerPg());
		if (assignmentPage.getAssignmentsInreviewerPg().isDisplayed()) {
			baseClass.passedStep("Redirect to reviewer home page successfully");
		} else {
			baseClass.failedStep("will not redirect to homepage");
		}

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.selectproject(projectName);
		baseClass.stepInfo(projectName + "..is select from header dropdown");
		if (assignmentPage.getAssignmentsInreviewerPg().isDisplayed()) {
			baseClass.passedStep("Redirect to reviewer home page successfully");
		} else {
			baseClass.failedStep("will not redirect to homepage");
		}
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as " + Input.pa1FullName);
		if (data.getDatasetBtn().isDisplayed()) {
			baseClass.passedStep("Redirect to PAU home page successfully");
		} else {
			baseClass.failedStep("will not Redirect to homepage");
		}

	}

	/**
	 * @author Krishna Date: 01/18/21 Modified date:N/A Modified by: Description
	 *         Verify that in Domain Dashboard page, on clicking on Project from
	 *         'Active Projects' widgets, should take the DAU to Default SG in the
	 *         project
	 * 
	 */
	@Test(description = "RPMXCON-54806", enabled = true, groups = { "regression" })
	public void verifyDANavigationToProjectFromActiveProjects() throws Exception {
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54806");
		baseClass.stepInfo(
				"Verify that in Domain Dashboard page, on clicking on Project from 'Active Projects' widgets, should take the DAU to Default SG in the project");
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.selectdomain(Input.domainName);
		driver.waitForPageToBeReady();
		WebElement mytable = sgpage.daFirstBlock().getWebElement();
		List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
		// To calculate no of rows In table.
		int rows_count = rows_table.size();
		System.out.println(rows_count);
		baseClass.waitTillElemetToBeClickable(sgpage.daFirstTabledata(rows_count - 1));
		sgpage.daFirstTabledata(rows_count - 1).waitAndClick(5);
		baseClass.waitForElement(sgpage.securityGroupTab());
		String getAttribute = sgpage.securityGroupTab().GetAttribute("title");
		if (getAttribute.equalsIgnoreCase("Default Security Group")) {
			baseClass.passedStep("Default security group is selcted");
		} else {
			baseClass.failedStep("Default security group is not selcted");
		}
		baseClass.waitTillElemetToBeClickable(sgpage.backToDomain());
		sgpage.backToDomain().waitAndClick(5);
		driver.waitForPageToBeReady();
		if (sgpage.daFirstBlock().isDisplayed()) {
			baseClass.passedStep("Navigated back to domain admin home page");
		} else {
			baseClass.failedStep("Not Navigated back to domain admin home page");
		}
	}

	/**
	 * @author Krishna Date: 01/18/21 Modified date:N/A Modified by: Description
	 *         Verify that as DA user, clicking on project should redirect to
	 *         default security group and able to navigate back to the Domain
	 *         Dashboard
	 * 
	 */
	@Test(description = "RPMXCON-54820", enabled = true, groups = { "regression" })
	public void verifyDANavigationToProject2() throws Exception {
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54820");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and able to navigate back to the Domain Dashboard");
		// Verifying as SA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.selectdomain(Input.domainName);
		WebElement mytable1 = sgpage.daSecondBlock().getWebElement();
		List<WebElement> rows_table = mytable1.findElements(By.tagName("tr"));
		// To calculate no of rows In table.
		int rows_count1 = rows_table.size();
		System.out.println(rows_count1);
		baseClass.waitTillElemetToBeClickable(sgpage.daSecondTabledata(rows_count1 - 1));
		sgpage.daSecondTabledata(rows_count1 - 1).waitAndClick(5);
		baseClass.waitForElement(sgpage.securityGroupTab());
		String getAttribute = sgpage.securityGroupTab().GetAttribute("title");
		if (getAttribute.equalsIgnoreCase("Default Security Group")) {
			baseClass.passedStep("Default security group is selcted");
		} else {
			baseClass.failedStep("Default security group is not selcted");
		}
		baseClass.waitTillElemetToBeClickable(sgpage.backToDomain());
		sgpage.backToDomain().waitAndClick(5);
		driver.waitForPageToBeReady();
		if (sgpage.daFirstBlock().isDisplayed()) {
			baseClass.passedStep("Navigated back to domain admin home page");
		} else {
			baseClass.failedStep("Not Navigated back to domain admin home page");
		}
	}

	/**
	 * @author Krishna Date: Modified date:N/A Modified by: Description Verify that
	 *         On Produciton home page, Security group should displayed same as RMU
	 *         is logged in
	 * 
	 */
	@Test(description = "RPMXCON-54770", enabled = true, groups = { "regression" })
	public void verifySGForProduction() throws Exception {
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54770");
		baseClass.stepInfo(
				"Verify that On Produciton home page, Security group should displayed same as RMU is logged in");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.waitForElement(sgpage.securityGroupTab());
		String getAttribute = sgpage.securityGroupTab().GetAttribute("title");
		if (getAttribute.equalsIgnoreCase("Default Security Group")) {
			baseClass.passedStep("Default security group is selcted");
		} else {
			baseClass.failedStep("Default security group is not selcted");
		}
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		baseClass.waitTillElemetToBeClickable(sgpage.productionPageSelectedSG());
		String sgname = sgpage.productionPageSelectedSG().getText();
		if (sgname.equalsIgnoreCase(getAttribute)) {
			baseClass.passedStep("Default security group is selcted");
		} else {
			baseClass.failedStep("Default security group is not selcted");
		}

	}

	/**
	 * @author Krishna Date: Modified date:N/A Modified by: Description To verify by
	 *         Production is accessible by default to RMU user
	 * 
	 */
	@Test(description = "RPMXCON-54736", enabled = true, groups = { "regression" })
	public void verifyProductionOptionForRMU() throws Exception {
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54770");
		baseClass.stepInfo("To verify by Production is accessible by default to RMU user");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		if (sgpage.productionLeftMenu().Displayed()) {
			baseClass.passedStep("Productions left menu is accessible for RMU");
		} else {
			baseClass.failedStep("Productions left menu is NOT accessible for RMU");
		}
	}

	/**
	 * @author Date: Modified date:N/A Modified by: Description Verify that RMU can
	 *         delete the production
	 * 
	 */
	@Test(description = "RPMXCON-54741", enabled = true, groups = { "regression" })
	public void verifyRmuCanDeleteTheProduction() throws Exception {
		baseClass.stepInfo("RPMXCON-54741");
		baseClass.stepInfo("Verify that RMU can delete the production");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String productionname2 = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Login as Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Production/Home");

		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Production/Home");

		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname2);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.waitForPageToBeReady();
		page.deleteProduction(productionname2);
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
		SessionSearch sessionSearch = new SessionSearch(driver);
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
		page.fillingTIFFSectionPrivDocs(tagname, Input.tagNameTechnical);
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
		page.fillingTIFFSectionPrivDocs(tagname, Input.tagNameTechnical);
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

	/**
	 * @Author Krishna Date: 8/08/2022
	 * @Description : Verify if RMU is select the other project in which he is PAU
	 *              role, it should take to SG in the that project
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54848", enabled = true, groups = { "regression" })
	public void verifyRmuSelectOtherProjectInPaRoleTakeToSg2() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54848");
		baseClass.stepInfo(
				"Verify if RMU is select the other project in which he is PAU role, it should take to SG in the that project");
		SoftAssert softassert = new SoftAssert();
		docexp = new DocExplorerPage(driver);
		DataSets data = new DataSets(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		DocViewMetaDataPage docViewMetaData = new DocViewMetaDataPage(driver);
		// Login as PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as in Rmu");
		softassert.assertTrue(docexp.getDocExplorerTabAfterDashBoard().isDisplayed());
		baseClass.stepInfo("Rmu is redirect to dashboard page");
		loginPage.logout();

		// create project
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		String projectName = "SecurityGroupProject" + Utility.dynamicNameAppender();
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "1");
		data.getNotificationMessage(0, projectName);
		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		System.out.println(projectName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		driver.waitForPageToBeReady();
		baseClass.selectproject(projectName);
		softassert.assertEquals(projectName, projectName);
		baseClass.passedStep(projectName + "..RMU Select project in which is user is PAU is selected successfully");
		driver.waitForPageToBeReady();
		docViewMetaData.getSecurityGroupDropDownButton().waitAndClick(3);
		baseClass.waitForElement(docViewMetaData.getSecurityGroup(Input.securityGroup));
		if (docViewMetaData.getSecurityGroup(Input.securityGroup).isDisplayed()) {
			baseClass.passedStep(Input.securityGroup + ".. is successfully displayed in selected project");
		} else {
			baseClass.failedStep("Sg is not displayed");

		}
		softassert.assertAll();
	}

	/**
	 * @Author Sakthivel Date: 9/08/2022
	 * @Description :Verify that as DA user,clicking on project should redirect to
	 *              default security group and then on changing the Project from
	 *              header drop down should take to Default SG in the selected
	 *              project
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54773", enabled = true, groups = { "regression" })
	public void verifyDAClickingProductDsgChangingSelectedProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54773");
		baseClass.stepInfo(
				"Verify that as DA user,clicking on project should redirect to default security group and then on changing the Project from header drop down should take to Default SG in the selected project");
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify default security group in selected project");
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("As user to DSG by default in selected project");
		} else {
			baseClass.failedStep("It is not  to default SG by default ");
		}
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Click on other project");
		baseClass.stepInfo("verify default security group in selected project");
		String ExpectedString1 = baseClass.getsgNames().getText();
		System.out.println(ExpectedString1);
		if (actualString.equals(ExpectedString1)) {
			baseClass.passedStep("DAU to Default SG in the selected project Successfully..");
		} else {
			baseClass.failedStep("It is not  to default SG by default ");
		}
	}

	/**
	 * @author Krishna ModifyDate:04/08/2022 RPMXCON-54915
	 * @throws Exception
	 * @Description To verify that project admin can navigate to tags for selected
	 *              security group and observe no duplicates.
	 * 
	 */
	@Test(description = "RPMXCON-54915", enabled = true, groups = { "regression" })
	public void verifyPANavigateTagsSelectedSgObserveNoDuplicates() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54915");
		baseClass.stepInfo(
				"To verify that when the SG is set to use SG-specific attributes then Folder Propagation should happens using EmailDuplicateDocIDs attribute.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		String tags = "Tags";
		String defaulttags = "Default Tags";

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as PA");
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");

		baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(tags));
		sgpage.getSGSelectAccessControlTages(tags).waitAndClick(3);
		sgpage.verifySelectTagsAssignedInSelectedList(defaulttags);
		driver.waitForPageToBeReady();
		int totFolderCount = sgpage.getSgDefaultTag().size();
		if (totFolderCount == 1) {
			baseClass.passedStep("Default Tags labels Duplicates is NOT appear on Security Group mapping screen.");

		} else {
			baseClass.failedStep("Default Tags labels Duplicates is appear on mappling screen");
		}
		driver.Navigate().refresh();
		baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(tags));
		sgpage.getSGSelectAccessControlTages(tags).waitAndClick(3);
		sgpage.verifySelectTagsAssignedInSelectedList(defaulttags);
		driver.waitForPageToBeReady();
		int totFolderCount1 = sgpage.getSgDefaultTag().size();
		if (totFolderCount1 == 1) {
			baseClass.passedStep("Default Tags labels Duplicates is NOT appear on Security Group mapping screen.");

		} else {
			baseClass.failedStep("Default Tags labels Duplicates is appear on mappling screen");
		}
	}

	/**
	 * @author Krishna Date-08/11/22 RPMXCON-54867
	 * @throws Exception
	 * @Description Verify after impersonated AS PAU in domain project where DAU is
	 *              PAU and select the other domain project/non-domain from header
	 *              drop down ,should redirect to the PAU home page for the selected
	 *              project
	 * 
	 */
	@Test(description = "RPMXCON-54867", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonatedDAURedirectToPAUHomePage() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54867");
		baseClass.stepInfo(
				"Verify after impersonated AS PAU in domain project where DAU is PAU and select the other domain project/non-domain from header drop down ,should redirect to the PAU home page for the selected project");
		DataSets data = new DataSets(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in DAU");

		baseClass.stepInfo("Impersonate DA to PA");
		driver.waitForPageToBeReady();
		baseClass.impersonateDAtoPA();

		baseClass.stepInfo("Select other Domain project");
		driver.waitForPageToBeReady();
		baseClass.selectproject(Input.projectName);

		driver.waitForPageToBeReady();
		if (data.getDatasetBtn().isDisplayed()) {
			baseClass.passedStep("It is redirect to the PAU home page for the selected project Successfully");
		} else {
			baseClass.failedStep("verification failed");
		}
		loginPage.logout();
	}

	/**
	 * @author Krishna Date: Modified date:N/A Modified by: Description Verify that
	 *         if PAU impersonate as RMU,can create and generate the new production
	 * 
	 */
	@Test(description = "RPMXCON-54869", enabled = true, groups = { "regression" })
	public void verifyPAUImpersonateRmuGenerateNewProduction() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("RPMXCON-54869");
		baseClass.stepInfo("Verify that if PAU impersonate as RMU,can create and generate the new production");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "p" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as PAU");
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("Redirect to Rmu dashboard");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		baseClass.stepInfo("New Tag created with classification " + tagname);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		baseClass.passedStep("Production generate completed successfully");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author krishna ModifyDate:02/08/2022 RPMXCON-54307
	 * @throws Exception
	 * @Description To verify that when the SG is set to use SG-specific attributes
	 *              then Folder Propagation should happens using
	 *              EmailDuplicateDocIDs attribute.
	 * 
	 */
	@Test(description = "RPMXCON-54307", enabled = true, groups = { "regression" })
	public void verifySgAttributesFolderPropogationUsingEmailDuplicateDocIds() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54307");
		baseClass.stepInfo(
				"To verify that when the SG is set to use SG-specific attributes then Folder Propagation should happens using EmailDuplicateDocIDs attribute.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		String Foldername = "Test" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as PA");
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		sgpage.createSecurityGroups(SGname);
		baseClass.stepInfo(SGname + "is created successfully");
		baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(1));
		sgpage.getSG_GenerateEmailRadioButton(1).waitAndClick(3);

		// Release the doc to security group
		sessionsearch.basicContentSearch(Input.searchStringStar);
		sessionsearch.bulkRelease(SGname);
		baseClass.passedStep("  Document released to Selected.." + SGname);
		driver.waitForPageToBeReady();
		sgpage.navigateToSecurityGropusPageURL();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSecurityGroupList());
		sgpage.getSecurityGroupList().selectFromDropdown().selectByVisibleText(SGname);
		baseClass.stepInfo(SGname + "..Selected as expected");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(1));
		sgpage.getSG_GenerateEmailRadioButton(2).waitAndClick(3);
		baseClass.waitForElement(sgpage.getSG_GenerateEmailButton());
		sgpage.getSG_GenerateEmailButton().waitAndClick(3);
		baseClass.passedStep("Generate button is clicked successfully");
		baseClass.waitForElement(sgpage.getYesButton());
		sgpage.getYesButton().waitAndClick(2);
		baseClass.waitTime(5);
		sgpage.getNotificationMessage(0);
		driver.waitForPageToBeReady();
		String Expected = sgpage.getSGBgCompletedTask().getText();
		System.out.println(Expected);
		baseClass.waitForElement(sgpage.getSecurityGroupBgViewAll());
		sgpage.getSecurityGroupBgViewAll().waitAndClick(3);
		baseClass.stepInfo("navigated to My Background task page");
		driver.waitForPageToBeReady();
		sgpage.getSgBgStatusDropDown().selectFromDropdown().selectByVisibleText("COMPLETED");
		baseClass.waitForElement(sgpage.getSgBgFilterBtn());
		sgpage.getSgBgFilterBtn().waitAndClick(2);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSGBgCompletedId());
		String Actual = sgpage.getSGBgCompletedId().getText();
		System.out.println(Actual);
		if (Expected.contains(Actual)) {
			baseClass.passedStep(Actual + ".. status is displayed as Completed successfully");

		} else {
			baseClass.failedMessage("failed");
		}
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as RMU");

		// Release the doc to security group
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkFolderDuplicateEmailDocs(Foldername);
		baseClass.passedStep(
				Foldername + "...Selected documents email duplicates to selected folder is successfully associated");
		loginPage.logout();
	}

	/**
	 * @author Krishna Date: Modified date:N/A Modified by: Description :Verify that
	 *         user can not enter external scripts in Create Security Group screen
	 */
	@Test(description = "RPMXCON-54907", enabled = true, groups = { "regression" })
	public void verifyUserCannotExternalScriptInSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54907");
		baseClass.stepInfo("Verify that user can not enter external scripts in Create Security Group screen");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		SoftAssert softassert = new SoftAssert();
		String SGname = "<script>alert(3)</script>";
		String SGname1 = "\\/()~`!@#$%^&*,;. |";

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);

		sgpage.navigateToSecurityGropusPageURL();
		sgpage.createSecurityGroups(SGname);
		String Actualwarningmsg = "The security group name is invalid.";
		baseClass.waitTime(5);
		String expectWarningmsg = sgpage.getSG_InvalidErrorMsg().getText();
		System.out.println(expectWarningmsg);
		baseClass.stepInfo("Expected warning message..." + expectWarningmsg);
		driver.waitForPageToBeReady();
		if (Actualwarningmsg.equals(expectWarningmsg)) {
			baseClass.passedStep(expectWarningmsg + "   Warning message is displayed successfully");
		} else {
			baseClass.failedStep("Warning msg is not displayed");
		}

		baseClass.waitForElement(sgpage.getSG_NameCancelBtn());
		sgpage.getSG_NameCancelBtn().waitAndClick(5);

		driver.waitForPageToBeReady();
		softassert.assertFalse(sgpage.getSG_PopUp().isElementPresent());
		baseClass.passedStep("Security Group is deleted and no pop up  displayed as expected.");
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		sgpage.createSecurityGroups(SGname1);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		softassert.assertTrue(sgpage.getSG_PopUp().isElementPresent());
		baseClass.passedStep("User has been able to use the listed chars. as expected");
		sgpage.deleteSecurityGroups(SGname1);
		loginPage.logout();
	}

	/**
	 * @author Krishna Date: Modified date:N/A Modified by: Description :To verify
	 *         that project admin can assign/unassign Comments for selected security
	 *         group.
	 */
	@Test(description = "RPMXCON-53685", enabled = true, groups = { "regression" })
	public void verifyPAAssignUnAssignCommentsForSelectingSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53685");
		baseClass.stepInfo("To verify that project admin can assign/unassign Comments for selected security group.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		CommentsPage comments = new CommentsPage(driver);
		String addComment = "comment" + Utility.dynamicNameAppender();
		SoftAssert softassert = new SoftAssert();
		String comment = "Comments";
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		comments.AddComments(addComment);
		driver.waitForPageToBeReady();
		sgpage.navigateToSecurityGropusPageURL();
		driver.waitForPageToBeReady();
		baseClass.waitTime(8);
		sgpage.createSecurityGroups(SGname);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSecurityGroupList());
		sgpage.getSecurityGroupList().selectFromDropdown().selectByVisibleText(SGname);
		baseClass.stepInfo(SGname + "..Selected as expected");
		baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(comment));
		sgpage.getSGSelectAccessControlTages(comment).waitAndClick(3);

		// verify Comments selected and assign/UnAssign
		sgpage.verifySelectCommentisAssignedInSelectedList(addComment);
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.waitTime(5);
		baseClass.waitTillElemetToBeClickable(sgpage.getSelectedCommentsCheckBox(addComment));
		sgpage.getSelectedCommentsCheckBox(addComment).waitAndClick(5);
		baseClass.waitForElement(sgpage.getSG_Comment_Left());
		sgpage.getSG_Comment_Left().waitAndClick(3);
		baseClass.waitForElement(sgpage.getCommentsCheckBox(addComment));
		if (sgpage.getCommentsCheckBox(addComment).isElementAvailable(5)) {
			baseClass.passedStep(addComment + "is displayed on available list");

		} else {
			baseClass.failedMessage("folder is not displayed");

		}
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.waitForElement(sgpage.getCommentsCheckBox(addComment));
		softassert.assertTrue(sgpage.getCommentsCheckBox(addComment).isElementAvailable(5));
		baseClass.passedStep(addComment + "is unassigned from security group successfully");
		driver.waitForPageToBeReady();
		sgpage.deleteSecurityGroups(SGname);
		this.driver.getWebDriver().get(Input.url + "Comments/CommentsList");
		comments.DeleteComments(addComment);

	}

	/**
	 * @author Krishna Date: Modified date:N/A Modified by: Description :To verify
	 *         when Project Admin assigns/unassigns keywords to selected security
	 *         group
	 */
	@Test(description = "RPMXCON-53689", enabled = true, groups = { "regression" })
	public void verifyPAAssignUnAssignKeywordsForSelectingSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53689");
		baseClass.stepInfo("To verify when Project Admin assigns/unassigns keywords to selected security group.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		KeywordPage keywordPage = new KeywordPage(driver);
		SoftAssert softassert = new SoftAssert();
		String keywordname = "AAKeyword" + Utility.dynamicNameAppender();
		String colour = "Blue";
		String keyword = "Keywords";
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		// Add keywords
		keywordPage.navigateToKeywordPage();
		baseClass.stepInfo("Add keyword");
		keywordPage.addKeyword(keywordname, colour);
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.createSecurityGroups(SGname);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSecurityGroupList());
		sgpage.getSecurityGroupList().selectFromDropdown().selectByVisibleText(SGname);
		baseClass.stepInfo(SGname + "..Selected as expected");
		baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(keyword));
		sgpage.getSGSelectAccessControlTages(keyword).waitAndClick(3);

		// verify keywords selected and assign/UnAssign
		baseClass.waitTime(8);
		sgpage.verifySelectKeywordsAssignedInSelectedList(keywordname);
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		baseClass.waitForElement(sgpage.getSecurityGroupList());
		sgpage.getSecurityGroupList().selectFromDropdown().selectByVisibleText(SGname);
		baseClass.stepInfo(SGname + "..Selected As expected");
		baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(keyword));
		sgpage.getSGSelectAccessControlTages(keyword).waitAndClick(3);
		softassert.assertTrue(sgpage.getKeyWordsCheckBox(keywordname).isElementAvailable(5));
		baseClass.stepInfo(keywordname + "is Displayed on available list");
		baseClass.waitTime(5);
		baseClass.waitTillElemetToBeClickable(sgpage.getSelectedKeyWordCheckBox(keywordname));
		sgpage.getSelectedKeyWordCheckBox(keywordname).waitAndClick(5);
		baseClass.waitForElement(sgpage.getSG_Keyword_Left());
		sgpage.getSG_Keyword_Left().waitAndClick(3);
		baseClass.waitForElement(sgpage.getKeyWordsCheckBox(keywordname));
		if (sgpage.getKeyWordsCheckBox(keywordname).isElementAvailable(5)) {
			baseClass.passedStep(keywordname + "is selected and displayed on available list");

		} else {
			baseClass.failedMessage("folder is not displayed");

		}
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(20);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.waitForElement(sgpage.getKeyWordsCheckBox(keywordname));
		softassert.assertTrue(sgpage.getKeyWordsCheckBox(keywordname).isElementAvailable(5));
		baseClass.passedStep(keywordname + "is unrealesed from " + SGname + " successfully");
		softassert.assertAll();
		driver.waitForPageToBeReady();
		// delete keyword word
		sgpage.deleteSecurityGroups(SGname);
		driver.waitForPageToBeReady();
		keywordPage.navigateToKeywordPage();
		keywordPage.deleteKeyword(keywordname);
	}

	/**
	 * 
	 * @author Krishna Date: Modified date:N/A Modified by: DescriptionTo verify
	 *         that project admin can assign/unassign Annotation Layer for selected
	 *         security group.
	 */

	@Test(description = "RPMXCON-54090", enabled = true, groups = { "regression" })

	public void verifyPAAssignUnAssignAnnotationLayerSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54090");
		baseClass.stepInfo(
				"To verify that project admin can assign/unassign Annotation Layer for selected security group.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		String addName = "test" + Utility.dynamicNameAppender();
		AnnotationLayer annotationLayer = new AnnotationLayer(driver);
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		annotationLayer.AddAnnotation(addName);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		sgpage.createSecurityGroups(SGname);
		baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSelectSecurityGroup());
		sgpage.getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(SGname);
		sgpage.getAssignedAnnotationLayerAddedSg(addName);
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(5);
		baseClass.VerifySuccessMessage("Your selections were saved successfully");
		baseClass.CloseSuccessMsgpopup();
		sgpage.verifyAssignedlayertoSgInWarningMsg();
		sgpage.unAssigningTheTagInAnnotation(addName, addName);
		sgpage.deleteSecurityGroups(SGname);
		driver.waitForPageToBeReady();
		annotationLayer.deleteAnnotationByPagination(addName);

	}

	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                 RPMXCON-53911
	 * @throws Exception
	 * @Description To verify that user can unrelease the tag from the security
	 *              group.
	 */
	@Test(description = "RPMXCON-53911", enabled = true, groups = { "regression" })
	public void verifyUserUnrealseTagFromSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53911");
		baseClass.stepInfo("To verify that user can unrelease the tag from the security group.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		String tagname = "AANew" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		sgpage.createSecurityGroups(SGname);
		System.out.println(SGname);
		baseClass.CloseSuccessMsgpopup();
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);
		tagsAndFolderPage.getTagName(tagname).waitAndClick(5);
		tagsAndFolderPage.selectActionArrow("Bulk release");
		tagsAndFolderPage.verifyBulk_releaseCount(SGname);
		baseClass.CloseSuccessMsgpopup();
		baseClass.stepInfo(tagname + "   Selected tag is Released");

		driver.waitForPageToBeReady();
		tagsAndFolderPage.getTagName(tagname).waitAndClick(5);
		tagsAndFolderPage.selectActionArrow("Bulk release");
		tagsAndFolderPage.verifyBulk_UnreleaseTagInSg(SGname);
		baseClass.stepInfo(tagname + "   Selected tag is unReleased");
		sgpage.deleteSecurityGroups(SGname);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as in  " + Input.rmu1FullName);
		String tagname1 = "AANew" + UtilityLog.dynamicNameAppender();
		tagsAndFolderPage.CreateTag(tagname1, Input.securityGroup);
		tagsAndFolderPage.verifyTagDocCount(tagname1, 0);
		baseClass.passedStep(tagname1 + "   Tag is count is displayed as 0 successfully");

	}

	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                 RPMXCON-53912
	 * @throws Exception
	 * @Description To verify that folder can unrelease from the security group.
	 */
	@Test(description = "RPMXCON-53912", enabled = true, groups = { "regression" })
	public void verifyUserUnrealseFolderFromSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53912");
		baseClass.stepInfo("To verify that folder can unrelease from the security group.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		String Foldername = "AANew" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		sgpage.createSecurityGroups(SGname);
		System.out.println(SGname);
		baseClass.CloseSuccessMsgpopup();
		tagsAndFolderPage.CreateFolder(Foldername, Input.securityGroup);
		driver.waitForPageToBeReady();
		tagsAndFolderPage.bulkReleaseFolderInSg(Foldername, SGname);
		baseClass.stepInfo(Foldername + "   Selected Folder is Released");

		driver.waitForPageToBeReady();
		tagsAndFolderPage.getFolderName(Foldername).waitAndClick(5);
		tagsAndFolderPage.verifyBulk_UnreleaseFolderInSg(SGname);
		baseClass.stepInfo(Foldername + "   Selected Folder is unReleased");
		sgpage.deleteSecurityGroups(SGname);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as in  " + Input.rmu1FullName);
		String Foldername1 = "AANew" + UtilityLog.dynamicNameAppender();
		tagsAndFolderPage.CreateFolderInRMU(Foldername1);
		tagsAndFolderPage.verifyFolderDocCount(Foldername1, 0);
		baseClass.passedStep(Foldername1 + "   Folder count is displayed as 0 successfully");

	}

	/**
	 * @author Brundha Date:22/09/2022 RPMXCON-53672
	 * @Description To verify that Project Admin can view the Security group page
	 *              details.
	 * 
	 */
	@Test(description = "RPMXCON-53672", enabled = true, groups = { "regression" })
	public void verifyingSecurityGroupDetails() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53672");
		baseClass.stepInfo("To verify that Project Admin can view the Security group page details.");

		SecurityGroupsPage SG = new SecurityGroupsPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String securityGroup = "SG" + Utility.dynamicNameAppender();
		SG.createSecurityGroups(securityGroup);

		baseClass.stepInfo("verifying Security group page details");
		baseClass.ValidateElement_Presence(SG.getProjectSelector(), "Project Selector");

		baseClass.ValidateElement_Presence(SG.getReductionTagLink(), "Redaction Tag");
		baseClass.ValidateElement_Presence(SG.getCommentLink(), "Comments");
		baseClass.ValidateElement_Presence(SG.getProjectFieldsLink(), "Project Field");
		baseClass.ValidateElement_Presence(SG.getKeywordsLink(), "Keywords");
		baseClass.ValidateElement_Presence(SG.getTagsLink(), "Tags");
		baseClass.ValidateElement_Presence(SG.getAnnotationLayerLink(), "Annotation layer");
		baseClass.ValidateElement_Presence(SG.getReductionTagLink(), "Redaction Tag");

		baseClass.ValidateElement_Presence(SG.getSecurityGroupCreateButton(), "Create Button");
		baseClass.ValidateElement_Presence(SG.getRenameBtn(), "Rename Button");
		baseClass.ValidateElement_Presence(SG.SG_deleteButton(), "Delete Button");
		baseClass.ValidateElement_Presence(SG.getSG_AnnSaveButton(), "Save Button");
		baseClass.ValidateElement_Presence(SG.getSG_CancelBtn(), "Cancel Button");
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		if (SG.getRadioBn().Selected()) {
			baseClass.passedStep("Project level Email is selected as default");
		} else {
			baseClass.failedStep("Project level Email is not selected");
		}
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		driver.waitForPageToBeReady();
		SG.getSecurityGroupList().waitAndClick(5);
		baseClass.waitForElement(SG.selectSGFromDropdown(securityGroup));
		SG.selectSGFromDropdown(securityGroup).waitAndClick(2);
		baseClass.waitForElement(SG.SG_deleteButton());
		SG.SG_deleteButton().Click();
		baseClass.getNOBtn().waitAndClick(5);
		baseClass.ValidateElement_Presence(SG.getSGGrpList(securityGroup), securityGroup);
		SG.deleteSecurityGroups(securityGroup);
		baseClass.ValidateElement_Absence(SG.getSGGrpList(securityGroup), securityGroup);

	}

}
