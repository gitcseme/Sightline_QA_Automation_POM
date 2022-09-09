package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.Keys;
import org.testng.Assert;
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
import pageFactory.BatchPrintPage;
import pageFactory.ClientsPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_ProjectCreation_Regresssion_01 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
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
	SoftAssert softAssertion;
	BatchPrintPage batchPrint;
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
	 * date: 07/26/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate new project creation of Domain client type by Domain Admin(Impersonate from System Admin)
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52897",enabled = true, groups = {"regression" })
	public void validateNewlyCreatedProjectInDomainAdmin() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52897");
		base.stepInfo("Validate new project creation of Domain client type by Domain Admin(Impersonate from System Admin)");
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		project = new ProjectPage(driver);
		
		String projectName = Input.randomText + Utility.dynamicNameAppender();
		String alfaNumeric = "Aa006";
		
		//login as da
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		base.impersonateSAtoDA(Input.domainName);
		
		//navigated to project page
		driver.waitForPageToBeReady();
		project.navigateToProductionPage();
		base.stepInfo("navigatd to Project Page");
		
		//add new project button clicked
		driver.waitForPageToBeReady();
		base.waitForElement(project.getAddProjectBtn());
		project.getAddProjectBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.stepInfo("add new project button was clicked");
		base.waitForElement(project.getProjectName());
		project.getProjectName().SendKeys(projectName);
		
		//verificataion
		if(base.text(Input.domainName).isDisplayed()) {
			base.passedStep("Client name was list all client names that are part of the selected \"Domain\"");
		}else {
			base.failedStep("client name display failed");
		}
		if(base.text("Database").isElementPresent()&&base.text("Workspace").isElementPresent()) {
			base.passedStep("Database and workspace paths was populate of the selected Domain");
		}else {
			base.failedStep("workspace and database display failed");
		}
		if(!base.text("HCode").isDisplayed()) {
			base.passedStep("\"HCode\" field is not present ");
		}else {
			base.failedStep("Hcode was present");
		}
		project.getProductionFolder().ScrollTo();
		if( project.getProjectFolder().isDisplayed() && project.getIngestionFolder().isDisplayed() 
				&& project.getProductionFolder().isDisplayed() &&base.text("/").isElementPresent()) {
			base.passedStep("The project folder, ingestion folder and production folder was autopopulate with '/' ");
		}else {
			base.failedStep("'/' verification failed");
		}
		project.getFirmTextBox().SendKeys(alfaNumeric);
		project.getCorpClientTextBox().SendKeys(alfaNumeric);
		base.passedStep("User should be able to enter any alphanumeric string in the \"Firm\" and \"Corp Client\" text box.");
		if(!base.text("Settings").isDisplayed()) {
			base.stepInfo("\"Settings\" tab is not present");
		}else {
			base.failedStep("setting tab verification failed");
		}
		
		//save the project
		base.clearBullHornNotification();
		project.getButtonSaveProject().waitAndClick(10);
		base.waitForNotification();
		dash.getNotificationMessage(0, projectName); 
		
		//verify project available in project list
		project.filterTheProject(projectName);
		if(base.text(projectName).isDisplayed()) {
			base.passedStep("Newly created project should be available in project list");
		}else {
			base.failedStep("Project not dipalyed");
		}
		
		base.passedStep("Validate new project creation of Domain client type by Domain Admin(Impersonate from System Admin)");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/26/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate HCode value while creating new project
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52899",enabled = true, groups = {"regression" })
	public void validateHCodeValue() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52899");
		base.stepInfo("Validate HCode value while creating new project");
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		project = new ProjectPage(driver);
		
		String projectName = Input.randomText + Utility.dynamicNameAppender();
		String alfaNumeric = "Aa"+Utility.dynamicNameAppender();
		String hcode = "#!&%$hi123_"+Utility.dynamicNameAppender();
		
		//login as da
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//navigated to project page
		driver.waitForPageToBeReady();
		project.navigateToProductionPage();
		base.stepInfo("navigatd to Project Page");
		
		//add new project button clicked
		driver.waitForPageToBeReady();
		base.waitForElement(project.getAddProjectBtn());
		project.getAddProjectBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.stepInfo("add new project button was clicked");
		base.waitForElement(project.getProjectName());
		project.getProjectName().SendKeys(projectName);
		
		//verificataion
		if(base.text("Not a Domain").isDisplayed()) {
			base.passedStep("By default Client type is \"Not a Domain\" selected ");
		}else {
			base.failedStep("Not a domain verification failed");
		}
		if(project.getSelectEntity().Visible()) {
			base.passedStep("Client name is list all client names that are of type \"Not a Domain\"");
		}else {
			base.failedStep("client name verification failed");
		}
		project.getSelectEntity().selectFromDropdown().selectByIndex(1);
		if(base.text("Database").isElementPresent()&&base.text("Workspace").isElementPresent()) {
			base.passedStep("Database and workspace paths was populate of the selected Domain");
		}else {
			base.failedStep("workspace and database display failed");
		}
		project.getProjectDBServerDropdown().selectFromDropdown().selectByIndex(1);
		if(base.text("HCode").isDisplayed()) {
			base.passedStep("\"HCode\" field should be available for user entry");
		}else {
			base.failedStep("Hcode was not present");
		}
		project.getProductionFolder().ScrollTo();
		if( project.getProjectFolder().isDisplayed() && project.getIngestionFolder().isDisplayed() 
				&& project.getProductionFolder().isDisplayed()) {
			base.passedStep("The project folder, ingestion folder and production folder was autopopulate");
		}else {
			base.failedStep("verification failed");
		}
		project.getProjectServerPath().waitAndClick(10);
		project.getIngestionserverpath().selectFromDropdown().selectByIndex(0);
		project.getIngestionserverpath().selectFromDropdown().selectByIndex(1);
		project.getProductionserverpath().waitAndClick(10);
		project.getProjectFolder().SendKeys(Input.randomText);
		project.getIngestionFolder().SendKeys(Input.randomText);
		project.getProductionFolder().SendKeys(Input.randomText);
		project.getFirmTextBox().SendKeys(alfaNumeric);
		project.getCorpClientTextBox().SendKeys(alfaNumeric);
		base.passedStep("User should be able to enter any alphanumeric string in the \"Firm\" and \"Corp Client\" text box.");
		if(base.text("Settings").isDisplayed()) {
			base.passedStep("\"Settings\" tab was enabled and user should be able select/edit required values.");
		}else {
			base.failedStep("setting tab verification failed");
		}
		project.getAddProject_SettingsTab().waitAndClick(10);
		project.getNoOfDocuments().SendKeys("20000");
		project.getHCode().SendKeys(hcode+Keys.ENTER);
		if(base.text("You cannot specify any special characters in the field value").isDisplayed()) {
			base.passedStep("Appropriate validation message should be displayed indicating that it cannot containing special characters");
		}else {
			base.failedStep("validation message verification failed");
		}
		project.getHCode().SendKeys(alfaNumeric);
		
		
		//save the project
		base.clearBullHornNotification();
		project.getButtonSaveProject().waitAndClick(10);
		base.waitForNotification();
		dash.getNotificationMessage(0, projectName); 
		
		//verify project available in project list
		project.filterTheProject(projectName);
		if(base.text(projectName).isDisplayed()) {
			base.passedStep("Newly created project should be available in project list");
		}else {
			base.failedStep("Project not dipalyed");
		}
		int column = base.getIndex(project.getProjectTableHeader(), "IN DOMAIN");
		if(project.getColumValue(column).getText().trim().equals("No")) {
			base.passedStep("project was listed as Non-Domain project.");
		}else {
			base.failedStep("non domain verification failed");
		}
		
		base.passedStep("Validated HCode value while creating new project");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/26/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description : To verify that System Admin can change the Type from Domain to 'Not a Domain' from Edit popup window
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52923",enabled = true, groups = {"regression" })
	public void verifySysAdminChangeTypeDomainToNonDomain() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52923");
		base.stepInfo("To verify that System Admin can change the Type from Domain to 'Not a Domain' from Edit popup window");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();
		client = new ClientsPage(driver);
		project = new ProjectPage(driver);
		
		String clientName = "C"+ Utility.dynamicNameAppender();
		String domainid = "D"+ Utility.dynamicNameAppender();
		
		//add new client
		client.navigateToClientPage();
		base.stepInfo("Navigated to clientPage");
		client.AddDomainClient(clientName,domainid);
		
		//edit client
		client.filterClient(clientName);
		client.getClientEditBtn(clientName).waitAndClick(10);
		project.getSelectEntity().selectFromDropdown().selectByVisibleText("Not a Domain");
		driver.waitForPageToBeReady();
		if(!base.textValue("Database").isElementAvailable(1)) {
			base.passedStep("It should hide all the settings which was required for Domain Client.");
		}else {
			base.failedStep("setting hide verification failed");
		}
		if(base.text("Name").isElementPresent()&&base.text("Short Name").isElementPresent()&&base.text("Type").isElementPresent()) {
			base.passedStep("Only Client Name, Short Name and Type drop down is displayed");
		}else {
			base.failedStep("label verification failed");
		}
		client.getSaveBtn().waitAndClick(10);
		base.VerifySuccessMessage("The client details were updated successfully");
		base.CloseSuccessMsgpopup();
		//verify client
		client.filterClient(clientName);
		int column = base.getIndex(client.getClientTableHeaders(), "TYPE");
		if(client.getClientTableValue(clientName, column).getText().trim().equals("Not a Domain")) {
			base.passedStep("It is update the Type. Also there should not be any impact for already created projects.");
		}else {
			base.failedStep("type vetification failed");
		}
		client.deleteClinet(clientName);
		
		base.passedStep("verified that System Admin can change the Type from Domain to 'Not a Domain' from Edit popup window");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/26/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate modifying all editable field values and save changes for a domain project by System Admin(Impersonate to Domain Admin)
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52965",enabled = true, groups = {"regression" })
	public void validateAllEditableFiedSysAdminImperSonate() throws InterruptedException  {
		
		base = new BaseClass(driver);
		project = new ProjectPage(driver);
		
		base.stepInfo("Test case Id: RPMXCON-52965");
		base.stepInfo("Validate modifying all editable field values and save changes for a domain project by Domain Admin");
		
		String ModifyName = Input.randomText + Utility.dynamicNameAppender();
		
		//login as Sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		base.impersonateSAtoDA(Input.domainName);
		
		//action edit
		project.navigateToProductionPage();
		String projectName = project.checkTempDomainProjectIsAvailable();
		project.editProject(projectName);
		
		//modification
		project.getProjectName().SendKeys(ModifyName);
		project.getFirmTextBox().SendKeys(ModifyName);
		project.getCorpClientTextBox().SendKeys(ModifyName);
		if(!project.getVerifyInputValues(ModifyName).isElementAvailable(1)) {
			base.passedStep("The folder names will not be updated to reflect the change, and will remain the same");
		}else {
			base.failedStep("not update verification failed");
		}
		project.getButtonSaveProject().waitAndClick(10);
		base.VerifySuccessMessage("Project updated successfully");
		
		//verify
		project.editProject(ModifyName);
		if(project.getVerifyInputValues(ModifyName).isElementPresent()) {
			base.passedStep("Updated changes was reflect");
		}else {
			base.failedStep("updated the verification failed");
		}
		
		//restore default name
		project.getProjectName().SendKeys(projectName);
		project.getButtonSaveProject().waitAndClick(10);
		
		base.passedStep("Validated modifying all editable field values and save changes for a domain project by System Admin(Impersonate to Domain Admin)");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/02/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate modifying all editable field values and save changes for a domain project by Domain Admin
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53000",enabled = true, groups = {"regression" })
	public void verifyNotificationForProjectCompletionInDa() throws InterruptedException  {
		
		base = new BaseClass(driver);
		project = new ProjectPage(driver);
		dash = new DomainDashboard(driver);
		
		base.stepInfo("Test case Id: RPMXCON-53000");
		base.stepInfo("Verify that notification should be received upon completion of the project successfully with DA User");
		
		String name = Input.randomText + Utility.dynamicNameAppender();
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		project.navigateToProductionPage();
		base.clearBullHornNotification();
		project.AddDomainProjectViaDaUser(name);
		base.waitForNotification();
		dash.getNotificationMessage(0, name);
		
		base.passedStep("Verify that notification should be received upon completion of the project successfully with DA User");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/02/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify user must be able to click on Notification once background task get completed.
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53005",enabled = true, groups = {"regression" })
	public void verifyUserAbleToClickNotification() throws InterruptedException  {
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		project = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53005");
		base.stepInfo("Verify user must be able to click on Notification once background task get completed.");
		
		String ProjectName = Input.randomText + Utility.dynamicNameAppender();
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		driver.waitForPageToBeReady();
		project.navigateToProductionPage();
		base.clearBullHornNotification();
		project.AddDomainProjectViaDaUser(ProjectName);
		dash.naviageToDomainDashBoardPage();
		base.waitForNotification();
		String currentURL = driver.getWebDriver().getCurrentUrl();
		dash.getNotificationMessage(0, ProjectName);
		base.clickFirstBackRoundTask();
		driver.waitForPageToBeReady();
		base.passedStep("Any entry in the Background Tasks Page are clickable");
		String currentURL1 = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertNotEquals(currentURL, currentURL1);
		softAssertion.assertAll();
		base.passedStep("and will take the user to the respective page.");
		
		base.passedStep("Verify user must be able to click on Notification once background task get completed.");
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