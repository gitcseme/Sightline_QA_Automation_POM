package sightline.codingForms;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
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

public class CodingForm_Regrression_22 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;
	DocViewPage docview;
	SoftAssert soft;
	CodingForm cf;

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
		base = new BaseClass(driver);
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);

	}
	
	/**
	 * @Author : Aathith
	 * @Description Verify that Preview displays correctly and properly for 
	 * Tag/Comments/Metadata objects along with Check/Radio Group and Check Item for new coding form
	 */
	@Test(description = "RPMXCON-54075",enabled = true, groups = { "regression" })
	public void verifyPreviewAlongWithTagCommentMetadata() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54075");
	    base.stepInfo("Verify that Preview displays correctly and properly for "
	    		+ "Tag/Comments/Metadata objects along with Check/Radio Group and Check Item for new coding form");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		cf.addCodingFormName(cfName);
		
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
		 cf.selectDefaultActions(4, Input.optional);
		 cf.selectDefaultActions(5, Input.optional);
		 
		 //save codingform
		 cf.saveCodingForm();
		 
		 //edit codingform
		 cf.editCodingForm(cfName);
		 
		 //validate
		 cf.clickPreviewButon();
		 soft.assertFalse(cf.getCodingForm_PreviewText(0).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(1).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(2).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(3).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(5).isElementAvailable(3));
		 base.passedStep("Preview displayed correctly and properly");
		 
	    soft.assertAll();
	    base.passedStep("Verified that Preview displays correctly and properly for "
	    		+ "Tag/Comments/Metadata objects along with Check/Radio Group and Check Item for new coding form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description Verify that Preview displays correctly and properly for 
	 * Tag/Comments/Metadata objects along with Check/Radio Group and Radio Item for New coding form
	 */
	@Test(description = "RPMXCON-54076",enabled = true, groups = { "regression" })
	public void verifyPreviewCrcltyForAllItemActionType() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54076");
	    base.stepInfo("Verify that Preview displays correctly and properly for "
	    		+ "Tag/Comments/Metadata objects along with Check/Radio Group and Radio Item for New coding form");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		cf.addCodingFormName(cfName);
		
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
		 cf.selectDefaultActions(1, Input.required);
		 cf.selectDefaultActions(2, Input.optional);
		 cf.enterErrorAndHelpMsg(2, "Yes", Input.errorMsg, Input.helpText);
		 cf.selectDefaultActions(4, Input.required);
		 cf.selectDefaultActions(5, Input.required);
		 
		 //validate cf
		 cf.validateCodingForm();
		 
		 //validate preview
		 cf.clickPreviewButon();
		 soft.assertFalse(cf.getCodingForm_PreviewText(0).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(1).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(2).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(3).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(5).isElementAvailable(3));
		 base.passedStep("Preview displayed correctly and properly");
		 
	    soft.assertAll();
	    base.passedStep("Verified that Preview displays correctly and properly for "
	    		+ "Tag/Comments/Metadata objects along with Check/Radio Group and Radio Item for New coding form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that Preview displays correctly and properly for Tags objects 
	 * along with Selected and "Not Selected" condition for New coding form
	 */
	@Test(description = "RPMXCON-54079",enabled = true, groups = { "regression" })
	public void validatePriviewCorrectlySelectNotSelected() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54079");
	    base.stepInfo("Verify that Preview displays correctly and properly for "
	    		+ "Tags objects along with Selected and \"Not Selected\" condition for New coding form");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.tag);
		 cf.addcodingFormAddButton();
		 cf.addTwoCheckBox(Input.tag);
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.addcodingFormAddButton();
		 cf.specialObjectsBox(Input.radioGroup);
		 cf.addcodingFormAddButton();
		 
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

		 //validate cf
		 cf.validateCodingForm();
		 
		 //validate
		 cf.clickPreviewButon();
		 base.moveWaitAndClick(cf.getPreview1stRadioBtn(), 15);
		 soft.assertTrue(cf.getCodingForm_PreviewText(2).isElementAvailable(3));
		 soft.assertTrue(cf.getCodingForm_PreviewText(3).isElementAvailable(3));
		 soft.assertTrue(cf.getPreviewRadioBtn().isElementAvailable(3));
		 soft.assertTrue(cf.getPreview1stRadioBtn().Selected());
		 soft.assertFalse(cf.getPreview2ndRadioBtn().Selected());
		 base.passedStep("Preview Should get displayed correctly");
		 
	    soft.assertAll();
	    base.passedStep("Verified that Preview displays correctly and properly for "
	    		+ "Tags objects along with Selected and \"Not Selected\" condition for New coding form");
	    loginPage.logout();
	}	
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that Preview displays correctly and properly for 
	 * Metadata objects along with "Not Selected" condition for New coding form
	 */
	@Test(description = "RPMXCON-54080",enabled = true, groups = { "regression" })
	public void validatePriviewCorrectlyMetaDataSelectNotSelected() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54080");
	    base.stepInfo("Verify that Preview displays correctly and properly for"
	    		+ " Metadata objects along with \"Not Selected\" condition for New coding form");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.addCodingFormName(cfName);
		
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

		 //validate cf
		 cf.validateCodingForm();
		 
		 //validate
		 cf.clickPreviewButon();
		 soft.assertFalse(cf.getCodingForm_PreviewText(0).isDisplayed());
		 soft.assertTrue(cf.getCodingForm_PreviewText(1).isDisplayed());
		 soft.assertFalse(cf.getCodingForm_PreviewText(1).Selected());
		 base.passedStep("Preview displayed correctly and properly");
		 
	    soft.assertAll();
	    base.passedStep("Verified that Preview displays correctly and properly for "
	    		+ "Metadata objects along with \"Not Selected\" condition for New coding form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that Preview displays correctly and properly for all 
	 * objects along with all condition and Radio Item for New coding form
	 */
	@Test(description = "RPMXCON-54081",enabled = true, groups = { "regression" })
	public void varifyPreviewCrctlyAlongWithCondition() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54081");
	    base.stepInfo("Verify that Preview displays correctly and properly for all "
	    		+ "objects along with all condition and Radio Item for New coding form");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		cf.addCodingFormName(cfName);
		
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
		 
		 //validate cf
		 cf.validateCodingForm();
		 
		 //validate preview
		 cf.clickPreviewButon();
		 soft.assertTrue(cf.getPreviewComment().isElementAvailable(3));
		 soft.assertTrue(cf.getPreviewMetaData().isElementAvailable(3));
		 soft.assertTrue(base.text("Static Text").isElementAvailable(3));
		 base.passedStep("Preview displayed correctly and properly");
		 
	    soft.assertAll();
	    base.passedStep("Verified that Preview displays correctly and properly for all objects "
	    		+ "along with all condition and Radio Item for New coding form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that Preview displays correctly and properly for all objects 
	 * along with all condition and Check Item for new coding form
	 */
	@Test(description = "RPMXCON-54082",enabled = true, groups = { "regression" })
	public void verifyPreviewCrctlyAllCondiotionAlongWithCheckItem() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54082");
	    base.stepInfo("Verify that Preview displays correctly and properly for all objects "
	    		+ "along with all condition and Check Item for new coding form");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		cf.addCodingFormName(cfName);
		
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
		 
		 //validate cf
		 cf.validateCodingForm();
		 
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
		 base.passedStep("Preview displayed correctly and properly");
		 
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    soft.assertAll();
	    base.passedStep("Verified that Preview displays correctly and properly for "
	    		+ "all objects along with all condition and Check Item for new coding form");
	    loginPage.logout();
	}
	/**
	 * @throws ParseException 
	 * @Author : Iyappan.Kasinathan 
	 * @Description :Verify validation from preview of coding form when coding form is created
	 *                with the editable metadata field of data type DateOnly
	 */
	
	@Test(description = "RPMXCON-54413",enabled = true, groups = { "regression" })
	public void validateNonDateFormatInPreviewCf() throws InterruptedException, ParseException {
		base.stepInfo("Test case Id: RPMXCON-54413");
		base.stepInfo("Verify validation from preview of coding form when coding form is created with the editable metadata field of data type DateOnly");
	    String codingform = "CFDate"+Utility.dynamicNameAppender();
	    String date = "Date" + Utility.dynamicNameAppender();
	    System.out.println(date);
	    CodingForm codingForm = new CodingForm(driver);
	    ProjectPage projectPage = new ProjectPage(driver);
	    SecurityGroupsPage securityGroupPage = new SecurityGroupsPage(driver);
	    SoftAssert softAssertion = new SoftAssert();
	    DocViewPage docviewPg = new DocViewPage(driver);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat ldf = new SimpleDateFormat("dd/MM/yyyy");
		Date d1 = ldf.parse(sdf.format(new Date()));
		String d2 = ldf.format(d1);
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATE DataType
		projectPage.addCustomFieldDataType(date, "Date");
		base.stepInfo("Custom meta data field created with DATE datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(date);
		base.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		base.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addNewCodingFormButton();
		base.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.CreateCodingFormWithParameter(codingform,null,null,date,"metadata");
		codingForm.addcodingFormAddButton();
		codingForm.saveCodingForm();
		base.stepInfo("Coding form saved successfully");
		base.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		base.waitTillElemetToBeClickable(docviewPg.getDateFormat());
		docviewPg.getDateFormat().SendKeys(d2);
		Actions action = new Actions(driver.getWebDriver());
		action.sendKeys(Keys.TAB).build().perform();
		base.waitTillElemetToBeClickable(codingForm.getTestUrCodeClick());
		codingForm.getTestUrCodeClick().waitAndClick(5);
		String errorMessage = codingForm.geErrMsgInPreviewBox().getText();
		softAssertion.assertEquals(errorMessage,"Invalid DateTime");
		softAssertion.assertAll();
		base.passedStep("When passing non formatted date, getting the error messsage as "+errorMessage+" successfully as expected");
		codingForm.deleteCodingForm(codingform, codingform);
		loginPage.logout();
	}
	/**
	 * @throws ParseException 
	 * @Author : Iyappan.Kasinathan 
	 * @Description :Verify validation from preview of coding form when coding form is created with the editable metadata field of data type DateTime
	 */
	
	@Test(description = "RPMXCON-54412",enabled = true, groups = { "regression" })
	public void validateNonDateTimeFormatInPreviewCf() throws InterruptedException, ParseException {
		base.stepInfo("Test case Id: RPMXCON-54412");
		base.stepInfo("Verify validation from preview of coding form when coding form is created with the editable metadata field of data type DateTime");
	    String codingform = "CFDate"+Utility.dynamicNameAppender();
	    String dateTime = "DateTime" + Utility.dynamicNameAppender();
	    System.out.println(dateTime);
	    CodingForm codingForm = new CodingForm(driver);
	    ProjectPage projectPage = new ProjectPage(driver);
	    SecurityGroupsPage securityGroupPage = new SecurityGroupsPage(driver);
	    SoftAssert softAssertion = new SoftAssert();
	    DocViewPage docviewPg = new DocViewPage(driver);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat ldf = new SimpleDateFormat("dd/MM/yyyy");
		Date d1 = ldf.parse(sdf.format(new Date()));
		String d2 = ldf.format(d1);
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DateTime DataType
		projectPage.addCustomFieldDataType(dateTime, "DATETIME");
		base.stepInfo("Custom meta data field created with DateTime datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(dateTime);
		base.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		base.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addNewCodingFormButton();
		base.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.CreateCodingFormWithParameter(codingform,null,null,dateTime,"metadata");
		codingForm.addcodingFormAddButton();
		codingForm.saveCodingForm();
		base.stepInfo("Coding form saved successfully");
		base.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		base.waitTillElemetToBeClickable(docviewPg.getDateFormat());
		docviewPg.getDateFormat().SendKeys(d2);
		Actions action = new Actions(driver.getWebDriver());
		action.sendKeys(Keys.TAB).build().perform();
		base.waitTillElemetToBeClickable(codingForm.getTestUrCodeClick());
		codingForm.getTestUrCodeClick().waitAndClick(5);
		String errorMessage = codingForm.geErrMsgInPreviewBox().getText();
		softAssertion.assertEquals(errorMessage,"Invalid DateTime");
		softAssertion.assertAll();
		base.passedStep("When passing non formatted datetime, getting the error messsage as "+errorMessage+" successfully as expected");
		codingForm.deleteCodingForm(codingform, codingform);
		loginPage.logout();
	}
	

	/**
	 * @Author :Baskar
	 * @Description : Verify that Preview displays correctly and properly when Tag 
	 *                   and Check group combined along with Check Item for New coding form
	 */
	@Test(description = "RPMXCON-54074", enabled = true, groups = { "regression" })
	public void verifyPreviewAlongWithCheckItem() throws Exception {
		base.stepInfo("Test case Id: RPMXCON-54074");
		base.stepInfo("Verify that Preview displays correctly and properly when Tag and"
				+ " Check group combined along with Check Item for New coding form");
		soft = new SoftAssert();
		cf = new CodingForm(driver);
		String codingform = "cf"+Utility.dynamicNameAppender();
		String tagname = "tag"+Utility.dynamicNameAppender();
		int index = 1;
		String expectedCheckGrp = "checkgroup_"+index+"";
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);
		base.stepInfo("Created the tag sucessfully");
		
		// creating codingform
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");		
		cf.addNewCodingFormButton();
		base.waitForElement(cf.getCodingFormName());
		cf.getCodingFormName().SendKeys(codingform);
		cf.CreateCodingFormWithParameter(codingform,tagname,null,null,"tag");
		cf.addcodingFormAddButton();
		cf.specialObjectsBox(Input.checkGroup);
		cf.addcodingFormAddButton();
		cf.selectTagTypeByIndex("check item",index,0);
		cf.getRGDefaultAction().ScrollTo();
		base.waitForElement(cf.getRGDefaultAction());
		cf.getRGDefaultAction().selectFromDropdown().selectByVisibleText(Input.notSelectable);
		base.stepInfo("check group is associated to the created tag");
		driver.scrollPageToTop();
		
		// validating the success message in validate button
		base.waitForElement(cf.getCFValidateBtn());
		cf.getCFValidateBtn().waitAndClick(5);
		String validationExpected="Coding Form Validation Successful.";
		String validationActual=cf.getCFValidateMsg().getText();
		soft.assertEquals(validationActual, validationExpected);
		base.passedStep("without any error ,successfull validation message displayed");
		base.waitForElement(cf.getCFValidationPopUpOkBtn());
		cf.getCFValidationPopUpOkBtn().waitAndClick(5);
		base.waitForElement(cf.getSaveCFBtn());
		cf.getSaveCFBtn().waitAndClick(5);
		base.waitForElement(cf.getValidationButtonYes());
		cf.getValidationButtonYes().waitAndClick(5);
		base.stepInfo("Coding form saved successfully");
		
		//Edit the existing coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		//verify the radio group associated with tag
		cf.editCodingForm(codingform);
		base.stepInfo("Edited the coding form sucessfully");
		
		// preview validation
		driver.scrollPageToTop();
		base.waitTillElemetToBeClickable(cf.getCF_PreviewButton());
		cf.getCF_PreviewButton().waitAndClick(10);
		boolean notSelect=cf.getDisabledTagsInPreviewBox(tagname).isElementAvailable(2);
		soft.assertTrue(notSelect);
		base.waitForElement(cf.getTagGroupValues(index));
		String actualCheckGroup = cf.getTagGroupValues(index).GetAttribute("systemcontrolname");
		soft.assertEquals(expectedCheckGrp, actualCheckGroup);
		soft.assertAll();
		base.passedStep("The check group associated in tag is successfully reflected after editing the saved coding form");
		base.passedStep("preview display correctly along with checkgroup and tag in notselectable state");
		
		//Deleting the tag and cf
		cf.deleteCodingForm(codingform, codingform);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.deleteAllTags(tagname);

		loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that if comment or metadata objects have no configured help text, 
	 * no mouse over tool tip should appear for that field in either Coding Form Preview or Coding Form panel of DocView
	 */
	@Test(description = "RPMXCON-54267",enabled = true, groups = { "regression" })
	public void verifyNoToolTipMsg() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54267");
	    base.stepInfo("Verify that if comment or metadata objects have no configured help text, no mouse over tool tip "
	    		+ "should appear for that field in either Coding Form Preview or Coding Form panel of DocView");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		docview = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.comments);
		 cf.firstCheckBox(Input.metaData);
		 cf.addcodingFormAddButton();
		 cf.saveCodingForm();
		 
		 //validate in preview
		 cf.clickPreviewButon();
		 soft.assertEquals(base.getToolTipMsg(cf.getCodingForm_PreviewText(0)), "");
		 soft.assertEquals(base.getToolTipMsg(cf.getCodingForm_PreviewText(1)), "");
		 base.passedStep("No tool tip is displayed on mouse over of the objects");
		 
	    cf.AssignCFstoSG(cfName);
	    sessionSearch.basicContentSearch(Input.testData1);
	    sessionSearch.ViewInDocViews();
	    
	    //validate in docview
	    soft.assertEquals(base.getToolTipMsg(cf.getCodingForm_PreviewText(0)), "");
		soft.assertEquals(base.getToolTipMsg(cf.getCodingForm_PreviewText(1)), "");
		base.passedStep("No tool tip is displayed on mouse over of the objects");
		
		//validate in docview child window
		docview.clickGearIconOpenCodingFormChildWindow();
		docview.switchToNewWindow(2);
		soft.assertEquals(base.getToolTipMsg(cf.getCodingForm_PreviewText(0)), "");
		soft.assertEquals(base.getToolTipMsg(cf.getCodingForm_PreviewText(1)), "");
		base.passedStep("No tool tip is displayed on mouse over of the objects");
		driver.close();
		docview.switchToNewWindow(1);
		
		//restore default codingform
		driver.waitForPageToBeReady();
		cf.navigateToCodingFormPage();
		docview.acceptBrowserAlert(true);
		cf.AssigndefaultCFstoSG(Input.codeFormName);
	    
		//delete created cf
		cf.deleteCodingForm(cfName, cfName);
		
	    soft.assertAll();
	    base.passedStep("Verify that if comment or metadata objects have no configured help text, no mouse over tool tip "
	    		+ "should appear for that field in either Coding Form Preview or Coding Form panel of DocView");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that if comment or metadata objects have configured help text, on mouse over tool tip should 
	 * appear for that field in either Coding Form Preview or Coding Form panel of DocView
	 */
	@Test(description = "RPMXCON-54268",enabled = true, groups = { "regression" })
	public void verifyToolTipMsg() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54268");
	    base.stepInfo("Verify that if comment or metadata objects have configured help text, on mouse over tool tip should"
	    		+ " appear for that field in either Coding Form Preview or Coding Form panel of DocView");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		docview = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.comments);
		 cf.firstCheckBox(Input.metaData);
		 cf.addcodingFormAddButton();
		 cf.enterErrorAndHelpMsg(0, "Yes", Input.helpText, Input.errorMsg);
		 cf.enterErrorAndHelpMsg(1, "Yes", Input.helpText, Input.errorMsg);
		 cf.saveCodingForm();
		 
		 //validate in preview
		 cf.clickPreviewButon();
		 soft.assertEquals(base.getToolTipMsg(cf.getCodingForm_PreviewText(0)), Input.helpText);
		 soft.assertEquals(base.getToolTipMsg(cf.getCodingForm_PreviewText(1)), Input.helpText);
		 base.passedStep("Configured tool tip is displayed on mouse over of the objects");
		 
	    cf.AssignCFstoSG(cfName);
	    sessionSearch.basicContentSearch(Input.testData1);
	    sessionSearch.ViewInDocViews();
	    
	    //validate in docview
	    driver.waitForPageToBeReady();
	    soft.assertEquals(base.getToolTipMsg(cf.getCodingForm_PreviewText(0)), Input.helpText);
		soft.assertEquals(base.getToolTipMsg(cf.getCodingForm_PreviewText(1)), Input.helpText);
		base.passedStep("Configured tool tip is displayed on mouse over of the objects");
		
		//validate in docview child window
		docview.clickGearIconOpenCodingFormChildWindow();
		docview.switchToNewWindow(2);
		soft.assertEquals(base.getToolTipMsg(cf.getCodingForm_PreviewText(0)), Input.helpText);
		soft.assertEquals(base.getToolTipMsg(cf.getCodingForm_PreviewText(1)), Input.helpText);
		base.passedStep("Configured tool tip is displayed on mouse over of the objects");
		driver.close();
		docview.switchToNewWindow(1);
		
		//restore default codingform
		driver.waitForPageToBeReady();
		cf.navigateToCodingFormPage();
		docview.acceptBrowserAlert(true);
		cf.AssigndefaultCFstoSG(Input.codeFormName);
	    
		//delete created cf
		cf.deleteCodingForm(cfName, cfName);
		
	    soft.assertAll();
	    base.passedStep("Verify that if comment or metadata objects have configured help text, on mouse over tool tip "
	    		+ "should appear for that field in either Coding Form Preview or Coding Form panel of DocView");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that default action and field logic presented as is (Special Objects - Check Group) when user edit the same Coding Form.
	 */
	@Test(description = "RPMXCON-54536",enabled = true, groups = { "regression" })
	public void verifyDefaultActionFeildLogicInTechIssueGroup() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54536");
	    base.stepInfo("Verify that default action and field logic presented as is (Special Objects - Check Group) when user edit the same Coding Form.");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		//open cf in edit mode
		cf.navigateToCodingFormPage();
		cf.editCodingForm(Input.codingFormName);
		
		//expand tech issue group
		base.waitForElement(cf.getCfObjectHeader("Tech Issue Group"));
		cf.getCfObjectHeader("Tech Issue Group").waitAndClick(5);
		driver.waitForPageToBeReady();
		
		//verify 
		soft.assertTrue(cf.getCfObjectDefaultActionLabelDropDrown("Tech Issue Group", "Default Action").isElementAvailable(3));
		soft.assertTrue(cf.getCfObjectDefaultActionLabelDropDrown("Tech Issue Group", "Field Logic").isElementAvailable(3));
		soft.assertTrue(cf.getCfObjectHeader("Tech Issue Group").getText().contains("CHECKGROUP"));
		base.passedStep("CheckGroup's Default Action along with field logic NOT miss in coding form.     For - CheckGroup's  -"
				+ " Default action and field logic presented as is (Special Objects) when user edit the same Coding Form.");
		
	    soft.assertAll();
	    base.passedStep("Verified that default action and field logic presented as is (Special Objects - Check Group) when user edit the same Coding Form.");
	    loginPage.logout();
	    
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that default action and field logic presented as is (Special Objects - Check Group) when user edit the same Coding Form.
	 */
	@Test(description = "RPMXCON-54537",enabled = true, groups = { "regression" })
	public void verifyDefaultActionFeildLogicInTechIssueGroupCheckGroup() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54537");
	    base.stepInfo("Verify that default action and field logic presented as is (Special Objects - Check Group) when user edit the same Coding Form.");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//open cf in edit mode
		cf.navigateToCodingFormPage();
		cf.editCodingForm(Input.codingFormName);
		base.stepInfo("coding form open in edit mode");
		
		//expand texh issue group
		driver.waitForPageToBeReady();
		base.waitForElement(cf.getCfObjectHeader("Tech Issue Group"));
		cf.getCfObjectHeader("Tech Issue Group").waitAndClick(5);
		driver.waitForPageToBeReady();
		base.stepInfo("Tch Issue group was expanded");
		
		//verify default action feild logic values 
		soft.assertTrue(cf.getCfObjectHeader("Tech Issue Group").getText().contains("CHECKGROUP"));
		soft.assertEquals(cf.getCfObjectDefaultActionLabelInput("Tech Issue Group", "Error Message").GetAttribute("value"),
				"If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above");
		soft.assertEquals(cf.getCfObjectDefaultActionLabelDropDrown("Tech Issue Group", "Default Action").selectFromDropdown().getFirstSelectedOption().getText(), Input.hidden);
		soft.assertEquals(cf.getCfObjectFeildLogicDropDrownByIndex("Tech Issue Group", 2).selectFromDropdown().getFirstSelectedOption().getText(), Input.select);
		soft.assertEquals(cf.getCfObjectFeildLogicDropDrownByIndex("Tech Issue Group", 3).selectFromDropdown().getFirstSelectedOption().getText(), Input.thisRequired);
		base.passedStep("As per Attachment - CF for Special Objects (Check Group) should saved with default action and field logic.");
		
	    soft.assertAll();
	    base.passedStep("Verified that default action and field logic presented as is (Special Objects - Check Group) when user edit the same Coding Form.");
	    loginPage.logout();
	    
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that Preview displays correctly and properly for Tag/Comments/Metadata objects 
	 * along with Check/Radio Group and Radio Item on coding form screen
	 */
	@Test(description = "RPMXCON-54061",enabled = true, groups = { "regression" })
	public void verifyPreviewDisplaysCorrecly() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54061");
	    base.stepInfo("Verify that Preview displays correctly and properly for Tag/Comments/Metadata"
	    		+ " objects along with Check/Radio Group and Radio Item on coding form screen");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.addCodingFormName(cfName);
		
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
		 cf.selectDefaultActions(1, Input.required);
		 cf.selectDefaultActions(2, Input.optional);
		 cf.enterErrorAndHelpMsg(2, "Yes", Input.errorMsg, Input.helpText);
		 cf.selectDefaultActions(4, Input.hidden);
		 cf.selectDefaultActions(5, Input.notSelectable);
		 cf.saveCodingForm();
		 
		 //edit codingform
		 cf.editCodingForm(cfName);
		 cf.clickPreviewButon();
		 
		 //verify
		 soft.assertTrue(cf.getPreviewComment().isElementAvailable(3));
		 soft.assertTrue(cf.getPreviewMetaData().isElementAvailable(3));
		 base.passedStep("Preview displayed correctly and properly");
		 
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    soft.assertAll();
	    base.passedStep("Verified that Preview displays correctly and properly for Tag/Comments/Metadata "
	    		+ "objects along with Check/Radio Group and Radio Item on coding form screen");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that default action and field logic (Special Objects - Check Group) works in the context of a document review in Parent Window
	 */
	@Test(description = "RPMXCON-54538",enabled = true, groups = { "regression" })
	public void verifyCheckGroupInDocView() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54538");
	    base.stepInfo("Verify that default action and field logic (Special Objects - Check Group) works in the context of a document review in Parent Window");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//check default codingform in default
		cf.selectDefaultProjectCodingForm();
		
		//drag and drop query
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.viewInDocView();
		
		//verify
		driver.waitForPageToBeReady();
		docview.Hot_DocCheckBox().waitAndClick(1);
		soft.assertFalse(base.text("Processing_Issue").isDisplayed()," progressing_issue");
		soft.assertFalse(base.text("Processing_Issue").isDisplayed(), "Foreign_Language");
		base.passedStep("Tech issue group check box is as per default action");
		
	    soft.assertAll();
	    base.passedStep("Verified that default action and field logic (Special Objects - Check Group) works in the context of a document review in Parent Window");
	    loginPage.logout();
	    
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that comment label should be allowed to be upto 255 characters in length and should wrap around properly if goes beyond the edge of the box
	 */
	@Test(description = "RPMXCON-54522",enabled = true, groups = { "regression" })
	public void verifyCommentTextWrapProperly() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54522");
	    base.stepInfo("Verify that comment label should be allowed to be upto 255 characters in length and should wrap around properly if goes beyond the edge of the box");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		docview = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		String moreChar  = Utility.randomCharacterAppender(300);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		cf.addCodingFormName(cfName);
		
		 //add cf field
		 cf.firstCheckBox(Input.comments);
		 cf.addcodingFormAddButton();
		 cf.enterErrorAndHelpMsg(0, "Yes", Input.helpText, Input.errorMsg);
		 String cmdLabel = cf.getCodingForm_TagNames(1).getText().trim();
		 
		 //validate in preview
		 cf.clickPreviewButon();
		 soft.assertEquals(cf.getCodingForm_PreviewText(0).getText().trim(), cmdLabel);
		 base.passedStep("Coding form saved  Presentation of the Comment Field Name should be immediately above the Comment Box in Coding Form Preview pop up");
		 
		 //edit comment more than 255
		 driver.waitForPageToBeReady();
		 cf.getPopUpCloseBtn().waitAndClick(5);
		 cf.getCF_objectName(0).SendKeys(moreChar);
		 cf.saveCodingForm();
		 cf.clickPreviewButon();
		 
		 //validate length 
		 soft.assertTrue(cf.getCodingForm_TagNames(1).getText().length()<256);
		 base.passedStep("Comment label should be allowed to be upto 255 characters in length");
		 
		 //text wrap verification
		 soft.assertTrue(cf.getCodingForm_PreviewText(0).getWebElement().getSize().height>19, "preview wrap check");
		 base.passedStep("Comment label should wrap around around properly in cf preview");
		 
		 
	    cf.AssignCFstoSG(cfName);
	    sessionSearch.basicContentSearch(Input.testData1);
	    sessionSearch.ViewInDocViews();
	    
	    //pre-req-save stamp
	    base.waitForElement(docview.getDocument_CommentsTextBox());
		docview.getDocument_CommentsTextBox().SendKeys("add some comment for cf stamp");
		docview.stampColourSelection("stamp"+Utility.dynamicNameAppender(), Input.stampColour);
		
	    //validate in docview
	    driver.waitForPageToBeReady();
	    soft.assertTrue(cf.getCodingForm_PreviewText(0).getWebElement().getSize().height>19, "docview parent window check");
	    base.passedStep("Comment label should wrap around around properly in parent window");
		
		//validate in docview child window
		docview.clickGearIconOpenCodingFormChildWindow();
		docview.switchToNewWindow(2);
		soft.assertTrue(cf.getCodingForm_PreviewText(0).getWebElement().getSize().height>19, "docview child window check");
		base.passedStep("Comment label should wrap around around properly in child window");
		
		//stamp check
		docview.lastAppliedStamp(Input.stampColour);
		soft.assertTrue(cf.getCodingForm_PreviewText(0).getWebElement().getSize().height>19, "docview check from stamp");
		base.passedStep("Comment label should wrap around around properly for stamp");
		
		driver.close();
		docview.switchToNewWindow(1);
		
		//restore default codingform
		driver.waitForPageToBeReady();
		cf.navigateToCodingFormPage();
		docview.acceptBrowserAlert(true);
		cf.AssigndefaultCFstoSG(Input.codeFormName);
	    
		//delete created cf
		cf.deleteCodingForm(cfName, cfName);
		
	    soft.assertAll();
	    base.passedStep("Verified that comment label should be allowed to be upto 255 characters in length and should wrap around properly if goes beyond the edge of the box");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that when user edit the Coding Form then User can edit default action and field logic Special Objects.(Radio Group)
	 */
	@Test(description = "RPMXCON-54540",enabled = true, groups = { "regression" })
	public void verifyUserCanEditDAandFL() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54540");
	    base.stepInfo("Verify that when user edit the Coding Form then User can edit default action and field logic Special Objects.(Radio Group)");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String codingform = "Default Project Coding Form_Copy"+ Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//create cf as per attachment
		cf.createCodingFormLikeDefaultCodingForm(codingform);
		
		//edit cf
		cf.editCodingForm(codingform);
		
		//expand
		base.waitForElement(cf.getCfObjectHeader("Tech Issue Group"));
		cf.getCfObjectHeader("Tech Issue Group").waitAndClick(5);
		driver.waitForPageToBeReady();
		
		//verify user can edit
		cf.getCfObjectDefaultActionLabelDropDrown("Tech Issue Group", "Default Action").selectFromDropdown().selectByVisibleText(Input.required);
		cf.getCfObjectFeildLogicDropDrownByIndex("Tech Issue Group", 3).selectFromDropdown().selectByVisibleText(Input.thisOptional);
		driver.waitForPageToBeReady();
		soft.assertEquals(cf.getCfObjectDefaultActionLabelDropDrown("Tech Issue Group", "Default Action").selectFromDropdown().getFirstSelectedOption().getText(), Input.required);
		soft.assertEquals(cf.getCfObjectFeildLogicDropDrownByIndex("Tech Issue Group", 3).selectFromDropdown().getFirstSelectedOption().getText(), Input.thisOptional);
		base.passedStep("When user edit the Coding Form then User  able to  edit default action and field logic Special Objects.(Radio Group)");
		
		//delete created cf
		cf.deleteCodingForm(codingform, codingform);
		
	    soft.assertAll();
	    base.passedStep("Verify that when user edit the Coding Form then User can edit default action and field logic Special Objects.(Radio Group)");
	    loginPage.logout();
	    
	}
	
	/**
	 * @Author : Aathith
	 * @Description :Verify that default action and field logic presented as is (Special Objects - Radio Group) when user edit the same Coding Form.
	 */
	@Test(description = "RPMXCON-54541",enabled = true, groups = { "regression" })
	public void verifyDefaultActionFieldLogic() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54541");
	    base.stepInfo("Verify that default action and field logic presented as is (Special Objects - Radio Group) when user edit the same Coding Form.");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String codingform = "Copy_of_Default Project Coding Form"+ Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//create cf as per attachment
		cf.createCodingFormLikeDefaultCodingForm(codingform);
		
		//edit cf
		cf.editCodingForm(codingform);
		
		//expand
		base.waitForElement(cf.getCfObjectHeader("Tech Issue Group"));
		cf.getCfObjectHeader("Tech Issue Group").waitAndClick(5);
		driver.waitForPageToBeReady();
		
		System.out.println(cf.getCfObjectHeader("Tech Issue Group").getText());
		soft.assertTrue(cf.getCfObjectDefaultActionLabelDropDrown("Tech Issue Group", "Default Action").isElementAvailable(3) , " default action present");
		soft.assertTrue(cf.getCfObjectDefaultActionLabelDropDrown("Tech Issue Group", "Field Logic").isElementAvailable(3), " field logic present");
		soft.assertTrue(cf.getCfObjectHeader("Tech Issue Group").getText().contains("RADIOGROUP"), "spl obj verification");
		base.passedStep("Radio Group's Default Action along with field logic should NOT miss in coding form.     For - Radio Group's - "
				+ "Default action and field logic presented as is (Special Objects) when user edit the same Coding Form.");
		
		//delete created cf
		cf.deleteCodingForm(codingform, codingform);
		
	    soft.assertAll();
	    base.passedStep("Verified that default action and field logic presented as is (Special Objects - Radio Group) when user edit the same Coding Form.");
	    loginPage.logout();
	    
	}
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
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