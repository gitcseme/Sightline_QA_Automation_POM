package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.NoSuchElementException;

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
import junit.framework.Assert;
import pageFactory.BaseClass;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TagAndFolder {
	Driver driver;
	LoginPage lp;
	HomePage hm;
	BaseClass bc;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod)
			throws IOException, ParseException, InterruptedException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		driver = new Driver();
		bc = new BaseClass(driver);
		lp = new LoginPage(driver);
		softAssertion = new SoftAssert();
	}

	@DataProvider(name = "impersonateData")
	public Object[][] impersonateData() {
		Object[][] data = { { "SA", "PA" }, { "SA", "RMU" } };
		return data;
	}

	@Test(enabled = true, priority = 1, groups = { "smoke", "regression" })
	public void deleteTagSAasPA() throws ParseException, InterruptedException, IOException {

		driver = new Driver();
		// Login as PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		// Impersonate as RMU
		bc = new BaseClass(driver);
		bc.impersonateSAtoPA();

		// add tag
		String tag = "newTag" + Utility.dynamicNameAppender();
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.CreateTag(tag, "Default Security Group");
		System.out.println("Tag added Successfully : " + tag);

		// add folder
		String folder = "newFolder" + Utility.dynamicNameAppender();
		page.CreateFolder(folder, "Default Security Group");
		System.out.println("Folder added Successfully : " + folder);

		// Delete tag under security group
		page.DeleteTag(tag, "Default Security Group");

		// try to delete tag under same security group again, it should fail!
		try {
			page.DeleteTag(tag, "Default Security Group");
			Assert.assertFalse(1 == 0);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Tag successfully deleted from security group!");
		}

		// Delete from all Groups, it make sure deleting tag under security group not
		// deleted from all groups!

		page.DeleteTag(tag, "All Groups");

		// try deleting tag from all groups again to make sure its been deleted
		try {
			page.DeleteTag(tag, "All Groups");
			Assert.assertFalse(1 == 0);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Tag successfully deleted from all groups!");
			System.out.println("Tag deletion working fine!!");
		}

		page.DeleteFolder(folder, "Default Security Group");
		System.out.println("Folder deleted from security group");

		// try deleting again to confirm, it should fail
		try {
			page.DeleteFolder(folder, "Default Security Group");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Folder successfully deleted from security group");

		}
		// delete folder form all groups!
		page.DeleteFolder(folder, "All Groups");

		// try deleting folder from all groups again to make sure its been deleted
		try {
			page.DeleteFolder(folder, "All Groups");
			Assert.assertFalse(1 == 0);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("folder successfully deleted from all groups!");
			System.out.println("folder deletion working fine!!");
		}

	}

	// added by Narendra
	@Test(enabled = true, priority = 2, groups = { "smoke", "regression" })
	public void OperationOnTag() throws ParseException, IOException, InterruptedException {
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Add tag
		String tag = "newTag" + Utility.dynamicNameAppender();
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.CreateTag(tag, "Default Security Group");
		System.out.println("Tag added Successfully : " + tag);

		// Cancel tag
		page.Tags(tag, "Default Security Group");
		System.out.println("Tag modification cancelled successfully");

		// Edit Tag Group
		page.TagGroup("Default Security Group");
		System.out.println("Tag group modified successfully");

		// Delete tag under security group
		page.DeleteTag(tag, "Default Security Group");
		System.out.println("Tag successfully deleted from security group!");

	}

	@Test(enabled = true, priority = 3, groups = { "smoke", "regression" })
	public void OperationOnFolder() throws ParseException, IOException, InterruptedException {
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// add folder
		String folder = "newFolder" + Utility.dynamicNameAppender();
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.CreateFolder(folder, "Default Security Group");
		System.out.println("Folder added Successfully : " + folder);

		// Delete tag under security group
		page.DeleteFolder(folder, "Default Security Group");
		System.out.println("Folder deleted from security group");

	}

	/**
	 * @author Raghuram A Date: 01/18/21 Modified date:1/24/21 Modified by: Raghuram
	 *         A Description : Verify that after Impersonation of SA User - can
	 *         edit/delete Tag Group name appropriately that have created by other
	 *         user on "Tags and Folders" >> Tags screen - RPMXCON-59219 Sprint 10
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void tagGroupActions() throws Exception {

		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		String TagFolderNamePA = "aTagFolder" + Utility.dynamicNameAppender();
		String TagFolderNameRMU = "aTagFolderR" + Utility.dynamicNameAppender();
		String ReNamedTagFolderName = "aReTagFolder" + Utility.dynamicNameAppender();
		String ReNamedTagFolderNameRMU = "aReTagFolderR" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-59219 - TagsAndFolder Sprint 09");
		bc.stepInfo(
				"Verify that after Impersonation of SA User - can edit/delete Tag Group name appropriately that have created  by other user on \"Tags and Folders\" >> Tags screen");

		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Logged in as : " + Input.rmu1FullName);

		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallTagRoot();
		tagAndFolderPage.createTagGroup(Input.securityGroup, TagFolderNamePA, "Success", null);

		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallTagRoot();
		tagAndFolderPage.createTagGroup(Input.securityGroup, TagFolderNameRMU, "Success", null);
		lp.logout();

		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		bc.rolesToImp("SA", "PA");

		// updates
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallTagRoot();
		tagAndFolderPage.verifyNodePresent(TagFolderNamePA, true, "Tag");
		tagAndFolderPage.editTagGroup(Input.securityGroup, TagFolderNamePA, ReNamedTagFolderName, "Success", null);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllTagsGroups(ReNamedTagFolderName, "Success");

		bc.rolesToImp("PA", "RMU");

		// updates
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallTagRoot();
		tagAndFolderPage.verifyNodePresent(TagFolderNameRMU, true, "Tag");
		tagAndFolderPage.editTagGroup(Input.securityGroup, TagFolderNameRMU, ReNamedTagFolderNameRMU, "Success", null);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllTagsGroups(ReNamedTagFolderNameRMU, "Success");

		lp.logout();
	}

	/**
	 * @author Raghuram A Date: 01/18/21 MModified date:1/24/21 Modified by:
	 *         Raghuram A Description : Verify that after Impersonation of SA User -
	 *         can edit/delete Folder Group name appropriately that have created by
	 *         other user on "Tags and Folders" >> Folders screen - RPMXCON-59220
	 *         Sprint 10
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void folderGroupActions() throws Exception {

		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		String folderGroupNamePA = "aFolderGroup" + Utility.dynamicNameAppender();
		String folderGroupNameRMU = "aFolderGroupR" + Utility.dynamicNameAppender();
		String ReNamedfolderGroupNamePA = "aFolderGroup" + Utility.dynamicNameAppender();
		String ReNamedfolderGroupNameRMU = "aFolderGroupR" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-59220 - TagsAndFolder Sprint 09");
		bc.stepInfo(
				"Verify that after Impersonation of SA User - can edit/delete Folder Group name appropriately that have created  by other user on \"Tags and Folders\" >> Folders screen");

		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Logged in as : " + Input.rmu1FullName);

		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallFolderRoot();
		tagAndFolderPage.createFolderGroup(Input.securityGroup, folderGroupNamePA, "Success", null);

		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallFolderRoot();
		tagAndFolderPage.createFolderGroup(Input.securityGroup, folderGroupNameRMU, "Success", null);
		lp.logout();

		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		bc.rolesToImp("SA", "PA");

		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallFolderRoot();
		tagAndFolderPage.verifyNodePresent(folderGroupNamePA, true, "Folder");
		tagAndFolderPage.editFolderGroup(Input.securityGroup, folderGroupNamePA, ReNamedfolderGroupNamePA, "Success",
				null);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllFolderGroup(ReNamedfolderGroupNamePA, "Success");

		bc.rolesToImp("PA", "RMU");

		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallFolderRoot();
		tagAndFolderPage.verifyNodePresent(folderGroupNameRMU, true, "Folder");
		tagAndFolderPage.editFolderGroup(Input.securityGroup, folderGroupNameRMU, ReNamedfolderGroupNameRMU, "Success",
				null);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllFolderGroup(ReNamedfolderGroupNameRMU, "Success");

		lp.logout();
	}

	/**
	 * @author Raghuram A Date: 01/18/21 Modified date:N/A Modified by: Description
	 *         : Verify that after Impersonation af SA User - can edit/delete folder
	 *         names appropriately that have created by other user on 'Tags and
	 *         Folders'>> Folders screen - RPMXCON-59218 Sprint 11
	 */
	@Test(enabled = true, groups = { "regression" }, dataProvider = "impersonateData", priority = 6)
	public void verifyAfterImpersonateUserEditAndDeleteFolder(String fromRole, String toRole) throws Exception {

		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		String folder = "newFolder" + Utility.dynamicNameAppender();
		String renamedFolder = "renamedFolder" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-59217 TagsAndFolder Sprint 11");
		bc.stepInfo(
				"Verify that after Impersonation af SA User - can edit/delete folder names appropriately that have created by other user on 'Tags and Folders'>> Folders screen");

		// login as RMU
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Logged in as : " + Input.rmu1FullName);

		// Create folder
		tagAndFolderPage.CreateFolder(folder, Input.securityGroup);

		lp.logout();

		// login as SA
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);

		// Impersonate into PA/RMU
		bc.rolesToImp(fromRole, toRole);

		// Rename folder
		tagAndFolderPage.selectallFolderRoot();
		tagAndFolderPage.verifyNodePresent(folder, true, "Folder");
		tagAndFolderPage.editFolder(Input.securityGroup, folder, renamedFolder, "Success", null);

		// Delete folder
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllFolders(renamedFolder);

		lp.logout();

	}

	/**
	 * @author Raghuram A Date: 01/18/21 Modified date:N/A Modified by: Description
	 *         : Verify that after Impersonation af SA User - can edit/delete Tag
	 *         names appropriately that have created by other user on 'Tags and
	 *         Folders'>> Folders screen - RPMXCON-59218 Sprint 11
	 */
	@Test(enabled = true, groups = { "regression" }, dataProvider = "impersonateData", priority = 7)
	public void verifyAfterImpersonateUserEditAndDeleteTag(String fromRole, String toRole) throws Exception {
		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		String Tag = "newTag" + Utility.dynamicNameAppender();
		String renamedTag = "renamedTag" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-59218 TagsAndFolder Sprint 11");
		bc.stepInfo(
				"Verify that after Impersonation af SA User - can edit/delete Tag names appropriately that have created by other user on 'Tags and Folders'>> Folders screen");

		// login as RMU
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Logged in as : " + Input.rmu1FullName);

		// Create folder
		tagAndFolderPage.CreateTag(Tag, Input.securityGroup);

		lp.logout();

		// login as SA
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);

		// Impersonate into PA/RMU
		driver.waitForPageToBeReady();
		bc.rolesToImp(fromRole, toRole);

		// Rename Tag
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallTagRoot();
		tagAndFolderPage.verifyNodePresent(Tag, true, "Tag");
		tagAndFolderPage.editTag(Input.securityGroup, Tag, renamedTag, "Success", null);

		// Delete Tag
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllTags(renamedTag);

		lp.logout();
	}

	/**
	 * @author Raghuram A Date: 01/25/21 Modified date:N/A Modified by: Description
	 *         :Verify that after Impersonation of SA User - can edit/delete Tag
	 *         Group name appropriately that have created on \"Tags and Folders\" >>
	 *         Folders screen - RPMXCON-59210 Sprint 11
	 */
	@Test(enabled = true, groups = { "regression" }, dataProvider = "impersonateData", priority = 8)
	public void verifyAfterImpersonateUserEditAndDeleteTagGroup(String fromRole, String toRole) throws Exception {
		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		String tagGroup = "newTagGroup" + Utility.dynamicNameAppender();
		String renamedTagGroup = "renamedTagGroup" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-59210 TagsAndFolder Sprint 11");
		bc.stepInfo(
				"Verify that after Impersonation of SA User - can edit/delete Tag Group name appropriately that have created on \"Tags and Folders\" >> Folders screen");

		// login as SA
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);

		// Impersonate into PA/RMU
		driver.waitForPageToBeReady();
		bc.rolesToImp(fromRole, toRole);

		// Create Folder group
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallTagRoot();
		tagAndFolderPage.createTagGroup(Input.securityGroup, tagGroup, "Success", null);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		// Rename Folder group
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallTagRoot();
		tagAndFolderPage.verifyNodePresent(tagGroup, true, "Tag");
		tagAndFolderPage.editTagGroup(Input.securityGroup, tagGroup, renamedTagGroup, "Success", null);

		// Delete folder group
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllTagsGroups(renamedTagGroup, "Success");

		lp.logout();
	}

	/**
	 * @author Raghuram A Date: 01/25/21 Modified date:N/A Modified by: Description
	 *         :Verify that after Impersonation of SA User - can edit/delete Folder
	 *         Group name appropriately that have created on "Tags and Folders" >>
	 *         Folders screen - RPMXCON-59211 Sprint 11
	 */
	@Test(enabled = true, groups = { "regression" }, dataProvider = "impersonateData", priority = 9)
	public void verifyAfterImpersonateUserEditAndDeleteFolderGroup(String fromRole, String toRole) throws Exception {
		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		String folderGroup = "newFolderGroup" + Utility.dynamicNameAppender();
		String renamedFolderGroup = "renamedFolderGroup" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-59211 TagsAndFolder Sprint 11");
		bc.stepInfo(
				"Verify that after Impersonation of SA User - can edit/delete Folder Group name appropriately that have created on \"Tags and Folders\" >> Folders screen");

		// login as SA
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);

		// impersonate into PA/RMU
		driver.waitForPageToBeReady();
		bc.rolesToImp(fromRole, toRole);

		// create Folder group
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallFolderRoot();
		tagAndFolderPage.createFolderGroup(Input.securityGroup, folderGroup, "Success", null);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		// rename Folder group
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallFolderRoot();
		tagAndFolderPage.verifyNodePresent(folderGroup, true, "Folder");
		tagAndFolderPage.editFolderGroup(Input.securityGroup, folderGroup, renamedFolderGroup, "Success", null);

		// delete folder group
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllFolderGroup(renamedFolderGroup, "Success");

		lp.logout();
	}

	/**
	 * @author Raghuram A Date: 01/25/21 Modified date:N/A Modified by: Description
	 *         : Verify that after Impersonation of SA User - can edit/delete folder
	 *         names appropriately that have created on \"Tags and Folders\" >>
	 *         Folders screen - RPMXCON-59208 Sprint 11
	 */
	@Test(enabled = true, groups = { "regression" }, dataProvider = "impersonateData", priority = 10)
	public void verifyAfterImpersonateUserEditAndDeleteFolderAsSameUser(String fromRole, String toRole)
			throws Exception {

		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		String folder = "newFolder" + Utility.dynamicNameAppender();
		String renamedFolder = "renamedFolder" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-59208 TagsAndFolder Sprint 11");
		bc.stepInfo(
				"Verify that after Impersonation of SA User - can edit/delete folder names appropriately that have created on \"Tags and Folders\" >> Folders screen");

		// login as SA
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);

		// impersonate into PA/RMU
		driver.waitForPageToBeReady();
		bc.rolesToImp(fromRole, toRole);

		// create Folder group
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallFolderRoot();
		tagAndFolderPage.CreateFolder(folder, Input.securityGroup);

		// rename folder
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallFolderRoot();
		tagAndFolderPage.verifyNodePresent(folder, true, "Folder");
		tagAndFolderPage.editFolder(Input.securityGroup, folder, renamedFolder, "Success", null);

		// Delete folder
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllFolders(renamedFolder);

		lp.logout();
	}

	/**
	 * @author Jeevitha Date: 01/25/21 Modified date:N/A Modified by: Description :
	 *         Verify that after Impersonation of SA User - can edit/delete Tag
	 *         names appropriately that have created on \"Tags and Folders\" >>
	 *         Folders screen - RPMXCON-59209 Sprint 11
	 */
	@Test(enabled = true, groups = { "regression" }, dataProvider = "impersonateData", priority = 11)
	public void verifyAfterImpersonateUserEditAndDeleteTagAsSameUser(String fromRole, String toRole) throws Exception {
		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		String Tag = "newTag" + Utility.dynamicNameAppender();
		String renamedTag = "renamedTag" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-59209 TagsAndFolder Sprint 11");
		bc.stepInfo(
				"Verify that after Impersonation of SA User - can edit/delete Tag names appropriately that have created on \"Tags and Folders\" >> Folders screen");

		// login as SA
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);

		// impersonate into PA/RMU
		driver.waitForPageToBeReady();
		bc.rolesToImp(fromRole, toRole);

		// create folder
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallTagRoot();
		tagAndFolderPage.CreateTag(Tag, Input.securityGroup);

		// rename Tag
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallTagRoot();
		tagAndFolderPage.verifyNodePresent(Tag, true, "Tag");
		tagAndFolderPage.editTag(Input.securityGroup, Tag, renamedTag, "Success", null);

		// Delete Tag
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllTags(renamedTag);

		lp.logout();

	}

	/**
	 * @author Raghuram A Date: 03/21/21 Modified date:N/A Modified by: Description
	 *         :Verify that after Impersonation of PA User - can edit/delete Tag
	 *         Group name appropriately that have created on "Tags and Folders" >>
	 *         Folders screen - RPMXCON-59214 Sprint 14
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 12)
	public void verifyAfterPAImpersonateUserEditAndDeleteTagGroup() throws Exception {
		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		String tagGroup = "newTagGroup" + Utility.dynamicNameAppender();
		String renamedTagGroup = "renamedTagGroup" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-59214 TagsAndFolder Sprint 14");
		bc.stepInfo(
				"Verify that after Impersonation of PA User - can edit/delete Tag Group name appropriately that have created on \"Tags and Folders\" >> Folders screen");

		// login as PA
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Impersonate into PA/RMU
		driver.waitForPageToBeReady();
		bc.rolesToImp("PA", "RMU");

		// Create Folder group
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallTagRoot();
		tagAndFolderPage.createTagGroup(Input.securityGroup, tagGroup, "Success", null);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		// Rename Folder group
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallTagRoot();
		tagAndFolderPage.verifyNodePresent(tagGroup, true, "Tag");
		tagAndFolderPage.editTagGroup(Input.securityGroup, tagGroup, renamedTagGroup, "Success", null);

		// Delete folder group
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllTagsGroups(renamedTagGroup, "Success");
		tagAndFolderPage.verifyNodeNotPresent(renamedTagGroup, " - not present - Tag Group Deleted Successfully ",
				"Deletion failed");

		lp.logout();
	}

	/**
	 * @author Raghuram A Date: 03/21/21 Modified date:N/A Modified by: Description
	 *         : Verify that after Impersonation of PA User - can edit/delete Folder
	 *         Group name appropriately that have created on "Tags and Folders" >>
	 *         Folders screen - RPMXCON-59215 Sprint 14
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 13)
	public void verifyAfterPAImpersonateUserEditAndDeleteFolderGroup() throws Exception {
		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		String folderGroup = "newFolderGroup" + Utility.dynamicNameAppender();
		String renamedFolderGroup = "renamedFolderGroup" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-59215 TagsAndFolder Sprint 14");
		bc.stepInfo(
				"Verify that after Impersonation of PA User - can edit/delete Folder Group name appropriately that have created on \"Tags and Folders\" >> Folders screen");

		// login as PA
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);

		// impersonate into RMU
		driver.waitForPageToBeReady();
		bc.rolesToImp("PA", "RMU");

		// create Folder group
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallFolderRoot();
		tagAndFolderPage.createFolderGroup(Input.securityGroup, folderGroup, "Success", null);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		// rename Folder group
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallFolderRoot();
		tagAndFolderPage.verifyNodePresent(folderGroup, true, "Folder");
		tagAndFolderPage.editFolderGroup(Input.securityGroup, folderGroup, renamedFolderGroup, "Success", null);

		// delete folder group
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllFolderGroup(renamedFolderGroup, "Success");
		tagAndFolderPage.verifyNodeNotPresent(renamedFolderGroup, "- not present - Folder Group Deleted Successfully ",
				"Deletion failed");

		lp.logout();
	}

	/**
	 * @author Raghuram A Date: 03/22/21 Modified date:N/A Modified by: Description
	 *         : Verify that after Impersonation of PA User - can edit/delete Folder
	 *         Group name appropriately that have created on "Tags and Folders" >>
	 *         Folders screen - RPMXCON-59212 Sprint 14
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 14)
	public void verifyAfterPAImpersonateUserEditAndDeleteFolder() throws InterruptedException, IOException {

		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		String folder = "newFolder" + Utility.dynamicNameAppender();
		String renamedFolder = "renamedFolder" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-59212 TagsAndFolder Sprint 14");
		bc.stepInfo(
				"Verify that after impersonation of PA User - can edit/delete folder names appropriately that have created on 'Tags and Folders'>>Folders screen");

		// login as PA
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);

		// impersonate into RMU
		driver.waitForPageToBeReady();
		bc.rolesToImp("PA", "RMU");

		// create Folder
		tagAndFolderPage.CreateFolder(folder, Input.securityGroup);

		// rename created folder
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallFolderRoot();
		tagAndFolderPage.verifyNodePresent(folder, true, "Folder");
		tagAndFolderPage.editFolder(Input.securityGroup, folder, renamedFolder, "Success", null);

		// delete renamed folder
		tagAndFolderPage.deleteAllFolders(renamedFolder);
		tagAndFolderPage.verifyNodeNotPresent(renamedFolder, "- not present - Folder  Deleted Successfully ",
				"Deletion failed");

		// logout
		lp.logout();
	}

	/**
	 * @author Raghuram A Date: 03/22/21 Modified date:N/A Modified by: Description
	 *         : Verify that after impersonation of PA User - can edit/delete Tag
	 *         names appropriately that have created on 'Tags and Folders'>>Folders
	 *         screen - RPMXCON-59213 Sprint 14
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 15)
	public void verifyAfterPAImpersonateUserEditAndDeleteTag() throws InterruptedException, IOException {

		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		String tag = "newTag" + Utility.dynamicNameAppender();
		String renamedTag = "renamedTag" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-59213 TagsAndFolder Sprint 14");
		bc.stepInfo(
				"Verify that after impersonation of PA User - can edit/delete Tag names appropriately that have created on 'Tags and Folders'>>Folders screen");

		// login as PA
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);

		// impersonate into RMU
		driver.waitForPageToBeReady();
		bc.rolesToImp("PA", "RMU");

		// create tag
		tagAndFolderPage.CreateTag(tag, Input.securityGroup);

		// Edit created tag
		driver.waitForPageToBeReady();
		tagAndFolderPage.selectallTagRoot();
		tagAndFolderPage.verifyNodePresent(tag, true, "Folder");
		tagAndFolderPage.editTag(Input.securityGroup, tag, renamedTag, "Success", null);

		// delete edited tag
		tagAndFolderPage.deleteAllTags(renamedTag);
		tagAndFolderPage.verifyNodeNotPresent(renamedTag, "- not present - Tag  Deleted Successfully ",
				"Deletion failed");

		// logout
		lp.logout();
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				lp.logout();
			} catch (Exception e) {
//							 TODO: handle exception
			}
		}
		try {
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
			lp.clearBrowserCache();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		try {
			lp.clearBrowserCache();
		} finally {
			lp.clearBrowserCache();
		}
	}
}
