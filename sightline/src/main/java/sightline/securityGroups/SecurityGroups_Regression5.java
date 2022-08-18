package sightline.securityGroups;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.constraints.Size;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SecurityGroups_Regression5 {
	
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
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
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
		
		//verify multiple selected tags are assign/UnAssign for listed fields 
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
	public void verifyBellyBandWarningMsgDisplayClickingRegenrateBtn() throws Exception {

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



}
