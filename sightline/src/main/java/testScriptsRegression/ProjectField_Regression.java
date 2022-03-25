package testScriptsRegression;

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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ProjectField_Regression {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SessionSearch sessionSearch;
	SoftAssert softAssertion;
	ProjectFieldsPage projectFieldsPage;
	

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		// Open browser
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
	}
	
	/**
	 * 
	 * @Author: Mohan Created date: 10/02/2022 Modified date: NA Modified by: Mohan
	 * @Description :Verify that after entering 'Filter Fields By' and on click of 'Apply' should search Project Fields.'RPMXCON-47066'
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifyFilterFieldsByClickingApplyAndEnterButton() {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47066");
		baseClass.stepInfo("Verify that after entering 'Filter Fields By' and on click of 'Apply' should search Project Fields.");
		
		String textField = Input.fieldByValue;
		baseClass.stepInfo("Step 1: Login as Project Admin");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.projectName);
		
		
		baseClass.stepInfo("Step 2: Go to Manage > Project Fields");
		projectFieldsPage = new ProjectFieldsPage(driver);
		projectFieldsPage.navigateToProjectFieldsPage();
		baseClass.passedStep("Navigated to Project Field Page successfully");
		
		baseClass.stepInfo("Step 3: Enter the text in 'Filter Fields By' and click on Apply");
		projectFieldsPage.applyFilterByFilterName(textField);
		projectFieldsPage.validateFilterFieldsByContainsValueInTheGrid(projectFieldsPage.getProjectGridFieldNameValue(textField),textField);
		
		baseClass.stepInfo("Step 4: Enter the text in 'Filter Fields By' and hit enter key");
		projectFieldsPage.enterFilterFieldValueAndClickEnter(textField);
		projectFieldsPage.validateFilterFieldsByContainsValueInTheGrid(projectFieldsPage.getProjectGridFieldNameValue(textField),textField);
		
		loginPage.logout();
		
	}
	
	
	/**
	 * @author Gopinath
	 * @TestCase ID:47070 Verify that message should be displayed when there is no matching text for the 'Filter Fields By' for project fields- Covered localization
	 * @Description:To Verify that message should be displayed when there is no matching text for the 'Filter Fields By' for project fields- Covered localization
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void verifyNoDataMessageForNoMatchingText() {
		baseClass.stepInfo("Test case Id: RPMXCON-47070");
		baseClass.stepInfo("Verify that after entering 'Filter Fields By' and on click of 'Apply' should search Project Fields.");
		utility = new Utility(driver);
		String fieldName = "Test" +  Utility.dynamicNameAppender();
		String germanLanguage="German - Germany";
		String englishLanguage="English - United States";
		
		baseClass.stepInfo("Step 1: Login as Project Admin");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.projectName);
		
		baseClass.stepInfo("Step 2: Go to Manage > Project Fields");
		projectFieldsPage = new ProjectFieldsPage(driver);
		projectFieldsPage.navigateToProjectFieldsPage();
		baseClass.passedStep("Navigated to Project Field Page successfully");
		
		baseClass.stepInfo("Step 3: Enter the text in 'Filter Fields By' and click on Apply");
		projectFieldsPage.applyFilterByFilterName(fieldName);
		
		baseClass.stepInfo("Verify filter return no data");
		projectFieldsPage.verifyfilterReturnNoData();
		
		baseClass.stepInfo("Step 3:change localization to German");
		loginPage.editProfile(germanLanguage);
		
		baseClass.stepInfo("verify text field and apply button are localise to German");
		projectFieldsPage.verifyfilterLabelApplyButtonLocaliseToGerman();
		
		baseClass.stepInfo(" Enter the text in 'Filter Fields By' and click on Apply");
		projectFieldsPage.applyFilterByFilterName(fieldName);
		
		baseClass.stepInfo("Verify filter return no data after localised to german");
		projectFieldsPage.verifyfilterReturnNoDataGerman();
		
		baseClass.stepInfo("Change localization to English");
		loginPage.editProfile(englishLanguage);
		loginPage.logout();
	}
	
	
	
	/**
	 * @author Gopinath
	 * @TestCase Id:47069 Verify that once user applied filter then after clearing filter text on click of Apply or hitting enter key should display the list of all project fields
	 * @Description:To Verify that once user applied filter then after clearing filter text on click of Apply or hitting enter key should display the list of all project fields
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void verifyFilterFieldBeforeAndAfterClearFilter() {
		baseClass.stepInfo("Test case Id: RPMXCON-47069");
		baseClass.stepInfo("Verify that once user applied filter then after clearing filter text on click of Apply or hitting enter key should display the list of all project fields");
		
		String fieldName = Input.fieldByValue;
		baseClass.stepInfo("Step 1: Login as Project Admin");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.projectName);
		
		
		baseClass.stepInfo("Step 2: Go to Manage > Project Fields");
		projectFieldsPage = new ProjectFieldsPage(driver);
		projectFieldsPage.navigateToProjectFieldsPage();
		baseClass.passedStep("Navigated to Project Field Page successfully");
		
		List<String> fieldNamesBeforeFilter = baseClass.availableListofElements(projectFieldsPage.getFieldNames());
		
		baseClass.stepInfo("Step 3: Enter the text in 'Filter Fields By' and click on Apply");
		projectFieldsPage.applyFilterByFilterName(fieldName);
		
		baseClass.stepInfo("verify All field Names in project grid Contains filter value");
		projectFieldsPage.validateFilterFieldsByContainsFieldName(fieldName);
		
		baseClass.stepInfo("Step 4: verify all field names are displayed after clear filter");
		projectFieldsPage.clearFilter();
		projectFieldsPage.verifyAllFieldNamesDisplaydAfterClearFilter(fieldNamesBeforeFilter);
		
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
			// loginPage.clearBrowserCache();
		}

	}

}
