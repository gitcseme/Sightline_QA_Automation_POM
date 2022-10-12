package testScriptsRegressionSprint22;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
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
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SecurityGroups_Regression22 {
	
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
		baseClass.stepInfo(tagname+"   Selected tag is Released");
		
		driver.waitForPageToBeReady();
		tagsAndFolderPage.getTagName(tagname).waitAndClick(5);
		tagsAndFolderPage.selectActionArrow("Bulk release");
		tagsAndFolderPage.verifyBulk_UnreleaseTagInSg(SGname);
		baseClass.stepInfo(tagname+"   Selected tag is unReleased");
		sgpage.deleteSecurityGroups(SGname);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as in  " + Input.rmu1FullName);
		String tagname1 = "AANew" + UtilityLog.dynamicNameAppender();
		tagsAndFolderPage.CreateTag(tagname1, Input.securityGroup);
		tagsAndFolderPage.verifyTagDocCount(tagname1, 0);
		baseClass.passedStep(tagname1 +"   Tag is count is displayed as 0 successfully");
	
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
		baseClass.stepInfo(Foldername+"   Selected Folder is Released");
		
		driver.waitForPageToBeReady();
		tagsAndFolderPage.getFolderName(Foldername).waitAndClick(5);
		tagsAndFolderPage.verifyBulk_UnreleaseFolderInSg(SGname);
		baseClass.stepInfo(Foldername+"   Selected Folder is unReleased");
		sgpage.deleteSecurityGroups(SGname);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as in  " + Input.rmu1FullName);
		String Foldername1 = "AANew" + UtilityLog.dynamicNameAppender();
		tagsAndFolderPage.CreateFolderInRMU(Foldername1);
		tagsAndFolderPage.verifyFolderDocCount(Foldername1, 0);
		baseClass.passedStep(Foldername1 +"   Folder count is displayed as 0 successfully");
	
}
	/**
	 * @author Brundha Date:22/09/2022 RPMXCON-53672
	 * @Description To verify that Project Admin can view the Security group page details.
	 * 
	 */
	@Test(description = "RPMXCON-53672", enabled = true, groups = { "regression" })
	public void verifyingSecurityGroupDetails() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53672");
		baseClass.stepInfo("To verify that Project Admin can view the Security group page details.");
		
       SecurityGroupsPage SG=new SecurityGroupsPage(driver);
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
		if(SG.getRadioBn().Selected()) {
			baseClass.passedStep("Project level Email is selected as default");
		}else {
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
		baseClass.ValidateElement_Presence(SG.getSGGrpList(securityGroup),securityGroup);
		SG.deleteSecurityGroups(securityGroup);
		baseClass.ValidateElement_Absence(SG.getSGGrpList(securityGroup),securityGroup);
		
	}
	
	
	
}
