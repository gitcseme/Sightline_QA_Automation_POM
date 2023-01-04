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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TagsAndFolders_Phase2_Regression {

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

	@DataProvider(name = "PAandRMU")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password } };
	}

	@DataProvider(name = "PaAndRmuUser")
	public Object[][] PaAndRmuUser() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "Project Administrator" },
				{ Input.rmu1userName, Input.rmu1password, "Review Manager" } };
		return users;
	}

	/**
	 * @author Vijaya.Rani ModifyDate:09/08/2022 RPMXCON-65047
	 * @throws Exception
	 * @Description Verify that error message does not display and application
	 *              accepts - when new Folder name entered with < > * ; ‘ / ( ) # &
	 *              ” from Doc Explorer > bulk Folder.
	 */
	@Test(description = "RPMXCON-65047", enabled = true, groups = { "regression" })
	public void verifyErrorMsgNotDisplayedFoldernameWithSpicalCharacters() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-65047");
		base.stepInfo(
				"Verify that error message does not display and application accepts - when new Folder name entered with < > * ; ‘ / ( ) # & ” from Doc Explorer > bulk Folder.");
		sessionSearch = new SessionSearch(driver);
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		String foldername = "fol" + Utility.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		base.stepInfo("Navigate to DocExplorer select bulk folder");
		docExplorer.newBulkFolderExisting(foldername);
		base.passedStep(
				"Error message is NOT displayed on click of 'Continue' when folder name entered with characters as-> < > * ; ‘ / ( ) # & ");

		driver.Navigate().refresh();
		base.stepInfo("verify bulk folder name");
		docExplorer.bulkFolderExistingInDocExplorer(foldername);
		base.passedStep("New Folder name is get created  with < > * ; ‘ / ( ) # & ” from Doc Explorer > bulk Folder");

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:09/08/2022 RPMXCON-52475
	 * @throws Exception
	 * @Description To verify RMU user can create, delete, modify, a new tag Group.
	 */
	@Test(description = "RPMXCON-52475", enabled = true, groups = { "regression" })
	public void verifyRMUCreateTagGroupDeleteModify() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-52475");
		base.stepInfo("To verify RMU user can create, delete, modify, a new tag Group.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		String strtag = "Tag";
		String renamedTag = "renamedTag" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		base.stepInfo("Go to Tags and FolderPage Create tag Group");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallTagRoot();
		tagsAndFolderPage.createTagGroup(Input.securityGroup, strtag, "Success", null);
		base.passedStep("New Tag Group is created successfully.");

		base.stepInfo("Go to Tags and FolderPage Create tag and cancel");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallTagRoot();
		tagsAndFolderPage.createTagGroupAndCancel(Input.securityGroup, strtag, null);
		base.passedStep("New tag group is not be saved. Popup is closed.");

		base.stepInfo("Go to Tags and FolderPage Create tag");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallTagRoot();
		tagsAndFolderPage.createDuplicateTagGroup(Input.securityGroup, strtag, "Failure-Error", null);

		base.stepInfo("Edit Tag");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.editTagGroup(Input.securityGroup, strtag, renamedTag, "Success", null);
		base.passedStep("Updated tag group is displayed.");

		base.stepInfo("Remove The Tag Group Click no btn");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.deleteAllTagsGroupsClickNo(renamedTag);
		base.passedStep("Tag Group is not  deleted.");

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.deleteAllTagsGroups(renamedTag, "Success");
		base.passedStep("Tag Group is deleted..");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:09/08/2022 RPMXCON-52486
	 * @throws Exception
	 * @Description To verify that RMU user can create, delete, modify new Folder
	 *              Group.
	 */
	@Test(description = "RPMXCON-52486", enabled = true, groups = { "regression" })
	public void verifyRMUCreateFolderGroupDeleteModify() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-52486");
		base.stepInfo("To verify that RMU user can create, delete, modify new Folder Group.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		String folderGroup = "FolderGroup";
		String renamedFolder = "renamedFolder" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		base.stepInfo("Go to Tags and FolderPage Create folder Group");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallFolderRoot();
		tagsAndFolderPage.createFolderGroup(Input.securityGroup, folderGroup, "Success", null);
		base.passedStep("New Folder Group is created successfully.");

		base.stepInfo("Go to Tags and FolderPage Create folder and cancel");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallFolderRoot();
		tagsAndFolderPage.createFolderGroupAndCancel(Input.securityGroup, folderGroup, null);
		base.passedStep("New folder group is not be saved. Popup is closed.");

		base.stepInfo("Go to Tags and FolderPage Create folder");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallFolderRoot();
		tagsAndFolderPage.createDuplicateFolderGroup(Input.securityGroup, folderGroup, "Failure-Error", null);

		base.stepInfo("Edit folder");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.editFolderGroup(Input.securityGroup, folderGroup, renamedFolder, "Success", null);
		base.passedStep("Updated folder group is displayed.");

		base.stepInfo("Remove The folder Group Click no btn");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.deleteAllFolderGroupClickNo(renamedFolder);
		base.passedStep("folder Group is not  deleted.");

		// Delete folder group
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.deleteAllFolderGroup(renamedFolder, "Success");
		base.passedStep("folder Group is deleted.");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:10/08/2022 RPMXCON-53278
	 * @throws Exception
	 * @Description Verify that RMU should be able to delete the Tag Group from
	 *              application if created by him and associated to only one
	 *              security group.
	 */
	@Test(description = "RPMXCON-53278", enabled = true, groups = { "regression" })
	public void verifyRMUCreateTagGroupAndDelete() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-53278");
		base.stepInfo(
				"Verify that RMU should be able to delete the Tag Group from application if created by him and associated to only one security group.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		String strtag = "Tag" + Utility.dynamicNameAppender();
		;

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		base.stepInfo("Go to Tags and FolderPage Create tag Group");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallTagRoot();
		tagsAndFolderPage.createTagGroup(Input.securityGroup, strtag, "Success", null);
		base.passedStep("Newly created Tag Group is displayed on the Tag tab .");

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.deleteAllTagsGroups(strtag, "Success");
		base.passedStep(
				"Tag Group is deleted from the application Deleted Tag Group is not displayed on Manage Tags tab for RMU.");
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:10/08/2022 RPMXCON-52641
	 * @throws Exception
	 * @Description To verify that if 'Show Docs Counts' is OFF then document counts
	 *              is not displayed on the Tags tab.
	 */
	@Test(description = "RPMXCON-52641", enabled = true, groups = { "regression" })
	public void verifyShowDocCountIsOFFTagDocsCountNotDisplay() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-52641");
		base.stepInfo(
				"To verify that if 'Show Docs Counts' is OFF then document counts is not displayed on the Tags tab.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.showDocsCountisOFFTags();
		base.passedStep("Document counts is not displayed for Tag Group and tags.");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:10/08/2022 RPMXCON-52642
	 * @throws Exception
	 * @Description To verify that if 'Show Docs Counts' is ON then document counts
	 *              is shoul be displayed on the Tag Group and Tag tab.
	 */
	@Test(description = "RPMXCON-52642", enabled = true, groups = { "regression" })
	public void verifyShowDocCountIsONTagDocsCountIsDisplay() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-52642");
		base.stepInfo(
				"To verify that if 'Show Docs Counts' is ON then document counts is shoul be displayed on the Tag Group and Tag tab.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.showDocsCountisONTags();
		base.passedStep("document count is displayed on Tag Group and Tag.");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:11/08/2022 RPMXCON-52643
	 * @throws Exception
	 * @Description To verify that if 'Show Docs Counts' is ON then document counts
	 *              is shoul be displayed on the Folder Group and Folder.
	 */
	@Test(description = "RPMXCON-52643", enabled = true, groups = { "regression" })
	public void verifyShowDocCountIsONFolderDocsCountIsDisplay() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-52643");
		base.stepInfo(
				"To verify that if 'Show Docs Counts' is ON then document counts is shoul be displayed on the Folder Group and Folder.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.showDocsCountisONFolder();
		base.passedStep("document count is displayed on Folder Group and Folder.");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:11/08/2022 RPMXCON-52644
	 * @throws Exception
	 * @Description To verify that if 'Show Docs Counts' is OFF then document counts
	 *              is not displayed on the Folder tab.
	 */
	@Test(description = "RPMXCON-52644", enabled = true, groups = { "regression" })
	public void verifyShowDocCountIsOFFFolderDocsCountNotDisplay() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-52644");
		base.stepInfo(
				"To verify that if 'Show Docs Counts' is OFF then document counts is not displayed on the Folder tab.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.showDocsCountisOFFFolder();
		base.passedStep("Document counts is not displayed for Folder Group and Folder.");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:11/08/2022 RPMXCON-53279
	 * @throws Exception
	 * @Description Verify that RMU should be able to delete the Folder Group from
	 *              application if created by him and associated to only one
	 *              security group.
	 */
	@Test(description = "RPMXCON-53279", enabled = true, groups = { "regression" })
	public void verifyRMUCreateFolderGroupAndDelete() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-53279");
		base.stepInfo(
				"Verify that RMU should be able to delete the Folder Group from application if created by him and associated to only one security group.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		String folderGroup = "FolderGroup" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		base.stepInfo("Go to Tags and FolderPage Create folder Group");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallFolderRoot();
		tagsAndFolderPage.createFolderGroup(Input.securityGroup, folderGroup, "Success", null);
		base.passedStep("Newly created Folder Group is displayed on the Folder tab");

		// Delete folder group
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.deleteAllFolderGroup(folderGroup, "Success");
		base.passedStep(
				"Folder Group is deleted from the application Deleted Folder Group is not displayed on Manage Folder tab for RMU/PAU ");
		loginPage.logout();
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

	/**
	 * @author NA Testcase No:RPMXCON-53437
	 * @Description:Verify contentual help text from Create Folder pop up for the
	 *                     'Propagate Tag To'
	 **/
	@Test(description = "RPMXCON-53437", enabled = true, groups = { "regression" })
	public void verifyingPropagatePopupInTags() throws Exception {
		base = new BaseClass(driver);
		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);

		base.stepInfo("RPMXCON-53437 in Tags And Folders");
		base.stepInfo("Verify contentual help text from Create Folder pop up for the 'Propagate Tag To'");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Navigating to tags and folder page");
		tf.navigateToTagsAndFolderPage();

		base.stepInfo("verifying the propagate popup text in tags");
		tf.verifyingPopupInTags(Input.securityGroup);

		loginPage.logout();
	}

	/**
	 * @author Bruundha Testcase No:RPMXCON-52491
	 * @Description:To verify Tag and Folder group is displayed at project level.
	 **/
	@Test(description = "RPMXCON-52491", enabled = true, groups = { "regression" })
	public void verifyingTagsAndFoldersAllTags() throws Exception {
		base = new BaseClass(driver);
		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);

		String tagGroupName = "Tg" + Utility.dynamicNameAppender();
		String tagGroupName1 = "Tg" + Utility.dynamicNameAppender();
		String tagGroupName2 = "Tg" + Utility.dynamicNameAppender();
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String securityGroup1 = "SG" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXCON-52491 in Tags And Folders");
		base.stepInfo("To verify Tag and Folder group is displayed at project level.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Creating a security group");
		sg.createSecurityGroups(securityGroup);
		System.out.println(securityGroup);

		base.stepInfo("Navigating to tags and folder page");
		tf.navigateToTagsAndFolderPage();

		base.stepInfo("Creating new tag group and verifying all tags group");
		tf.CreatingNewTagGroup(Input.securityGroup, tagGroupName);
		base.CloseSuccessMsgpopup();
		tf.CreatingNewTagGroup(securityGroup, tagGroupName1);
		tf.verifyingAllTagsGroup(tagGroupName1);

		base.stepInfo("Switching to another project and verifying all tags group");
		base.switchProject(Input.projectName);
		driver.waitForPageToBeReady();

		base.stepInfo("Creating a security group");
		sg.createSecurityGroups(securityGroup1);
		System.out.println(securityGroup1);

		tf.navigateToTagsAndFolderPage();
		tf.CreatingNewTagGroup(securityGroup1, tagGroupName2);
		tf.verifyingAllTagsGroup(tagGroupName2);

		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-53448
	 * @Description:Verify that Tag propagation must be based on the ingested
	 *                     MD5Hash field when Tag is saved from doc view coding form
	 **/
	@Test(description = "RPMXCON-53448", enabled = true, groups = { "regression" })
	public void verifyTagProgationInCodingForm() throws Exception {

		foldername = "Folder" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		base.stepInfo(
				"Verify that Tag propagation must be based on the ingested MD5Hash field when Tag is saved from doc view coding form");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in As " + Input.rmu1FullName);
		base.selectproject(Input.additionalDataProject);
		String tag = "Tag" + Utility.dynamicNameAppender();
		String codingForm = "CF" + Utility.dynamicNameAppender();
		String MD5Hashdocument = "H136960011001001.txt";

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.createNewTagNotSave(tag, Input.tagNamePrev);
		base.waitForElement(tagsAndFolderPage.getPropTagExactDuplic());
		tagsAndFolderPage.getPropTagExactDuplic().waitAndClick(10);
		base.waitForElement(tagsAndFolderPage.getSaveTag());
		tagsAndFolderPage.getSaveTag().waitAndClick(10);
		System.out.println(tag);
		base.VerifySuccessMessage("Tag added successfully");
		base.stepInfo("Tag : " + tag + " Created Successfully With Exact Duplicate (MD5Hash)");

		CodingForm form = new CodingForm(driver);
		form.navigateToCodingFormPage();
		form.saveCodingFormWithRedioGroup(codingForm, tag);
		driver.waitForPageToBeReady();
		form.assignCodingFormToSG(codingForm);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch(Input.docFileName, MD5Hashdocument);
		sessionSearch.ViewInDocView();

		DocViewPage docview = new DocViewPage(driver);
		base.waitForElement(docview.getDocView_MiniDocListIds(1));
		docview.getDocView_MiniDocListIds(1).waitAndClick(10);
		docview.saveAndNextNewCodingFormSelectingTags();
		base.passedStep("Document with same MD5Hash as of saved document is tag propagated successfully");

		driver.waitForPageToBeReady();
		form.navigateToCodingFormPage();
		form.assignCodingFormToSG(Input.codingFormName);
		loginPage.logout();
	}

	/**
	 * @author N/A Testcase No:RPMXCON-53445
	 * @Description: Verify that if the doc being applied to are standalone with
	 *               same MD5 hash, then Folder propagation should happen
	 **/

	@Test(description = "RPMXCON-53445", enabled = true, groups = { "regression" })
	public void verifyFolderProginIngMD5Hash() throws Exception {
		foldername = "Folder" + Utility.dynamicNameAppender();
		base.stepInfo("RPMXCON-53445");
		base.stepInfo("To Verify that if the doc being applied to are standalone with same MD5 hash, "
				+ "then Folder propagation should happen");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in As : " + Input.pa1userName);
		base = new BaseClass(driver);
		base.selectproject(Input.additionalDataProject);

		tagsAndFolderPage.createNewFolderNotSave(foldername);
		base.waitForElement(tagsAndFolderPage.getPropFolderExactDuplic());
		tagsAndFolderPage.getPropFolderExactDuplic().waitAndClick(3);
		base.waitForElement(tagsAndFolderPage.getSaveFolder());
		tagsAndFolderPage.getSaveFolder().waitAndClick(10);
		base.VerifySuccessMessage("Folder added successfully");
		base.stepInfo("Folder : " + foldername + " Created Successfully With Exact Duplicate (MD5Hash)");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		dataset.selectDataSetWithName("_MD5Hash");

		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();
		doc.documentSelection(3);
		doc.bulkFolderExisting(foldername);
		base.passedStep("Verify that if the doc being applied to are standalone with same MD5 hash,"
				+ " then Folder propagation should happen");
		loginPage.logout();

	}

	/**
	 * @author N/A Testcase No:RPMXCON-53438
	 * @Description: Verify that Tag propagation must be based on the ingested
	 *               MD5Hash field
	 **/

	@Test(description = "RPMXCON-53438", enabled = true, groups = { "regression" })
	public void verifyTagProginIngMD5HashField() throws Exception {
		tagname = "Tag" + Utility.dynamicNameAppender();
		base.stepInfo("RPMXCON - 53438");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in As : " + Input.pa1userName);
		base.stepInfo("To Verify that Tag propagation must be based on the ingested MD5Hash field");
		base = new BaseClass(driver);
		base.selectproject(Input.additionalDataProject);

		tagsAndFolderPage.createNewTagNotSave(tagname, Input.tagNamePrev);
		base.waitForElement(tagsAndFolderPage.getPropTagExactDuplic());
		tagsAndFolderPage.getPropTagExactDuplic().waitAndClick(3);
		base.waitForElement(tagsAndFolderPage.getSaveTag());
		tagsAndFolderPage.getSaveTag().waitAndClick(10);
		base.VerifySuccessMessage("Tag added successfully");
		base.stepInfo("Tag : " + tagname + " Created Successfully With Exact Duplicate (MD5Hash)");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		dataset.selectDataSetWithName("_MD5Hash");

		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();
		doc.documentSelection(3);
		doc.bulkTagExistingFromDoclist(tagname);
		base.passedStep("Verified -  that Tag propagation must be based on the " + "ingested MD5Hash field");
		loginPage.logout();

	}

	@Test(description = "RPMXCON-52934", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void tagFoderGroupDocToggleAction(String userName, String password, String role) throws Exception {

		String tagGroupName = "aTagGroupR" + Utility.dynamicNameAppender();
		String TagName = "aTagR" + Utility.dynamicNameAppender();
		String TagName2 = "aTagR" + Utility.dynamicNameAppender();

		String folderGroupName = "aFolderGroupR" + Utility.dynamicNameAppender();
		String FolderName = "aFolderR" + Utility.dynamicNameAppender();
		String FolderName2 = "aFolderR" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-52934 - TagsAndFolder Sprint 24");
		base.stepInfo(
				"Verify the count is displayed correctly instead of number of documents in tag group /folder group.");

		loginPage.loginToSightLine(userName, password);
		base.stepInfo("Logged in as : " + userName);

		// Tags And Folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallTagRoot();
		tagsAndFolderPage.createTagGroup(Input.securityGroup, tagGroupName, "Success", null);
		base.waitForElement(tagsAndFolderPage.getTagNameDataCon(tagGroupName));
		tagsAndFolderPage.getTagNameDataCon(tagGroupName).waitAndClick(15);
		tagsAndFolderPage.CreateTagCC(TagName, Input.securityGroup);
		base.waitForElement(tagsAndFolderPage.getTagNameDataCon(tagGroupName));
		tagsAndFolderPage.getTagNameDataCon(tagGroupName).waitAndClick(15);
		tagsAndFolderPage.CreateTagCC(TagName2, Input.securityGroup);

		// Tags And Folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallFolderRoot();
		tagsAndFolderPage.createFolderGroup(Input.securityGroup, folderGroupName, "Success", null);
		driver.waitForPageToBeReady();
		base.waitForElement(tagsAndFolderPage.getTagNameDataCon(folderGroupName));
		tagsAndFolderPage.getTagNameDataCon(folderGroupName).waitAndClick(15);
		tagsAndFolderPage.CreateFolderCC(FolderName, Input.securityGroup);
		base.waitForElement(tagsAndFolderPage.getTagNameDataCon(folderGroupName));
		tagsAndFolderPage.getTagNameDataCon(folderGroupName).waitAndClick(15);
		tagsAndFolderPage.CreateFolderCC(FolderName2, Input.securityGroup);

		// Calculate the unique doc count for the respective searches
		sessionSearch.basicContentSearch(Input.searchString9);
		sessionSearch.addPureHit();
		sessionSearch.getNewSearchButton().waitAndClick(10);
		sessionSearch.multipleBasicContentSearch(Input.searchString2);
		sessionSearch.addPureHit();
		sessionSearch.ViewInDocListWithOutPureHit();
		int aggregateHitCount = saveSearch.docListPageFooterCountCheck();
		base.selectproject();

		// Perform Search and release docs to the tags and folders
		sessionSearch.basicContentSearch(Input.searchString9);
		sessionSearch.bulkTagExisting(TagName);
		sessionSearch.bulkFolderExisting(FolderName);
		base.selectproject();

		// Perform Search and release docs to the tags and folders
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkTagExisting(TagName2);
		sessionSearch.bulkFolderExisting(FolderName2);

		// Navigate to Tags And Folder and verify Tag group Doc count
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.verifyTagDocCount(tagGroupName, aggregateHitCount);

		// Turn off toggle
		tagsAndFolderPage.turnOffToggle(tagsAndFolderPage.getTag_ToggleDocCount());
		base.printResutInReport(
				base.ValidateElement_AbsenceReturn(tagsAndFolderPage.getTagandCount(tagGroupName, aggregateHitCount)),
				"Tag group doc count not displayed after toggle turned off",
				"Tag group doc count still displays after toggle turned off", "Pass");

		// verify Folder group Doc count
		tagsAndFolderPage.verifyFolderDocCount(folderGroupName, aggregateHitCount);

		// Turn off toggle
		tagsAndFolderPage.turnOffToggle(tagsAndFolderPage.getFolder_ToggleDocCount());
		base.printResutInReport(
				base.ValidateElement_AbsenceReturn(
						tagsAndFolderPage.getTagandCount(folderGroupName, aggregateHitCount)),
				"Folder group doc count not displayed after toggle turned off",
				"Folder group doc count still displays after toggle turned off", "Pass");

		loginPage.logout();

	}

	/**
	 * @author NA Testcase No:RPMXCON-53282
	 * @Description:Verify that RMU should be able to delete the Tag from
	 *                     application if created by him and associated to only one
	 *                     security group
	 **/
	@Test(description = "RPMXCON-53282", enabled = true, groups = { "regression" })
	public void verifyTagDeleteInRMU() throws Exception {

		tagname = "Tag" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		base.stepInfo(
				"Verify that RMU should be able to delete the Tag from application if created by him and associated to only one security group");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("logged in As : " + Input.rmu1userName);

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.DeleteTag(tagname, Input.securityGroup);
		base.printResutInReport(base.ValidateElement_AbsenceReturn(tagsAndFolderPage.getTagName(tagname)),
				"Deleted Tag is not displayed", "Deleted Tag is  displayed", "Pass");

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		base.printResutInReport(base.ValidateElement_AbsenceReturn(tagsAndFolderPage.getTagName(tagname)),
				"Deleted Tag is not displayed", "Deleted Tag is  displayed", "Pass");

	}

	/**
	 * @author NA Testcase No:RPMXCON-53283
	 * @Description:Verify that RMU should be able to delete the Folder from
	 *                     application if created by him and associated to only one
	 *                     security group
	 **/
	@Test(description = "RPMXCON-53283", enabled = true, groups = { "regression" })
	public void verifyFolderDeleteInRMU() throws Exception {

		foldername = "Folder" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		base.stepInfo(
				"Verify that RMU should be able to delete the Folder from application if created by him and associated to only one security group");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("logged in As : " + Input.rmu1userName);

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteFolder(foldername, Input.securityGroup);
		base.printResutInReport(base.ValidateElement_AbsenceReturn(tagsAndFolderPage.getFolderName(foldername)),
				"Deleted Folder is not displayed", "Deleted Folder is  displayed", "Pass");

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		base.printResutInReport(base.ValidateElement_AbsenceReturn(tagsAndFolderPage.getFolderName(foldername)),
				"Deleted Folder is not displayed", "Deleted Folder is  displayed", "Pass");

	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-53439
	 * @Description: Verify that Folder propagation must be based on the ingested
	 *               MD5Hash field
	 **/
	@Test(description = "RPMXCON-53439", enabled = true, groups = { "regression" })
	public void verifyFolderPropagation() throws Exception {

		foldername = "Folder" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		base.stepInfo("Verify that Folder propagation must be based on the ingested MD5Hash field");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in As : " + Input.pa1userName);
		base.selectproject(Input.additionalDataProject);

		tagsAndFolderPage.createNewFolderNotSave(foldername);
		base.waitForElement(tagsAndFolderPage.getSaveFolder());
		tagsAndFolderPage.getSaveFolder().waitAndClick(10);
		base.waitForElement(tagsAndFolderPage.getPropFolderExactDuplic());
		tagsAndFolderPage.getPropFolderExactDuplic().waitAndClick(10);
		base.passedStep(
				"created new folder and checked the checkbox  'Propagate Tag To: Exact Duplicates (Use MD5Hash)'");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		dataset.selectDataSetWithName("_MD5Hash");

		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();
		doc.documentSelection(3);
		doc.bulkFolderExisting(foldername);
		base.passedStep("Documents with MD5Hash field is selected and Bulk Folder action performed");

	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-53183
	 * @Description:To verify that 'View in Doc List'and 'View in Doc View' actions
	 *                 should be disabled if Tags are not selected
	 **/
	@Test(description = "RPMXCON-53183", dataProvider = "PAandRMU", enabled = true, groups = { "regression" })
	public void verifyDocListAndDocViewDisabled_Tags(String userName, String passWord) throws Exception {

		foldername = "Folder" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		base.stepInfo(
				"To verify that 'View in Doc List'and 'View in Doc View' actions should be disabled if Tags are not selected");
		loginPage.loginToSightLine(userName, passWord);
		base.stepInfo("Logged in As " + userName);

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

		if (tagsAndFolderPage.getViewInDoclist_Tags().GetAttribute("class").contains("disabled")) {
			base.passedStep("View in Doclist is disabled");
		} else {
			base.failedMessage("View in Doclist is enabled");
		}
		driver.waitForPageToBeReady();

		if (tagsAndFolderPage.getViewInDocView_Tags().GetAttribute("class").contains("disabled")) {
			base.passedStep("View in DocView is disabled");
		} else {
			base.failedMessage("View in DocView is enabled");
		}

	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-53184
	 * @Description:To verify that 'View in Doc List'and 'View in Doc View' actions
	 *                 should be disabled if Folder is not selected
	 **/
	@Test(description = "RPMXCON-53184", dataProvider = "PAandRMU", enabled = true, groups = { "regression" })
	public void verifyDocListAndDocViewDisbled_Folders(String userName, String passWord) throws Exception {

		foldername = "Folder" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		base.stepInfo(
				"To verify that 'View in Doc List'and 'View in Doc View' actions should be disabled if Folder is not selected");
		loginPage.loginToSightLine(userName, passWord);
		base.stepInfo("Logged in As " + userName);

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		base.waitForElement(tagsAndFolderPage.getFoldersTab());
		tagsAndFolderPage.getFoldersTab().waitAndClick(10);
		base.waitForElement(tagsAndFolderPage.getFolderSecutiryGroup());
		tagsAndFolderPage.getFolderSecutiryGroup().selectFromDropdown().selectByVisibleText(Input.securityGroup);
		base.waitForElement(tagsAndFolderPage.getAllFoldersTab());
		tagsAndFolderPage.getAllFoldersTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(tagsAndFolderPage.getFolderActionDropDownArrow());
		tagsAndFolderPage.getFolderActionDropDownArrow().waitAndClick(5);

		if (tagsAndFolderPage.getViewInDoclist_Folders().GetAttribute("class").contains("disabled")) {
			base.passedStep("View in Doclist is disabled");
		} else {
			base.failedMessage("View in Doclist is enabled");
		}

		driver.waitForPageToBeReady();

		if (tagsAndFolderPage.getViewInDocView_Folders().GetAttribute("class").contains("disabled")) {
			base.passedStep("View in DocView is disabled");
		} else {
			base.failedMessage("View in DocView is enabled");
		}

	}

	/**
	 * @author N/A Testcase No:RPMXCON-53194
	 * @Description:verify that if Folder contains Zero document and select action
	 *                     'View in Doc List', " + "message should be displayed
	 *                     'Your query returned no data
	 **/
	@Test(description = "RPMXCON-53194", enabled = true, dataProvider = "PAandRMU", groups = { "regression" })
	public void verifyFoldwithZeroDocinViewinDL(String username, String password) throws Exception {
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();
		String expMSG = "There are NO documents in the tags or folders that you have selected";

		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		base = new BaseClass(driver);

		base.stepInfo("RPMXCON-53194");
		base.stepInfo("To verify that if Folder contains Zero document and select action 'View in Doc List', "
				+ "message should be displayed 'Your query returned no data'");

		loginPage.loginToSightLine(username, password);
		base.stepInfo("Logged in As " + username);
		tags.navigateToTagsAndFolderPage();

		if (username.equals(Input.rmu1userName)) {
			tags.CreateFolderInRMU(folderName);
		} else {
			tags.CreateFolder(foldername, Input.securityGroup);
		}

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		if (username.equals(Input.rmu1userName)) {
			tags.selectFolderViewInDocList(folderName);
		} else {
			tags.selectFolderViewInDocList(foldername);
		}

		base.VerifyWarningMessage(expMSG);
		base.passedStep("verified-  that if Folder contains Zero document and select action 'View in Doc List',"
				+ " message should be displayed 'Your query returned no data'");
		loginPage.logout();
	}

	/**
	 * @author N/A Testcase No:RPMXCON-53436
	 * @Description:Verify contentual help text from Create Tag pop up for the
	 *                     'Propagate Tag To
	 **/
	@Test(description = "RPMXCON-53436", enabled = true, groups = { "regression" })
	public void verifyConceptualHelpText() throws Exception {
		String expPopUpMsg = "Please specify the relevant options for tag/folder propagation. "
				+ "For example, select the 'Family Members' option, if you would like the same tag/folder to be propagated/applied"
				+ " to all the other family members of this document, when a document is being tagged/foldered. "
				+ "Similarly, you can propagate this tag to Exact Duplicates, Email Threads (all other documents with the same ThreadID) "
				+ "and Email Duplicates by selecting the corresponding options. Note that Exact Duplicates propagates tag/folder to all other docs having the same MD5Hash metadata, "
				+ "irrespective of whether these docs are parents, children or standalone docs.";
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		base.stepInfo("RPMXCON-53436");
		base.stepInfo("Verify contentual help text from Create Tag pop up for the 'Propagate Tag To'");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As " + Input.pa1userName);
		tags.navigateToTagsAndFolderPage();
		base.waitForElement(tags.getFoldersTab());
		tags.getFoldersTab().waitAndClick(5);
		base.waitForElement(tags.getAllFolderRoot());
		tags.getAllFolderRoot().waitAndClick(5);
		base.waitForElement(tags.getFolderActionDropDownArrow());
		tags.getFolderActionDropDownArrow().waitAndClick(5);
		base.waitForElement(tags.getAddFolder());
		tags.getAddFolder().waitAndClick(5);
		base.waitForElement(tags.getPropFolderToHelpBtn());
		tags.getPropFolderToHelpBtn().waitAndClick(5);
		base.waitForElement(tags.getPropFolderToHelpTXT());
		String actPopUpMsg = tags.getPropFolderToHelpTXT().getText();
		base.stepInfo("Actual Text In Popup : " + actPopUpMsg);
		if (expPopUpMsg.equals(actPopUpMsg)) {
			base.passedStep("Verified -  contentual help text from Create Tag pop up for the 'Propagate Tag To'");
		} else {
			base.failedStep("Contentual help text from Create Tag pop up for the 'Propagate Tag To Not as Expected");
		}
		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-52492
	 * @Description:To verify Tags and Folders displays at security group level.
	 **/
	@Test(description = "RPMXCON-52492", enabled = true, groups = { "regression" })
	public void verifyTagsDisplay_SecurityGroupLevel() throws Exception {

		tagname = "Tag" + Utility.dynamicNameAppender();
		String securityGroup = "Security" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		base.stepInfo("To verify Tags and Folders displays at security group level.");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in As : " + Input.pa1userName);

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);
		base.stepInfo("created a tag under Default Security Group");

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		security.createSecurityGroups(securityGroup);
		System.out.println(securityGroup);
		base.stepInfo("created a new Security Group");

		UserManagement user = new UserManagement(driver);
		user.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected other security group");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		base.printResutInReport(base.ValidateElement_AbsenceReturn(tagsAndFolderPage.getTagName(tagname)),
				"Tag is not displayed under another security group", "Tag is  displayed under another security group",
				"Pass");

		base.selectproject(Input.additionalDataProject);
		base.stepInfo("Selected other project");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		base.printResutInReport(base.ValidateElement_AbsenceReturn(tagsAndFolderPage.getTagName(tagname)),
				"Tag is not displayed under another security group", "Tag is  displayed under another security group",
				"Pass");

		loginPage.logout();

	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-52684
	 * @Description:Verify Tag/comment/keyword/folder/redaction tag created by one
	 *                     RMU can not deleted by other RMU after release by Project
	 *                     Admin
	 **/
	@Test(description = "RPMXCON-52684", enabled = true, groups = { "regression" })
	public void verifyAccessAfterReleaseForUsers() throws Exception {

		foldername = "Folder" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String redTag = "Redaction" + Utility.dynamicNameAppender();
		String keyword = "K" + Utility.dynamicNameAppender();
		String color = "Blue";
		String securityGroup = "Security" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		base.stepInfo(
				"Verify Tag/comment/keyword/folder/redaction tag created by one RMU can not deleted by other RMU after release by Project Admin");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("logged in As : " + Input.rmu1userName);

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);

		KeywordPage keyWord = new KeywordPage(driver);
		keyWord.navigateToKeywordPage();
		keyWord.addKeyword(keyword, color);
		base.passedStep("keyword created successfully");

		RedactionPage redaction = new RedactionPage(driver);
		redaction.navigateToRedactionsPageURL();
		redaction.createRedactionTagWithExistingNameAndVerifyErrorMessage(redTag);
		base.passedStep("Redaction tag created successfully");
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		security.createSecurityGroups(securityGroup);
		driver.waitForPageToBeReady();
		System.out.println(securityGroup);
		base.stepInfo("created security group 2 : " + securityGroup);

		security.navigateToSecurityGropusPageURL();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		security.addFolderToSecurityGroup(securityGroup, foldername);

		driver.waitForPageToBeReady();
		security.addTagToSecurityGroup(securityGroup, tagname);

		security.addKeywordToSecurityGroup(securityGroup, keyword);
		driver.waitForPageToBeReady();

		security.assignRedactionTagtoSG(redTag);

		base.stepInfo("Released created tag/comment/keyword/redaction tag to security group 2");

		UserManagement user = new UserManagement(driver);
		user.assignAccessToSecurityGroups(securityGroup, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		driver.waitForPageToBeReady();
		base.selectsecuritygroup(securityGroup);

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.DeleteFolder(foldername, securityGroup);
		driver.waitForPageToBeReady();

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.DeleteTag(tagname, securityGroup);
		driver.waitForPageToBeReady();

		keyWord.navigateToKeywordPage();
		keyWord.deleteKeywordByName(keyword);
		driver.waitForPageToBeReady();

		redaction.navigateToRedactionsPageURL();
		redaction.DeleteRedaction(redTag);
		driver.waitForPageToBeReady();

		base.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		security.deleteSecurityGroups(securityGroup);
		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-53444
	 * @Description:Verify that if the doc being applied to are standalone with same
	 *                     MD5hash, then Tag propagation should happen
	 **/
	@Test(description = "RPMXCON-53444", enabled = true, groups = { "regression" })
	public void verifyStandaloneDocsTagPropagation() throws Exception {

		tagname = "Tag" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		base.stepInfo(
				"Verify that if the doc being applied to are standalone with same MD5hash, then Tag propagation should happen");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in As : " + Input.pa1userName);
		base.selectproject(Input.additionalDataProject);

		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.createNewTagNotSave(tagname, Input.tagNamePrev);
		base.waitForElement(tagsAndFolderPage.getPropTagExactDuplic());
		tagsAndFolderPage.getPropTagExactDuplic().waitAndClick(3);
		base.waitForElement(tagsAndFolderPage.getSaveTag());
		tagsAndFolderPage.getSaveTag().waitAndClick(10);
		base.VerifySuccessMessage("Tag added successfully");
		base.stepInfo("Tag : " + tagname + " Created Successfully With Exact Duplicate (MD5Hash)");

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch(Input.metadataIngestion, "MediaID10COMBINEDMD5Hash");
		sessionSearch.ViewInDocList();

		DocListPage docList = new DocListPage(driver);
		docList.documentSelection(2);
		driver.scrollPageToTop();
		docList.bulkTagExistingFromDoclist(tagname);
		if (tagname.equals(docList.getSelectExistingTag(tagname))) {
			base.passedStep(
					"Document are Tagged successfully and standalone documents having same MD5Hash areTag propagated");
		}

	}
}
