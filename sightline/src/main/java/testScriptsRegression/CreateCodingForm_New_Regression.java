package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.asserts.SoftAssert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CreateCodingForm_New_Regression {
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

	String dateTime = "DateTime" + Utility.dynamicNameAppender();
	String intData = "INT" + Utility.dynamicNameAppender();
	String NVARCHAR = "Nvarchar" + Utility.dynamicNameAppender();
	String date = "Date" + Utility.dynamicNameAppender();
	String time = "Time" + Utility.dynamicNameAppender();
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
	// Open browser
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		savedSearch = new SavedSearch(driver);
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);

		
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
		
	}

	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference RPMXCON-51207] Verify on click of 'Save and Next' 
	 *                button coding form should be validated as per the customized Tags 
	 *                and Radio Group combined along with Radio Item on coding form screen 
	 *                in context of security group
	 * 
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 01)
	public void  createCodingFormWithTwotags() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52086");
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String tagnameprev = "Tagprev"+Utility.dynamicNameAppender();
		String tag="tag";

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
        // create tag
		tagsAndFoldersPage.CreateTag(tagnameprev,"Default Security Group");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.AddCodingFormWithTwoTag(codingfrom, tagnameprev, "tag",tag, "radio item");
		baseClass.stepInfo("Coding form saved with the two tags along with radio button");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1,Input.language);

		docViewPage.selectPureHit();

		// Search for non-audio documents
		baseClass.stepInfo("Searching non-audio documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();
		
		docViewPage.saveAndNextNewCodingFormTags();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");

		
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference RPMXCON-51206] Verify on click of 'Save and Next' button 
	 *                coding form should be validated as per the customized coding form for Tag/Comments/Metadata 
	 *                objects in context of security group
	 * 
	 */
	//@Test(enabled = false, groups = { "regression" }, priority = 02)
	public void  createCodingFormWithMetaData() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52085");
		String codingfrom = "CF" + Utility.dynamicNameAppender();
		String tagnameprev = "Tagprev" + Utility.dynamicNameAppender();
		String comment = "Comment" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		String text = codingForm.addCodingFormWithAllTabsMetaDataError(codingfrom, tagnameprev, comment);
		baseClass.stepInfo("Coding form saved with Editable metadata with error message");

		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		// Search for non-audio documents
		baseClass.stepInfo("Searching non-audio documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.saveAndNextMetaDataErrorMsg(text);

		codingForm.assignCodingFormToSG("Default Project Coding Form");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference RPMXCON-51204] Verify on click of 'Save and Next' 
	 *                button coding form should be validated as per the customized coding 
	 *                form using Comment object on coding form screen in context of security group
	 * 
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 03)
	public void  createCodingFormWithComment() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52084");
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String tagnameprev = "Tagprev"+Utility.dynamicNameAppender();
		String comment = "Comment"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		CommentsPage cp= new CommentsPage(driver);
		cp.AddComments(comment);
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addCodingFormWithComment(codingfrom, tagnameprev, comment);
		baseClass.stepInfo("Coding form saved with comment with Make it display but not selectable");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1,Input.language);

		docViewPage.selectPureHit();

		// Search for non-audio documents
		baseClass.stepInfo("Searching non-audio documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.saveAndNextCommentNotClickable();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference RPMXCON-51203] Verify on click of 'Save and Next' 
	 *                button coding form should be validated as per the customized coding form 
	 *                using Tag object along with Check Item in context of security group
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 04)
	public void  createCodingFormWithTagsOptional() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52083");
		String tag="tag";
		String tagTypeCheck="check item";
		String action="Make It Optional";
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String tagnameprev = "Tagprev"+Utility.dynamicNameAppender();
		String comment = "Comment"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		 // create tag
		tagsAndFoldersPage.CreateTag(tagnameprev,"Default Security Group");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addCodingFormWithTag(codingfrom, tagnameprev, comment,tag,tagTypeCheck,action);
		baseClass.stepInfo("Coding form saved with comment with Make it display but not selectable");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1,Input.language);

		docViewPage.selectPureHit();

		// Search for non-audio documents
		baseClass.stepInfo("Searching non-audio documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.saveAndNextNewCodingFormTags();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference: RPMXCON-51200] Verify on click of 'Save and Next' 
	 *                button coding form should be validated as per the customized coding 
	 *                form using multiple comments elements
	 */
	
	//@Test(enabled = false, groups = { "regression" }, priority = 05)
	public void  createCodingFormWithCommentError() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52071");
		String tag="tag";
		String tagTypeCheck="check item";
		String action="Make It Required";
		String comments="comment";
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String tagnameprev = "Tagprev"+Utility.dynamicNameAppender();
		String comment = "Comment"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		CommentsPage cp= new CommentsPage(driver);
		cp.AddComments(comment);
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addCodingFormWithCommentValidation(codingfrom, tagnameprev, comment,comments,tagTypeCheck,action);
		baseClass.stepInfo("Coding form saved with comment with Make it display but not selectable");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1,Input.language);

		docViewPage.selectPureHit();

		// Search for non-audio documents
		baseClass.stepInfo("Searching non-audio documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.saveAndNextCommentTextBoxValidation();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : Verify the appearance of the text in the Coding Form Panel of 
	 *                DocView from child window
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 06)
	public void  appearanceTextParentWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51507");
		String tag="tag";
		String tagTypeRadio="radio item";
		String action="Make It Required";
		String comments="comment";
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String tagnameprev = "Tagprev"+Utility.dynamicNameAppender();
		String comment = "Comment"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		 // create tag
		tagsAndFoldersPage.CreateTag(tagnameprev,"Default Security Group");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addCodingFormWithTagInstructionText(codingfrom, tagnameprev, comment,tag,tagTypeRadio,action);
		baseClass.stepInfo("Coding form saved with comment with Make it display but not selectable");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docViewPage.apperanceInstructionTextLeftParent();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : Verify the appearance of the text in the Coding Form Panel of 
	 *                DocView from parent window
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 07)
	public void  appearanceTextChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51506");
		String tag="tag";
		String tagTypeRadio="radio item";
		String action="Make It Required";
		String comments="comment";
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String tagnameprev = "Tagprev"+Utility.dynamicNameAppender();
		String comment = "Comment"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		 // create tag
		tagsAndFoldersPage.CreateTag(tagnameprev,"Default Security Group");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addCodingFormWithTagInstructionText(codingfrom, tagnameprev, comment,tag,tagTypeRadio,action);
		baseClass.stepInfo("Coding form saved with comment with Make it display but not selectable");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docViewPage.apperanceInstructionTextLeftChild();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference RPMXCON-51202] Verify on click of 'Save and Next' 
	 *                button coding form should be validated as per the customized coding 
	 *                form using Multiple Static Text elements in context of security group
	 */

	//@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void  CreateCodingFormUsingStatic() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52082");
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String staticText="Mandatory field to  be selected";
		UtilityLog.info("Started Execution for prerequisite");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addCodingFormWithStaticText(codingfrom,staticText);
		baseClass.stepInfo("Coding form saved with comment with Make it display but not selectable");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docViewPage.staticTextErrorMessage();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that custom metadata field value should be retained 
	 *               on doc view when created with DATETIME datatype in context of security group
	 */
	
	//@Test(enabled = false, groups = { "regression" }, priority = 9)
	public void verifyThatCustomMetaDataFieldValueWithDATETIMEDatatype() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52175");
	    String codingfrom = "CFDateTime"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATETIME DataType
		projectPage.addCustomFieldDataType(dateTime, "DATETIME");
		baseClass.stepInfo("Custom meta data field created with DATETIME datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(dateTime);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingfrom, dateTime);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(dateTime);

		// logout
		loginPage.logout();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
//		Doing cleanup activity done in prerequities
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();
		
	}

	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should 
	 *               be retained on doc view when created with INT datatype in 
	 *               context of assignment and security group
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void verifyThatCustomMetaDataFieldValueWithINTDataTypeAssign() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52178");
	    String codingfrom = "CFINTDataType"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(intData, "INT");
		baseClass.stepInfo("Custom meta data field created with INT datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(intData);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingfrom, intData);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingfrom);
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(intData);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should be 
	 *               retained on doc view when created with NVARCHAR datatype in 
	 *               context of assignment
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void verifyThatCustomMetaDataFieldValueWithNVARCHARDataTypeAssign() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52179");
	    String codingForms = "CFNVARCHARDataType"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with NVARCHAR DataType
		projectPage.addCustomFieldProjectDataType(NVARCHAR, "NVARCHAR");
		baseClass.stepInfo("Custom meta data field created with NVARCHAR datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(NVARCHAR);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingForms, NVARCHAR);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingForms);
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(NVARCHAR);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingForms,codingForms);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should be 
	 *               retained on doc view when created with DATE datatype in
	 *               context of assignment
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 12)
	public void verifyThatCustomMetaDataFieldValueWithDATEDatatypeAssgn() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52180");
	    String codingForms = "CFDate"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();

		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATE DataType
		projectPage.addCustomFieldDataType(date, "Date");
		baseClass.stepInfo("Custom meta data field created with DATE datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(date);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingForms, date);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingForms);
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(date);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingForms,codingForms);
		
		// logout
		loginPage.logout();

		
	}

	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should 
	 *               be retained on doc view when created with DATETIME
	 *               datatype in context of assignment
	 */
	
	//@Test(enabled = false, groups = { "regression" }, priority = 13)
	public void verifyThatCustomMetaDataFieldValueWithDATETIMEDatatypeAssgn() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52181");
	    String codingForms = "CFDateTime"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();

		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATETIME DataType
		projectPage.addCustomFieldDataType(dateTime, "DATETIME");
		baseClass.stepInfo("Custom meta data field created with DATETIME datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(dateTime);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingForms, dateTime);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingForms);
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(dateTime);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingForms,codingForms);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should 
	 *               be retained on click of saved stamp in context of security group
	 */
	
	//@Test(enabled = false, groups = { "regression" }, priority = 14)
	public void verifyThatCustomMetaDataFieldValueDatatypeOnSavedStamp() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52183");
	    String codingfrom = "CFINT"+Utility.dynamicNameAppender();
	    String stampText = "New"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(intData, "INT");
		baseClass.stepInfo("Custom meta data field created with INT datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(intData);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingfrom, intData);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.clickOnSavedStamp(stampText, Input.stampSelection,intData);
		
		// logout
		loginPage.logout();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
//		Doing cleanup activity done in prerequities
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();
		
	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should 
	 *               be retained in view coding pop up of saved stamp in 
	 *               context of security group
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 15)
	public void verifyThatCustomMetaDataFieldValueDatatypeOnSavedStampPopUP() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52184");
	    String codingfrom = "CFINT"+Utility.dynamicNameAppender();
	    String stampText = "New"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(intData, "INT");
		baseClass.stepInfo("Custom meta data field created with DATETIME datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(intData);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingfrom, intData);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.viewCodingPopUp(stampText, Input.stampSelection,intData);
		
		// logout
		loginPage.logout();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
//		Doing cleanup activity done in prerequities
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();
		
	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should be 
	 *               retained on click of saved stamp in context of assignment
	 */
	
	//@Test(enabled = false, groups = { "regression" }, priority = 16)
	public void verifyThatCustomMetaDataFieldValueWithINTDatatypeAssgn() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52185");
	    String codingForms = "CFINT"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String stampText = "New"+Utility.dynamicNameAppender();


		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(intData, "INT");
		baseClass.stepInfo("Custom meta data field created with INT datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(intData);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingForms, intData);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingForms);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.clickOnSavedStamp(stampText, Input.stampSelection,intData);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingForms,codingForms);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should 
	 *               be retained in view coding pop up of saved stamp 
	 *               in context of assignment
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 17)
	public void verifyThatCustomMetaDataFieldValueWithDATETIMEDatatypeAssgnStampPopUp() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52186");
	    String codingForms = "CFDateTime"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String stampText = "New"+Utility.dynamicNameAppender();

		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATETIME DataType
		projectPage.addCustomFieldDataType(dateTime, "DATETIME");
		baseClass.stepInfo("Custom meta data field created with DATETIME datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(dateTime);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingForms, dateTime);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingForms);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.viewCodingPopUp(stampText, Input.stampSelection,dateTime);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingForms,codingForms);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:9/11/2021 Modified by: Baskar 
	 * @Description :Verify that custom metadata field value should be retained 
	 *               on doc view when created with DATE datatype in 
	 *               context of security group
	 */
	
	//@Test(enabled = false, groups = { "regression" }, priority = 18)
	public void verifyThatCustomMetaDataFieldValueDATEDataType() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52174");
	    baseClass.stepInfo("Verify that custom metadata field value should be retained "
	    		+ "on doc view when created with DATE datatype in context of security group");
	    String codingfrom = "CFINT"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATE DataType
		projectPage.addCustomFieldDataType(date, "Date");
		baseClass.stepInfo("Custom meta data field created with DATE datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(date);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingfrom, date);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(date);
		driver.waitForPageToBeReady();
		
		// logout
		loginPage.logout();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
//		Doing cleanup activity done in prerequities
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();
		
	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify validation of coding form when coding form is 
	 *               created with metadata field as DateTime on saving of the stamp
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void validationOfNonDateFormat() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51583");
	    baseClass.stepInfo("Verify validation of coding form when coding form is created "
	    		+ "with metadata field as DateOnly on saving of the stamp");
	    String codingfrom = "CFDateTime"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String assgnColour = "ColourAssign"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DateTime DataType
		projectPage.addCustomFieldDataType(dateTime, "DATETIME");
		baseClass.stepInfo("Custom meta data field created with DateTime datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(dateTime);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnUsingParameter(codingfrom, dateTime,"Make it Optional");
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingfrom);
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.nonDateFormatValidation(dateTime,assgnColour,Input.stampSelection);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify on click of 'Save'/'Complete button coding form should 
	 *               be validated as per the customized coding form using 
	 *               Multiple Static Text elements
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 20)
	public void staticTextToDisplayInCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51202");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated "
	    		+ "as per the customized coding form using Multiple Static Text elements");
	    String codingfrom = "CFStaticText"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
		String staticText="Mandatory field to  be selected";
		UtilityLog.info("Started Execution for prerequisite");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addCodingFormWithStaticText(codingfrom,staticText);
		baseClass.stepInfo("Coding form created with static text");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingfrom);
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		docViewPage.staticTextToBeDisplayed(staticText);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify on click of 'Save'/'Complete button coding form should
	 *               be validated as per the customized coding form 
	 *               using Multiple Metadata elements
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 21)
	public void metaDataUsingCustomizedCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51201");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be "
	    		+ "validated as per the customized coding form using Multiple Metadata elements");
	    String codingfrom = "CFMetaData"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String defaultAction="Make It Required";
		UtilityLog.info("Started Execution for prerequisite");
		
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
			
		
		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(intData, "INT");
		baseClass.stepInfo("Custom meta data field created with INT datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(intData);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.metaDataValidation(codingfrom,intData,defaultAction);
		baseClass.stepInfo("Coding form created with metadata fieldValue");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingfrom);
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description :Verify on click of 'Save'/'Complete button coding form should be validated as per 
	 *                  the customized coding form using multiple tags elements
	 */
	//@Test(enabled = false, groups = { "regression" }, priority = 22)
	public void verifyCustomizedCodingFormUsingMultipleTags() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51199");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using multiple tags elements");
	    String codingform = "CFTags"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String tagName =null;
	    String commentName = null;
	    String metadataName = null;
	    String defaultAction="Make It Required";
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.createCodingFormUsingTwoObjects(codingform, tagName, commentName, metadataName, "tag");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectDefaultActions(0,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(0,"Yes","Help for testing","Error for testing");
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.selectDefaultActions(1,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(1,"Yes","Help for testing","Error for testing");
	 	String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	 	System.out.println(expectedSecondObjectName);
	 	codingForm.saveCodingForm();
	 	//create assignment
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		//Impersonate to reviewer
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		docViewPage.verifyCodingFormName(codingform);
		//verify tags are disbled
		docViewPage.verifyTagsAreDisabled(0);
		docViewPage.verifyTagsAreDisabled(1);
		//verify tag names
		docViewPage.verifyCodingFormTagNameInDocviewPg(0,expectedFirstObjectName);
		docViewPage.verifyCodingFormTagNameInDocviewPg(1,expectedSecondObjectName);		
		loginPage.logout();
		//Login as reviewer
		loginPage.loginToSightLine(Input.rev1userName,Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		docViewPage.verifyCodingFormName(codingform);
		//verify tags are disbled
		docViewPage.verifyTagsAreDisabled(0);
		docViewPage.verifyTagsAreDisabled(1);
		//verify tag names
		docViewPage.verifyCodingFormTagNameInDocviewPg(0,expectedFirstObjectName);
		docViewPage.verifyCodingFormTagNameInDocviewPg(1,expectedSecondObjectName);
		loginPage.logout();
		//delete assignment and codinform
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform,codingform);	
		codingForm.verifyCodingFormIsDeleted(codingform);
		loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description :Verify on click of 'Save'/'Complete button coding form should be validated as per the
	 *                         customized coding form for editable metadata fields
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 23)
	public void verifyCustomizedCodingFormUsingMultipleMetadatas() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51196");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form"+
	                        " for editable metadata fields");
	    String codingform = "CFMetadatas"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String tagName =null;
	    String commentName = null;
	    String metadataName = null;
	    String defaultAction="Make It Required";
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.createCodingFormUsingTwoObjects(codingform, tagName, commentName, metadataName, "metadata");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectDefaultActions(0,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(0,"Yes","Help for testing","Error for testing");
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.selectDefaultActions(1,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(1,"Yes","Help for testing","Error for testing");
	 	String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	 	System.out.println(expectedSecondObjectName);
	 	codingForm.saveCodingForm();
	 	//create assignment and asign to coding form
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		//Impersonate as reviewer
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//validations in docviewPage
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);
		docViewPage.verifyCodingFormNameInDocviewPg(0,expectedFirstObjectName+"*");
		docViewPage.verifyCodingFormNameInDocviewPg(1,expectedSecondObjectName+"*");		
		docViewPage.verifyMetaDataHelpnErrorMsgInDocviewPg(expectedFirstObjectName,"Help for testing","Error for testing");
		docViewPage.verifyMetaDataHelpnErrorMsgInDocviewPg(expectedSecondObjectName,"Help for testing","Error for testing");
		loginPage.logout();
		//LogIn as reviewer
		loginPage.loginToSightLine(Input.rev1userName,Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//validations in docviewPage
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);		
		docViewPage.verifyCodingFormNameInDocviewPg(0,expectedFirstObjectName+"*");
		docViewPage.verifyCodingFormNameInDocviewPg(1,expectedSecondObjectName+"*");		
		docViewPage.verifyMetaDataHelpnErrorMsgInDocviewPg(expectedFirstObjectName,"Help for testing","Error for testing");
		docViewPage.verifyMetaDataHelpnErrorMsgInDocviewPg(expectedSecondObjectName,"Help for testing","Error for testing");
		loginPage.logout();
		//Delete assignment and codingform
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform,codingform);	
		codingForm.verifyCodingFormIsDeleted(codingform);
		loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per
	 *                the customized coding form using radio group
	 */
	//@Test(enabled = false, groups = { "regression" }, priority = 24)
	public void verifyCustomizedCodingFormUsingRadioGroup() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51197");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per "+
	                                "the customized coding form using radio group");
	    String codingform = "CFTagRadioGrp"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String tagName = "cfTag"+Utility.dynamicNameAppender();
	    String defaultAction="Make It Required";
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
        //create tag
	 	tagsAndFoldersPage.CreateTag(tagName, Input.securityGroup);
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
	 	codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		baseClass.waitForElement(codingForm.getCodingForm_Tag(tagName));
		codingForm.getCodingForm_Tag(tagName).waitAndClick(10);
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectRadioOrCheckGroup("radio item");
	 	codingForm.enterErrorAndHelpMsg(0,"No","Help for testing",null);
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.selectDefaultActions(1,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(1,"Yes","Help for testing","Error for testing");
	 	codingForm.saveCodingForm();
	 	//create assignment and assign to codingform
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		//Impersonated as reviewer
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//Validations in docview page
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);
		docViewPage.validateRadioOrCheckGroupInDocviewPg("radio-group");
		docViewPage.verifyCodingFormRadioGrpTagNameInDocviewPg(0,expectedFirstObjectName);		
		docViewPage.verifyHelpnErrorMsgOfRadioGrpTagInDocviewPg(0,"Help for testing","Error for testing");		
		loginPage.logout();
		//LogIn as reviewer
		loginPage.loginToSightLine(Input.rev1userName,Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//Validations in docview page
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);		
		docViewPage.validateRadioOrCheckGroupInDocviewPg("radio-group");
		docViewPage.verifyCodingFormRadioGrpTagNameInDocviewPg(0,expectedFirstObjectName);		
		docViewPage.verifyHelpnErrorMsgOfRadioGrpTagInDocviewPg(0,"Help for testing","Error for testing");		
		loginPage.logout();
		//Delete assignments,codingform and tag
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);		
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform,codingform);	
		codingForm.verifyCodingFormIsDeleted(codingform);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFoldersPage.deleteAllTags(tagName);
		loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as 
	 *                per the customized coding form using check group
	 */
	//@Test(enabled = false, groups = { "regression" }, priority = 25)
	public void verifyCustomizedCodingFormUsingCheckGroup() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51198");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per "+
	                              " the customized coding form using check group");
	    String codingform = "CFTagCheckGrp"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String tagName = "cfTag"+Utility.dynamicNameAppender();
	    String defaultAction="Make It Required";
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
        //create tag
	 	tagsAndFoldersPage.CreateTag(tagName, Input.securityGroup);
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
	 	codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		baseClass.waitForElement(codingForm.getCodingForm_Tag(tagName));
		codingForm.getCodingForm_Tag(tagName).waitAndClick(10);
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectRadioOrCheckGroup("check item");
	 	codingForm.enterErrorAndHelpMsg(0,"No","Help for testing",null);
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.selectDefaultActions(1,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(1,"Yes","Help for testing","Error for testing");
	 	codingForm.saveCodingForm();
	 	//Create assignment and assign the coding form
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		//Impersonated as reviewer
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//Validations in docview page
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);
		docViewPage.validateRadioOrCheckGroupInDocviewPg("check-group");
		docViewPage.verifyCodingFormCheckGrpTagNameInDocviewPg(0,expectedFirstObjectName);		
		docViewPage.verifyHelpnErrorMsgOfCheckGrpTagInDocviewPg(0,"Help for testing","Error for testing");		
		loginPage.logout();
		//LogIn as reviewer
		loginPage.loginToSightLine(Input.rev1userName,Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//Validations in docview page
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);		
		docViewPage.validateRadioOrCheckGroupInDocviewPg("check-group");
		docViewPage.verifyCodingFormCheckGrpTagNameInDocviewPg(0,expectedFirstObjectName);		
		docViewPage.verifyHelpnErrorMsgOfCheckGrpTagInDocviewPg(0,"Help for testing","Error for testing");		
		loginPage.logout();
		//Delete assignment,codingform and tag
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);		
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform,codingform);	
		codingForm.verifyCodingFormIsDeleted(codingform);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFoldersPage.deleteAllTags(tagName);
		loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for Comment object for new coding form
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 26)
	public void validatePreviewDisplayOfComment() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54069");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Comment object for new coding form");
	    String codingform = "CFcomment"+Utility.dynamicNameAppender();
	    String defaultAction="Make It Display But Not Selectable";
	    //LogIn as rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.createCodingFormUsingFirstObject(codingform, "comment");
	    codingForm.addcodingFormAddButton();
	    codingForm.selectDefaultActions(0,defaultAction);
	    String commentName = codingForm.getCFObjectsLabel(0);
	    codingForm.saveCodingForm();
	    //verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    codingForm.verifyCommentDisabled(commentName);
	    codingForm.verifyCFObjectsLabel(0, commentName);
	    codingForm.validatePreviewSuccessMsg();
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for Editable Metadata object for new coding form
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 27)
	public void validatePreviewDisplayOfMetadata() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54070");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Editable Metadata object for new coding form");
	    String codingform = "CFmetadata"+Utility.dynamicNameAppender();
	    String defaultAction="Make It Optional";
	    //LogIn as rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.createCodingFormUsingFirstObject(codingform, "metadata");
	    codingForm.addcodingFormAddButton();
	    codingForm.selectDefaultActions(0,defaultAction);
	    String metaDataName = codingForm.getCFObjectsLabel(0);
	    codingForm.saveCodingForm();
	    //verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    codingForm.verifyCFObjectsLabel(0, metaDataName);
	    baseClass.stepInfo("Verify the field without enter any text");
	    codingForm.validatePreviewSuccessMsg();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.waitTillElemetToBeClickable( codingForm.getCFmetadataTextBox(metaDataName));
	    codingForm.getCFmetadataTextBox(metaDataName).SendKeys(Input.searchString1);
	    baseClass.stepInfo("Verify the field by passing the text");
	    codingForm.validatePreviewSuccessMsg();
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for Static object for New coding form
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 28)
	public void validatePreviewDisplayOfStaticText() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54072");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Static object for New coding form");
	    String codingform = "CFStatic"+Utility.dynamicNameAppender();
	    //LogIn as rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.addCodingFormWithStaticText(codingform,Input.searchString1);
	    //verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    codingForm.verifyCFObjectsLabel(0, Input.searchString1);
	    codingForm.validatePreviewSuccessMsg();
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for 
	 *                Tag/Comments/Metadata objects for New coding form
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 29)
	public void validatePreviewDisplayOfMultipleObjects() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54071");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Tag/Comments/Metadata objects for New coding form");
	    String codingform = "CFmetadata"+Utility.dynamicNameAppender();
	    //Login as a rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    //create codingform
	    codingForm.createCodingFormUsingFirstObject(codingform,"tag");
	    baseClass.waitForElement(codingForm.getCodingForm_CommentTab());
	    codingForm.getCodingForm_CommentTab().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingForm_FirstComment());
		codingForm.getCodingForm_FirstComment().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingForm_EDITABLE_METADATA_Tab());
		codingForm.getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingForm_FirstMetadata());
		codingForm.getCodingForm_FirstMetadata().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingForm_SecondMetadata());
		codingForm.getCodingForm_SecondMetadata().waitAndClick(10);
		codingForm.addcodingFormAddButton();
	 	codingForm.enterErrorAndHelpMsg(0,"Yes","Help for testing","Error for testing");
	 	String tagName = codingForm.getCFObjectsLabel(0);
	 	String commentName = codingForm.getCFObjectsLabel(1);
	 	String metadataName1 = codingForm.getCFObjectsLabel(2);
	 	String metadatName2 = codingForm.getCFObjectsLabel(3);
	 	codingForm.saveCodingForm();
	 	//verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    codingForm.verifyCFObjectsLabel(0,tagName);
	    codingForm.verifyCFObjectsLabel(1,commentName);
	    codingForm.verifyCFObjectsLabel(2,metadataName1);
	    codingForm.verifyCFObjectsLabel(3,metadatName2);
	    codingForm.validatePreviewSuccessMsg();
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description :Verify on click of 'Save'/'Complete button coding form should be validated as per the 
	 *               customized coding form using multiple comments elements
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 30)
	public void verifyCustomizedCodingFormUsingMultipleComments() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51200");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding"+
	                                   " form using multiple comments elements");
	    String codingform = "CFComments"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String tagName =null;
	    String commentName = null;
	    String metadataName = null;
	    String defaultAction="Make It Required";
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.createCodingFormUsingTwoObjects(codingform, tagName, commentName, metadataName, "comment");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectDefaultActions(0,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(0,"Yes","Help for testing","Error for testing");
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.selectDefaultActions(1,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(1,"Yes","Help for testing","Error for testing");
	 	String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	 	System.out.println(expectedSecondObjectName);
	 	codingForm.saveCodingForm();
	 	//create assignment
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		System.out.println(assgnCoding);
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		//impersonate as reviewer
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//validations on docview page
		baseClass.waitForElement(docViewPage.getCodingFormHelpText(expectedFirstObjectName));
		docViewPage.getCodingFormHelpText(expectedFirstObjectName).Clear();
		docViewPage.getCodingFormHelpText(expectedFirstObjectName).Clear();
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);
		docViewPage.verifyCodingFormNameInDocviewPg(0,expectedFirstObjectName+"*");
		docViewPage.verifyCodingFormNameInDocviewPg(1,expectedSecondObjectName+"*");		
		docViewPage.verifyHelpnErrorMsgInDocviewPg(expectedFirstObjectName,"Help for testing","Error for testing");
		docViewPage.verifyHelpnErrorMsgInDocviewPg(expectedSecondObjectName,"Help for testing","Error for testing");
		loginPage.logout();
		//login as reviewer
		loginPage.loginToSightLine(Input.rev1userName,Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//validations on docview page
		baseClass.waitForElement(docViewPage.getCodingFormHelpText(expectedFirstObjectName));
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.getCodingFormHelpText(expectedFirstObjectName).Clear();
		docViewPage.getCodingFormHelpText(expectedFirstObjectName).Clear();
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);		
		docViewPage.verifyCodingFormNameInDocviewPg(0,expectedFirstObjectName+"*");
		docViewPage.verifyCodingFormNameInDocviewPg(1,expectedSecondObjectName+"*");		
		docViewPage.verifyHelpnErrorMsgInDocviewPg(expectedFirstObjectName,"Help for testing","Error for testing");
		docViewPage.verifyHelpnErrorMsgInDocviewPg(expectedSecondObjectName,"Help for testing","Error for testing");		
		loginPage.logout();
		//delete codingform
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		System.out.println(assgnCoding);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform,codingform);	
		codingForm.verifyCodingFormIsDeleted(codingform);
		loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for Tag objects along with "Selected"
	 *               condition user for New coding form
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 31)
	public void validatePreviewDisplayTagObjects() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54077");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Tag objects along with"+
	                           " \"Selected\" condition user for New coding form");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String defaultAction1 = "Make It Display But Not Selectable";
	    String defaultAction2 = "Make It Hidden";
	    String actionName = "Make this Hidden";
	    //LogIn as rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.createCodingFormUsingTwoObjects(codingform,null,null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.selectDefaultActions(0,defaultAction1);
	    codingForm.selectDefaultActions(1,defaultAction2);
	 	String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	 	codingForm.selectFieldLogicValues(0,expectedSecondObjectName,"Selected",actionName);
	 	codingForm.saveCodingForm();
	    //verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    docViewPage.verifyTagsAreDisabled(0);
	    baseClass.ValidateElement_Absence(codingForm.getCFPreviewObjectName(expectedSecondObjectName), "Expected tag name is not present");
	    baseClass.passedStep("Expected tag name is not present");
	    codingForm.validatePreviewSuccessMsg();
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}	
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for Comments objects 
	 *                along with "Not Selected" condition user for New coding form
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 32)
	public void validatePreviewDisplayCommentObjects() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54078");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Comments objects along with"+
	                               " \"Not Selected\" condition user for New coding form");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String defaultAction1 = "Make It Optional";
	    String defaultAction2 = "Make It Required";
	    String actionName = "Make this Read Only";
	    //LogIn as rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.createCodingFormUsingTwoObjects(codingform,null,null,null,"comment");
	    codingForm.addcodingFormAddButton();
	    codingForm.selectDefaultActions(0,defaultAction1);
	    codingForm.selectDefaultActions(1,defaultAction2);
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	 	driver.scrollingToBottomofAPage();
	 	codingForm.selectFieldLogicValues(1,expectedFirstObjectName,"Not Selected",actionName);
	 	codingForm.saveCodingForm();
	    //verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    codingForm.verifyCommentDisabled(expectedSecondObjectName);	    
	    codingForm.validatePreviewSuccessMsg();
	    baseClass.stepInfo("Without pass any text to both comment tab got successfull msg as expected");
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.waitForElement(codingForm.getCFPreviewCommentField(expectedFirstObjectName));
	    codingForm.getCFPreviewCommentField(expectedFirstObjectName).SendKeys(Input.searchString1);
	    codingForm.validatePreviewErrorMsg();
	    baseClass.stepInfo("Pass text to first comment tab alone and  got error msg as expected");
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.waitForElement(codingForm.getCFPreviewCommentField(expectedFirstObjectName));
	    codingForm.getCFPreviewCommentField(expectedFirstObjectName).SendKeys(Input.searchString1);
	    baseClass.waitForElement(codingForm.getCFPreviewCommentField(expectedSecondObjectName));
	    codingForm.getCFPreviewCommentField(expectedSecondObjectName).SendKeys(Input.searchString1);
	    codingForm.validatePreviewSuccessMsg();
	    baseClass.stepInfo("By passing text to both comments tab got successfull msg as expected");
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for Tag objects along with "Selected"
	 *                condition user on coding form screen
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 33)
	public void validatePreviewDisplayTagObjectsByEditing() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54062");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Tag objects along "+
	                            "with \"Selected\" condition user on coding form screen");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String defaultAction1 = "Make It Display But Not Selectable";
	    String defaultAction2 = "Make It Hidden";
	    String actionName = "Make this Hidden";
	    //LogIn as rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.createCodingFormUsingTwoObjects(codingform,null,null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.selectDefaultActions(0,defaultAction1);
	    codingForm.selectDefaultActions(1,defaultAction2);
	 	String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	 	codingForm.selectFieldLogicValues(0,expectedSecondObjectName,"Selected",actionName);
	 	codingForm.saveCodingForm();
	 	codingForm.editCodingFormAfterSavingCodingForm(codingform);
	    //verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    docViewPage.verifyTagsAreDisabled(0);
	    baseClass.ValidateElement_Absence(codingForm.getCFPreviewObjectName(expectedSecondObjectName), "Expected tag name is not present");
	    baseClass.passedStep("Expected tag name is not present");
	    codingForm.validatePreviewSuccessMsg();
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for Comments objects along with
	 *                "Not Selected" condition user on coding form screen
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 34)
	public void validatePreviewDisplayCommentObjectsByEditing() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54063");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Comments objects along with "+
	                             "\"Not Selected\" condition user on coding form screen");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String defaultAction1 = "Make It Optional";
	    String defaultAction2 = "Make It Required";
	    String actionName = "Make this Read Only";
	    //LogIn as rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.createCodingFormUsingTwoObjects(codingform,null,null,null,"comment");
	    codingForm.addcodingFormAddButton();
	    codingForm.selectDefaultActions(0,defaultAction1);
	    codingForm.selectDefaultActions(1,defaultAction2);
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	 	driver.scrollingToBottomofAPage();
	 	codingForm.selectFieldLogicValues(1,expectedFirstObjectName,"Not Selected",actionName);
	 	codingForm.saveCodingForm();
 	 	codingForm.editCodingFormAfterSavingCodingForm(codingform);
	    //verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    codingForm.verifyCommentDisabled(expectedSecondObjectName);	    
	    codingForm.validatePreviewSuccessMsg();
	    baseClass.stepInfo("Without pass any text to both comment tab got successfull msg as expected");
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.waitForElement(codingForm.getCFPreviewCommentField(expectedFirstObjectName));
	    codingForm.getCFPreviewCommentField(expectedFirstObjectName).SendKeys(Input.searchString1);
	    codingForm.validatePreviewErrorMsg();
	    baseClass.stepInfo("Pass text to first comment tab alone and  got error msg as expected");
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.waitForElement(codingForm.getCFPreviewCommentField(expectedFirstObjectName));
	    codingForm.getCFPreviewCommentField(expectedFirstObjectName).SendKeys(Input.searchString1);
	    baseClass.waitForElement(codingForm.getCFPreviewCommentField(expectedSecondObjectName));
	    codingForm.getCFPreviewCommentField(expectedSecondObjectName).SendKeys(Input.searchString1);
	    codingForm.validatePreviewSuccessMsg();
	    baseClass.stepInfo("By passing text to both comments tab got successfull msg as expected");
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}

	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Coding Form Preview is working Properly when user configured Field logic as
	 *                 Part of Radio Group objects in Coding Form
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 35)
	public void validatePreviewDisplayUsingRadioGroupObjects() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54568");
	    baseClass.stepInfo("Verify that Coding Form Preview is working Properly when user configured Field logic as"+
	                        " Part of Radio Group objects in Coding Form");
	    String defaultAction = "Make It Hidden";
	    String actionName = "Make this Required";
	    String codingform = "CFTag"+Utility.dynamicNameAppender();	   
		//LogIn as rmu
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form as per attachment
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
	    codingForm.CreateCodingFormWithParameter(codingform,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Technical_Issue",null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Processing_Issue",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Foreign_Language",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Privileged",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("radio item",1,0);
	    codingForm.selectTagTypeByIndex("radio item",1,1);
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    driver.scrollPageToTop();
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("radio item",2,2);
	    codingForm.selectTagTypeByIndex("radio item",2,3);
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",1,4);
	    codingForm.enterObjectName(5, "Responsive Group");
	    codingForm.enterObjectName(6, "Tech Issue Group");
	    codingForm.enterObjectName(7, "Check Group");
	    codingForm.selectDefaultActions(6, defaultAction);
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	    String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	    String expecteFourthObjectName = codingForm.getCFObjectsLabel(4);
	    codingForm.selectFieldLogicValues(6,expectedFirstObjectName,"Selected",actionName);
	    codingForm.selectOperatorAndFieldLogics(6,2,expectedSecondObjectName,"Selected","OR");
	    codingForm.selectOperatorAndFieldLogics(6,3,expecteFourthObjectName,"Selected","OR");
	    codingForm.saveCodingForm();
	    baseClass.stepInfo("Two check group and one radio group are added to coding form");
	    //validations
	    try {
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.ValidateElement_Presence(codingForm.getTagInPreviewBoxHidden(6), "Hidden tag");
	    baseClass.passedStep("The tags are hidden as expected");
	    codingForm.validatePreviewSuccessMsg();
	    baseClass.stepInfo("Apply field logic rules in tech issue radio group");
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
	    driver.waitForPageToBeReady();
	    codingForm.selectTagInPreviewBox(7).waitAndClick(10);
		codingForm.validatePreviewErrorMsg();
		baseClass.passedStep("Got error messsage successfully as expected");
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	baseClass.failedStep("Failed to validate preview display");
	    }
		//delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Coding Form Preview is working Properly when user configured Field logic
	 *                as Part of Check Group objects in Coding Form
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 36)
	public void validatePreviewDisplayUsingCheckGroupObjects() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54569");
	    baseClass.stepInfo("Verify that Coding Form Preview is working Properly when user configured Field logic as"+
	                                   " Part of Check Group objects in Coding Form");
	    String defaultAction = "Make It Hidden";
	    String actionName = "Make this Required";
	    String codingform = "CFTag"+Utility.dynamicNameAppender();	   
		//LogIn as rmu
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form as per attachment
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		//Add tags
	    codingForm.CreateCodingFormWithParameter(codingform,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Technical_Issue",null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Processing_Issue",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Foreign_Language",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Privileged",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    driver.scrollPageToTop();
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",2,2);
	    codingForm.selectTagTypeByIndex("check item",2,3);
	    driver.scrollPageToTop();
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("radio item",1,4);
	    codingForm.enterObjectName(5, "Responsive Group");
	    codingForm.enterObjectName(6, "Tech Issue Group");
	    codingForm.enterObjectName(7, "Radio Group");
	    codingForm.selectDefaultActions(6, defaultAction);
	    baseClass.stepInfo("Two check group and one radio group are added to coding form");
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	    String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	    String expecteFourthObjectName = codingForm.getCFObjectsLabel(4);
	    codingForm.selectFieldLogicValues(6,expectedFirstObjectName,"Selected",actionName);
	    codingForm.selectOperatorAndFieldLogics(6,2,expectedSecondObjectName,"Selected","OR");
	    codingForm.selectOperatorAndFieldLogics(6,3,expecteFourthObjectName,"Selected","OR");
	    codingForm.saveCodingForm();
	    //validations
	    try {
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.ValidateElement_Presence(codingForm.getTagInPreviewBoxHidden(6), "Hidden tag");
	    baseClass.passedStep("The tags are hidden as expected");
	    codingForm.validatePreviewSuccessMsg();
	    baseClass.stepInfo("Apply field logic rules in tech issue check group");
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
	    driver.waitForPageToBeReady();
	    codingForm.selectTagInPreviewBox(7).waitAndClick(10);
		codingForm.validatePreviewErrorMsg();
		baseClass.passedStep("Got error messsage successfully as expected");
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	baseClass.failedStep("Failed to validate preview display");
	    }
		//delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}

	
	/**
	 * @Author : Baskar date:23/11/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify coding form for a document in an assignment after
	 *              applying coding stamp when different coding form is applied to
	 *              assignment and security group and document is viewed from basic
	 *              search
	 */

	//@Test(enabled = false, groups = { "regression" }, priority = 37)
	public void differentCodingStampShouldDisplayToSGAndAssgn() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51004");
		baseClass.stepInfo("Verify coding form for a document in an assignment after applying "
				+ "       coding stamp when different coding form is applied to "
				+ "assignment and security group and document is viewed from basic search");
		String AssignStamp = "StampPopUp" + Utility.dynamicNameAppender();
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();
		String codingFormName = "NewCF" + Utility.dynamicNameAppender();
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

//		Creating new coding form
		codingForm.differentCodingForm(codingFormName);

//	    Assgn coding form to SG
		codingForm.assignCodingFormToSG(codingFormName);

		// searching document for assignmnet creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		String docId = docViewPage.stampForSameCodingForm(assgnColour, Input.stampSelection);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docViewPage.differentCoding(docId);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		assignmentPage.deleteAssgnmntUsingPagination(AssignStamp);
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		codingForm.deleteCodingForm(codingFormName, codingFormName);

		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Mohan date:23/11/21 Modified date: NA Modified by: NA 
	 * @Description :Verify that While creating a Coding Form, Associated 
	 * RadioGroup Tag(s)- values does not disappear in coding form. 'RPMXCON-54545'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 38)
	public void verifyCodingFormWithRadioGroupTagAndCheckGroup() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-54545");
		baseClass.stepInfo("Verify that While creating a Coding Form, Associated RadioGroup Tag(s)- values does not disappear in coding form.");
		
		
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String radio="Responsive Group";
		String check="Tech Issue Group";
		String tag = "tag";
		String tag1Lable = "Responsive";
		String tag2Lable = "Technical_Issue";
		String tag3Lable = "Processing_Issue";


		// Login As Reviewer Manager
		baseClass.stepInfo("Step 2: Login to Application with RMU");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		//select Tags
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		
		baseClass.stepInfo("Step 3&4: Navigate Manage >> Coding Form and Create a Coding Form as per attachment.");
		driver.waitForPageToBeReady();
		codingForm.createCodingFormUsingTwoObjects(codingfrom,null,null,null, tag);
		codingForm.addcodingFormAddButton();
		codingForm.enterObjectName(0, tag1Lable);
		String cfObjectsLabel = codingForm.getCFObjectsLabel(0);
		codingForm.enterObjectName(1, tag2Lable);
		String cfObjectsLabel1 = codingForm.getCFObjectsLabel(1);
		driver.scrollPageToTop();
		baseClass.waitForElement(codingForm.getCodingForm_SelectTagCheckBox(3));
		codingForm.getCodingForm_SelectTagCheckBox(3).waitAndClick(10);
		codingForm.addcodingFormAddButton();
		codingForm.enterObjectName(2, tag3Lable);
		String cfObjectsLabel2 = codingForm.getCFObjectsLabel(2);
		driver.scrollPageToTop();
		codingForm.getCF_RadioGrpObject().waitAndClick(10);
		codingForm.addcodingFormAddButton();
		codingForm.enterObjectName(3, radio);
		driver.scrollPageToTop();
		codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
		codingForm.enterObjectName(4, check);
		codingForm.selectTagTypeByIndex("radio item",1,0);
		codingForm.selectTagTypeByIndex("radio item",1,1);
		codingForm.selectTagTypeByIndex("check item",1,2);
		codingForm.saveCodingForm();
		
		baseClass.stepInfo("Step 5: Verify that While creating a Coding Form, Associated RadioGroup Tag(s)- values does not disappear in coding form.");
		codingForm.editCodingFormAfterSavingCodingForm(codingfrom);
		baseClass.waitForElement(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		String cfPreviewObjectsLabel = codingForm.getCFPreviewRadioText(cfObjectsLabel).getText();
		String cfPreviewObjectsLabel1 = codingForm.getCFPreviewRadioText(cfObjectsLabel1).getText();
		String cfPreviewObjectsLabel2 = codingForm.getCFPreviewCheckText(cfObjectsLabel2).getText();
		
		softAssertion.assertEquals(cfObjectsLabel,cfPreviewObjectsLabel);
		softAssertion.assertEquals(cfObjectsLabel1,cfPreviewObjectsLabel1);
		softAssertion.assertEquals(cfObjectsLabel2,cfPreviewObjectsLabel2);
		baseClass.passedStep("While creating a Coding Form, Associated RadioGroup Tag(s)- values are not disappear in coding form successfully");
		
		driver.waitForPageToBeReady();
		baseClass.waitForElement(codingForm.getCFPreviewPopUpOkBtn());
		codingForm.getCFPreviewPopUpOkBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getManageCodingFormButton());
		codingForm.getManageCodingFormButton().waitAndClick(10);
		codingForm.deleteCodingForm(codingfrom, codingfrom);
		codingForm.verifyCodingFormIsDeleted(codingfrom);
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date: 26/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that for RMU/Reviewer user 'Code same as last' 
	 *                should not be enable on click of 'Save'/'Save and Next' 
	 *                when coding form created with static text
	 */

	//@Test(enabled = true, groups = { "regression" }, priority = 39)
	public void staticTextWarningMsgUsingSave() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52147");
		baseClass.stepInfo("Verify that for RMU/Reviewer user 'Code same as last' "
				+ "should not be enable on click of 'Save'/'Save and Next' "
				+ "when coding form created with static text");
		String staticText = "staticText" + Utility.dynamicNameAppender();
		String cfName = "CfStatic" + Utility.dynamicNameAppender();
		
		// Login as a Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();

		// Creating Coding Form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.creationOfStaticText(staticText,cfName);
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Project field added to coding form in Doc view");

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching non-audio documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);
		sessionSearch.ViewInDocView();
		
		// verify warning message
		docViewPage.warningMessageStaticUsingSave();
		
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(cfName,cfName);
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		
		driver.waitForPageToBeReady();
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date:29/11/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference RPMXCON-51205] Verify on click of 'Save and Next' 
	 *                coding form should be validated as per the customized coding form 
	 *                for Editable Metadata objects
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 40)
	public void  verifyEditableMetaDataWithOptional() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52102");
		baseClass.stepInfo("Verify on click of 'Save and Next' coding form should be "
				+ "validated as per the customized coding form for Editable Metadata objects");
		String cfName = "CF"+Utility.dynamicNameAppender();
		String passingValue = "Edit"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		String textBox=codingForm.createWithOneMetaData(cfName);
		baseClass.stepInfo("Coding form saved with Editable metadata value with optional mode");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyMetaDataTab(textBox, passingValue);

		codingForm.assignCodingFormToSG("Default Project Coding Form");
		codingForm.deleteCodingForm(cfName, cfName);
		
		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date:29/11/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference RPMXCON-51210] Verify validation of coding form on 
	 *                click of 'Save and Next' using Tag/Comments/Metadata objects 
	 *                along with Check/Radio Group and Radio Item
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 41)
	public void  verifyCustomizedUsingSaveAndNext() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52101");
		baseClass.stepInfo("Verify validation of coding form on "
				+ "click of 'Save and Next' using Tag/Comments/Metadata objects along "
				+ "with Check/Radio Group and Radio Item");
		String cfName = "CF"+Utility.dynamicNameAppender();
		String error="Error"+Utility.dynamicNameAppender();
		String help="Help"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.errorMsgForComments(cfName,help,error);
		baseClass.stepInfo("Coding form created with tag/comment/metadata");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.validRequiredComment(error);
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date:1/12/21 Modified date: NA Modified by: Baskar 
	 * @Description : Coding form child window: Verify that user should not save the 
	 *                coding stamp without selecting coding form for required fields 
	 *                in context of security group
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 42)
	public void  validateChildwindowForRequiredTag() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52045");
		baseClass.stepInfo("Test case Id: RPMXCON-52044");
		baseClass.stepInfo("Verify that user should not save the "
				+ "coding stamp without selecting coding form for required fields "
				+ "in context of security group");
		String cfName = "CF"+Utility.dynamicNameAppender();
		String fieldText="Stamp"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.commentRequired(cfName);
		baseClass.stepInfo("Coding form created with comments required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.validateWithoutEditUsingStamp(fieldText);
		
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52077
	 * @Description:To Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form for comment element in context of security group. 
	 */
	//@Test(enabled = true,groups = { "regression" }, priority = 43)
	public void  validateChildwindowErrorMsg() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52077");
		
		baseClass.stepInfo("Verify that user should not save the "
				+ "coding stamp without selecting coding form for required fields "
				+ "in context of security group");
		String cfName = "CF"+Utility.dynamicNameAppender();
		String fieldText="Stamp"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.saveCodingFormWithErrorAndTextmsg(cfName);
		baseClass.stepInfo("Coding form created with comments required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyErrorMsg();
		docViewPage.openChildWindow();
		
		baseClass.passedStep("Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form for comment element in context of security group ");
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52078
	 * @Description:To Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form for editable metadata fields in context of security group.
	 */
	//@Test(enabled = true,groups = { "regression" }, priority = 44)
	public void  validateChildwindowErrorMsgInMeta() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52078");
		
		baseClass.stepInfo("Verify that user should not save the "
				+ "coding stamp without selecting coding form for required fields "
				+ "in context of security group");
		String cfName = "CF"+Utility.dynamicNameAppender();
		String fieldText="Stamp"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.saveCodingFormWithErrorAndTextmsgInMetaData(cfName);
		baseClass.stepInfo("Coding form created with comments required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyErrorMsgforMetaData();
		docViewPage.openChildWindowInMeta();
		
		baseClass.passedStep("Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form for editable metadata fields in context of security group");		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date:08/12/21 Modified date: NA Modified by: Baskar 
	 * @Description : Verify that a coding form appears correctly and properly in 
	 *                doc view screen - Search >> Doc View
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 45)
	public void  validateCodingFormAppear() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51460");
		baseClass.stepInfo("Verify that a coding form appears correctly "
				+ "and properly in doc view screen - Search >> Doc View");
		String cfName = "CF"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.commentRequired(cfName);
		baseClass.stepInfo("Coding form created with comments");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		codingForm.ViewCFinDocViewThrSearch(cfName);
		docViewPage.verifyPanels();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52080
	 * @Description : To Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using check group.
	 */
	//@Test(enabled = true,groups = { "regression" }, priority = 46)
	public void verifyCustomizedCodingFormUsingCheckGroupInDocView() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52080");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using check groupthe customized coding form using radio group");
	    String cfName = "CFTagCheckGrp"+Utility.dynamicNameAppender();
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingFormWithCheckGroup(cfName);
	 	baseClass.stepInfo("Coding form created with Check Group in required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyErrorMsgforCheckGroup();

		docViewPage.openChildWindowInCheckGroup();

		baseClass.passedStep("Verified on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using check group");
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52087
	 * @Description : To  Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tags and Check group combined with Check Item in context of security group.
	 */
	//@Test(enabled=false,groups = { "regression" }, priority = 47)
	public void verifyCustomizedCodingFormUsingTagAndCheckGroupInDocView() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52087");
	    baseClass.stepInfo(" Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tags and Check group combined with Check Item in context of security group");
	    String cfName = "CFTagRadioGrp"+Utility.dynamicNameAppender();
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingForm2TagsWithCheckGroup(cfName);
	 	baseClass.stepInfo("Coding form created 2 tags with Check Group in hidden and required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyParentWindowCursor();
		docViewPage.verifyChildWindowCursor();

		baseClass.passedStep(" Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tags and Check group combined with Check Item in context of security group");
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52079
	 * @Description : To Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form for comment element in context of security group.
	 */
	//@Test(enabled = false,groups = { "regression" }, priority = 48)
	public void verifyCustomizedCodingFormUsingRadioGroupInDocView() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52079");
	    baseClass.stepInfo("Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form for comment element in context of security group"+
	                                "the customized coding form using radio group");
	    String cfName = "CFTagRadioGrp"+Utility.dynamicNameAppender();
	    String tagName="cftag"+Utility.dynamicNameAppender();
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
	 	//create tag
	 	tagsAndFoldersPage.CreateTag(tagName, Input.securityGroup);
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingFormWithRedioGroup(cfName,tagName);
	 	baseClass.stepInfo("Coding form created with Radio Group in required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyErrorMsgforRadioGroup();

		docViewPage.openChildWindowInRadioGroup();

		baseClass.passedStep("Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form for comment element in context of security group.");
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	
	
	
	/**
	 * @Author : Baskar date: NA Modified date:10/12/2021 Modified by: Baskar 
	 * @Description :Verify validation of coding form when coding form is created 
	 *               with metadata field as DateOnly on click of 'Save'
	 */
	
	//@Test(enabled = false, groups = { "regression" }, priority = 49)
	public void nonDateFormatForDateUsingSave() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51580");
	    baseClass.stepInfo("Verify validation of coding form when coding form is "
	    		+ "created with metadata field as DateOnly on click of 'Save'");
	    String codingForms = "CFDate"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();

		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATE DataType
		projectPage.addCustomFieldDataType(date, "Date");
		baseClass.stepInfo("Custom meta data field created with DATE datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(date);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnUsingParameter(codingForms, date,Input.optional);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingForms);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel for non-date format
		docViewPage.nonDateFormatValidationUsingSave(date);

		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date:12/10/2021 Modified date:12/10/2021 Modified by: Baskar 
	 * @Description :Verify validation of coding form when coding form is created with 
	 *               metadata field as DateTime on click of 'Save' outside of an assignment
	 */
	
	//@Test(enabled = false, groups = { "regression" }, priority = 50)
	public void validationUsingAlphaAndNonDateFormat() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51607");
	    baseClass.stepInfo("Verify validation of coding form when coding form is created with "
	    		+ "metadata field as DateTime on click of 'Save' outside of an assignment");
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();

		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATE DataType
		projectPage.addCustomFieldDataType(date, "Date");
		projectPage.addCustomFieldDataType(dateTime, "DATETIME");
		projectPage.addCustomFieldProjectDataType(intData, "INT");
		baseClass.stepInfo("Custom meta data field created with datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(date);
		securityGroupPage.addProjectFieldtoSG(dateTime);
		securityGroupPage.addProjectFieldtoSG(intData);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.creatingCodingThreeDataType(assgnCoding, date,dateTime,intData);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// assign coding form to SG
		codingForm.assignCodingFormToSG(assgnCoding);
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocViews();
		
		// verify the coding form panel for non-date format and alpha character
		docViewPage.nonDateFormatAndAlphaValidationUsingSave(date,dateTime,intData);

		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date:10/12/21 Modified date: NA Modified by: Baskar 
	 * @Description : Verify on click of 'Save and Next' button coding form should be 
	 *                validated as per the default selected action for the coding form 
	 *                in context of security group
	 */
	
	//@Test(enabled = false, groups = { "regression" }, priority = 51)
	public void  validateForRequiredTag() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52074");
		baseClass.stepInfo("Verify on click of 'Save and Next' button coding form "
				+ "should be validated as per the default selected action "
				+ "for the coding form in context of security group");
		String cfName = "CF"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.commentRequired(cfName);
		baseClass.stepInfo("Coding form created with comments required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.validateErrorMsgRequiredComment();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52089
	 * @Description : To  Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tag objects along with "Selected" condition in context of security group.
	 */
	//@Test(enabled=false,groups = { "regression" }, priority = 52)
	public void verifyCodingFormWithNotSelectableAndOption() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52089");
	    baseClass.stepInfo(" Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tag objects along with \"Selected\" condition in context of security group");
	    String cfName = "CFTag"+Utility.dynamicNameAppender();
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingForm2TagsWithNotSelectableAndOption(cfName);
	 	baseClass.stepInfo("Coding form created 2 tags with not Selectable And Option");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyParentWindowCursor();
		docViewPage.verifyChildWindowCursor();

		baseClass.passedStep(" Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tag objects along with \"Selected\" condition in context of security group");
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52090
	 * @Description : To   Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Comments objects along with "Not Selected" condition in context of security group.
	 */
	//@Test(enabled = false,groups = { "regression" }, priority = 53)
	public void verifyCodingFormCommentWithOptionalAndRequired() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52090");
	    baseClass.stepInfo(" Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Comments objects along with \"Not Selected\" condition in context of security group");
	    String cfName = "CFComment"+Utility.dynamicNameAppender();
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingForm2CommentWithOptionalAndRequired(cfName);
	 	baseClass.stepInfo("Coding form created 2 tags with not Selectable And Option");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyErrorMsgInDocView();
		docViewPage.openChildWindowInCheckGroup();

		baseClass.passedStep(" Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form using Comments objects along with \"Not Selected\" condition in context of security group");
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	
//////Changes done as per stabilization team below one//////
	////// For my Reference where//////@Start priority 47
	
	/**
	 * @Author : Baskar date:13/12/21 Modified date: NA Modified by: Baskar 
	 * @Description : To verify that if coding form is not associated to the 
	 *                security group, document should not be saved.
	 */
	
	//@Test(enabled = false, groups = { "regression" }, priority = 54)
	public void validateCodingFormWhenNotAssgn() throws InterruptedException {
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-52989");
		baseClass.stepInfo("To verify that if coding form is not associated "
				+ "to the security group, document should not be saved.");
		String cfName = "CF"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.commentRequired(cfName);
		baseClass.stepInfo("Coding form created with comments required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		codingForm.deleteCodingForm(cfName,cfName);
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.noDefaultCodingForm();
		softAssertion.assertAll();
		
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52091
	 * @Description : To  Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tags objects along with Selected and "Not Selected" condition in context of security group.
	 */
	//@Test(enabled = false,groups = { "regression" }, priority = 55)
	public void verifyCodingFom2RadioGrpAddLogicWithSelectedNotSelected() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52091");
	    baseClass.stepInfo(" Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tags objects along with Selected and \"Not Selected\" condition in context of security group");
	    String cfName = "CFComment"+Utility.dynamicNameAppender();
	    String tagName="cftag"+Utility.dynamicNameAppender();
	    String tagName1="cftag"+Utility.dynamicNameAppender();
	    
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	//add tag
	 	tagsAndFoldersPage.CreateTag(tagName, Input.securityGroup);
	 	tagsAndFoldersPage.CreateTag(tagName1, Input.securityGroup);
	 	
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingForm2RadioGrpAddLogicWithSelectedNotSelected(cfName,tagName,tagName1);
	 	baseClass.stepInfo("Coding form created 2 Radio group with selected and not Selected");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.getRadioBoxforRdoGrp_2().waitAndClick(10);
		docViewPage.verifyErrorMsgInDocView();
		docViewPage.openChildWindowInCheckGroup();

		baseClass.passedStep(" Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tags objects along with Selected and \"Not Selected\" condition in context of security group");
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52092
	 * @Description : To Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Metadata objects along with "Not Selected" condition in context of security group.
	 */
	//@Test(enabled = false,groups = { "regression" }, priority = 56)
	public void verifyCodingFom2MetaDataAddLogicNotSelected() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52092");
	    baseClass.stepInfo("Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Metadata objects along with \"Not Selected\" condition in context of security group");
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String meta = "demo"+Utility.dynamicNameAppender();
		String meta1 = "demo"+Utility.dynamicNameAppender();
		String intData= "INT";
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		projectPage.clickingManageButton();
		projectPage.addMetaDataFieldUsingIntergerType(meta, intData, Input.docBasic, Input.tinyInt);
		projectPage.addMetaDataFieldUsingIntergerType(meta1, intData, Input.docBasic, Input.tinyInt);
		baseClass.stepInfo("Custom meta data field created with integer");
		
		securityGroupPage.addProjectFieldtoSG(meta);
		securityGroupPage.addProjectFieldtoSG(meta1);
		baseClass.stepInfo("Custom metadata integer field assign to security group");
		
		loginPage.logout();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu2userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingForm2MetaDataAddLogicNotSelected(cfName,meta,meta1);
	 	baseClass.stepInfo("Coding form created 2 check group add logic is not Selected");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyErrorMsgInDocView();
		docViewPage.openChildWindowInCheckGroup();

		baseClass.passedStep("Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form using Metadata objects along with \"Not Selected\" condition in context of security group");
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51195
	 * @Description : To Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for comment element.
	 */
	//@Test(enabled = false,groups = { "regression" }, priority = 57)
	public void verifySaveCompleteValidateCustomizedCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51195");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for comment element");
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "Assignment"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	codingForm.firstCheckBox("tag");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.getCodingForm_StaticText(0).SendKeys("Field Label");
	 	codingForm.getCodingForm_ErrorMsg(0).SendKeys(Input.errorMsg);
	 	codingForm.getCodingForm_HelpMsg(0).SendKeys(Input.helpText);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Comment Coding form created with Field label,error message,help text ");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
				

		// Edit coding Form and complete Action
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep("Verified on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for comment element");

		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51203
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Tag object along with Check Item.
	 */
	//@Test(enabled = false,groups = { "regression" }, priority = 58)
	public void verifySaveCompleteValidateTagCheckItemCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51203");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Tag object along with Check Item");
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "Assignment"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	codingForm.firstCheckBox("tag");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectRadioOrCheckGroup("check item");
	 	codingForm.selectDefaultActions(1, Input.optional);
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Tag Coding form created with tag along with check item ");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
				

		// Edit coding Form and complete Action
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep("Verified on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Tag object along with Check Item");

		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51204
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Comment object on coding form screen.
	 */
	//@Test(enabled = false,groups = { "regression" }, priority = 59)
	public void verifySaveCompleteValidateCommentNotSelectableCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51204");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Comment object on coding form screen");
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "Assignment"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	codingForm.firstCheckBox("comment");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectDefaultActions(0, Input.notSelectable);
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Metadata Coding form created with Make it Optional");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		boolean flag=docViewPage.getAddComment1().Enabled();
		if(!flag) {
			System.out.println("document comment not selecable");
			baseClass.passedStep("document comment not selecable");
		}else {
			System.out.println("document comment selecable");
			baseClass.failedStep("document comment selecable");
		}
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		boolean flag1=docViewPage.getAddComment1().Enabled();
		if(!flag1) {
			System.out.println("document comment not selecable");
			baseClass.passedStep("document comment not selecable");
		}else {
			System.out.println("document comment selecable");
			baseClass.failedStep("document comment selecable");
		}
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Comment object on coding form screen");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51205
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for Editable Metadata objects
	 */
	//@Test(enabled = false,groups = { "regression" }, priority = 60)
	public void verifySaveCompleteValidateMetaCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51205");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for Editable Metadata objects");
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "Assignment"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	codingForm.firstCheckBox("metadata");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectDefaultActions(0, Input.optional);
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Comment Coding form created with Display but Not Selectable");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();
		docViewPage.getAttachCountTextBox().waitAndClick(5);
		docViewPage.getAttachCountTextBox().SendKeys("Edit document comment And Save");
		baseClass.stepInfo("text box is editble");
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getAttachCountTextBox().waitAndClick(5);
		docViewPage.getAttachCountTextBox().SendKeys("Edit document comment And Save");
		baseClass.stepInfo("text box is editble");
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for Editable Metadata objects");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51206
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for Tag/Comments/Metadata objects.
	 */
	//@Test(enabled = false,groups = { "regression" }, priority = 61)
	public void verifySaveCompleteValidateTagCommentMetaCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51206");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for Tag/Comments/Metadata objects");
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String tag="cfTag"+Utility.dynamicNameAppender();
	    String assignName = "Assignment"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create tag
	 	tagsAndFoldersPage.CreateTag(tag,"Default Security Group");
	 	
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	baseClass.waitForElement(codingForm.getCodingForm_Tag(tag));
	 	codingForm.getCodingForm_Tag(tag).waitAndClick(10);
	 	codingForm.firstCheckBox(Input.comments);
	 	codingForm.addTwoCheckBox(Input.metaData);
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectRadioOrCheckGroup("check item");
	 	codingForm.selectDefaultActions(4, Input.required);
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.getCodingForm_HelpMsg(0).SendKeys(Input.helpText);
	 	codingForm.getCodingForm_ErrorMsg(4).SendKeys(Input.errorMsg);
	 	codingForm.getCodingForm_HelpMsg(4).SendKeys(Input.helpText);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Coding form added with tag, comment,two metadata,check item, make it required created");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();
		//docViewPage.DocViewCodingForm4thCheckBox().waitAndClick(5);
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		//docViewPage.DocViewCodingForm4thCheckBox().waitAndClick(5);
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.passedStep("Verified on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for Tag/Comments/Metadata objects");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51207
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized Tags and Radio Group combined along with Radio Item on coding form screen.
	 */
	//@Test(enabled = false,groups = { "regression" }, priority = 62)
	public void verifySaveCompleteValidate2TagRadioItemCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51207");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized Tags and Radio Group combined along with Radio Item on coding form screen");
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "Assignment"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
	 	
	 	
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	codingForm.addTwoCheckBox(Input.tag);
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectRadioOrCheckGroup(0,"radio item");
	 	codingForm.selectRadioOrCheckGroup(1,"radio item");
	 	codingForm.selectDefaultActions(2, Input.optional);
	 	codingForm.selectDefaultActions(3, Input.notSelectable);
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Coding form added with tag,radio item, make it optional and not selectable created");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized Tags and Radio Group combined along with Radio Item on coding form screen");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51214
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Metadata objects along with "Not Selected" condition.
	 */
	//@Test(enabled = false,groups = { "regression" }, priority = 63)
	public void verifySaveCompleteValidatemetaDataNotSelectedCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51214");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Metadata objects along with \"Not Selected\" condition");
	   
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "CFAssignment"+Utility.dynamicNameAppender();
	    String meta = "demo"+Utility.dynamicNameAppender();
		String meta1 = "demo"+Utility.dynamicNameAppender();
		String intData= "INT";
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		projectPage.clickingManageButton();
		projectPage.addMetaDataFieldUsingIntergerType(meta, intData, Input.docBasic, Input.tinyInt);
		projectPage.addMetaDataFieldUsingIntergerType(meta1, intData, Input.docBasic, Input.tinyInt);
		baseClass.stepInfo("Custom meta data field created with integer");
		
		securityGroupPage.addProjectFieldtoSG(meta);
		securityGroupPage.addProjectFieldtoSG(meta1);
		baseClass.stepInfo("Custom metadata integer field assign to security group");
		
		loginPage.logout();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu2userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingForm2MetaDataAddLogicNotSelected(cfName,meta,meta1);
	 	baseClass.stepInfo("Coding form created 2 metaData add logic is not Selected");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.passedStep("Verified on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Metadata objects along with \"Not Selected\" condition");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51215
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using all objects along with all condition and Radio Item
	 */
	//@Test(enabled = false,groups = { "regression" }, priority = 64)
	public void verifySaveCompleteValidateAllObjectAlongWithRadioItemdCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51215");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using all objects along with all condition and Radio Item");
	   
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "CFAssignment"+Utility.dynamicNameAppender();
	    String tagName = "tag"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu2userName + "'");
	 	
	 	 // create tag
		tagsAndFoldersPage.CreateTag(tagName,"Default Security Group");
		
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	baseClass.waitForElement(codingForm.getCodingForm_Tag(tagName));
	 	codingForm.getCodingForm_Tag(tagName).waitAndClick(10);
	 	codingForm.firstCheckBox(Input.comments);
	 	codingForm.firstCheckBox(Input.metaData);
	 	codingForm.specialObjectsBox("staticText");
	 	codingForm.specialObjectsBox("radio");
	 	codingForm.specialObjectsBox("check");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.getCF_TagTypes(0).selectFromDropdown().selectByVisibleText("Radio Item");
	 	driver.waitForPageToBeReady();
	 	codingForm.getCF_RadioGroup(0).selectFromDropdown().selectByIndex(1);
	 	codingForm.selectDefaultActions(1, Input.hidden);
	 	codingForm.selectDefaultActions(2, Input.notSelectable);
	 	codingForm.selectDefaultActions(4, Input.optional);
	 	codingForm.getCodingForm_ErrorMsg(4).SendKeys(Input.errorMsg);
	 	codingForm.getCodingForm_HelpMsg(4).SendKeys(Input.helpText);
	 	codingForm.selectDefaultActions(5, Input.required);
	 	driver.waitForPageToBeReady();
	 	driver.scrollPageToTop();
	 	codingForm.addLogicOptionWithIndex(1, 1, Input.select, Input.thisHidden);
	 	codingForm.addLogicOptionWithIndex(2, 1, Input.notSelect, Input.thisReadOnly);
	 	codingForm.addLogicOptionWithIndexWithoutIncreace(4, 1, Input.select, Input.thisOptional);
	 	codingForm.addLogicOptionWithIndexWithoutIncreace(5, 1, Input.notSelect, Input.thisRequired);
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Coding form created all object along with radio item");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.passedStep("Verified on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using all objects along with all condition and Radio Item");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51187
	 * @Description : Verify on click of 'Save' button coding form should be validated as per the default selected action for the coding form outside of an assignment
	 */
	//@Test(enabled = false,groups = { "regression" }, priority = 65)
	public void verifyDefaultCodingforminDocView() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51187");
	    baseClass.stepInfo("Verify on click of 'Save' button coding form should be validated as per the default selected action for the coding form outside of an assignment");
	    String cfName = "CF"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	codingForm.firstCheckBox("tag");
	 	codingForm.firstCheckBox("comment");
	 	codingForm.firstCheckBox("metadata");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.selectDefaultActions(1, Input.required);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("default coding form should be created");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocViews();
		baseClass.stepInfo("Search with text input for docs completed");
		
		//docView
		docViewPage.getAddComment1().Clear();
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.CloseSuccessMsgpopup();
		docViewPage.getAddComment1().SendKeys("Document commemnt added");
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocViews();
		baseClass.stepInfo("Search with text input for docs completed");		

		// Edit coding Form and complete Action
		docViewPage.getAddComment1().Clear();
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.CloseSuccessMsgpopup();
		docViewPage.getAddComment1().SendKeys("Document commemnt added");
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		baseClass.passedStep("Verified on click of 'Save' button coding form should be validated as per the default selected action for the coding form outside of an assignment");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51188
	 * @Description : Verify on click of 'Complete' button coding form should be validated as per the default selected action for the coding form
	 */
	//@Test(enabled = false,groups = { "regression" }, priority = 66)
	public void verifyDefaultCodingformAssignmentDocView() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51188");
	    baseClass.stepInfo("Verify on click of 'Complete' button coding form should be validated as per the default selected action for the coding form");
	    String cfName = "CF"+Utility.dynamicNameAppender();
		String assignName ="Assignment"+Utility.dynamicNameAppender();
	    
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	codingForm.firstCheckBox("tag");
	 	codingForm.firstCheckBox("comment");
	 	codingForm.firstCheckBox("metadata");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.selectDefaultActions(1, Input.required);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("default coding form should be created");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		driver.waitForPageToBeReady();
		
		//assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getAddComment1().Clear();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();		
		baseClass.CloseSuccessMsgpopup();
		docViewPage.getAddComment1().SendKeys("Document commemnt added");
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document completed successfully");
		
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		//assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getAddComment1().Clear();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();		
		baseClass.CloseSuccessMsgpopup();
		docViewPage.getAddComment1().SendKeys("Document commemnt added");
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document completed successfully");
		
		
		baseClass.passedStep("Verified on click of 'Complete' button coding form should be validated as per the default selected action for the coding form");
		
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: NA Modified date:11/01/2022 Modified by: Baskar 
	 * @Description :Verify that custom metadata field value should be retained 
	 *               on doc view when created with TIME datatype in context of security group
	 */
	
	//@Test(enabled = true, groups = { "regression" }, priority = 67)
	public void verifyThatCustomMetaDataFieldValueWithTIMEDatatype() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52176");
	    baseClass.stepInfo("Verify that custom metadata field value should be retained on "
	    		+ "doc view when created with TIME datatype in context of security group");
	    securityGroupPage=new SecurityGroupsPage(driver);
	    codingForm=new CodingForm(driver);
	    sessionSearch=new SessionSearch(driver);
	    docViewPage=new DocViewPage(driver);
	    
	    String codingfrom = "CFDateTime"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATETIME DataType
		projectPage.addCustomFieldDataType(time, "Time");
		baseClass.stepInfo("Custom meta data field created with TIME datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(time);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingfrom, time);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(time);

		// logout
		loginPage.logout();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
//		Doing cleanup activity done in prerequities
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();
		
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51209
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Tag/Comments/Metadata objects along with Check/Radio Group and Check Item
	 */
	//@Test(enabled = true ,groups = { "regression" }, priority = 68)
	public void verifySaveCompleteValidateAllObjectAlongWithCheckItemdCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51209");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Tag/Comments/Metadata objects along with Check/Radio Group and Check Item form should be validated as per the customized coding form using all objects along with all condition and Radio Item");
	   
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "CFAssignment"+Utility.dynamicNameAppender();
	    String tagName = "tag"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
	 	
	 	 // create tag
		tagsAndFoldersPage.CreateTag(tagName,"Default Security Group");
		
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	baseClass.waitForElement(codingForm.getCodingForm_Tag(tagName));
	 	codingForm.getCodingForm_Tag(tagName).waitAndClick(10);
	 	codingForm.firstCheckBox(Input.comments);
	 	codingForm.firstCheckBox(Input.metaData);
	 	codingForm.specialObjectsBox("staticText");
	 	codingForm.specialObjectsBox("radio");
	 	codingForm.specialObjectsBox("check");
	 	driver.scrollPageToTop();
	 	codingForm.addcodingFormAddButton();
	 	codingForm.getCF_TagTypes(0).selectFromDropdown().selectByVisibleText("Check Item");
	 	driver.waitForPageToBeReady();
	 	codingForm.getCF_CheckGroup(0).selectFromDropdown().selectByIndex(1);
	 	codingForm.selectDefaultActions(1, Input.hidden);
	 	codingForm.selectDefaultActions(2, Input.notSelectable);
	 	codingForm.selectDefaultActions(4, Input.optional);
	 	codingForm.getCodingForm_ErrorMsg(4).SendKeys(Input.errorMsg);
	 	codingForm.getCodingForm_HelpMsg(4).SendKeys(Input.helpText);
	 	codingForm.selectDefaultActions(5, Input.required);
	 	codingForm.getCodingForm_ErrorMsg(5).SendKeys(Input.errorMsg);
	 	codingForm.getCodingForm_HelpMsg(5).SendKeys(Input.helpText);
	 	driver.waitForPageToBeReady();
	 	driver.scrollPageToTop();
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Coding form created all object along with check item");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.passedStep("Verified on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Tag/Comments/Metadata objects along with Check/Radio Group and Check Item");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51210
	 * @Description : Verify validation of coding form on click of 'Save'/'Complete button using Tag/Comments/Metadata objects along with Check/Radio Group and Radio Item
	 */
	//@Test(enabled = true ,groups = { "regression" }, priority = 69)
	public void verifySaveCompleteValidateAllObjectAlongWithCheckRadioItemdCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51210");
	    baseClass.stepInfo("Verify validation of coding form on click of 'Save'/'Complete button using Tag/Comments/Metadata objects along with Check/Radio Group and Radio Item");
	   
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "CFAssignment"+Utility.dynamicNameAppender();
	    String tagName = "tag"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu2userName + "'");
	 	
	 	 // create tag
		tagsAndFoldersPage.CreateTag(tagName,"Default Security Group");
		
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	baseClass.waitForElement(codingForm.getCodingForm_Tag(tagName));
	 	codingForm.getCodingForm_Tag(tagName).waitAndClick(10);
	 	codingForm.firstCheckBox(Input.comments);
	 	codingForm.firstCheckBox(Input.metaData);
	 	codingForm.specialObjectsBox("staticText");
	 	codingForm.specialObjectsBox("radio");
	 	codingForm.specialObjectsBox("check");
	 	driver.scrollPageToTop();
	 	codingForm.addcodingFormAddButton();
	 	codingForm.getCF_TagTypes(0).selectFromDropdown().selectByVisibleText("Check Item");
	 	driver.waitForPageToBeReady();
	 	codingForm.getCF_CheckGroup(0).selectFromDropdown().selectByIndex(1);
	 	codingForm.selectDefaultActions(1, Input.hidden);
	 	codingForm.selectDefaultActions(2, Input.required);
	 	codingForm.getCodingForm_ErrorMsg(2).SendKeys(Input.errorMsg);
	 	codingForm.getCodingForm_HelpMsg(2).SendKeys(Input.helpText);
	 	codingForm.selectDefaultActions(4, Input.hidden);
	 	codingForm.selectDefaultActions(5, Input.notSelectable);
	 	driver.waitForPageToBeReady();
	 	driver.scrollPageToTop();
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Coding form created all object along with check item");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.passedStep("Verified validation of coding form on click of 'Save'/'Complete button using Tag/Comments/Metadata objects along with Check/Radio Group and Radio Item");
		
		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date: NA Modified date:11/02/2021 Modified by: Baskar 
	 * @Description :Verify validation of coding form when coding form is created 
	 *               with metadata field as DateTime on click of 'Complete'
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void validationOfNonDateFormatINAssign() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51582");
	    baseClass.stepInfo("Verify validation of coding form when coding form is "
	    		+ "created with metadata field as DateTime on click of 'Complete'");
	    
	    String codingfrom = "CFDateTime"+Utility.dynamicNameAppender();
	    String assgnName = "Assign"+Utility.dynamicNameAppender();
	    String assgnColour = "ColourAssign"+Utility.dynamicNameAppender();
	    String dateTime = "DateTime" + Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DateTime DataType
		projectPage.addCustomFieldDataType(dateTime, "DATETIME");
		baseClass.stepInfo("Custom meta data field created with DateTime datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(dateTime);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnUsingParameter(codingfrom, dateTime,"Make it Optional");
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnName, codingfrom);
		assignmentPage.toggleSaveButton();
		assignmentPage.add2ReviewerAndDistribute();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.nonDateFormatValidationUsingSaveAndComplete(dateTime,assgnColour,Input.stampSelection);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnName);
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();

	}

		

	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			try { // if any tc failed and dint logout!
				loginPage.logoutWithoutAssert();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		UtilityLog.info("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			loginPage.quitBrowser();
		} finally {
			//loginPage.clearBrowserCache();
		}
	}

}
