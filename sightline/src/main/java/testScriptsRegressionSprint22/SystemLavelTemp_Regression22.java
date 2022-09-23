package testScriptsRegressionSprint22;

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

public class SystemLavelTemp_Regression22 {

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
		base.waitTime(5);
		codingForm.getCF_PreviewButton().waitAndClick(3);
		String helptext = codingForm.getCodingForm_HelpText().getText();
		System.out.println(helptext);
		String text = codingForm.getCFHelpmsgTitle().GetAttribute("title");
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
