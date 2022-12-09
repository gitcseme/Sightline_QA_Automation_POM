package testScriptsRegressionSprint27;

import java.awt.AWTException;
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
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ICE_Regression27 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;
	KeywordPage keyPage;
	DocListPage docList;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();
		

	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:1/12/2022 RPMXCON-50308
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify the 'Datasets' options for the users when System Admin
	 *              impersonates to the below roles.
	 */
	@Test(description = "RPMXCON-50308", enabled = true, groups = { "regression" })
	public void verifyDatasetaDisplayInSAImpersonateUsers() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-50308");
		baseClass.stepInfo(
				"To verify the 'Datasets' options for the users when System Admin impersonates to the below roles.");

		DataSets data = new DataSets(driver);
		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.sa1userName + "");
		// Impersonate SA to PA
		baseClass.impersonateSAtoPA();
		softAssert.assertTrue(data.getDatasetBtn().isDisplayed());
		baseClass.passedStep("DataSats left menu is enabled by default for the user with Project Admin role");
		softAssert.assertAll();
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.sa1userName + "");
		// Impersonate SA to RMU
		baseClass.impersonateSAtoRMU();
		softAssert.assertTrue(data.getDatasetBtn().isDisplayed());
		baseClass.passedStep("DataSats left menu is enabled by default for the user with RMU role");
		softAssert.assertAll();
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.sa1userName + "");
		// Impersonate SA to DA
		baseClass.impersonateSAtoDA();
		baseClass.waitTime(10);
		softAssert.assertFalse(data.getDatasetBtn().isDisplayed());
		baseClass.passedStep("DataSats left menu is disabled by default for the user with DA role");
		softAssert.assertAll();
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:1/12/2022 RPMXCON-50304
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify left side menus for 'Datasets' when Domain Admin
	 *              impersonates as Project Admin/RMU.
	 */
	@Test(description = "RPMXCON-50304", enabled = true, groups = { "regression" })
	public void verifyDatasetaDisplayInDAImpersonateUsers() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-50304");
		baseClass.stepInfo(
				"To verify left side menus for 'Datasets' when Domain Admin impersonates as Project Admin/RMU.");

		DataSets data = new DataSets(driver);
		// Login as DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.da1userName + "");
		baseClass.waitTime(10);
		// Impersonate DA to PA
		baseClass.impersonateDAtoPA();
		softAssert.assertTrue(data.getDatasetBtn().isDisplayed());
		baseClass.passedStep("DataSats left menu is enabled by default for the user with Project Admin role");
		softAssert.assertAll();
		loginPage.logout();

		// Login as DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.da1userName + "");
		baseClass.waitTime(10);
		// Impersonate DA to RMU
		baseClass.impersonateDAtoRMU();
		softAssert.assertTrue(data.getDatasetBtn().isDisplayed());
		baseClass.passedStep("DataSats left menu is enabled by default for the user with Project Admin role");
		softAssert.assertAll();
		loginPage.logout();

		// Login as DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.da1userName + "");
		baseClass.waitTime(10);
		softAssert.assertFalse(data.getDatasetBtn().isDisplayed());
		baseClass.passedStep("DataSats left menu is disabled by default for the user with DA role");
		softAssert.assertAll();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 09/12/2022 TestCase Id:RPMXCON-50076
	 * Description:SL-ICE Integration:Verify that Metadata EmailAuthorAddress works correctly in Basic Search screen.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-50076",enabled = true, groups = { "regression" })
	public void verifyEmailAuthorAddressMetadataQuery() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-50076");
		baseClass.stepInfo("Verify that Metadata EmailAuthorAddress works correctly in Basic Search screen.");
		String[] value = {"gouri.dhavalikar@consilio.com","Swapnal.Sonawane@consilio.com"};
		String[] field = {"EmailAuthorAddress"};
		String configQuery = null;
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");
		DataSets dataSet = new DataSets(driver);
		softAssert.assertTrue(dataSet.getDatasetBtn().isDisplayed());
		baseClass.passedStep("User have dataset menu access");
		baseClass.stepInfo("perform metadata search with query");
		for(int i=0;i<value.length;i++) {
			int count =sessionSearch.MetaDataSearchInBasicSearch(field[0],value[i]);
			if(count>0) {
				baseClass.passedStep("Docs returned for the configured query"+count);
				configQuery=value[i];
				break;
			}
			else {
				baseClass.stepInfo("no docs returned for the query"+value[i]);
				//performing search for next query
				baseClass.selectproject();
			}
		}
		baseClass.stepInfo("verify docs returned satisfied the configured query");
		sessionSearch.ViewInDocList();
		docList = new DocListPage(driver);
		docList.SelectColumnDisplayByRemovingExistingOnes(field);
		driver.waitForPageToBeReady();
		String returnQuery = docList.getDataInDoclist(1,4).getText();
		if(configQuery.equalsIgnoreCase(returnQuery)) {
			baseClass.passedStep("return documents satisfied configured query");
		}
		else {
			baseClass.failedStep("return docs not satisfied configured query");
		}
		loginPage.logout();
	}

	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  Assignment_Regression_sprint23 .**");
	}
}
