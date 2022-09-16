package sightline.tagsAndFolders;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
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

public class TagsAndFolder_Regression {

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
	 * @author Vijaya.Rani ModifyDate:09/08/2022 RPMXCON-65047
	 * @throws Exception
	 * @Description Verify that error message does not display and application
	 *              accepts - when new Folder name entered with < > * ; ‘ / ( ) # &
	 *              ” from Doc Explorer > bulk Folder.
	 */
	@Test(description = "RPMXCON-65047", enabled = true, groups = { "regression" })
	public void verifyErrorMsgNotDisplayedFoldernameWithSpicalCharacters() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-65047");
		baseClass.stepInfo(
				"Verify that error message does not display and application accepts - when new Folder name entered with < > * ; ‘ / ( ) # & ” from Doc Explorer > bulk Folder.");
		sessionSearch = new SessionSearch(driver);
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		String foldername = "fol" + Utility.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("Navigate to DocExplorer select bulk folder");
		docExplorer.newBulkFolderExisting(foldername);
		baseClass.passedStep(
				"Error message is NOT displayed on click of 'Continue' when folder name entered with characters as-> < > * ; ‘ / ( ) # & ");

		driver.Navigate().refresh();
		baseClass.stepInfo("verify bulk folder name");
		docExplorer.bulkFolderExistingInDocExplorer(foldername);
		baseClass.passedStep(
				"New Folder name is get created  with < > * ; ‘ / ( ) # & ” from Doc Explorer > bulk Folder");

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:09/08/2022 RPMXCON-52475
	 * @throws Exception
	 * @Description To verify RMU user can create, delete, modify, a new tag Group.
	 */
	@Test(description = "RPMXCON-52475", enabled = true, groups = { "regression" })
	public void verifyRMUCreateTagGroupDeleteModify() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52475");
		baseClass.stepInfo("To verify RMU user can create, delete, modify, a new tag Group.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		String strtag = "Tag";
		String renamedTag = "renamedTag" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Go to Tags and FolderPage Create tag Group");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallTagRoot();
		tagsAndFolderPage.createTagGroup(Input.securityGroup, strtag, "Success", null);
		baseClass.passedStep("New Tag Group is created successfully.");

		baseClass.stepInfo("Go to Tags and FolderPage Create tag and cancel");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallTagRoot();
		tagsAndFolderPage.createTagGroupAndCancel(Input.securityGroup, strtag, null);
		baseClass.passedStep("New tag group is not be saved. Popup is closed.");

		baseClass.stepInfo("Go to Tags and FolderPage Create tag");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallTagRoot();
		tagsAndFolderPage.createDuplicateTagGroup(Input.securityGroup, strtag, "Failure-Error", null);

		baseClass.stepInfo("Edit Tag");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.editTagGroup(Input.securityGroup, strtag, renamedTag, "Success", null);
		baseClass.passedStep("Updated tag group is displayed.");

		baseClass.stepInfo("Remove The Tag Group Click no btn");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.deleteAllTagsGroupsClickNo(renamedTag);
		baseClass.passedStep("Tag Group is not  deleted.");

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.deleteAllTagsGroups(renamedTag, "Success");
		baseClass.passedStep("Tag Group is deleted..");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:09/08/2022 RPMXCON-52486
	 * @throws Exception
	 * @Description To verify that RMU user can create, delete, modify new Folder Group.
	 */
	@Test(description = "RPMXCON-52486", enabled = true, groups = { "regression" })
	public void verifyRMUCreateFolderGroupDeleteModify() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52486");
		baseClass.stepInfo("To verify that RMU user can create, delete, modify new Folder Group.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		String folderGroup = "FolderGroup";
		String renamedFolder = "renamedFolder" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Go to Tags and FolderPage Create folder Group");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallFolderRoot();
		tagsAndFolderPage.createFolderGroup(Input.securityGroup, folderGroup, "Success", null);
		baseClass.passedStep("New Folder Group is created successfully.");

		baseClass.stepInfo("Go to Tags and FolderPage Create folder and cancel");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallFolderRoot();
		tagsAndFolderPage.createFolderGroupAndCancel(Input.securityGroup, folderGroup, null);
		baseClass.passedStep("New folder group is not be saved. Popup is closed.");

		baseClass.stepInfo("Go to Tags and FolderPage Create folder");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallFolderRoot();
		tagsAndFolderPage.createDuplicateFolderGroup(Input.securityGroup, folderGroup, "Failure-Error", null);

		baseClass.stepInfo("Edit folder");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.editFolderGroup(Input.securityGroup, folderGroup, renamedFolder, "Success", null);
		baseClass.passedStep("Updated folder group is displayed.");

		baseClass.stepInfo("Remove The folder Group Click no btn");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.deleteAllFolderGroupClickNo(renamedFolder);
		baseClass.passedStep("folder Group is not  deleted.");

		// Delete folder group
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.deleteAllFolderGroup(renamedFolder, "Success");
		baseClass.passedStep("folder Group is deleted.");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:10/08/2022 RPMXCON-53278
	 * @throws Exception
	 * @Description Verify that RMU should be able to delete the Tag Group from application if created by him and associated to only one security group.
	 */
	@Test(description = "RPMXCON-53278", enabled = true, groups = { "regression" })
	public void verifyRMUCreateTagGroupAndDelete() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53278");
		baseClass.stepInfo("Verify that RMU should be able to delete the Tag Group from application if created by him and associated to only one security group.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		String strtag = "Tag"+ Utility.dynamicNameAppender();;

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Go to Tags and FolderPage Create tag Group");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallTagRoot();
		tagsAndFolderPage.createTagGroup(Input.securityGroup, strtag, "Success", null);
		baseClass.passedStep("Newly created Tag Group is displayed on the Tag tab .");
		
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.deleteAllTagsGroups(strtag, "Success");
		baseClass.passedStep("Tag Group is deleted from the application Deleted Tag Group is not displayed on Manage Tags tab for RMU.");
		loginPage.logout();
	
	}

	/**
	 * @author Vijaya.Rani ModifyDate:10/08/2022 RPMXCON-52641
	 * @throws Exception
	 * @Description To verify that if 'Show Docs Counts' is OFF then document counts is not displayed on the Tags tab.
	 */
	@Test(description = "RPMXCON-52641", enabled = true, groups = { "regression" })
	public void verifyShowDocCountIsOFFTagDocsCountNotDisplay() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52641");
		baseClass.stepInfo(
				"To verify that if 'Show Docs Counts' is OFF then document counts is not displayed on the Tags tab.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.showDocsCountisOFFTags();
		baseClass.passedStep("Document counts is not displayed for Tag Group and tags.");
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:10/08/2022 RPMXCON-52642
	 * @throws Exception
	 * @Description To verify that if 'Show Docs Counts' is ON then document counts is shoul be displayed on the Tag Group and Tag tab.
	 */
	@Test(description = "RPMXCON-52642", enabled = true, groups = { "regression" })
	public void verifyShowDocCountIsONTagDocsCountIsDisplay() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52642");
		baseClass.stepInfo(
				"To verify that if 'Show Docs Counts' is ON then document counts is shoul be displayed on the Tag Group and Tag tab.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.showDocsCountisONTags();
		baseClass.passedStep("document count is displayed on Tag Group and Tag.");
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:11/08/2022 RPMXCON-52643
	 * @throws Exception
	 * @Description To verify that if 'Show Docs Counts' is ON then document counts is shoul be displayed on the Folder Group and Folder.
	 */
	@Test(description = "RPMXCON-52643", enabled = true, groups = { "regression" })
	public void verifyShowDocCountIsONFolderDocsCountIsDisplay() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52643");
		baseClass.stepInfo(
				"To verify that if 'Show Docs Counts' is ON then document counts is shoul be displayed on the Folder Group and Folder.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.showDocsCountisONFolder();
		baseClass.passedStep("document count is displayed on Folder Group and Folder.");
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:11/08/2022 RPMXCON-52644
	 * @throws Exception
	 * @Description To verify that if 'Show Docs Counts' is OFF then document counts is not displayed on the Folder tab.
	 */
	@Test(description = "RPMXCON-52644", enabled = true, groups = { "regression" })
	public void verifyShowDocCountIsOFFFolderDocsCountNotDisplay() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52644");
		baseClass.stepInfo(
				"To verify that if 'Show Docs Counts' is OFF then document counts is not displayed on the Folder tab.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.showDocsCountisOFFFolder();
		baseClass.passedStep("Document counts is not displayed for Folder Group and Folder.");
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:11/08/2022 RPMXCON-53279
	 * @throws Exception
	 * @Description Verify that RMU should be able to delete the Folder Group from application if created by him and associated to only one security group.
	 */
	@Test(description = "RPMXCON-53279", enabled = true, groups = { "regression" })
	public void verifyRMUCreateFolderGroupAndDelete() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53279");
		baseClass.stepInfo("Verify that RMU should be able to delete the Folder Group from application if created by him and associated to only one security group.");
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		String folderGroup = "FolderGroup"+ Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Go to Tags and FolderPage Create folder Group");
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallFolderRoot();
		tagsAndFolderPage.createFolderGroup(Input.securityGroup, folderGroup, "Success", null);
		baseClass.passedStep("Newly created Folder Group is displayed on the Folder tab");

		// Delete folder group
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.deleteAllFolderGroup(folderGroup, "Success");
		baseClass.passedStep("Folder Group is deleted from the application Deleted Folder Group is not displayed on Manage Folder tab for RMU/PAU ");
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

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}
