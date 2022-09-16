package testScriptsRegressionSprint21;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SecurityGroup_Regression21_2 {
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
	 * @author:Iyappan.Kasinathan date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                   RPMXCON-54808
	 * @throws Exception
	 * @Description Verify that when SysAdmin impersonate as DAU,clicking on any
	 *              project , should take to the Default SG in the project
	 */
	@Test(description = "RPMXCON-54808", enabled = true, groups = { "regression" })
	public void verifySAImpersonateAsDAUTakeDsgInTheProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54808");
		baseClass.stepInfo(
				"Verify that when SysAdmin impersonate as DAU,clicking on any  project , should take to the Default SG in the project");
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login as PA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.sa1userName + "");
		baseClass.stepInfo("Impersonate SA to DA");
		baseClass.impersonateSAtoDA();

		// go to project
		baseClass.stepInfo("Click on any project from dashboard");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		// verify Default security group in selected project
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("Default SG in the selected project is displayed successfully");
		} else {
			baseClass.failedStep("Default SG in the selected project is not display");
		}
		loginPage.logout();
	}

	/**
	 * @author:Iyappan.Kasinathan date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                   RPMXCON-54807
	 * @throws Exception
	 * @Description Verify that in Domain Dashboard page, clicking on project from
	 *              'Active Projects Utilization' widgets, should take the DAU to
	 *              Default SG in the project
	 */
	@Test(description = "RPMXCON-54807", enabled = true, groups = { "regression" })
	public void verifyDomainDashBoardPageClickedOnProjectWidgetsTakeDAUDsg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54807");
		baseClass.stepInfo(
				"Verify that in Domain Dashboard page, clicking on project from 'Active Projects Utilization' widgets, should take the DAU to Default SG in the project");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);

		// Login as DAU
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.da1userName + "");
		baseClass.selectdomain(Input.domainName);
		WebElement mytable1 = sgpage.daSecondBlock().getWebElement();
		List<WebElement> rows_table = mytable1.findElements(By.tagName("tr"));

		// To calculate no of rows In table.
		int rows_count1 = rows_table.size();
		System.out.println(rows_count1);
		baseClass.waitTillElemetToBeClickable(sgpage.daSecondTabledata(rows_count1 - 1));
		sgpage.daSecondTabledata(rows_count1 - 1).waitAndClick(5);
		baseClass.stepInfo("Project cliked on from 'Active Projects utilization' widget successfully ");

		// verify Default security group in selected project
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("Redirect to Default SG in the selected project is displayed successfully");
		} else {
			baseClass.failedStep("Default SG in the selected project is not display");
		}
		loginPage.logout();
	}

	/**
	 * @author:Iyappan.Kasinathan date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                   RPMXCON-54789
	 * @throws Exception
	 * @Description Verify that when RMU changes the Project from header drop
	 *              down,it should take to Default SG of the selected project
	 */
	@Test(description = "RPMXCON-54789", enabled = true, groups = { "regression" })
	public void verifyRMUChangesProjectTakeDsgSelectedProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54789");
		baseClass.stepInfo(
				"Verify that when RMU changes the Project from header drop down,it should take to Default SG of the selected project");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		baseClass.selectproject();
		baseClass.stepInfo("Project selected to Rmu header dropdown");
		DomainDashboard domainDash = new DomainDashboard(driver);
        
		// verify RMU home page
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify Reviewer manager Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" User navigated to Reviewer manager home page is displayed in selected project successfully");
		} else {
			baseClass.failedStep("Reviewer home page is not displayed ");
		}
		loginPage.logout();

	}

	/**
	 * @author:Iyappan.Kasinathan date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                   RPMXCON-54788
	 * @throws Exception
	 * @Description Verify that if PAU changes the Project from header drop down
	 *              then it should take the corresponding project as a PAU only
	 */
	@Test(description = "RPMXCON-54788", enabled = true, groups = { "regression" })
	public void verifyPAUChangesProjectHeaderFropDownTakeCorrespondingProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54788");
		baseClass.stepInfo(
				"Verify that if PAU changes the Project from header drop down then it should take the corresponding project as a PAU only");
		DataSets data = new DataSets(driver);

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject();
		baseClass.stepInfo("Project selected to PA header dropdown");
        
		// verify PAU home page
		driver.waitForPageToBeReady();
		if (data.getDatasetBtn().isDisplayed()) {
			baseClass.passedStep(" User redirect to PA home page is displayed in selected project successfully");
		} else {
			baseClass.failedStep("verification failed");
		}
		loginPage.logout();

	}
	

	/**
	 * @author:Iyappan.Kasinathan date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                   RPMXCON-54778
	 * @throws Exception
	 * @Description Verify that if DAU user impersonate as RMU,and change the
	 *              Project from header drop down should take to Default SG in the
	 *              selected project
	 */
	@Test(description = "RPMXCON-54778", enabled = true, groups = { "regression" })
	public void verifyDAUImpersonateRmuChangeProjectDsgSelectProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54778");
		baseClass.stepInfo(
				"Verify that if DAU user impersonate as RMU,and change the Project from header drop down should take to Default SG in the selected project");
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login as DAU
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		baseClass.impersonateDAtoRMU();
		baseClass.selectproject();
       
		// verify 
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify Reviewer manager Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep("Redirect to Reviewer manager home page is displayed in selected project successfully");
		} else {
			baseClass.failedStep("Reviewer home page is not displayed ");
		}
		loginPage.logout();

	}

	/**
	 * @author:Iyappan.Kasinathan date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                   RPMXCON-54779
	 * @throws Exception
	 * @Description Verify thatif DAU user impersonate as RMU,and changes the SG in
	 *              SG Dropdown then it should take the corresponding SG in the same
	 *              project
	 */
	@Test(description = "RPMXCON-54779", enabled = true, groups = { "regression" })
	public void verifyDAUUserImpersonateRmuChangesSgInSameProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54779");
		baseClass.stepInfo(
				"Verify that if DAU user impersonate as RMU,and change the Project from header drop down should take to Default SG in the selected project");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		String sgName = "Security Group1_" + UtilityLog.dynamicNameAppender();
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		DocViewMetaDataPage docViewMetaData = new DocViewMetaDataPage(driver);

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Create security groups and assign");
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(sgName);
		docViewRedact.assignAccesstoSGs(sgName, Input.rmu1userName);
		loginPage.logout();

		// Login as DAU
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		baseClass.impersonateDAtoRMU();

		// Verify corresponding SG
		driver.waitForPageToBeReady();
		docViewMetaData.getSecurityGroupDropDownButton().waitAndClick(3);
		baseClass.waitForElement(docViewMetaData.getSecurityGroup(sgName));
		if (docViewMetaData.getSecurityGroup(sgName).isElementPresent()) {
			baseClass.passedStep(sgName + ".. is successfully displayed in selected project");
		} else {
			baseClass.failedStep("Sg is not displayed");

		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sgpage.deleteSecurityGroups(sgName);
		loginPage.logout();
	}
	
	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                   RPMXCON-54787
	 * @throws Exception
	 * @Description Verify that when PAU impersonate as RMU,and changes the SG in SG
	 *              Dropdown then it should take the corresponding SG in the same
	 *              project
	 */
	@Test(description = "RPMXCON-54787", enabled = true, groups = { "regression" })
	public void verifyPAUUserImpersonateRmuChangesSgTakeCorrospondingSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54787");
		baseClass.stepInfo(
				"Verify that when PAU impersonate as RMU,and changes the SG in SG Dropdown then it should take the corresponding SG in the same project");
		DocViewMetaDataPage docViewMetaData = new DocViewMetaDataPage(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		String sgName = "Security Group1_" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Create security groups and assign");
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(sgName);
		docViewRedact.assignAccesstoSGs(sgName, Input.rmu1userName);
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		baseClass.impersonatePAtoRMU();

		// verify RMU home page
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify Reviewer manager Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass
					.passedStep("Redirect to Reviewer manager home page is displayed in selected project successfully");
		} else {
			baseClass.failedStep("Reviewer home page is not displayed ");
		}

		// verify corresponding SG
		driver.waitForPageToBeReady();
		docViewMetaData.getSecurityGroupDropDownButton().waitAndClick(3);
		baseClass.waitForElement(docViewMetaData.getSecurityGroup(sgName));
		if (docViewMetaData.getSecurityGroup(sgName).isElementPresent()) {
			baseClass.passedStep("Corresponding SG in the same project is displayed successfuly");
		} else {
			baseClass.failedStep("Sg is not displayed");

		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sgpage.deleteSecurityGroups(sgName);
		loginPage.logout();
	}

	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                   RPMXCON-54319
	 * @throws Exception
	 * @Description To verify that if user select SG level attribute and click on
	 *              Save, "belly band"message should be displayed
	 */
	@Test(description = "RPMXCON-54319", enabled = true, groups = { "regression" })
	public void verifyBellyBandWarningMsgDisplayClickingRegenrateBtn() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54319");
		baseClass.stepInfo(
				"To verify that if user select SG level attribute and click on Save, \"belly band\"message should be displayed");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		SoftAssert softassert = new SoftAssert();
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		sgpage.createSecurityGroups(SGname);
		baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(2));
		sgpage.getSG_GenerateEmailRadioButton(2).waitAndClick(5);
		baseClass.stepInfo("Use Security Group-Specific Email inclusive check mark is selected");
		baseClass.waitForElement(sgpage.getSG_AnnSaveButton());
		sgpage.getSG_AnnSaveButton().waitAndClick(5);
		driver.waitForPageToBeReady();

		// verify warning message
		String errormsg = "Use Security Group-Specific Email Inclusive and Email Duplicate data";
		baseClass.waitForElement(sgpage.getVerifySG_EmailGenerateWarningMsg());
		String Actualwarningmsg = sgpage.getVerifySG_EmailGenerateWarningMsg().getText();
		baseClass.stepInfo("Actual Warning message...." + Actualwarningmsg);
		String expectWarningmsg = sgpage.getVerifySG_EmailGenerateWarningMsgText(errormsg).getText();
		baseClass.stepInfo("Expected warning message..." + expectWarningmsg);
		softassert.assertEquals(Actualwarningmsg, expectWarningmsg);
		driver.waitForPageToBeReady();
		if (Actualwarningmsg.contains(expectWarningmsg)) {
			baseClass.passedStep("Warning message is displayed successfully");
		} else {
			baseClass.failedStep("Warning msg is not displayed");
		}
		softassert.assertAll();
		sgpage.deleteSecurityGroups(SGname);
	}

	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                   RPMXCON-54320
	 * @throws Exception
	 * @Description To verify that when PA, unrelease any documents from a Security
	 *              Group then belly band" reminder should be displayed
	 */
	@Test(description = "RPMXCON-54320", enabled = true, groups = { "regression" })
	public void verifyPAUnReleaseDocsFromSgBellyBandMsgDisplayed() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54320");
		baseClass.stepInfo(
				"To verify that when PA, unrelease any documents from a Security Group then belly band\" reminder should be displayed");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		SoftAssert softassert = new SoftAssert();
		SessionSearch sessionsearch = new SessionSearch(driver);
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		sgpage.createSecurityGroups(SGname);
		System.out.println(SGname);

		// Release the doc to security group
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(SGname);
		driver.waitForPageToBeReady();
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.waitForElement(sgpage.getSelectSecurityGroup());
		sgpage.getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(SGname);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(1));
		sgpage.getSG_GenerateEmailRadioButton(2).waitAndClick(3);
		baseClass.waitForElement(sgpage.getSG_GenerateEmailButton());
		sgpage.getSG_GenerateEmailButton().waitAndClick(3);
		baseClass.passedStep("Generate button is clicked successfully");
		baseClass.waitForElement(sgpage.getYesButton());
		sgpage.getYesButton().waitAndClick(2);

		// verify warning message displayed
		driver.waitForPageToBeReady();
		sessionsearch.navigateToSessionSearchPageURL();
		driver.scrollPageToTop();
		baseClass.waitForElement(sessionsearch.getBulkActionButton());
		sessionsearch.getBulkActionButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionsearch.getBulkReleaseActionDL());
		sessionsearch.getBulkReleaseActionDL().waitAndClick(10);
		baseClass.waitForElement(sessionsearch.getBulkRelDefaultSecurityGroup_CheckBox(SGname));
		sessionsearch.getBulkRelDefaultSecurityGroup_CheckBox(SGname).waitAndClick(10);
		baseClass.waitForElement(sessionsearch.getBulkRelease_ButtonRelease());
		sessionsearch.getBulkRelease_ButtonRelease().waitAndClick(20);

		String errormsg = "Please be aware that the Security Group is set to use Security Group-specific attributes for email inclusiveness and email duplicate analysis";
		baseClass.waitForElement(sgpage.getSG_EmailGenerateWarningMsgBulkRelease());
		String Actualwarningmsg = sgpage.getSG_EmailGenerateWarningMsgBulkRelease().getText();
		baseClass.stepInfo("Actual Warning message...." + Actualwarningmsg);
		String expectWarningmsg = sgpage.getSG_EmailGenerateWarningMsgBulkReleaseText(errormsg).getText();
		baseClass.stepInfo("Expected warning message..." + expectWarningmsg);
		softassert.assertEquals(Actualwarningmsg, expectWarningmsg);
		driver.waitForPageToBeReady();
		if (Actualwarningmsg.contains(expectWarningmsg)) {
			baseClass.passedStep("Warning message is displayed successfully");
		} else {
			baseClass.failedStep("Warning msg is not displayed");
		}
		softassert.assertAll();
		sgpage.deleteSecurityGroups(SGname);

	}

	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                   RPMXCON-54310
	 * @throws Exception
	 * @Description To verify that if Security Group is at SG level attribute and if
	 *              PA release documents to Security Group then belly band" message
	 *              should be displayed.
	 */
	@Test(description = "RPMXCON-54310", enabled = true, groups = { "regression" })
	public void verifyPAReleaseDocsFromSgBellyBandMsgDisplayed() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54310");
		baseClass.stepInfo(
				"To verify that if Security Group is at SG level attribute and if  PA release documents to  Security Group then belly band\" message should be displayed.");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		SoftAssert softassert = new SoftAssert();
		SessionSearch sessionsearch = new SessionSearch(driver);
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		String searchname = "New" + UtilityLog.dynamicNameAppender();
		SavedSearch saveSearch = new SavedSearch(driver);

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");
		sgpage.createSecurityGroups(SGname);
		System.out.println(SGname);
		baseClass.CloseSuccessMsgpopup();
		baseClass.waitForElement(sgpage.getSG_GenerateEmailRadioButton(1));
		sgpage.getSG_GenerateEmailRadioButton(2).waitAndClick(3);
		baseClass.waitForElement(sgpage.getSG_GenerateEmailButton());
		sgpage.getSG_GenerateEmailButton().waitAndClick(3);
		baseClass.passedStep("Generate button is clicked successfully");
		baseClass.waitForElement(sgpage.getYesButton());
		sgpage.getYesButton().waitAndClick(2);

		// basic Saved search
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.saveSearch(searchname);
		saveSearch.savedSearch_Searchandclick(searchname);
		baseClass.waitForElement(saveSearch.getChooseSearchRadiobtn(searchname));
		saveSearch.getChooseSearchRadiobtn(searchname);
		saveSearch.getReleaseIcon().waitAndClick(3);
		baseClass.waitForElement(saveSearch.getReleaseDocToSG(SGname));
		saveSearch.getReleaseDocToSG(SGname).waitAndClick(5);

		// Click Release Button and verify warning msg
		baseClass.waitForElement(saveSearch.getReleaseBtn());
		saveSearch.getReleaseBtn().waitAndClick(5);
		String errormsg = "Please be aware that the Security Group is set to use Security Group-specific attributes for email inclusiveness and email duplicate analysis";
		baseClass.waitForElement(sgpage.getSG_EmailGenerateWarningMsgBulkRelease());
		String Actualwarningmsg = sgpage.getSG_EmailGenerateWarningMsgBulkRelease().getText();
		baseClass.stepInfo("Actual Warning message...." + Actualwarningmsg);
		String expectWarningmsg = sgpage.getSG_EmailGenerateWarningMsgBulkReleaseText(errormsg).getText();
		baseClass.stepInfo("Expected warning message..." + expectWarningmsg);
		softassert.assertEquals(Actualwarningmsg, expectWarningmsg);
		driver.waitForPageToBeReady();
		if (Actualwarningmsg.contains(expectWarningmsg)) {
			baseClass.passedStep("Warning message is displayed successfully");
		} else {
			baseClass.failedStep("Warning msg is not displayed");
		}
		softassert.assertAll();
		sgpage.deleteSecurityGroups(SGname);
	}
}