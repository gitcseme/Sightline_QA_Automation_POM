package testScriptsRegressionSprint24;

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
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.DataSets;
import pageFactory.DocListPage;
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

public class TagsAndFolders_Regression24 {

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

	/**
	 * @author NA A Date: 10/14/22 Modified date:N/A Modified by: N/A Description :
	 *         Verify the count is displayed correctly instead of number of
	 *         documents in tag group /folder group. RPMXCON-52934 Sprint 23
	 */
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
		tagsAndFolderPage.getTagNameDataCon(tagGroupName).waitAndClick(5);
		tagsAndFolderPage.CreateTagCC(TagName, Input.securityGroup);
		tagsAndFolderPage.getTagNameDataCon(tagGroupName).waitAndClick(5);
		tagsAndFolderPage.CreateTagCC(TagName2, Input.securityGroup);

		// Tags And Folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.selectallFolderRoot();
		tagsAndFolderPage.createFolderGroup(Input.securityGroup, folderGroupName, "Success", null);
		tagsAndFolderPage.getTagNameDataCon(folderGroupName).waitAndClick(5);
		tagsAndFolderPage.CreateFolderCC(FolderName, Input.securityGroup);
		tagsAndFolderPage.getTagNameDataCon(folderGroupName).waitAndClick(5);
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
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		System.out.println(securityGroup);
		base.stepInfo("created security group 2 : " + securityGroup);

		security.navigateToSecurityGropusPageURL();
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
		tagsAndFolderPage.createNewTagNotSave(tagname,Input.tagNamePrev);
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
