package sightline.codingForms;

import pageFactory.DocViewPage;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.model.Author;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
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
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocExplorerPage;

public class CodingForm_Phase2_Regression {
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
	TagsAndFoldersPage  tagsAndFoldersPage;
	ReusableDocViewPage reusableDocView;
	MiniDocListPage miniDocList;
	UserManagement userManagementPage;
	CommentsPage commentsPage;
	
	String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	String codingform = "CFTags"+Utility.dynamicNameAppender();
	String assignment1 = "assignment"+Utility.dynamicNameAppender();
    String assignment2 = "assignment"+Utility.dynamicNameAppender();
    String cfName1 = "cf"+Utility.dynamicNameAppender();
    String cfName2 = "cf"+Utility.dynamicNameAppender();
    List<String> cfName = null;
//	String codingform = "CFA" + Utility.dynamicNameAppender();
	String cfTwo = "CFB" + Utility.dynamicNameAppender();
	List<String> allCf;

	String helpMsg1 = "Is there some reason that review cannot determined?";
	String helpMsg2 = "Does this doc contain some type of issue that prohibits the ability for the record to be reviewed";
	String helpMsg3 = "Does this doc contain some language besides what you can review?";
	String errorMsg = "If the document has technical issue cannot be reviewed,you must select reason why from the list above";
	String navigationConfirmationMsg ="This action will not save your edits, please save your changes before navigating away from Doc View. Do you want to still navigate away without saving your changes ?";

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
		// Login as a RMU
		
//		loginPage = new LoginPage(driver);
//	    assignmentPage = new AssignmentsPage(driver);
//	    sessionSearch = new SessionSearch(driver);
//	    softAssertion = new SoftAssert();
//		codingForm = new CodingForm(driver);
//		reusableDocView = new ReusableDocViewPage(driver);	
//		docViewPage = new DocViewPage(driver);
//		miniDocList = new MiniDocListPage(driver);
//		userManagementPage = new UserManagement(driver);
	}
    
	/**
	 * @throws Exception 
	 * @Author :Indium-Baskar
	 * @Description : Verify that Preview displays correctly and properly when
	 *                Tags and Check group combined along with Check Item on coding form screen
	 */
	@Test(description = "RPMXCON-54059",enabled = true, groups = { "regression" })
	public void verifyCFPreviewUsingCheckItem() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54059");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly "
	    		+ "when Tags and Check group combined along with Check Item on coding form screen");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String tagOne = "TagOne"+Utility.dynamicNameAppender();
	    String tagTwo = "TagTwo"+Utility.dynamicNameAppender();
	    tagsAndFoldersPage=new TagsAndFoldersPage(driver);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
	    
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	   
		//Add tags
		tagsAndFoldersPage.CreateTag(tagOne, Input.securityGroup);
		tagsAndFoldersPage.CreateTag(tagTwo, Input.securityGroup);

		// creating codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		
		//Adding the tags to codingform
	    codingForm.CreateCodingFormWithParameter(codingform,tagOne,null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,tagTwo,null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("Selected  tags are added to coding form");
	    
	    //Add special objects
	    codingForm.specialObjectsBox("check");
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",0,0);
	    codingForm.selectDefaultActions(0, Input.hidden);	
	    codingForm.selectTagTypeByIndex("check item",0,1);
	    codingForm.selectDefaultActions(1, Input.required);	
	    driver.scrollPageToTop();
	    codingForm.saveCodingForm();
	    driver.waitForPageToBeReady();
	    
		//opening same codinform in edit mode
	    driver.Navigate().refresh();
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(40);
	    codingForm.editCodingForm(codingform);
	    driver.waitForPageToBeReady();
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    docViewPage.verifyTagsAreDisabledInPreviewBox(1);
	    baseClass.stepInfo("Checkbox for required tag is disabled");
	    boolean falgTrue= codingForm.getCF_PreviewTagNameHidden(tagTwo).isDisplayed();
	    boolean falgFalse= codingForm.getCF_PreviewTagNameHidden(tagOne).isDisplayed();
	    softAssertion.assertTrue(falgTrue);
	    softAssertion.assertFalse(falgFalse);
	    baseClass.passedStep("codingfrom preview working as per configured");
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	
	/**
	 * @throws Exception 
	 * @Author :Indium-Baskar
	 * @Description : To verify, As an RMU login, When I will edit an existing coding form,
	 *                I can be able to select a new Tag & able to save this change
	 */
	@Test(description = "RPMXCON-54006",enabled = true, groups = { "regression" })
	public void verifyRmuUserCanEditExistingCF() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54006");
	    baseClass.stepInfo("To verify, As an RMU login, When I will edit an existing coding form, "
	    		+ "I can be able to select a new Tag & able to save this change");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String tagOne = "TagOne"+Utility.dynamicNameAppender();
	    String tagTwo = "TagTwo"+Utility.dynamicNameAppender();
	    tagsAndFoldersPage=new TagsAndFoldersPage(driver);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
	    
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	   
		//Add tags
		tagsAndFoldersPage.CreateTag(tagOne, Input.securityGroup);
		tagsAndFoldersPage.CreateTag(tagTwo, Input.securityGroup);

		// creating codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
	    codingForm.CreateCodingFormWithParameter(codingform,tagOne,null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    driver.scrollPageToTop();
	    codingForm.saveCodingForm();
	    driver.waitForPageToBeReady();
	    
		//opening same codinform in edit mode
	    driver.Navigate().refresh();
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(40);
	    codingForm.editCodingForm(codingform);
	    
	    // adding tag to existing codingfrom
	    codingForm.CreateCodingFormWithParameter(codingform,tagTwo,null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    boolean flagTrue=codingForm.getAvailableObjInStructure(tagTwo).isDisplayed();
	    softAssertion.assertTrue(flagTrue);
	    baseClass.stepInfo("Tag added to existing coding from structure");
	    codingForm.codingFormSaveButton();
	    baseClass.VerifySuccessMessage("Coding Form updated successfully");
	    codingForm.deleteCodingForm(codingform, codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	
	
	/**
	 * @throws Exception 
	 * @Author :Indium-Baskar
	 * @Description : Verify that No button functionality is working proper on "Copy" pop up Coding Form screen
	 */
	@Test(description = "RPMXCON-54005",enabled = true, groups = { "regression" })
	public void verifyNoButtonShouldNotCopyCF() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54005");
	    baseClass.stepInfo("Verify that No button functionality is working proper on \"Copy\" pop up Coding Form screen");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String tagOne = "TagOne"+Utility.dynamicNameAppender();
	    String tagTwo = "TagTwo"+Utility.dynamicNameAppender();
	    tagsAndFoldersPage=new TagsAndFoldersPage(driver);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
	    
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	   
		//Add tags
		tagsAndFoldersPage.CreateTag(tagOne, Input.securityGroup);
		tagsAndFoldersPage.CreateTag(tagTwo, Input.securityGroup);

		// creating codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
	    codingForm.CreateCodingFormWithParameter(codingform,tagOne,null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    driver.scrollPageToTop();
	    codingForm.saveCodingForm();
	    driver.waitForPageToBeReady();
	    
		//opening same codingform in copy mode
	    driver.Navigate().refresh();
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(40);
	    baseClass.waitForElement(codingForm.getCodingForm_Search());
	    codingForm.getCodingForm_Search().SendKeys(codingform);
	    codingForm.getCodingForm_CopyButton(codingform).waitAndClick(5);
	    baseClass.waitForElement(codingForm.getDefaultNoBtn());
	    codingForm.getDefaultNoBtn().waitAndClick(5);
	    baseClass.waitForElement(codingForm.getCodingForm_Search());
	    codingForm.getCodingForm_Search().SendKeys(codingform);
	    int headerSize=baseClass.getIndex(codingForm.getCf_HeaderValue(),"Coding Form Name");
	    baseClass.waitTime(5);
	    List<WebElement> rows =codingForm.getCfListSize(headerSize).FindWebElements();
	    int count=rows.size();
	    if (count>=1) {
	    	baseClass.passedStep("No button working properly, coding from not copied");
		}
	    else {
			baseClass.failedStep("Coding from copied");
		}
	    codingForm.deleteCodingForm(codingform, codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	
	/**
	 * @throws Exception 
	 * @Author :Indium-Baskar
	 * @Description : Verify that Nested Remove link works properly inside container on Coding form screen
	 */
	@Test(description = "RPMXCON-53988",enabled = true, groups = { "regression" })
	public void verifyRemoveLinkInCF() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-53988");
	    baseClass.stepInfo("Verify that Nested Remove link works properly inside container on Coding form screen");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String tagOne = "TagOne"+Utility.dynamicNameAppender();
	    String tagTwo = "TagTwo"+Utility.dynamicNameAppender();
	    String tagThree = "TagThree"+Utility.dynamicNameAppender();
	    tagsAndFoldersPage=new TagsAndFoldersPage(driver);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
	    
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	   
		//Add tags
		driver.waitForPageToBeReady();
		tagsAndFoldersPage.CreateTag(tagOne, Input.securityGroup);
		tagsAndFoldersPage.CreateTag(tagTwo, Input.securityGroup);
		tagsAndFoldersPage.CreateTag(tagThree, Input.securityGroup);

		// creating codingform(as per pre-requisties)
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.firstCheckBox("tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.firstCheckBox("comment");
	    codingForm.addcodingFormAddButton();
	    codingForm.firstCheckBox("metadata");
	    codingForm.addcodingFormAddButton();
	    codingForm.saveCodingForm();
	    
	    // selecting multiple from tag tab
	    codingForm.getCf_TAGTAB().waitAndClick(5);
	    codingForm.CreateCodingFormWithParameter(codingform,tagOne,null,null,"tag");
	    codingForm.CreateCodingFormWithParameter(codingform,tagTwo,null,null,"tag");
	    codingForm.CreateCodingFormWithParameter(codingform,tagThree,null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    
	    // validating from remove link
	    codingForm.getCf_RemoveLink(5).waitAndClick(5);
	    baseClass.waitForElement(codingForm.getCodingForm_SGValidation_ButtonYes());
	    codingForm.getCodingForm_SGValidation_ButtonYes().waitAndClick(10);
	    codingForm.getCf_RemoveLink(4).waitAndClick(4);
	    baseClass.waitForElement(codingForm.getCodingForm_SGValidation_ButtonYes());
	    codingForm.getCodingForm_SGValidation_ButtonYes().waitAndClick(10);
	    codingForm.getCf_RemoveLink(3).waitAndClick(3);
	    baseClass.waitForElement(codingForm.getCodingForm_SGValidation_ButtonYes());
	    codingForm.getCodingForm_SGValidation_ButtonYes().waitAndClick(10);
	    baseClass.stepInfo("Removing tag by remove link");
	    
	    boolean flagFalseOne=codingForm.getAvailableObjInStructure(tagOne).isElementAvailable(3);
	    softAssertion.assertFalse(flagFalseOne);
	    boolean flagFalseTwo=codingForm.getAvailableObjInStructure(tagTwo).isElementAvailable(3);
	    softAssertion.assertFalse(flagFalseTwo);
	    boolean flagFalseThree=codingForm.getAvailableObjInStructure(tagThree).isElementAvailable(3);
	    softAssertion.assertFalse(flagFalseThree);
	    baseClass.passedStep("Removed tag are not displaying in coding form structure");
	    codingForm.deleteCodingForm(codingform, codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	
	/**
	 * @throws Exception 
	 * @Author :Indium-Baskar
	 * @Description : Verify that user adds tag/ comment / editable metadata field only once to the coding form
	 */
	@Test(description = "RPMXCON-53982",enabled = true, groups = { "regression" })
	public void verifyUserCannnotAddSameNameAgainInCf() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-53982");
	    baseClass.stepInfo("Verify that user adds tag/ comment / editable metadata field only once to the coding form");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String tagOne = "TagOne"+Utility.dynamicNameAppender();
	    String projectFieldINT = "Meta"+Utility.dynamicNameAppender();
	    String addComment = "comment"+Utility.dynamicNameAppender();
	    tagsAndFoldersPage=new TagsAndFoldersPage(driver);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		commentsPage = new CommentsPage(driver);
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(projectFieldINT, "NVARCHAR");
		baseClass.stepInfo("Custom meta data field created with INT datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(projectFieldINT);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		commentsPage.AddComments(addComment);
	    
		//Add tags
		tagsAndFoldersPage.CreateTag(tagOne, Input.securityGroup);

		// creating codingform(as per pre-requisties)
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.firstCheckBox("tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.firstCheckBox("comment");
	    codingForm.addcodingFormAddButton();
	    codingForm.firstCheckBox("metadata");
	    codingForm.addcodingFormAddButton();
	    codingForm.saveCodingForm();
	    baseClass.stepInfo("Codingform as per per-requistes");
	    
	    // selecting multiple tab
	    codingForm.getCf_TAGTAB().waitAndClick(5);
	    codingForm.CreateCodingFormWithParameter(codingform,tagOne,null,null,"tag");
	    codingForm.CreateCodingFormWithParameter(codingform,null,addComment,null,"comment");
	    codingForm.CreateCodingFormWithParameter(codingform,null,null,projectFieldINT,"metadata");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("Added multiple tab in coding form structure");
	    
	    // selecting same field which added previously
	    codingForm.getCf_TAGTAB().waitAndClick(5);
	    boolean tagDisabled=codingForm.getCf_TagDisabled(tagOne).GetAttribute("disabled").contains("");
	    softAssertion.assertTrue(tagDisabled);
	    codingForm.getCodingForm_CommentTab().waitAndClick(10);
	    boolean commentDisabled=codingForm.getCf_CommentsDisabled(addComment).GetAttribute("disabled").contains("");
	    softAssertion.assertTrue(commentDisabled);
	    codingForm.getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
	    boolean metadataDisabled=codingForm.getCf_MetaDataDisabled(projectFieldINT).GetAttribute("disabled").contains("");
	    softAssertion.assertTrue(metadataDisabled);
	    baseClass.passedStep("Added available fields, cannot able to add again in coding form structure");
	    codingForm.deleteCodingForm(codingform, codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	
	/**
	 * @throws Exception 
	 * @Author :Indium-Baskar
	 * @Description : Shared Steps: Creating a New Coding form
	 */
	@Test(description = "RPMXCON-47210",enabled = true, groups = { "regression" })
	public void validateCfTextBox() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-47210");
	    baseClass.stepInfo("Shared Steps: Creating a New Coding form");
	    String codingform = "1LR Coding Form" + Utility.dynamicNameAppender();
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// validating the codingform textbox
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		baseClass.waitForElement(codingForm.getSaveCFBtn());
		codingForm.getSaveCFBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Coding Form Saved successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.passedStep("Codingform get saved with the valid name");
	    codingForm.deleteCodingForm(codingform, codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}

	
	/**
	 * @throws Exception 
	 * @Author :Indium-Baskar
	 * @Description : Shared Steps: Verify that Coding form screen opens properly inside Application
	 */
	@Test(description = "RPMXCON-47209",enabled = true, groups = { "regression" })
	public void validateCfScreenWorksProperly() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-47209");
	    baseClass.stepInfo("Shared Steps: Verify that Coding form screen opens properly inside Application");
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// validating the codingform textbox
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.stepInfo("User navigated to Manage coding form page");
	    boolean flagManage=driver.getPageSource().contains("Manage Coding Forms");
	    boolean flagNew=driver.getPageSource().contains("New Coding Form");
	    softAssertion.assertTrue(flagManage);
	    softAssertion.assertTrue(flagNew);
	    softAssertion.assertAll();
	    baseClass.passedStep("Manage coding form screen working properly as expected");
	    loginPage.logout();
	}
	
	
	/**
	 * @throws Exception 
	 * @Author :Indium-Baskar
	 * @Description : Verify that Preview displays correctly and properly when Tags 
	 *                and Radio Group combined along with Radio Item on coding form screen
	 */
	@Test(description = "RPMXCON-54058",enabled = true, groups = { "regression" })
	public void verifyCFPreviewUsingRadioItem() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54058");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly when Tags "
	    		+ "and Radio Group combined along with Radio Item on coding form screen");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String tagOne = "TagOne"+Utility.dynamicNameAppender();
	    String tagTwo = "TagTwo"+Utility.dynamicNameAppender();
	    tagsAndFoldersPage=new TagsAndFoldersPage(driver);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
	    
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	   
		//Add tags
		tagsAndFoldersPage.CreateTag(tagOne, Input.securityGroup);
		tagsAndFoldersPage.CreateTag(tagTwo, Input.securityGroup);

		// creating codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		
		//Adding the tags to codingform
	    codingForm.CreateCodingFormWithParameter(codingform,tagOne,null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,tagTwo,null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("Selected  tags are added to coding form");
	    
	    //Add special objects
	    codingForm.specialObjectsBox("radio");
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("radio item",1,0);
	    codingForm.selectTagTypeByIndex("radio item",1,1);
	    codingForm.selectDefaultActions(2, Input.optional);	
	    driver.scrollPageToTop();
	    codingForm.saveCodingForm();
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(20);
	    
		//opening same codingform in edit mode
	    driver.Navigate().refresh();
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(40);
	    codingForm.editCodingForm(codingform);
	    driver.waitForPageToBeReady();
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    boolean tagOneTrue=codingForm.selectTagInPreviewBox(2).Enabled();
	    softAssertion.assertTrue(tagOneTrue);
	    baseClass.passedStep("For optional action radio box is enabled");
	    codingForm.getCF_Preview_Ok().waitAndClick(10);
	    
	    // validating for not selectable
	    codingForm.getCf_DownPull("Radio Group").waitAndClick(10);
	    codingForm.selectDefaultActions(2, Input.notSelectable);	
	    driver.scrollPageToTop();
	    baseClass.waitForElement(codingForm.getSaveCFBtn());
	    codingForm.getSaveCFBtn().waitAndClick(5);
		baseClass.waitForElement(codingForm.getCf_ValidationYesUsingPosition(2));
		codingForm.getCf_ValidationYesUsingPosition(2).waitAndClick(5);
	    driver.Navigate().refresh();
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(50);
	    codingForm.editCodingForm(codingform);
	    baseClass.waitTime(50);
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.waitForElement(codingForm.getCf_PreviewTagDisabled(tagOne));
	    boolean tagDisFalse=codingForm.getCf_PreviewTagDisabled(tagOne).Enabled();
	    softAssertion.assertFalse(tagDisFalse);
	    System.out.println(tagDisFalse);
	    baseClass.stepInfo("Radiobox for notsectable tag is disabled");
	    baseClass.passedStep("codingfrom preview working as per configured");
	    codingForm.deleteCodingForm(codingform, codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that we have instructional text present in "Sort Coding
	 *              Form Order" pop-up page (UI).
	 */
	@Test(description = "RPMXCON-65465", enabled = true, groups = { "regression" })
	public void verifyHamBurgerIcon() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-65465");
		baseClass.stepInfo(
				"Verify that we have instructional text present in \"Sort Coding Form Order\" pop-up page (UI).");
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		DocExplorerPage docExplorerPage = new DocExplorerPage(driver);
		savedSearch = new SavedSearch(driver);
        AssignmentsPage assgnpage = new AssignmentsPage(driver);
        
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Navigate to manage coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		boolean setCF = codingForm.getSetCodingFormToSG().Enabled();
		softAssertion.assertTrue(setCF);
		boolean showHide = codingForm.getShowHide().Enabled();
		softAssertion.assertTrue(showHide);
		baseClass.stepInfo("Set security group coding form and show/hide are present and ist in enable state");

		// click on set security group
		List<String>allCfName=new LinkedList<String>();
		// Navigate to manage coding form
		for (int i = 0; i <15; i++) {
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			codingForm.	addNewCodingFormButton();
			String name=codingForm.passingCodingFormName("ZCF" + Utility.dynamicNameAppender());
			allCfName.add(name);
			baseClass.waitForElement(codingForm.getSaveCFBtn());
			codingForm.getSaveCFBtn().waitAndClick(5);
		}
		codingForm.checkingBelow15CFCheckboxForSG();
		baseClass.waitTime(1);
		codingForm.getSelectCodeFormRadioBtn(Input.codeFormName).waitAndClick(5);
		baseClass.waitTime(1);
		if(!assgnpage.sortOrderNxtBtn().Enabled()) {
			assgnpage.getSelectCF_CheckBox(Input.codeFormName).waitAndClick(5);
		}
		codingForm.sortOrderNxtBtn().ScrollTo();
		codingForm.sortOrderNxtBtn().Click();
//		baseClass.waitForElement(codingForm.getStep2CfPopUp());
//		boolean flagPopup2 = codingForm.getStep2CfPopUp().isElementAvailable(2);
//		softAssertion.assertTrue(flagPopup2);
		baseClass.stepInfo("Step 02: Sort Coding Form Order");
		List<String> actual = new ArrayList<String>();
		List<WebElement> beforeDrag = codingForm.sortOrderHamBurger().FindWebElements();
		for (WebElement webElement : beforeDrag) {
			actual.add(webElement.getText().toString());
		}
		String frst = beforeDrag.get(2).getText();
		// interchanging the cf using hamburger icon
		Actions actions = new Actions(driver.getWebDriver());
		// Drag and Drop to x,y
		actions.clickAndHold(codingForm.getHamBurgerDrag(frst).getWebElement());
		actions.moveToElement(codingForm.getHamBurgerDrag(frst).getWebElement(), -10, 10);
		actions.moveToElement(codingForm.getHamBurgerDrag(frst).getWebElement(), -10, 30);
		actions.moveToElement(codingForm.getHamBurgerDrag(frst).getWebElement(), 5, 27).build().perform();
		actions.release();
		actions.build().perform();

		baseClass.waitTime(2);
		List<String> expected = new ArrayList<String>();
		List<WebElement> afterDrag = codingForm.sortOrderHamBurger().FindWebElements();
		for (WebElement webElement : afterDrag) {
			expected.add(webElement.getText().toString());
		}
		softAssertion.assertNotEquals(actual, expected);
		softAssertion.assertAll();
		// deleting the cf
		for (String cfDelete : allCfName) {
			codingForm.deleteCodingForm(cfDelete, cfDelete);
		}
		baseClass.passedStep("Using hamburger icon we can able to interchange the cf as per needed");
		loginPage.logout();
	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that we have instructional text present in "Add/remove
	 *              coding form in this security group" pop-up page (UI).
	 */
	@Test(description = "RPMXCON-65464", enabled = true, groups = { "regression" })
	public void verifyConfigureUpto15Cf() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-65464");
		baseClass.stepInfo(
				"Verify that we have instructional text present in \"Add/remove coding form in this security group\" pop-up page (UI).");
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		DocExplorerPage docExplorerPage = new DocExplorerPage(driver);
		savedSearch = new SavedSearch(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		List<String>allCfName=new LinkedList<String>();
		// Navigate to manage coding form
		for (int i = 0; i <15; i++) {
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			codingForm.	addNewCodingFormButton();
			String name=codingForm.passingCodingFormName("ZCF" + Utility.dynamicNameAppender());
			allCfName.add(name);
			baseClass.waitForElement(codingForm.getSaveCFBtn());
			codingForm.getSaveCFBtn().waitAndClick(5);
		}
		// Navigate to manage coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		boolean setCF = codingForm.getSetCodingFormToSG().Enabled();
		softAssertion.assertTrue(setCF);
		boolean showHide = codingForm.getShowHide().Enabled();
		softAssertion.assertTrue(showHide);
		baseClass.stepInfo("Set security group coding form and show/hide are present and ist in enable state");

		// click on set security group
		codingForm.configureBelow15Cf(Input.codingFormName);
		codingForm.makingDefaultCfToSg(Input.codingFormName);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.validatingDefaultSgFromManageScreen(Input.codingFormName);
		softAssertion.assertAll();
		// deleting the cf
		for (String cfDelete : allCfName) {
			codingForm.deleteCodingForm(cfDelete, cfDelete);
		}
		baseClass.passedStep("User can able to configure only 15 cf in set security group coding form");
		baseClass.passedStep("Manage coding form screen default cf displayed");
		loginPage.logout();
	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that when user clicks on "Next Stage Sort Order" button
	 *              from the Add/remove coding form pop-up after selecting 15 or
	 *              less than 15 coding form and keeping any one as default user
	 *              should go to Next page without any error.
	 */
	@Test(description = "RPMXCON-64892", enabled = true, groups = { "regression" })
	public void verifyAfterConfigureWithoutError() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-64892");
		baseClass.stepInfo("Verify that when user clicks on \"Next Stage Sort Order\" button "
				+ "from the Add/remove coding form pop-up after selecting 15 or less "
				+ "than 15 coding form and keeping any one as default user should go "
				+ "to Next page without any error.");
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		DocExplorerPage docExplorerPage = new DocExplorerPage(driver);
		savedSearch = new SavedSearch(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		List<String>allCfName=new LinkedList<String>();
		// Navigate to manage coding form
		for (int i = 0; i <15; i++) {
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			codingForm.	addNewCodingFormButton();
			String name=codingForm.passingCodingFormName("ZCF" + Utility.dynamicNameAppender());
			allCfName.add(name);
			baseClass.waitForElement(codingForm.getSaveCFBtn());
			codingForm.getSaveCFBtn().waitAndClick(5);
		}
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		boolean setCF = codingForm.getSetCodingFormToSG().Enabled();
		softAssertion.assertTrue(setCF);
		boolean showHide = codingForm.getShowHide().Enabled();
		softAssertion.assertTrue(showHide);
		baseClass.stepInfo("Set security group coding form and show/hide are present and ist in enable state");

		// click on set security group
		codingForm.configureBelow15Cf(Input.codingFormName);
		codingForm.makingDefaultCfToSg(Input.codingFormName);
		baseClass.stepInfo("user navigated without any error when configured with less that 15 cf");
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.validatingDefaultSgFromManageScreen(Input.codingFormName);
		baseClass.passedStep("user navigated without any error when configured with less that 15 cf");
		softAssertion.assertAll();
		// deleting the cf
		for (String cfDelete : allCfName) {
			codingForm.deleteCodingForm(cfDelete, cfDelete);	
		}
		
		
		loginPage.logout();
	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that the check box and radio button present under
	 *              "CODING FORM NAME" and "SET AS DEFAULT (REQUIRED)" column in
	 *              "Add/remove coding form in this security group" coding form
	 *              pop-up can be checked and Uncheck.
	 */
	@Test(description = "RPMXCON-64673", enabled = true, groups = { "regression" })
	public void verifyCheckBoxAndRadioOption() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-64673");
		baseClass.stepInfo("Verify that the check box and radio button present under \"CODING FORM NAME\""
				+ " and \"SET AS DEFAULT (REQUIRED)\" column in \"Add/remove coding form in"
				+ " this security group\" coding form pop-up can be checked and Uncheck.");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		DocExplorerPage docExplorerPage = new DocExplorerPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Navigate to manage coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		boolean setCF = codingForm.getSetCodingFormToSG().Enabled();
		softAssertion.assertTrue(setCF);
		boolean showHide = codingForm.getShowHide().Enabled();
		softAssertion.assertTrue(showHide);
		baseClass.stepInfo("Set security group coding form and show/hide are present and ist in enable state");

		// click on set security group
		baseClass.waitForElement(codingForm.getSetCFButton());
		codingForm.getSetCFButton().ScrollTo();
		codingForm.getSetCFButton().waitAndClick(10);
		baseClass.waitForElement(codingForm.getStep1CfPopUp());
		boolean flagPopup1 = codingForm.getStep1CfPopUp().isElementAvailable(2);
		baseClass.stepInfo("Step 01 : Add / Remove Coding Forms in this Security Group");
		softAssertion.assertTrue(flagPopup1);
		codingForm.getPopUpCheckBox().waitAndClick(5);
		baseClass.waitForElement(codingForm.getPopUpCheckBox());
		boolean checkbox=codingForm.getPopUpCheckBox().GetAttribute("class").contains("checkbox");
		softAssertion.assertTrue(checkbox);
		baseClass.stepInfo("Coding Form name diaplayed at top checkbox in popup");
		int unCheck=codingForm.getCfUnChecBoxUsingSize().size();
		System.out.println(unCheck);
		baseClass.passedStep("user navigated without any error when configured with less that 15 cf");
		System.out.println(codingForm.getSetDefaultSG().getText().trim());
		boolean sg=codingForm.getSetDefaultSG().getText().trim().contains("SET AS DEFAULT (REQUIRED)");
		softAssertion.assertTrue(sg);
		baseClass.stepInfo("Set as default(Required) in popup window");
		baseClass.waitForElement(codingForm.getVerifyRadioBtn(Input.codeFormName));
		boolean radio=codingForm.getVerifyRadioBtn(Input.codeFormName).GetAttribute("class").contains("radio");
		softAssertion.assertTrue(radio);
		baseClass.passedStep("Radio btn option available in below set as default");
		softAssertion.assertAll();
		loginPage.logout();
	}
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that Unique validation message appears 
	 *                 when user configured more than 1 Logic Rules
	 */
	@Test(description = "RPMXCON-53990",enabled = true, groups = { "regression" })
	public void validationMsgForLogicRole() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-53990");
	    baseClass.stepInfo("Verify that Unique validation message appears "
	    		+ "when user configured more than 1 Logic Rules");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String tagOne = "TagOne"+Utility.dynamicNameAppender();
	    tagsAndFoldersPage=new TagsAndFoldersPage(driver);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
	    String expectedButton="YesNoCancel";
	    
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	   
		//Add tags
		tagsAndFoldersPage.CreateTag(tagOne, Input.securityGroup);

		// creating codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		
		//Adding the tags to codingform structure
	    codingForm.CreateCodingFormWithParameter(codingform,tagOne,null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("Selected  tags are added to coding form structure");
	    
	    // tag text for validation
	    baseClass.waitForElement(codingForm.getFullTagText());
	    String tagName=codingForm.getFullTagText().getText();
	    String spaceTag = tagName.replaceAll("\\s+", "");
		String expected="Validation Status: Please select Field Logic Action for "+ spaceTag +"  Do you want to continue?";
		
		// clicking logic field twice
	    baseClass.waitForElement(codingForm.getCF_AddLogicButton());
	    codingForm.getCF_AddLogicButton().waitAndClick(5);
	    codingForm.getCF_AddLogicButton().waitAndClick(5);
	    
	    // coding form save
	    baseClass.waitForElement(codingForm.getSaveCFBtn());
	    codingForm.getSaveCFBtn().waitAndClick(5);
	    
	    // validation for field logic selection
	    baseClass.waitForElement(codingForm.getValidationText());
	    String validationText=codingForm.getValidationText().getText();
	    String actualResult = validationText.replaceAll("\\n", " ");
	    softAssertion.assertEquals(expected, actualResult);
	    
	    // validation for button
	    baseClass.waitForElement(codingForm.getValidationButtonText());
	    String actualButton=codingForm.getValidationButtonText().getText();
	    softAssertion.assertEquals(expectedButton, actualButton);
	    
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that Proper validation message appears when 
	 *                user not configured friendly field name on coding form
	 */
	@Test(description = "RPMXCON-53981",enabled = true, groups = { "regression" })
	public void validateFriendlyFieldLabel() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-53981");
	    baseClass.stepInfo("Verify that Proper validation message appears when user not "
	    		+ "configured friendly field name on coding form");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String tagOne = "TagOne"+Utility.dynamicNameAppender();
	    String projectFieldINT = "Meta"+Utility.dynamicNameAppender();
	    String addComment = "comment"+Utility.dynamicNameAppender();
	    tagsAndFoldersPage=new TagsAndFoldersPage(driver);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		commentsPage = new CommentsPage(driver);
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
	    String expectedButton="YesNoCancel";
	    String expected = null;
	    
	    // Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(projectFieldINT, "NVARCHAR");
		baseClass.stepInfo("Custom meta data field created with INT datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(projectFieldINT);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		commentsPage.AddComments(addComment);
	   
		//Add tags
		tagsAndFoldersPage.CreateTag(tagOne, Input.securityGroup);

		// creating codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		
		String[] tabName= {"tag","comment","metadata"};
		String[] availableListName= {tagOne,addComment,projectFieldINT};
		
		//Adding the tags to codingform structure
		for (int i = 0; i < tabName.length; i++) {
			for (int j = 0; j < availableListName.length; j++) {
	    codingForm.CreateCodingFormWithParameter(codingform,availableListName[j],availableListName[j],availableListName[j],tabName[i]);
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("Selected  tags are added to coding form structure");
	    
	    // tag text for validation
	    baseClass.waitForElement(codingForm.getCF_objectName(0));
	    codingForm.getCF_objectName(0).Clear();
	    baseClass.waitForElement(codingForm.getFullTagText());
	    String tagName=codingForm.getFullTagText().getText();
	    String spaceTag = tagName.replaceAll("\\s+", "").replaceAll("[(){}]","");
		expected="Validation Status: Please Enter Friendly label for "+ spaceTag +"  Do you want to continue?";
		
	    // coding form save
	    baseClass.waitForElement(codingForm.getSaveCFBtn());
	    codingForm.getSaveCFBtn().waitAndClick(5);
	    
	    // validation for friendly field label
		baseClass.waitForElement(codingForm.getValidationText());
		String validationText = codingForm.getValidationText().getText();
		String actualResult = validationText.replaceAll("\\n", " ");
		softAssertion.assertEquals(expected, actualResult);
		baseClass.passedStep("validation message diaplyed as "+expected+" ");
		
	    // validation for button
	    baseClass.waitForElement(codingForm.getValidationButtonText());
	    String actualButton=codingForm.getValidationButtonText().getText();
	    softAssertion.assertEquals(expectedButton, actualButton);
	    baseClass.passedStep("Button validation displatd as "+actualButton+"");
	    codingForm.getValidationButtonYes().waitAndClick(5);
	    codingForm.getCf_RemoveLink(0).waitAndClick(5);
	    codingForm.getCodingForm_SGValidation_ButtonYes().waitAndClick(5);
	    softAssertion.assertAll();
		++i;
	}

			baseClass.stepInfo("As per all functionality it wotking, finally assert condition get passed");
}
	   
	    loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that User (RMU) creates customized coding 
	 *                form using multiple tags elements
	 */
	@Test(description = "RPMXCON-53960",enabled = true, groups = { "regression" })
	public void verifyRmuUserCanCreateMuipleTagCF() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-53960");
	    baseClass.stepInfo("Verify that User (RMU) creates customized coding form "
	    		+ "using multiple tags elements");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String tagOne = "TagOne"+Utility.dynamicNameAppender();
	    String tagTwo = "TagOne"+Utility.dynamicNameAppender();
	    String tagThree = "TagOne"+Utility.dynamicNameAppender();
	    tagsAndFoldersPage=new TagsAndFoldersPage(driver);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
	    
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
	   
		//Add tags
		tagsAndFoldersPage.CreateTag(tagOne, Input.securityGroup);
		tagsAndFoldersPage.CreateTag(tagTwo, Input.securityGroup);
		tagsAndFoldersPage.CreateTag(tagThree, Input.securityGroup);

		// creating codingform using mutiple tags
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		
	    codingForm.CreateCodingFormWithParameter(codingform,tagOne,null,null,"tag");
	    codingForm.CreateCodingFormWithParameter(codingform,tagTwo,null,null,"tag");
	    codingForm.CreateCodingFormWithParameter(codingform,tagThree,null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("Selected  tags are added to coding form structure");
	    driver.waitForPageToBeReady();
	    
	    int count=codingForm.getListOfNameInCfStructure().size();
	    if (count>2) {
	    	baseClass.stepInfo("Multiple tag are added in the coding form");
		}
	    else {
			baseClass.failedStep("No tags presents in the coding form");
		}
	    codingForm.saveCodingForm();
	    
	    // validating rmu user can save the cf using multiple tag
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    baseClass.waitForElement(codingForm.getCodingForm_Search());
		codingForm.getCodingForm_Search().SendKeys(codingform);
		boolean cfTrue=codingForm.getVerifyCfSavedName(codingform).isElementAvailable(3);
		softAssertion.assertTrue(cfTrue);
		softAssertion.assertAll();
		baseClass.passedStep("Saved cf displayed in manage coding form screen");
	    loginPage.logout();
	}
	/**
	 * @Author : Aathith
	 * @Description : Verify that duplicate customized coding form does not get created on "Manage Coding Forms" screen
	 */
	@Test(description = "RPMXCON-53973",enabled = true, groups = { "regression" })
	public void validateCfScreenWorksPrprly() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-53973");
	    baseClass.stepInfo("Verify that duplicate customized coding form does not get created on \"Manage Coding Forms\" screen");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		//paremeters
		String cfName[] = {"1LR Coding Form", "  1LR Coding Form", "1LR Coding Form  ", "  1LR Coding Form  "};
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    baseClass.stepInfo("User navigated to Manage coding form page");
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    soft.assertTrue(driver.getPageSource().contains("New Coding Form"));
	    baseClass.stepInfo("coding form works properly");
	    
	    //pre-req
	    if(!cf.searchCodingForm(cfName[0])) {
	    	cf.createCodingform(cfName[0]);
	    }
	    
	    //verification
	    cf.navigateToCodingFormPage();
	    for(String codingform:cfName) {
	    	cf.addCodingFormNameOnly(codingform);
	    	baseClass.VerifyErrorMessage("10001000001 : Coding Form label is already exist");
	    }
	    
	    soft.assertAll();
	    baseClass.passedStep("Verified that duplicate customized coding form does not get created on \"Manage Coding Forms\" screen");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To Verify, as an RM user login, I will be able to sort the column based on Coding form name in manage coding forms page
	 */
	@Test(description = "RPMXCON-53975",enabled = true, groups = { "regression" })
	public void verifyRMAbleToSortCodingFormTable() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-53975");
	    baseClass.stepInfo("To Verify, as an RM user login, I will be able to sort the column baseClassd on Coding form name in manage coding forms page");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    baseClass.stepInfo("User navigated to Manage coding form page");
	    
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    int i =baseClass.getIndex(cf.getCodingFormTableHeaders(), "CODING FORM NAME");
	    soft.assertEquals(cf.getCodingFormTableHeadColumn(i).GetAttribute("aria-sort"), "ascending");
	    baseClass.stepInfo("table is in ascending order");
	    baseClass.waitTime(3);
	    cf.getCodingFormTableHeadColumn(i).waitAndClick(5);
	    driver.waitForPageToBeReady();
	    soft.assertEquals(cf.getCodingFormTableHeadColumn(i).GetAttribute("aria-sort"), "descending");
	    baseClass.stepInfo("table is in descending order");
	    
	    soft.assertAll();
	    baseClass.passedStep("Verified as an RM user login, I will be able to sort the column baseClassd on Coding form name in manage coding forms page");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To Verify, as an RM user login, I will be able to sort the column based on user created name in manage coding forms page
	 */
	@Test(description = "RPMXCON-53976",enabled = true, groups = { "regression" })
	public void verifyRMAbleToSortCodingFormTableByCreatedUser() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-53976");
	    baseClass.stepInfo("To Verify, as an RM user login, I will be able to sort the column baseClassd on user created name in manage coding forms page");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    baseClass.stepInfo("User navigated to Manage coding form page");
	    
	    //verification
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    int i = baseClass.getIndex(cf.getCodingFormTableHeaders(), "CREATED BY");
	    cf.getCodingFormTableHeadColumn(i).waitAndClick(5);
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(2);
	    soft.assertEquals(cf.getCodingFormTableHeadColumn(i).GetAttribute("aria-sort"), "ascending");
	    baseClass.stepInfo("table is in ascending order");
	    cf.getCodingFormTableHeadColumn(i).waitAndClick(5);
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(2);
	    soft.assertEquals(cf.getCodingFormTableHeadColumn(i).GetAttribute("aria-sort"), "descending");
	    baseClass.stepInfo("table is in descending order");
	    
	    soft.assertAll();
	    baseClass.passedStep("Verified, as an RM user login, I will be able to sort the column baseClassd on user created name in manage coding forms page");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To Verify, as an RM user login, I will be able to sort the column based on Date Created in manage coding forms page
	 */
	@Test(description = "RPMXCON-53977",enabled = true, groups = { "regression" })
	public void verifyRMAbleToSortCodingFormTableByCreatedDate() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-53977");
	    baseClass.stepInfo("To Verify, as an RM user login, I will be able to sort the column baseClassd on Date Created in manage coding forms page");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    baseClass.stepInfo("User navigated to Manage coding form page");
	    
	    //verification
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    int i = baseClass.getIndex(cf.getCodingFormTableHeaders(), "DATE CREATED (UTC)");
	    cf.getCodingFormTableHeadColumn(i).waitAndClick(5);
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(2);
	    soft.assertEquals(cf.getCodingFormTableHeadColumn(i).GetAttribute("aria-sort"), "ascending");
	    baseClass.stepInfo("table is in ascending order");
	    cf.getCodingFormTableHeadColumn(i).waitAndClick(5);
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(2);
	    soft.assertEquals(cf.getCodingFormTableHeadColumn(i).GetAttribute("aria-sort"), "descending");
	    baseClass.stepInfo("table is in descending order");
	    
	    soft.assertAll();
	    baseClass.passedStep("Verified, as an RM user login, I will be able to sort the column baseClassd on Date Created in manage coding forms page");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To Verify, as an RM user login, I will be able to select a default coding form from manage coding forms page
	 */
	@Test(description = "RPMXCON-53978",enabled = true, groups = { "regression" })
	public void verifyDefaultCodingFormSelected() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-53978");
	    baseClass.stepInfo("To Verify, as an RM user login, I will be able to select a default coding form from manage coding forms page");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    baseClass.stepInfo("User navigated to Manage coding form page");
	    
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    cf.searchCodingForm("Default Project Coding Form");
	    if(!cf.getDefaultCodingFormTableValues(5).getText().contains("Default")) {
	    	cf.selectDefaultProjectCodingForm();
	    }
	    baseClass.stepInfo("Selected coding form will be default coding form for that security group");
	    
	    soft.assertAll();
	    baseClass.passedStep("Veriied, as an RM user login, I will be able to select a default coding form from manage coding forms page");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that Proper validation message appears when user clicks on validation button without configuring Form Name
	 */
	@Test(description = "RPMXCON-53980",enabled = true, groups = { "regression" })
	public void verifyValidateMsgWithoutConfigCFName() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-53980");
	    baseClass.stepInfo("Verify that Proper validation message appears when user clicks on validation button without configuring Form Name");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2517 
	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    baseClass.stepInfo("User navigated to Manage coding form page");
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    soft.assertTrue(driver.getPageSource().contains("New Coding Form"));
	    baseClass.stepInfo("coding form works properly");
	    
	    cf.getAddNewCodingFormBtn().waitAndClick(10);
	    driver.waitForPageToBeReady();
	    soft.assertTrue(baseClass.text("Group: TAGS").isDisplayed());
	    baseClass.passedStep("All available Tags should get displayed");
	    cf.getCodingForm_FirstTag().waitAndClick(10);
	    cf.getCodingForm_AddToFormButton().waitAndClick(5);
	    soft.assertTrue(driver.getPageSource().contains("TAG Label"));
	    baseClass.passedStep("Selected TAG should appeared inside Coding Form Structure");
	    driver.waitForPageToBeReady();
	    cf.getCFValidateBtn().waitAndClick(5);
	    driver.waitForPageToBeReady();
	    soft.assertEquals(cf.getCodingForm_MandField().getText().trim(),"Please provide a name for your Coding Form");
	    baseClass.passedStep("Proper validation message is appears on screen");
	    
	    soft.assertAll();
	    baseClass.passedStep("Verified that Proper validation message appears when user clicks on validation button without configuring Form Name");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that user adds special objects multiple times to the coding form
	 */
	@Test(description = "RPMXCON-53983",enabled = true, groups = { "regression" })
	public void verifyUserAddSpecialObjectMultipleTime() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-53983 ");
	    baseClass.stepInfo("Verify that user adds special objects multiple times to the coding form");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		String cfName = "1LR Coding Form";
		String[] prereq = {"tag", "comment", "metadata"};
		String[] parem = {"staticText", "check", "radio"};
		String[] verifyText = {"statictext_", "checkgroup_", "radiogroup_"};
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		//EXECUTE SHARED STEPS IN TC#2547 
	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
		cf.getAddNewCodingFormBtn().waitAndClick(10);
		cf.getCodingFormName().SendKeys(cfName);
		
		//Pre-requisite 
		for(String avlobj: prereq) {
			cf.createCodingFormUsingFirstObject(cfName, avlobj);
		}
		cf.getCodingForm_AddToFormButton().waitAndClick(5);
		int i=3,n=9,j=0;
		for(String splobj: parem) {
			for(;i<n;i++) {
				cf.specialObjectsBox(splobj);
				cf.getCodingForm_AddToFormButton().waitAndClick(5);
				driver.waitForPageToBeReady();
				soft.assertTrue(driver.getPageSource().contains(verifyText[j]+i));
			}
			n+=5;j++;
		}
		baseClass.passedStep("User should  add @SpecialObjects field multiple times on  coding form");
		
	    soft.assertAll();
	    baseClass.passedStep("Verified that user adds special objects multiple times to the coding form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To verify, As an RMU login, I will be able to Edit a Coding Form
	 */
	@Test(description = "RPMXCON-53992",enabled = true, groups = { "regression" })
	public void verifyRmuAlbleToEditCF() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-53992");
	    baseClass.stepInfo("To verify, As an RMU login, I will be able to Edit a Coding Form");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    baseClass.stepInfo("User navigated to Manage coding form page");
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    soft.assertTrue(driver.getPageSource().contains("New Coding Form"));
	    baseClass.stepInfo("User will land in Coding Forms page");
	    String cfname = cf.getCodingFormTableValues(1, 1).getText().trim();
	    
	    cf.getEditClick().waitAndClick(5);
	    driver.waitForPageToBeReady();
	    soft.assertTrue(driver.getPageSource().contains("Create/Edit Coding Form"));
	    soft.assertTrue(driver.getPageSource().contains("Coding Form Editor"));
	    soft.assertTrue(baseClass.text(cfname).isElementAvailable(3));
	    baseClass.passedStep("After Edit option selected, User redirect to Create/Edit page with the selected coding form");
	    
	    
	    soft.assertAll();
	    baseClass.passedStep("verified, As an RMU login, I  able to Edit a Coding Form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To verify, As an RMU login, When I will edit an existing coding form, In edit page I can remove any of tags, comments or Metadata that associated with this coding form, & save it after removing
	 */
	@Test(description = "RPMXCON-54003",enabled = true, groups = { "regression" })
	public void verifyEditCFRemoveTag() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54003");
	    baseClass.stepInfo("To verify, As an RMU login, When I will edit an existing coding form, In edit page I can remove any of tags, comments or Metadata that associated with this coding form, & save it after removing");
	    
	    CodingForm cf = new CodingForm(driver);
		
		String cfName = "codingForm"+Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//validation
	    cf.createCodingform(cfName);
	    cf.editCodingForm(cfName);
	    cf.removeNthCodingForm(0);
	    cf.updateCodingForm();
	    baseClass.passedStep("Page is saved successfully after removing");
	    
	    //delete created coding form
	    cf.deleteCodingForm(cfName,cfName);
	    
	    baseClass.passedStep("verified, As an RMU login, When I will edit an existing coding form, In edit page I can remove any of tags, comments or Metadata that associated with this coding form, & save it after removing");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : As an RMU login, When user will apply edit on any exiting coding form, & when user modified something, & will navigate to some other page, it should ask for save the changes
	 */
	@Test(description = "RPMXCON-54004",enabled = true, groups = { "regression" })
	public void verifyEditCFRemoveTagNavigatePage() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54004");
	    baseClass.stepInfo("As an RMU login, When user will apply edit on any exiting coding form, & when user modified something, & will navigate to some other page, it should ask for save the changes");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		String cfName = "codingForm"+Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//remove one of the form in codingform
	    cf.createCodingform(cfName);
	    cf.editCodingForm(cfName);
	    cf.removeNthCodingForm(0);
	    
	    //click navigate and verify popup msg
	    cf.getManageCodingFormButton().waitAndClick(5);
	    String actualtext = cf.getCF_ValidationAlert().getText().trim();
	    System.out.println(actualtext);
	    soft.assertEquals(actualtext, "You have made changes to this Coding Form that have not yet been saved. Do you want to SAVE this Coding Form before navigating away from it?");
	    
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    soft.assertAll();
	    baseClass.passedStep("As an RMU login, When user will apply edit on any exiting coding form, & when user modified something, & will navigate to some other page, it should ask for save the changes");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To verify, As an RMU login, When I will edit an existing coding form, I can be able to select a new Comment & able to save this change
	 */
	@Test(description = "RPMXCON-54008",enabled = true, groups = { "regression" })
	public void verifyRmuAbleToSelectCommentInExitCF() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54008");
	    baseClass.stepInfo("To verify, As an RMU login, When I will edit an existing coding form, I can be able to select a new Comment & able to save this change");
	    
	    CodingForm cf = new CodingForm(driver);
		
		String cfName = "codingForm"+Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//edit cf
	    cf.createTagsSavedInCf(cfName);
	    cf.editCodingForm(cfName);
	    
	    //add comment and save cf
	    driver.waitForPageToBeReady();
	    cf.addTwoCheckBox("comment");
	    cf.updateCodingForm();
	    
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    baseClass.passedStep("verified As an RMU login, When I will edit an existing coding form, I can be able to select a new Comment & able to save this change");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that long coding form name appears properly on Coding Form screen
	 */
	@Test(description = "RPMXCON-54009",enabled = true, groups = { "regression" })
	public void verifyLongNameApeersProperly() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54009");
	    baseClass.stepInfo("Verify that long coding form name appears properly on Coding Form screen");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		String cfName = "LongCodingFormNameLongCodingFormNameLongCodingFormName";
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2517 
		 cf.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 baseClass.stepInfo("User navigated to Manage coding form page");
		 soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
		 soft.assertTrue(driver.getPageSource().contains("New Coding Form"));
		 baseClass.stepInfo("coding opens properly");
		
		//create a long name cf 
	    cf.createCodingform(cfName);
	    cf.navigateToCodingFormPage();
	    
	    //verify name display properly
	    cf.searchCodingForm(cfName);
	    String actual = cf.getCodingFormTableValues(1, 1).getText().trim();
	    soft.assertEquals(actual, cfName);
	    
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    soft.assertAll();
	    baseClass.passedStep("Verified that long coding form name appears properly on Coding Form screen");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that Preview displays correctly and properly for 
	 * Tag/Comments/Metadata objects along with Check/Radio Group and Check Item on coding form screen
	 */
	@Test(description = "RPMXCON-54060",enabled = true, groups = { "regression" })
	public void verifyPreviewDisplaysCorrecly() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54060");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for "
	    		+ "Tag/Comments/Metadata objects along with Check/Radio Group and Check Item on coding form screen");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 baseClass.waitForElement(cf.getAddNewCodingFormBtn());
		 cf.getAddNewCodingFormBtn().waitAndClick(10);
		 baseClass.waitForElement(cf.getCodingFormName());
		 cf.getCodingFormName().SendKeys(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.firstCheckBox(Input.comments);
		 cf.firstCheckBox(Input.metaData);
		 cf.specialObjectsBox(Input.staticText);
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.specialObjectsBox(Input.checkGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter details
		 cf.getCF_TagTypes(0).selectFromDropdown().selectByVisibleText("Check Item");
		 cf.selectDefaultActions(0, Input.hidden);
		 cf.selectDefaultActions(1, Input.notSelectable);
		 cf.selectDefaultActions(2, Input.optional);
		 cf.enterErrorAndHelpMsg(2, "Yes", Input.errorMsg, Input.helpText);
		 cf.selectDefaultActions(4, Input.required);
		 cf.selectDefaultActions(5, Input.optional);
		 cf.saveCodingForm();
		 
		 //edit codingform
		 cf.editCodingForm(cfName);
		 cf.clickPreviewButon();
		 
		 //verify
		 soft.assertTrue(cf.getPreviewComment().isElementAvailable(3));
		 soft.assertTrue(cf.getPreviewMetaData().isElementAvailable(3));
		 baseClass.passedStep("Preview displayed correctly and properly");
		 
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    soft.assertAll();
	    baseClass.passedStep("Verify that Preview displays correctly and properly for "
	    		+ "Tag/Comments/Metadata objects along with Check/Radio Group and Check Item on coding form screen");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that Preview displays correctly and properly for all 
	 * objects along with all condition and Radio Item on coding form screen
	 */
	@Test(description = "RPMXCON-54066",enabled = true, groups = { "regression" })
	public void verifyPreviewDisplaysCorreclyWithCondition() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54066");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for all "
	    		+ "objects along with all condition and Radio Item on coding form screen");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 baseClass.waitForElement(cf.getAddNewCodingFormBtn());
		 cf.getAddNewCodingFormBtn().waitAndClick(10);
		 baseClass.waitForElement(cf.getCodingFormName());
		 cf.getCodingFormName().SendKeys(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.firstCheckBox(Input.comments);
		 cf.firstCheckBox(Input.metaData);
		 cf.specialObjectsBox(Input.staticText);
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.specialObjectsBox(Input.checkGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter action details
		 cf.selectDefaultActions(0, Input.hidden);
		 cf.selectTagType("radio item", 0, 1);
		 cf.selectDefaultActions(1, Input.notSelectable);
		 cf.selectDefaultActions(2, Input.optional);
		 cf.enterErrorAndHelpMsg(2, "Yes", Input.errorMsg, Input.helpText);
		 cf.selectDefaultActions(4, Input.required);
		 cf.selectDefaultActions(5, Input.required);
		 
		 //add logic
		 driver.scrollPageToTop();
		 cf.addLogicOptionWithIndex(1, 1, Input.select, Input.thisHidden);
		 cf.addLogicOptionWithIndex(2, 1, Input.notSelect, Input.thisReadOnly);
		 cf.addLogicOptionWithIndexWithoutIncreace(4, 1, Input.select, Input.thisOptional);
		 cf.addLogicOptionWithIndexWithoutIncreace(5, 1, Input.notSelect, Input.thisRequired);
		 cf.saveCodingForm();
		 
		 //edit codingform
		 cf.editCodingForm(cfName);
		 
		 //validate
		 driver.waitForPageToBeReady();
		 baseClass.waitForElement(cf.getCF_PreviewButton());
		 cf.getCF_PreviewButton().waitAndClick(10);
		 driver.waitForPageToBeReady();
		 soft.assertTrue(cf.getPreviewComment().isElementAvailable(3));
		 soft.assertTrue(cf.getPreviewMetaData().isElementAvailable(3));
		 soft.assertTrue(baseClass.text("Static Text").isElementAvailable(3));
		 baseClass.passedStep("Preview displayed correctly and properly");
		 
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    soft.assertAll();
	    baseClass.passedStep("Verified that Preview displays correctly and properly for all objects "
	    		+ "along with all condition and Radio Item on coding form screen");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description Verify that Preview displays correctly and properly when 
	 * Tag and Radio Group combined along with Radio Item for new  coding form
	 */
	@Test(description = "RPMXCON-54073",enabled = true, groups = { "regression" })
	public void verifyPreviewDisplaysCorreclyTagAlongWithRadio() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54073");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly when"
	    		+ " Tag and Radio Group combined along with Radio Item for new  coding form");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 baseClass.waitForElement(cf.getAddNewCodingFormBtn());
		 cf.getAddNewCodingFormBtn().waitAndClick(10);
		 baseClass.waitForElement(cf.getCodingFormName());
		 cf.getCodingFormName().SendKeys(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter action details
		 cf.selectTagType("radio item", 0, 1);
		 cf.selectDefaultActions(1, Input.hidden);
		 
		 //validation
		 driver.scrollPageToTop();
		 cf.getCFValidateBtn().waitAndClick(10);
		 driver.waitForPageToBeReady();
		 soft.assertTrue(baseClass.text("Coding Form Validation Successful.").isElementAvailable(3));
		 cf.getCodingForm_Validation_ButtonYes().waitAndClick(5);
		 cf.clickPreviewButon();
		 soft.assertFalse(cf.getPreviewRadioBtn().isDisplayed());
		 baseClass.passedStep("Preview displayed correctly and properly");
		 
	    soft.assertAll();
	    baseClass.passedStep("Verified that Preview displays correctly and properly when "
	    		+ "Tag and Radio Group combined along with Radio Item for new  coding form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description Verify that check group association should be saved for the Tag when coding form is saved
	 */
	@Test(description = "RPMXCON-54499",enabled = true, groups = { "regression" })
	public void verifyTagWithCheckGroup() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54499");
	    baseClass.stepInfo("Verify that check group association should be saved for the Tag when coding form is saved");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 baseClass.waitForElement(cf.getAddNewCodingFormBtn());
		 cf.getAddNewCodingFormBtn().waitAndClick(10);
		 baseClass.waitForElement(cf.getCodingFormName());
		 cf.getCodingFormName().SendKeys(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.specialObjectsBox(Input.checkGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter action details
		 cf.selectTagType(Input.checkItem, 0, 1);
		 cf.saveCodingForm();
		 
		 cf.clickPreviewButon();
		 soft.assertTrue(cf.getPreviewCheckBox().isElementAvailable(3));
		 baseClass.passedStep("Check group association saved for the tag when coding form is saved.");
		 
	    soft.assertAll();
	    baseClass.passedStep("Verified that check group association should be saved for the Tag when coding form is saved");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description Verify that check group association should be saved for the Tag when coding form is edited
	 */
	@Test(description = "RPMXCON-54502",enabled = true, groups = { "regression" })
	public void verifyTagWithCheckGroupInEditCf() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54502");
	    baseClass.stepInfo("Verify that check group association should be saved for the Tag when coding form is edited");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		 cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.specialObjectsBox(Input.checkGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter action details
		 cf.selectTagType(Input.checkItem, 0, 1);
		 cf.saveCodingForm();
		 
		 //edit
		 cf.editCodingForm(cfName);
		 soft.assertTrue(baseClass.text("TAG:").isElementAvailable(3));
		 soft.assertTrue(baseClass.text("CHECKGROUP:").isElementAvailable(3));
		 
	    soft.assertAll();
	    baseClass.passedStep("Verified that check group association should be saved for the Tag when coding form is edited");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description UI: Verify the error message alignment from preview of coding form
	 */
	@Test(description = "RPMXCON-54527",enabled = true, groups = { "regression" })
	public void verifyErrMsgAligPrev() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54527");
	    baseClass.stepInfo("UI: Verify the error message alignment from preview of coding form");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		 cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.firstCheckBox(Input.comments);
		 cf.firstCheckBox(Input.metaData);
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter action details
		 cf.selectTagType("radio item", 0, 1);
		 cf.selectDefaultActions(1, Input.required);
		 cf.enterErrorAndHelpMsg(1, "Yes", Input.helpText, Input.errorMsg);
		 cf.selectDefaultActions(2, Input.required);
		 cf.enterErrorAndHelpMsg(2, "Yes", Input.helpText, Input.errorMsg);
		 cf.selectDefaultActions(3, Input.required);
		 cf.enterErrorAndHelpMsg(3, "Yes", Input.helpText, Input.errorMsg);
		 cf.saveCodingForm();
		 
		 //validation
		 cf.clickPreviewButon();
		 cf.getTestCodingForm().waitAndClick(5);
		 driver.waitForPageToBeReady();
		 
		 //err msg validation
		 soft.assertEquals(cf.getPrevError(1).getText().trim(), Input.errorMsg);
		 soft.assertEquals(cf.getPrevError(2).getText().trim(), Input.errorMsg);
		 soft.assertEquals(cf.getPrevError(3).getText().trim(), Input.errorMsg);
		 baseClass.passedStep("Preview of coding form pop up should open  Error message displayed for the required fields ");
		 
		 //'*' vlidation
		 String firstobj = cf.getCodingForm_PreviewText(1).getText().trim();
		 String secobj = cf.getCodingForm_PreviewText(2).getText().trim();
		 String thiredobj = cf.getCodingForm_PreviewText(3).getText().trim();
		 soft.assertEquals(firstobj.charAt(firstobj.length()-1), '*');
		 soft.assertEquals(secobj.charAt(secobj.length()-1), '*');
		 soft.assertEquals(thiredobj.charAt(thiredobj.length()-1), '*');
		 baseClass.passedStep("asterisk aligned properly for the required fields");
		 
	    soft.assertAll();
	    baseClass.passedStep("UI: Verified the error message alignment from preview of coding form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : 
	 * @Description : To verify as an RMU login, I will be able to Un-hide a column in manage coding form page
	 */
	@Test(description = "RPMXCON-54017",enabled = true, groups = { "regression" })
	public void verifyRmuAbleToHideUnhideColumn() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54017");
	    baseClass.stepInfo("To verify as an RMU login, I will be able to Un-hide a column in manage coding form page");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		String hideColumn = "Coding Form Name";
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		 cf.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 baseClass.stepInfo("User navigated to Manage coding form page");
		 soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
		 soft.assertTrue(driver.getPageSource().contains("New Coding Form"));
		 baseClass.stepInfo("User landed in Coding form page");
		
		//validation
		 cf.hideOrShowColum(hideColumn);
		 soft.assertFalse(baseClass.text(hideColumn).isDisplayed());
		 cf.hideOrShowColum(hideColumn);
		 soft.assertTrue(baseClass.text(hideColumn).isDisplayed());
		 baseClass.passedStep("Selected check box from hide/Unhide dropdown Unhide from Home page of manage coding form page");
	    
	    soft.assertAll();
	    baseClass.passedStep("verified as an RMU login, I will be able to Un-hide a column in manage coding form page");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that Preview displays correctly and properly for Tags objects 
	 * along with Selected and "Not Selected" condition on coding form screen
	 */
	@Test(description = "RPMXCON-54064",enabled = true, groups = { "regression" })
	public void verifyPreviewDisplayCrctlyTagAlongRadioFroup() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54064");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Tags "
	    		+ "objects along with Selected and \"Not Selected\" condition on coding form screen");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 baseClass.waitForElement(cf.getAddNewCodingFormBtn());
		 cf.getAddNewCodingFormBtn().waitAndClick(10);
		 baseClass.waitForElement(cf.getCodingFormName());
		 cf.getCodingFormName().SendKeys(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.addcodingFormAddButton();
		 cf.addTwoCheckBox(Input.tag);
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.addcodingFormAddButton();
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter action details
		 cf.selectDefaultActions(0, Input.hidden);
		 cf.selectDefaultActions(1, Input.notSelectable);
		 cf.selectTagType("radio item", 0, 1);
		 cf.selectTagType("radio item", 1, 2);
		 cf.selectDefaultActions(2, Input.optional);
		 cf.selectDefaultActions(3, Input.required);
		 
		 //add logic
		 driver.scrollPageToTop();
		 cf.addLogicOptionWithIndex(2, 1, Input.select, Input.thisOptional);
		 cf.addLogicOptionWithIndex(3, 1, Input.notSelect, Input.thisOptional);
		 cf.saveCodingForm();
		 
		 //edit codingform
		 cf.editCodingForm(cfName);
		 
		 //validate
		 cf.clickPreviewButon();
		 baseClass.moveWaitAndClick(cf.getPreview1stRadioBtn(), 15);
		 soft.assertTrue(cf.getCodingForm_PreviewText(2).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(3).isElementAvailable(3));
		 soft.assertTrue(cf.getPreviewRadioBtn().isElementAvailable(3));
		 soft.assertTrue(cf.getPreview1stRadioBtn().Selected());
		 soft.assertFalse(cf.getPreview2ndRadioBtn().Selected());
		 baseClass.passedStep("Preview Should get displayed correctly");
		 
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    soft.assertAll();
	    baseClass.passedStep("Verified that Preview displays correctly and properly for Tags objects along "
	    		+ "with Selected and \"Not Selected\" condition on coding form screen");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that Preview displays correctly and properly for all
	 *  objects along with all condition and Check Item on coding form screen
	 */
	@Test(description = "RPMXCON-54067",enabled = true, groups = { "regression" })
	public void verifyPreviewDisplaysCorreclyCheckItem() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54067");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for all objects"
	    		+ " along with all condition and Check Item on coding form screen");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 baseClass.waitForElement(cf.getAddNewCodingFormBtn());
		 cf.getAddNewCodingFormBtn().waitAndClick(10);
		 baseClass.waitForElement(cf.getCodingFormName());
		 cf.getCodingFormName().SendKeys(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.firstCheckBox(Input.comments);
		 cf.firstCheckBox(Input.metaData);
		 cf.specialObjectsBox(Input.staticText);
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.specialObjectsBox(Input.checkGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter action details
		 cf.selectDefaultActions(0, Input.hidden);
		 cf.selectTagType(Input.checkItem, 0, 1);
		 cf.selectDefaultActions(1, Input.notSelectable);
		 cf.selectDefaultActions(2, Input.optional);
		 cf.enterErrorAndHelpMsg(2, "Yes", Input.errorMsg, Input.helpText);
		 cf.selectDefaultActions(4, Input.required);
		 cf.selectDefaultActions(5, Input.required);
		 
		 //add logic
		 driver.scrollPageToTop();
		 cf.addLogicOptionWithIndex(1, 1, Input.select, Input.thisHidden);
		 cf.addLogicOptionWithIndex(2, 1, Input.notSelect, Input.thisReadOnly);
		 cf.addLogicOptionWithIndexWithoutIncreace(4, 1, Input.select, Input.thisOptional);
		 cf.addLogicOptionWithIndexWithoutIncreace(5, 1, Input.notSelect, Input.thisRequired);
		 cf.saveCodingForm();
		 
		 //edit codingform
		 cf.editCodingForm(cfName);
		 
		 //validate
		 cf.clickPreviewButon();
		 soft.assertFalse(cf.getCodingForm_PreviewText(0).isElementAvailable(1));
		 soft.assertTrue(cf.getCodingForm_PreviewText(1).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(2).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(3).isElementAvailable(3));
		 soft.assertFalse(cf.getCodingForm_PreviewText(4).isElementAvailable(1));
		 soft.assertTrue(cf.getCodingForm_PreviewText(5).isElementAvailable(3));
		 baseClass.passedStep("Preview displayed correctly and properly");
		 
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    soft.assertAll();
	    baseClass.passedStep("Verified that Preview displays correctly and properly for all objects along with all "
	    		+ "condition and Check Item on coding form screen");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that Preview displays correctly and properly for Metadata objects along with "Not Selected" condition on coding form screen
	 */
	@Test(description = "RPMXCON-54065",enabled = true, groups = { "regression" })
	public void verifyPreviewDisplayCrctlyForMetadataalongwithCheckItem() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-54065");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Metadata objects along "
	    		+ "with \"Not Selected\" condition on coding form screen");
	    
	    SoftAssert soft = new SoftAssert();
	    CodingForm cf = new CodingForm(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 baseClass.waitForElement(cf.getAddNewCodingFormBtn());
		 cf.getAddNewCodingFormBtn().waitAndClick(10);
		 baseClass.waitForElement(cf.getCodingFormName());
		 cf.getCodingFormName().SendKeys(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.metaData);
		 cf.addcodingFormAddButton();
		 cf.addTwoCheckBox(Input.metaData);
		 cf.specialObjectsBox(Input.checkGroup);
		 cf.addcodingFormAddButton();
		 cf.specialObjectsBox(Input.checkGroup);
		 cf.addcodingFormAddButton();
		 
		 //enter action details
		 cf.selectDefaultActions(0, Input.hidden);
		 cf.selectDefaultActions(1, Input.notSelectable);
		 cf.selectDefaultActions(2, Input.optional);
		 cf.selectDefaultActions(3, Input.required);
		 
		 //add logic
		 driver.scrollPageToTop();
		 cf.addLogicOptionWithIndex(2, 1, Input.notSelect, Input.thisRequired);
		 cf.saveCodingForm();
		 
		 //edit codingform
		 cf.editCodingForm(cfName);
		 
		 //validate
		 cf.clickPreviewButon();
		 soft.assertFalse(cf.getCodingForm_PreviewText(0).isDisplayed());
		 soft.assertTrue(cf.getCodingForm_PreviewText(1).isDisplayed());
		 soft.assertFalse(cf.getCodingForm_PreviewText(1).Selected());
		 baseClass.passedStep("Preview displayed correctly and properly");
		 
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    soft.assertAll();
	    baseClass.passedStep("Verify that Preview displays correctly and properly for Metadata objects "
	    		+ "along with \"Not Selected\" condition on coding form screen");
	    loginPage.logout();
	}
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify the character limit for the additional field of Radio
	 *              Group and Check Group
	 */
	@Test(description = "RPMXCON-54303", enabled = true, groups = { "regression" })
	public void verifyCharacterLimit() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54303");
		baseClass.stepInfo("Verify the character limit for the additional field of Radio Group and Check Group");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addNewCodingFormButton();
		codingForm.specialObjectsBox(Input.radioGroup);
		codingForm.addcodingFormAddButton();
		codingForm.specialObjectsBox(Input.checkGroup);
		codingForm.addcodingFormAddButton();
		
		// passing 500 character for radio instrument
		boolean radio=codingForm.getCodingForm_HelpMsg(0).isElementAvailable(2);
		softAssertion.assertTrue(radio);
		baseClass.stepInfo("Instrumental text present for radio group");
		String radioText=baseClass.passingCharacterBasedOnSize(500);
		codingForm.getCodingForm_HelpMsg(0).SendKeys(radioText);
		String radioOutput=codingForm.getCodingForm_HelpMsg(0).GetAttribute("value");
		int size=radioOutput.length();
		softAssertion.assertEquals(Integer.toString(size), "500");
		baseClass.passedStep("500 character are able to pass inside radio instrument text box");
		
		// passing 500 character for check instrument
		boolean check=codingForm.getCodingForm_HelpMsg(1).isElementAvailable(2);
		softAssertion.assertTrue(check);
		baseClass.stepInfo("Instrumental text present for check group");
		String checkText=baseClass.passingCharacterBasedOnSize(500);
		codingForm.getCodingForm_HelpMsg(1).SendKeys(checkText);
		String checkOutput=codingForm.getCodingForm_HelpMsg(1).GetAttribute("value");
		int sizeTwo=checkOutput.length();
		softAssertion.assertEquals(Integer.toString(sizeTwo), "500");
		baseClass.passedStep("500 character are able to pass inside check instrument text box");
		// Assertion
		softAssertion.assertAll();
		loginPage.logout();
	}

	
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that additional field to both Radio Groups and Check Groups 
	 *                should appear in the Coding Form
	 */
	@Test(description = "RPMXCON-54302", enabled = true, groups = { "regression" })
	public void verifyInstrumentText() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54302");
		baseClass.stepInfo("Verify that additional field to both Radio Groups and Check Groups "
				+ "should appear in the Coding Form");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addNewCodingFormButton();
		codingForm.specialObjectsBox(Input.radioGroup);
		codingForm.addcodingFormAddButton();
		codingForm.specialObjectsBox(Input.checkGroup);
		codingForm.addcodingFormAddButton();
		
		// validating the additional fields
		boolean radio=codingForm.getCodingForm_HelpMsg(0).isElementAvailable(2);
		softAssertion.assertTrue(radio);
		baseClass.stepInfo("Instrumental text present for radio group");
		boolean check=codingForm.getCodingForm_HelpMsg(1).isElementAvailable(2);
		softAssertion.assertTrue(check);
		baseClass.stepInfo("Instrumental text present for check group");
		baseClass.passedStep("Additional fields are displayed for both radio and check group");
		softAssertion.assertAll();
		loginPage.logout();
	}

	
	/**
	 * @Author :Indium-Baskar
	 * @Description : To verify as an RMU login, user will be able to see 100 records on
	 *                selecting 100 from drop-down in manage coding form page
	 */
	@Test(description = "RPMXCON-54020", enabled = true, groups = { "regression" })
	public void verify100RecordsInCf() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54020");
		baseClass.stepInfo("To verify as an RMU login, user will be able to see 100 records on "
				+ "selecting 100 from drop-down in manage coding form page");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		allCf = new LinkedList<String>();
		// creating codingform
		for (int i = 1; i <=100 ; i++) {
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			codingForm.addNewCodingFormButton();
			String name=codingForm.passingCodingFormName("cf" + Utility.dynamicNameAppender());
			allCf.add(name);
			baseClass.waitForElement(codingForm.getSaveCFBtn());
			codingForm.getSaveCFBtn().waitAndClick(5);
		}
		// validating 100 records
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		codingForm.getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("100");
		baseClass.stepInfo("Selecting the dropdown as 100");
		baseClass.waitTime(5);
		int count=codingForm.getCFnames().size();
		softAssertion.assertEquals(Integer.toString(count), "100");
		baseClass.passedStep("Manage codingform screen 100 records are displayed when user selected dropdown as 100");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar
	 * @Description : To verify as an RMU login, user will be able to see 25 records 
	 *                on selecting 25 from drop-down in manage coding form page
	 */
	@Test(description = "RPMXCON-54018", enabled = true, groups = { "regression" })
	public void verify25RecordsInCf() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54018");
		baseClass.stepInfo("To verify as an RMU login, user will be able to see 25 records "
				+ "on selecting 25 from drop-down in manage coding form page");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		// validating 25 records
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		codingForm.getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("25");
		baseClass.stepInfo("Selecting the dropdown as 25");
		baseClass.waitTime(5);
		int count=codingForm.getCFnames().size();
		softAssertion.assertEquals(Integer.toString(count), "25");
		baseClass.passedStep("Manage codingform screen 25 records are displayed when user selected dropdown as 25");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar
	 * @Description : To verify as an RMU login, user will be able to see 50 records on selecting 
	 *                50 from drop-down in manage coding form page
	 */
	@Test(description = "RPMXCON-54019", enabled = true, groups = { "regression" })
	public void verify50RecordsInCf() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54019");
		baseClass.stepInfo("To verify as an RMU login, user will be able to see 50 records on "
				+ "selecting 50 from drop-down in manage coding form page");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		// validating 25 records
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		codingForm.getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("50");
		baseClass.stepInfo("Selecting the dropdown as 50");
		baseClass.waitTime(5);
		int count=codingForm.getCFnames().size();
		softAssertion.assertEquals(Integer.toString(count), "50");
		baseClass.passedStep("Manage codingform screen 50 records are displayed when user selected dropdown as 50");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : Verify that radio group association should be saved for the Tag when coding form is saved
	 */
	@Test(description = "RPMXCON-54500", enabled = true, groups = { "regression" })
	public void verifyRadioGrpAssociationOfSavedCF() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54500");
		baseClass.stepInfo("Verify that radio group association should be saved for the Tag when coding form is saved");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);
		String codingform = "cf"+Utility.dynamicNameAppender();
		String tagname = "tag"+Utility.dynamicNameAppender();
		int index = 1;
		String expectedRadioGrp = "radiogroup_"+index+""; 
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		// Create tag
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		baseClass.stepInfo("Created the tag sucessfully");
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");		
		codingForm.addNewCodingFormButton();
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.CreateCodingFormWithParameter(codingform,tagname,null,null,"tag");
		codingForm.addcodingFormAddButton();
		codingForm.specialObjectsBox(Input.radioGroup);
		codingForm.addcodingFormAddButton();
		codingForm.selectTagTypeByIndex("radio item",index,0);
		baseClass.stepInfo("Radio group is associated to the created tag");
		codingForm.saveCodingForm();
		baseClass.stepInfo("Coding form saved successfully");
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		baseClass.waitForElement(codingForm.getTagGroupValues(index));
		String actualRadioGroup = codingForm.getTagGroupValues(index).GetAttribute("systemcontrolname");
		softAssertion.assertEquals(expectedRadioGrp, actualRadioGroup);
		softAssertion.assertAll();
		baseClass.passedStep("The radio group associated in tag is successfully reflected after saving the coding form");
		codingForm.deleteCodingForm(codingform, codingform);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.deleteAllTags(tagname);
		loginPage.logout();
	}
	
	/**
	 * @Author :Iyappan.Kasinathan
	 * @Description : Verify that radio group association should be saved for the Tag when coding form is edited
	 */
	@Test(description = "RPMXCON-54501", enabled = true, groups = { "regression" })
	public void verifyRadioGrpAssociationOfEditedCF() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54501");
		baseClass.stepInfo("Verify that radio group association should be saved for the Tag when coding form is edited");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);
		String codingform = "cf"+Utility.dynamicNameAppender();
		String tagname = "tag"+Utility.dynamicNameAppender();
		int index = 1;
		String expectedRadioGrp = "radiogroup_"+index+"";
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		baseClass.stepInfo("Created the tag sucessfully");
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");		
		codingForm.addNewCodingFormButton();
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.CreateCodingFormWithParameter(codingform,tagname,null,null,"tag");
		codingForm.addcodingFormAddButton();
		codingForm.specialObjectsBox(Input.radioGroup);
		codingForm.addcodingFormAddButton();
		codingForm.selectTagTypeByIndex("radio item",index,0);
		baseClass.stepInfo("Radio group is associated to the created tag");
		codingForm.saveCodingForm();
		baseClass.stepInfo("Coding form saved successfully");
		//Edit the existing coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		//verify the radio group associated with tag
		codingForm.editCodingForm(codingform);
		baseClass.stepInfo("Edited the coding form sucessfully");
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		baseClass.waitForElement(codingForm.getTagGroupValues(index));
		String actualRadioGroup = codingForm.getTagGroupValues(index).GetAttribute("systemcontrolname");
		softAssertion.assertEquals(expectedRadioGrp, actualRadioGroup);
		softAssertion.assertAll();
		baseClass.passedStep("The radio group associated in tag is successfully reflected after editing the saved coding form");
		//Deleting the tag and cf
		codingForm.deleteCodingForm(codingform, codingform);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.deleteAllTags(tagname);
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that "Save Confirmation" message appears on "Coding Form" Screen
	 */
	@Test(description = "RPMXCON-54007", enabled = true, groups = { "regression" })
	public void verifySaveConfrmMsg() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54007");
		baseClass.stepInfo("Verify that \"Save Confirmation\" message appears on \"Coding Form\" Screen");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		tagsAndFoldersPage=new TagsAndFoldersPage(driver);
		String tag="cf" + Utility.dynamicNameAppender();
		String cfName="cfName" + Utility.dynamicNameAppender();
		String fdName="fname" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		tagsAndFoldersPage.CreateTag(tag, Input.securityGroup);
		// creating
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.addNewCodingFormButton();
		codingForm.basedOnCreatingNewObject(tag, null, null, "tag");
		codingForm.addcodingFormAddButton();
		codingForm.passingCodingFormName(cfName);
		codingForm.saveCodingForm();
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		// editing the codingform
		codingForm.editCodingForm(cfName);
		baseClass.waitForElement(codingForm.getTag_Object(tag));
		codingForm.getTag_Object(tag).waitAndClick(5);
		codingForm.getCF_objectName(0).SendKeys(fdName);
		baseClass.stepInfo("editing the friendly label for tag");
		codingForm.getManageCodingFormButton().waitAndClick(5);
		baseClass.stepInfo("clicking on manage coding form tab");
		// validation of confirm message
		boolean flag=codingForm.getSaveWarningMsg().isElementAvailable(2);
		softAssertion.assertTrue(flag);
		codingForm.getValidationButtonYes().waitAndClick(5);
		boolean flagCnfrm=codingForm.getSaveConformMsg().isElementAvailable(2);
		softAssertion.assertTrue(flagCnfrm);
		baseClass.passedStep("confirmation displayed after user edit label when clicked manage coding form tab");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar
	 * @Description :Verify that duplicate customized coding form does not get 
	 *                created on Using Copy functionality "Manage Coding Forms" screen
	 */
	@Test(description = "RPMXCON-54011", enabled = true, groups = { "regression" })
	public void verifyDuplicateCF() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54011");
		baseClass.stepInfo("Verify that duplicate customized coding form does not get created on Using "
				+ "Copy functionality \"Manage Coding Forms\" screen");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		tagsAndFoldersPage=new TagsAndFoldersPage(driver);
		String cfName="cfName" + Utility.dynamicNameAppender();
		String rename="cfName" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		// creating
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.addNewCodingFormButton();
		codingForm.passingCodingFormName(cfName);
		driver.scrollPageToTop();
		baseClass.waitForElement(codingForm.getSaveCFBtn());
		codingForm.getSaveCFBtn().waitAndClick(5);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		// copying the codingform
		for (int i = 1; i <=4; i++) {
			if (i==3) {
				codingForm.editCodingForm(cfName);
				codingForm.passingCodingFormName(rename);
				baseClass.waitForElement(codingForm.getSaveCFBtn());
				codingForm.getSaveCFBtn().waitAndClick(5);
			}
			if (i==1||i==2||i==4) {
				if (i==1||i==2) {
					codingForm.copyCodingForm(cfName);
				}
				if (i==4) {
					codingForm.copyCodingForm(rename);
				}
				if (i==1||i==2) {
					baseClass.waitForElement(codingForm.getCodingForm_Search());
					codingForm.getCodingForm_Search().SendKeys(cfName);
				}
				if (i==4) {
					baseClass.waitForElement(codingForm.getCodingForm_Search());
					codingForm.getCodingForm_Search().SendKeys(rename);
				}
				if (i==1||i==2||i==4) {
				baseClass.waitTime(3);
				List<String> manageScreen = new LinkedList<String>();
				List<WebElement>data=codingForm.getCFnames().FindWebElements();
				for (WebElement codingName : data) {
					String value = codingName.getText().toString();
					if (i==1||i==2) {
						if (value.equals(cfName)) {
						}
						else {
							manageScreen.add(value);
						}
					}
					if (i==4) {
						if (value.equals(rename)) {
						}
						else {
							manageScreen.add(value);
						}
					}
					
				}
				if (i==2) {
					manageScreen.remove(0);
				}
				String datas=manageScreen.get(0).toString();
				System.out.println(datas);
				baseClass.stepInfo("while copying codingform name generated with new name as '"+datas+"'" );
				softAssertion.assertNotEquals(cfName, datas);
				manageScreen.remove(0);
				}
			}
		}
		baseClass.passedStep("No duplicated coding form present at manage codingform screen while copying");
		baseClass.passedStep("Successfull message received as ,Coding form copied successfully");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @Author :Baskar
	 * @Description : Verify that after removing the radio/check group association of the tag, 
	 *                tag label should be displayed in preview of coding form
	 */
	@Test(description = "RPMXCON-54305", enabled = true, groups = { "regression" })
	public void verifyTagLabelAfterRemovingGroup() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54305");
		baseClass.stepInfo("Verify that after removing the radio/check group association of the tag, tag label should be displayed in preview of coding form");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		String codingform = "cf"+Utility.dynamicNameAppender();
		String tagname = "tag1"+Utility.dynamicNameAppender();
		String tagTwo = "tag2"+Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		tagsAndFoldersPage.CreateTag(tagname, Input.securityGroup);
		tagsAndFoldersPage.CreateTag(tagTwo, Input.securityGroup);
		baseClass.stepInfo("Created the tag sucessfully");
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");		
		codingForm.addNewCodingFormButton();
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.CreateCodingFormWithParameter(codingform,tagname,null,null,"tag");
		codingForm.CreateCodingFormWithParameter(codingform,tagTwo,null,null,"tag");
		codingForm.addcodingFormAddButton();
		codingForm.specialObjectsBox(Input.radioGroup);
		codingForm.specialObjectsBox(Input.checkGroup);
		codingForm.addcodingFormAddButton();
		codingForm.selectTagTypeByIndex("radio item",1,0);
		baseClass.stepInfo("Radio group is associated to the created tag");
		codingForm.selectTagTypeByIndex("check item",1,1);
		baseClass.stepInfo("check group is associated to the created tag");
		codingForm.saveCodingForm();
		baseClass.waitTime(10);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Coding form saved successfully");
		
		// validation for radio and checkgroup button
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		driver.waitForPageToBeReady();
//		baseClass.waitForElement(codingForm.getCF_Preview_Radio(tagname));
		boolean radio=codingForm.getCF_Preview_Radio(tagname).isElementAvailable(5);
		System.out.println(radio);
		baseClass.stepInfo("In Preview validation radio button are displayed");
		softAssertion.assertTrue(radio);
//		baseClass.waitForElement(codingForm.getCF_Preview_CheckBox(tagTwo));
		boolean checkBox=codingForm.getCF_Preview_CheckBox(tagTwo).isElementAvailable(5);
		System.out.println(checkBox);
		baseClass.stepInfo("In Preview validation checkbox button are displayed");
		softAssertion.assertTrue(checkBox);
		baseClass.waitForElement(codingForm.getCF_Preview_OkBtn());
		codingForm.getCF_Preview_OkBtn().waitAndClick(5);
		codingForm.getCodingForm_SelectRemoveLink(2).ScrollTo();
		baseClass.waitForElement(codingForm.getCodingForm_SelectRemoveLink(2));
		codingForm.getCodingForm_SelectRemoveLink(2).waitAndClick(5);
		codingForm.getCodingForm_SelectRemoveLink(3).ScrollTo();
		baseClass.waitForElement(codingForm.getCodingForm_SelectRemoveLink(3));
		codingForm.getCodingForm_SelectRemoveLink(3).waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Removed both radio and checkbox special objects");
		
		// validation of tag label after remove group
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		baseClass.waitTime(5);
		driver.waitForPageToBeReady();
		boolean checkBoxOne=codingForm.getCF_Preview_TagLabel(tagname,0).isElementAvailable(5);
		System.out.println(checkBoxOne);
		softAssertion.assertTrue(checkBoxOne);
		boolean checkBoxTwo=codingForm.getCF_Preview_TagLabel(tagTwo,1).isElementAvailable(5);
		System.out.println(checkBoxTwo);
		softAssertion.assertTrue(checkBoxTwo);
		codingForm.getCF_Preview_OkBtn().waitAndClick(5);
		
		softAssertion.assertAll();
		baseClass.passedStep("After removing the group tag, Tag label displayed as expected");
		//Deleting the tag and cf
		codingForm.deleteCodingForm(codingform, codingform);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFoldersPage.deleteAllTags(tagname);
		tagsAndFoldersPage.deleteAllTags(tagTwo);
		loginPage.logout();
	}
	
	
	/**
	 * @Author :Indium-Baskar
	 * @Description :Verify that Proper validation message appears 
	 *               when a user deletes added object(s) from coding form screen
	 */
	@Test(description = "RPMXCON-54226", enabled = true, groups = { "regression" })
	public void verifyRemoveAllTabsInAsc() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54226");
		baseClass.stepInfo("Verify that Proper validation message appears when a user "
				+ "deletes added object(s) from coding form screen");
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
		baseClass.waitTime(3);
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
			codingForm.selectRemoveLinkWithValidation(i);
		}
		baseClass.stepInfo("Removing all data from codingform structure");
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
	 * @Author :Baskar
	 * @Description : Verify that Preview displays correctly and properly when Tag 
	 *                   and Check group combined along with Check Item for New coding form
	 */
	@Test(description = "RPMXCON-54074", enabled = true, groups = { "regression" })
	public void verifyPreviewAlongWithCheckItem() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-54074");
		baseClass.stepInfo("Verify that Preview displays correctly and properly when Tag and"
				+ " Check group combined along with Check Item for New coding form");
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		commentsPage = new CommentsPage(driver);
		String codingform = "cf"+Utility.dynamicNameAppender();
		String tagname = "tag"+Utility.dynamicNameAppender();
		int index = 1;
		String expectedCheckGrp = "checkgroup_"+index+"";
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);
		baseClass.stepInfo("Created the tag sucessfully");
		
		// creating codingform
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");		
		codingForm.addNewCodingFormButton();
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.CreateCodingFormWithParameter(codingform,tagname,null,null,"tag");
		codingForm.addcodingFormAddButton();
		codingForm.specialObjectsBox(Input.checkGroup);
		codingForm.addcodingFormAddButton();
		codingForm.selectTagTypeByIndex("check item",index,0);
		codingForm.getRGDefaultAction().ScrollTo();
		baseClass.waitForElement(codingForm.getRGDefaultAction());
		codingForm.getRGDefaultAction().selectFromDropdown().selectByVisibleText(Input.notSelectable);
		baseClass.stepInfo("check group is associated to the created tag");
		driver.scrollPageToTop();
		
		// validating the success message in validate button
		baseClass.waitForElement(codingForm.getCFValidateBtn());
		codingForm.getCFValidateBtn().waitAndClick(5);
		String validationExpected="Coding Form Validation Successful.";
		String validationActual=codingForm.getCFValidateMsg().getText();
		softAssertion.assertEquals(validationActual, validationExpected);
		baseClass.passedStep("without any error ,successfull validation message displayed");
		baseClass.waitForElement(codingForm.getCFValidationPopUpOkBtn());
		codingForm.getCFValidationPopUpOkBtn().waitAndClick(5);
		baseClass.waitForElement(codingForm.getSaveCFBtn());
		codingForm.getSaveCFBtn().waitAndClick(5);
		baseClass.waitForElement(codingForm.getValidationButtonYes());
		codingForm.getValidationButtonYes().waitAndClick(5);
		baseClass.stepInfo("Coding form saved successfully");
		
		//Edit the existing coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		//verify the radio group associated with tag
		codingForm.editCodingForm(codingform);
		baseClass.stepInfo("Edited the coding form sucessfully");
		
		// preview validation
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		boolean notSelect=codingForm.getDisabledTagsInPreviewBox(tagname).isElementAvailable(2);
		softAssertion.assertTrue(notSelect);
		baseClass.waitForElement(codingForm.getTagGroupValues(index));
		String actualCheckGroup = codingForm.getTagGroupValues(index).GetAttribute("systemcontrolname");
		softAssertion.assertEquals(expectedCheckGrp, actualCheckGroup);
		softAssertion.assertAll();
		baseClass.passedStep("The check group associated in tag is successfully reflected after editing the saved coding form");
		baseClass.passedStep("preview display correctly along with checkgroup and tag in notselectable state");
		
		//Deleting the tag and cf
		codingForm.deleteCodingForm(codingform, codingform);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.deleteAllTags(tagname);

		loginPage.logout();
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
			String tag = "Atag1" + Utility.dynamicNameAppender();
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
	public void verifyRemoveAllTabsInAscendingOrder() throws Exception {
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
		try {;
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
