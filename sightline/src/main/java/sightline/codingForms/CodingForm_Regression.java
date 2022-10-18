package sightline.codingForms;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CodingForm_Regression {

	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	CodingForm codingForm;
	SavedSearch savedSearch;
	ProjectPage projectPage;
	SecurityGroupsPage securityGroupPage;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	TagsAndFoldersPage tagsAndFoldersPage;
	ReusableDocViewPage reusableDocView;
	MiniDocListPage miniDocList;
	UserManagement userManagementPage;
	CommentsPage commentsPage;
	DocExplorerPage docExplorerPage;

	List<String> cfName = null;
	String codingform = "CFA" + Utility.dynamicNameAppender();
	String cfTwo = "CFB" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that Remove functionality is working correctly and
	 *              properly for Comments in ascending order on coding form screen
	 */
	@Test(description = "RPMXCON-54229", enabled = true, groups = { "regression" })
	public void verifyRemoveFunFromAsc() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54229");
		baseClass.stepInfo("Verify that Remove functionality is working correctly and "
				+ "properly for Comments in ascending order on coding form screen");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		List<String> allComments = new LinkedList<String>();
		// adding comments
		for (int i = 1; i <= 3; i++) {
			this.driver.getWebDriver().get(Input.url + "Comments/CommentsList");
			String comments = commentsPage.addComments("com1" + Utility.dynamicNameAppender());
			allComments.add(comments);
		}
		// Navigating to manage cf
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		for (String comment : allComments) {
			codingForm.addNewCodingFormButton();
			codingForm.getCodingForm_CommentTab().waitAndClick(5);
			codingForm.getCodingForm_Comment(comment).waitAndClick(5);
		}
		// adding comments to coding form structure
		codingForm.addcodingFormAddButton();
		for (String comment : allComments) {
			// verify element disappear
			boolean flagVisible = codingForm.getCf_CommentsDisabled(comment).isElementAvailable(2);
			softAssertion.assertTrue(flagVisible);
			baseClass.stepInfo("Added object inside the coding form structure are disappeared");
		}
		// removing the comments using remove link
		for (int i = 0; i <= 2; i++) {
			// removing from asc
			codingForm.selectRemoveLinkFromCodingForm(i);
		}
		// After removing the comments , values should appear
		for (String comment : allComments) {
			baseClass.waitForElement(codingForm.getCodingForm_Comment(comment));
			boolean flagVisible = codingForm.getCodingForm_Comment(comment).Visible();
			softAssertion.assertTrue(flagVisible);
			baseClass.passedStep("Removed from the coding from structure, value are appeared in left tab");
		}
		// deleting the comments
		for (String comment : allComments) {
			this.driver.getWebDriver().get(Input.url + "Comments/CommentsList");
			commentsPage.DeleteComments(comment);
		}
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : To Verify, as an RM user login, I will be able to manage a
	 *              coding forms
	 */
	@Test(description = "RPMXCON-53974", enabled = true, groups = { "regression" })
	public void verifyRmuUserManageScreen() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53974");
		baseClass.stepInfo("To Verify, as an RM user login, I will be able to manage a coding forms");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);
		String cfName = "cf" + Utility.dynamicNameAppender();
		String comment = "comments" + Utility.dynamicNameAppender();

		// login as
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		// create comments
		this.driver.getWebDriver().get(Input.url + "Comments/CommentsList");
		commentsPage.addComments(comment);
		List<String> manageScreen = new LinkedList<String>();
		// Navigating to manage cf
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		// creating new cf
		codingForm.basedOnCodingFormParameter(cfName, null, comment, null, "comment");
		codingForm.addcodingFormAddButton();
		baseClass.waitForElement(codingForm.getSaveCFBtn());
		codingForm.getSaveCFBtn().waitAndClick(5);
		baseClass.waitForElement(codingForm.getCodingForm_Validation_ButtonYes());
		codingForm.getCodingForm_Validation_ButtonYes().waitAndClick(5);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		baseClass.waitTime(3);
		List<WebElement> cfname = codingForm.getCodingForm_ListName().FindWebElements();
		for (WebElement codingName : cfname) {
			String value = codingName.getText().toString();
			manageScreen.add(value);
		}
		baseClass.stepInfo("checking coding form manage screen");
		System.out.println(manageScreen);
		loginPage.logout();
		// login again to recheck
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// Navigating to manage cf
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		List<String> actual = new LinkedList<String>();
		baseClass.waitTime(3);
		List<WebElement> actualCf = codingForm.getCodingForm_ListName().FindWebElements();
		for (WebElement codingName : actualCf) {
			String value = codingName.getText().toString();
			actual.add(value);
		}
		softAssertion.assertEquals(manageScreen, actual);
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that Remove functionality is working correctly and
	 *              properly for Comments in descending order on coding form screen
	 */
	@Test(description = "RPMXCON-54232", enabled = true, groups = { "regression" })
	public void verifyRemoveFunFromDesc() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54232");
		baseClass.stepInfo("Verify that Remove functionality is working correctly and "
				+ "properly for Comments in descending order on coding form screen");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		List<String> allComments = new LinkedList<String>();
		// adding comments
		for (int i = 1; i <= 3; i++) {
			this.driver.getWebDriver().get(Input.url + "Comments/CommentsList");
			String comments = commentsPage.addComments("com1" + Utility.dynamicNameAppender());
			allComments.add(comments);
		}
		// Navigating to manage cf
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		for (String comment : allComments) {
			codingForm.addNewCodingFormButton();
			codingForm.getCodingForm_CommentTab().waitAndClick(5);
			codingForm.getCodingForm_Comment(comment).waitAndClick(5);
		}
		// adding comments to coding form structure
		codingForm.addcodingFormAddButton();
		for (String comment : allComments) {
			// verify element disappear
			boolean flagVisible = codingForm.getCf_CommentsDisabled(comment).isElementAvailable(2);
			softAssertion.assertTrue(flagVisible);
			baseClass.stepInfo("Added object inside the coding form structure are disappeared");
		}
		// removing the comments using remove link
		for (int i = 2; i >= 0; i--) {
			// removing from desc
			codingForm.selectRemoveLinkFromCodingForm(i);
		}
		// After removing the comments , values should appear
		for (String comment : allComments) {
			baseClass.waitForElement(codingForm.getCodingForm_Comment(comment));
			boolean flagVisible = codingForm.getCf_CommentsDisabled(comment).isElementAvailable(2);
			softAssertion.assertFalse(flagVisible);
			baseClass.passedStep("Removed from the coding from structure, value are appeared in left tab");
		}
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that Remove functionality is working correctly and
	 *              properly for EDITABLE METADATA in ascending order on coding form
	 *              screen
	 */
	@Test(description = "RPMXCON-54230", enabled = true, groups = { "regression" })
	public void verifyRemoveMetaDatAsc() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54230");
		baseClass.stepInfo("Verify that Remove functionality is working correctly and properly "
				+ "for EDITABLE METADATA in ascending order on coding form screen");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
		List<String> metadataAll = new LinkedList<String>();
		for (int i = 1; i <= 3; i++) {
			String projectFieldINT = "DataINT" + Utility.dynamicNameAppender();
			projectPage.addCustomFieldProjectDataType(projectFieldINT, "INT");
			securityGroupPage.addProjectFieldtoSG(projectFieldINT);
			metadataAll.add(projectFieldINT);
			System.out.println(projectFieldINT);
		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		// Navigating to manage cf
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		for (String meta : metadataAll) {
			codingForm.addNewCodingFormButton();
			baseClass.waitForElement(codingForm.getCodingForm_EDITABLE_METADATA_Tab());
			codingForm.getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
			baseClass.waitForElement(codingForm.getCodingForm_MetaData(meta));
			codingForm.getCodingForm_MetaData(meta).waitAndClick(10);
		}
		// adding metadata to coding form structure
		codingForm.addcodingFormAddButton();
		for (String meta : metadataAll) {
			// verify element disappear
			boolean flagVisible = codingForm.getCf_MetaDataDisabled(meta).isElementAvailable(2);
			softAssertion.assertTrue(flagVisible);
			baseClass.stepInfo("Added object inside the coding form structure are disappeared");
		}
		// removing the comments using remove link
		for (int i = 0; i <= 2; i++) {
			// removing from asc
			codingForm.selectRemoveLinkFromCodingForm(i);
			baseClass.stepInfo("Removing metadata from codingform structure in ascending");
		}
		// After removing the comments , values should appear
		for (String meta : metadataAll) {
			boolean flagVisible = codingForm.getCf_MetaDataDisabled(meta).isElementAvailable(2);
			softAssertion.assertFalse(flagVisible);
			baseClass.passedStep("Removed from the coding from structure, value are appeared in left tab");
		}
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that Remove functionality is working correctly and properly 
	 *                for EDITABLE METADATA in descending order on coding form screen
	 */
	@Test(description = "RPMXCON-54233", enabled = true, groups = { "regression" })
	public void verifyRemoveMetaDatDesc() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54233");
		baseClass.stepInfo("Verify that Remove functionality is working correctly and properly for "
				+ "EDITABLE METADATA in descending order on coding form screen");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
		List<String> metadataAll = new LinkedList<String>();
		for (int i = 1; i <= 3; i++) {
			String projectFieldINT = "DataINT" + Utility.dynamicNameAppender();
			projectPage.addCustomFieldProjectDataType(projectFieldINT, "INT");
			securityGroupPage.addProjectFieldtoSG(projectFieldINT);
			metadataAll.add(projectFieldINT);
			System.out.println(projectFieldINT);
		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		// Navigating to manage cf
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		for (String meta : metadataAll) {
			codingForm.addNewCodingFormButton();
			baseClass.waitForElement(codingForm.getCodingForm_EDITABLE_METADATA_Tab());
			codingForm.getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
			baseClass.waitForElement(codingForm.getCodingForm_MetaData(meta));
			codingForm.getCodingForm_MetaData(meta).waitAndClick(10);
		}
		// adding metadata to coding form structure
		codingForm.addcodingFormAddButton();
		for (String meta : metadataAll) {
			// verify element disappear
			boolean flagVisible = codingForm.getCf_MetaDataDisabled(meta).isElementAvailable(2);
			softAssertion.assertTrue(flagVisible);
			baseClass.stepInfo("Added object inside the coding form structure are disappeared");
		}
		// removing the metada using remove link
		for (int i = 2; i >=0; i--) {
			// removing from desc
			codingForm.selectRemoveLinkFromCodingForm(i);
			baseClass.stepInfo("Removing metadata from codingform structure in descending order");
		}
		// After removing the metadata , values should appear
		for (String meta : metadataAll) {
			boolean flagVisible = codingForm.getCf_MetaDataDisabled(meta).isElementAvailable(2);
			softAssertion.assertFalse(flagVisible);
			baseClass.passedStep("Removed from the coding from structure, value are appeared in left tab");
		}
		softAssertion.assertAll();
		loginPage.logout();
	}

	
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that Remove functionality is working 
	 *                correctly and properly for TAGS in descending order on coding form screen
	 */
	@Test(description = "RPMXCON-54231", enabled = true, groups = { "regression" })
	public void verifyRemoveTagsDesc() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54231");
		baseClass.stepInfo("Verify that Remove functionality is working correctly and properly "
				+ "for TAGS in descending order on coding form screen");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		tagsAndFoldersPage=new TagsAndFoldersPage(driver);

		// login as rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		List<String> tagAll = new LinkedList<String>();
		for (int i = 1; i <=3; i++) {
			String tag = "tag" + Utility.dynamicNameAppender();
			tagsAndFoldersPage.CreateTag(tag, Input.codeFormName);
			baseClass.stepInfo("creating a new tag");
			tagAll.add(tag);
		}
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		// Navigating to manage cf
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		for (String tag : tagAll) {
			codingForm.addNewCodingFormButton();
			baseClass.waitForElement(codingForm.getCodingForm_Tag(tag));
			codingForm.getCodingForm_Tag(tag).waitAndClick(10);
		}
		baseClass.stepInfo("Adding tag to coding form structure");
		// adding tag to coding form structure
		codingForm.addcodingFormAddButton();
		for (String tag : tagAll) {
			// verify element disappear
			boolean flagVisible = codingForm.getCf_TagDisabled(tag).isElementAvailable(2);
			softAssertion.assertTrue(flagVisible);
			baseClass.stepInfo("Added object inside the coding form structure are disappeared in left side");
		}
		// removing the tag using remove link
		for (int i = 2; i >=0; i--) {
			// removing from desc
			codingForm.selectRemoveLinkFromCodingForm(i);
			baseClass.stepInfo("Removing tag from codingform structure in descending order");
		}
		// After removing the tag , values should appear
		for (String tag : tagAll) {
			boolean flagVisible = codingForm.getCf_TagDisabled(tag).isElementAvailable(2);
			softAssertion.assertFalse(flagVisible);
			baseClass.passedStep("Removed from the coding form structure, value are appeared in left tab");
		}
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that Remove functionality is working correctly and properly for 
	 *                Comment/TAGS/ EDITABLE METADATA in ascending order on coding form screen
	 */
	@Test(description = "RPMXCON-54234", enabled = true, groups = { "regression" })
	public void verifyRemoveAllTabsInAsc() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54234");
		baseClass.stepInfo("Verify that Remove functionality is working correctly and properly for Comment/TAGS/ "
				+ "EDITABLE METADATA in ascending order on coding form screen");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		tagsAndFoldersPage=new TagsAndFoldersPage(driver);
		String metadata = "DataINT" + Utility.dynamicNameAppender();
		String tag = "tag" + Utility.dynamicNameAppender();

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
		// creating metadata
		List<String> collectionData=new ArrayList<String>();
		projectPage.addCustomFieldProjectDataType(metadata, "INT");
		securityGroupPage.addProjectFieldtoSG(metadata);
		collectionData.add(metadata);
		// logout
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		// creating comments
		this.driver.getWebDriver().get(Input.url + "Comments/CommentsList");
		String comments = commentsPage.addComments("com1" + Utility.dynamicNameAppender());
		collectionData.add(comments);
		// creating tags
		tagsAndFoldersPage.CreateTag(tag, Input.codeFormName);
		collectionData.add(tag);
		// Navigating to manage cf
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addNewCodingFormButton();
		codingForm.basedOnCreatingNewObject(tag, null, null, "tag");
		baseClass.stepInfo("Adding tag into coding form structure");
		codingForm.basedOnCreatingNewObject(null, comments, null, "comment");
		baseClass.stepInfo("Adding comment into coding form structure");
		codingForm.basedOnCreatingNewObject(null, null, metadata, "metadata");
		baseClass.stepInfo("Adding metadata into coding form structure");
		codingForm.addcodingFormAddButton();
		boolean flagMeta = codingForm.getCf_MetaDataDisabled(metadata).isElementAvailable(2);
		softAssertion.assertTrue(flagMeta);
		boolean flagTag = codingForm.getCf_TagDisabled(tag).isElementAvailable(2);
		softAssertion.assertTrue(flagTag);
		boolean flagComment = codingForm.getCf_CommentsDisabled(comments).isElementAvailable(2);
		softAssertion.assertTrue(flagComment);
		baseClass.stepInfo("Added object inside the coding form structure are disappeared");
		// removing the all data using remove link
		for (int i = 0; i <= 2; i++) {
			// removing from asc
			codingForm.selectRemoveLinkFromCodingForm(i);
		}
		baseClass.stepInfo("Removing all data from codingform structure in ascending");
		// After removing all data , values should appear
		boolean flagMetaFalse = codingForm.getCf_MetaDataDisabled(metadata).isElementAvailable(2);
		softAssertion.assertFalse(flagMetaFalse);
		boolean flagTagFalse = codingForm.getCf_TagDisabled(tag).isElementAvailable(2);
		softAssertion.assertFalse(flagTagFalse);
		boolean flagCommentFalse = codingForm.getCf_CommentsDisabled(comments).isElementAvailable(2);
		softAssertion.assertFalse(flagCommentFalse);
		baseClass.passedStep("Removed  from the coding from structure, value are appeared in left tab");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that Remove functionality is working correctly and properly for 
	 *                Comment/TAGS/ EDITABLE METADATA in descending order on coding form screen
	 */
	@Test(description = "RPMXCON-54235", enabled = true, groups = { "regression" })
	public void verifyRemoveAllTabsInDesc() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54235");
		baseClass.stepInfo("Verify that Remove functionality is working correctly and properly for Comment/TAGS/ "
				+ "EDITABLE METADATA in descending order on coding form screen");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		tagsAndFoldersPage=new TagsAndFoldersPage(driver);
		String metadata = "DataINT" + Utility.dynamicNameAppender();
		String tag = "tag" + Utility.dynamicNameAppender();

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
		
		// creating metadata
		List<String> collectionData=new ArrayList<String>();
		projectPage.addCustomFieldProjectDataType(metadata, "INT");
		securityGroupPage.addProjectFieldtoSG(metadata);
		collectionData.add(metadata);
		
		// logout
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		
		// creating comments
		this.driver.getWebDriver().get(Input.url + "Comments/CommentsList");
		String comments = commentsPage.addComments("com1" + Utility.dynamicNameAppender());
		collectionData.add(comments);
		
		// creating tags
		tagsAndFoldersPage.CreateTag(tag, Input.codeFormName);
		collectionData.add(tag);
		
		// Navigating to manage cf
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addNewCodingFormButton();
		codingForm.basedOnCreatingNewObject(tag, null, null, "tag");
		baseClass.stepInfo("Adding tag into coding form structure");
		codingForm.basedOnCreatingNewObject(null, comments, null, "comment");
		baseClass.stepInfo("Adding comment into coding form structure");
		codingForm.basedOnCreatingNewObject(null, null, metadata, "metadata");
		baseClass.stepInfo("Adding metadata into coding form structure");
		codingForm.addcodingFormAddButton();
		boolean flagMeta = codingForm.getCf_MetaDataDisabled(metadata).isElementAvailable(2);
		softAssertion.assertTrue(flagMeta);
		boolean flagTag = codingForm.getCf_TagDisabled(tag).isElementAvailable(2);
		softAssertion.assertTrue(flagTag);
		boolean flagComment = codingForm.getCf_CommentsDisabled(comments).isElementAvailable(2);
		softAssertion.assertTrue(flagComment);
		baseClass.stepInfo("Added object inside the coding form structure are disappeared");
		
		// removing the all data using remove link
		for (int i = 2; i >= 0; i--) {
			// removing from desc
			codingForm.selectRemoveLinkFromCodingForm(i);
		}
		baseClass.stepInfo("Removing all data from codingform structure in descending");
		
		// After removing all data , values should appear
		boolean flagMetaFalse = codingForm.getCf_MetaDataDisabled(metadata).isElementAvailable(2);
		softAssertion.assertFalse(flagMetaFalse);
		boolean flagTagFalse = codingForm.getCf_TagDisabled(tag).isElementAvailable(2);
		softAssertion.assertFalse(flagTagFalse);
		boolean flagCommentFalse = codingForm.getCf_CommentsDisabled(comments).isElementAvailable(2);
		softAssertion.assertFalse(flagCommentFalse);
		baseClass.passedStep("Removed  from the coding from structure, value are appeared in left tab");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar
	 * @Description : To verify as an RMU login, I will be able to search a coding form in manage coding form page by entering coding form name in search box
	 */
	@Test(description = "RPMXCON-54015",enabled = true, groups = { "regression" })
	public void verifySearchMangeCodingFormPage() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54015");
		baseClass.stepInfo("To verify as an RMU login, I will be able to search a coding form in manage coding form page by entering coding form name in search box");
	    
	    codingForm = new CodingForm(driver);
	    softAssertion  = new SoftAssert();
		
		String searchString = "coding";
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		codingForm.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 baseClass.stepInfo("User navigated to Manage coding form page");
		 softAssertion.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
		 softAssertion.assertTrue(driver.getPageSource().contains("New Coding Form"));
		 baseClass.stepInfo("User landed in Coding form page");
		
		//validation
		 codingForm.searchCodingForm(searchString);
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(2);
	    List<String> result = baseClass.getAvailableListofElements(codingForm.getCodingFormTableColumn(1));
	    for(String cfname: result) {
	    	if(!cfname.toLowerCase().contains(searchString))
	    		baseClass.failedStep(searchString+" is not appear in result"+cfname);
	    }
	    baseClass.passedStep("search string apper in result");
	    softAssertion.assertEquals(codingForm.getCodingFormTableHeadColumn(1).GetAttribute("aria-sort"), "ascending");
	    baseClass.passedStep("The moment user will start entering some name on search box, it will sort based on entered string");
	    
	    softAssertion.assertAll();
	    baseClass.passedStep("verified as an RMU login, I will be able to search a coding form in manage coding form page by entering coding form name in search box");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Baskar
	 * @Description : To verify as an RMU login, I will be able to hide a column in manage coding form page
	 */
	@Test(description = "RPMXCON-54016",enabled = true, groups = { "regression" })
	public void verifyRmuAbleToHideColumn() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54016");
		baseClass.stepInfo("To verify as an RMU login, I will be able to hide a column in manage coding form page");
	    
	    codingForm = new CodingForm(driver);
	    softAssertion  = new SoftAssert();
		
		String hideColumn = "Coding Form Name";
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		codingForm.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 baseClass.stepInfo("User navigated to Manage coding form page");
		 softAssertion.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
		 softAssertion.assertTrue(driver.getPageSource().contains("New Coding Form"));
		 baseClass.stepInfo("User landed in Coding form page");
		
		//validation
		 codingForm.hideOrShowColum(hideColumn);
		 softAssertion.assertFalse(baseClass.text(hideColumn).isDisplayed());
	    
		 softAssertion.assertAll();
	    baseClass.passedStep("verified as an RMU login, I will be able to hide a column in manage coding form page");
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
		System.out.println("******TEST CASES FOR CODINGFORM EXECUTED******");
	}

}
