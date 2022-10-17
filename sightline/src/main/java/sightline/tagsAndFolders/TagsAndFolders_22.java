package sightline.tagsAndFolders;

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
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TagsAndFolders_22 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;
	ProductionPage page;
	SessionSearch sessionSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		base = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();

	}

	/**
	 * @author NA Testcase No:RPMXCON-53437
	 * @Description:Verify contentual help text from Create Folder pop up for the
	 *                     'Propagate Tag To'
	 **/
	@Test(description = "RPMXCON-53437", enabled = true, groups = { "regression" })
	public void verifyingPropagatePopupInTags() throws Exception {
		base = new BaseClass(driver);
		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);

		base.stepInfo("RPMXCON-53437 in Tags And Folders");
		base.stepInfo("Verify contentual help text from Create Folder pop up for the 'Propagate Tag To'");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Navigating to tags and folder page");
		tf.navigateToTagsAndFolderPage();

		base.stepInfo("verifying the propagate popup text in tags");
		tf.verifyingPopupInTags(Input.securityGroup);

		loginPage.logout();
	}

	/**
	 * @author Bruundha Testcase No:RPMXCON-52491
	 * @Description:To verify Tag and Folder group is displayed at project level.
	 **/
	@Test(description = "RPMXCON-52491", enabled = true, groups = { "regression" })
	public void verifyingTagsAndFoldersAllTags() throws Exception {
		base = new BaseClass(driver);
		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);

		String tagGroupName = "Tg" + Utility.dynamicNameAppender();
		String tagGroupName1 = "Tg" + Utility.dynamicNameAppender();
		String tagGroupName2 = "Tg" + Utility.dynamicNameAppender();
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String securityGroup1 = "SG" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXCON-52491 in Tags And Folders");
		base.stepInfo("To verify Tag and Folder group is displayed at project level.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Creating a security group");
		sg.createSecurityGroups(securityGroup);
		System.out.println(securityGroup);

		base.stepInfo("Navigating to tags and folder page");
		tf.navigateToTagsAndFolderPage();

		base.stepInfo("Creating new tag group and verifying all tags group");
		tf.CreatingNewTagGroup(Input.securityGroup, tagGroupName);
		base.CloseSuccessMsgpopup();
		tf.CreatingNewTagGroup(securityGroup, tagGroupName1);
		tf.verifyingAllTagsGroup(tagGroupName1);

		base.stepInfo("Switching to another project and verifying all tags group");
		base.switchProject(Input.projectName);
		driver.waitForPageToBeReady();
		
		base.stepInfo("Creating a security group");
		sg.createSecurityGroups(securityGroup1);
		System.out.println(securityGroup1);
		
		tf.navigateToTagsAndFolderPage();
		tf.CreatingNewTagGroup(securityGroup1, tagGroupName2);
		tf.verifyingAllTagsGroup(tagGroupName2);
		
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
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
		System.out.println("******TEST CASES FOR Tags And Folders EXECUTED******");

	}
}
