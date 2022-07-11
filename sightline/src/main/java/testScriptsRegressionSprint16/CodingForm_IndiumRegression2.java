package testScriptsRegressionSprint16;

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
	 * @Author :Indium-Baskar
	 * @Description : Verify that Unique validation message appears 
	 *                 when user configured more than 1 Logic Rules
	 */
	@Test(description = "RPMXCON-53990",enabled = true, groups = { "regression" })
	public void verifyCFPreviewUsingCheckItem() throws Exception {
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
