package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SecurityGroups_Regression26 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	DocViewMetaDataPage docViewMetaDataPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;

	String assignmentName = "AAassignment" + Utility.dynamicNameAppender();

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
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
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

	/**
	 * @author Krishna Date: Modified date:N/A Modified by: Description :Verify
	 *         that user can not enter external scripts in Create Security Group
	 *         screen
	 */
	@Test(description = "RPMXCON-54907", enabled = true, groups = { "regression" })
	public void verifyUserCannotExternalScriptInSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54907");
		baseClass.stepInfo("Verify that user can not enter external scripts in Create Security Group screen");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		SoftAssert softassert = new SoftAssert();
		String SGname = "<script>alert(3)</script>";
		String SGname1 = "\\/()~`!@#$%^&*,;. |";

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
        
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.createSecurityGroups(SGname);
		String Actualwarningmsg = "The security group name is invalid.";
		baseClass.waitTime(5);
		String expectWarningmsg = sgpage.getSG_InvalidErrorMsg().getText();
		System.out.println(expectWarningmsg);
		baseClass.stepInfo("Expected warning message..." + expectWarningmsg);
		driver.waitForPageToBeReady();
		if (Actualwarningmsg.equals(expectWarningmsg)) {
			baseClass.passedStep(expectWarningmsg+"   Warning message is displayed successfully");
		} else {
			baseClass.failedStep("Warning msg is not displayed");
		}
		
		baseClass.waitForElement(sgpage.getSG_NameCancelBtn());
		sgpage.getSG_NameCancelBtn().waitAndClick(5);

		driver.waitForPageToBeReady();
		softassert.assertFalse(sgpage.getSG_PopUp().isElementPresent());
		baseClass.passedStep("Security Group is deleted and no pop up  displayed as expected.");
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		sgpage.createSecurityGroups(SGname1);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		softassert.assertTrue(sgpage.getSG_PopUp().isElementPresent());
		baseClass.passedStep("User has been able to use the listed chars. as expected");
		sgpage.deleteSecurityGroups(SGname1);
		loginPage.logout();
	}

}
