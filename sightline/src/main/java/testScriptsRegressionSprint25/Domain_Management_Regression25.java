package testScriptsRegressionSprint25;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import pageFactory.ClientsPage;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Domain_Management_Regression25 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Utility utility;
	SoftAssert softAssertion;
	DomainDashboard domainDashboard;

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
		domainDashboard = new DomainDashboard(driver);
		
	}
	
	/**
	 * Author :Arunkumar date: 09/11/2022 TestCase Id:RPMXCON-52816
	 * Description :verify that Created By and Created On date column should be displayed on the Grid
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-52816",enabled = true, groups = { "regression" })
	public void verifyColumnDisplayedInGrid() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-52816");
		baseClass.stepInfo("verify that Created By and Created On date column should be displayed on the Grid");
		
		//Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		baseClass.stepInfo("go to manage clients section");
		domainDashboard.navigateToManageClientSection();
		baseClass.passedStep("Manage clients page displayed");
		baseClass.stepInfo("Verify columns displayed on grid");
		domainDashboard.verifyColumnPresentInClientGridTable();
		loginPage.logout();
	}
	/**
	 * @author Brundha.T Testcase No:RPMXCON-52921
	 * @Description:To verify that System Admin can change the Type from Non-Domain
	 *                 to Domain from Edit popup window
	 **/
	@Test(description = "RPMXCON-52921", enabled = true, groups = { "regression" })
	public void verifyNewPathInIngestionFolder() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-52921");
		baseClass.stepInfo(
				"To verify that System Admin can change the Type from Non-Domain to Domain from Edit popup window");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		String projectName = "Project" + Utility.dynamicNameAppender();
		System.out.println(projectName);
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(4);

		ProjectPage projects = new ProjectPage(driver);
		ClientsPage Client = new ClientsPage(driver);

		baseClass.stepInfo("Navigating to client page");
		projects.navigateToClientFromHomePage();
		projects.addNewClient_NonDomainProject(clientName, shortName, "Not a Domain");

		baseClass.stepInfo("Filter the not a domain client");
		Client.filterClient(clientName);

		baseClass.stepInfo("Editing existing not a domain client to domain");
		Client.getClientEditBtn(clientName).waitAndClick(10);
		projects.getSelectEntity().selectFromDropdown().selectByVisibleText("Domain");
		driver.waitForPageToBeReady();

		String domainName = "D" + Utility.dynamicRandomNumberAppender();
		baseClass.waitForElement(projects.getDomainName());
		projects.getDomainName().SendKeys(domainName);
		driver.scrollingToBottomofAPage();

		baseClass.waitForElement(projects.getProjectDBServerDropdown());
		if (projects.getProjectDBServerDropdown().isElementPresent()) {
			baseClass.passedStep("Servers are displayed as expected");
		} else {
			baseClass.failedStep("Servers are not displayed");
		}
		projects.getProjectDBServerDropdown().selectFromDropdown().selectByIndex(1);
		projects.getProjectServerPath().waitAndClick(10);
		baseClass.waitForElement(projects.getIngestionserverpath());
		projects.getIngestionserverpath().waitAndClick(2);
		baseClass.waitForElement(projects.getProductionserverpath());
		projects.getProductionserverpath().waitAndClick(2);
		projects.getClientNameSaveBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("The client details were updated successfully");

		baseClass.stepInfo("verifying updated domain type in grid page");
		Client.filterClient(clientName);
		int ColHeader = baseClass.getIndex(Client.getClientTableHeaders(), "TYPE");
		String Domain = Client.getColumnValueinDomainClient(ColHeader).getText();
		if (Domain.equals("Domain")) {
			baseClass.stepInfo("Non-domin project is updated as domain");
		} else {
			baseClass.failedStep("Non-domain Project is not updated as domain");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-52858
	 * @Description:Validate “In Domain” column value sorting on Project list screen
	 *                       for System Admin user
	 **/
	@Test(description = "RPMXCON-52858", enabled = true, groups = { "regression" })
	public void verifyingSortingOfColumnInProjects() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-52858");
		baseClass.stepInfo("Validate “In Domain” column value sorting on Project list screen for System Admin user");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		ProjectPage projects = new ProjectPage(driver);

		baseClass.stepInfo("Navigating to Projects Page");
		projects.navigateToProductionPage();

		baseClass.stepInfo("verifying Ascending and descending sorting in IN DOMAIN column");
		for (int i = 1; i <= 2; i++) {
			driver.waitForPageToBeReady();
			baseClass.text("In Domain").waitAndClick(5);

			if (i == 1) {
				projects.verifyingSortingOrderInColumn("IN DOMAIN", true, "Ascending");
			} else {
				projects.verifyingSortingOrderInColumn("IN DOMAIN", true, "Descending");
			}
			baseClass.stepInfo(
					"Selecting navigation Button and verifying the sorting order is maintained in Successive pages");
			driver.waitForPageToBeReady();
			projects.getLastPageNavigation().waitAndClick(5);

			if (i == 1) {
				projects.verifyingSortingOrderInColumn("IN DOMAIN", true, "Ascending");
			} else {
				projects.verifyingSortingOrderInColumn("IN DOMAIN", true, "Descending");
			}
		}

		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-52833
	 * @Description:Verify that from Add User pop up the Project drop-down should
	 *                     show only projects belonging to the current domain
	 **/
	@Test(description = "RPMXCON-52833", enabled = true, groups = { "regression" })
	public void verifyingProjectDropdownInAddNewUser() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-52833");
		baseClass.stepInfo(
				"Verify that from Add User pop up the Project drop-down should show only projects belonging to the current domain");

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in As " + Input.da1userName);

		baseClass.selectdomain(Input.domainName);

		ArrayList<String> Values = new ArrayList<>();
		ArrayList<String> DropDownValues = new ArrayList<>();
		ProjectPage projects = new ProjectPage(driver);
		UserManagement user = new UserManagement(driver);

		baseClass.stepInfo("Navigating to Projects Page");
		projects.navigateToProductionPage();

		baseClass.waitTime(3);
		baseClass.stepInfo("getting the projects in current domain");
		String Count = projects.getLastPageNavigation().getText();
		int TotalCount = Integer.valueOf(Count);
		System.out.println(TotalCount);

		for (int i = 0; i <= TotalCount; i++) {
			baseClass.waitTime(1);
			int size = user.getProjectNameCol().size();
			for (int j = 1; j <=size; j++) {
				driver.waitForPageToBeReady();
				String PrjtName = user.getProjectNameColValue(j).getText();
				Values.add(PrjtName);
			}
			System.out.println(Values);
			driver.waitForPageToBeReady();
			String NextBtn = user.getNextBtn().GetAttribute("Class");
			if (NextBtn.contains("disabled")) {
				break;
			} else {
				driver.waitForPageToBeReady();
				user.getUserListNextButton().waitAndClick(5);
			}
		}
		user.navigateToUsersPAge();
		baseClass.stepInfo("Selecting Add new user and validating the project dropdown");
		baseClass.waitForElement(user.getAddUserBtn());
		user.getAddUserBtn().Click();
		driver.waitForPageToBeReady();
		user.getSelectRole().selectFromDropdown().selectByVisibleText(Input.ProjectAdministrator);
		int Size = user.getProjectDropdown().selectFromDropdown().getOptions().size();
		baseClass.waitTime(2);
		for (int i = 2; i <= Size; i++) {
			String DropText = user.getDomainProjectDropdown(i).GetAttribute("title");
			DropDownValues.add(DropText);
		}
		System.out.println(DropDownValues);
		if (Values.equals(DropDownValues)) {
			baseClass.passedStep("Project drop-down shows projects belonging to the current domain of domain admin");
		} else {
			baseClass.failedStep("Project drop-down not showing projects belonging to the current domain of domain admin");
		}
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
		System.out.println("******TEST CASES EXECUTED******");

	}
}
