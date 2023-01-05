package sightline.smallComponents;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SystemLevelTemplate_Phase2_Regression {

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
	
	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] { 
			{ Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password }	
		};
				
	}


	/**
	 * @author Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *         RPMXCON-52939
	 * @throws Exception
	 * @Description Verify that When a Non-Domain project is created then
	 *              provisioned Redaction Tags are available in the Project- PA/RMU
	 */
	@Test(description = "RPMXCON-52939", enabled = true, dataProvider = "userDetails", groups = { "regression" })
	public void verifyNonDomainProjectCreatedProvisionedRedactionTags(String fullName, String userName, String password) throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-52939");
		baseClass.stepInfo(
				"Verify that When a Non-Domain project is created then provisioned Redaction Tags are available in the Project- PA/RMU");
		RedactionPage redactionPage = new RedactionPage(driver);
		
        //  login as 
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject(Input.NonDomainProject);
		baseClass.stepInfo("Select as nondomain project");
		this.driver.getWebDriver().get(Input.url + "Redaction/Redaction");
		baseClass.stepInfo("Navigate to Redaction tag page successfully");
		redactionPage.verifyReductionTagPrivacyAndPriviledge();
		driver.waitForPageToBeReady();
		redactionPage.getSecurityGrp().waitAndClick(10);
		baseClass.waitForElement(redactionPage.getDefaultSecurityGroup());
		redactionPage.getDefaultSecurityGroup().waitAndClick(5);
		baseClass.stepInfo("Clicked default security group");
		redactionPage.verifyReductionTagPrivacyAndPriviledge();
		loginPage.logout();
	}
	
	/**
	 * @author Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *         RPMXCON-52975
	 * @throws Exception
	 * @Description Verify that When a Domain project is created then provisioned CF
	 *              with specific help messages is available in the Project- RMU
	 */
	@Test(description = "RPMXCON-52975", enabled = true, groups = { "regression" })
	public void verifyCreatedProvisionedCFHelpMsgAvailableInRmu() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-52975");
		base.stepInfo(
				"Verify that When a Domain project is created then provisioned CF with specific help messages  is  available in the Project- RMU");
		CodingForm codingForm = new CodingForm(driver);
		String codingfrom = "CF" + Utility.dynamicNameAppender();
		String comment = "Comment" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("User successfully logged into slightline webpage  Rmu as with " + Input.rmu1userName + "");
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		base.stepInfo("User navigated to manage codingform page successfully");
		base.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		base.waitForElement(codingForm.getCodingForm_FirstTag());
		codingForm.getCodingForm_FirstTag().waitAndClick(10);
		base.waitForElement(codingForm.getCodingForm_AddToFormButton());
		codingForm.getCodingForm_AddToFormButton().Click();
		base.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingfrom);
		base.waitForElement(codingForm.getCodingForm_HelpText());
		codingForm.getCodingForm_HelpText().SendKeys(comment);
		base.waitForElement(codingForm.getSaveCFBtn());
		codingForm.getSaveCFBtn().Click();
		base.waitForElement(codingForm.getCodingForm_Validation_ButtonYes());
		codingForm.getCodingForm_Validation_ButtonYes().Click();
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		String helptext = codingForm.getCodingForm_HelpText().getText();
		base.waitTime(5);
		codingForm.getCF_PreviewButton().waitAndClick(3);
		System.out.println(helptext);
		String text = codingForm.getCFHelpmsgTitleInCodingForm().GetAttribute("title");
		if (text.contains(comment)) {
			base.passedStep("Provisioned CF is created  " + text
					+ "  specific help messages is  available in the Project- RMU successfully ");

		} else {
			base.failedStep("Help msg is not available");
		}

	}

	/**
	 * @author Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *         RPMXCON-52979
	 * @throws Exception
	 * @Description Verify that When a Non-Domain project is created then
	 *              provisioned CF with specific help messages is available in the
	 *              Project- RMU
	 */
	@Test(description = "RPMXCON-52979", enabled = true, groups = { "regression" })
	public void verifyNonDomainProjectProvisionedCFHelpMsgAvailableInRmu() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-52979");
		base.stepInfo(
				"Verify that When a Non-Domain project is created then provisioned CF with specific help messages  is  available in the Project- RMU");
		CodingForm codingForm = new CodingForm(driver);
		String codingfrom = "CF" + Utility.dynamicNameAppender();
		String comment = "Comment" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("User successfully logged into slightline webpage  Rmu as with " + Input.rmu1userName + "");
		base.selectproject(Input.NonDomainProject);
		base.stepInfo("Select as Nondomain project");
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		base.stepInfo("User navigated to manage codingform page successfully");
		base.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		base.waitForElement(codingForm.getCodingForm_FirstTag());
		codingForm.getCodingForm_FirstTag().waitAndClick(10);
		base.waitForElement(codingForm.getCodingForm_AddToFormButton());
		codingForm.getCodingForm_AddToFormButton().Click();
		base.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingfrom);
		base.waitForElement(codingForm.getCodingForm_HelpText());
		codingForm.getCodingForm_HelpText().SendKeys(comment);
		base.waitForElement(codingForm.getSaveCFBtn());
		codingForm.getSaveCFBtn().Click();
		base.waitForElement(codingForm.getCodingForm_Validation_ButtonYes());
		codingForm.getCodingForm_Validation_ButtonYes().Click();
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
		base.waitTime(5);
		codingForm.getCF_PreviewButton().waitAndClick(3);
		String helptext = codingForm.getCodingForm_HelpText().getText();
		System.out.println(helptext);
		String text = codingForm.getCFHelpmsgTitle().GetAttribute("title");
		if (text.contains(comment)) {
			base.passedStep("Provisioned CF is created  " + text
					+ "  specific help messages is  available in Nondomain Project- RMU successfully ");

		} else {
			base.failedStep("Help msg is not available");
		}

	}

	/**
	 * @author Vijaya.Rani ModifyDate:07/09/2022 RPMXCON-53130
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that When a Non-Domain project is created then
	 *              provisioned Folder Group is available in the Project- PA/RMU.
	 * 
	 */
	@Test(description = "RPMXCON-53130", enabled = true, groups = { "regression" })
	public void verifyPADeleteTagsFloderRedactionFromGlobal() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-53130");
		baseClass.stepInfo(
				"Verify that When a Non-Domain project is created then provisioned Folder Group is available in the Project- PA/RMU.");

		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagAndFolder = new TagsAndFoldersPage(driver);
		SoftAssert softassertion = new SoftAssert();
		String folder="new" + Utility.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		
		baseClass.stepInfo("Select NonDomain Project");
		baseClass.selectproject(Input.NonDomainProject);

		baseClass.stepInfo("Go to Manage Tags And Folder Page");
		tagAndFolder.navigateToTagsAndFolderPage();
		tagAndFolder.provisionedFolder();
		baseClass.passedStep("provisioned Productions available under Folder Group");

		baseClass.stepInfo("Select Default Security Group in DropDown");
		tagAndFolder.getSecurityGroup().selectFromDropdown().selectByVisibleText(Input.securityGroup);

		tagAndFolder.CreateFolder(folder, Input.securityGroup);
		driver.waitForPageToBeReady();
		tagAndFolder.getFolderExpandIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		tagAndFolder.getFolderExpandIcon().Click();
		softassertion.assertTrue(tagAndFolder.getFolderExpand().Displayed());
		baseClass.passedStep("User expand nested Folders/Tags on Manage Tags/Folders on screen Successfully.");
		softassertion.assertAll();
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:07/09/2022 RPMXCON-52938
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that When a Non-Domain project is created then
	 *              provisioned Folder is available in the Project- PA/RMU.
	 * 
	 */
	@Test(description = "RPMXCON-52938", enabled = true, groups = { "regression" })
	public void verifyNonDomainProjectFolderavailable() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52938");
		baseClass.stepInfo(
				"Verify that When a Non-Domain project is created then provisioned Folder is available in the Project- PA/RMU.");

		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagAndFolder = new TagsAndFoldersPage(driver);
		SoftAssert softassertion = new SoftAssert();
		String folder="new" + Utility.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		baseClass.stepInfo("Select NonDomain Project");
		baseClass.selectproject(Input.NonDomainProject);

		baseClass.stepInfo("Go to Manage Tags And Folder Page");
		tagAndFolder.navigateToTagsAndFolderPage();
		tagAndFolder.provisionedFolder();
		baseClass.passedStep("provisioned Productions available under Folder Group");

		baseClass.stepInfo("Select Default Security Group in DropDown");
		tagAndFolder.getSecurityGroup().selectFromDropdown().selectByVisibleText(Input.securityGroup);
		
		tagAndFolder.CreateFolder(folder, Input.securityGroup);
		driver.waitForPageToBeReady();
		tagAndFolder.getFolderExpandIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		tagAndFolder.getFolderExpandIcon().Click();
		softassertion.assertTrue(tagAndFolder.getFolderExpand().Displayed());
		baseClass.passedStep("User expand nested Folders/Tags on Manage Tags/Folders on screen Successfully.");
		softassertion.assertAll();
		loginPage.logout();

	}
	
	/**
	 * @author Mohan.Venugopal ModifyDate:12/09/2022 RPMXCON-52980 
	 * @throws Exception 
	 * @Description Verify that provisioned Redaction Tags is working properly in DocView in the Project- RMU
	 * 
	 */
	@Test(description = "RPMXCON-52980", enabled = true, groups = { "regression" })
	public void verifyProvisionedRedactionTagsForRMU() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52980");
		baseClass.stepInfo(
				"Verify that provisioned Redaction Tags is working properly in DocView in the Project- RMU");

		sessionSearch = new SessionSearch(driver);
		RedactionPage redactionPage  = new RedactionPage(driver);
		
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		//Navigate to Redaction Tag Page and verify redaction tag provisioned
		redactionPage.navigateToRedactionsPageURL();
		redactionPage.verifyRedactionTagPage();
		
		//Search for docs and Navigate to DocView
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.viewInDocView();
		
		//Verify that provisioned Redaction Tags is working properly in DocView in the Project- RMU
		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		docViewRedactions.selectMiniDocListAndViewInDocView(2);
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
		baseClass.waitForElement(docViewRedactions.redactionIcon());
		docViewRedactions.redactionIcon().waitAndClick(5);
		docViewRedactions.performThisPageRedaction("Default Redaction Tag");
		docViewRedactions.selectMiniDocListAndViewInDocView(3);
		driver.waitForPageToBeReady();
		docViewRedactions.performThisPageRedaction("Redacted Privacy");
		docViewRedactions.selectMiniDocListAndViewInDocView(4);
		docViewRedactions.performThisPageRedaction("Redacted Privilege");
		baseClass.passedStep("Provisioned Redaction Tags are working properly in DocView in the Project- RMU successfully");
		
		//logout
		loginPage.logout();
		
		
		
		
	}
	
	/**
	 * @author Mohan.Venugopal ModifyDate:12/09/2022 RPMXCON-52974 
	 * @throws Exception 
	 * @Description Verify that When a Domain project is created then provisioned CF with Specific logic  is  available in the Project- RMU
	 * 
	 */
	@Test(description = "RPMXCON-52974", enabled = true, groups = { "regression" })
	public void verifyCodingFormWithSpecificLogicInDomainProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52974");
		baseClass.stepInfo(
				"Verify that When a Domain project is created then provisioned CF with Specific logic is available in the Project- RMU");

		sessionSearch = new SessionSearch(driver);
		CodingForm codingForm = new CodingForm(driver);
		String codingFormName="SpecificLogic"+Utility.dynamicNameAppender();
		
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		//Navigate to CF Page.
		codingForm.navigateToCodingFormPage();
		codingForm.PreviewValidations(codingFormName,"tag","Check Item","Make It Optional");
		baseClass.passedStep("Provisioned CF gets created with Specific logic are available in the Project- RMU.");
		
		
		//logout
		loginPage.logout();
		
	}
	
	/**
	 * @author Mohan.Venugopal ModifyDate:13/09/2022 RPMXCON-52978 
	 * @throws Exception 
	 * @Description Verify that When a Non-Domain project is created then provisioned CF with Specific logic  is  available in the Project- RMU
	 * 
	 */
	@Test(description = "RPMXCON-52978", enabled = true, groups = { "regression" })
	public void verifyCodingFormWithSpecificLogicInNonDomainProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52978");
		baseClass.stepInfo(
				"Verify that When a Non-Domain project is created then provisioned CF with Specific logic  is  available in the Project- RMU");

		sessionSearch = new SessionSearch(driver);
		CodingForm codingForm = new CodingForm(driver);
		String codingFormName="SpecificLogic"+Utility.dynamicNameAppender();
		
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		//Select Non-Domain Project
		baseClass.selectproject(Input.NonDomainProject);
		
		//Navigate to CF Page.
		codingForm.navigateToCodingFormPage();
		codingForm.PreviewValidations(codingFormName,"tag","Check Item","Make It Optional");
		baseClass.passedStep("Provisioned CF gets created with Specific logic are available in the Project- RMU.");
		
		
		//logout
		loginPage.logout();
		
	}
	
	
	/**
	 * @author Mohan.Venugopal ModifyDate:13/09/2022 RPMXCON-52977 
	 * @throws Exception 
	 * @Description Verify that When a Non-Domain project is created then provisioned CF with Specific Tags and Order is  available in the Project- RMU
	 * 
	 */
	@Test(description = "RPMXCON-52977", enabled = true, groups = { "regression" })
	public void verifyCodingFormWithSpecificTagsAndOrdersInNonDomainProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52977");
		baseClass.stepInfo(
				"Verify that When a Non-Domain project is created then provisioned CF with Specific Tags and Order is  available in the Project- RMU");

		sessionSearch = new SessionSearch(driver);
		CodingForm codingForm = new CodingForm(driver);
		String codingFormName="SpecificTagsAndOrders"+Utility.dynamicNameAppender();
		
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		
		//Select Non-Domain Project
		baseClass.selectproject(Input.NonDomainProject);
		
		//Navigate to CF Page.
		codingForm.navigateToCodingFormPage();
		codingForm.validateSpecificTagsAndOrdersInCodingForm(codingFormName,"tag","check item","Make It Required");
		baseClass.passedStep("Provisioned CF are created with Specific Tags and Order are available in the Project- RMU.");
		
		
		//logout
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
