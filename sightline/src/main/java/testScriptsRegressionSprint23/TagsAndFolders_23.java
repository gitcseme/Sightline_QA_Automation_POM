package testScriptsRegressionSprint23;

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
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TagsAndFolders_23 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	SoftAssert softAssertion;
	String tagname;

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
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		base = new BaseClass(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
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
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-53280
	 * @Description:Verify that if RMU delete the Tag which is assigned to multiple SG, "
				+ "it should deleted only from the current selected SG only
	 **/
	@Test(description = "RPMXCON-53280", enabled = true, groups = { "regression" })
	public void verifyRMUTagDeleteMultipleSG() throws Exception {
		
		String sgName1 = "Production_Security_Group" + Utility.dynamicNameAppender();
		String sgName2 = "Production_Security_Group" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		base.stepInfo("RPMXCON-53280");
		base.stepInfo("Verify that if RMU delete the Tag which is assigned to multiple SG, "
				+ "it should deleted only from the current selected SG only");
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);	
		// create tag 	
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		SecurityGroupsPage sgPage = new SecurityGroupsPage(driver);
		sgPage.navigateToSecurityGropusPageURL();
		sgPage.AddSecurityGroup(sgName1);
		base = new BaseClass(driver);
		base.VerifySuccessMessage("Security group added successfully");
		
		driver.waitForPageToBeReady();
		
		sgPage.AddSecurityGroup(sgName2);
		base.VerifySuccessMessage("Security group added successfully");
		
		UserManagement userManage = new UserManagement(driver);
		userManage.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		userManage.assignAccessToSecurityGroups(sgName1, Input.rmu1userName);
		userManage.assignAccessToSecurityGroups(sgName2, Input.rmu1userName);	
		
		sgPage.navigateToSecurityGropusPageURL();
		driver.waitForPageToBeReady();
		sgPage.addTagToSecurityGroup(sgName1, tagname);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		sgPage.addTagToSecurityGroup(sgName2, tagname);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base = new BaseClass(driver);
		base.selectsecuritygroup(Input.securityGroup);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		base.selectsecuritygroup(Input.securityGroup);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.verifyNodeNotPresent(tagname, "Tag :" + tagname + "Not Present", "Tag :" + tagname + "Present");
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.verifyTagDocCount(tagname, 0);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base = new BaseClass(driver);
		base.selectsecuritygroup(sgName1);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.verifyTagDocCount(tagname, 0);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base = new BaseClass(driver);
		base.selectsecuritygroup(sgName2);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.verifyTagDocCount(tagname, 0);
		base.stepInfo("Verified that if RMU delete the Tag which is assigned to multiple SG, "
				+ "it should deleted only from the current selected SG only");
		loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-53281
	 * @Description:Verify that if RMU delete the Folder which is assigned to multiple SG, "
				+ "it should deleted only from the current selected SG only
	 **/
	@Test(description = "RPMXCON-53281", enabled = true, groups = { "regression" })
	public void verifyRMUFolderDeleteMultipleSG() throws Exception {
		String sgName1 = "Production_Security_Group" + Utility.dynamicNameAppender();
		String sgName2 = "Production_Security_Group" + Utility.dynamicNameAppender();
		 String folderName = "Folder" + Utility.dynamicNameAppender();
		base.stepInfo("RPMXCON-53281");
		base.stepInfo("Verify that if RMU delete the Folder which is assigned to multiple SG, "
				+ "it should deleted only from the current selected SG only");
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);	
		// create tag 	
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolderInRMU(folderName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		SecurityGroupsPage sgPage = new SecurityGroupsPage(driver);
		sgPage.navigateToSecurityGropusPageURL();
		sgPage.AddSecurityGroup(sgName1);
		base = new BaseClass(driver);
		base.VerifySuccessMessage("Security group added successfully");
		sgPage.AddSecurityGroup(sgName2);
		base.VerifySuccessMessage("Security group added successfully");
		
		UserManagement userManage = new UserManagement(driver);
		userManage.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		userManage.assignAccessToSecurityGroups(sgName1, Input.rmu1userName);
		userManage.assignAccessToSecurityGroups(sgName2, Input.rmu1userName);	
		
		sgPage.navigateToSecurityGropusPageURL();
		driver.waitForPageToBeReady();
		sgPage.addFolderToSecurityGroup(sgName1, folderName);
		
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
	
		sgPage.addFolderToSecurityGroup(sgName2, folderName);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base = new BaseClass(driver);
		base.selectsecuritygroup(Input.securityGroup);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(folderName);
		
		base.selectsecuritygroup(Input.securityGroup);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.verifyNodeNotPresent(folderName, "Folder :" + folderName + "Not Present", "Folder :" + folderName + "Present");
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		
		tagsAndFolderPage.verifyFolderDocCount(folderName, 0);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base = new BaseClass(driver);
		base.selectsecuritygroup(sgName1);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		
		tagsAndFolderPage.verifyFolderDocCount(folderName, 0);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base = new BaseClass(driver);
		base.selectsecuritygroup(sgName2);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		
		tagsAndFolderPage.verifyFolderDocCount(folderName, 0);
		base.stepInfo("Verified that if RMU delete the Folder which is assigned to multiple SG, "
				+ "it should deleted only from the current selected SG only");
		loginPage.logout();
	
	}

	/**
	 * @author Sowndarya Testcase No:RPMXCON-53192
	 * @Description:To verify that if Tag contains Zero document and select action 'View in Doc List', message should be displayed 'Your query returned no data'
	 **/
	@Test(description = "53192", enabled = true, groups = { "regression" })
	public void verifyMessagePopupWithZeroDocTag() throws Exception {
		
		tagname = "Tag" + Utility.dynamicNameAppender();
		base.stepInfo("RPMXCON-53280");
		base.stepInfo("To verify that if Tag contains Zero document and select action 'View in Doc List', message should be displayed 'Your query returned no data'");
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);	
		// create tag 	
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		driver.waitForPageToBeReady();
		
		String selectedTag = tagsAndFolderPage.getCreatedTag(tagname).getText();
		System.out.println(selectedTag +"is selected.");
		
		base.waitForElement(tagsAndFolderPage.getCreatedTag("Tag4023903"));
		tagsAndFolderPage.getCreatedTag("Tag4023903").waitAndClick(10);
		
		driver.scrollPageToTop();
		
		base.waitForElement(tagsAndFolderPage.getTagActionDropDownArrow());
		tagsAndFolderPage.getTagActionDropDownArrow().waitAndClick(10);
		base.waitForElement(tagsAndFolderPage.getTagViewDoclist());
		tagsAndFolderPage.getTagViewDoclist().waitAndClick(10);
		String expected="There are NO documents in the tags or folders that you have selected";
		base.VerifyWarningMessage(expected);
		loginPage.logout();
		
	
}
}