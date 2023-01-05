package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
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
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class RedactionTags_Phase2_Regression {

	
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
	 * @author Krishna Date:  Modified date:N/A Modified by: 
	 * To verify user without Manage Privileges can not create Redaction tag
	 * 53627
	 */
	@Test(description = "RPMXCON-53627", enabled = true, groups = { "regression" })
	public void verifyNoRedactionTagAccess() throws Exception {
		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		int j;
		int i;
		baseClass.stepInfo("Test case Id: RPMXCON-53627");
		baseClass.stepInfo("To verify user without Manage Privileges can not create Redaction tag");

		// Verifying as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		List<String> menuOptions1 = new ArrayList<String>();
		List<WebElement> Options = tagAndFolderPage.saMenuItems().FindWebElements();
		for (j = 0; j < Options.size(); j++) {
			menuOptions1.add(Options.get(j).getAttribute("name"));

		}
		System.out.println(menuOptions1);
		if (menuOptions1.toString().contains("Redaction Tags")) {

			baseClass.failedStep("SA user has redaction Tags change option");
		} else {
			baseClass.passedStep("SA user can not make changes to Redaction Tags");
		}

		loginPage.logout();

		// Verifying as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		List<String> menuOptions2 = new ArrayList<String>();
		List<WebElement> Options1 = tagAndFolderPage.leftMenuItems().FindWebElements();
		for (i = 0; i < Options1.size(); i++) {
			menuOptions2.add(Options1.get(i).getAttribute("title"));

		}
		System.out.println(menuOptions2);
		if (menuOptions2.toString().contains("Redaction Tags")) {

			baseClass.failedStep("Rev user has redaction Tags change option");
		} else {
			baseClass.passedStep("Rev user can not make changes to Redaction Tags");
		}
		loginPage.logout();
	}
	
	@DataProvider(name = "userDetails3")
	public Object[][] userLoginDetails3() {
		return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.pa1FullName, Input.pa1userName, Input.pa1password } };
	}

	/**
	 * @author Krishna Date:  Modified date:N/A Modified by:
	 * To verify user with Manage Privileges
	 * 53626
	 */
	@Test(description = "RPMXCON-53626", enabled = true, groups = { "regression" }, dataProvider = "userDetails3")
	public void verifyRedactionTagAccess(String fullName, String userName, String password) throws Exception {
		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		int i;
		baseClass.stepInfo("Test case Id: RPMXCON-53626");
		baseClass.stepInfo("To verify user with Manage Privileges");
		loginPage.loginToSightLine(userName, password);
		List<String> menuOptions2 = new ArrayList<String>();
		List<WebElement> Options1 = tagAndFolderPage.leftMenuItems().FindWebElements();
		for (i = 0; i < Options1.size(); i++) {
			menuOptions2.add(Options1.get(i).getAttribute("name"));

		}
		System.out.println(menuOptions2);
		if (menuOptions2.toString().contains("Redaction")) {

			baseClass.passedStep("RMU user has redaction Tags change option");
		} else {
			baseClass.failedStep("Rev user can not make changes to Redaction Tags");
		}

// Verifying error message for existing tag
		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.navigateToRedactionsPageURL();
		redactionpage.createRedactionTagWithExistingNameAndVerifyErrorMessage("Default Redaction Tag");
		driver.waitForPageToBeReady();

		if (redactionpage.redactionTagErrorMsg().isDisplayed()) {
			String errorMessage = redactionpage.redactionTagErrorMsg().getText();
			System.out.println(errorMessage);
			if (errorMessage.contains("object with same name already exists")) {
				baseClass.passedStep("Redaction with same name is not saved");
			}
		} else {
			baseClass.failedStep("Redaction with same name is saved");
		}

// Verifying error message for empty string 
		redactionpage.navigateToRedactionsPageURL();
		redactionpage.createRedactionTagWithExistingNameAndVerifyErrorMessage("");
		driver.waitForPageToBeReady();

		if (redactionpage.redactionTagErrorMsg().isDisplayed()) {
			String errorMessage = redactionpage.redactionTagErrorMsg().getText();
			System.out.println(errorMessage);
			if (errorMessage.contains("Please Enter Name")) {
				baseClass.passedStep("Redaction with no name is not saved");
			}
		} else {
			baseClass.failedStep("Redaction with no name is saved");
		}

// Verifying error message for special char
		redactionpage.navigateToRedactionsPageURL();
		redactionpage.createRedactionTagWithExistingNameAndVerifyErrorMessage("*#@");
		driver.waitForPageToBeReady();

		if (redactionpage.redactionTagErrorMsg().isDisplayed()) {
			String errorMessage = redactionpage.redactionTagErrorMsg().getText();
			System.out.println(errorMessage);
			if (errorMessage.contains("Special characters are not allowed")) {
				baseClass.passedStep("Redaction with no name is not saved");
			}
		} else {
			baseClass.failedStep("Redaction with no name is saved");
		}

		loginPage.logout();
	}
	
	

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Redaction Tags EXECUTED******");

	}



}
