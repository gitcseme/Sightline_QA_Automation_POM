package sightline.smallComponents;

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
import pageFactory.CommunicationExplorerPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ReportEngine_Phase2_Regression {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Input ip;
	Utility utility;
	SoftAssert soft;
	UserManagement user;
	SessionSearch session;
	CommunicationExplorerPage communicationExpPage;

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
		loginPage = new LoginPage(driver);
		soft = new SoftAssert();
		user = new UserManagement(driver);
	}
	
	
	/**
	 * @author Jayanthi.Ganesan Verify the Communication map shows expected document
	 *         count when source is more than 1 'Folders'.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-56924", enabled = true, groups = { "regression" })
	public void verify() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-56924");
		base.stepInfo(
				"Verify the Communication map shows expected document count when source is  more than 1 'Folders'. ");
		// Login as RMU user
		base.stepInfo("**Step-1 & 2 -Login to sightline and Select Project**");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String folderName = "comExp" + Utility.dynamicNameAppender();
		String folderName2 = "comExp" + Utility.dynamicNameAppender();

		base.stepInfo("**Pre-Requesites  Folders Creation**");
		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		base.stepInfo(" Doing a basic content search and bulk assign the docs to assignment.");
		session.basicContentSearch(Input.searchString1);
		session.bulkFolder(folderName);
		base.passedStep("Folder Created and bulk action done Sucessfully " + folderName);

		base.selectproject();
		session.basicContentSearch(Input.searchString2);
		session.bulkFolder(folderName2);
		base.passedStep("Folder Created and bulk action done Sucessfully " + folderName2);
		base.selectproject();

		// select folder in WP to get expected doc count to be displayed communication
		// explorer
		session.switchToWorkproduct();
		session.workProductSearch("folder", folderName, false);
		session.selectOperator("AND");
		session.workProductSearch("folder", folderName2, false);
		base.stepInfo("Configured Seach query under work product tab  with two folders and AND operator .");
		int purehit = session.saveAndReturnPureHitCount();

		base.stepInfo("Search result is displayed[query:" + folderName + " AND " + folderName2
				+ " ]and purehit is : " + purehit);

		communicationExpPage.navigateToCommunicationExpPage();
		driver.waitForPageToBeReady();
		communicationExpPage.SelectSource_Folder(folderName, folderName2);
		communicationExpPage.clickApplyBtn();
		//
		int docCount_displayed = Integer.parseInt(communicationExpPage.VerifyTaggedDocsCountDisplay());
		base.stepInfo("Doc Count displayed after selecting two folders as source in communication exp report is"
				+ String.valueOf(docCount_displayed));
		if (docCount_displayed <= purehit) {
			base.passedStep("The report generated with expected count of Folders " + "containing documents ");
		} else {
			base.failedStep("The report  generated is not  with expected count of Folders containing documents");
		}
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tf.deleteAllFolders("comExp");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify Communication report should work for folders created in
	 *              this session as well as for pre-existing folders.
	 *              [RPMXCON-56923]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-56923", enabled = true, groups = { "regression" })
	public void verifyCommunicationReportWorkForFolder() throws Exception {
		String folderName = "FOLDER" + utility.dynamicNameAppender();

		// Login as a PA User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged In As : PA");

		base.stepInfo("Test case Id:RPMXCON-56923 Report Engine");
		base.stepInfo(
				"Verify Communication report should work for folders created in this session as well as for pre-existing folders.");

		//Configure EmailDomain query & bulk folder 
		int purehit = session.basicMetaDataSearch(Input.emailAllDomain, null, Input.domainNameConsilio, null);
		session.bulkFolder(folderName);

		//Navigate to Communication Page & select source as FOLDER
		communicationExpPage.navigateToCommunicationExpPage();
		communicationExpPage.SelectSource_Folder(folderName, null);
		
		//verify report give same document count as present in selected folders.
		communicationExpPage.verifyDocCountDisplayed(true, purehit);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify the role drop down for when Domain admin edits the
	 *              other User should not display system admin option.
	 *              [RPMXCON-56926]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-56926", enabled = true, groups = { "regression" })
	public void verifySAOptionIsNotPresentInDD() throws Exception {

		// Login as a DA User
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Logged In As : DA");

		base.stepInfo("Test case Id:RPMXCON-56926 Batch Redaction");
		base.stepInfo(
				"Verify the role drop down for when Domain admin edits the other User should not display system admin option.");

		//Click edit for user
		user.navigateToUsersPAge();
		user.filterByName(Input.pa1userName);
		user.selectEditUserUsingPagination(Input.projectName, false, "");
		
		//verify SA role is not present in dropdown
		boolean status = user.verifyRoleDD(Input.SystemAdministrator);
		String passMsg = Input.SystemAdministrator + " is not present in Role Dropdown";
		String failMsg = Input.SystemAdministrator + " is present in dropdown";
		base.printResutInReport(status, passMsg, failMsg, "Fail");

		// logout
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate Project Admin is not allowed to impersonate to
	 *              Domain/System Admin while impersonating to RMU/Reviewer
	 *              [RPMXCON-56937]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-56937", enabled = true, groups = { "regression" })
	public void verifyPaIsNotAllowedToImpDaOrSa() throws Exception {

		// Login as a PA User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged In As : PA");

		base.stepInfo("Test case Id:RPMXCON-56937 Report Engine");
		base.stepInfo(
				"Validate Project Admin is not allowed to impersonate to Domain/System Admin while impersonating to RMU/Reviewer");

		// click change role and open impersonate popup
		base.openImpersonateTab();

		// verify SA/DA role is not present in dropdown
		boolean saStatus = user.verifyRoleDD(Input.SystemAdministrator);
		boolean daStatus = base.getSelectRole(Input.DomainAdministrator).isElementAvailable(2);

		String passMsg1 = Input.SystemAdministrator + " is not present in Role Dropdown";
		String failMsg1 = Input.SystemAdministrator + " is present in dropdown";
		base.printResutInReport(saStatus, passMsg1, failMsg1, "Fail");

		String passMsg2 = Input.DomainAdministrator + " is not present in Role Dropdown";
		String failMsg2 = Input.DomainAdministrator + " is present in dropdown";
		base.printResutInReport(daStatus, passMsg2, failMsg2, "Fail");

		// Imoersonate to RMU
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		base.rolesToImp("PA", "RMU");

		// click change role and open impersonate popup
		base.openImpersonateTab();

		// verify SA/DA role is not present in dropdown
		boolean saStatusrev = user.verifyRoleDD(Input.SystemAdministrator);
		boolean da1Statusrev = base.getSelectRole(Input.DomainAdministrator).isElementAvailable(2);

		String passMsg1rev = Input.SystemAdministrator + " is not present in Role Dropdown";
		String failMsg1rev = Input.SystemAdministrator + " is present in dropdown";
		base.printResutInReport(saStatusrev, passMsg1rev, failMsg1rev, "Fail");

		String passMsg2rev = Input.DomainAdministrator + " is not present in Role Dropdown";
		String failMsg2rev = Input.DomainAdministrator + " is present in dropdown";
		base.printResutInReport(da1Statusrev, passMsg2rev, failMsg2rev, "Fail");

		// Impersonate to REV
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		base.rolesToImp("RMU", "REV");

		//select different project
		base.selectproject(Input.largeVolDataProject);
		
		//verify Current project after changing project
		base.verifyCurrentProject(Input.largeVolDataProject);

		// verify Current user role after changing project
		base.verifyCurrentRole(Input.Reviewer);

		// logout
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility base = new Utility(driver);
			base.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Reports Communication EXECUTED******");

	}
}
