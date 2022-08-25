package testScriptsRegressionSprint19;

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
import pageFactory.ClientsPage;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_Regression_05 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Input ip;
	Utility utility;
	SoftAssert softAssertion;
	DomainDashboard dash;
	ProjectPage project;
	ClientsPage client;
	UserManagement user;
	
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
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/11/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate Updating RMU to Domain Admin
	 */
	@Test(description = "RPMXCON-53168",enabled = true, groups = {"regression" })
	public void validatedRmuToDA() throws Exception  {
		
		user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53168");
		base.stepInfo("Validate Updating RMU to Domain Admin");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		String email = Utility.randomCharacterAppender(6)+"@consilio.com";
		
		//create a user
		user.createNewUser(Input.randomText, Input.randomText, Input.ReviewManager, email, Input.domainName, Input.projectName);
		base.CloseSuccessMsgpopup();
		
		//change role rmu to da
		user.filterByName(email);
		user.editLoginUser();
		base.waitForElement(user.getUserChangeDropDown());
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		base.getYesBtn().waitAndClick(5);
		user.saveButtonOfFunctionTab();
		base.VerifySuccessMessage("User profile was successfully modified");
		base.CloseSuccessMsgpopup();
		
		//verify
		String getRole = user.getTableData("ROLE", 1);
		softAssertion.assertEquals(getRole, Input.DomainAdministrator);
		base.passedStep("User should be updated to Domain Admin");
		
		//eit funality
		user.editLoginUser();
		user.clickFunctionnalityTab();
		user.getIngestion().waitAndClick(5);
		user.saveButtonOfFunctionTab();
		base.VerifySuccessMessage("User profile was successfully modified");
		
		//change role for another record
		base.stepInfo("performe action for same user another record");
		user.editLoginUser();
		base.waitForElement(user.getUserChangeDropDown());
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.ProjectAdministrator);
		base.getYesBtn().waitAndClick(5);
		user.selectProject().selectFromDropdown().selectByVisibleText(Input.projectName);
		user.saveButtonOfFunctionTab();
		base.VerifySuccessMessage("User profile was successfully modified");
		base.CloseSuccessMsgpopup();
		
		getRole = user.getTableData("ROLE", 1);
		softAssertion.assertEquals(getRole, Input.ProjectAdministrator);
		base.passedStep("User should be updated to Domain Admin");
		
		//edit funality
		user.editLoginUser();
		user.clickFunctionnalityTab();
		user.getIngestion().waitAndClick(5);
		user.saveButtonOfFunctionTab();
		base.VerifySuccessMessage("User profile was successfully modified");
		
		//delete added user
		user.filterTodayCreatedUser();
		user.filterByName(email);
		user.deleteUser();
		
		softAssertion.assertAll();
		base.passedStep("Validated Updating RMU to Domain Admin");
		loginPage.logout();
		
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/17/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify when Domain Admin assigned to 1 or more non-domain project then domain dropdown in the impersonation popup should display value "Not a Domain"
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53070",enabled = true, groups = {"regression" })
	public void verifyNOnDomainProjectInNotDomain() throws InterruptedException  {
		
		base = new BaseClass(driver);
		user = new UserManagement(driver);
		dash = new DomainDashboard(driver);
		
		base.stepInfo("Test case Id: RPMXCON-53070");
		base.stepInfo("Verify when Domain Admin assigned to 1 or more non-domain project then domain dropdown in the impersonation popup should display value \"Not a Domain\"");
		
		//pre-req assign nondomain projet to da user
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		user.filterByName(Input.da1userName);
		String userName = user.getfirstUserName();
		if(!base.text(Input.NonDomainProject).isElementAvailable(2)) {
		user.AssignUserToProject(Input.NonDomainProject, Input.ProjectAdministrator, userName);
		}
		loginPage.logout();
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		dash.waitForDomainDashBoardIsReady();
		base.openImpersonateTab();
		base.selectImpersonateRole(Input.ProjectAdministrator);
		base.stepInfo("Select role as Project Admin  Check domain drop down from the pop up");
		base.selectImpersonateDomain("Not a Domain");
		base.stepInfo("Select \"Not a Domain\" from the domain dropdown,");
		if(base.getSelectDomain("Not a Domain").isDisplayed()) {
			base.passedStep("When the user selects \"not a domain\" from the domain dropdown, "
					+ "the project dropdown is list non-domain type projects for which this user has access to. (is a PAU or RMU or reviewer).");
		}else {
			base.failedStep("Not a Domain verification failed");
		}
		
		base.passedStep("Verified when Domain Admin assigned to 1 or more non-domain project then domain dropdown in the impersonation popup should display value \"Not a Domain\"");
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		user.UnAssignUserToProject(Input.NonDomainProject, Input.ProjectAdministrator, userName);
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
		System.out.println("******TEST CASES FOR DomainManagement EXECUTED******");

	}

}