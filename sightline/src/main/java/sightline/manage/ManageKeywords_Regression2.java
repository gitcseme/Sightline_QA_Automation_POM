package sightline.manage;

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

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ManageKeywords_Regression2 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	UserManagement userManage;
	KeywordPage keyWord;

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
		keyWord = new KeywordPage(driver);

	}
	
	/**
	 * @author Mohan.Venugopal Created Date:07/07/2022 RPMXCON-52739
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that keyword should accept the wildcard terms while adding
	 */
	@Test(description = "RPMXCON-52739", enabled = true, groups = { "regression" })
	public void verifyKeywordWildCardsTerms() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52739");
		baseClass.stepInfo("Verify that keyword should accept the wildcard terms while adding");
		String color = "Blue";
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		String keywordName = "@hotmail.com"+ Utility.dynamicNameAppender();
		
		
		// Navigate to Keywords Page and create keyword with Wildcard
		keyWord.navigateToKeywordPage();
		keyWord.addKeyword(keywordName, color);
		baseClass.passedStep("Wildcard terms are accepted in the keywords successfully");
		
		loginPage.logout();
		
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		// Navigate to Keywords Page and create keyword with Wildcard
		keyWord.navigateToKeywordPage();
		keyWord.addKeyword(keywordName, color);
		baseClass.passedStep("Wildcard terms are accepted in the keywords successfully");

		loginPage.logout();
	}
	
	
	/**
	 * @author Mohan.Venugopal Created Date:07/07/2022 RPMXCON-52529
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify when Project Admin user deletes keyword group and keywords
	 */
	@Test(description = "RPMXCON-52529", enabled = true, groups = { "regression" })
	public void verifyPACanDeleteKeywords() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52529");
		baseClass.stepInfo("To verify when Project Admin user deletes keyword group and keywords");
		String color = "Blue";
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		String keywordName = "deleteKeyword01"+Utility.dynamicNameAppender();
		
		
		// Navigate to Keywords Page and create keyword with Wildcard
		keyWord.navigateToKeywordPage();
		keyWord.addKeyword(keywordName, color);
		keyWord.deleteKeywordByNameAndCancel(keywordName);
		baseClass.passedStep("Keyword group, keywords is not deleted successfully.");
		
		keyWord.deleteKeywordByName(keywordName);
		baseClass.passedStep("Keyword group, keywords is deleted and success message is displayed.");
		
		loginPage.logout();
		
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		// Navigate to Keywords Page and create keyword with Wildcard
		keyWord.navigateToKeywordPage();
		keyWord.addKeyword(keywordName, color);
		keyWord.deleteKeywordByNameAndCancel(keywordName);
		baseClass.passedStep("Keyword group, keywords is not deleted successfully.");
		
		keyWord.deleteKeywordByName(keywordName);
		baseClass.passedStep("Keyword group, keywords is deleted and success message is displayed.");
		
		loginPage.logout();
	}
	
	
	/**
	 * @author Mohan.Venugopal Created Date:12/07/2022 RPMXCON-53476
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that character limit to add keywords should be increased from 500 to 4000 characters and should be saved successfully
	 */
	@Test(description = "RPMXCON-53476", enabled = true, groups = { "regression" })
	public void verifyCharatersWith4000KeywordGroupName() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-53476");
		baseClass.stepInfo("Verify that character limit to add keywords should be increased from 500 to 4000 characters and should be saved successfully");
		
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		String keyWordName = "AACharacterLimit"+ Utility.dynamicNameAppender();
		String keywordValue = "Case5399"+ Utility.dynamicNameAppender();
		String keyWordNameDescription = "CharacterLimit"+ Utility.randomCharacterAppender(3500);
		String color = "Blue";
		
		
		// Navigate to Keyword Page and create keyword Group more than 500 and within 4000 characters
		keyWord.navigateToKeywordPage();
		keyWord.verifyMoreThan500AndWithin4000CharacterCanBeAddKeyword(keyWordName,keywordValue, keyWordNameDescription, color);
		baseClass.passedStep("Keyword group is created successfully with 4000 characters in keywords Page");
		
		//To clean the created keyword
		keyWord.deleteKeywordByName(keyWordName);
		loginPage.logout();
		
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		// Navigate to Keyword Page and create keyword Group more than 500 and within 4000 characters
		String keyWordName01 = "AACharacterLimit500"+ Utility.dynamicNameAppender();
		keyWord.navigateToKeywordPage();
		keyWord.verifyMoreThan500AndWithin4000CharacterCanBeAddKeyword(keyWordName01,keywordValue, keyWordNameDescription, color);
		baseClass.passedStep("Keyword group is created successfully with 4000 characters in keywords Page");
		
		//To clean the created keyword
		keyWord.deleteKeywordByName(keyWordName);
		loginPage.logout();
	}
	
	
	/**
	 * @author Mohan.Venugopal Created Date:12/07/2022 RPMXCON-52495
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify when user deletes keyword group and keywords
	 */
	@Test(description = "RPMXCON-52495", enabled = true, groups = { "regression" })
	public void verifyDeleteKeywordGroupName4000Characters() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52495");
		baseClass.stepInfo("To verify when user deletes keyword group and keywords");
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		String keyWordName = "AACharacterLimit"+ Utility.dynamicNameAppender();
		String keywordValue = "Case4000"+ Utility.dynamicNameAppender();
		String keyWordNameDescription = "CharacterLimit"+ Utility.randomCharacterAppender(3500);
		String color = "Blue";
		
		
		// Navigate to Keyword Page and create keyword Group more than 500 and within 4000 characters
		keyWord.navigateToKeywordPage();
		keyWord.verifyMoreThan500AndWithin4000CharacterCanBeAddKeyword(keyWordName, keywordValue,keyWordNameDescription, color);
		keyWord.deleteKeywordByNameAndCancel(keyWordName);
		keyWord.deleteKeywordByName(keyWordName);
		baseClass.passedStep("Keyword group with 4000 characters is deleted successfully in keywords Page");
		loginPage.logout();
		
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		String keyWordName01 = "AADeletion"+ Utility.dynamicNameAppender();
		String keywordValue01 = "Case500"+ Utility.dynamicNameAppender();
		// Navigate to Keyword Page and create keyword Group more than 500 and within 4000 characters
		keyWord.navigateToKeywordPage();
		
		keyWord.verifyMoreThan500AndWithin4000CharacterCanBeAddKeyword(keyWordName01, keywordValue01,keyWordNameDescription, color);
		keyWord.deleteKeywordByNameAndCancel(keyWordName01);
		keyWord.deleteKeywordByName(keyWordName01);
		baseClass.passedStep("Keyword group with 4000 characters is deleted successfully in keywords Page");
		loginPage.logout();
	}
	
	/**
	 * @author Mohan.Venugopal Created Date:11/07/2022 RPMXCON-52502
	 * @Description To verify whether user is able to create keyword group, keyword after changing role to RMU from Project Admin by Sys Admin
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */
	@Test(description = "RPMXCON-52502", enabled = true, groups = { "regression" })
	public void verifyUserSACanChangeRoleToRMUFromPA() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52502");
		baseClass.stepInfo("To verify whether user is able to create keyword group, keyword after changing role to RMU from Project Admin by Sys Admin");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		
		//Navigate to Users Page
		UserManagement userManagement = new UserManagement(driver);
		userManagement.editRoleOfAnUserSAForManage(Input.pa2userName, "Project Administrator","1",Input.projectName);
		
		loginPage.logout();
		
		// Login As PA from RMU
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.pa2userName + "");
		
		// Keyword cannot be created
		driver.waitForPageToBeReady();
		if (userManagement.getManageBtn().isDisplayed()) {
			baseClass.failedStep(" User have access to Manage > Keywords page.");
		} else {
			baseClass.passedStep("User doesn't have access to Manage > Keywords page.");
		}
		
		
		loginPage.logout();
		
		
		
		//Re assigning the User to its original username
		// Login As PA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		
		//Navigate to Users Page
		userManagement.editRoleOfAnUserSAForManage(Input.pa2userName, "Review Manager","1",Input.projectName);
		baseClass.stepInfo("User had changed the access to original name");
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
