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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
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

public class ManageKeywords_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;
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
		keyWord.navigateToKeywordPage();
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
		keyWord.navigateToKeywordPage();
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
		keyWord.navigateToKeywordPage();
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
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		//Verify Manage Keyowrd Page for RMU
		keyWord.navigateToKeywordPage();
		
		keyWord.verifyManageKeywordPageRMUAndPA(Input.rmu2userName);
		loginPage.logout();
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		// Verify Manage Keyowrd Page for PA
		keyWord.navigateToKeywordPage();
		keyWord.verifyManageKeywordPageRMUAndPA(Input.pa2userName);
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
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		//Verify Manage Keyowrd Page for RMU
		keyWord.navigateToKeywordPage();
		keyWord.verifyDuplicateKeywordFromList();
		loginPage.logout();
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
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
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		//Verify Manage Keyowrd Page for RMU
		keyWord.navigateToKeywordPage();
		keyWord.editExistigKeywordAndVerifyThem(keywordname);
		loginPage.logout();
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
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
		SoftAssert softAssert = new SoftAssert();
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
		if (keyWord.getManageBtn().isDisplayed()) {
			baseClass.failedStep("KeywordHighlightning Button is present");
		}else {
			baseClass.passedStep("Reviewer doesn't have access to create keyword group and keywords.");
		}
		loginPage.logout();
	}
	
	/**
	 * @author Mohan.Venugopal Created Date:07/07/2022 RPMXCON-52739
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that keyword should accept the wildcard terms while adding
	 */
	@Test(description = "RPMXCON-52739", enabled = true, groups = { "regression" })
	public void verifyKeywordWildCardsTerms() throws InterruptedException, AWTException {
		KeywordPage keyWord = new KeywordPage(driver);
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
		KeywordPage keyWord = new KeywordPage(driver);
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
		keyWord.deleteKeywordByNameAndCancel1(keywordName);
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
		KeywordPage keyWord = new KeywordPage(driver);
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
		KeywordPage keyWord = new KeywordPage(driver);
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
	
	/**
	 * @author Mohan.Venugopal Created Date:11/07/2022 RPMXCON-52539
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify whether user is able to create keyword group, keyword after changing role to RMU from Reviewer by Project Admin
	 */
	
	@Test(description = "RPMXCON-52539", enabled = true, groups = { "regression" })
	public void verifyChangeRoleToRMUFromReviewer() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52539");
		baseClass.stepInfo("To verify whether user is able to create keyword group, keyword after changing role to RMU from Reviewer by Project Admin");
		KeywordPage keyWord = new KeywordPage(driver);
		String keywordname = "PhaseII" + Utility.dynamicNameAppender();
		String color = "Blue";
		// Login As PA
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		
		//Navigate to Users Page
		UserManagement userManagement = new UserManagement(driver);
		userManagement.navigateToUsersPAge();
		
		//Edit role from Reviewer to Rmu by PAU
		userManagement.editRoleFromReviewerToRMU(Input.rev2userName, "Reviewer","1",Input.projectName);
		
		loginPage.logout();
		baseClass.waitTime(5);
		driver.Navigate().refresh();		//Refreshing page is required as role changes are not reflecting immediately
		// Login As RMU from Reviewer
		loginPage.loginToSightLine(Input.rev2userName, Input.rev2password);
		baseClass.selectproject();
		
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rev2userName + "");
		
		keyWord.navigateToKeywordPage();
		keyWord.addKeyword(keywordname, color);
		keyWord.deleteKeywordByName(keywordname);
		keyWord.editExistigKeywordAndVerifyThem(keywordname);
		baseClass.passedStep("User have access to Manage > Keywords page and able to create, modify and delete keywords successfully.");
		loginPage.logout();
		
		//Re assigning the User to its original username
		// Login As PA
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa2userName + "");
		
		//Navigate to Users Page
		userManagement.navigateToUsersPAge();
		userManagement.editRoleForRMUANdPAUsers(Input.rev2userName, "Review Manager",Input.projectName);
		baseClass.stepInfo("User had changed the access to original name");
		loginPage.logout();	
	}
	
	/**
	 * @author Mohan.Venugopal Created Date:11/07/2022 RPMXCON-52536
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify whether user is able to create comment after changing role to Project Admin from RMU by Project Admin
	 */
	@Test(description = "RPMXCON-52536", enabled = true, groups = { "regression" })
	public void verifyChangeRoleToPAFromRMU() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52536");
		baseClass.stepInfo("To verify whether user is able to create comment after changing role to Project Admin from RMU by Project Admin");
		KeywordPage keyWord = new KeywordPage(driver);
		String keywordname = "PhaseII" + Utility.dynamicNameAppender();
		String color = "Blue";
		// Login As PA
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa2userName + "");
		
		//Navigate to Users Page
		UserManagement userManagement = new UserManagement(driver);
		userManagement.navigateToUsersPAge();
		userManagement.editRoleFromPAUToRMU(Input.rmu2userName, "Review Manager","0",Input.projectName);
		
		loginPage.logout();
		baseClass.waitTime(5);
		driver.Navigate().refresh();		//Refreshing page is required as role changes are not reflecting immediately
		// Login As PA from RMU
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.rmu2userName + "");
		
	
		
		keyWord.navigateToKeywordPage();
		keyWord.addKeyword(keywordname, color);
		keyWord.deleteKeywordByName(keywordname);
		keyWord.editExistigKeywordAndVerifyThem(keywordname);
		baseClass.passedStep("User have access to Manage > Keywords page and able to create, modify and delete keywords successfully.");
		
		
		loginPage.logout();
		
		//Re assigning the User to its original username
		// Login As PA
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa2userName + "");
		
		//Navigate to Users Page
		userManagement.navigateToUsersPAge();
		userManagement.editRoleFromPAToRMU(Input.rmu2userName, "Project Administrator",Input.projectName);
		baseClass.stepInfo("User had changed the access to original name");
		loginPage.logout();
		
		
	}


	/**
	 * @author Vijaya.Rani ModifyDate:16/08/2022 RPMXCON-52749
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description In Manage Keywords, keywords added are automatically truncated
	 *              without informing the user.
	 */
	@Test(description = "RPMXCON-52749", enabled = true, groups = { "regression" })
	public void verifyManageKeyWordAutomaticallytruncated() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52749");
		baseClass
				.stepInfo("In Manage Keywords, keywords added are automatically truncated without informing the user.");
		KeywordPage keyWord = new KeywordPage(driver);
		String keywordname = "Addkeyword" + Utility.dynamicNameAppender();
		String color = "Blue";
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		// Add keyword
		driver.waitForPageToBeReady();
		keyWord.navigateToKeywordPage();
		keyWord.addKeywordWithColor(keywordname, color);
		baseClass.passedStep("keyword group is not be more than 500 characters");
		keyWord.deleteKeyword(keywordname);
		// Add keyword
		driver.waitForPageToBeReady();
		keyWord.addKeywordWithColor(keywordname, color);
		baseClass.passedStep("keyword group is less than or equal to 500 characters");
		keyWord.deleteKeyword(keywordname);
		// Add keyword
		driver.waitForPageToBeReady();
		keyWord.addKeywordWithColor(keywordname, color);
		baseClass.passedStep(
				"Entered keyword group with 500 characters is considered by DocView and the corresponding highlights are applied");
		keyWord.deleteKeyword(keywordname);
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
