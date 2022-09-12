package testScriptsRegressionSprint20;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

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

public class CodingForm_Regression_20 {

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
	 * @Description : Verify that duplicate customized coding form does not get created on "Manage Coding Forms" screen
	 */
	@Test(description = "RPMXCON-53973",enabled = true, groups = { "regression" })
	public void validateCfScreenWorksProperly() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-53973");
	    base.stepInfo("Verify that duplicate customized coding form does not get created on \"Manage Coding Forms\" screen");
	    
	    soft = new SoftAssert();
		cf = new CodingForm(driver);
		
		//paremeters
		String cfName[] = {"1LR Coding Form", "  1LR Coding Form", "1LR Coding Form  ", "  1LR Coding Form  "};
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    base.stepInfo("User navigated to Manage coding form page");
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    soft.assertTrue(driver.getPageSource().contains("New Coding Form"));
	    base.stepInfo("coding form works properly");
	    
	    //pre-req
	    if(!cf.searchCodingForm(cfName[0])) {
	    	cf.createCodingform(cfName[0]);
	    }
	    
	    //verification
	    cf.navigateToCodingFormPage();
	    for(String codingform:cfName) {
	    	cf.addCodingFormNameOnly(codingform);
	    	base.VerifyErrorMessage("10001000001 : Coding Form label is already exist");
	    }
	    
	    soft.assertAll();
	    base.passedStep("Verified that duplicate customized coding form does not get created on \"Manage Coding Forms\" screen");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To Verify, as an RM user login, I will be able to sort the column based on Coding form name in manage coding forms page
	 */
	@Test(description = "RPMXCON-53975",enabled = true, groups = { "regression" })
	public void verifyRMAbleToSortCodingFormTable() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-53975");
	    base.stepInfo("To Verify, as an RM user login, I will be able to sort the column based on Coding form name in manage coding forms page");
	    
	    soft = new SoftAssert();
		cf = new CodingForm(driver);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    base.stepInfo("User navigated to Manage coding form page");
	    
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    int i =base.getIndex(cf.getCodingFormTableHeaders(), "CODING FORM NAME");
	    soft.assertEquals(cf.getCodingFormTableHeadColumn(i).GetAttribute("aria-sort"), "ascending");
	    base.stepInfo("table is in ascending order");
	    cf.getCodingFormTableHeadColumn(i).waitAndClick(5);
	    driver.waitForPageToBeReady();
	    soft.assertEquals(cf.getCodingFormTableHeadColumn(i).GetAttribute("aria-sort"), "descending");
	    base.stepInfo("table is in descending order");
	    
	    soft.assertAll();
	    base.passedStep("Verified as an RM user login, I will be able to sort the column based on Coding form name in manage coding forms page");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To Verify, as an RM user login, I will be able to sort the column based on user created name in manage coding forms page
	 */
	@Test(description = "RPMXCON-53976",enabled = true, groups = { "regression" })
	public void verifyRMAbleToSortCodingFormTableByCreatedUser() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-53976");
	    base.stepInfo("To Verify, as an RM user login, I will be able to sort the column based on user created name in manage coding forms page");
	    
	    soft = new SoftAssert();
		cf = new CodingForm(driver);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    base.stepInfo("User navigated to Manage coding form page");
	    
	    //verification
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    int i = base.getIndex(cf.getCodingFormTableHeaders(), "CREATED BY");
	    cf.getCodingFormTableHeadColumn(i).waitAndClick(5);
	    driver.waitForPageToBeReady();
	    base.waitTime(2);
	    soft.assertEquals(cf.getCodingFormTableHeadColumn(i).GetAttribute("aria-sort"), "ascending");
	    base.stepInfo("table is in ascending order");
	    cf.getCodingFormTableHeadColumn(i).waitAndClick(5);
	    driver.waitForPageToBeReady();
	    base.waitTime(2);
	    soft.assertEquals(cf.getCodingFormTableHeadColumn(i).GetAttribute("aria-sort"), "descending");
	    base.stepInfo("table is in descending order");
	    
	    soft.assertAll();
	    base.passedStep("Verified, as an RM user login, I will be able to sort the column based on user created name in manage coding forms page");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To Verify, as an RM user login, I will be able to sort the column based on Date Created in manage coding forms page
	 */
	@Test(description = "RPMXCON-53977",enabled = true, groups = { "regression" })
	public void verifyRMAbleToSortCodingFormTableByCreatedDate() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-53977");
	    base.stepInfo("To Verify, as an RM user login, I will be able to sort the column based on Date Created in manage coding forms page");
	    
	    soft = new SoftAssert();
		cf = new CodingForm(driver);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    base.stepInfo("User navigated to Manage coding form page");
	    
	    //verification
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    int i = base.getIndex(cf.getCodingFormTableHeaders(), "DATE CREATED (UTC)");
	    cf.getCodingFormTableHeadColumn(i).waitAndClick(5);
	    driver.waitForPageToBeReady();
	    base.waitTime(2);
	    soft.assertEquals(cf.getCodingFormTableHeadColumn(i).GetAttribute("aria-sort"), "ascending");
	    base.stepInfo("table is in ascending order");
	    cf.getCodingFormTableHeadColumn(i).waitAndClick(5);
	    driver.waitForPageToBeReady();
	    base.waitTime(2);
	    soft.assertEquals(cf.getCodingFormTableHeadColumn(i).GetAttribute("aria-sort"), "descending");
	    base.stepInfo("table is in descending order");
	    
	    soft.assertAll();
	    base.passedStep("Verified, as an RM user login, I will be able to sort the column based on Date Created in manage coding forms page");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To Verify, as an RM user login, I will be able to select a default coding form from manage coding forms page
	 */
	@Test(description = "RPMXCON-53978",enabled = true, groups = { "regression" })
	public void verifyDefaultCodingFormSelected() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-53978");
	    base.stepInfo("To Verify, as an RM user login, I will be able to select a default coding form from manage coding forms page");
	    
	    soft = new SoftAssert();
		cf = new CodingForm(driver);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    base.stepInfo("User navigated to Manage coding form page");
	    
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    cf.searchCodingForm("Default Project Coding Form");
	    if(!cf.getDefaultCodingFormTableValues(5).getText().contains("Default")) {
	    	cf.selectDefaultProjectCodingForm();
	    }
	    base.stepInfo("Selected coding form will be default coding form for that security group");
	    
	    soft.assertAll();
	    base.passedStep("Veriied, as an RM user login, I will be able to select a default coding form from manage coding forms page");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that Proper validation message appears when user clicks on validation button without configuring Form Name
	 */
	@Test(description = "RPMXCON-53980",enabled = true, groups = { "regression" })
	public void verifyValidateMsgWithoutConfigCFName() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-53980");
	    base.stepInfo("Verify that Proper validation message appears when user clicks on validation button without configuring Form Name");
	    
	    soft = new SoftAssert();
		cf = new CodingForm(driver);
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2517 
	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    base.stepInfo("User navigated to Manage coding form page");
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    soft.assertTrue(driver.getPageSource().contains("New Coding Form"));
	    base.stepInfo("coding form works properly");
	    
	    cf.getAddNewCodingFormBtn().waitAndClick(10);
	    driver.waitForPageToBeReady();
	    soft.assertTrue(base.text("Group: TAGS").isDisplayed());
	    base.passedStep("All available Tags should get displayed");
	    cf.getCodingForm_FirstTag().waitAndClick(10);
	    cf.getCodingForm_AddToFormButton().waitAndClick(5);
	    soft.assertTrue(driver.getPageSource().contains("TAG Label"));
	    base.passedStep("Selected TAG should appeared inside Coding Form Structure");
	    driver.waitForPageToBeReady();
	    cf.getCFValidateBtn().waitAndClick(5);
	    driver.waitForPageToBeReady();
	    soft.assertEquals(cf.getCodingForm_MandField().getText().trim(),"Please provide a name for your Coding Form");
	    base.passedStep("Proper validation message is appears on screen");
	    
	    soft.assertAll();
	    base.passedStep("Verified that Proper validation message appears when user clicks on validation button without configuring Form Name");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that user adds special objects multiple times to the coding form
	 */
	@Test(description = "RPMXCON-53983",enabled = true, groups = { "regression" })
	public void verifyUserAddSpecialObjectMultipleTime() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-53983 ");
	    base.stepInfo("Verify that user adds special objects multiple times to the coding form");
	    
	    soft = new SoftAssert();
		cf = new CodingForm(driver);
		
		String cfName = "1LR Coding Form";
		String[] prereq = {"tag", "comment", "metadata"};
		String[] parem = {"staticText", "check", "radio"};
		String[] verifyText = {"statictext_", "checkgroup_", "radiogroup_"};
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
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
		base.passedStep("User should  add @SpecialObjects field multiple times on  coding form");
		
	    soft.assertAll();
	    base.passedStep("Verified that user adds special objects multiple times to the coding form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To verify, As an RMU login, I will be able to Edit a Coding Form
	 */
	@Test(description = "RPMXCON-53992",enabled = true, groups = { "regression" })
	public void verifyRmuAlbleToEditCF() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-53992");
	    base.stepInfo("To verify, As an RMU login, I will be able to Edit a Coding Form");
	    
	    soft = new SoftAssert();
		cf = new CodingForm(driver);
		
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	    cf.navigateToCodingFormPage();
	    driver.waitForPageToBeReady();
	    base.stepInfo("User navigated to Manage coding form page");
	    soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
	    soft.assertTrue(driver.getPageSource().contains("New Coding Form"));
	    base.stepInfo("User will land in Coding Forms page");
	    String cfname = cf.getCodingFormTableValues(1, 1).getText().trim();
	    
	    cf.getEditClick().waitAndClick(5);
	    driver.waitForPageToBeReady();
	    soft.assertTrue(driver.getPageSource().contains("Create/Edit Coding Form"));
	    soft.assertTrue(driver.getPageSource().contains("Coding Form Editor"));
	    soft.assertTrue(base.text(cfname).isElementAvailable(3));
	    base.passedStep("After Edit option selected, User redirect to Create/Edit page with the selected coding form");
	    
	    
	    soft.assertAll();
	    base.passedStep("verified, As an RMU login, I  able to Edit a Coding Form");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To verify, As an RMU login, When I will edit an existing coding form, In edit page I can remove any of tags, comments or Metadata that associated with this coding form, & save it after removing
	 */
	@Test(description = "RPMXCON-54003",enabled = true, groups = { "regression" })
	public void verifyEditCFRemoveTag() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54003");
	    base.stepInfo("To verify, As an RMU login, When I will edit an existing coding form, In edit page I can remove any of tags, comments or Metadata that associated with this coding form, & save it after removing");
	    
		cf = new CodingForm(driver);
		
		String cfName = "codingForm"+Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//validation
	    cf.createCodingform(cfName);
	    cf.editCodingForm(cfName);
	    cf.removeNthCodingForm(0);
	    cf.updateCodingForm();
	    base.passedStep("Page is saved successfully after removing");
	    
	    //delete created coding form
	    cf.deleteCodingForm(cfName,cfName);
	    
	    base.passedStep("verified, As an RMU login, When I will edit an existing coding form, In edit page I can remove any of tags, comments or Metadata that associated with this coding form, & save it after removing");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : As an RMU login, When user will apply edit on any exiting coding form, & when user modified something, & will navigate to some other page, it should ask for save the changes
	 */
	@Test(description = "RPMXCON-54004",enabled = true, groups = { "regression" })
	public void verifyEditCFRemoveTagNavigatePage() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54004");
	    base.stepInfo("As an RMU login, When user will apply edit on any exiting coding form, & when user modified something, & will navigate to some other page, it should ask for save the changes");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "codingForm"+Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

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
	    base.passedStep("As an RMU login, When user will apply edit on any exiting coding form, & when user modified something, & will navigate to some other page, it should ask for save the changes");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To verify, As an RMU login, When I will edit an existing coding form, I can be able to select a new Comment & able to save this change
	 */
	@Test(description = "RPMXCON-54008",enabled = true, groups = { "regression" })
	public void verifyRmuAbleToSelectCommentInExitCF() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54008");
	    base.stepInfo("To verify, As an RMU login, When I will edit an existing coding form, I can be able to select a new Comment & able to save this change");
	    
		cf = new CodingForm(driver);
		
		String cfName = "codingForm"+Utility.dynamicNameAppender();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//edit cf
	    cf.createTagsSavedInCf(cfName);
	    cf.editCodingForm(cfName);
	    
	    //add comment and save cf
	    cf.addTwoCheckBox("comment");
	    cf.updateCodingForm();
	    
	    //delete created cf
	    cf.deleteCodingForm(cfName,cfName);
	    
	    base.passedStep("verified As an RMU login, When I will edit an existing coding form, I can be able to select a new Comment & able to save this change");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : Verify that long coding form name appears properly on Coding Form screen
	 */
	@Test(description = "RPMXCON-54009",enabled = true, groups = { "regression" })
	public void verifyLongNameApeersProperly() throws Exception {
		
	    base.stepInfo("Test case Id: RPMXCON-54009");
	    base.stepInfo("Verify that long coding form name appears properly on Coding Form screen");
	    
		cf = new CodingForm(driver);
		soft  = new SoftAssert();
		
		String cfName = "LongCodingFormNameLongCodingFormNameLongCodingFormName";
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		//EXECUTE SHARED STEPS IN TC#2517 
		 cf.navigateToCodingFormPage();
		 driver.waitForPageToBeReady();
		 base.stepInfo("User navigated to Manage coding form page");
		 soft.assertTrue(driver.getPageSource().contains("Manage Coding Forms"));
		 soft.assertTrue(driver.getPageSource().contains("New Coding Form"));
		 base.stepInfo("coding opens properly");
		
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
	    base.passedStep("Verified that long coding form name appears properly on Coding Form screen");
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
