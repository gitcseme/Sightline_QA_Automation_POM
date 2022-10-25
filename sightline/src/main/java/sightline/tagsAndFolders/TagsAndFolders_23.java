package sightline.tagsAndFolders;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TagsAndFolders_23 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	SoftAssert softAssertion;
	String tagname;
	String foldername;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		base = new BaseClass(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());

		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}

	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

	/**
	 * @author Sowndarya Testcase No:RPMXCON-53192
	 * @Description:To verify that if Tag contains Zero document and select action
	 *                 'View in Doc List', message should be displayed 'Your query
	 *                 returned no data'
	 **/
	@Test(description = "RPMXCON-53192", enabled = true, groups = { "regression" })
	public void verifyMessagePopupWithZeroDocTag() throws Exception {

		tagname = "Tag" + Utility.dynamicNameAppender();
		base.stepInfo("RPMXCON-53192");
		base.stepInfo(
				"To verify that if Tag contains Zero document and select action 'View in Doc List', message should be displayed 'Your query returned no data'");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// create tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		driver.waitForPageToBeReady();

		String selectedTag = tagsAndFolderPage.getCreatedTag(tagname).getText();
		System.out.println(selectedTag + "is selected.");

		base.waitForElement(tagsAndFolderPage.getCreatedTag(tagname));
		tagsAndFolderPage.getCreatedTag(tagname).waitAndClick(10);

		driver.scrollPageToTop();

		base.waitForElement(tagsAndFolderPage.getTagActionDropDownArrow());
		tagsAndFolderPage.getTagActionDropDownArrow().waitAndClick(10);
		base.waitForElement(tagsAndFolderPage.getTagViewDoclist());
		tagsAndFolderPage.getTagViewDoclist().waitAndClick(10);
		String expected = "There are NO documents in the tags or folders that you have selected";
		base.VerifyWarningMessage(expected);
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-53280
	 * @Description:Verify that if RMU delete the Tag which is assigned to multiple
	 *                     SG,it should deleted only from the current selected SG
	 *                     only
	 **/
	@Test(description = "RPMXCON-53280", enabled = true, groups = { "regression" })
	public void verifyRMUTagDeleteMultipleSG() throws Exception {
		String sgName1 = "Production_Security_Group" + Utility.dynamicNameAppender();
		String sgName2 = "Production_Security_Group" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		base.stepInfo("RPMXCON-53280");
		base.stepInfo("Verify that if RMU delete the Tag which is assigned to multiple SG, "
				+ "it should deleted only from the current selected SG only");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// create tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		SecurityGroupsPage sgPage = new SecurityGroupsPage(driver);
		sgPage.navigateToSecurityGropusPageURL();
		sgPage.AddSecurityGroup(sgName1);
		base = new BaseClass(driver);
		base.VerifySuccessMessage("Security group added successfully");
		sgPage.AddSecurityGroup(sgName2);
		base.VerifySuccessMessage("Security group added successfully");

		UserManagement userManage = new UserManagement(driver);
		userManage.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		userManage.assignAccessToSecurityGroups(sgName1, Input.rmu1userName);
		driver.waitForPageToBeReady();
		userManage.assignAccessToSecurityGroups(sgName2, Input.rmu1userName);

		sgPage.navigateToSecurityGropusPageURL();
		driver.waitForPageToBeReady();
		sgPage.addTagToSecurityGroup(sgName1, tagname);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		sgPage.addTagToSecurityGroup(sgName2, tagname);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base = new BaseClass(driver);
		base.selectsecuritygroup(Input.securityGroup);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		base.selectsecuritygroup(Input.securityGroup);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.verifyNodeNotPresent(tagname, "Tag :" + tagname + "Not Present",
				"Tag :" + tagname + "Present");
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.verifyTagDocCount(tagname, 0);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base = new BaseClass(driver);
		base.selectsecuritygroup(sgName1);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.verifyTagDocCount(tagname, 0);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base = new BaseClass(driver);
		base.selectsecuritygroup(sgName2);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.verifyTagDocCount(tagname, 0);
		base.stepInfo("Verified that if RMU delete the Tag which is assigned to multiple SG, "
				+ "it should deleted only from the current selected SG only");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-53281
	 * @Description:Verify that if RMU delete the Folder which is assigned to
	 *                     multiple SG,it should deleted only from the current
	 *                     selected SG only
	 **/
	@Test(description = "RPMXCON-53281", enabled = true, groups = { "regression" })
	public void verifyRMUFolderDeleteMultipleSG() throws Exception {
		String sgName1 = "Production_Security_Group" + Utility.dynamicNameAppender();
		String sgName2 = "Production_Security_Group" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXCON-53281");
		base.stepInfo("Verify that if RMU delete the Folder which is assigned to multiple SG, "
				+ "it should deleted only from the current selected SG only");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// create tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolderInRMU(folderName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		SecurityGroupsPage sgPage = new SecurityGroupsPage(driver);
		sgPage.navigateToSecurityGropusPageURL();
		sgPage.AddSecurityGroup(sgName1);
		base = new BaseClass(driver);
		base.VerifySuccessMessage("Security group added successfully");
		sgPage.AddSecurityGroup(sgName2);
		base.VerifySuccessMessage("Security group added successfully");

		UserManagement userManage = new UserManagement(driver);
		userManage.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		userManage.assignAccessToSecurityGroups(sgName1, Input.rmu1userName);
		driver.waitForPageToBeReady();
		userManage.assignAccessToSecurityGroups(sgName2, Input.rmu1userName);

		sgPage.navigateToSecurityGropusPageURL();
		driver.waitForPageToBeReady();
		sgPage.addFolderToSecurityGroup(sgName1, folderName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		sgPage.addFolderToSecurityGroup(sgName2, folderName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base = new BaseClass(driver);
		base.selectsecuritygroup(Input.securityGroup);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(folderName);

		base.selectsecuritygroup(Input.securityGroup);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.verifyNodeNotPresent(folderName, "Folder :" + folderName + "Not Present",
				"Folder :" + folderName + "Present");
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		tagsAndFolderPage.verifyFolderDocCount(folderName, 0);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base = new BaseClass(driver);
		base.selectsecuritygroup(sgName1);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		tagsAndFolderPage.verifyFolderDocCount(folderName, 0);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base = new BaseClass(driver);
		base.selectsecuritygroup(sgName2);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		tagsAndFolderPage.verifyFolderDocCount(folderName, 0);
		base.stepInfo("Verified that if RMU delete the Folder which is assigned to multiple SG, "
				+ "it should deleted only from the current selected SG only");
		loginPage.logout();

	}

	/**
	 * @author NA Testcase No:RPMXCON-52477
	 * @Description:To verify validation/move/delete a New Tag
	 **/
	@Test(description = "RPMXCON-52477", enabled = true, groups = { "regression" })
	public void verifyValidateMoveDeleteNewTag() throws Exception {

		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String tagname2 = "Tag" + Utility.dynamicNameAppender();
		SoftAssert asserts = new SoftAssert();
		String tagGroup = "Tag Group" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		base.stepInfo("To verify validation/move/delete a New Tag");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in As : " + Input.pa1userName);

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		base.waitForElement(tagsAndFolderPage.getSecurityGroupTag());
		tagsAndFolderPage.getSecurityGroupTag().selectFromDropdown().selectByVisibleText(Input.securityGroup);
		base.waitForElement(tagsAndFolderPage.getAddTagTable());
		tagsAndFolderPage.getAddTagTable().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(tagsAndFolderPage.getAllTagRoot());
		tagsAndFolderPage.getAllTagRoot().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getTagActionDropDownArrow());
		tagsAndFolderPage.getTagActionDropDownArrow().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getAddTag());
		tagsAndFolderPage.getAddTag().waitAndClick(10);
		base.waitForElement(tagsAndFolderPage.getTagClassification());
		tagsAndFolderPage.getTagClassification().waitAndClick(3);

		List<String> actOrder = new ArrayList<String>();
		List<String> expOrder = new ArrayList<String>();
		expOrder.add("Select Tag Classification");
		expOrder.add("Responsive");
		expOrder.add("Privileged");
		expOrder.add("Private Data");
		expOrder.add("Technical Issue");

		List<WebElement> tagsclss = tagsAndFolderPage.getAllTagClassification().FindWebElements();
		driver.waitForPageToBeReady();
		System.out.println(tagsclss);
		for (int k = 0; k < tagsclss.size(); k++) {
			actOrder.add(tagsclss.get(k).getText());
		}
		base.stepInfo("Actual" + actOrder);
		base.stepInfo("Expected " + expOrder);
		asserts.assertEquals(expOrder, actOrder);
		asserts.assertAll();

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		base.waitForElement(tagsAndFolderPage.getSecurityGroupTag());
		tagsAndFolderPage.getSecurityGroupTag().selectFromDropdown().selectByVisibleText(Input.securityGroup);
		base.waitForElement(tagsAndFolderPage.getAddTagTable());
		tagsAndFolderPage.getAddTagTable().waitAndClick(5);
		driver.scrollPageToTop();
		base.waitForElement(tagsAndFolderPage.getAllTagRoot());
		tagsAndFolderPage.getAllTagRoot().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getTagActionDropDownArrow());
		tagsAndFolderPage.getTagActionDropDownArrow().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getAddTag());
		tagsAndFolderPage.getAddTag().waitAndClick(10);
		base.waitForElement(tagsAndFolderPage.getSaveTag());
		tagsAndFolderPage.getSaveTag().waitAndClick(10);
		asserts.assertTrue(tagsAndFolderPage.getTagErrorMsg().isElementAvailable(10));

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		base.waitForElement(tagsAndFolderPage.getHelpButton());
		tagsAndFolderPage.getHelpButton().waitAndClick(3);
		asserts.assertTrue(tagsAndFolderPage.getHelpText().isElementAvailable(10));
		asserts.assertAll();

		
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		base.waitForElement(tagsAndFolderPage.getSecurityGroupTag());
		tagsAndFolderPage.getSecurityGroupTag().selectFromDropdown().selectByVisibleText(Input.securityGroup);
		base.waitForElement(tagsAndFolderPage.getAddTagTable());
		tagsAndFolderPage.getAddTagTable().waitAndClick(5);
		driver.scrollPageToTop();
		base.waitForElement(tagsAndFolderPage.getAllTagRoot());
		tagsAndFolderPage.getAllTagRoot().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getTagActionDropDownArrow());
		tagsAndFolderPage.getTagActionDropDownArrow().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(tagsAndFolderPage.getAddTag());
		tagsAndFolderPage.getAddTag().waitAndClick(10);
		base.waitForElement(tagsAndFolderPage.getTagName());
		tagsAndFolderPage.getTagName().SendKeys(tagname);
		base.waitForElement(tagsAndFolderPage.getSaveTag());
		tagsAndFolderPage.getSaveTag().waitAndClick(10);
		base.VerifyErrorMessage("80001000001 : An object with same name already exists.");
		
		
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		base.waitForElement(tagsAndFolderPage.getSecurityGroupTag());
		tagsAndFolderPage.getSecurityGroupTag().selectFromDropdown().selectByVisibleText(Input.securityGroup);
		base.waitForElement(tagsAndFolderPage.getAddTagTable());
		tagsAndFolderPage.getAddTagTable().waitAndClick(5);
		driver.scrollPageToTop();
		base.waitForElement(tagsAndFolderPage.getAllTagRoot());
		tagsAndFolderPage.getAllTagRoot().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getTagActionDropDownArrow());
		tagsAndFolderPage.getTagActionDropDownArrow().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getAddTag());
		tagsAndFolderPage.getAddTag().waitAndClick(10);
		asserts.assertFalse(tagsAndFolderPage.getPropTagFamilyMember().Selected());
		asserts.assertFalse(tagsAndFolderPage.getPropTagExactDuplic().Selected());
		asserts.assertFalse(tagsAndFolderPage.getProbTagEmailThred().Selected());
		asserts.assertFalse(tagsAndFolderPage.getProbTagEmailDupli().Selected());
		asserts.assertAll();

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		base.waitForElement(tagsAndFolderPage.getSecurityGroupTag());
		tagsAndFolderPage.getSecurityGroupTag().selectFromDropdown().selectByVisibleText(Input.securityGroup);
		base.waitForElement(tagsAndFolderPage.getAddTagTable());
		tagsAndFolderPage.getAddTagTable().waitAndClick(5);
		driver.scrollPageToTop();
		base.waitForElement(tagsAndFolderPage.getAllTagRoot());
		tagsAndFolderPage.getAllTagRoot().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getTagActionDropDownArrow());
		tagsAndFolderPage.getTagActionDropDownArrow().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getAddTag());
		tagsAndFolderPage.getAddTag().waitAndClick(10);

		List<String> actPropOrder = new ArrayList<String>();
		List<String> expPropOrder = new ArrayList<String>();
		expPropOrder.add("Family Members");
		expPropOrder.add("Exact Duplicates (Uses MD5Hash)");
		expPropOrder.add("Email Threads");
		expPropOrder.add("Email Duplicates");
		
		List<WebElement> allProbTags = tagsAndFolderPage.getAllProbTags().FindWebElements();
		driver.waitForPageToBeReady();
		
		for (int k = 0; k < allProbTags.size(); k++) {
			actPropOrder.add(allProbTags.get(k).getText());
		}
		base.stepInfo("Actual :" + actPropOrder);
		base.stepInfo("Expected :" + expPropOrder);
		asserts.assertEquals(expPropOrder, actPropOrder);
		asserts.assertAll();

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		base.waitForElement(tagsAndFolderPage.getAddTagTable());
		tagsAndFolderPage.getAddTagTable().waitAndClick(5);
		driver.scrollPageToTop();
		base.waitForElement(tagsAndFolderPage.getAllTagRoot());
		tagsAndFolderPage.getAllTagRoot().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getTagActionDropDownArrow());
		tagsAndFolderPage.getTagActionDropDownArrow().waitAndClick(5);
		tagsAndFolderPage.getAddTag().waitAndClick(10);
		base.waitForElement(tagsAndFolderPage.getTagName());
		tagsAndFolderPage.getTagName().SendKeys(tagname1);
		base.waitForElement(tagsAndFolderPage.getSaveTag());
		tagsAndFolderPage.getSaveTag().waitAndClick(10);

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		base.waitForElement(tagsAndFolderPage.getAddTagTable());
		tagsAndFolderPage.getAddTagTable().waitAndClick(5);
		driver.scrollPageToTop();
		base.waitForElement(tagsAndFolderPage.getAllTagRoot());
		tagsAndFolderPage.getAllTagRoot().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getTagActionDropDownArrow());
		tagsAndFolderPage.getTagActionDropDownArrow().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getAddTag());
		tagsAndFolderPage.getAddTag().waitAndClick(10);
		base.waitForElement(tagsAndFolderPage.getTagName());
		tagsAndFolderPage.getTagName().SendKeys(tagname2);
		base.waitForElement(tagsAndFolderPage.getTagClassification());
		tagsAndFolderPage.getTagClassification().selectFromDropdown().selectByVisibleText(Input.tagNamePrev);
		base.waitForElement(tagsAndFolderPage.getPropTagFamilyMember());
		tagsAndFolderPage.getPropTagFamilyMember().waitAndClick(3);
		base.waitForElement(tagsAndFolderPage.getSaveTag());
		tagsAndFolderPage.getSaveTag().waitAndClick(10);
		base.VerifySuccessMessage("Tag added successfully");

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallTagRoot();
		tagsAndFolderPage.createTagGroup(Input.securityGroup, tagGroup, "False", null);
		base.dragAndDrop(tagsAndFolderPage.getTagNameDataCon(tagname2), tagsAndFolderPage.getTagNameDataCon(tagGroup));

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallTagRoot();
		base.waitForElement(tagsAndFolderPage.getTagNameDataCon(tagname));
		tagsAndFolderPage.getTagNameDataCon(tagname).waitAndClick(10);
		driver.scrollPageToTop();
		base.waitForElement(tagsAndFolderPage.getTagActionDropDownArrow());
		tagsAndFolderPage.getTagActionDropDownArrow().waitAndClick(10);
		base.waitForElement(tagsAndFolderPage.getDeleteTag());
		tagsAndFolderPage.getDeleteTag().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(base.getNOBtn());
		base.getNOBtn().waitAndClick(5);
		tagsAndFolderPage.verifyTagDocCount(tagname, 0);

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		tagsAndFolderPage.verifyNodeNotPresent(tagname, "Tag Not Present", "Tag Present");
		base.passedStep("verified - validation/move/delete a New Tag");
		loginPage.logout();
	}
	

	/**
	 * @author NA Testcase No:RPMXCON-53440
	 * @Description:Verify that if the doc being applied to has no MD5Hash," + "
	 *                     then no Tag propagation should happen
	 **/
	@Test(description = "RPMXCON-53440", enabled = true, groups = { "regression" })
	public void verifyMD5HashNoTagProp() throws Exception {
		tagname = "Tag" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("logged in As : " + Input.rmu1userName);
		base.stepInfo(
				"Verify that if the doc being applied to has no MD5Hash," + " then no Tag propagation should happen");
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		base.waitForElement(tagsAndFolderPage.getAddTagTable());
		tagsAndFolderPage.getAddTagTable().waitAndClick(5);
		driver.scrollPageToTop();
		tagsAndFolderPage.getAllTagRoot().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getTagActionDropDownArrow());
		tagsAndFolderPage.getTagActionDropDownArrow().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getAddTag());
		tagsAndFolderPage.getAddTag().waitAndClick(10);
		base.waitForElement(tagsAndFolderPage.getTagName());
		driver.waitForPageToBeReady();
		tagsAndFolderPage.getTagName().SendKeys(tagname);
		base.waitForElement(tagsAndFolderPage.getTagClassification());
		tagsAndFolderPage.getTagClassification().selectFromDropdown().selectByVisibleText(Input.tagNamePrev);
		base.waitForElement(tagsAndFolderPage.getPropTagExactDuplic());
		tagsAndFolderPage.getPropTagExactDuplic().waitAndClick(3);
		base.waitForElement(tagsAndFolderPage.getSaveTag());
		tagsAndFolderPage.getSaveTag().waitAndClick(10);
		base.VerifySuccessMessage("Tag added successfully");
		base.stepInfo("Tag Created Successfully With Exact Duplicate (MD5Hash)");

		sessionSearch.navigateToSessionSearchPageURL();
		int purehit = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkTagExisting(tagname);

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.verifyTagDocCount(tagname, purehit);
		base.stepInfo(
				"Verified - that if the doc being applied to has no MD5Hash, then no Tag propagation should happen");
		loginPage.logout();

	}

	/**
	 * @author sowndariya Testcase No:RPMXCON-65048
	 * @Description:Verify that error message does not display and application
	 *                     accepts - when new Tag name entered with * ; ‘ / ( ) # &
	 *                     ” from Doc Explorer > bulk Tag
	 **/
	@Test(description = "RPMXCON-65048", enabled = true, groups = { "regression" })
	public void verifyErrorMsgWithSpecialChInTagCreation() throws Exception {
		tagname = "Tag*" + Utility.dynamicNameAppender();
		base.stepInfo("RPMXCON-65048");
		base.stepInfo(
				"Verify that error message does not display and application accepts - when new Tag name entered with * ; ‘ / ( ) # & ” from Doc Explorer > bulk Tag");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		DocExplorerPage docExp = new DocExplorerPage(driver);
		docExp.selectDocumentsAndActionBulkTag(5, tagname);

		tagsAndFolderPage.createNewTagFromActionPopup(tagname, Input.securityGroup);
		driver.waitForPageToBeReady();
		if (tagsAndFolderPage.getErrorMsgPopup().isElementAvailable(5)) {
			base.passedStep(
					"Verify that error message does not display and application accepts - when new Tag name entered with * ; ‘ / ( ) # & ” from Doc Explorer > bulk Tag");
		} else {
			base.failedStep("No error message is found");
		}

		loginPage.logout();
	}

	/**
	 * @author N/A Testcase No:RPMXCON-53441
	 * @Description:Verify that if the doc being applied to has no MD5Hash, then no
	 *                     Folder propagation should happen
	 **/
	@Test(description = "RPMXCON-53441", enabled = true, groups = { "regression" })
	public void verifyMD5HashNoFolderProp() throws Exception {
		String folderName = "Folder" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("logged in As : " + Input.rmu1userName);
		base.stepInfo("Verify that if the doc being applied to has no MD5Hash,"
				+ " then no Folder propagation should happen");

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		base.waitForElement(tagsAndFolderPage.getFoldersTab());
		tagsAndFolderPage.getFoldersTab().waitAndClick(5);
		base.stepInfo("Folder Tab Clicked");
		driver.scrollPageToTop();
		tagsAndFolderPage.getAllFolderRoot().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getFolderActionDropDownArrow());
		tagsAndFolderPage.getFolderActionDropDownArrow().waitAndClick(5);
		base.waitForElement(tagsAndFolderPage.getAddFolder());
		tagsAndFolderPage.getAddFolder().waitAndClick(10);
		base.waitForElement(tagsAndFolderPage.getFolderName());
		tagsAndFolderPage.getFolderName().SendKeys(folderName);
		base.waitForElement(tagsAndFolderPage.getPropFolderExactDuplic());
		tagsAndFolderPage.getPropFolderExactDuplic().waitAndClick(3);
		base.waitForElement(tagsAndFolderPage.getSaveFolder());
		tagsAndFolderPage.getSaveFolder().waitAndClick(10);
		base.VerifySuccessMessage("Folder added successfully");
		base.stepInfo("Folder : " + foldername + " Created Successfully With Exact Duplicate (MD5Hash)");

		sessionSearch.navigateToSessionSearchPageURL();
		int purehit = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkFolderExisting(folderName);

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.verifyFolderDocCount(folderName, purehit);
		base.passedStep("Verified - that if the doc being applied to has no MD5Hash, "
				+ "then no Folder propagation should happen");
		loginPage.logout();
	}
}