package signtline.codingForms;

import pageFactory.DocViewPage;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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

public class CodingForm_IndiumRegressionPhase2 {
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
	    String codingform = "1LR Coding Form";
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
