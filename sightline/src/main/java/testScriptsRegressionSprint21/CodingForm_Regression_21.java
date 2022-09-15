package testScriptsRegressionSprint21;

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

public class CodingForm_Regression_21 {

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
	 * @Description : Verify that Preview displays correctly and properly for 
	 * Tag/Comments/Metadata objects along with Check/Radio Group and Check Item on coding form screen
	 */
	@Test(description = "RPMXCON-54060",enabled = true, groups = { "regression" })
	public void verifyPreviewDisplaysCorrecly() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54060");
	    base.stepInfo("Verify that Preview displays correctly and properly for "
	    		+ "Tag/Comments/Metadata objects along with Check/Radio Group and Check Item on coding form screen");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 base.waitForElement(cf.getAddNewCodingFormBtn());
		 cf.getAddNewCodingFormBtn().waitAndClick(10);
		 base.waitForElement(cf.getCodingFormName());
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
		 base.passedStep("Preview displayed correctly and properly");
		 
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    soft.assertAll();
	    base.passedStep("Verify that Preview displays correctly and properly for "
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
		
	    base.stepInfo("Test case Id: RPMXCON-54066");
	    base.stepInfo("Verify that Preview displays correctly and properly for all "
	    		+ "objects along with all condition and Radio Item on coding form screen");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 base.waitForElement(cf.getAddNewCodingFormBtn());
		 cf.getAddNewCodingFormBtn().waitAndClick(10);
		 base.waitForElement(cf.getCodingFormName());
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
		 base.waitForElement(cf.getCF_PreviewButton());
		 cf.getCF_PreviewButton().waitAndClick(10);
		 driver.waitForPageToBeReady();
		 soft.assertTrue(cf.getPreviewComment().isElementAvailable(3));
		 soft.assertTrue(cf.getPreviewMetaData().isElementAvailable(3));
		 soft.assertTrue(base.text("Static Text").isElementAvailable(3));
		 base.passedStep("Preview displayed correctly and properly");
		 
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    soft.assertAll();
	    base.passedStep("Verified that Preview displays correctly and properly for all objects "
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
		
	    base.stepInfo("Test case Id: RPMXCON-54073");
	    base.stepInfo("Verify that Preview displays correctly and properly when"
	    		+ " Tag and Radio Group combined along with Radio Item for new  coding form");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 base.waitForElement(cf.getAddNewCodingFormBtn());
		 cf.getAddNewCodingFormBtn().waitAndClick(10);
		 base.waitForElement(cf.getCodingFormName());
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
		 soft.assertTrue(base.text("Coding Form Validation Successful.").isElementAvailable(3));
		 cf.getCodingForm_Validation_ButtonYes().waitAndClick(5);
		 cf.clickPreviewButon();
		 soft.assertFalse(cf.getPreviewRadioBtn().isDisplayed());
		 base.passedStep("Preview displayed correctly and properly");
		 
	    soft.assertAll();
	    base.passedStep("Verified that Preview displays correctly and properly when "
	    		+ "Tag and Radio Group combined along with Radio Item for new  coding form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description Verify that check group association should be saved for the Tag when coding form is saved
	 */
	@Test(description = "RPMXCON-54499",enabled = true, groups = { "regression" })
	public void verifyTagWithCheckGroup() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54499");
	    base.stepInfo("Verify that check group association should be saved for the Tag when coding form is saved");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2547 
		 cf.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 base.waitForElement(cf.getAddNewCodingFormBtn());
		 cf.getAddNewCodingFormBtn().waitAndClick(10);
		 base.waitForElement(cf.getCodingFormName());
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
		 base.passedStep("Check group association saved for the tag when coding form is saved.");
		 
	    soft.assertAll();
	    base.passedStep("Verified that check group association should be saved for the Tag when coding form is saved");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description Verify that check group association should be saved for the Tag when coding form is edited
	 */
	@Test(description = "RPMXCON-54502",enabled = true, groups = { "regression" })
	public void verifyTagWithCheckGroupInEditCf() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54502");
	    base.stepInfo("Verify that check group association should be saved for the Tag when coding form is edited");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

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
		 soft.assertTrue(base.text("TAG:").isElementAvailable(3));
		 soft.assertTrue(base.text("CHECKGROUP:").isElementAvailable(3));
		 
	    soft.assertAll();
	    base.passedStep("Verified that check group association should be saved for the Tag when coding form is edited");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description UI: Verify the error message alignment from preview of coding form
	 */
	@Test(description = "RPMXCON-54527",enabled = true, groups = { "regression" })
	public void verifyErrMsgAligPrev() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54527");
	    base.stepInfo("UI: Verify the error message alignment from preview of coding form");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "codingform" + Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

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
		 base.passedStep("Preview of coding form pop up should open  Error message displayed for the required fields ");
		 
		 //'*' vlidation
		 String firstobj = cf.getCodingForm_PreviewText(1).getText().trim();
		 String secobj = cf.getCodingForm_PreviewText(2).getText().trim();
		 String thiredobj = cf.getCodingForm_PreviewText(3).getText().trim();
		 soft.assertEquals(firstobj.charAt(firstobj.length()-1), '*');
		 soft.assertEquals(secobj.charAt(secobj.length()-1), '*');
		 soft.assertEquals(thiredobj.charAt(thiredobj.length()-1), '*');
		 base.passedStep("asterisk aligned properly for the required fields");
		 
	    soft.assertAll();
	    base.passedStep("UI: Verified the error message alignment from preview of coding form");
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