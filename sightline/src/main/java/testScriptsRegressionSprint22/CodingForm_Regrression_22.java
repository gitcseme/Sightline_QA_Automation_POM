package testScriptsRegressionSprint22;

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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
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