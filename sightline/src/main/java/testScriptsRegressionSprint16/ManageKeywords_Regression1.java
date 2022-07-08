package testScriptsRegressionSprint16;

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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ManageKeywords_Regression1 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;
	SoftAssert softAssert;

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
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);

	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:01/07/2022 RPMXCON-52500
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify when Sys Admin should be able to create keyword group
	 *              and keywords after impersonating
	 */
	@Test(description = "RPMXCON-52500", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationToAccessCreateKeyword() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52500");
		baseClass.stepInfo(
				"To verify when Sys Admin should be able to create keyword group and keywords after impersonating.");
		KeywordPage keyWord = new KeywordPage(driver);
		String keywordname = "Addkeyword" + Utility.dynamicNameAppender();
		String color = "Blue";
		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		// Impersonate SA to RMU
		baseClass.impersonateSAtoRMU();
		// Add keyword
		keyWord.addKeywordWithColor(keywordname, color);
		baseClass.passedStep("After impersonating to access the create keyword group and keywords. ");
		loginPage.logout();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		String keywordname1 = "Addkeyword1" + Utility.dynamicNameAppender();
		String color1 = "Blue";
		// Impersonate SA to PA
		baseClass.impersonateSAtoPA();
		// Add keyword
		keyWord.addKeywordWithColor(keywordname1, color1);
		baseClass.passedStep("After impersonating to access the create keyword group and keywords. ");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:01/07/2022 RPMXCON-52501
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify when Project Admin should be able to create keyword
	 *              group and keywords after impersonating to the RMU role
	 */
	@Test(description = "RPMXCON-52501", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationToCreateKeyword() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52501");
		baseClass.stepInfo(
				"To verify when Project Admin should be able to create keyword group and keywords after impersonating to the RMU role.");
		KeywordPage keyWord = new KeywordPage(driver);
		String keywordname = "Addkeyword" + Utility.dynamicNameAppender();
		String color = "Blue";
		// Login As SA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		// Impersonate PA to RMU
		baseClass.impersonatePAtoRMU();

		// Add keyword
		keyWord.addKeywordWithColor(keywordname, color);
		baseClass.passedStep("After impersonating to RMU role from Project Admin, access to create keyword group and keywords in the selected project and the security group. ");

		loginPage.logout();
	}
	
	/**
	 * @author Mohan.Venugopal Created Date:06/07/2022 RPMXCON-52505
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify Manage Keywords page
	 */
	@Test(description = "RPMXCON-52505", enabled = true, groups = { "regression" })
	public void verifyManageKeywordPage() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52505");
		baseClass.stepInfo("To verify Manage Keywords page");
		KeywordPage keyWord = new KeywordPage(driver);
		
		// Login As PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		//Verify Manage Keyowrd Page for RMU
		keyWord.navigateToKeywordPage();
		keyWord.verifyManageKeywordPageRMUAndPA("RMU");
		loginPage.logout();
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		// Verify Manage Keyowrd Page for PA
		keyWord.navigateToKeywordPage();
		keyWord.verifyManageKeywordPageRMUAndPA("PA");
		loginPage.logout();
	}
	
	/**
	 * @author Mohan.Venugopal Created Date:06/07/2022 RPMXCON-52497
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify user creates duplicate keyword group and adds keywords into the group
	 */
	@Test(description = "RPMXCON-52497", enabled = true, groups = { "regression" })
	public void verifyManageKeywordPageDuplicate() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52497");
		baseClass.stepInfo("To verify user creates duplicate keyword group and adds keywords into the group");
		KeywordPage keyWord = new KeywordPage(driver);
		
		// Login As PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		//Verify Manage Keyowrd Page for RMU
		keyWord.navigateToKeywordPage();
		keyWord.verifyDuplicateKeywordFromList();
		loginPage.logout();
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		// Verify Manage Keyowrd Page for PA
		keyWord.navigateToKeywordPage();
		keyWord.verifyDuplicateKeywordFromList();
		loginPage.logout();
	}
	
	
	/**
	 * @author Mohan.Venugopal Created Date:07/07/2022 RPMXCON-52494
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify when user edits keyword group and keywords into the group
	 */
	@Test(description = "RPMXCON-52494", enabled = true, groups = { "regression" })
	public void verifyManageKeywordPageEdit() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52494");
		baseClass.stepInfo("To verify when user edits keyword group and keywords into the group");
		KeywordPage keyWord = new KeywordPage(driver);
		String keywordname = "PhaseII" + Utility.dynamicNameAppender();
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		//Verify Manage Keyowrd Page for RMU
		keyWord.navigateToKeywordPage();
		keyWord.editExistigKeywordAndVerifyThem(keywordname);
		loginPage.logout();
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		// Verify Manage Keyowrd Page for PA
		keyWord.navigateToKeywordPage();
		keyWord.editExistigKeywordAndVerifyThem(keywordname);
		loginPage.logout();
	}
	
	/**
	 * @author Mohan.Venugopal Created Date:07/07/2022 RPMXCON-52496
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify on click of Cancel button keyword group and keyword should not be created
	 */
	@Test(description = "RPMXCON-52496", enabled = true, groups = { "regression" })
	public void verifyKeywordAfterPressOfCancelButtonToNotCreate() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52496");
		baseClass.stepInfo("To verify on click of Cancel button keyword group and keyword should not be created");
		KeywordPage keyWord = new KeywordPage(driver);
		String keywordname = "Pass";
		String color = "Blue";
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		
		// Add keyword and Cancel it
		keyWord.navigateToKeywordPage();
		keyWord.addKeywordsAndPressCancelButton(keywordname, color);
		loginPage.logout();
		
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		// Add keyword and Cancel it
		keyWord.navigateToKeywordPage();
		keyWord.addKeywordsAndPressCancelButton(keywordname, color);
		loginPage.logout();
	}
	
	/**
	 * @author Mohan.Venugopal Created Date:07/07/2022 RPMXCON-52499
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify user with Sys Admin and Reviewer can not create keyword group and keywords
	 */
	@Test(description = "RPMXCON-52499", enabled = true, groups = { "regression" })
	public void verifySAandReviewerCanCreateKeyword() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52499");
		baseClass.stepInfo("To verify user with Sys Admin and Reviewer can not create keyword group and keywords");
		KeywordPage keyWord = new KeywordPage(driver);
		
		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		
		// Keyword cannot be created
		softAssert = new SoftAssert();
		baseClass.waitForElement(keyWord.getManageBtn());
		keyWord.getManageBtn().waitAndClick(5);
		
		if (keyWord.getKeywordBtn().isElementAvailable(5)) {
			baseClass.failedStep("KeywordHighlightning Button is present");
		}else {
			baseClass.passedStep("Sys Admin doesn't have access to create keyword group and keywords.");
		}
		loginPage.logout();
		
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		
		// Keyword cannot be creted
		softAssert = new SoftAssert();
		if (keyWord.getManageBtn().isElementAvailable(5)) {
			baseClass.failedStep("KeywordHighlightning Button is present");
		}else {
			baseClass.passedStep("Reviewer doesn't have access to create keyword group and keywords.");
		}
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
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}
