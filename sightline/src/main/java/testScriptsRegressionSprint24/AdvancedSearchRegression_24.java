package testScriptsRegressionSprint24;

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
	import pageFactory.BaseClass;
	import pageFactory.DocViewRedactions;
	import pageFactory.LoginPage;
	import pageFactory.SavedSearch;
	import pageFactory.SecurityGroupsPage;
	import pageFactory.SessionSearch;
	import pageFactory.Utility;
	import testScriptsSmoke.Input;

	public class AdvancedSearchRegression_24 {
		Driver driver;
		LoginPage loginPage;
		SessionSearch sessionSearch;
		SavedSearch savedSearch;
		BaseClass baseClass;
		Input in;
		SoftAssert assertion;
		SecurityGroupsPage securityGroupsPage;
		DocViewRedactions docViewRedact;

		@BeforeClass(alwaysRun = true)
		public void preCondition() throws ParseException, InterruptedException, IOException {
			System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
			in = new Input();
			in.loadEnvConfig();
		}

		@BeforeMethod
		public void beforeTestMethod(Method testMethod) {
			System.out.println("------------------------------------------");
			System.out.println("Executing method : " + testMethod.getName());
			driver = new Driver();
			loginPage = new LoginPage(driver);
			sessionSearch = new SessionSearch(driver);
			baseClass = new BaseClass(driver);
			assertion = new SoftAssert();
			savedSearch = new SavedSearch(driver);

		}

		/**
		 * @author
		 * @Modified date:N/A
		 * @Modified by: N/A
		 * @Description : Verify that warning message appears after pre-submittal
		 *              validations WP on Advanced Search Screen. RPMXCON-57280
		 */
		@Test(description = "RPMXCON-57280", enabled = true, groups = { "regression" })
		public void verifyWarningMessageAppearsAfterPreSubmittalValidationsWPAdv() {

			baseClass.stepInfo("Test case Id: RPMXCON-57280 Advanced Search");
			baseClass.stepInfo(
					"Verify that warning message appears after pre-submittal validations  WP on Advanced Search Screen.");

			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

			// navigating to advanced search page and Configuring the search Query in
			// WorkProduct Tab
			sessionSearch.switchToWorkproduct();
			sessionSearch.getWorkProductTextBox().SendKeys(Input.searchString1);
			baseClass.stepInfo("navigating to advanced search page and Configuring the search Query in WorkProduct Tab.");

			// perform search action
			sessionSearch.SearchBtnAction();
			baseClass.stepInfo("perform search action.");

			// Verify that application displays warning message
			String expectedWarningMessage = "Please enter a valid search expression";
			baseClass.VerifyWarningMessage(expectedWarningMessage);
			baseClass.passedStep("Verified that application displays warning message.");
			baseClass.printResutInReport(null, expectedWarningMessage, expectedWarningMessage, expectedWarningMessage);

			// logOut
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

			System.out.println("**Executed Advanced search Regression2_21**");
		}

	}
