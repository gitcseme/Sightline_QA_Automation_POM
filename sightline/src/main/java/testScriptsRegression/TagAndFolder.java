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
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

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
	 * @author Raghuram A Date: 01/18/21 Modified date:N/A Modified by: Description
	 *         : Verify that after Impersonation of SA User - can edit/delete Tag
	 *         Group name appropriately that have created by other user on "Tags and
	 *         Folders" >> Tags screen - RPMXCON-59219 Sprint 10
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void tagGroupActions() throws Exception {

		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		String TagFolderNamePA = "aTagFolder" + Utility.dynamicNameAppender();
		String TagFolderNameRMU = "aTagFolderR" + Utility.dynamicNameAppender();
		String ReNamedTagFolderName = "aReTagFolder" + Utility.dynamicNameAppender();
		String ReNamedTagFolderNameRMU = "aReTagFolderR" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-59219 - Saved Search Sprint 09");
		bc.stepInfo(
				"Verify that after Impersonation of SA User - can edit/delete Tag Group name appropriately that have created  by other user on \"Tags and Folders\" >> Tags screen");

		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Logged in as : " + Input.rmu1FullName);

		tagAndFolderPage.createTagGroup(Input.securityGroup, TagFolderNamePA, "Success", null);
		tagAndFolderPage.createTagGroup(Input.securityGroup, TagFolderNameRMU, "Success", null);
		lp.logout();

		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		bc.rolesToImp("SA", "PA");

		tagAndFolderPage.editTagGroup(Input.securityGroup, TagFolderNamePA, ReNamedTagFolderName, "Success", null);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllTagsGroups(ReNamedTagFolderName, "Success");

		bc.rolesToImp("PA", "RMU");

		tagAndFolderPage.editTagGroup(Input.securityGroup, TagFolderNameRMU, ReNamedTagFolderNameRMU, "Success", null);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllTagsGroups(ReNamedTagFolderNameRMU, "Success");

		lp.logout();
	}

	/**
	 * @author Raghuram A Date: 01/18/21 Modified date:N/A Modified by: Description
	 *         : Verify that after Impersonation of SA User - can edit/delete Folder
	 *         Group name appropriately that have created by other user on "Tags and
	 *         Folders" >> Folders screen - RPMXCON-59220 Sprint 10
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void folderGroupActions() throws Exception {

		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		String folderGroupNamePA = "aFolderGroup" + Utility.dynamicNameAppender();
		String folderGroupNameRMU = "aFolderGroupR" + Utility.dynamicNameAppender();
		String ReNamedfolderGroupNamePA = "aFolderGroup" + Utility.dynamicNameAppender();
		String ReNamedfolderGroupNameRMU = "aFolderGroupR" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id: RPMXCON-59220 - Saved Search Sprint 09");
		bc.stepInfo(
				"Verify that after Impersonation of SA User - can edit/delete Folder Group name appropriately that have created  by other user on \"Tags and Folders\" >> Folders screen");

		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Logged in as : " + Input.rmu1FullName);

		tagAndFolderPage.createFolderGroup(Input.securityGroup, folderGroupNamePA, "Success", null);
		tagAndFolderPage.createFolderGroup(Input.securityGroup, folderGroupNameRMU, "Success", null);
		lp.logout();

		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		bc.rolesToImp("SA", "PA");

		tagAndFolderPage.editFolderGroup(Input.securityGroup, folderGroupNamePA, ReNamedfolderGroupNamePA, "Success",
				null);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllFolderGroup(ReNamedfolderGroupNamePA, "Success");

		bc.rolesToImp("PA", "RMU");

		tagAndFolderPage.editFolderGroup(Input.securityGroup, folderGroupNameRMU, ReNamedfolderGroupNameRMU, "Success",
				null);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagAndFolderPage.deleteAllFolderGroup(ReNamedfolderGroupNameRMU, "Success");

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
