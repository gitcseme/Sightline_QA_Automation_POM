package sightline.securityGroups;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SecurityGroup_Regression1_21 {

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
	TagsAndFoldersPage tagsAndFolderPage;

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
	 * @author Vijaya.Rani ModifyDate:19/09/2022 RPMXCON-47087
	 * @throws Exception
	 * @Description Verify that newly created annotation layer should be displayed
	 *              though the Project Admin/RMU user who created annotation layer
	 *              is in-active.
	 * 
	 */
	@Test(description = "RPMXCON-47087", enabled = true, groups = { "regression" })
	public void verifyNewlyCreatAnnotationLayerInPA() throws Exception {

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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
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

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}