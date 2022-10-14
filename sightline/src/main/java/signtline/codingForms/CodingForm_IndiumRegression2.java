package signtline.codingForms;

import pageFactory.DocViewPage;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
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

public class CodingForm_IndiumRegression2 {
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
