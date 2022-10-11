package sightline.docview;

	import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

	public class DocView_Regression_21 { 

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

		@DataProvider(name = "userDetails2")
		public Object[][] userLoginDetails2() {
			return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
					{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
		}


		/**
		 * @author Sakthivel ModifyDate:02/08/2022 RPMXCON-48811
		 * @throws Exception
		 * @Description Verify that "Text Highlighting" functionality is working proper
		 *              on DocView Screen.
		 * 
		 */
		@Test(description = "RPMXCON-48811", enabled = true, groups = { "regression" })
		public void verifyHighlightingTextIsWorkingOnDocViewScreen() throws Exception {

			baseClass.stepInfo("Test case Id: RPMXCON-48811");
			baseClass.stepInfo("Verify that \"Text Highlighting\" functionality is working proper on DocView Screen.");
			SessionSearch sessionsearch = new SessionSearch(driver);
			KeywordPage keywordPage = new KeywordPage(driver);
			String hitTerms = "Test" + Utility.dynamicNameAppender();;
			String color = "Red";
			DocViewPage docView = new DocViewPage(driver);

			// Login as Reviewer Manager
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

			// Add keywords
			this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
			keywordPage.addKeywordWithColor(hitTerms, color);
			baseClass.stepInfo("Text Highlighting option color is in " + color);

			// document searched and navigated to DocView
			sessionsearch.basicContentSearch(Input.randomText);
			sessionsearch.ViewInDocView();
			baseClass.stepInfo("Docs Viewed in Doc View");
			driver.waitForPageToBeReady();
			docView.verifyPersistingHitsHighlightingTextInSelectedDoc(hitTerms);
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			baseClass.stepInfo("page is refreshed");
			driver.scrollPageToTop();
			docView.verifyPersistingHitsHighlightingTextInSelectedDoc(hitTerms);
			loginPage.logout();
	       
			//Login AS REV
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			baseClass.stepInfo("Login as REV");
			// document searched and navigated to DocView
			sessionsearch.basicContentSearch(Input.randomText);
			sessionsearch.ViewInDocView();
			baseClass.stepInfo("Docs Viewed in Doc View");
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			docView.verifyPersistingHitsHighlightingTextInSelectedDoc(hitTerms);
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			baseClass.stepInfo("page is refreshed");
			driver.scrollPageToTop();
			docView.verifyPersistingHitsHighlightingTextInSelectedDoc(hitTerms);
			keywordPage.deleteKeywordByName(hitTerms);
		}
	}
	
